import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CrudService } from './crud.service';
import { Message } from '../models/message';

@Injectable({
  providedIn: 'root',
})
export class MessageService extends CrudService<Message> {
  protected serviceUrl = '/message';

  constructor(protected http: HttpClient) {
    super();
  }
}
