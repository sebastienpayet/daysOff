import {createAction, props} from '@ngrx/store';
import {LeaveBalance} from '@domain/leave-balance/leave-balance';
import {LeaveBalanceCreateCommand} from '@services/leave-balance/command/leave-balance-create-command';
import {LeaveBalanceListCommand} from '@services/leave-balance/command/leave-balance-list-command';

export const LIST_LEAVE_BALANCES = '[Leave Balance] List all leaves balances';
export const LIST_LEAVE_BALANCES_SUCCESS = '[Leave Balance] List all leaves success';
export const CREATE_LEAVE_BALANCE = '[Leave Balance] Create a leave';
export const CREATE_LEAVE_BALANCE_SUCCESS = '[Leave Balance] Create a leave success';

export const listLeaveBalances = createAction(LIST_LEAVE_BALANCES, props<LeaveBalanceListCommand>());
export const listLeaveBalancesSuccess = createAction(LIST_LEAVE_BALANCES_SUCCESS, props<LeaveBalancesPayload>());
export const createLeaveBalance = createAction(CREATE_LEAVE_BALANCE, props<LeaveBalanceCreateCommand>());
export const createLeaveBalanceSuccess = createAction(CREATE_LEAVE_BALANCE_SUCCESS, props<LeaveBalancePayload>());

export interface LeaveBalancesPayload {
  leaveBalances: LeaveBalance[];
}

export interface LeaveBalancePayload {
  leaveBalance: LeaveBalance;
}
