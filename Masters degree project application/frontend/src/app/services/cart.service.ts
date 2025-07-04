import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartItems } from '../models/cart-items';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartItems: CartItems[] = [];
  private cartItemsSubject = new BehaviorSubject<CartItems[]>([]);

  cartItems$ = this.cartItemsSubject.asObservable();

  constructor() {
    this.loadCartFromLocalStorage();
  }

  private saveCartToLocalStorage(): void {
    localStorage.setItem('cart', JSON.stringify(this.cartItems));
  }

  private loadCartFromLocalStorage(): void {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      this.cartItems = JSON.parse(savedCart);
      this.cartItemsSubject.next(this.cartItems);
    }
  }

  addToCart(product: Product, quantity: number = 1): void {
    const existingItem = this.cartItems.find(
      (item) => item.product.id === product.id
    );

    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      this.cartItems.push(new CartItems(product, quantity));
    }

    this.cartItemsSubject.next(this.cartItems);
    this.saveCartToLocalStorage();
  }

  removeFromCart(productId: string): void {
    this.cartItems = this.cartItems.filter(
      (item) => item.product.id !== productId
    );
    this.cartItemsSubject.next(this.cartItems);
    this.saveCartToLocalStorage();
  }

  clearCart(): void {
    this.cartItems = [];
    this.cartItemsSubject.next(this.cartItems);
    localStorage.removeItem('cart');
  }

  getTotalPrice(): number {
    return this.cartItems.reduce(
      (total, item) =>
        total +
        item.quantity * (item.product.discountedPrice || item.product.price),
      0
    );
  }
}
