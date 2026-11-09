import { TestBed } from '@angular/core/testing';
import { Product } from '../catalog/product';
import { CartService } from './cart-service';

const KEYBOARD: Product = {
  id: 1,
  name: 'Keyboard',
  description: 'Compact keyboard.',
  priceInCents: 4900,
  categoryId: 1,
  categoryName: 'Peripherals',
};
const MOUSE: Product = { ...KEYBOARD, id: 2, name: 'Mouse', priceInCents: 2500 };

describe('CartService', () => {
  let cart: CartService;

  beforeEach(() => {
    cart = TestBed.inject(CartService);
  });

  it('starts empty', () => {
    expect(cart.lines()).toEqual([]);
    expect(cart.count()).toBe(0);
    expect(cart.totalInCents()).toBe(0);
  });

  it('adds the same product twice as one line', () => {
    cart.add(KEYBOARD);
    cart.add(KEYBOARD);
    expect(cart.lines()).toEqual([
      { productId: 1, name: 'Keyboard', priceInCents: 4900, quantity: 2 },
    ]);
    expect(cart.count()).toBe(2);
  });

  it('computes the total in cents, with no rounding errors', () => {
    cart.add(KEYBOARD);
    cart.add(KEYBOARD);
    cart.add(MOUSE);
    expect(cart.totalInCents()).toBe(12300);
  });

  it('removes a line when its quantity drops to zero', () => {
    cart.add(KEYBOARD);
    cart.add(MOUSE);
    cart.changeQuantity(1, 0);
    expect(cart.lines().map((line) => line.name)).toEqual(['Mouse']);
  });

  it('never goes over 99 pieces, like the API', () => {
    cart.add(MOUSE);
    cart.changeQuantity(2, 150);
    expect(cart.lines()[0].quantity).toBe(99);
  });

  it('empties the cart after an order', () => {
    cart.add(KEYBOARD);
    cart.clear();
    expect(cart.count()).toBe(0);
  });
});
