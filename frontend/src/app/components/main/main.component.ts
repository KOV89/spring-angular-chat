import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, ViewChild } from '@angular/core';
import { NgScrollbar } from 'ngx-scrollbar';
import { MessageService } from '../../../services/message.service';
import { Message } from '../../../models/message';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Destination, WebsocketService } from '../../../services/websocket.service';
import { debounceTime, map, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MainComponent implements AfterViewInit, OnDestroy {
  private readonly destroy$ = new Subject<void>();
  public messages: Map<number, Message> = new Map();
  public page: number | undefined = 0;
  public user: User;
  public messageForm: FormGroup;
  public scrollToBottom: boolean = true;

  @ViewChild(NgScrollbar) scrollbarRef: NgScrollbar | undefined;

  constructor(
    private readonly messageService: MessageService,
    private readonly authService: AuthService,
    private readonly websocketService: WebsocketService,
    private readonly cdr: ChangeDetectorRef,
  ) {
    this.loadPage(true);
    this.user = authService.getUser()!;
    this.messageForm = new FormGroup({
      message: new FormControl('', [Validators.pattern('^.{0,256}$')]),
    });
    this.websocketService.wsSubscriber(Destination.CHAT_MESSAGES, this.addMessage.bind(this), this.destroy$);
  }

  ngAfterViewInit(): void {
    this.scrollbarRef?.verticalScrolled
      .pipe(
        map((e: Event) => (e.target as HTMLElement).scrollTop),
        debounceTime(500),
        takeUntil(this.destroy$),
      )
      .subscribe((scrollTop: number) => {
        if (scrollTop === 0 && this.page) {
          this.scrollbarRef?.scrollTo({ top: 1, duration: 0 });
          this.loadPage();
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  public sendMessage(): void {
    const { message } = this.messageForm.value;
    if (this.messageForm.valid && !!message.length) {
      const dto: Omit<Message, 'id'> = { text: message, time: new Date(), user: { id: this.user.id! } };
      this.messageService.create(dto).subscribe(() => {
        this.messageForm.reset();
      });
    }
  }

  public onScrollUpdated(): void {
    if (this.scrollToBottom) {
      this.scrollbarRef?.scrollTo({ bottom: 0, duration: 0 });
      this.scrollToBottom = false;
    }
  }

  private loadPage(scrollToBottom = false): void {
    this.messageService.getAllByPage(this.page, 15).subscribe((page) => {
      this.addMessages(page.content, scrollToBottom);
      if (page.last) this.page = undefined;
      if (this.page !== undefined) this.page++;
    });
  }

  private addMessage(message: Message): void {
    this.addMessages(Array.from([message]), true);
  }

  private addMessages(messages: Message[], scrollToBottom = false): void {
    messages.forEach((message) => this.messages.set(message.id!, message));
    this.messages = new Map([...this.messages.entries()].sort((a, b) => a[0] - b[0]));
    this.scrollToBottom = scrollToBottom;
    this.cdr.detectChanges();
  }
}
