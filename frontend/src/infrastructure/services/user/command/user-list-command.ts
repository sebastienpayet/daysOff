import {Team} from '@domain/team/team';

export class UserListCommand {
  services: Team[];

  constructor(services: Team[]) {
    this.services = services
  }
}
