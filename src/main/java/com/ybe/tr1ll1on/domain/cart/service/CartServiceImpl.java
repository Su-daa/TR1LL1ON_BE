package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.CartItemDto;
import com.ybe.tr1ll1on.domain.cart.dto.GetCartResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public GetCartResponse getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        List<CartItemDto> cartItemDtos = carts.stream()
                .map(cart -> cart.getCartItem().stream()
                        .map(cartItem -> new CartItemDto(
                                cartItem.getId(),
                                cartItem.getProduct().getId(),
                                cartItem.getPersonNumber(),
                                cartItem.getPrice()
                        ))
                        .collect(Collectors.toList())
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());

        GetCartResponse response = new GetCartResponse();
        response.setCartItems(cartItemDtos);

        return response;
    }

    @Override
    public AddCartItemResponse addCartItem(Long productId, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // UserRepository가 없으므로 주석 처리
        // User user = userRepository.findById(userId)
        //         .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 사용자 정보가 없으므로, 사용자 ID만으로 간단히 사용자를 생성하여 사용
        User user = new User();
        user.setId(userId);

        // Check if the user has a cart, if not, create one
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            user.setCart(cart);
            // UserRepository가 없으므로 주석 처리
            // userRepository.save(user);
        }

        CartItem cartItem = new CartItem();
        cartItem.setStartDate(LocalDateTime.now());
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        cartItemRepository.save(cartItem);

        AddCartItemResponse response = new AddCartItemResponse();
        response.setCartItemId(cartItem.getId());
        response.setProductId(product.getId());
        response.setPersonNumber(cartItem.getPersonNumber());

        return response;
    }

    @Override
    public RemoveCartItemResponse removeCartItem(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));

        cartItemRepository.delete(cartItem);

        RemoveCartItemResponse response = new RemoveCartItemResponse();
        response.setCartItemId(cartItemId);

        return response;
    }
}
