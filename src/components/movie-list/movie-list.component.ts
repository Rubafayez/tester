import { ChangeDetectionStrategy, Component, inject, signal, computed, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MovieService } from '../../services/movie.service';
import { CartService } from '../../services/cart.service';
import { Movie, Genre } from '../../models/movie.model';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { switchMap, startWith } from 'rxjs/operators';
import { of } from 'rxjs';

interface MovieResponse {
  movies: Movie[];
  total: number;
}

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, RouterLink, FormsModule],
})
export class MovieListComponent {
  movieService = inject(MovieService);
  cartService = inject(CartService);

  // State Signals
  currentPage = signal(1);
  pageSize = signal(10);
  sortBy = signal<'title' | 'rating'>('rating');
  sortOrder = signal<'asc' | 'desc'>('desc');
  searchTitle = signal('');
  selectedGenre = signal('');
  selectedLetter = signal('');

  private genres$ = this.movieService.getGenres();
  genres = toSignal(this.genres$, { initialValue: [] });
  alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');

  private params = computed(() => ({
    page: this.currentPage(),
    limit: this.pageSize(),
    sortBy: this.sortBy(),
    sortOrder: this.sortOrder(),
    title: this.searchTitle(),
    genre: this.selectedGenre(),
    firstLetter: this.selectedLetter(),
  }));

  private movieResult$ = toObservable(this.params).pipe(
    switchMap(params => 
        this.movieService.getMovies(
          params.page, params.limit, params.sortBy, params.sortOrder,
          params.title, params.genre, params.firstLetter
        ).pipe(startWith(null))
    )
  );

  movieResult = toSignal(this.movieResult$, { initialValue: null as MovieResponse | null });
  
  movies = computed(() => this.movieResult()?.movies ?? []);
  totalMovies = computed(() => this.movieResult()?.total ?? 0);
  totalPages = computed(() => Math.ceil(this.totalMovies() / this.pageSize()));
  isLoading = computed(() => this.movieResult() === null);


  constructor() {
    // Reset page to 1 when filters change
    effect(() => {
      this.searchTitle();
      this.selectedGenre();
      this.selectedLetter();
      this.sortBy();
      this.sortOrder();
      this.currentPage.set(1);
    }, { allowSignalWrites: true });
  }

  addToCart(movie: Movie): void {
    this.cartService.addToCart(movie);
  }
  
  nextPage(): void {
    if (this.currentPage() < this.totalPages()) {
      this.currentPage.update(p => p + 1);
    }
  }

  previousPage(): void {
    if (this.currentPage() > 1) {
      this.currentPage.update(p => p - 1);
    }
  }

  goToPage(page: number): void {
    this.currentPage.set(page);
  }

  onSort(by: 'title' | 'rating'): void {
    if (this.sortBy() === by) {
        this.sortOrder.update(order => order === 'asc' ? 'desc' : 'asc');
    } else {
        this.sortBy.set(by);
        this.sortOrder.set('desc');
    }
  }
  
  onBrowseByLetter(letter: string) {
    this.selectedLetter.set(this.selectedLetter() === letter ? '' : letter);
    this.selectedGenre.set('');
    this.searchTitle.set('');
  }

  onBrowseByGenre(genreName: string) {
    this.selectedGenre.set(this.selectedGenre() === genreName ? '' : genreName);
    this.selectedLetter.set('');
    this.searchTitle.set('');
  }

  clearFilters(): void {
    this.searchTitle.set('');
    this.selectedGenre.set('');
    this.selectedLetter.set('');
  }
}
