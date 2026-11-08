import { CurrencyPipe } from '@angular/common';
import { Component, computed, input, output } from '@angular/core';
import { Product } from '../product';

@Component({
  selector: 'app-product-card',
  imports: [CurrencyPipe],
  templateUrl: './product-card.html',
})
export class ProductCard {
  readonly product = input.required<Product>();
  readonly addToCart = output<Product>();
  protected readonly price = computed(() => this.product().priceInCents / 100);
}
