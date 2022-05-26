import {Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Team} from '@domain/team/team';
import {Role} from '@domain/user/role.enum';
import {UserCreateCommand} from '@services/user/command/user-create-command';
import {Store} from '@ngrx/store';
import {createUser, updateUser} from '@store/user/user.actions';
import {User} from '@domain/user/user';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {UserUpdateCommand} from '@services/user/command/user-update-command';
import {TeamStoreRepository} from '@store/team/team-store-repository.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-user-form-dialog',
  templateUrl: './user-form-dialog.component.html',
  styleUrls: ['./user-form-dialog.component.css']
})
export class UserFormDialogComponent implements OnInit {

  public teams: Observable<Team[]>;
  roles = Object.keys(Role).map(key => Role[key]).filter(value => typeof value === 'string') as string[];
  hide: boolean;
  public redefinePassword = false;
  public userForm: FormGroup;
  passwordControl: FormControl;
  passwordConfirmControl: FormControl;

  constructor(private fb: FormBuilder,
              private store: Store<{}>,
              @Inject(MAT_DIALOG_DATA) public data: { updatedUser: User },
              private teamStoreRepository: TeamStoreRepository
  ) {
  }

  onSubmit(): void {
    let command;

    if (this.data.updatedUser) {
      command = new UserUpdateCommand(
        this.userForm.value.id,
        this.userForm.value.lastName,
        this.userForm.value.firstName,
        this.userForm.value.hasMinorChild,
        this.userForm.value.teams,
        this.userForm.value.email,
        this.userForm.value.role,
        this.userForm.value.password
      );
      this.store.dispatch(updateUser(command));
    } else {
      command = new UserCreateCommand(
        this.userForm.value.lastName,
        this.userForm.value.firstName,
        this.userForm.value.hasMinorChild,
        this.userForm.value.teams,
        this.userForm.value.email,
        this.userForm.value.role,
        this.userForm.value.password
      );
      this.store.dispatch(createUser(command));
    }

  }

  ngOnInit(): void {
    this.teams = this.teamStoreRepository.findAll();
    const updatedUser = this.data?.updatedUser;
    this.passwordControl = new FormControl(
      null, Validators.compose([
        Validators.required,
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*([^a-zA-Z\\d\\s])).{8,}$')
        // this is for the letters (both uppercase and lowercase) and numbers validation
      ])
    );
    this.passwordConfirmControl = new FormControl(
      '', [Validators.required, passwordConfirmValidator]
    );

    this.userForm = this.fb.group({
      firstName: [updatedUser?.firstname, Validators.required],
      lastName: [updatedUser?.lastname, Validators.required],
      role: [updatedUser?.role ?? 'USER', Validators.required],
      teams: [updatedUser?.teamIds, Validators.required],
      hasMinorChild: updatedUser?.hasMinorChild ?? false,
      email: [updatedUser?.email, Validators.email],
    });

    if (!updatedUser || this.redefinePassword) {
      this.userForm.addControl('password', this.passwordControl);
      this.userForm.addControl('confirmPassword', this.passwordConfirmControl);
    }

    if (updatedUser) {
      this.userForm.addControl('id', new FormControl(updatedUser.id));
    }
  }

  switchRedefinePassword(): void {
    this.redefinePassword = !this.redefinePassword;

    if (this.redefinePassword) {
      this.userForm.addControl('password', this.passwordControl);
      this.userForm.addControl('confirmPassword', this.passwordConfirmControl);
    } else {
      this.userForm.removeControl('password');
      this.userForm.removeControl('confirmPassword');
    }
  }
}

export function passwordConfirmValidator(formControl: AbstractControl): any {
  if (!formControl.parent) {
    return null;
  }

  if (formControl.parent.get('password')?.value) {
    const original = formControl.parent.get('password').value;
    const confirmation = formControl.parent.get('confirmPassword').value;
    const isEqual = original != null && confirmation != null && confirmation === original;
    return !isEqual ? {isEqual: {value: confirmation}} : null;
  }
  return null;
}
