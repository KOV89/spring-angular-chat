import { ChangeDetectionStrategy, Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent {
  user$: Observable<User | undefined>;

  constructor(public readonly authService: AuthService) {
    this.user$ = authService.getUserObservable();
  }
}
