import { CurrencyPipe } from '@angular/common';
import { httpResource } from '@angular/common/http';
import { Component, computed, inject, input, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CartService } from '../../cart/cart-service';
import { problemDetailOf } from '../../problem-detail';
import { Product } from '../product';

@Component({
  selector: 'app-product-detail',
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './product-detail.html',
})
export class ProductDetail {
  // The :id of the route arrives as an input (withComponentInputBinding).
  readonly id = input.required<string>();

  private readonly cart = inject(CartService);
  protected readonly added = signal(false);

  protected readonly product = httpResource<Product>(
    () => `/api/products/${this.id()}`,
  );
  protected readonly errorMessage = computed(
    () => problemDetailOf(this.product.error()).detail,
  );

  protected addToCart(product: Product): void {
    this.cart.add(product);
    this.added.set(true);
  }
}
