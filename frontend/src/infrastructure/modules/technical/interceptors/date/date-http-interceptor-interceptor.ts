import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {convertToUTC} from '@modules/technical/helper/date-helper';

@Injectable()
export class DateHttpInterceptorInterceptor implements HttpInterceptor {

  iso8601 = /^\d{4}-\d\d-\d\dT\d\d:\d\d:\d\d(\.\d+)?(([+-]\d\d:\d\d)|Z)?$/;

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          const body = event.body;
          this.convertToDate(body);
        }
      }, (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status >= 400) {
          }
        }
      }),
    );
  }

  convertToDate(body): any {
    if (body === null || body === undefined) {
      return body;
    }

    if (typeof body !== 'object') {
      return body;
    }

    for (const key of Object.keys(body)) {
      const value = body[key];
      if (this.isIso8601(value)) {
        body[key] =  convertToUTC(new Date(value));
      } else if (typeof value === 'object') {
        this.convertToDate(value);
      }
    }
  }

  isIso8601(value): boolean {
    if (value === null || value === undefined) {
      return false;
    }

    return this.iso8601.test(value);
  }
}
