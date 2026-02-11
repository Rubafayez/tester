import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, RouterLink]
})
export class CartComponent {
  cartService = inject(CartService);

  // Expose for template
  cartItems = this.cartService.cartItems;
  totalPrice = this.cartService.totalPrice;

  updateQuantity(movieId: string, event: Event): void {
    const quantity = parseInt((event.target as HTMLInputElement).value, 10);
    if (!isNaN(quantity)) {
      this.cartService.updateQuantity(movieId, quantity);
    }
  }

  removeFromCart(movieId: string): void {
    this.cartService.removeFromCart(movieId);
  }
}
