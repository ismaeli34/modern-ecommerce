package com.example.modernecommerce.modernecommerce.service;

import com.example.modernecommerce.modernecommerce.exception.ProductException;
import com.example.modernecommerce.modernecommerce.model.Review;
import com.example.modernecommerce.modernecommerce.model.User;
import com.example.modernecommerce.modernecommerce.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);


}
