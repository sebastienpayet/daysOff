import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {map} from 'rxjs/operators';
import {StoreData} from '@store/store-data';
import {Leave} from '@domain/leave/leave';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {LeaveRepository} from '@business/port/leave-repository.interface';

@Injectable({providedIn: 'root'})
export class LeaveStoreRepository implements LeaveRepository {

  constructor(private leaveStore: Store<{ leaves: StoreData<Leave> }>) {
  }

  delete(id: string): Observable<boolean> {
    return this.leaveStore.select(state => state.leaves.entities.delete(id));
  }

  findAll(): Observable<Leave[]> {
    return this.leaveStore.select(state => state.leaves)
      .pipe(
        map(leaves => leaves ? [...leaves.entities.values()] : []),
      );
  }

  findAllByStatuses(statuses: LeaveStatus[]): Observable<Leave[]> {
    return this.leaveStore.select(state => state.leaves)
      .pipe(
        map(leaves => {
          return Array.from(leaves.entities.values())
            .filter(leave => {
              const currentStatus = Leave.getCurrentStatus(leave.leaveWorkflows);
              return statuses.find(status => status === currentStatus) !== undefined;
            });
        }),
      );
  }

  findById(id: string): Observable<Leave> {
    return this.leaveStore.select(state => state.leaves.entities.get(id));
  }
}
