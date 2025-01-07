package com.example.modernecommerce.modernecommerce.service.impl;

import com.example.modernecommerce.modernecommerce.model.User;
import com.example.modernecommerce.modernecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This code represents a custom implementation of the UserDetailsService interface in a Spring Security context.
 * It's used to load user details during the authentication process, fetching user information from
 * a database via the UserRepository. If the user is found, their details are wrapped in a UserDetails
 * object for authentication and authorization. If the user is not found, a UsernameNotFoundException is thrown.
 */

@Service
public class CustomUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username);
        if (user==null){
            throw  new UsernameNotFoundException("User not found with email"+username);
        }
        /**
         * if a user is found, this line creates a User object from Spring Security, which implements the UserDetails interface.
         * This object includes the user's email (username), password, and a list of authorities (roles or permissions).
         * This UserDetails object is used by Spring Security for authentication and authorization purposes.
         */
      List<GrantedAuthority> authorities=  new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
