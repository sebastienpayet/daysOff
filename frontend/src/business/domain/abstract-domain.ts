export abstract class AbstractDomain<T> {
  id: string;
  removed: boolean;
  version: number;
  createdBy: string;
  createdDate: Date;
  lastModifiedBy: string;
  lastModifiedDate: Date;
}
