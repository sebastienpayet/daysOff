import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'normalizeLowerCase'
})
export class NormalizeLowerCasePipe implements PipeTransform {

  transform(value: string | null): string {
    return value?.trim().toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, '');
  }

}
