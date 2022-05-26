import {Repository} from '@business/port/repository.interface';
import {Observable} from 'rxjs';
import {LeaveBalance} from '@domain/leave-balance/leave-balance';

export interface LeaveBalanceRepository extends Repository<LeaveBalance> {
  findAllByUserIdAndYear(userId: string, year: number): Observable<LeaveBalance[]>;
}
