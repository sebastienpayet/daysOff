import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {of} from 'rxjs';
import {ERROR_RAISED} from '@store/common/common.actions';
import {
  CREATE_LEAVE_BALANCE_SUCCESS,
  createLeaveBalance,
  LIST_LEAVE_BALANCES_SUCCESS,
  listLeaveBalances
} from '@store/leaveBalance/leave-balance.actions';
import {LeaveBalanceService} from '@services/leave-balance/leave-balance.service';


@Injectable()
export class LeaveBalanceEffects {

  listAllLeaveBalances$ = createEffect(() => this.actions$.pipe(
      ofType(listLeaveBalances),
      mergeMap((command) => this.leaveBalanceService.list(command)
        .pipe(
          map(leaveBalances => ({type: LIST_LEAVE_BALANCES_SUCCESS, leaveBalances})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  createLeaveBalance$ = createEffect(() => this.actions$.pipe(
      ofType(createLeaveBalance),
      mergeMap((command) => this.leaveBalanceService.create(command)
        .pipe(
          map(leaveBalance => ({type: CREATE_LEAVE_BALANCE_SUCCESS, leaveBalance})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  constructor(
    private actions$: Actions,
    private leaveBalanceService: LeaveBalanceService
  ) {
  }
}
