import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzImageModule } from 'ng-zorro-antd/image';
import { NzRateModule } from 'ng-zorro-antd/rate';
import { FormsModule } from '@angular/forms';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import {
  NzNotificationModule,
  NzNotificationService,
} from 'ng-zorro-antd/notification';
import { SearchResultsComponent } from '../search-result/search-result.component';
import { NzBreadCrumbModule } from 'ng-zorro-antd/breadcrumb';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CartService } from '../../services/cart.service';
import { ProductCarouselComponent } from '../product-carousel/product-carousel.component';
import { CustomCarouselComponent } from '../custom-carousel/custom-carousel.component';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [
    RouterModule,
    CommonModule,
    FormsModule,
    NzButtonModule,
    NzIconModule,
    NzInputModule,
    NzCardModule,
    NzFormModule,
    NzNotificationModule,
    NzImageModule,
    NzRateModule,
    NzTagModule,
    BsDropdownModule,
    CustomCarouselComponent,
    NzBreadCrumbModule,
  ],
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ProductDetailsComponent implements OnInit {
  product: Product | null = null;
  loading: boolean = true;
  relatedProducts: Product[] = [];
  selectedPhoto: string | null = null;
  similarProducts: Product[] = [];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private cartService: CartService,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');
    if (productId) {
      this.loadProduct(productId);
      this.loadSimilarProducts(productId);
      this.loadRelatedProducts(productId);
    }
  }

  loadProduct(productId: string): void {
    this.loading = true;
    this.productService.getProductById(productId).subscribe(
      (data) => {
        this.product = data;
        this.loading = false;
      },
      (error) => {
        console.error('Nie udało się załadować produktu:', error);
        this.loading = false;
      }
    );
  }

  selectPhoto(photo: string): void {
    this.selectedPhoto = photo;
  }
  addToCart(): void {
    if (this.product) {
      this.cartService.addToCart(this.product);

      this.notification.success(
        'Produkt dodany do koszyka',
        `${this.product.name} został dodany do koszyka!`,
        {
          nzPlacement: 'bottomRight',
          nzStyle: { marginBottom: '40px' },
        }
      );
    }
  }

  loadSimilarProducts(productId: string): void {
    this.productService.getSimilarProducts(productId).subscribe((products) => {
      console.log('Podobne produkty:', products); // Debug
      this.similarProducts = products;
    });
  }

  loadRelatedProducts(productId: string): void {
    this.productService.getRelatedProducts(productId).subscribe((products) => {
      console.log('Produkty z powiązanych kategorii:', products); // Debug
      this.relatedProducts = products;
    });
  }
}
