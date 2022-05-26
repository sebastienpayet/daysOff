import {LeaveType} from '@domain/leave/leave-type.enum';
import {BalanceType} from '@domain/leave-balance/balance-type.enum';

export class LeaveBalanceCreateCommand {
  leaveType: LeaveType;
  userId: string;
  comment: string;
  year: number;
  amount: number;
  leaveId: string;
  balanceType: BalanceType;

  constructor(leaveId: string, amount: number, year: number, balanceType: BalanceType,
              leaveType: LeaveType, userId: string, comment: string) {
    this.leaveType = leaveType;
    this.userId = userId;
    this.comment = comment;
    this.year = year;
    this.amount = amount;
    this.leaveId = leaveId;
    this.balanceType = balanceType;
  }
}
