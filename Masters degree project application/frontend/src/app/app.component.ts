import { Component, ViewChild } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { FooterComponent } from './components/footer/footer.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SearchResultsComponent } from './components/search-result/search-result.component'; // Importowanie komponentu SearchResultsComponent
import { debounceTime, Subject } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    SidebarComponent,
    FooterComponent,
    NavbarComponent,
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatProgressSpinnerModule,
    HomeComponent,
    SearchResultsComponent, // Dodanie komponentu do imports
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  navigateToCategory(categoryName: string): void {
    this.router.navigate(['/category', categoryName]);
  }
  @ViewChild('homeComponent') homeComponent!: HomeComponent;
  searchTerm: string = ''; // Term wyszukiwania
  searchTermSubject: Subject<string> = new Subject<string>(); // Subject dla debouncing

  constructor(private router: Router) {
    // Wykonaj wyszukiwanie po 500ms od ostatniej zmiany
    this.searchTermSubject.pipe(debounceTime(500)).subscribe((searchTerm) => {
      this.onSearch(searchTerm);
    });
  }

  title = 'sklep-IT';

  onCategorySelected(category: string): void {
    this.router.navigate(['/category', category]);
  }

  onSearch(searchTerm: string): void {
    this.searchTerm = searchTerm;
    // Możesz dodać wywołanie API lub logikę wyszukiwania tutaj
  }

  // Wywołanie onSearch z opóźnieniem (debounce)
  onSearchInputChange(searchTerm: string): void {
    this.searchTermSubject.next(searchTerm); // Przesyłamy nowy searchTerm do Subject
  }
}
