import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RefreshViewOnChangeDirective} from '@modules/technical/directives/refresh-view-on-change/refresh-view-on-change.directive';
import {UserPipe} from './pipes/user.pipe';
import {NormalizeLowerCasePipe} from './pipes/normalizeLowerCase.pipe';
import {httpInterceptorProviders} from '@modules/technical/interceptors';
import {UserIdPipe} from './pipes/user-id.pipe';
import {TranslateModule} from '@ngx-translate/core';
import { HoursPipe } from './pipes/hours.pipe';
import { Round2DecimalPipe } from './pipes/round2-decimal.pipe';

@NgModule({
  declarations: [RefreshViewOnChangeDirective, UserPipe, NormalizeLowerCasePipe, UserIdPipe, HoursPipe, Round2DecimalPipe],
    exports: [
        CommonModule,
        RefreshViewOnChangeDirective,
        UserPipe,
        UserIdPipe,
        NormalizeLowerCasePipe,
        TranslateModule,
        HoursPipe,
        Round2DecimalPipe
    ],
  providers: [
    httpInterceptorProviders, NormalizeLowerCasePipe, UserPipe, UserIdPipe,
  ]
})
export class SharedModule {
}
