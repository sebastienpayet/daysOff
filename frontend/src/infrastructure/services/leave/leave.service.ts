import {Injectable} from '@angular/core';
import {ServiceConfiguration} from '@configuration/serviceConfiguration';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Leave} from '@domain/leave/leave';
import {LeaveCreateCommand} from '@services/leave/command/leave-create-command';
import {LeaveSubmitCommand} from '@services/leave/command/leave-submit-command';
import {LeaveDraftCommand} from '@services/leave/command/leave-draft-command';
import {LeaveRefuseCommand} from '@services/leave/command/leave-refuse-command';
import {LeaveDeleteCommand} from '@services/leave/command/leave-delete-command';
import {LeaveValidationByServiceCommand} from '@services/leave/command/leave-validation-by-service-command';
import {LeaveUpdateCommand} from '@services/leave/command/leave-update-command';
import {LeaveValidationByManagementCommand} from '@services/leave/command/leave-validation-by-management-command';

@Injectable({
  providedIn: 'root'
})
export class LeaveService extends ServiceConfiguration {

  private path = this.url + '/leaveRequest';

  constructor(private http: HttpClient) {
    super();
  }

  list(): Observable<Leave[]> {
    return this.http.get<Leave[]>(this.path + '/listLeaveRequests');
  }

  create(command: LeaveCreateCommand): Observable<Leave> {
    return this.http.post<Leave>(this.path, command);
  }

  update(command: LeaveUpdateCommand): Observable<Leave> {
    return this.http.patch<Leave>(this.path + '/updateALeaveRequest', command);
  }

  delete(command: LeaveDeleteCommand): Observable<Leave> {
    return this.http.patch<Leave>(this.path + '/deleteALeaveRequest', command);
  }

  submitALeaveRequest(command: LeaveSubmitCommand): Observable<Leave> {
    return this.http.patch<Leave>(this.path + '/submitALeaveRequest', command);
  }

  validateALeaveRequestByService(command: LeaveValidationByServiceCommand): Observable<Leave> {
    return this.http.patch<Leave>(this.path + '/validateALeaveRequestByService', command);
  }

  validateALeaveRequestByManagement(command: LeaveValidationByManagementCommand): Observable<Leave> {
    return this.http.patch<Leave>(this.path + '/validateALeaveRequestByManagement', command);
  }


  refuseALeaveRequest(command: LeaveRefuseCommand): Observable<Leave> {
    return this.http.patch<Leave>(this.path + '/refuseALeaveRequest', command);
  }

  setLeaveRequestAsDraft(command: LeaveDraftCommand): Observable<Leave> {
    return this.http.patch<Leave>(this.path + '/setLeaveRequestAsDraft', command);
  }
}
