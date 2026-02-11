import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, FormsModule, RouterLink]
})
export class LoginComponent {
  authService = inject(AuthService);
  router = inject(Router);

  email = 'user@fabflix.com';
  password = 'password';
  errorMessage = signal<string | null>(null);

  onSubmit(): void {
    this.errorMessage.set(null);
    if (this.authService.login(this.email, this.password)) {
      this.router.navigate(['/movies']);
    } else {
      this.errorMessage.set('Invalid email or password.');
    }
  }
}
