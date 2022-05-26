import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {LeaveBalanceStoreRepository} from '@store/leaveBalance/leave-balance-store-repository.service';
import {combineLatest, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {LeaveStoreRepository} from '@store/leave/leave-store-repository.service';
import {BalanceType} from '@domain/leave-balance/balance-type.enum';
import {LeaveType} from '@domain/leave/leave-type.enum';

@Component({
  selector: 'app-leave-balance-user-detail',
  templateUrl: './leave-balance-user-detail.component.html',
  styleUrls: ['./leave-balance-user-detail.component.css']
})
export class LeaveBalanceUserDetailComponent implements OnInit, AfterViewInit {

  constructor(
    public dataSource: MatTableDataSource<BalanceDetail>,
    @Inject(MAT_DIALOG_DATA) public data: { userId: string, year: number },
    private readonly leaveBalanceStoreRepository: LeaveBalanceStoreRepository,
    private readonly userStoreRepository: UserStoreRepository,
    private readonly leaveStoreRepository: LeaveStoreRepository
  ) {
  }

  public static readonly DIALOG_ID = 'leaveBalanceUserDetail';
  displayedColumns = ['leaveBalanceCreatedDate', 'leaveBalanceBalanceType', 'leaveBalanceLeaveType',
    'leaveBalanceBalanceAmount', 'leaveStartDate', 'leaveBalanceComment'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<BalanceDetail>;

  detailDatum: Observable<BalanceDetail[]>;
  filterValue: string;
  balanceType = BalanceType;
  leaveType = LeaveType;
  totalAmount: number;

  ngOnInit(): void {
    this.detailDatum = combineLatest(
      [
        this.leaveBalanceStoreRepository.findAllByUserIdAndYear(this.data.userId, this.data.year),
        this.leaveStoreRepository.findAll()
      ]
    )
      .pipe(
        map(([leavesBalances, leaves]) => {
          const balanceDetails: BalanceDetail[] = [];
          leavesBalances.sort((a, b) => new Date(b.createdDate).getTime());
          let previousLeaveId = '';
          leavesBalances.forEach(leaveBalance => {
            const currentLeave = leaves.find(leave => leave.id === leaveBalance.leaveId);

            balanceDetails.push(
              {
                leaveBalanceCreatedDate: leaveBalance.createdDate,
                leaveBalanceBalanceType: leaveBalance.balanceType,
                leaveBalanceLeaveType: leaveBalance.leaveType,
                leaveBalanceBalanceAmount: leaveBalance.amount,
                leaveBalanceComment: leaveBalance.comment,
                leaveStartDate: currentLeave?.startDate,
                leaveEndDate: currentLeave?.endDate,
                leaveDuration: currentLeave?.duration,
                leaveLeaveType: currentLeave?.leaveType,
                lineBreak: (previousLeaveId !== currentLeave?.id || currentLeave === null)
              }
            );

            previousLeaveId = currentLeave?.id;
          });
          this.dataSource.data = balanceDetails;
          this.totalAmount = Math.round(this.getTotalAmount() * 1e2 ) / 1e2;
          return balanceDetails;
        }));
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    if (this.table) {
      this.table.dataSource = this.dataSource;
    }
  }

  applyFilter(event: Event): void {
    this.filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = this.filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  getTotalAmount(): number {
    return this.dataSource.data.map(detail => detail).reduce(
      (acc, balanceDetail) => {
        let value: number;
        if (balanceDetail.leaveBalanceBalanceType === BalanceType.DEBIT) {
          value = 0 - balanceDetail.leaveBalanceBalanceAmount;
        } else {
          value = balanceDetail.leaveBalanceBalanceAmount;
        }
        return acc + value;
      }, 0);
  }
}

export interface BalanceDetail {
  leaveBalanceCreatedDate: Date;
  leaveBalanceBalanceType: BalanceType;
  leaveBalanceLeaveType: LeaveType;
  leaveBalanceBalanceAmount: number;
  leaveBalanceComment: string;
  leaveStartDate: Date;
  leaveEndDate: Date;
  leaveDuration: number;
  leaveLeaveType: LeaveType;
  lineBreak: boolean;
}
