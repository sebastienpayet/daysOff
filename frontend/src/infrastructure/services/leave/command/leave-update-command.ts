import {LeaveType} from '@domain/leave/leave-type.enum';
import {convertToIso8601UTCString} from '@modules/technical/helper/date-helper';

export class LeaveUpdateCommand {
  leaveId: string;
  startDate: string;
  fullDayAtStart: boolean;
  endDate: string;
  fullDayAtEnd: boolean;
  leaveType: LeaveType;
  comment: string;

  constructor(leaveId: string, startDate: Date, fullDayAtStart: boolean, endDate: Date,
              fullDayAtEnd: boolean, type: LeaveType, userId: string, comment: string) {
    this.leaveId = leaveId;
    this.startDate = convertToIso8601UTCString(startDate);
    this.fullDayAtStart = fullDayAtStart;
    this.endDate = convertToIso8601UTCString(endDate);
    this.fullDayAtEnd = fullDayAtEnd;
    this.leaveType = type;
    this.comment = comment;
  }
}
