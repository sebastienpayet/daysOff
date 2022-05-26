import {AbstractDomain} from '@domain/abstract-domain';

export class Settings extends AbstractDomain<Settings> {
  publicHolidays: Date[];
  inputRecoveryInHours: boolean;
  numberOfHoursInAWorkingDay: number;
}
