import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import {
  NzNotificationModule,
  NzNotificationService,
} from 'ng-zorro-antd/notification';
import { NzListModule } from 'ng-zorro-antd/list';
import { CartItems } from '../../models/cart-items';
import { CartService } from '../../services/cart.service';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzEmptyModule } from 'ng-zorro-antd/empty';

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzButtonModule,
    NzIconModule,
    RouterModule,
    NzEmptyModule,
    NzInputModule,
    NzCardModule,
    NzSelectModule,
    RouterModule,
    NzFormModule,
    NzListModule,
    NzNotificationModule,
  ],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css',
})
export class ShoppingCartComponent implements OnInit {
  cartItems: CartItems[] = [];
  totalAmount: number = 0;
  showDiscountInput: boolean = false;
  emptyTpl: any;

  constructor(
    private cartService: CartService,
    private router: Router,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {
    this.cartService.cartItems$.subscribe((items) => {
      this.cartItems = items;
      this.calculateTotalAmount();
    });
  }

  calculateTotalAmount(): void {
    this.totalAmount = this.cartService.getTotalPrice();
  }

  removeItem(item: CartItems): void {
    this.cartService.removeFromCart(item.product.id);
  }

  increaseQuantity(item: CartItems): void {
    this.cartService.addToCart(item.product, 1);
  }

  decreaseQuantity(item: CartItems): void {
    if (item.quantity > 1) {
      this.cartService.addToCart(item.product, -1);
    }
  }

  clearCart(): void {
    this.cartService.clearCart();
  }

  checkout(): void {
    if (this.cartItems.length === 0) {
      this.notification.error('Błąd', 'Koszyk jest pusty.');
      return;
    }
    this.router.navigate(['/checkout']);
  }

  goBackToShopping(): void {
    this.router.navigate(['/']);
  }

  toggleDiscountInput(): void {
    this.showDiscountInput = !this.showDiscountInput;
  }
}
