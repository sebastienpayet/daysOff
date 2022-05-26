export class LeaveValidationByManagementCommand {
  leaveId: string;
  comment: string;
  annual: number;
  special: number;
  workingTimeReduction: number;
  recovery: number;
  timeSavingAccount: number;

  constructor(
    leaveId: string,
    comment: string,
    annual: number,
    special: number,
    workingTimeReduction: number,
    recovery: number,
    timeSavingAccount: number
  ) {
    this.leaveId = leaveId;
    this.comment = comment;
    this.annual = annual;
    this.special = special;
    this.workingTimeReduction = workingTimeReduction;
    this.recovery = recovery;
    this.timeSavingAccount = timeSavingAccount;
  }
}
