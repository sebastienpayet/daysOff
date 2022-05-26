import {AbstractDomain} from '@domain/abstract-domain';
import {Role} from '@domain/user/role.enum';

export class User extends AbstractDomain<User>{
  createdBy: string;
  createdDate: Date;
  id: string;
  lastModifiedBy: string;
  lastModifiedDate: Date;
  removed: boolean;
  version: number;
  firstname: string;
  lastname: string;
  hasMinorChild: boolean;
  teamIds: string[];
  role: Role;
  email: string;
}
