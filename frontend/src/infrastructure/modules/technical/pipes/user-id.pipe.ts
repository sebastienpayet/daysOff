import {Pipe, PipeTransform} from '@angular/core';
import {User} from '@domain/user/user';

@Pipe({
  name: 'userId'
})
export class UserIdPipe implements PipeTransform {

  transform(value: string, users: User[] = []): unknown {
    const user = users.find(currentUser => currentUser.id === value);
    if (user) {
      return user.firstname + ' ' + user.lastname;
    }
    return 'unknown user';
  }

}
