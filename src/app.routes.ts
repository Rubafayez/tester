import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { MovieListComponent } from './components/movie-list/movie-list.component';
import { MovieDetailComponent } from './components/movie-detail/movie-detail.component';
import { StarDetailComponent } from './components/star-detail/star-detail.component';
import { CartComponent } from './components/cart/cart.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import { authGuard } from './guards/auth.guard';
import { NotFoundComponent } from './components/not-found/not-found.component';

export const APP_ROUTES: Routes = [
  { path: 'login', component: LoginComponent },
  { 
    path: 'movies', 
    component: MovieListComponent,
    canActivate: [authGuard] 
  },
  { 
    path: 'movie/:id', 
    component: MovieDetailComponent,
    canActivate: [authGuard]
  },
  { 
    path: 'star/:id', 
    component: StarDetailComponent,
    canActivate: [authGuard]
  },
  { 
    path: 'cart', 
    component: CartComponent,
    canActivate: [authGuard]
  },
  { 
    path: 'checkout', 
    component: CheckoutComponent,
    canActivate: [authGuard]
  },
  { 
    path: 'confirmation', 
    component: ConfirmationComponent,
    canActivate: [authGuard]
  },
  { path: '', redirectTo: '/movies', pathMatch: 'full' },
  { path: '**', component: NotFoundComponent },
];
