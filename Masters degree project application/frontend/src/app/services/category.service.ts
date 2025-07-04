import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private baseUrl = 'http://localhost:8080/api/category/all';

  constructor(private httpClient: HttpClient) {}

  getCategory(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.baseUrl);
  }
}
