import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {MatDialog} from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private dialog: MatDialog
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const url: string = state.url;
    return this.checkLogin(url);
  }

  checkLogin(url: string): true | UrlTree {
    // check authentication
    if (this.authenticationService.isAuthenticated()) {
      return true;
    }

    // Store the attempted URL for redirecting
    if (url.endsWith('login')) {
      url = ''; // redirect to home page in case of login direct access
    }
    this.authenticationService.redirectUrl = url;
    // Redirect to the login page
    this.dialog.closeAll();
    return this.router.parseUrl('/login');
  }

}
