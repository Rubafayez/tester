import { ChangeDetectionStrategy, Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { switchMap } from 'rxjs/operators';
import { MovieService } from '../../services/movie.service';
import { CartService } from '../../services/cart.service';
import { Movie } from '../../models/movie.model';
import { of } from 'rxjs';

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, RouterLink]
})
export class MovieDetailComponent {
  route = inject(ActivatedRoute);
  movieService = inject(MovieService);
  cartService = inject(CartService);

  private movie$ = this.route.paramMap.pipe(
    switchMap(params => {
      const id = params.get('id');
      return id ? this.movieService.getMovieById(id) : of(undefined);
    })
  );

  movie = toSignal(this.movie$);

  addToCart(movie: Movie) {
    this.cartService.addToCart(movie);
  }
}
