import { CurrencyPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { OrderService } from '../order-service';

@Component({
  selector: 'app-order-confirmation',
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './order-confirmation.html',
})
export class OrderConfirmation {
  protected readonly order = inject(OrderService).lastOrder;
}
