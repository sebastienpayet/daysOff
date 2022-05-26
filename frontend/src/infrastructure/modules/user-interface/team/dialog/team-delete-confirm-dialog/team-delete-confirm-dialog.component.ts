import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {Store} from '@ngrx/store';
import {TeamDeleteCommand} from '@services/team/command/team-delete-command';
import {deleteTeam} from '@store/team/team.actions';

@Component({
  selector: 'app-team-delete-confirm-dialog',
  templateUrl: './team-delete-confirm-dialog.component.html',
  styleUrls: ['./team-delete-confirm-dialog.component.css']
})
export class TeamDeleteConfirmDialogComponent {
  public static readonly DIALOG_ID = 'teamDelete';

  constructor(
    private dialog: MatDialog,
    private store: Store,
    @Inject(MAT_DIALOG_DATA) public data: { teamId: string }
  ) {
  }

  abort(): void {
    this.dialog.getDialogById(TeamDeleteConfirmDialogComponent.DIALOG_ID).close();
  }

  confirm(): void {
    const command = new TeamDeleteCommand(this.data.teamId);
    this.store.dispatch(deleteTeam(command));
    this.dialog.getDialogById(TeamDeleteConfirmDialogComponent.DIALOG_ID).close();
  }

}
