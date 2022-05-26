import {environment} from "@environments/environment";

export abstract class ServiceConfiguration {
  public url = environment.url;
}
