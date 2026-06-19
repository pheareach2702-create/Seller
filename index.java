// Base exchange rate standard for Khmer Riel mapping
const EXCHANGE_RATE = 4100; 

// Simulated dynamic catalog from your image data items
const productsData = [
    { id: 1, name: "Snow Blanc Brightening Set", price: 234, icon: "🧴" },
    { id: 2, name: "Plankton Renewal Jelly Set", price: 186, icon: "🧼" },
    { id: 3, name: "Aqua Snow Blanc Fresh Set", price: 192, icon: "💧" },
    { id: 4, name: "Snow Blanc Cleansing Tubes", price: 161, icon: "🧪" },
    { id: 5, name: "Snow Blanc Essence Pack", price: 174, icon: "☀️" },
    { id: 6, name: "Gluta Eye Lift Treatment Set", price: 182, icon: "👁️" },
    { id: 7, name: "Glogen Collagen Peptide Set", price: 204, icon: "🌸" },
    { id: 8, name: "Double Lock Radiance Serum", price: 198, icon: "🧪" },
    { id: 9, name: "Double Lock Miracle Drops", price: 192, icon: "✨" },
    { id: 10, name: "Glogen Soft Moisture Cream", price: 174, icon: "🎀" }
];

let selectedCart = {};

// Handle Initial Page Render Layout
window.onload = function() {
    renderProducts();
};

function renderProducts() {
    const container = document.getElementById('productsContainer');
    container.innerHTML = '';
    
    productsData.forEach(prod => {
        const card = document.createElement('div');
        card.className = 'product-card';
        card.innerHTML = `
            <div class="img-container">${prod.icon}</div>
            <h4>${prod.name}</h4>
            <div class="price-tag">$${prod.price}</div>
            <button class="btn btn-select" onclick="addToCart(${prod.id})">Add Package</button>
        `;
        container.appendChild(card);
    });
}

// Authentication Interception Closer
function closeAuth(method) {
    let identifier = "";
    if(method === 'Phone') {
        const phoneVal = document.getElementById('userPhone').value;
        if(!phoneVal) { alert("Please input a valid phone number!"); return; }
        identifier = phoneVal;
    } else {
        identifier = method + " User";
    }
    
    document.getElementById('authModal').style.display = 'none';
    document.getElementById('userInfo').innerHTML = `<i class="fa-solid fa-circle-user"></i> ${identifier}`;
}

// Shopping Cart Mechanics
function addToCart(productId) {
    const product = productsData.find(p => p.id === productId);
    if (selectedCart[productId]) {
        selectedCart[productId].qty += 1;
    } else {
        selectedCart[productId] = { ...product, qty: 1 };
    }
    updateCartUI();
}

function removeFromCart(productId) {
    delete selectedCart[productId];
    updateCartUI();
}

function updateCartUI() {
    const listElement = document.getElementById('cartItemsList');
    listElement.innerHTML = '';
    
    let subtotalUSD = 0;
    const itemsArray = Object.values(selectedCart);

    if(itemsArray.length === 0) {
        listElement.innerHTML = '<p class="empty-msg">No package selected yet.</p>';
    } else {
        itemsArray.forEach(item => {
            subtotalUSD += (item.price * item.qty);
            const row = document.createElement('div');
            row.className = 'cart-row';
            row.innerHTML = `
                <span>${item.name} <b>x${item.qty}</b></span>
                <span>$${item.price * item.qty} <i class="fa-solid fa-trash-can remove-item" onclick="removeFromCart(${item.id})"></i></span>
            `;
            listElement.appendChild(row);
        });
    }

    // Mathematical conversions to Khmer Riel Currency
    let totalKHR = subtotalUSD * EXCHANGE_RATE;

    document.getElementById('totalUSD').innerText = subtotalUSD.toFixed(2);
    // Format KHR with commas for clean local display
    document.getElementById('totalKHR').innerText = totalKHR.toLocaleString();
}

// Finalization and submission validation logic
function placeOrder() {
    const usdAmount = document.getElementById('totalUSD').innerText;
    const khrAmount = document.getElementById('totalKHR').innerText;
    const locationInput = document.getElementById('deliveryLocation').value.trim();

    if(parseFloat(usdAmount) <= 0) {
        alert("Your order summary is empty! Select at least one product package.");
        return;
    }
    if(!locationInput) {
        alert("Please specify a valid delivery address or drop a location coordinate before scanning!");
        return;
    }

    alert(`🎉 Order Confirmed!\n\nTotal Due: $${usdAmount} (${khrAmount} KHR)\nDelivery Area: "${locationInput}"\n\nYour merchant has received the invoice. Thank you for your payment!`);
}