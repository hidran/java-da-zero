interface Order {
  id: number;
  totalInCents: number;
}
let nextId = 1001;

function placeOrder(totalInCents: number): Promise<Order> {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (totalInCents > 0) {
        resolve({ id: nextId++, totalInCents });
      } else {
        reject(new Error('The cart is empty'));
      }
    }, 500);
  });
}

placeOrder(4900).then((order) => console.log(`then: order ${order.id}`));
console.log('The order is on its way');

async function checkout(totalInCents: number): Promise<void> {
  try {
    const order = await placeOrder(totalInCents);
    console.log(`await: order ${order.id}, ${order.totalInCents} cents`);
  } catch (error) {
    console.log(`await: ${error}`);
  }
}

await checkout(7400);
await checkout(0);
