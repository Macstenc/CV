import { CommonModule } from '@angular/common';
import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { RouterModule } from '@angular/router';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';
import { Product } from '../../models/product';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { FormsModule } from '@angular/forms';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { SearchResultsComponent } from '../search-result/search-result.component';
import { CartService } from '../../services/cart.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-product-carousel',
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
  ],
  templateUrl: './product-carousel.component.html',
  styleUrls: ['./product-carousel.component.css'],
})
export class ProductCarouselComponent implements OnInit, OnChanges {
  constructor(
    private cartService: CartService,
    private notification: NzNotificationService
  ) {}
  @Input() products: Product[] = [];
  @Input() title: string = '';
  productChunks: Product[][] = [];
  currentPage: number = 0;
  pageSize: number = 5;

  ngOnInit(): void {
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

  nextPage(): void {
    if (this.currentPage < this.productChunks.length - 1) {
      this.currentPage++;
    }
  }
  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['products'] && changes['products'].currentValue) {
      this.chunkProducts();
    }
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

  getTransformStyle(): string {
    return `translateX(-${this.currentPage * 100}%)`;
  }
}
