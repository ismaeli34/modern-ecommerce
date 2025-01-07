package com.example.modernecommerce.modernecommerce.service;

import com.example.modernecommerce.modernecommerce.exception.ProductException;
import com.example.modernecommerce.modernecommerce.model.Rating;
import com.example.modernecommerce.modernecommerce.model.User;
import com.example.modernecommerce.modernecommerce.request.RatingRequest;

import java.util.List;

public interface RatingService  {

    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductRatings(Long productid);


}
