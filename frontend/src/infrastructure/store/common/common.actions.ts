import {createAction, props} from '@ngrx/store';

export const ERROR_RAISED = '[Error] An error has been raised';
export const errorRaised = createAction(ERROR_RAISED, props<ErrorPayload>());
export const LOAD_ALL_DATA = '[Application] Load full All datas';
export const loadAllData = createAction(LOAD_ALL_DATA);
export const LOAD_ALL_RESTRICTED_DATA = '[Application] Load restricted All datas';
export const loadAllRestrictedData = createAction(LOAD_ALL_RESTRICTED_DATA);

export interface ErrorPayload {
  data: {
    error: string
    message: string
    status: number
    statusText: string
    ok: boolean
    name: string
  };
}
