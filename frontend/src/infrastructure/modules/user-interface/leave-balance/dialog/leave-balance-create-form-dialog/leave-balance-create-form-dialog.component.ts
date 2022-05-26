import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {LeaveType} from '@domain/leave/leave-type.enum';
import {BalanceType} from '@domain/leave-balance/balance-type.enum';
import {Observable} from 'rxjs';
import {User} from '@domain/user/user';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {Store} from '@ngrx/store';
import {MatDialog} from '@angular/material/dialog';
import {createLeaveBalance} from '@store/leaveBalance/leave-balance.actions';
import {LeaveBalanceCreateCommand} from '@services/leave-balance/command/leave-balance-create-command';
import {SettingsStoreRepository} from '@store/settings/settings-store-repository.service';
import {Settings} from '@domain/settings/settings';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-leave-balance-create-form-dialog',
  templateUrl: './leave-balance-create-form-dialog.component.html',
  styleUrls: ['./leave-balance-create-form-dialog.component.css']
})
export class LeaveBalanceCreateFormDialogComponent implements OnInit {

  constructor(private fb: FormBuilder,
              public userStoreRepository: UserStoreRepository,
              public settingsStoreRepository: SettingsStoreRepository,
              public store: Store<{}>,
              private dialog: MatDialog) {
  }

  public static readonly DIALOG_ID = 'leaveBalanceCreate';

  public leaveType = LeaveType;
  leaveTypes = Object.keys(LeaveType).map(key => LeaveType[key]).filter(value => typeof value === 'string') as string[];
  balanceTypes = Object.keys(BalanceType).map(key => BalanceType[key]).filter(value => typeof value === 'string') as string[];

  users$: Observable<User[]>;
  settings$: Observable<Settings>;
  settings: Settings;
  startingYear = (new Date().getFullYear()) - 2;
  currentYear = new Date().getFullYear();

  leaveBalanceForm = this.fb.group({
    year: [this.currentYear, Validators.required],
    userId: [null, Validators.required],
    leaveType: [LeaveType[LeaveType.ANNUAL], Validators.required],
    balanceType: [BalanceType[BalanceType.CREDIT], Validators.required],
    amount: [null, [Validators.required, Validators.pattern('\\d*\\.?\\d{1,2}')]],
    comment: null
  });

  convertToDaysIfNecessary(amount: number, leaveType: string): number {
    if (leaveType === LeaveType.RECOVERY && this.settings.inputRecoveryInHours) {
      return Math.round(amount * 1e4 / this.settings.numberOfHoursInAWorkingDay) / 1e4;
    } else {
      return amount;
    }
  }

  onSubmit(): void {
    const command = new LeaveBalanceCreateCommand(
      null,
      this.convertToDaysIfNecessary(this.leaveBalanceForm.value.amount,
        this.leaveBalanceForm.value.leaveType),
      this.leaveBalanceForm.value.year,
      this.leaveBalanceForm.value.balanceType,
      this.leaveBalanceForm.value.leaveType,
      this.leaveBalanceForm.value.userId,
      this.leaveBalanceForm.value.comment,
    );
    this.store.dispatch(createLeaveBalance(command));
    this.dialog.getDialogById(LeaveBalanceCreateFormDialogComponent.DIALOG_ID).close();
  }

  ngOnInit(): void {
    this.users$ = this.userStoreRepository.findAll();
    this.settings$ = this.settingsStoreRepository.getSettings().pipe(
      tap((result => this.settings = result))
    );
  }

  updateUser(users: User[]): void {
    this.leaveBalanceForm.patchValue({userId: users[0]?.id ?? ''});
  }

  updateYear(year: number): void {
    this.leaveBalanceForm.controls.year.setValue(year);
  }
}
