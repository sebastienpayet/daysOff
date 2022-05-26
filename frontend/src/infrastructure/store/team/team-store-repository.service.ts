import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {map} from 'rxjs/operators';
import {Repository} from '@business/port/repository.interface';
import {StoreData} from '@store/store-data';
import {Team} from '@domain/team/team';

@Injectable({providedIn: 'root'})
export class TeamStoreRepository implements Repository<Team> {

  constructor(private teamStore: Store<{ teams: StoreData<Team> }>) {
  }

  delete(id: string): Observable<boolean> {
    return this.teamStore.select(state => state.teams.entities.delete(id));
  }

  findAll(): Observable<Team[]> {
    return this.teamStore.select(state => state.teams)
      .pipe(
        map(teams => teams ? [...teams.entities.values()] : []),
      );
  }

  findById(id: string): Observable<Team> {
    return this.teamStore.select(state => state.teams.entities.get(id));
  }
}
