import { CommonModule } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  HostListener,
  OnInit,
  Output,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';
import { AuthService } from '../../services/auth.service';
import { SearchResultsComponent } from '../search-result/search-result.component';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { NzIconModule } from 'ng-zorro-antd/icon';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzButtonModule,
    NzIconModule,
    RouterModule,
    NzInputModule,
    NzCardModule,
    NzFormModule,
    NzNotificationModule,
    SearchResultsComponent,
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  @Output() categorySelected = new EventEmitter<string>();
  @Output() searchEvent = new EventEmitter<string>();

  isSearchActive = false;
  categories: Category[] = [];
  isNavbarExpanded = false;
  isLoggedIn: boolean = false;
  searchTerm: string = '';

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  loginStatus$!: Observable<boolean>;

  ngOnInit(): void {
    this.loadCategories();
    this.loginStatus$ = this.authService.loginStatus$;
    this.authService.loginStatus$.subscribe((status) => {
      this.isLoggedIn = status;
      this.cdr.detectChanges(); // Trigger change detection for the login status
    });
  }

  toggleNavbar(expand: boolean) {
    this.isNavbarExpanded = expand;
  }

  onCategoriesListMouseLeave() {
    this.isNavbarExpanded = false;
  }

  onNavbarMouseLeave() {
    this.isNavbarExpanded = false;
  }
  gotoUserPanel(): void {
    this.router.navigate(['/user-panel']); // Przykładowa ścieżka dla panelu użytkownika
  }

  gotoOrders(): void {
    this.router.navigate(['/orders']); // Przykładowa ścieżka dla zamówień
  }

  @HostListener('document:click', ['$event.target'])
  onClickOutside(target: HTMLElement): void {
    const searchContainer = document.querySelector('.search-container');
    if (searchContainer && !searchContainer.contains(target)) {
      this.isSearchActive = false;
    }
  }

  toggleSearch(active: boolean): void {
    this.isSearchActive = active || this.searchTerm.length > 0;
  }

  onSearchInput(): void {
    this.toggleSearch(true); // Show search results if there is input
    this.searchEvent.emit(this.searchTerm.trim()); // Emit the trimmed search term
  }

  loadCategories(): void {
    this.categoryService.getCategory().subscribe((data) => {
      this.categories = data;
    });
  }

  selectCategory(category: string): void {
    this.categorySelected.emit(category); // Emituje nazwę kategorii
    this.isNavbarExpanded = false;
  }

  searchProducts(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    this.searchEvent.emit(inputElement.value.trim()); // Emit search term
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  gotoShopingCart() {
    this.router.navigate(['/cart']);
  }
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  createAccount(): void {
    this.router.navigate(['/register']);
  }
}
