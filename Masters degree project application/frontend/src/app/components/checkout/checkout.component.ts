import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzAutocompleteModule } from 'ng-zorro-antd/auto-complete';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { CommonModule } from '@angular/common';
import { CartItems } from '../../models/cart-items';
import { Order } from '../../models/order';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [
    FormsModule,
    NzAutocompleteModule,
    NzInputModule,
    CommonModule,
    NzCheckboxModule,
    NzButtonModule,
    NzSelectModule,
  ],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css',
})
export class CheckoutComponent implements OnInit {
  options: string[] = [];
  cartItems: CartItems[] = [];
  totalPrice: number = 0;
  emailExists: boolean = false;
  emailCheck$ = new Subject<string>();
  userAddresses: any[] = [];

  formUser = {
    email: '',
    firstName: '',
    lastName: '',
    phone: '',
    street: '',
    city: '',
    postalCode: '',
    country: '',
    agreeToTerms: false,
  };

  constructor(
    private notification: NzNotificationService,
    private cartService: CartService,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {
    this.cartService.cartItems$.subscribe((items) => {
      this.cartItems = items;
      this.totalPrice = this.cartService.getTotalPrice();
    });

    this.emailCheck$
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        switchMap((email) => this.authService.checkEmailExists(email))
      )
      .subscribe((exists) => {
        this.emailExists = exists;
        if (exists) {
          this.notification.error('Błąd', 'Email jest już w użyciu!');
        }
      });
  }

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe((user) => {
      if (user) {
        this.formUser.firstName = user.firstName;
        this.formUser.lastName = user.lastName;
        this.formUser.email = user.email;
        this.formUser.phone = user.phoneNumber || '';
        this.userAddresses = user.shippingAddresses || [];
        if (this.userAddresses.length > 0) {
          this.selectAddress(this.userAddresses[0]);
        }
      }
    });
  }

  onEmailInput(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    const emailValue = inputElement.value;

    if (emailValue) {
      this.emailCheck$.next(emailValue);
    }
  }

  selectAddress(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const selectedIndex = selectElement.selectedIndex;

    if (selectedIndex >= 0) {
      const selectedAddress = this.userAddresses[selectedIndex];
      this.formUser.street = selectedAddress.street;
      this.formUser.city = selectedAddress.city;
      this.formUser.postalCode = selectedAddress.postalCode;
      this.formUser.country = selectedAddress.country;
    }
  }

  onSubmit(): void {
    if (!this.formUser.agreeToTerms) {
      this.notification.error('Błąd', 'Musisz zaakceptować regulamin.');
      return;
    }

    if (Object.values(this.formUser).some((field) => !field)) {
      this.notification.error('Błąd', 'Uzupełnij wszystkie pola.');
      return;
    }

    if (this.cartItems.length === 0) {
      this.notification.error('Błąd', 'Koszyk jest pusty.');
      return;
    }

    const order: Order = new Order(
      null,
      this.formUser.firstName,
      this.formUser.lastName,
      this.formUser.email,
      this.formUser.phone,
      this.cartItems.map((item) => ({
        productId: item.product.id,
        productName: item.product.name,
        quantity: item.quantity,
        price: item.product.discountedPrice || item.product.price,
        totalPrice: item.quantity * item.product.discountedPrice,
      })),
      this.totalPrice,
      {
        street: this.formUser.street,
        city: this.formUser.city,
        postalCode: this.formUser.postalCode,
        country: this.formUser.country,
      },
      new Date(),
      'Pending'
    );

    this.orderService.createOrder(order).subscribe({
      next: () => {
        this.notification.success(
          'Sukces',
          'Twoje zamówienie zostało złożone!'
        );
        this.cartService.clearCart();
        this.router.navigate(['/home']);
      },
      error: () => {
        this.notification.error(
          'Błąd',
          'Nie udało się złożyć zamówienia. Spróbuj ponownie.'
        );
      },
    });
  }
}
