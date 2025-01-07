package com.example.modernecommerce.modernecommerce.service.impl;

import com.example.modernecommerce.modernecommerce.exception.CartItemException;
import com.example.modernecommerce.modernecommerce.exception.UserException;
import com.example.modernecommerce.modernecommerce.model.Cart;
import com.example.modernecommerce.modernecommerce.model.CartItem;
import com.example.modernecommerce.modernecommerce.model.Product;
import com.example.modernecommerce.modernecommerce.model.User;
import com.example.modernecommerce.modernecommerce.repository.CartItemRepository;
import com.example.modernecommerce.modernecommerce.repository.CartRepository;
import com.example.modernecommerce.modernecommerce.service.CartItemService;
import com.example.modernecommerce.modernecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;


    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());
        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getQuantity()*cartItem.getProduct().getPrice());
            item.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());
        //jo user loggedin hai wo bhi chahiye
       User reqUser= userService.findUserById(userId);
       if(user.getId().equals(reqUser.getId())){
           cartItemRepository.deleteById(cartItemId);
       }
        else{
            throw  new UserException("You cannot remove another user's item");
       }


    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("CartItem not found with id"+ cartItemId);

    }
}
