import {createAction, props} from '@ngrx/store';
import {Settings} from '@domain/settings/settings';

export const LOAD_SETTINGS = '[Settings] Load settings';
export const LOAD_SETTINGS_SUCCESS = '[Settings] Load settings success';

export const loadSettings = createAction(LOAD_SETTINGS);
export const loadSettingsSuccess = createAction(LOAD_SETTINGS_SUCCESS, props<SettingsPayload>());

export interface SettingsPayload {
  settings: Settings;
}
