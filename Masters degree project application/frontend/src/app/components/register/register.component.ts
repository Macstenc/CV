import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzButtonModule,
    NzInputModule,
    NzCardModule,
    NzFormModule,
    NzNotificationModule,
    RouterModule,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  user: User = new User('', '', '', '', '', '', 'USER', '', []);
  repeatPassword: string = '';
  loading = false;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}
  get passwordMismatch(): boolean {
    return this.user.password !== this.repeatPassword && !!this.repeatPassword;
  }
  onSubmit(): void {
    if (this.passwordMismatch) {
      this.errorMessage = 'Passwords do not match!';
      setTimeout(() => (this.errorMessage = null), 3000);
      return;
    }

    this.loading = true;

    this.authService.register(this.user).subscribe({
      next: (response) => {
        console.log('Odpowiedź z backendu:', response);

        this.successMessage = 'Użytkownik zarejestrowany pomyślnie!';
        this.errorMessage = null;

        this.router.navigate(['/login']);
      },
      error: (error) => {
        if (error.status === 201) {
          this.successMessage = 'Użytkownik zarejestrowany pomyślnie!';
          this.router.navigate(['/login']);
        } else {
          console.log(error);
          this.errorMessage = error.error || 'Niepoprawne dane';

          setTimeout(() => {
            this.errorMessage = null;
          }, 3000);
        }
        this.loading = false;
      },
    });
  }
  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  addAddress(): void {
    this.user.shippingAddresses?.push({
      street: '',
      city: '',
      postalCode: '',
      country: '',
    });
  }

  removeAddress(index: number): void {
    this.user.shippingAddresses?.splice(index, 1);
  }
}
