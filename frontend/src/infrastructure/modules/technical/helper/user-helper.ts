import {Role} from '@domain/user/role.enum';
import {User} from '@domain/user/user';

export function currentUserHasRole(user: User, role: Role): boolean {
  return user.role.toString() === Role[role];
}
