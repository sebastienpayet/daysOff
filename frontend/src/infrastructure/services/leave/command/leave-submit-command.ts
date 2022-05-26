export class LeaveSubmitCommand {
  leaveId: string;
  comment: string;

  constructor(leaveId: string, comment: string) {
    this.leaveId = leaveId;
    this.comment = comment;
  }
}
