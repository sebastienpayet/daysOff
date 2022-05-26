import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {Team} from '@domain/team/team';
import {TeamUpdateCommand} from '@services/team/command/team-update-command';
import {createTeam, updateTeam} from '@store/team/team.actions';
import {TeamCreateCommand} from '@services/team/command/team-create-command';

@Component({
  selector: 'app-team-form-dialog',
  templateUrl: './team-form-dialog.component.html',
  styleUrls: ['./team-form-dialog.component.css']
})
export class TeamFormDialogComponent implements OnInit {
  public static readonly DIALOG_ID = 'teamUpdate';

  public teamForm: FormGroup;

  constructor(private fb: FormBuilder,
              private store: Store<{}>,
              @Inject(MAT_DIALOG_DATA) public data: { updatedTeam: Team },
              private dialog: MatDialog
  ) {
  }

  onSubmit(): void {
    let command;

    if (this.data.updatedTeam) {
      command = new TeamUpdateCommand(
        this.teamForm.value.id,
        this.teamForm.value.name
      );
      this.store.dispatch(updateTeam(command));
    } else {
      command = new TeamCreateCommand(
        this.teamForm.value.name
      );
      this.store.dispatch(createTeam(command));
    }
    this.dialog.getDialogById(TeamFormDialogComponent.DIALOG_ID).close();
  }

  ngOnInit(): void {
    const updatedTeam = this.data?.updatedTeam;

    this.teamForm = this.fb.group({
      name: [updatedTeam?.name, Validators.required],
    });

    if (updatedTeam) {
      this.teamForm.addControl('id', new FormControl(updatedTeam.id));
    }
  }

}
