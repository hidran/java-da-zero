import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { Product } from '../product';
import { ProductCard } from './product-card';

const KEYBOARD: Product = {
  id: 1,
  name: 'Keyboard',
  description: 'Compact keyboard.',
  priceInCents: 4900,
  categoryId: 1,
  categoryName: 'Peripherals',
};

describe('ProductCard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [provideRouter([])] });
  });

  it('shows name, category and the price in euro', async () => {
    const fixture = TestBed.createComponent(ProductCard);
    fixture.componentRef.setInput('product', KEYBOARD);
    await fixture.whenStable();
    const text = (fixture.nativeElement as HTMLElement).textContent;
    expect(text).toContain('Keyboard');
    expect(text).toContain('Peripherals');
    expect(text).toContain('€49.00');
  });

  it('links to the detail page of the product', async () => {
    const fixture = TestBed.createComponent(ProductCard);
    fixture.componentRef.setInput('product', KEYBOARD);
    await fixture.whenStable();
    const link = (fixture.nativeElement as HTMLElement).querySelector('a');
    expect(link?.getAttribute('href')).toBe('/products/1');
  });

  it('emits the product when the button is clicked', async () => {
    const fixture = TestBed.createComponent(ProductCard);
    fixture.componentRef.setInput('product', KEYBOARD);
    const added = vi.fn();
    fixture.componentInstance.addToCart.subscribe(added);
    await fixture.whenStable();
    (fixture.nativeElement as HTMLElement).querySelector('button')?.click();
    expect(added).toHaveBeenCalledWith(KEYBOARD);
  });
});
