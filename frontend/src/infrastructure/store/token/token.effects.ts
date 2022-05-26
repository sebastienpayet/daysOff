import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, concatMap, delay, map, mergeMap, switchMap, tap} from 'rxjs/operators';
import {of} from 'rxjs';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {askForLogin, LOGIN_SUCCESS, loginSuccess, logout} from './token.actions';
import {Router} from '@angular/router';
import {ERROR_RAISED} from '@store/common/common.actions';
import {CommonEffects} from '@store/common/common.effects';
import {Role} from '@domain/user/role.enum';


@Injectable()
export class TokenEffects {
  askForLogin$ = createEffect(() => this.actions$.pipe(
      ofType(askForLogin),
      concatMap((action) => {
        this.authenticationService.handshake().subscribe();
        return of(action)
      }),
      delay(150), // give time to browser to save the xsrf cookie
      mergeMap((action) =>
        this.authenticationService.login(action.username, action.password)
          .pipe(
            map(data => ({
              type: LOGIN_SUCCESS,
              encryptedToken: data.body,
              token: this.authenticationService.decryptToken(data.body)
            })),
            catchError((error) => of({type: ERROR_RAISED, data: error}))
          )
      )
    )
  );

  loginSuccess$ = createEffect(() => this.actions$.pipe(
      ofType(loginSuccess),
      tap((payload) => {
        this.authenticationService.saveToken(payload.encryptedToken);
        this.authenticationService.redirectToAskedUrl();
      }),
      switchMap(() => { // load all needed data after login
        if (this.authenticationService.token.userRole === Role.ADMINISTRATOR.toString()) {
          return CommonEffects.adminFullLoadActions;
        } else {
          return CommonEffects.restrictedFullLoadActions;
        }
      }),
      catchError((error) => of({type: ERROR_RAISED, data: error}))
    )
  );

  logOut$ = createEffect(() => this.actions$.pipe(
      ofType(logout),
      tap(() => {
          if (!this.router.url.endsWith('login')) {
            this.router.navigate(['login']).then(r => r);
          }
        }
      ),
      catchError((error) => of({type: ERROR_RAISED, data: error}))
    ),
    {dispatch: false});


  constructor(
    private actions$: Actions,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
  }
}
