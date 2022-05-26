import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {finalize, tap} from 'rxjs/operators';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {MatDialog} from '@angular/material/dialog';
import {Observable} from 'rxjs';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService,
              private dialog: MatDialog) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let status: number;

    // extend server response observable with logging
    return next.handle(req)
      .pipe(
        tap(
          // Succeeds when there is a response; ignore other events
          event => status = (event as HttpResponse<any>).status,
          // Operation failed; error is an HttpErrorResponse
          error => status = (error as HttpResponse<any>).status
        ),
        // Log when response observable either completes or errors
        finalize(() => {
          if (status >= 400) {
            this.dialog.closeAll();
            this.authenticationService.logout();
          }
        })
      );
  }
}
