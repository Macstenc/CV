import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { CommonModule } from '@angular/common';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { ProductCarouselComponent } from '../product-carousel/product-carousel.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ProductCarouselComponent,
    NzCarouselModule,
    NzButtonModule,
    NzCardModule,
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  recommendedProducts: Product[] = [];
  mostViewed: Product[] = [];
  newProducts: Product[] = [];
  lastProducts: Product[] = [];
  highestDiscount: Product[] = [];

  constructor(private productService: ProductService) {}
  promotions: string[] = ['Promotion1', 'Promotion2', 'Promotion3'];
  array = [1, 2, 3];

  ngOnInit(): void {
    this.loadRecommendedProducts();
    this.loadMostViewed();
    this.loadNewProducts();
    this.loadLastProducts();
    this.loadHighestDiscount();
  }

  loadRecommendedProducts(): void {
    this.productService.getRecommendedProducts(1, 80).subscribe((products) => {
      this.recommendedProducts = products;
      console.log('Polecane produkty:', this.recommendedProducts);
    });
  }

  loadMostViewed(): void {
    this.productService
      .getMostViewedWithPagination(1, 60)
      .subscribe((products) => {
        this.mostViewed = products;
      });
  }

  loadNewProducts(): void {
    this.productService.getNewProducts(1, 80).subscribe((products) => {
      this.newProducts = products;
    });
  }

  loadLastProducts(): void {
    this.productService.getLastStocks(1, 80).subscribe((products) => {
      this.lastProducts = products;
    });
  }
  loadHighestDiscount(): void {
    this.productService.getHighestDiscount(1, 80).subscribe((products) => {
      this.highestDiscount = products;
    });
  }
}
