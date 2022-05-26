import {Repository} from '@business/port/repository.interface';
import {Leave} from '@domain/leave/leave';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {Observable} from 'rxjs';

export interface LeaveRepository extends Repository<Leave> {
  findAllByStatuses(statuses: LeaveStatus[]): Observable<Leave[]>;
}
