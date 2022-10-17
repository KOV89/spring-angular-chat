import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private readonly authService: AuthService) {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]{4,16}$')]),
      password: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9]{4,16}$')]),
    });
  }

  login() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value);
    }
  }

  registration() {
    if (this.loginForm.valid) {
      this.authService.registration(this.loginForm.value);
    }
  }
}
