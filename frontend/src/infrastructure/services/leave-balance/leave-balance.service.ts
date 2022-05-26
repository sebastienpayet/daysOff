import {Injectable} from '@angular/core';
import {ServiceConfiguration} from '@configuration/serviceConfiguration';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LeaveBalance} from '@domain/leave-balance/leave-balance';
import {LeaveBalanceCreateCommand} from '@services/leave-balance/command/leave-balance-create-command';
import {LeaveBalanceListCommand} from '@services/leave-balance/command/leave-balance-list-command';

@Injectable({
  providedIn: 'root'
})
export class LeaveBalanceService extends ServiceConfiguration {

  private path = this.url + '/leaveBalance';

  constructor(private http: HttpClient) {
    super();
  }

  list(command: LeaveBalanceListCommand): Observable<LeaveBalance[]> {
    return this.http.post<LeaveBalance[]>(this.path + '/listLeaveBalances', command);
  }

  create(command: LeaveBalanceCreateCommand): Observable<LeaveBalance> {
    return this.http.post<LeaveBalance>(this.path, command);
  }
}
