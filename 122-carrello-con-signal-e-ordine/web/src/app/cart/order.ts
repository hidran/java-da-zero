export interface OrderLine {
  productId: number;
  productName: string;
  unitPriceInCents: number;
  quantity: number;
  subtotalInCents: number;
}

export interface Order {
  id: number;
  status: string;
  lines: OrderLine[];
  totalInCents: number;
}
