import {createAction, props} from '@ngrx/store';
import {Token} from '@store/token/token';

export const LOGIN_SUCCESS = '[Token] Logged in';
export const LOGOUT = '[Token] Logged out';
export const ASK_FOR_LOGIN = '[Token] Ask for login';

export const askForLogin = createAction(ASK_FOR_LOGIN, props<LoginPayload>());
export const loginSuccess = createAction(LOGIN_SUCCESS, props<TokenPayload>());
export const logout = createAction(LOGOUT);

export interface LoginPayload {
  username: string;
  password: string;
}

export interface TokenPayload {
  encryptedToken: string;
  token: Token;
}

