import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';

import {MainComponent} from '@modules/main/main-component/main.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MainMenuComponent} from '@modules/main/main-menu/main-menu.component';
import {LoginComponent} from '@modules/main/login/login.component';
import {HttpClientModule, HttpClientXsrfModule} from '@angular/common/http';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {userReducer} from '@store/user/user.reducer';
import {UserEffects} from '@store/user/user.effects';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {environment} from '@environments/environment';
import {clearState, tokenReducer} from '@store/token/token.reducer';
import {TokenEffects} from '@store/token/token.effects';
import {AppRoutingModule} from '@modules/main/app-routing.module';
import {leaveReducer} from '@store/leave/leave.reducer';
import {LeaveEffects} from '@store/leave/leave.effects';
import {UserModule} from '@modules/user-interface/user/user.module';
import {LeaveBalanceModule} from '@modules/user-interface/leave-balance/leave-balance.module';
import {LeavesModule} from '@modules/user-interface/leaves/leaves.module';
import {BoardModule} from '@modules/user-interface/board/board.module';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatToolbarModule} from '@angular/material/toolbar';
import {SharedModule} from '@modules/technical/shared.module';
import {MatCardModule} from '@angular/material/card';
import {ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatNativeDateModule} from '@angular/material/core';
import {CommonEffects} from '@store/common/common.effects';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {TeamEffects} from '@store/team/team.effects';
import {teamReducer} from '@store/team/team.reducer';
import {leaveBalanceReducer} from '@store/leaveBalance/leave-balance.reducer';
import {LeaveBalanceEffects} from '@store/leaveBalance/leave-balance.effects';
import {settingsReducer} from '@store/settings/settings.reducer';
import {SettingsEffects} from '@store/settings/settings.effects';
import {TranslateModule, TranslateService} from '@ngx-translate/core';
import localeFr from '@angular/common/locales/fr';
import {registerLocaleData} from '@angular/common';
import {TeamModule} from '@modules/user-interface/team/team.module';

registerLocaleData(localeFr);

@NgModule({
  declarations: [
    MainComponent,
    MainMenuComponent,
    LoginComponent
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'fr-FR'}
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientXsrfModule,
    StoreModule.forRoot({
      users: userReducer,
      token: tokenReducer,
      leaves: leaveReducer,
      teams: teamReducer,
      leaveBalances: leaveBalanceReducer,
      settings: settingsReducer
    }, {metaReducers: [clearState]}),
    EffectsModule.forRoot([
      UserEffects,
      TokenEffects,
      LeaveEffects,
      CommonEffects,
      TeamEffects,
      LeaveBalanceEffects,
      CommonEffects,
      SettingsEffects
    ]),
    StoreDevtoolsModule.instrument({
      maxAge: 25, // Retains last 25 states
      logOnly: environment.production, // Restrict extension to log-only mode
    }),
    HttpClientModule,
    UserModule,
    TeamModule,
    LeaveBalanceModule,
    LeavesModule,
    BoardModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    SharedModule,
    MatCardModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatTooltipModule,
    MatNativeDateModule,
    MatSnackBarModule,
    TranslateModule.forRoot()
  ],
  bootstrap: [MainComponent]
})
export class MainModule {
  constructor(translateService: TranslateService) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./login/login.component.fr.properties`),
      true);
  }
}
