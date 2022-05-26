import {createReducer, on} from '@ngrx/store';
import {createLeaveBalanceSuccess, LeaveBalancePayload, LeaveBalancesPayload, listLeaveBalancesSuccess} from './leave-balance.actions';
import {StoreData} from '@store/store-data';
import {LeaveBalance} from '@domain/leave-balance/leave-balance';

export const initialState: StoreData<LeaveBalance> = {entities: new Map<string, LeaveBalance>()};

const LEAVE_BALANCE_REDUCER = createReducer(initialState,
  on(listLeaveBalancesSuccess, (state, payload: LeaveBalancesPayload) => {
      const newState = {entities: new Map<string, LeaveBalance>()};
      payload.leaveBalances.map((leaveBalance) => newState.entities.set(leaveBalance.id, leaveBalance));
      return newState;
    }
  ),
  on(createLeaveBalanceSuccess, (state, payload: LeaveBalancePayload) => {
    state.entities.set(payload.leaveBalance.id, payload.leaveBalance);
    return {entities: state.entities};
  })
);

export function leaveBalanceReducer(state, action): StoreData<LeaveBalance> {
  return LEAVE_BALANCE_REDUCER(state, action);
}
