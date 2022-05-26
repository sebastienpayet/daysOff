import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, switchMap, tap} from 'rxjs/operators';
import {ERROR_RAISED, errorRaised, loadAllData, loadAllRestrictedData} from '@store/common/common.actions';
import {MatSnackBar} from '@angular/material/snack-bar';
import {of, pipe} from 'rxjs';
import {LOAD_SETTINGS} from '@store/settings/settings.actions';
import {LIST_ALL_OBFUSCATED_USERS, LIST_ALL_USERS} from '@store/user/user.actions';
import {LIST_ALL_LEAVES} from '@store/leave/leave.actions';
import {LIST_ALL_TEAMS} from '@store/team/team.actions';
import {LIST_LEAVE_BALANCES} from '@store/leaveBalance/leave-balance.actions';
import {TranslateService} from '@ngx-translate/core';


@Injectable()
export class CommonEffects {

  public static FOUR_SLIDING_YEARS = [
    new Date().getFullYear() - 2,
    new Date().getFullYear() - 1,
    new Date().getFullYear(),
    new Date().getFullYear() + 1
  ];

  public static adminFullLoadActions = [
    {type: LOAD_SETTINGS},
    {type: LIST_ALL_USERS},
    {type: LIST_ALL_LEAVES},
    {type: LIST_ALL_TEAMS},
    {type: LIST_LEAVE_BALANCES, years: CommonEffects.FOUR_SLIDING_YEARS},
  ];

  public static restrictedFullLoadActions = [
    {type: LOAD_SETTINGS},
    {type: LIST_ALL_OBFUSCATED_USERS},
    {type: LIST_ALL_LEAVES},
    {type: LIST_ALL_TEAMS},
    {type: LIST_LEAVE_BALANCES, years: CommonEffects.FOUR_SLIDING_YEARS},
  ];

  constructor(
    private actions$: Actions,
    private snackBar: MatSnackBar,
    private translateService: TranslateService
  ) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./common.effects.fr.properties`),
      true);
  }

  private readonly UNKNOWN_ERROR_RECEIVED = 'Unknown Error';
  private readonly UNKNOWN_ERROR = 'UNKNOWN_ERROR';

  errorRaised$ = createEffect(() => this.actions$.pipe(
      ofType(errorRaised),
      tap((payload) => {
        let translateKey = payload.data.error;
        if (payload.data.statusText === this.UNKNOWN_ERROR_RECEIVED ||
          (payload.data.status !== 401 && payload.data.status !== 422)) {
          translateKey = this.UNKNOWN_ERROR;
        }

        this.snackBar.open(
          this.translateService.instant('i18n.CommonEffects.' + translateKey), null,
          {duration: 3000, announcementMessage: 'ERROR', panelClass: 'snack-error'}
        );
      }))
    , {dispatch: false}
  );

  loadAllData$ = createEffect(() => this.actions$.pipe(
      ofType(loadAllData),
      pipe(
        switchMap(() => CommonEffects.adminFullLoadActions),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      )
    )
  );

  loadAllRestrictedData$ = createEffect(() => this.actions$.pipe(
      ofType(loadAllRestrictedData),
      pipe(
        switchMap(() => CommonEffects.restrictedFullLoadActions),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      )
    )
  );
}
