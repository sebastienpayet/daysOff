import {createAction, props} from '@ngrx/store';
import {Leave} from '@domain/leave/leave';
import {LeaveCreateCommand} from '@services/leave/command/leave-create-command';
import {LeaveSubmitCommand} from '@services/leave/command/leave-submit-command';
import {LeaveFilter} from '@modules/user-interface/filter/side-filters/side-filters.component';
import {LeaveDraftCommand} from '@services/leave/command/leave-draft-command';
import {LeaveRefuseCommand} from '@services/leave/command/leave-refuse-command';
import {LeaveDeleteCommand} from '@services/leave/command/leave-delete-command';
import {LeaveValidationByServiceCommand} from '@services/leave/command/leave-validation-by-service-command';
import {LeaveUpdateCommand} from '@services/leave/command/leave-update-command';
import {LeaveValidationByManagementCommand} from '@services/leave/command/leave-validation-by-management-command';

export const LIST_ALL_LEAVES = '[Leave] List all leaves asking';
export const CREATE_LEAVE = '[Leave] Create a leave';
export const CREATE_LEAVE_SUCCESS = '[Leave] Create a leave success';
export const DELETE_LEAVE = '[Leave] Delete a leave';
export const DELETE_LEAVE_SUCCESS = '[Leave] Delete a leave success';
export const SUBMIT_LEAVE = '[Leave] Submit a leave';
export const SUBMIT_LEAVE_SUCCESS = '[Leave] Submit a leave success';
export const SET_LEAVE_TO_DRAFT = '[Leave] Set leave to draft';
export const SET_LEAVE_TO_DRAFT_SUCCESS = '[Leave] Set leave to draft success';
export const REFUSE_LEAVE = '[Leave] Refuse leave';
export const REFUSE_LEAVE_SUCCESS = '[Leave] Refuse leave success';
export const VALIDATE_LEAVE_BY_SERVICE = '[Leave] Validate leave by service';
export const VALIDATE_LEAVE_BY_SERVICE_SUCCESS = '[Leave] Validate leave by service success';
export const VALIDATE_LEAVE_BY_MANAGEMENT = '[Leave] Validate leave by management';
export const VALIDATE_LEAVE_BY_MANAGEMENT_SUCCESS = '[Leave] Validate leave by management success';
export const LIST_ALL_LEAVES_SUCCESS = '[Leave] List all leaves success';
export const UPDATE_LEAVE = '[Leave] Update a leave';
export const UPDATE_LEAVE_SUCCESS = '[Leave] Update a leave success';
export const FILTER_LEAVES = '[Leave] Filter leaves';

export const listAllLeaves = createAction(LIST_ALL_LEAVES);
export const listAllLeavesSuccess = createAction(LIST_ALL_LEAVES_SUCCESS, props<LeavesPayload>());
export const createLeave = createAction(CREATE_LEAVE, props<LeaveCreateCommand>());
export const createLeaveSuccess = createAction(CREATE_LEAVE_SUCCESS, props<LeavePayload>());
export const deleteLeave = createAction(DELETE_LEAVE, props<LeaveDeleteCommand>());
export const deleteLeaveSuccess = createAction(DELETE_LEAVE_SUCCESS, props<LeavePayload>());
export const submitLeave = createAction(SUBMIT_LEAVE, props<LeaveSubmitCommand>());
export const submitLeaveSuccess = createAction(SUBMIT_LEAVE_SUCCESS, props<LeavePayload>());
export const validateLeaveByService = createAction(VALIDATE_LEAVE_BY_SERVICE, props<LeaveValidationByServiceCommand>());
export const validateLeaveByServiceSuccess = createAction(VALIDATE_LEAVE_BY_SERVICE_SUCCESS, props<LeavePayload>());
export const validateLeaveByManagement = createAction(VALIDATE_LEAVE_BY_MANAGEMENT, props<LeaveValidationByManagementCommand>());
export const validateLeaveByManagementSuccess = createAction(VALIDATE_LEAVE_BY_MANAGEMENT_SUCCESS, props<LeavePayload>());
export const refuseLeave = createAction(REFUSE_LEAVE, props<LeaveRefuseCommand>());
export const refuseLeaveSuccess = createAction(REFUSE_LEAVE_SUCCESS, props<LeavePayload>());
export const setLeaveToDraft = createAction(SET_LEAVE_TO_DRAFT, props<LeaveDraftCommand>());
export const setLeaveToDraftSuccess = createAction(SET_LEAVE_TO_DRAFT_SUCCESS, props<LeavePayload>());
export const filterLeaves = createAction(FILTER_LEAVES, props<LeaveFilter>());
export const updateLeave = createAction(UPDATE_LEAVE, props<LeaveUpdateCommand>());
export const updateLeaveSuccess = createAction(UPDATE_LEAVE_SUCCESS, props<LeavePayload>());

export interface LeavesPayload {
  leaves: Leave[];
}

export interface LeavePayload {
  leave: Leave;
}
