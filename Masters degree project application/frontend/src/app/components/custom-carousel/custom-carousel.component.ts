import { Component, Input, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product';
import { Router, RouterModule } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { FormsModule } from '@angular/forms';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import {
  NzNotificationModule,
  NzNotificationService,
} from 'ng-zorro-antd/notification';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-custom-carousel',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzButtonModule,
    NzNotificationModule,
    NzIconModule,
    RouterModule,
    NzInputModule,
    NzCardModule,
    NzFormModule,
    NzNotificationModule,
    RouterModule,
  ],
  templateUrl: './custom-carousel.component.html',
  styleUrls: ['./custom-carousel.component.css'],
})
export class CustomCarouselComponent implements OnChanges {
  constructor(
    private cartService: CartService,
    private notification: NzNotificationService,
    private router: Router
  ) {}
  @Input() products: Product[] = [];
  @Input() title: string = '';
  productChunks: Product[][] = [];
  currentPage: number = 0;
  pageSize: number = 5;

  ngOnChanges(): void {
    this.chunkProducts();
  }

  chunkProducts(): void {
    const chunkSize = this.pageSize;
    this.productChunks = [];
    for (let i = 0; i < this.products.length; i += chunkSize) {
      const chunk = this.products.slice(i, i + chunkSize);
      this.productChunks.push(chunk);
    }
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
    }
  }

  nextPage(): void {
    if (this.currentPage < this.productChunks.length - 1) {
      this.currentPage++;
    }
  }

  getTransformStyle(): string {
    return `translateX(-${this.currentPage * 100}%)`;
  }
  navigateToProduct(productId: string): void {
    const url = `/product/${productId}`;
    this.router.navigateByUrl(url).then(() => {
      window.location.reload();
    });
  }

  addToCart(product: Product): void {
    if (product) {
      this.cartService.addToCart(product);

      this.notification.success(
        'Produkt dodany do koszyka',
        `${product.name} został dodany do koszyka!`,
        {
          nzPlacement: 'bottomRight',
          nzStyle: { marginBottom: '40px' },
        }
      );
    }
  }
}
