import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { switchMap, tap } from 'rxjs/operators';
import { MovieService } from '../../services/movie.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-star-detail',
  templateUrl: './star-detail.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, RouterLink]
})
export class StarDetailComponent {
  route = inject(ActivatedRoute);
  movieService = inject(MovieService);
  
  private starId$ = this.route.paramMap.pipe(
      switchMap(params => of(params.get('id')))
  );

  star = toSignal(
      this.starId$.pipe(
          switchMap(id => id ? this.movieService.getStarById(id) : of(undefined))
      )
  );
  
  movies = toSignal(
      this.starId$.pipe(
          switchMap(id => id ? this.movieService.getMoviesByStarId(id) : of([]))
      )
  );
}
