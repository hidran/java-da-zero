import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { ProductForm } from './product-form';

describe('ProductForm', () => {
  let backend: HttpTestingController;
  let fixture: ComponentFixture<ProductForm>;
  let element: HTMLElement;

  function type(selector: string, value: string): void {
    const field = element.querySelector<HTMLInputElement | HTMLSelectElement>(
      selector,
    );
    if (!field) {
      throw new Error(`No field ${selector}`);
    }
    field.value = value;
    field.dispatchEvent(new Event('input'));
    field.dispatchEvent(new Event('change'));
  }

  beforeEach(async () => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
      ],
    });
    backend = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(ProductForm);
    element = fixture.nativeElement as HTMLElement;
    TestBed.tick();
    backend.expectOne('/api/categories').flush([{ id: 4, name: 'Desk' }]);
    await fixture.whenStable();
  });

  afterEach(() => {
    backend.verify();
  });

  it('shows the same messages as Bean Validation and sends nothing', async () => {
    element.querySelector('form')?.dispatchEvent(new Event('submit'));
    await fixture.whenStable();
    expect(element.textContent).toContain('Name is required');
    expect(element.textContent).toContain('Description is required');
    expect(element.textContent).toContain('Price must be greater than zero');
    expect(element.textContent).toContain('Choose a category');
    backend.expectNone('/api/admin/products');
  });

  it('puts the field errors of the server under their fields', async () => {
    type('input', 'Keyboard');
    type('textarea', 'Same name as an existing product.');
    type('input[type="number"]', '49');
    type('select', '4');
    element.querySelector('form')?.dispatchEvent(new Event('submit'));
    const request = await vi.waitFor(() => backend.expectOne('/api/admin/products'));
    expect(request.request.body).toEqual({
      name: 'Keyboard',
      description: 'Same name as an existing product.',
      priceInCents: 4900,
      categoryId: 4,
    });
    request.flush(
      {
        status: 400,
        detail: 'One or more fields are invalid.',
        errors: { name: 'A product named Keyboard already exists.' },
      },
      { status: 400, statusText: 'Bad Request' },
    );
    const nameLabel = element.querySelector('label');
    await vi.waitFor(() =>
      expect(nameLabel?.textContent).toContain(
        'A product named Keyboard already exists.',
      ),
    );
  });
});
