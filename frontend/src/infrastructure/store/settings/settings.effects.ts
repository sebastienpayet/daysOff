import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {of} from 'rxjs';
import {ERROR_RAISED} from '@store/common/common.actions';
import {LOAD_SETTINGS_SUCCESS, loadSettings} from '@store/settings/settings.actions';
import {SettingsService} from '@services/settings/settings.service';


@Injectable()
export class SettingsEffects {

  loadSettings = createEffect(() => this.actions$.pipe(
      ofType(loadSettings),
      mergeMap(() => this.settingsService.load()
        .pipe(
          map(settings => ({type: LOAD_SETTINGS_SUCCESS, settings})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  constructor(
    private actions$: Actions,
    private settingsService: SettingsService
  ) {
  }
}
