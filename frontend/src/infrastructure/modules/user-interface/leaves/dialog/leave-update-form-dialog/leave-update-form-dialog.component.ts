import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {DatesRange} from '@modules/technical/helper/date-helper';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {Leave} from '@domain/leave/leave';
import {LeaveUpdateCommand} from '@services/leave/command/leave-update-command';
import {updateLeave} from '@store/leave/leave.actions';
import {LeaveType} from '@domain/leave/leave-type.enum';
import {Observable} from 'rxjs';
import {Settings} from '@domain/settings/settings';
import {tap} from 'rxjs/operators';
import {SettingsStoreRepository} from '@store/settings/settings-store-repository.service';
import {MatCalendarCellClassFunction} from '@angular/material/datepicker';

@Component({
  selector: 'app-leave-update-form-dialog',
  templateUrl: './leave-update-form-dialog.component.html',
  styleUrls: ['./leave-update-form-dialog.component.css']
})
export class LeaveUpdateFormDialogComponent implements OnInit {

  constructor(private fb: FormBuilder,
              public userStoreRepository: UserStoreRepository,
              public settingsStoreRepository: SettingsStoreRepository,
              @Inject(MAT_DIALOG_DATA) public data: { updatedLeave: Leave },
              public store: Store<{}>,
              private dialog: MatDialog) {
  }

  public static readonly DIALOG_ID = 'leaveUpdate';
  settings$: Observable<Settings[]>;
  settings: Settings;
  minDate = new Date(new Date().getFullYear() - 2, 0, 1);
  maxDate = new Date(new Date().getFullYear() + 1, 11, 31);

  leaveTypes = Object.keys(LeaveType).map(key => LeaveType[key]).filter(value => typeof value === 'string') as string[];
  users$ = this.userStoreRepository.findAll();
  range: DatesRange;

  leaveForm: FormGroup;

  onSubmit(): void {
    const command = new LeaveUpdateCommand(
      this.data.updatedLeave.id,
      this.range.startDate,
      this.leaveForm.value.fullDayAtStart,
      this.range.endDate,
      this.leaveForm.value.fullDayAtEnd,
      this.leaveForm.value.leaveType,
      this.leaveForm.value.userId,
      this.leaveForm.value.comment,
    );
    this.store.dispatch(updateLeave(command));
    this.dialog.getDialogById(LeaveUpdateFormDialogComponent.DIALOG_ID).close();
  }

  ngOnInit(): void {
    this.range = new DatesRange(
      this.data.updatedLeave.startDate,
      this.data.updatedLeave.endDate
    );

    this.leaveForm = this.fb.group({
      leaveType: [this.data.updatedLeave.leaveType, Validators.required],
      comment: this.data.updatedLeave.comment,
      fullDayAtStart: this.data.updatedLeave.fullDayAtStart,
      fullDayAtEnd: this.data.updatedLeave.fullDayAtEnd,
      range: [this.range, Validators.required]
    });

    this.users$ = this.userStoreRepository.findAll();
    this.settings$ = this.settingsStoreRepository.findAll().pipe(
      tap((settings) => {
        this.settings = settings[0];
      })
    );
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

  updateDateRange($event: DatesRange): void {
    this.range = new DatesRange(
      $event.startDate,
      $event.endDate
    );
  }
}
