import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {User} from '@domain/user/user';
import {LeaveCreateCommand} from '@services/leave/command/leave-create-command';
import {LeaveType} from '@domain/leave/leave-type.enum';
import {createLeave} from '@store/leave/leave.actions';
import {DatesRange, fixTimeOnDate} from '@modules/technical/helper/date-helper';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {MatDialog} from '@angular/material/dialog';
import {MatCalendarCellClassFunction} from '@angular/material/datepicker';
import {SettingsStoreRepository} from '@store/settings/settings-store-repository.service';
import {Settings} from '@domain/settings/settings';
import {tap} from 'rxjs/operators';

@Component({
  selector: 'app-leave-form-dialog',
  templateUrl: './leave-create-form-dialog.component.html',
  styleUrls: ['./leave-create-form-dialog.component.css']
})
export class LeaveCreateFormDialogComponent implements OnInit {

  constructor(private fb: FormBuilder,
              public userStoreRepository: UserStoreRepository,
              public settingsStoreRepository: SettingsStoreRepository,
              public store: Store<{}>,
              private dialog: MatDialog) {
  }

  public static readonly DIALOG_ID = 'leaveCreate';

  range: DatesRange;
  minDate = new Date(new Date().getFullYear() - 2, 0, 1);
  maxDate = new Date(new Date().getFullYear() + 1, 11, 31);

  leaveTypes = Object.keys(LeaveType).map(key => LeaveType[key]).filter(value => typeof value === 'string') as string[];

  leaveForm = this.fb.group({
    userId: [null, Validators.required],
    leaveType: [null, Validators.required],
    comment: null,
    fullDayAtStart: true,
    fullDayAtEnd: true,
    range: [null, Validators.required]
  });

  users$: Observable<User[]>;
  settings$: Observable<Settings[]>;
  settings: Settings;

  onSubmit(): void {
    let startDate = new Date(this.range.startDate);
    startDate = fixTimeOnDate(startDate, 0, 0, 0);

    let endDate = new Date(this.range.endDate);
    endDate = fixTimeOnDate(endDate, 23, 59, 59);

    const command = new LeaveCreateCommand(
      startDate,
      this.leaveForm.value.fullDayAtStart,
      endDate,
      this.leaveForm.value.fullDayAtEnd,
      this.leaveForm.value.leaveType,
      this.leaveForm.value.userId,
      this.leaveForm.value.comment,
    );
    this.store.dispatch(createLeave(command));
    this.dialog.getDialogById(LeaveCreateFormDialogComponent.DIALOG_ID).close();
  }

  ngOnInit(): void {
    this.users$ = this.userStoreRepository.findAll();
    this.settings$ = this.settingsStoreRepository.findAll().pipe(
      tap((settings) => {
        this.settings = settings[0];
      })
    );
  }

  updateUser(users: User[]): void {
    this.leaveForm.patchValue({userId: users[0]?.id ?? ''});
  }

  dateClass: MatCalendarCellClassFunction<Date> = (cellDate, view) => {
    // Only highlight public holidays
    if (view === 'month') {
      const testCell = cellDate.toString().substr(0, 15);
      if (this.settings.publicHolidays.find(
        publicHoliday => new Date(publicHoliday).toString().substr(0, 15) === testCell)
      ) {
        return 'public-holiday';
      }
    }
    return '';
  }

  updateDateRangeFilter($event: DatesRange): void {
    this.leaveForm.patchValue({range: $event});
    this.range = $event;
  }
}
