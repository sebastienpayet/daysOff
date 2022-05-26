import {Team} from '@domain/team/team';
import {Role} from '@domain/user/role.enum';

export class UserCreateCommand {
  lastname: string;
  firstname: string;
  hasMinorChild: boolean;
  teamIds: Set<Team>;
  email: string;
  credential: string;
  role: Role;

  constructor(lastname: string, firstname: string, hasMinorChild: boolean, services: Set<Team>, email: string,
              role: Role, credential: string) {
    this.lastname = lastname;
    this.firstname = firstname;
    this.hasMinorChild = hasMinorChild;
    this.teamIds = services;
    this.email = email;
    this.credential = credential;
    this.role = role;
  }
}
