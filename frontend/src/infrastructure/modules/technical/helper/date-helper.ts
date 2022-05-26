export class DatesRange {
  public startDate: Date;
  public endDate: Date;

  constructor(startDate?: Date, endDate?: Date) {
    if (startDate && endDate && startDate <= endDate) {
      const resultStartDate = fixTimeOnDate(startDate, 0, 0, 0);
      const resultEndDate = fixTimeOnDate(endDate, 23, 59, 59);

      this.startDate = resultStartDate;
      this.endDate = resultEndDate;
    }
  }
}

export function fixTimeOnDate(date: Date, hours: number, minutes: number, seconds: number): Date {
  const fixedDatetime = new Date(date);
  fixedDatetime.setHours(hours);
  fixedDatetime.setMinutes(minutes);
  fixedDatetime.setSeconds(seconds);
  return fixedDatetime;
}

export function convertToIso8601UTCString(date: Date): string {
  // target pattern 2021-09-30T21:59:59.000Z
  const dateMonth = date.getMonth() + 1

  return date.getFullYear()
    + '-' + ((dateMonth < 10) ? '0' + dateMonth : dateMonth)
    + '-' + ((date.getDate() < 10) ? '0' + date.getDate() : date.getDate())
    + 'T' + ((date.getHours() < 10) ? '0' + date.getHours() : date.getHours())
    + ':' + ((date.getMinutes() < 10) ? '0' + date.getMinutes() : date.getMinutes())
    + ':' + ((date.getSeconds() < 10) ? '0' + date.getSeconds() : date.getSeconds())
    + '.000Z';
}

export function convertToUTC(date: Date): Date {
  return new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(), date.getUTCHours(), date.getUTCMinutes(),
    date.getUTCSeconds());
}

export function areRangesOverlapping(left: DatesRange, right: DatesRange): boolean {
  return left.endDate > right.startDate && left.startDate < right.endDate;
}
