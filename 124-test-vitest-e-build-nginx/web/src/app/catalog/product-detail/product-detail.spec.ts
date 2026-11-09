import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { CartService } from '../../cart/cart-service';
import { Product } from '../product';
import { ProductDetail } from './product-detail';

const MONITOR: Product = {
  id: 3,
  name: 'Monitor',
  description: '27 inch monitor.',
  priceInCents: 18900,
  categoryId: 2,
  categoryName: 'Displays',
};

describe('ProductDetail', () => {
  let backend: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
      ],
    });
    backend = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    backend.verify();
  });

  it('loads the product of the route id and adds it to the cart', async () => {
    const fixture = TestBed.createComponent(ProductDetail);
    fixture.componentRef.setInput('id', '3');
    TestBed.tick();
    backend.expectOne('/api/products/3').flush(MONITOR);
    await fixture.whenStable();
    const element = fixture.nativeElement as HTMLElement;
    expect(element.querySelector('h1')?.textContent).toContain('Monitor');
    expect(element.textContent).toContain('€189.00');
    element.querySelector('button')?.click();
    expect(TestBed.inject(CartService).count()).toBe(1);
  });

  it('shows the detail of the ProblemDetail of a missing product', async () => {
    const fixture = TestBed.createComponent(ProductDetail);
    fixture.componentRef.setInput('id', '99');
    TestBed.tick();
    backend
      .expectOne('/api/products/99')
      .flush(
        {
          title: 'Product not found',
          status: 404,
          detail: 'Product 99 does not exist.',
        },
        { status: 404, statusText: 'Not Found' },
      );
    await fixture.whenStable();
    const alert = (fixture.nativeElement as HTMLElement).querySelector(
      '[role="alert"]',
    );
    expect(alert?.textContent).toContain('Product 99 does not exist.');
  });
});
