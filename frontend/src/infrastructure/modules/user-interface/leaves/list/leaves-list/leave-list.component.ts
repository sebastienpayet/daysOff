import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {ActionsSubject} from '@ngrx/store';
import {combineLatest, Observable} from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import {Leave} from '@domain/leave/leave';
import {map, tap} from 'rxjs/operators';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {UserPipe} from '@modules/technical/pipes/user.pipe';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {LeaveStoreRepository} from '@store/leave/leave-store-repository.service';
import {User} from '@domain/user/user';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Role} from '@domain/user/role.enum';
import {LeaveCreateFormDialogComponent} from '@modules/user-interface/leaves/dialog/leave-create-form-dialog/leave-create-form-dialog.component';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-users',
  templateUrl: './leave-list.component.html',
  styleUrls: ['./leave-list.component.css']
})
export class LeaveListComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<Leave>;
  public leaves$: Observable<Leave[]>;
  public modifyLeaveAction$: Observable<ActionsSubject>;
  public statuses = LeaveStatus;
  public userRoles = Role;
  public authenticatedUser$: Observable<User>;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['user', 'depositDate', 'startDate', 'endDate', 'leaveType', 'duration', 'workflow', 'comment', 'actions'];
  filterValue: string;

  constructor(
    public dataSource: MatTableDataSource<Leave>,
    public leaveRepository: LeaveStoreRepository,
    public userStoreRepository: UserStoreRepository,
    public dialog: MatDialog,
    public userPipe: UserPipe,
    public authenticationService: AuthenticationService,
    public translateService: TranslateService
  ) {
    this.dataSource = new MatTableDataSource<Leave>();
  }

  ngOnInit(): void {
    this.authenticatedUser$ = this.authenticationService.getAuthenticatedUser();

    this.leaves$ = combineLatest([
      this.userStoreRepository.findAll(),
      this.leaveRepository.findAll()
    ]).pipe(
      map(([users, leaves]) => leaves.map(leave => {
        // create a dynamic user property for datasource filter / sort binding
        const leaveUser = users.find(currentUser => currentUser.id === leave.userId);
        return Object.assign({}, leave, {
          userData: leaveUser,
          user: this.userPipe.transform(leaveUser),
          currentStatus: Leave.getCurrentStatus(leave.leaveWorkflows)
        });
      })),
      tap((leaves) => {
        this.dataSource.data = leaves;
      })
    );
  }

  openCreateDialog(): void {
    this.dialog.open(LeaveCreateFormDialogComponent, {
      id: LeaveCreateFormDialogComponent.DIALOG_ID,
      data: {},
      width: '550px'
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  applyFilter(event: Event): void {
    this.filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = this.filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
