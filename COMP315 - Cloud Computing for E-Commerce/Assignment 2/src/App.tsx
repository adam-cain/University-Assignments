import { useState, useEffect } from 'react'
import { ProductList } from './Components/ProductList'
import itemList from './Assets/random_products_175.json';
import './e-commerce-stylesheet.css'

type Product = {
  id: number
  name: string
  price: number
  category: string
  quantity: number
  rating: number
  image_link: string
}

type BasketItem = {
  product: Product
  quantity: number
}

function App() {
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [searchedProducts, setSearchedProducts] = useState<Product[]>(itemList);
  const [filter, setFilter] = useState<string>('AtoZ');
  const [inStock, setInStock] = useState<boolean>(false);
  const [basket, setBasket] = useState<BasketItem[]>([]);

  // ===== Hooks =====

  // Filter the list of products based on the search term, filter, and inStock status
  useEffect(() => {
    const filteredList = itemList.filter((product) =>
      product.name.toLowerCase().includes(searchTerm.toLowerCase()) &&
      (!inStock || product.quantity > 0)
    );

    switch (filter) {
      case 'AtoZ':
        filteredList.sort((a, b) => a.name.localeCompare(b.name));
        break;
      case 'ZtoA':
        filteredList.sort((a, b) => b.name.localeCompare(a.name));
        break;
      case '£LtoH':
        filteredList.sort((a, b) => a.price - b.price);
        break;
      case '£HtoL':
        filteredList.sort((a, b) => b.price - a.price);
        break;
      case '*LtoH':
        filteredList.sort((a, b) => a.rating - b.rating);
        break;
      case '*HtoL':
        filteredList.sort((a, b) => b.rating - a.rating);
        break;
    }

    setSearchedProducts(filteredList);
  }, [searchTerm, filter, inStock]);


  // ===== Basket management =====
  function showBasket() {
    const areaObject = document.getElementById('shopping-area');
    if (areaObject !== null) {
      areaObject.style.display = 'block';
    }
  }

  function hideBasket() {
    const areaObject = document.getElementById('shopping-area');
    if (areaObject !== null) {
      areaObject.style.display = 'none';
    }
  }

  function addBasketItem(product: Product) {
    const basketCopy = [...basket];
    const existingItem = basketCopy.find((item) => item.product.id === product.id);
    if (existingItem !== undefined) {
      existingItem.quantity++;
    } else {
      basketCopy.push({ product: product, quantity: 1 });
    }
    setBasket(basketCopy);
  }

  function removeBasketItem(product: Product) {
    let basketCopy = [...basket];
    const existingItem = basketCopy.find((item) => item.product.id === product.id);
    if (existingItem !== undefined) {
      existingItem.quantity--;
      if (existingItem.quantity === 0) {
        basketCopy = basketCopy.filter((item) => item.product.id !== product.id);
      }
    }
    setBasket(basketCopy);
  }

  return (
    <div id="container">
      <div id="logo-bar">
        <div id="logo-area">
          <img src="./src/assets/logo.png"></img>
        </div>
        <div id="shopping-icon-area">
          <img id="shopping-icon" onClick={showBasket} src="./src/assets/shopping-basket.png"></img>
        </div>
        <div id="shopping-area">
          <div id="exit-area">
            <p id="exit-icon" onClick={hideBasket}>x</p>
          </div>
          {
            basket.length > 0 ?
              <div id="basket-list">
                {basket.map((item) => {
                  return (
                    <div key={item.product.id} className="shopping-row">
                      <div className="shopping-information">
                        <p>{item.product.name} (£{item.product.price}) - {item.quantity}</p>
                      </div>
                      <button
                       onClick={() => removeBasketItem(item.product)}
                      >Remove</button>
                    </div>
                  )
                })}
                <div className='shopping-row'>
                  <p>Total: £{basket.reduce((acc, item) => acc + item.product.price * item.quantity, 0).toFixed(2)}</p>
                </div>
              </div>
              :
              <div id="basket-list">
                <p>Your basket is empty</p>
              </div>
          }
        </div>
      </div>
      <div id="search-bar">
        <input type="text" placeholder="Search..." onChange={changeEventObject => setSearchTerm(changeEventObject.target.value)}></input>
        <div id="control-area">
          <select
            value={filter}
            onChange={changeEventObject => setFilter(changeEventObject.target.value)}
          >
            <option value="AtoZ">By name (A - Z)</option>
            <option value="ZtoA">By name (Z - A)</option>
            <option value="£LtoH">By price (low - high)</option>
            <option value="£HtoL">By price (high - low)</option>
            <option value="*LtoH">By rating (low - high)</option>
            <option value="*HtoL">By rating (high - low)</option>
          </select>
          <input
            id="inStock"
            type="checkbox"
            value={inStock.toString()}
            onChange={changeEventObject => setInStock(changeEventObject.target.checked)}
          />
          <label htmlFor="inStock">In stock</label>
        </div>
      </div>
      <p id="results-indicator">
        {searchTerm.length === 0 ?
          itemList.length + (itemList.length === 1 ? 'Product' : 'Products')
          :
          searchedProducts.length + (searchedProducts.length === 1 ? 'Result' : 'Results')
        }
      </p>
      <ProductList itemList={searchedProducts} onAddToBasket={addBasketItem} />
    </div>
  )
}

export default App
