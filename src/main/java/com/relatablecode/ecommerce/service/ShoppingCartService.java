package com.relatablecode.ecommerce.service;

import com.relatablecode.ecommerce.auth.user.User;
import com.relatablecode.ecommerce.dto.CartItemDTO;
import com.relatablecode.ecommerce.model.CartItem;
import com.relatablecode.ecommerce.model.Product;
import com.relatablecode.ecommerce.model.ShoppingCart;
import com.relatablecode.ecommerce.repository.CartItemRepository;
import com.relatablecode.ecommerce.repository.ProductRepository;
import com.relatablecode.ecommerce.repository.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public void addItemToCart(User user, Long productId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseGet(() -> createNewCart(user));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();

        if (existingItem.isPresent()) {
            // If item exists, increase quantity
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            // If item does not exist, add new item
            CartItem newItem = new CartItem();
            newItem.setShoppingCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    public void removeItemFromCart(User user, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getShoppingCart().getUser().equals(user)) {
            throw new RuntimeException("Access denied");
        }

        if (item.getQuantity() > 1) {
            // If quantity is more than one, decrease it by one
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        } else {
            // If quantity is one, remove the item from the cart
            cartItemRepository.delete(item);
        }
    }

    public void bulkRemoveItemFromCart(User user, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getShoppingCart().getUser().equals(user)) {
            throw new RuntimeException("Access denied");
        }

        cartItemRepository.delete(item);
    }

    public List<CartItemDTO> getCartItems(User user) {
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cart.getItems().stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDTO.class))
                .collect(Collectors.toList());
    }

    public void clearCart(User user) {
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Delete all cart items
        cartItemRepository.deleteAll(cart.getItems());

        // Clear the items from the cart object
        cart.getItems().clear();

        // Optionally, save the cart if you are maintaining any additional information in the cart
        shoppingCartRepository.save(cart);
    }

    private ShoppingCart createNewCart(User user) {
        ShoppingCart newCart = new ShoppingCart();
        newCart.setUser(user);
        return shoppingCartRepository.save(newCart);
    }

}
