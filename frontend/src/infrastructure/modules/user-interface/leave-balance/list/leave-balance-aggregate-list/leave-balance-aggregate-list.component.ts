import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {ActionsSubject} from '@ngrx/store';
import {BehaviorSubject, combineLatest, Observable} from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import {map, tap} from 'rxjs/operators';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {UserPipe} from '@modules/technical/pipes/user.pipe';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {User} from '@domain/user/user';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Role} from '@domain/user/role.enum';
import {LeaveBalanceStoreRepository} from '@store/leaveBalance/leave-balance-store-repository.service';
import {LeaveBalance} from '@domain/leave-balance/leave-balance';
import {LeaveBalanceCreateFormDialogComponent} from '@modules/user-interface/leave-balance/dialog/leave-balance-create-form-dialog/leave-balance-create-form-dialog.component';
import {LeaveType} from '@domain/leave/leave-type.enum';
import {BalanceType} from '@domain/leave-balance/balance-type.enum';
import {LeaveBalanceUserDetailComponent} from '@modules/user-interface/leave-balance/list/leave-balance-user-detail/leave-balance-user-detail.component';

@Component({
  selector: 'app-users',
  templateUrl: './leave-balance-aggregate-list.component.html',
  styleUrls: ['./leave-balance-aggregate-list.component.css']
})
export class LeaveBalanceAggregateListComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<BalanceData>;
  public balanceDatum$: Observable<BalanceData[]>;
  public modifyLeaveAction$: Observable<ActionsSubject>;
  public statuses = LeaveStatus;
  public userRoles = Role;
  public authenticatedUser$: Observable<User>;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['userStringFullName', 'annual', 'special', 'working_time_reduction',
    'recovery', 'time_saving_account', 'total', 'actions'];

  filterValue: string;
  startingYear = (new Date().getFullYear()) - 2;
  currentYear = new Date().getFullYear();
  selectedYear$: BehaviorSubject<number> = new BehaviorSubject<number>(this.currentYear);

  constructor(
    public dataSource: MatTableDataSource<BalanceData>,
    public leaveBalanceRepository: LeaveBalanceStoreRepository,
    public userStoreRepository: UserStoreRepository,
    public dialog: MatDialog,
    public userPipe: UserPipe,
    public authenticationService: AuthenticationService
  ) {
    this.dataSource = new MatTableDataSource<BalanceData>();
  }

  ngOnInit(): void {
    this.authenticatedUser$ = this.authenticationService.getAuthenticatedUser();

    this.balanceDatum$ = combineLatest([
      this.userStoreRepository.findAll(),
      this.leaveBalanceRepository.findAll(),
      this.selectedYear$,
      this.authenticationService.getAuthenticatedUser()
    ]).pipe(
      map(([users, leaveBalances, selectedYear, currentUser]) => {
          if (currentUser?.role !== Role.ADMINISTRATOR) {
            users = users.filter(user => user.id === currentUser?.id);
          }

          return users.map(user => this.sumData(
            user, leaveBalances.filter(leaveBalance => leaveBalance.year === selectedYear && leaveBalance.userId === user.id)));
        }
      ),
      tap((sumDatum) => {
        this.dataSource.data = sumDatum;
      })
    );
  }

  private sumData(user: User, leaveBalances: LeaveBalance[]): BalanceData {
    const balanceData: BalanceData = {
      userStringFullName: this.userPipe.transform(user),
      userId: user.id,
      annual: 0,
      special: 0,
      workingTimeReduction: 0,
      recovery: 0,
      timeSavingAccount: 0,
      total: 0
    };

    if (leaveBalances.length > 0) {
      leaveBalances.forEach(
        leaveBalance => {
          let amount = leaveBalance.amount ?? 0;
          if (leaveBalance.balanceType === BalanceType.DEBIT) {
            amount = 0 - amount;
          }
          switch (leaveBalance.leaveType) {
            case LeaveType.ANNUAL:
              balanceData.annual += amount;
              break;
            case LeaveType.SPECIAL:
              balanceData.special += amount;
              break;
            case LeaveType.RECOVERY:
              balanceData.recovery += amount;
              break;
            case LeaveType.TIME_SAVING_ACCOUNT:
              balanceData.timeSavingAccount += amount;
              break;
            case LeaveType.WORKING_TIME_REDUCTION:
              balanceData.workingTimeReduction += amount;
              break;
          }
          balanceData.total += amount;
        }
      );
    }
    return balanceData;
  }

  openCreateDialog(): void {
    this.dialog.open(LeaveBalanceCreateFormDialogComponent, {
      id: LeaveBalanceCreateFormDialogComponent.DIALOG_ID,
      data: {},
      width: '600px',
      panelClass: 'custom-dialog-container'
    });
  }

  showDetail(userId: string, year: number): void {
    this.dialog.open(LeaveBalanceUserDetailComponent, {
      id: LeaveBalanceUserDetailComponent.DIALOG_ID,
      data: {userId, year},
      panelClass: 'custom-dialog-container'
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

  updateYear(year: number): void {
    this.selectedYear$.next(year);
  }

  isShowAddBalanceButton(user: User): boolean {
    return user.role === Role.ADMINISTRATOR;
  }
}

export interface BalanceData {
  userStringFullName: string;
  userId: string;
  annual: number;
  special: number;
  workingTimeReduction: number;
  recovery: number;
  timeSavingAccount: number;
  total: number;
}
