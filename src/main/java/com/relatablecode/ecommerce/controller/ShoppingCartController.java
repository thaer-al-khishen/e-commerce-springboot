package com.relatablecode.ecommerce.controller;

import com.relatablecode.ecommerce.auth.user.User;
import com.relatablecode.ecommerce.auth.user.UserRepository;
import com.relatablecode.ecommerce.dto.CartItemDTO;
import com.relatablecode.ecommerce.model.CartItem;
import com.relatablecode.ecommerce.requests.CartItemRequest;
import com.relatablecode.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final UserRepository userRepository;

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserRepository userRepository) {
        this.shoppingCartService = shoppingCartService;
        this.userRepository = userRepository;
    }

    // Endpoint to view cart items
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<CartItemDTO> items = shoppingCartService.getCartItems(user);
        return ResponseEntity.ok(items);
    }

    // Endpoint to add an item to the cart
    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartItemRequest itemRequest) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        shoppingCartService.addItemToCart(user, itemRequest.getProductId(), itemRequest.getQuantity());
        return ResponseEntity.ok().body("Item added to cart successfully");
    }

    // Endpoint to remove an item from the cart
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long itemId) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        shoppingCartService.removeItemFromCart(user, itemId);
        return ResponseEntity.ok().body("Item removed from cart successfully");
    }

    // Endpoint to bulk remove an item from the cart
    @DeleteMapping("/bulk/remove/{itemId}")
    public ResponseEntity<?> bulkRemoveItemFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long itemId) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        shoppingCartService.bulkRemoveItemFromCart(user, itemId);
        return ResponseEntity.ok().body("Item removed from cart successfully");
    }

    // Endpoint to clear the cart
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        shoppingCartService.clearCart(user);
        return ResponseEntity.ok().body("Cart cleared successfully");
    }

}
