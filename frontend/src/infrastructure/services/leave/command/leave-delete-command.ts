export class LeaveDeleteCommand {
  leaveId: string;
  comment: string;

  constructor(leaveId: string, comment: string) {
    this.leaveId = leaveId;
    this.comment = comment;
  }
}
