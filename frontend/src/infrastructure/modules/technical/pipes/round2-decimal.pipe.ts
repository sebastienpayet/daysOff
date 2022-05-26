import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'round2Decimal'
})
export class Round2DecimalPipe implements PipeTransform {

  transform(value: number, ...args: unknown[]): unknown {
    return Math.round(value * 1e2 ) / 1e2;
  }

}
