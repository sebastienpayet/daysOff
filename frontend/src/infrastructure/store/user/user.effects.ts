import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {
  CREATE_USER_SUCCESS,
  createUser, LIST_ALL_OBFUSCATED_USERS_SUCCESS,
  LIST_ALL_USERS_SUCCESS, listAllObfuscatedUsers,
  listAllUsers,
  UPDATE_USER_SUCCESS,
  updateUser
} from './user.actions';
import {UserService} from '@services/user/user.service';
import {of} from 'rxjs';
import {ERROR_RAISED} from '@store/common/common.actions';


@Injectable()
export class UserEffects {

  listAllUsers$ = createEffect(() => this.actions$.pipe(
    ofType(listAllUsers),
    mergeMap(() => this.userService.listAllUsers()
      .pipe(
        map(users => ({type: LIST_ALL_USERS_SUCCESS, users})),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      ))
    )
  );

  listAllObfuscatedUsers$ = createEffect(() => this.actions$.pipe(
      ofType(listAllObfuscatedUsers),
      mergeMap(() => this.userService.listAllObfuscatedUsers()
        .pipe(
          map(users => ({type: LIST_ALL_OBFUSCATED_USERS_SUCCESS, users})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  createUser$ = createEffect(() => this.actions$.pipe(
    ofType(createUser),
    mergeMap((command) => this.userService.create(command)
      .pipe(
        map(user => ({type: CREATE_USER_SUCCESS, user})),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      ))
    )
  );

  updateUser$ = createEffect(() => this.actions$.pipe(
    ofType(updateUser),
    mergeMap((command) => this.userService.update(command)
      .pipe(
        map(user => ({type: UPDATE_USER_SUCCESS, user})),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      ))
    )
  );

  constructor(
    private actions$: Actions,
    private userService: UserService
  ) {
  }
}
