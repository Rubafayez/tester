import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isAuthenticated = signal<boolean>(false);

  login(email: string, password: string): boolean {
    // Mock login logic
    if (email === 'user@fabflix.com' && password === 'password') {
      this.isAuthenticated.set(true);
      return true;
    }
    return false;
  }

  logout(): void {
    this.isAuthenticated.set(false);
  }
}
