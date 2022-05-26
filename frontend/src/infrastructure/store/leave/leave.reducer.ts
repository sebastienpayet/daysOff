import {createReducer, on} from '@ngrx/store';
import {
  createLeaveSuccess,
  deleteLeaveSuccess,
  LeavePayload,
  LeavesPayload,
  listAllLeavesSuccess,
  refuseLeaveSuccess,
  setLeaveToDraftSuccess,
  submitLeaveSuccess, updateLeaveSuccess, validateLeaveByManagementSuccess,
  validateLeaveByServiceSuccess
} from './leave.actions';
import {Leave} from '@domain/leave/leave';
import {StoreData} from '@store/store-data';

export const initialState: StoreData<Leave> = {entities: new Map<string, Leave>()};

const LEAVE_REDUCER = createReducer(initialState,
  on(listAllLeavesSuccess, (state, payload: LeavesPayload) => {
      const newState = {entities: new Map<string, Leave>()};
      payload.leaves.map((leave) => newState.entities.set(leave.id, leave));
      return newState;
    }
  ),
  on(createLeaveSuccess, (state, payload: LeavePayload) => {
    state.entities.set(payload.leave.id, payload.leave);
    return {entities: state.entities};
  }),
  on(submitLeaveSuccess, (state, payload: LeavePayload) => {
    state.entities.set(payload.leave.id, payload.leave);
    return {entities: state.entities};
  }),
  on(refuseLeaveSuccess, (state, payload: LeavePayload) => {
    state.entities.set(payload.leave.id, payload.leave);
    return {entities: state.entities};
  }),
  on(setLeaveToDraftSuccess, (state, payload: LeavePayload) => {
    state.entities.set(payload.leave.id, payload.leave);
    return {entities: state.entities};
  }),
  on(deleteLeaveSuccess, (state, payload: LeavePayload) => {
    state.entities.delete(payload.leave.id);
    return {entities: state.entities};
  }),
  on(validateLeaveByServiceSuccess, (state, payload: LeavePayload) => {
    state.entities.set(payload.leave.id, payload.leave);
    return {entities: state.entities};
  }),
  on(validateLeaveByManagementSuccess, (state, payload: LeavePayload) => {
    state.entities.set(payload.leave.id, payload.leave);
    return {entities: state.entities};
  }),
  on(updateLeaveSuccess, (state, payload: LeavePayload) => {
    state.entities.set(payload.leave.id, payload.leave);
    return {entities: state.entities};
  }),
);

export function leaveReducer(state, action): StoreData<Leave> {
  return LEAVE_REDUCER(state, action);
}


