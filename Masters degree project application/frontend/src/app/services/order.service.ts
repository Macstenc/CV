import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from '../models/order';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private baseUrl = 'http://localhost:8080/api/orders';

  constructor(private httpClient: HttpClient) {}

  createOrder(order: Order): Observable<Order> {
    return this.httpClient.post<Order>(`${this.baseUrl}`, order);
  }

  getOrderById(orderId: string): Observable<Order> {
    return this.httpClient.get<Order>(`${this.baseUrl}/${orderId}`);
  }

  getAllOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.baseUrl}`);
  }

  updateOrderStatus(orderId: string, status: string): Observable<Order> {
    return this.httpClient.put<Order>(`${this.baseUrl}/${orderId}`, { status });
  }
  getOrdersByUserEmail(email: string): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.baseUrl}/user/${email}`);
  }
}
