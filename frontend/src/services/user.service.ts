import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CrudService } from './crud.service';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService extends CrudService<User> {
  protected serviceUrl = '/user';

  constructor(protected http: HttpClient) {
    super();
  }
}
