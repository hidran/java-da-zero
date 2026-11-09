import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Product, ProductRequest } from './product';

// Reads go through httpResource in the components (lesson 118);
// the change goes through HttpClient here.
@Service()
export class ProductService {
  private readonly http = inject(HttpClient);

  create(product: ProductRequest): Promise<Product> {
    return firstValueFrom(this.http.post<Product>('/api/admin/products', product));
  }
}
