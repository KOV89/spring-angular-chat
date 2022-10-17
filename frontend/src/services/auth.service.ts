import { Injectable } from '@angular/core';
import { LoginUser, RegisterUser, Role, User, UserAuth } from '../models/user';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { clearUserState, setUserState } from '../store/user/user.action';
import { getUser, getUserState } from '../store/user/user.selector';
import { Observable } from 'rxjs';
import { ModalService } from './modal.service';

const helper = new JwtHelperService();

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = '/api/auth';
  private token: string | undefined;
  private user: User | undefined;

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router,
    private readonly store: Store,
    private readonly modalService: ModalService,
  ) {
    this.store.select(getUserState).subscribe((userState) => {
      this.user = userState.user;
      this.token = userState.token;
    });
  }

  login(user: LoginUser): void {
    this.http.post<UserAuth>(`${this.baseUrl + '/login'}`, user).subscribe({
      next: (userAuth: UserAuth) => {
        this.store.dispatch(
          setUserState({
            user: helper.decodeToken<User>(userAuth.token),
            token: userAuth.token,
          }),
        );
        this.router.navigate(['']);
      },
      error: (response: HttpErrorResponse) => {
        const message = response?.error?.message ?? 'Авторизация не пройдена';
        const title = 'Ошибка авторизации';
        this.modalService.showMessage(message, title);
      },
    });
  }

  logout(): void {
    this.store.dispatch(clearUserState());
    this.router.navigate(['/login']);
  }

  registration(user: RegisterUser): void {
    this.http.post<User>(`${this.baseUrl + '/registration'}`, user).subscribe({
      next: () => this.login(user),
      error: (response: HttpErrorResponse) => {
        const message = response?.error?.message ?? 'Регистрация не пройдена';
        const title = 'Ошибка регистрации';
        this.modalService.showMessage(message, title);
      },
    });
  }

  getToken(): string | undefined {
    return this.token;
  }

  getUser(): User | undefined {
    return this.user;
  }

  getUserObservable(): Observable<User | undefined> {
    return this.store.select(getUser);
  }

  isTokenExpired(): boolean {
    return helper.isTokenExpired(this.token);
  }

  isAuthenticated(): boolean {
    return !this.isTokenExpired();
  }

  isUserAdmin(): boolean {
    return !!this.user?.roles?.includes(Role.ROLE_ADMIN);
  }
}
