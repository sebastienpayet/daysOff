import {Component} from '@angular/core';
import {User} from '@domain/user/user';
import {Team} from '@domain/team/team';
import {filterLeaves} from '@store/leave/leave.actions';
import {Store} from '@ngrx/store';
import {DatesRange} from '@modules/technical/helper/date-helper';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {StatusAutocompleteComponent} from '@modules/user-interface/filter/status/status-autocomplete/status-autocomplete.component';

@Component({
  selector: 'app-side-filters',
  templateUrl: './side-filters.component.html',
  styleUrls: ['./side-filters.component.css']
})
export class SideFiltersComponent {

  leaveFilter: LeaveFilter;

  constructor(private store: Store<{}>) {
    this.leaveFilter = new LeaveFilter();
  }

  applyFilters(): void {
    this.store.dispatch(filterLeaves(this.leaveFilter));
  }

  updateUsersFilter($event: User[]): void {
    this.leaveFilter.users = $event;
    this.applyFilters();
  }

  updateServicesFilter($event: Team[]): void {
    this.leaveFilter.services = $event;
    this.applyFilters();
  }

  updateDateRangeFilter($event: DatesRange): void {
    this.leaveFilter.dateRange = $event;
    this.applyFilters();
  }

  updateStatusFilter($event: LeaveStatus[]): void {
    this.leaveFilter.status = $event;
    this.applyFilters();
  }
}

export class LeaveFilter {

  constructor() {
    const startDate = new Date();
    const endDate = new Date();
    endDate.setMonth(endDate.getMonth() + 3);

    this.dateRange = new DatesRange(startDate, endDate);
  }

  users: User[] = [];
  dateRange: DatesRange;
  services: Team[] = [];
  status: LeaveStatus[] = StatusAutocompleteComponent.defaultFilterValue;
}
