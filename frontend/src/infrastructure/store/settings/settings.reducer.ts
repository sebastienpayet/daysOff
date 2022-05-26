import {createReducer, on} from '@ngrx/store';
import {StoreData} from '@store/store-data';
import {Settings} from '@domain/settings/settings';
import {loadSettingsSuccess, SettingsPayload} from '@store/settings/settings.actions';

export const initialState: StoreData<Settings> = {entities: new Map<string, Settings>()};

const SETTINGS_REDUCER = createReducer(initialState,
  on(loadSettingsSuccess, (state, payload: SettingsPayload) => {
      const newState = {entities: new Map<string, Settings>()};
      newState.entities.set(payload.settings.id, payload.settings);
      return newState;
    }
  ),
);

export function settingsReducer(state, action): StoreData<Settings> {
  return SETTINGS_REDUCER(state, action);
}
