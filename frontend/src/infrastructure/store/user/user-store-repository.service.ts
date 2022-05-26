import {Observable} from 'rxjs';
import {User} from '@domain/user/user';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {map} from 'rxjs/operators';
import {Repository} from '@business/port/repository.interface';
import {StoreData} from '@store/store-data';

@Injectable({providedIn: 'root'})
export class UserStoreRepository implements Repository<User> {

  constructor(private usersStore: Store<{ users: StoreData<User> }>) {
  }

  findAll(): Observable<User[]> {
    return this.usersStore.select(state => state.users)
      .pipe(
        map(users => users ? [...users.entities.values()] : []),
      );
  }

  findById(id: string): Observable<User> {
    return this.usersStore.select(state => state.users.entities.get(id));
  }

  delete(id: string): Observable<boolean> {
    return this.usersStore.select(state => state.users.entities.delete(id));
  }
}
