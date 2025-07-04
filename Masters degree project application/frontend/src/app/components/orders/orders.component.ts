import { Component, OnInit } from '@angular/core';
import { NzMessageModule, NzMessageService } from 'ng-zorro-antd/message';
import { Order } from '../../models/order';
import { AuthService } from '../../services/auth.service';
import { OrderService } from '../../services/order.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzListModule } from 'ng-zorro-antd/list';
import { NzCollapseModule } from 'ng-zorro-antd/collapse';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzButtonModule,
    NzCollapseModule,
    NzIconModule,
    NzListModule,
    RouterModule,
    NzInputModule,
    NzCardModule,
    ReactiveFormsModule,
    NzSpinModule,
    NzMessageModule,
    NzFormModule,
    NzNotificationModule,
  ],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css',
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];
  email: string = '';
  loading: boolean = false;
  productNames: { [productId: string]: string } = {};

  constructor(
    private orderService: OrderService,
    private authService: AuthService,
    private message: NzMessageService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(
      (user) => {
        this.email = user.email;
        this.fetchOrders();
      },
      (error) => {
        console.error('Error fetching current user:', error);
        this.message.error('Error fetching current user!');
      }
    );
  }

  fetchOrders(): void {
    if (this.email) {
      this.loading = true;
      this.orderService.getOrdersByUserEmail(this.email).subscribe(
        (orders) => {
          this.orders = orders;
          this.orders.forEach((order) => {
            order.items.forEach((item) => {
              if (!this.productNames[item.productId]) {
                this.productService.getProductById(item.productId).subscribe(
                  (product) => {
                    this.productNames[item.productId] = product.name;
                  },
                  (error) => {
                    console.error('Error fetching product:', error);
                    this.message.error('Error fetching product!');
                  }
                );
              }
            });
          });
          this.loading = false;
        },
        (error) => {
          this.loading = false;
          this.message.error('Error fetching orders!');
        }
      );
    }
  }

  getProductName(productId: string): string {
    return this.productNames[productId] || 'Unknown Product';
  }
}
