import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {map} from 'rxjs/operators';
import {StoreData} from '@store/store-data';
import {LeaveBalance} from '@domain/leave-balance/leave-balance';
import {LeaveBalanceRepository} from '@business/port/leave-balance-repository.interface';

@Injectable({providedIn: 'root'})
export class LeaveBalanceStoreRepository implements LeaveBalanceRepository {

  constructor(private leaveBalanceStore: Store<{ leaveBalances: StoreData<LeaveBalance> }>) {
  }

  delete(id: string): Observable<boolean> {
    return this.leaveBalanceStore.select(state => state.leaveBalances.entities.delete(id));
  }

  findAll(): Observable<LeaveBalance[]> {
    return this.leaveBalanceStore.select(state => state.leaveBalances)
      .pipe(
        map(leaveBalances => leaveBalances ? [...leaveBalances.entities.values()] : []),
      );
  }

  findById(id: string): Observable<LeaveBalance> {
    return this.leaveBalanceStore.select(state => state.leaveBalances.entities.get(id));
  }

  findAllByUserIdAndYear(userId: string, year: number): Observable<LeaveBalance[]> {
    return this.leaveBalanceStore.select(state => state.leaveBalances)
      .pipe(
        map(leaveBalances => {
          return Array.from(leaveBalances.entities.values())
            .filter(leaveBalance => leaveBalance.userId === userId && leaveBalance.year === year);
        }),
      );
  }
}
