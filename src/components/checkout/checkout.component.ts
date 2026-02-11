import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
// FIX: Import FormGroup for explicit typing of the form property.
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, ReactiveFormsModule, RouterLink]
})
export class CheckoutComponent {
  cartService = inject(CartService);
  router = inject(Router);

  cartItems = this.cartService.cartItems;
  totalPrice = this.cartService.totalPrice;

  checkoutForm: FormGroup;

  constructor() {
    // FIX: Inject FormBuilder and initialize the form inside the constructor
    // to resolve a dependency injection issue with class field initializers.
    const fb = inject(FormBuilder);
    this.checkoutForm = fb.group({
      creditCard: ['', [Validators.required, Validators.pattern('^[0-9]{16}$')]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      address: ['', Validators.required],
    });
  }

  isFieldInvalid(fieldName: string): boolean {
      const control = this.checkoutForm.get(fieldName);
      return !!control && control.invalid && (control.dirty || control.touched);
  }

  onSubmit(): void {
    if (this.checkoutForm.valid) {
      const orderSummary = {
        items: this.cartItems(),
        total: this.totalPrice(),
        customer: this.checkoutForm.value
      };

      // Mock processing order
      console.log('Order submitted:', orderSummary);
      
      this.cartService.clearCart();
      this.router.navigate(['/confirmation'], { state: { order: orderSummary } });
    } else {
        this.checkoutForm.markAllAsTouched();
    }
  }
}
