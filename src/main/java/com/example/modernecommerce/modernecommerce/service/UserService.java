package com.example.modernecommerce.modernecommerce.service;

import com.example.modernecommerce.modernecommerce.exception.UserException;
import com.example.modernecommerce.modernecommerce.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
