import {Pipe, PipeTransform} from '@angular/core';
import {SettingsStoreRepository} from '@store/settings/settings-store-repository.service';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Pipe({
  name: 'hours'
})
export class HoursPipe implements PipeTransform {

  constructor(private settingsStoreRepository: SettingsStoreRepository) {
  }

  transform(value: number): Observable<number> {

    return this.settingsStoreRepository.getSettings().pipe(
      map((result) => {
        return Math.round(value * result.numberOfHoursInAWorkingDay * 1e2) / 1e2;
      })
    );
  }
}
