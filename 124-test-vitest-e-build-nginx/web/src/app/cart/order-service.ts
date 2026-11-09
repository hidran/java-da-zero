import { HttpClient } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { CartLine } from './cart-service';
import { Order } from './order';

@Service()
export class OrderService {
  private readonly http = inject(HttpClient);
  private readonly lastOrderSignal = signal<Order | undefined>(undefined);
  readonly lastOrder = this.lastOrderSignal.asReadonly();

  // Only ids and quantities travel:
  // the API takes names and prices from its own catalog.
  async place(lines: CartLine[]): Promise<Order> {
    const request = {
      lines: lines.map((line) => ({
        productId: line.productId,
        quantity: line.quantity,
      })),
    };
    const order = await firstValueFrom(
      this.http.post<Order>('/api/orders', request),
    );
    this.lastOrderSignal.set(order);
    return order;
  }
}
