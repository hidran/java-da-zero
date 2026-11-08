import { CurrencyPipe } from '@angular/common';
import { httpResource } from '@angular/common/http';
import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';
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

  protected readonly product = httpResource<Product>(
    () => `/api/products/${this.id()}`,
  );
  protected readonly errorMessage = computed(
    () => problemDetailOf(this.product.error()).detail,
  );
}
