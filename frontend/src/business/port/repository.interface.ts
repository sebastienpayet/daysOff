import {Observable} from 'rxjs';

export interface Repository<T> {
  findAll(): Observable<T[]>;
  findById(id: string): Observable<T>;
  delete(id: string): Observable<boolean>;
}
