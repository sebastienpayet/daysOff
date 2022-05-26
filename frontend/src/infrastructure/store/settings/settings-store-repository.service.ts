import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {map} from 'rxjs/operators';
import {Repository} from '@business/port/repository.interface';
import {StoreData} from '@store/store-data';
import {Settings} from '@domain/settings/settings';

@Injectable({providedIn: 'root'})
export class SettingsStoreRepository implements Repository<Settings> {

  constructor(private settingsStore: Store<{ settings: StoreData<Settings> }>) {
  }

  delete(id: string): Observable<boolean> {
    return this.settingsStore.select(state => state.settings.entities.delete(id));
  }

  findAll(): Observable<Settings[]> {
    return this.settingsStore.select(state => state.settings)
      .pipe(
        map(settings => settings ? [...settings.entities.values()] : []),
      );
  }

  findById(id: string): Observable<Settings> {
    return this.settingsStore.select(state => state.settings.entities.get(id));
  }

  getSettings(): Observable<Settings> {
    return this.settingsStore.select(state => state.settings)
      .pipe(
        map(settings => settings ? [...settings.entities.values()][0] : null)
      );
  }
}
