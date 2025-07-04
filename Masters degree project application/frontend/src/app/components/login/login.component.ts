import { ChangeDetectorRef, Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzFormModule } from 'ng-zorro-antd/form';
import {
  NzNotificationModule,
  NzNotificationService,
} from 'ng-zorro-antd/notification';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { SearchResultsComponent } from '../search-result/search-result.component';
import { BrowserModule } from '@angular/platform-browser';
import { NzCardModule } from 'ng-zorro-antd/card';

import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    NzButtonModule,
    NzInputModule,
    NzCardModule,
    NzFormModule,
    NzNotificationModule,
    BsDropdownModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  loading: boolean = false;
  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private notification: NzNotificationService
  ) {}

  login() {
    if (!this.username || !this.password) {
      this.errorMessage = 'Prosze wprowadź Login i Hasło';

      this.notification.error('Błąd logowania', this.errorMessage, {
        nzPlacement: 'bottomRight',
        nzDuration: 3000,
      });

      return;
    }

    this.loading = true;

    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/']).then(() => {
          this.loading = false;
        });
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Invalid username or password';
        this.notification.error('Login Failed', this.errorMessage, {
          nzPlacement: 'bottomRight',
          nzDuration: 3000,
        });
      },
    });
  }
  goToRegister(): void {
    this.router.navigate(['/register']);
  }
}
