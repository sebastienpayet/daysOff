/* "Barrel" of Http Interceptors */
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AuthenticationInterceptor} from './authentication/authentication.interceptor';
import {ErrorHandlerInterceptor} from './errorHandler/error-handler.interceptor';
import {DateHttpInterceptorInterceptor} from '@modules/technical/interceptors/date/date-http-interceptor-interceptor';
import {CsrfInterceptor} from '@modules/technical/interceptors/csrf/csrf.interceptor';

/** Http interceptor providers in outside-in order */
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: CsrfInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: ErrorHandlerInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: DateHttpInterceptorInterceptor, multi: true },
];
