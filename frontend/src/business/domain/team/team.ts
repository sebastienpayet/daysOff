import {AbstractDomain} from '@domain/abstract-domain';

export class Team extends AbstractDomain<Team> {
  name: string;
}
