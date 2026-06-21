// Exchange rate
const EXCHANGE_RATE = 4100;

// Product list
const products = [
{
id:1,
name:"Snow Blanc Set",
price:234,
icon:"🧴"
},
{
id:2,
name:"Plankton Jelly Set",
price:186,
icon:"🧼"
},
{
id:3,
name:"Aqua Snow Blanc",
price:192,
icon:"💧"
},
{
id:4,
name:"Snow Blanc Tube",
price:161,
icon:"🧪"
},
{
id:5,
name:"Essence Pack",
price:174,
icon:"☀️"
},
{
id:6,
name:"Eye Lift Treatment",
price:182,
icon:"👁️"
},
{
id:7,
name:"Collagen Peptide",
price:204,
icon:"🌸"
},
{
id:8,
name:"Radiance Serum",
price:198,
icon:"✨"
}
];

let cart = {};

// Start
window.onload = () => {
renderProducts();
updateCart();
};

// Create products
function renderProducts(){

const container =
document.getElementById("productsContainer");

container.innerHTML = "";

products.forEach(product=>{

container.innerHTML += `
<div class="product-card">

<div class="img-container">
${product.icon}
</div>

<h4>${product.name}</h4>

<div class="price-tag">
$${product.price}
</div>

<button
class="btn btn-select"
onclick="addToCart(${product.id})">

Add Package

</button>

</div>
`;

});

}

// Add cart
function addToCart(id){

if(cart[id]){
cart[id].qty++;
}
else{

const product =
products.find(p=>p.id===id);

cart[id]={
...product,
qty:1
};

}

updateCart();

}

// Remove cart
function removeItem(id){

delete cart[id];

updateCart();

}

// Update UI
function updateCart(){

const list =
document.getElementById("cartItemsList");

list.innerHTML="";

let totalUSD = 0;

const items = Object.values(cart);

if(items.length===0){

list.innerHTML =
`<p class="empty-msg">
No package selected yet.
</p>`;

}

items.forEach(item=>{

totalUSD += item.price * item.qty;

list.innerHTML += `

<div class="cart-row">

<span>
${item.name}
x${item.qty}
</span>

<span>

$${item.price*item.qty}

<i
class="fa-solid fa-trash"
style="cursor:pointer;color:red"
onclick="removeItem(${item.id})">
</i>

</span>

</div>

`;

});

document.getElementById("totalUSD")
.innerText = totalUSD.toFixed(2);

document.getElementById("totalKHR")
.innerText =
(totalUSD*EXCHANGE_RATE)
.toLocaleString();

}

// Google / Facebook login
function closeAuth(user){

document.getElementById("authModal")
.style.display = "none";

document.getElementById("userInfo")
.innerHTML = `
<i class="fa-solid fa-user"></i>
${user}
`;

}

// Phone login
function loginPhone(){

const phone =
document.getElementById("userPhone")
.value;

if(phone===""){
alert("Please enter phone number");
return;
}

closeAuth(phone);

}

// Order
function placeOrder(){

const address =
document.getElementById("deliveryLocation")
.value;

if(Object.keys(cart).length===0){

alert("Please add products first.");
return;

}

if(address===""){

alert("Please enter address.");
return;

}

alert(
"🎉 Order Confirmed!\n\nThank you for shopping with Glow&Co."
);

}