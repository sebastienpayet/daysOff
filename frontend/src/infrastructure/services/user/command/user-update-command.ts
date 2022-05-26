import {Team} from '@domain/team/team';
import {Role} from '@domain/user/role.enum';

export class UserUpdateCommand {

  id: string;
  lastname: string;
  firstname: string;
  hasMinorChild: boolean;
  teamIds: Set<Team>;
  email: string;
  credential: string;
  role: Role;

  constructor(id: string, lastname: string, firstname: string, hasMinorChild: boolean, services: Set<Team>, email: string,
              role: Role, credential: string = null) {
    this.id = id;
    this.lastname = lastname;
    this.firstname = firstname;
    this.hasMinorChild = hasMinorChild;
    this.teamIds = services;
    this.email = email;
    this.role = role;
    this.credential = credential;
  }

}
