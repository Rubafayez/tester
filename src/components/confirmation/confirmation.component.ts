import { ChangeDetectionStrategy, Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, RouterLink]
})
export class ConfirmationComponent implements OnInit {
  router = inject(Router);
  
  order = signal<any | null>(null);

  ngOnInit(): void {
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as { order: any };
    if (state?.order) {
      this.order.set(state.order);
    } else {
      // If no state, maybe redirect or show an error
      this.router.navigate(['/movies']);
    }
  }
}
