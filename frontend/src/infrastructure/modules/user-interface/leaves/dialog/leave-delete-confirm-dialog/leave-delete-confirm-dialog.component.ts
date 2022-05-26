import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {Store} from '@ngrx/store';
import {deleteLeave} from '@store/leave/leave.actions';
import {LeaveDeleteCommand} from '@services/leave/command/leave-delete-command';

@Component({
  selector: 'app-leave-delete-confirm-dialog',
  templateUrl: './leave-delete-confirm-dialog.component.html',
  styleUrls: ['./leave-delete-confirm-dialog.component.css']
})
export class LeaveDeleteConfirmDialogComponent {
  public static readonly DIALOG_ID = 'leaveDelete';

  constructor(
    private dialog: MatDialog,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: { leaveId: string }
  ) {
  }

  abort(): void {
    this.dialog.getDialogById(LeaveDeleteConfirmDialogComponent.DIALOG_ID).close();
  }

  confirm(): void {
    const command = new LeaveDeleteCommand(this.data.leaveId, '');
    this.store.dispatch(deleteLeave(command));
    this.dialog.getDialogById(LeaveDeleteConfirmDialogComponent.DIALOG_ID).close();
  }
}
