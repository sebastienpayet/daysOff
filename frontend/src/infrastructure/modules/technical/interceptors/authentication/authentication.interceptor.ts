import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Observable} from 'rxjs';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const encryptedToken = this.authenticationService.getEncryptedTokenFromLocalStorage();
    if (encryptedToken) {
      // Clone the request and replace the original headers with
      // cloned headers, updated with the authorization.
      const authReq = req.clone({
        headers: req.headers.set('token', encryptedToken)
      });
      // send cloned request with header to the next handler.
      return next.handle(authReq);
    } else {
      return next.handle(req);
    }
  }
}
