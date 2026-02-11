import { Injectable, signal, computed } from '@angular/core';
import { CartItem } from '../models/cart.model';
import { Movie } from '../models/movie.model';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  cartItems = signal<CartItem[]>([]);

  // Movie price is mocked as rating * 2
  private readonly MOVIE_PRICE_MULTIPLIER = 2;

  itemCount = computed(() =>
    this.cartItems().reduce((acc, item) => acc + item.quantity, 0)
  );

  totalPrice = computed(() =>
    this.cartItems().reduce(
      (acc, item) => acc + item.movie.rating * this.MOVIE_PRICE_MULTIPLIER * item.quantity,
      0
    )
  );

  addToCart(movie: Movie): void {
    this.cartItems.update((items) => {
      const existingItem = items.find((item) => item.movie.id === movie.id);
      if (existingItem) {
        return items.map((item) =>
          item.movie.id === movie.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }
      return [...items, { movie, quantity: 1 }];
    });
  }

  updateQuantity(movieId: string, quantity: number): void {
    this.cartItems.update((items) =>
      items
        .map((item) =>
          item.movie.id === movieId ? { ...item, quantity } : item
        )
        .filter((item) => item.quantity > 0)
    );
  }

  removeFromCart(movieId: string): void {
    this.cartItems.update((items) =>
      items.filter((item) => item.movie.id !== movieId)
    );
  }

  clearCart(): void {
    this.cartItems.set([]);
  }
}
