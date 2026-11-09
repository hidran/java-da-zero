import { httpResource } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import {
  FieldTree,
  form,
  FormField,
  FormRoot,
  maxLength,
  min,
  required,
  TreeValidationResult,
} from '@angular/forms/signals';
import { Router } from '@angular/router';
import { Category, ProductRequest } from '../../catalog/product';
import { ProductService } from '../../catalog/product-service';
import { FieldError } from '../../field-error/field-error';
import { problemDetailOf } from '../../problem-detail';

interface ProductFormValue {
  name: string;
  description: string;
  price: number;
  categoryId: string;
}

@Component({
  selector: 'app-product-form',
  imports: [FieldError, FormField, FormRoot],
  templateUrl: './product-form.html',
})
export class ProductForm {
  private readonly products = inject(ProductService);
  private readonly router = inject(Router);

  protected readonly categories = httpResource<Category[]>(() => '/api/categories');
  protected readonly model = signal<ProductFormValue>({
    name: '',
    description: '',
    price: 0,
    categoryId: '',
  });

  // The same rules as the Bean Validation annotations of ProductRequest in the API.
  protected readonly productForm = form(
    this.model,
    (path) => {
      required(path.name, { message: 'Name is required' });
      maxLength(path.name, 100, { message: 'Name can be at most 100 characters' });
      required(path.description, { message: 'Description is required' });
      maxLength(path.description, 500, {
        message: 'Description can be at most 500 characters',
      });
      required(path.price, { message: 'Price is required' });
      min(path.price, 0.01, { message: 'Price must be greater than zero' });
      required(path.categoryId, { message: 'Choose a category' });
    },
    { submission: { action: () => this.save() } },
  );

  private async save(): Promise<TreeValidationResult> {
    const value = this.model();
    const request: ProductRequest = {
      name: value.name,
      description: value.description,
      priceInCents: Math.round(value.price * 100),
      categoryId: Number(value.categoryId),
    };
    try {
      const saved = await this.products.create(request);
      await this.router.navigate(['/products', saved.id]);
      return undefined;
    } catch (error) {
      return this.serverErrors(error);
    }
  }

  // Each entry of the ProblemDetail "errors" map goes back to its own field.
  private serverErrors(error: unknown): TreeValidationResult {
    const problem = problemDetailOf(error);
    const fields: Record<string, FieldTree<unknown>> = {
      name: this.productForm.name,
      description: this.productForm.description,
      priceInCents: this.productForm.price,
      categoryId: this.productForm.categoryId,
    };
    const fieldErrors = Object.entries(problem.errors ?? {}).map(
      ([field, message]) => ({
        kind: 'server',
        message,
        fieldTree: fields[field] ?? this.productForm,
      }),
    );
    if (fieldErrors.length > 0) {
      return fieldErrors;
    }
    return { kind: 'server', message: problem.detail ?? 'Saving failed.' };
  }
}
