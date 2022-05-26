import {createReducer, on} from '@ngrx/store';
import {User} from '@domain/user/user';
import {
  createUserSuccess,
  listAllObfuscatedUsersSuccess,
  listAllUsersSuccess,
  updateUserSuccess,
  UserPayload,
  UsersPayload
} from './user.actions';
import {StoreData} from '@store/store-data';

export const initialState: StoreData<User> = {entities: new Map<string, User>()};

const USER_REDUCER = createReducer(initialState,
  on(listAllUsersSuccess, (state, payload: UsersPayload) => {
      const newState = {entities: new Map<string, User>()};
      payload.users.map((user) => newState.entities.set(user.id, user));
      return newState;
    }
  ),
  on(listAllObfuscatedUsersSuccess, (state, payload: UsersPayload) => {
      const newState = {entities: new Map<string, User>()};
      payload.users.map((user) => newState.entities.set(user.id, user));
      return newState;
    }
  ),
  on(createUserSuccess, (state, payload: UserPayload) => {
    state.entities.set(payload.user.id, payload.user);
    return {entities: state.entities};
  }),
  on(updateUserSuccess, (state, payload: UserPayload) => {
    state.entities.set(payload.user.id, payload.user);
    return {entities: state.entities};
  }),
  );

export function userReducer(state, action): StoreData<User> {
  return USER_REDUCER(state, action);
}

