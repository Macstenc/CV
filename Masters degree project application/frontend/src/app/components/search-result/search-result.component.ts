import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
@Component({
  selector: 'app-search-results', // Zmieniamy na 'app-search-results'
  standalone: true,
  imports: [
    RouterModule,
    CommonModule,
    MatButtonModule,
    MatMenuModule,
    MatCardModule,
    MatInputModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatIconModule,
    MatProgressSpinnerModule,
    BsDropdownModule,
  ],
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css'],
})
export class SearchResultsComponent implements OnInit, OnChanges {
  @Input() searchTerm!: string; // Deklaracja @Input() dla searchTerm
  products: Product[] = []; // Produkty odpowiadające zapytaniu
  loading: boolean = false; // Flaga ładowania

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    if (this.searchTerm) {
      this.searchProducts();
    }
  }

  ngOnChanges(): void {
    if (this.searchTerm) {
      this.searchProducts();
    } else {
      this.products = [];
    }
  }

  searchProducts(): void {
    this.loading = true;
    this.productService.getProductsByName(this.searchTerm).subscribe(
      (data) => {
        this.products = data;
        this.loading = false;
      },
      (error) => {
        this.loading = false;
        console.error('Błąd podczas wyszukiwania:', error);
      }
    );
  }

  selectProduct(product: Product): void {
    window.location.href = `/product/${product.id}`;
  }
}
