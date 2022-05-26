import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, combineLatest, Observable, Subscription} from 'rxjs';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {Store} from '@ngrx/store';
import {loginSuccess, logout, TokenPayload} from '@store/token/token.actions';
import {ServiceConfiguration} from '@configuration/serviceConfiguration';
import {Token} from '@store/token/token';
import {map, switchMap, tap} from 'rxjs/operators';
import {CryptService} from '@services/crypt/crypt.service';
import {User} from '@domain/user/user';
import {UserStoreRepository} from '@store/user/user-store-repository.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService extends ServiceConfiguration implements OnDestroy {

  public redirectUrl = '/';
  private readonly TOKEN = 'token';
  private readonly KEY = 'k';
  public isAuthenticated$ = new BehaviorSubject<boolean>(this.isAuthenticated());
  public token: Token;
  private subscription: Subscription = new Subscription();
  private lastAuthDate: Date;

  constructor(
    private http: HttpClient,
    private router: Router,
    private tokenStore: Store<TokenPayload>,
    private cryptService: CryptService,
    private userStoreRepository: UserStoreRepository
  ) {
    super();
    this.subscription.add(
      this.tokenStore.select(state => state)
        .subscribe(payload => {
          this.token = payload.token;
        })
    );
  }

  public handshake(): Observable<HttpResponse<any>> {
    // reset x-csrf token from backend
    // just make a call to get the setCookie header
    return this.http.get<HttpResponse<any>>(this.url + '/user/handshake',
      {responseType: 'text' as 'json', observe: 'response' as 'body'}
    )
  }

  public login(login: string, credential: string): Observable<HttpResponse<any>> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post<HttpResponse<any>>(this.url + '/user/login', {login, credential},
      {headers, responseType: 'text' as 'json', observe: 'response' as 'body'}
    ).pipe(tap((data) => {
      this.lastAuthDate = new Date();
      localStorage.setItem(this.KEY, data.headers.get(this.KEY));
    }));
  }

  public saveToken(encryptedToken: string): void {
    // refresh local storage
    localStorage.setItem(this.TOKEN, encryptedToken);
    this.isAuthenticated$.next(true);
  }

  public getEncryptedTokenFromLocalStorage(): string {
    return localStorage.getItem(this.TOKEN);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN);
    localStorage.removeItem(this.KEY);
    this.isAuthenticated$?.next(false);
    this.tokenStore.dispatch(logout());
  }

  public getAuthenticatedUser(): Observable<User> {
    return combineLatest([
      this.tokenStore.select(state => state.token).pipe(map(token => token?.userId)),
    ]).pipe(
      switchMap(([userId]) => this.userStoreRepository.findById(userId))
    );
  }

  public isAuthenticated(): boolean {
    const now = new Date();

    // si local storage ok mais pas token store, alors synchro
    const encryptedToken = localStorage.getItem(this.TOKEN);

    if (encryptedToken && localStorage.getItem(this.KEY) !== undefined && !this.token) {
      this.token = this.decryptToken(encryptedToken);
      this.tokenStore.dispatch(loginSuccess(
        {
          encryptedToken,
          token: this.token
        }
      ));
    }

    const authenticated = encryptedToken !== undefined
      && this.token
      // check if the token is not timed out (with last request on interceptor)
      && ((now.getTime() - this.token.nonce.getTime()) / 1000) <= this.token.lifetime
      // check if the token lifetime has not expired
      && (!this.lastAuthDate || ((now.getTime() - this.lastAuthDate.getTime()) / 1000) <= this.token.inactivityTime);

    // refresh last time authenticated has been tested
    if (authenticated) {
      this.lastAuthDate = new Date();
    } else {
      this.logout();
    }
    return authenticated;
  }

  redirectToAskedUrl(): void {
    this.router.navigateByUrl(this.redirectUrl).then(r => r);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public decryptToken(encryptedToken: string): Token {
    const token = JSON.parse(this.cryptService.decrypt(encryptedToken, localStorage.getItem('k')));
    token.nonce = new Date(
      token.nonce[0],
      Math.max(0, token.nonce[1] - 1),
      token.nonce[2],
      token.nonce[3],
      token.nonce[4],
      token.nonce[5],
    );
    return token;
  }
}
