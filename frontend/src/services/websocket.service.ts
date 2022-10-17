import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject, takeUntil } from 'rxjs';
import { Client, StompSubscription } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

/**
 * Адреса доступные для подписки
 */
export enum Destination {
  CHAT_MESSAGES = '/topic/messages',
}

/**
 * Сервис для работа с веб-сокетом
 */
@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  private readonly webSocketUrl = `/api/ws`;
  private readonly connectionStatus$ = new BehaviorSubject(false);
  private readonly client: Client;

  constructor() {
    this.client = new Client({
      reconnectDelay: 9000,
      heartbeatIncoming: 9000,
      heartbeatOutgoing: 9000,
    });
    this.client.webSocketFactory = () => new SockJS(this.webSocketUrl);
    this.client.onConnect = () => this.connectionStatus$.next(true);
    this.client.onDisconnect = () => this.connectionStatus$.next(false);
    this.client.onWebSocketClose = () => this.connectionStatus$.next(false);
    this.client.activate();
  }

  /**
   * Функция для подписки на веб-сокет
   * @param destination - адрес на который нам нужно подписаться
   * @param callback - функция обрабатывающая получение нового объекта
   * @param destroy - объект, при уничтожении которого будут отменены подписки
   */
  wsSubscriber(destination: Destination, callback: (message: any) => void, destroy: Subject<void>): void {
    let wsSubscriber: StompSubscription;
    this.connectionStatus$.pipe(takeUntil(destroy)).subscribe((active) => {
      if (active) {
        wsSubscriber = this.client.subscribe(Destination.CHAT_MESSAGES, (response) => callback(JSON.parse(response.body)), {});
      } else {
        wsSubscriber?.unsubscribe();
      }
    });
    destroy.subscribe({
      complete: () => wsSubscriber?.unsubscribe(),
    });
  }
}
