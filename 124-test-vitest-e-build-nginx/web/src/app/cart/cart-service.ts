import { computed, Service, signal } from '@angular/core';
import { Product } from '../catalog/product';

export interface CartLine {
  productId: number;
  name: string;
  priceInCents: number;
  quantity: number;
}

// The API accepts at most 99 pieces per line.
const MAX_QUANTITY = 99;

@Service()
export class CartService {
  private readonly linesSignal = signal<CartLine[]>([]);
  readonly lines = this.linesSignal.asReadonly();
  readonly count = computed(() =>
    this.lines().reduce((sum, line) => sum + line.quantity, 0),
  );
  readonly totalInCents = computed(() =>
    this.lines().reduce((sum, line) => sum + line.priceInCents * line.quantity, 0),
  );

  add(product: Product): void {
    const line = this.lines().find((item) => item.productId === product.id);
    if (line) {
      this.changeQuantity(product.id, line.quantity + 1);
      return;
    }
    const newLine = {
      productId: product.id,
      name: product.name,
      priceInCents: product.priceInCents,
      quantity: 1,
    };
    this.linesSignal.update((lines) => [...lines, newLine]);
  }

  changeQuantity(productId: number, quantity: number): void {
    if (quantity < 1) {
      this.remove(productId);
      return;
    }
    const capped = Math.min(quantity, MAX_QUANTITY);
    this.linesSignal.update((lines) =>
      lines.map((line) =>
        line.productId === productId ? { ...line, quantity: capped } : line,
      ),
    );
  }

  remove(productId: number): void {
    this.linesSignal.update((lines) =>
      lines.filter((line) => line.productId !== productId),
    );
  }

  clear(): void {
    this.linesSignal.set([]);
  }
}
