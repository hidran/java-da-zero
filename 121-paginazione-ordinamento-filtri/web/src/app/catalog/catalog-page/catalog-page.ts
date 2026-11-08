import { httpResource } from '@angular/common/http';
import {
  Component,
  computed,
  DestroyRef,
  inject,
  input,
  signal,
} from '@angular/core';
import { Params, Router } from '@angular/router';
import { problemDetailOf } from '../../problem-detail';
import { Category, Product, ProductPage } from '../product';
import { ProductCard } from '../product-card/product-card';

const PAGE_SIZE = 12;
const SEARCH_DELAY_MS = 300;

@Component({
  selector: 'app-catalog-page',
  imports: [ProductCard],
  templateUrl: './catalog-page.html',
})
export class CatalogPage {
  // The query parameters of the URL arrive as inputs (withComponentInputBinding).
  readonly name = input<string>();
  readonly categoryId = input<string>();
  readonly sort = input<string>();
  readonly page = input<string>();

  private readonly router = inject(Router);
  private searchTimer?: number;

  protected readonly sorts = [
    { value: 'ID', label: 'Featured' },
    { value: 'NAME', label: 'Name' },
    { value: 'PRICE', label: 'Price: low to high' },
  ];
  protected readonly currentPage = computed(() => Number(this.page() ?? 0));
  protected readonly currentSort = computed(() => this.sort() ?? 'ID');
  protected readonly currentCategoryId = computed(() =>
    Number(this.categoryId() ?? 0),
  );

  protected readonly categories = httpResource<Category[]>(() => '/api/categories');
  // Empty filters stay out of the request: the API then uses its defaults.
  protected readonly products = httpResource<ProductPage>(() => {
    const params: Record<string, string | number> = {
      sort: this.currentSort(),
      page: this.currentPage(),
      size: PAGE_SIZE,
    };
    const name = this.name();
    if (name) {
      params['name'] = name;
    }
    const categoryId = this.categoryId();
    if (categoryId) {
      params['categoryId'] = categoryId;
    }
    return { url: '/api/products', params };
  });
  protected readonly errorMessage = computed(
    () => problemDetailOf(this.products.error()).detail,
  );
  protected readonly lastAdded = signal('');

  constructor() {
    inject(DestroyRef).onDestroy(() => window.clearTimeout(this.searchTimer));
  }

  protected add(product: Product): void {
    this.lastAdded.set(product.name);
  }

  // Debounce: the URL changes only after the user stops typing for a moment.
  protected search(text: string): void {
    window.clearTimeout(this.searchTimer);
    this.searchTimer = window.setTimeout(
      () => this.updateQuery({ name: text || null, page: null }),
      SEARCH_DELAY_MS,
    );
  }

  protected chooseCategory(categoryId: string): void {
    this.updateQuery({ categoryId: categoryId || null, page: null });
  }

  protected chooseSort(sort: string): void {
    this.updateQuery({ sort: sort === 'ID' ? null : sort, page: null });
  }

  protected goToPage(page: number): void {
    this.updateQuery({ page: page === 0 ? null : page });
  }

  // A null value removes the parameter, so the URL stays short.
  private updateQuery(changes: Params): void {
    this.router.navigate([], { queryParams: changes, queryParamsHandling: 'merge' });
  }
}
