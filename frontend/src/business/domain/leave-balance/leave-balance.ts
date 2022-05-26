import {AbstractDomain} from '@domain/abstract-domain';
import {LeaveType} from '@domain/leave/leave-type.enum';
import {BalanceType} from '@domain/leave-balance/balance-type.enum';

export class LeaveBalance extends AbstractDomain<LeaveBalance> {
  leaveType: LeaveType;
  userId: string;
  comment: string;
  year: number;
  amount: number;
  leaveId: string; // have to be null for CREDIT, must not be null to DEBIT
  balanceType: BalanceType;
}
