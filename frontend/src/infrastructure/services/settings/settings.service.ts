import {Injectable} from '@angular/core';
import {ServiceConfiguration} from '@configuration/serviceConfiguration';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Settings} from '@domain/settings/settings';

@Injectable({
  providedIn: 'root'
})
export class SettingsService extends ServiceConfiguration {

  private path = this.url + '/settings';

  constructor(private http: HttpClient) {
    super();
  }

  load(): Observable<Settings> {
    return this.http.get<Settings>(this.path + '/loadSettings');
  }
}
