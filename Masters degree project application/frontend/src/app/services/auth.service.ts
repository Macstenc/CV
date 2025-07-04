import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  checkEmailExists(email: string): Observable<boolean> {
    const token = localStorage.getItem('token');

    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return this.http.get<boolean>(
      `${this.baseUrl}/users/check-email?email=${email}`,
      { headers: headers }
    );
  }

  private baseUrl = 'http://localhost:8080/api/auth';
  private apiUrl = `${this.baseUrl}`;
  private loginStatus = new BehaviorSubject<boolean>(this.hasToken());
  loginStatus$ = this.loginStatus.asObservable();

  constructor(private http: HttpClient) {}

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  login(username: string, password: string): Observable<{ token: string }> {
    return this.http
      .post<{ token: string }>(`${this.apiUrl}/login`, {
        username,
        password,
      })
      .pipe(
        tap((response) => {
          localStorage.setItem('token', response.token);
          this.loginStatus.next(true);
        })
      );
  }

  register(user: User): Observable<string> {
    user.id = null;
    return this.http.post<string>(`${this.apiUrl}/register`, user);
  }

  updateUser(user: User): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/users/update`, user, {
      headers: this.getAuthHeaders(),
      responseType: 'text' as 'json',
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    this.loginStatus.next(false);
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/users/me`, {
      headers: this.getAuthHeaders(),
    });
  }
}
