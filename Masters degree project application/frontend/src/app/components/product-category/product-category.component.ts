import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { FormsModule } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { NzEmptyModule } from 'ng-zorro-antd/empty';
import { NzSliderModule, NzSliderValue } from 'ng-zorro-antd/slider';
import { NzAutocompleteModule } from 'ng-zorro-antd/auto-complete';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzSwitchModule } from 'ng-zorro-antd/switch';
import { CustomCarouselComponent } from '../custom-carousel/custom-carousel.component';

@Component({
  selector: 'app-product-category',
  standalone: true,
  imports: [
    CommonModule,
    NzSelectModule,
    NzSpinModule,
    FormsModule,
    NzSliderModule,
    NzSwitchModule,
    NzEmptyModule,
    NzButtonModule,
    NzAutocompleteModule,
    NzInputModule,
    NzCardModule,
    NzFormModule,
    NzNotificationModule,
    BsDropdownModule,
    RouterModule,
  ],
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css'],
})
export class ProductCategoryComponent implements OnInit {
  category: string = '';
  products: Product[] = [];
  sortOption: string = 'price';
  sortDirection: string = 'asc';
  minPrice: number = 0;
  maxPrice: number = 1000;
  recommended: boolean = false;
  isLoading: boolean = true;
  sliderMaxValue: number = 1000;
  constructor(
    private productService: ProductService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.category = params['category'];
      this.fetchMaxPrice();
    });
  }

  fetchMaxPrice(): void {
    this.productService.getMaxPriceByCategory(this.category).subscribe(
      (maxPrice) => {
        this.sliderMaxValue = Math.ceil(maxPrice);
        this.maxPrice = this.sliderMaxValue;
        this.fetchProducts();
      },
      (error) => {
        console.error('Error fetching max price:', error);
      }
    );
  }

  fetchProducts(): void {
    this.isLoading = true;
    this.productService
      .getProductsByCategoryWithFilters(
        this.category,
        this.minPrice,
        this.maxPrice,
        this.sortOption,
        this.sortDirection,
        this.recommended
      )
      .subscribe(
        (data) => {
          this.products = data.map((product) => ({
            ...product,
            averageRating: this.roundToHalf(product.averageRating),
            discount: Math.round(product.discount),
          }));
          this.isLoading = false;
        },
        (error) => {
          console.error('Error fetching products:', error);
          this.isLoading = false;
        }
      );
  }

  onPriceChange(value: NzSliderValue): void {
    if (Array.isArray(value)) {
      const [min, max] = value;

      if (min >= max) {
        this.minPrice = max - 1;
      } else {
        this.minPrice = min;
      }

      if (max <= min) {
        this.maxPrice = min + 1;
      } else {
        this.maxPrice = max;
      }

      this.fetchProducts();
    } else {
      console.error('Invalid slider value:', value);
    }
  }

  private roundToHalf(value: number): number {
    return Math.round(value * 2) / 2;
  }
}
