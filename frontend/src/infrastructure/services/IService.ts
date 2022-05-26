import {HttpHeaders} from '@angular/common/http';

export interface IService {
  token: string;
  buildHeaders(): HttpHeaders;
}
