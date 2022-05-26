import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {Role} from '@domain/user/role.enum';
import {AuthenticationService} from '@services/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AdministratorGuard implements CanActivate {
  constructor(
    private authenticationService: AuthenticationService
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    return this.authenticationService.token.userRole === Role.ADMINISTRATOR;
  }

}
