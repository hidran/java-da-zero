import { Component, signal } from '@angular/core';
import { Product } from '../product';
import { ProductCard } from '../product-card/product-card';

@Component({
  selector: 'app-catalog-page',
  imports: [ProductCard],
  templateUrl: './catalog-page.html',
})
export class CatalogPage {
  // Sample data: the first three products of the Shop seed.
  protected readonly products = signal<Product[]>([
    {
      id: 1,
      name: 'Keyboard',
      description: 'Compact keyboard with quiet keys and a detachable cable.',
      priceInCents: 4900,
      categoryId: 1,
      categoryName: 'Peripherals',
    },
    {
      id: 2,
      name: 'Mouse',
      description: 'Wireless mouse with a silent click and a two-year battery.',
      priceInCents: 2500,
      categoryId: 1,
      categoryName: 'Peripherals',
    },
    {
      id: 3,
      name: 'Monitor',
      description: '27 inch monitor with a sharp picture and thin borders.',
      priceInCents: 18900,
      categoryId: 2,
      categoryName: 'Displays',
    },
  ]);
  protected readonly lastAdded = signal('');

  protected add(product: Product): void {
    this.lastAdded.set(product.name);
  }
}
