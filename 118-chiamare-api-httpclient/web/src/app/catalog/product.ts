// The shapes of the JSON that the Shop API sends and receives
// (the DTOs of the api folder).
export interface Product {
  id: number;
  name: string;
  description: string;
  priceInCents: number;
  categoryId: number;
  categoryName: string;
}

export interface ProductPage {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  content: Product[];
}
