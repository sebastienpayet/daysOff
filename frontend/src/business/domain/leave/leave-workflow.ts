import {LeaveStatus} from '@domain/leave/leave-status.enum';

export class LeaveWorkflow {
  date: Date;
  userId: string;
  leaveStatus: LeaveStatus;
  comment: string;
}
