import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MessageModalComponent } from '../app/components/modal/message-modal/message-modal.component';
/**
 * Сервис для работы с модальными окнами
 */
@Injectable({
  providedIn: 'root',
})
export class ModalService {
  constructor(protected readonly matDialog: MatDialog) {}

  showMessage(message: string, title?: string): void {
    this.matDialog.open(MessageModalComponent, {
      width: '400px',
      data: { message, title },
    });
  }
}
