const products = [
  { name: "Laptop", category: "electronics", price: 999 },
  { name: "Shirt", category: "clothing", price: 25 },
  { name: "Headphones", category: "electronics", price: 199 },
  { name: "Jeans", category: "clothing", price: 40 },
  { name: "Smartphone", category: "electronics", price: 799 }
];

let currentFilter = "all";
let currentSort = "default";

document.getElementById('filter').addEventListener('change', (e) => {
  currentFilter = e.target.value;
  updateProducts();
});

document.getElementById('sort').addEventListener('change', (e) => {
  currentSort = e.target.value;
  updateProducts();
});

function updateProducts() {
  let filtered = currentFilter === 'all'
    ? [...products]
    : products.filter(p => p.category === currentFilter);

  if (currentSort === "low-high") {
    filtered.sort((a, b) => a.price - b.price);
  } else if (currentSort === "high-low") {
    filtered.sort((a, b) => b.price - a.price);
  }

  renderProducts(filtered);
}

function renderProducts(products) {
  const container = document.getElementById('products-container');
  container.innerHTML = products.map(p => `
    <div class="product">
      <h3>${p.name}</h3>
      <p>$${p.price}</p>
    </div>
  `).join('');
}

// Initial render
updateProducts();
