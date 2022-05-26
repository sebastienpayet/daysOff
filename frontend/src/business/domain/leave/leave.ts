import {AbstractDomain} from '@domain/abstract-domain';
import {LeaveType} from '@domain/leave/leave-type.enum';
import {LeaveWorkflow} from '@domain/leave/leave-workflow';
import {LeaveStatus} from '@domain/leave/leave-status.enum';

export class Leave extends AbstractDomain<Leave> {
  depositDate: Date;
  startDate: Date;
  fullDayAtStart: boolean;
  endDate: Date;
  fullDayAtEnd: boolean;
  leaveWorkflows: LeaveWorkflow[];
  leaveType: LeaveType;
  userId: string;
  comment: string;
  duration: number;

  public static getCurrentStatus(leaveWorkflows: LeaveWorkflow[]): LeaveStatus {
    return [...leaveWorkflows]?.sort((a: LeaveWorkflow, b: LeaveWorkflow) => {
      return new Date(b.date).getTime() - new Date(a.date).getTime();
    })[0]?.leaveStatus ?? LeaveStatus.DRAFT;
  }
}
