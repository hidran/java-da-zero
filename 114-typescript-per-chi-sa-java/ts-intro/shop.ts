interface Product {
  id: number;
  name: string;
  priceInCents: number;
  description?: string;
}

type Sort = 'NAME' | 'PRICE';

const keyboard: Product = {
  id: 1,
  name: 'Keyboard',
  priceInCents: 4900,
  description: 'Compact keyboard with quiet keys.',
};
const cable: Product = { id: 2, name: 'HDMI cable', priceInCents: 1200 };

const euro = (cents: number): string => `${(cents / 100).toFixed(2)} euro`;
console.log(keyboard.description?.toUpperCase());
console.log(cable.description ?? 'No description');

const onSale = { ...keyboard, priceInCents: 3900 };
const { name, priceInCents } = onSale;
console.log(`${name} on sale: ${euro(priceInCents)}`);

function sortProducts(products: Product[], sort: Sort): Product[] {
  if (sort === 'NAME') {
    return products.toSorted((a, b) => a.name.localeCompare(b.name));
  }
  return products.toSorted((a, b) => a.priceInCents - b.priceInCents);
}

const products = [keyboard, cable, onSale];
for (const product of sortProducts(products, 'PRICE')) {
  console.log(`${product.name}: ${euro(product.priceInCents)}`);
}
