import {Component, Inject} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {validateLeaveByManagement} from '@store/leave/leave.actions';
import {Store} from '@ngrx/store';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {LeaveValidationByManagementCommand} from '@services/leave/command/leave-validation-by-management-command';

@Component({
  selector: 'app-leave-status-form-dialog',
  templateUrl: './leave-validate-by-management-form-dialog.component.html',
  styleUrls: ['./leave-validate-by-management-form-dialog.component.css']
})
export class LeaveValidateByManagementFormDialogComponent {

  public static readonly DIALOG_ID = 'leaveValidateByManagement';

  leaveForm = this.fb.group({
    annual: [0, Validators.required],
    special: [0, Validators.required],
    workingTimeReduction: [0, Validators.required],
    recovery: [0, Validators.required],
    timeSavingAccount: [0, Validators.required],
    comment: null
  });
  remainingToDispatch: number;

  constructor(private fb: FormBuilder,
              private store: Store<{}>,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: { leaveId: string, duration: number }
  ) {
    this.remainingToDispatch = data.duration;
  }

  onSubmit(): void {
    const command = new LeaveValidationByManagementCommand(
      this.data.leaveId,
      this.leaveForm.value.comment,
      this.leaveForm.value.annual,
      this.leaveForm.value.special,
      this.leaveForm.value.workingTimeReduction,
      this.leaveForm.value.recovery,
      this.leaveForm.value.timeSavingAccount,
    );
    this.store.dispatch(validateLeaveByManagement(command));
    this.dialog.getDialogById(LeaveValidateByManagementFormDialogComponent.DIALOG_ID).close();
  }

  computeRemaining(fieldName: any): number {
    let currentValue = this.leaveForm.get(fieldName).value as number;

    if (currentValue < 0) {
      currentValue = 0;
      this.leaveForm.controls[fieldName].setValue(currentValue);
    }

    const checkDispatched = this.data.duration
      - this.leaveForm.value.annual
      - this.leaveForm.value.special
      - this.leaveForm.value.workingTimeReduction
      - this.leaveForm.value.recovery
      - this.leaveForm.value.timeSavingAccount;

    if (checkDispatched < 0) {
      this.remainingToDispatch = 0;
      const correctedValue = currentValue - (0 - checkDispatched);
      this.leaveForm.controls[fieldName].setValue(correctedValue);
    } else {
      this.remainingToDispatch = checkDispatched;
    }

    return this.remainingToDispatch;
  }
}
