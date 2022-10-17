import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AppGuard implements CanActivate {
  constructor(private readonly router: Router, private readonly authService: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.getToken() && this.authService.isTokenExpired()) {
      this.authService.logout();
      return false;
    }

    if (this.authService.isAuthenticated() && state.url.endsWith('/login')) {
      this.router.navigate(['']);
      return false;
    }

    if (!this.authService.isAuthenticated() && !state.url.endsWith('/login')) {
      this.router.navigate(['/login']);
      return false;
    }

    if (state.url.endsWith('/')) {
      return true;
    }

    if (state.url.endsWith('/login')) {
      return true;
    }

    if (state.url.endsWith('/admin')) {
      if (this.authService.isUserAdmin()) {
        return true;
      } else {
        this.router.navigate(['']);
        return false;
      }
    }

    return false;
  }
}
