import {createReducer, on} from '@ngrx/store';
import {loginSuccess, LOGOUT} from './token.actions';
import {Token} from '@store/token/token';

export const initialState: Token = null;

const TOKEN_REDUCER = createReducer(initialState,
  on(loginSuccess, (state, payload) => payload.token),
);

export function tokenReducer(state, action): Token {
  return TOKEN_REDUCER(state, action);
}

export function clearState(reducer): any {
  return function(state, action): any {

    if (action.type === LOGOUT) {
      state = undefined;
    }

    return reducer(state, action);
  };
}
