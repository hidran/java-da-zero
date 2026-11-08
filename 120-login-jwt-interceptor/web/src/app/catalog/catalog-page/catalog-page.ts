import { httpResource } from '@angular/common/http';
import { Component, computed, signal } from '@angular/core';
import { problemDetailOf } from '../../problem-detail';
import { Product, ProductPage } from '../product';
import { ProductCard } from '../product-card/product-card';

@Component({
  selector: 'app-catalog-page',
  imports: [ProductCard],
  templateUrl: './catalog-page.html',
})
export class CatalogPage {
  protected readonly products = httpResource<ProductPage>(() => '/api/products');
  protected readonly errorMessage = computed(
    () => problemDetailOf(this.products.error()).detail,
  );
  protected readonly lastAdded = signal('');

  protected add(product: Product): void {
    this.lastAdded.set(product.name);
  }
}
