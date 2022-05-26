import {Component, Inject} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {refuseLeave, setLeaveToDraft, submitLeave, validateLeaveByService} from '@store/leave/leave.actions';
import {LeaveSubmitCommand} from '@services/leave/command/leave-submit-command';
import {Store} from '@ngrx/store';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {LeaveDraftCommand} from '@services/leave/command/leave-draft-command';
import {LeaveValidationByServiceCommand} from '@services/leave/command/leave-validation-by-service-command';

@Component({
  selector: 'app-leave-status-form-dialog',
  templateUrl: './leave-status-form-dialog.component.html',
  styleUrls: ['./leave-status-form-dialog.component.css']
})
export class LeaveStatusFormDialogComponent {

  public static readonly DIALOG_ID = 'leaveStatusUpdate';

  leaveForm = this.fb.group({
    comment: null
  });

  constructor(private fb: FormBuilder,
              private store: Store<{}>,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: { leaveId: string, targetStatus: LeaveStatus }
  ) {
  }

  onSubmit(): void {
    let command;
    switch (this.data.targetStatus) {
      case LeaveStatus.SUBMITTED:
        command = new LeaveSubmitCommand(
          this.data.leaveId,
          this.leaveForm.value.comment
        );
        this.store.dispatch(submitLeave(command));
        break;
      case LeaveStatus.DRAFT:
        command = new LeaveDraftCommand(
          this.data.leaveId,
          this.leaveForm.value.comment
        );
        this.store.dispatch(setLeaveToDraft(command));
        break;
      case LeaveStatus.REJECTED:
        command = new LeaveDraftCommand(
          this.data.leaveId,
          this.leaveForm.value.comment
        );
        this.store.dispatch(refuseLeave(command));
        break;
      case LeaveStatus.SERVICE_APPROVED:
        command = new LeaveValidationByServiceCommand(
          this.data.leaveId,
          this.leaveForm.value.comment
        );
        this.store.dispatch(validateLeaveByService(command));
        break;
      case LeaveStatus.MANAGEMENT_APPROVED:
        command = new LeaveValidationByServiceCommand(
          this.data.leaveId,
          this.leaveForm.value.comment
        );
        this.store.dispatch(validateLeaveByService(command));
        break;
    }
    this.dialog.getDialogById(LeaveStatusFormDialogComponent.DIALOG_ID).close();
  }

}
