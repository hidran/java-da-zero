import { CurrencyPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { problemDetailOf } from '../../problem-detail';
import { CartService } from '../cart-service';
import { OrderService } from '../order-service';

@Component({
  selector: 'app-cart-page',
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './cart-page.html',
})
export class CartPage {
  protected readonly cart = inject(CartService);
  private readonly orders = inject(OrderService);
  private readonly router = inject(Router);
  protected readonly placing = signal(false);
  protected readonly errorMessage = signal('');

  protected async placeOrder(): Promise<void> {
    this.placing.set(true);
    this.errorMessage.set('');
    try {
      await this.orders.place(this.cart.lines());
      this.cart.clear();
      await this.router.navigateByUrl('/order-confirmation');
    } catch (error) {
      this.errorMessage.set(problemDetailOf(error).detail ?? 'The order failed.');
    } finally {
      this.placing.set(false);
    }
  }
}
