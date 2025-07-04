import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  FormArray,
  Validators,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { NzMessageModule, NzMessageService } from 'ng-zorro-antd/message';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { SearchResultsComponent } from '../search-result/search-result.component';
import { NzSpinModule } from 'ng-zorro-antd/spin';

@Component({
  selector: 'app-user-panel',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzButtonModule,
    NzIconModule,
    RouterModule,
    NzInputModule,
    NzCardModule,
    ReactiveFormsModule,
    NzSpinModule,
    NzMessageModule,
    NzFormModule,
    NzNotificationModule,
  ],
  templateUrl: './user-panel.component.html',
  styleUrl: './user-panel.component.css',
})
export class UserPanelComponent implements OnInit {
  user: User | null = null;
  userForm!: FormGroup;
  isLoading = true;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private message: NzMessageService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadUserData();
  }

  private initForm(): void {
    this.userForm = this.fb.group({
      username: [{ value: '', disabled: true }, [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: [
        { value: '', disabled: true },
        [Validators.required, Validators.email],
      ],
      phoneNumber: [''],
      shippingAddresses: this.fb.array([]),
    });
  }

  private loadUserData(): void {
    this.authService.getCurrentUser().subscribe({
      next: (user) => {
        console.log(user);
        this.user = user;
        this.userForm.patchValue(user);
        this.setShippingAddresses(user.shippingAddresses || []);
        this.isLoading = false;
      },
      error: () => {
        this.message.error('Failed to load user data.');
        this.isLoading = false;
      },
    });
  }

  get shippingAddresses(): FormArray {
    return this.userForm.get('shippingAddresses') as FormArray;
  }

  private setShippingAddresses(addresses: any[]): void {
    this.shippingAddresses.clear();
    if (addresses && Array.isArray(addresses)) {
      addresses.forEach((address) => {
        this.shippingAddresses.push(
          this.fb.group({
            street: [address.street, Validators.required],
            city: [address.city, Validators.required],
            postalCode: [address.postalCode, Validators.required],
            country: [address.country, Validators.required],
          })
        );
      });
    }
  }

  addAddress(): void {
    this.shippingAddresses.push(
      this.fb.group({
        street: ['', Validators.required],
        city: ['', Validators.required],
        postalCode: ['', Validators.required],
        country: ['', Validators.required],
      })
    );
  }

  removeAddress(index: number): void {
    this.shippingAddresses.removeAt(index);
  }

  submitForm(): void {
    if (this.userForm.valid) {
      const updatedUser = {
        ...this.user,
        ...this.userForm.value,
      };
      this.authService.updateUser(updatedUser).subscribe({
        next: (response) => {
          console.log('Response from backend:', response);
          this.message.success(response);
        },
        error: (err) => {
          console.error('Error while updating user data:', err);
          this.message.error('Failed to update user data.');
        },
      });
    } else {
      this.message.error('Please fill in all required fields.');
    }
  }
}
