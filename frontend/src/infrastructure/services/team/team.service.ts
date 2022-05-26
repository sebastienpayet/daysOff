import {Injectable} from '@angular/core';
import {ServiceConfiguration} from '@configuration/serviceConfiguration';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Leave} from '@domain/leave/leave';
import {TeamCreateCommand} from '@services/team/command/team-create-command';
import {TeamDeleteCommand} from '@services/team/command/team-delete-command';
import {TeamUpdateCommand} from '@services/team/command/team-update-command';
import {Team} from '@domain/team/team';

@Injectable({
  providedIn: 'root'
})
export class TeamService extends ServiceConfiguration {

  private path = this.url + '/team';

  constructor(private http: HttpClient) {
    super();
  }

  list(): Observable<Leave[]> {
    return this.http.get<Leave[]>(this.path + '/listTeams');
  }

  create(command: TeamCreateCommand): Observable<Leave> {
    return this.http.post<Leave>(this.path, command);
  }

  delete(command: TeamDeleteCommand): Observable<Team> {
    return this.http.patch<Team>(this.path + '/delete', command);
  }

  update(command: TeamUpdateCommand): Observable<Team> {
    return this.http.patch<Team>(this.path, command);
  }
}
