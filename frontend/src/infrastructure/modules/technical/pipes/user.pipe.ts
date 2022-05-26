import {Pipe, PipeTransform} from '@angular/core';
import {User} from '@domain/user/user';

@Pipe({
  name: 'user'
})
export class UserPipe implements PipeTransform {

  transform(value: User, ...args: unknown[]): string {
    if (value) {
      return value.firstname + ' ' + value.lastname;
    }
    return 'unknown user';
  }
}
