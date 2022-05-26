import {User} from '@domain/user/user';
import {UserCreateCommand} from './command/user-create-command';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ServiceConfiguration} from '@configuration/serviceConfiguration';
import {UserUpdateCommand} from '@services/user/command/user-update-command';

@Injectable({
  providedIn: 'root'
})
export class UserService extends ServiceConfiguration {

  private path = this.url + '/user';

  constructor(private http: HttpClient) {
    super();
  }

  listAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.path + '/listAllUsers');
  }

  listAllObfuscatedUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.path + '/listAllObfuscatedUsers');
  }

  create(command: UserCreateCommand): Observable<User> {
    return this.http.post<User>(this.path, command);
  }

  update(command: UserUpdateCommand): Observable<User> {
    return this.http.patch<User>(this.path, command);
  }
}
