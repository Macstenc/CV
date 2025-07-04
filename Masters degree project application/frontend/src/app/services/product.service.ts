import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products';

  constructor(private httpClient: HttpClient) {}

  // Pobierz wszystkie produkty z paginacją
  getProductListWithPagination(
    pageNo: number,
    pageSize: number
  ): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      `${this.baseUrl}/allWithPagination?pageNo=${pageNo}&pageSize=${pageSize}`
    );
  }

  // Pobierz produkty po kategorii
  getProductsByCategory(category: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      `${this.baseUrl}/productsbyCategory/${category}`
    );
  }
  getProductById(productId: string): Observable<Product> {
    return this.httpClient.get<Product>(`${this.baseUrl}/getById/${productId}`);
  }
  getProductsByName(name: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      `${this.baseUrl}/productsbyName/${name}`
    );
  }
  getMaxPriceByCategory(category: string): Observable<number> {
    return this.httpClient.get<number>(
      `${this.baseUrl}/maxPrice?category=${category}`
    );
  }
  getSimilarProducts(productId: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      `${this.baseUrl}/similarProducts/${productId}`
    );
  }

  getRelatedProducts(productId: string): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      `${this.baseUrl}/relatedProducts/${productId}`
    );
  }

  getProductsByCategoryWithSorting(
    category: string,
    sortBy: string,
    direction: string,
    pageNo: number,
    pageSize: number
  ): Observable<Product[]> {
    return this.httpClient.get<Product[]>(
      `${this.baseUrl}/productsByCategoryWithSorting?category=${category}&sortBy=${sortBy}&direction=${direction}&pageNo=${pageNo}&pageSize=${pageSize}`
    );
  }
  getProductsByCategoryWithFilters(
    category: string,
    minPrice: number | null,
    maxPrice: number | null,
    sortBy: string,
    direction: string,
    recommended: boolean
  ): Observable<Product[]> {
    let params = `category=${category}&sortBy=${sortBy}&direction=${direction}`;
    if (minPrice !== null) params += `&minPrice=${minPrice}`;
    if (maxPrice !== null) params += `&maxPrice=${maxPrice}`;
    if (recommended) params += `&recommended=true`;

    return this.httpClient.get<Product[]>(
      `${this.baseUrl}/productsByCategoryWithFilters?${params}`
    );
  }

  getRecommendedProducts(
    pageNo: number,
    pageSize: number
  ): Observable<Product[]> {
    return this.httpClient
      .get<Product[]>(
        `${this.baseUrl}/recommendedWithPagination?pageNo=${pageNo}&pageSize=${pageSize}`
      )
      .pipe(
        tap((products) => {
          console.log('Pobrano produkty z API:', products.length);
        })
      );
  }
  getMostViewedWithPagination(
    pageNo: number,
    pageSize: number
  ): Observable<Product[]> {
    return this.httpClient
      .get<Product[]>(
        `${this.baseUrl}/mostViewedWithPagination?pageNo=${pageNo}&pageSize=${pageSize}`
      )
      .pipe(
        tap((products) => {
          console.log('Pobrano produkty z API:', products.length);
        })
      );
  }
  getNewProducts(pageNo: number, pageSize: number): Observable<Product[]> {
    return this.httpClient
      .get<Product[]>(
        `${this.baseUrl}/newestWithPagination?pageNo=${pageNo}&pageSize=${pageSize}`
      )
      .pipe(
        tap((products) => {
          console.log('Pobrano produkty z API:', products.length);
        })
      );
  }
  getLastStocks(pageNo: number, pageSize: number): Observable<Product[]> {
    return this.httpClient
      .get<Product[]>(
        `${this.baseUrl}/lowStockWithPagination?pageNo=${pageNo}&pageSize=${pageSize}`
      )
      .pipe(
        tap((products) => {
          console.log('Pobrano produkty z API:', products.length);
        })
      );
  }
  getHighestDiscount(pageNo: number, pageSize: number): Observable<Product[]> {
    return this.httpClient
      .get<Product[]>(
        `${this.baseUrl}/highestDiscountWithPagination?pageNo=${pageNo}&pageSize=${pageSize}`
      )
      .pipe(
        tap((products) => {
          console.log('Pobrano produkty z API:', products.length);
        })
      );
  }
}
