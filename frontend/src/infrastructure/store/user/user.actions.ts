import {createAction, props} from '@ngrx/store';
import {User} from '@domain/user/user';
import {UserCreateCommand} from '@services/user/command/user-create-command';
import {UserUpdateCommand} from '@services/user/command/user-update-command';

export const LIST_ALL_USERS = '[User] List all users asking';
export const LIST_ALL_USERS_SUCCESS = '[User] List all users success';
export const LIST_ALL_OBFUSCATED_USERS = '[User] List all obfuscated users asking';
export const LIST_ALL_OBFUSCATED_USERS_SUCCESS = '[User] List all obfuscated users success';
export const CREATE_USER = '[User] Create a user';
export const CREATE_USER_SUCCESS = '[User] Create a user success';
export const UPDATE_USER = '[User] Update a user';
export const UPDATE_USER_SUCCESS = '[User] Update a user success';

export const listAllUsers = createAction(LIST_ALL_USERS);
export const listAllUsersSuccess = createAction(LIST_ALL_USERS_SUCCESS, props<UsersPayload>());
export const listAllObfuscatedUsers = createAction(LIST_ALL_OBFUSCATED_USERS);
export const listAllObfuscatedUsersSuccess = createAction(LIST_ALL_OBFUSCATED_USERS_SUCCESS, props<UsersPayload>());

export const createUser = createAction(CREATE_USER, props<UserCreateCommand>());
export const createUserSuccess = createAction(CREATE_USER_SUCCESS, props<UserPayload>());
export const updateUser = createAction(UPDATE_USER, props<UserUpdateCommand>());
export const updateUserSuccess = createAction(UPDATE_USER_SUCCESS, props<UserPayload>());

export interface UsersPayload {
  users: User[];
}

export interface UserPayload {
  user: User;
}
