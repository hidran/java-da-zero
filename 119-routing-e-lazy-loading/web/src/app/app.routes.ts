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
    path: '**',
    loadComponent: () => import('./not-found/not-found').then((m) => m.NotFound),
    title: 'Page not found | Shop',
  },
];
