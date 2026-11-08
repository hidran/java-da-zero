import { Routes } from '@angular/router';
import { CatalogPage } from './catalog/catalog-page/catalog-page';

export const routes: Routes = [
  { path: '', component: CatalogPage, title: 'Catalog | Shop' },
  {
    path: 'products/:id',
    loadComponent: () =>
      import('./catalog/product-detail/product-detail').then((m) => m.ProductDetail),
    title: 'Product | Shop',
  },
  {
    path: 'cart',
    loadComponent: () =>
      import('./cart/cart-page/cart-page').then((m) => m.CartPage),
    title: 'Cart | Shop',
  },
  {
    path: 'order-confirmation',
    loadComponent: () =>
      import('./cart/order-confirmation/order-confirmation').then(
        (m) => m.OrderConfirmation,
      ),
    title: 'Order received | Shop',
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./auth/login-page/login-page').then((m) => m.LoginPage),
    title: 'Log in | Shop',
  },
  {
    path: '**',
    loadComponent: () => import('./not-found/not-found').then((m) => m.NotFound),
    title: 'Page not found | Shop',
  },
];
