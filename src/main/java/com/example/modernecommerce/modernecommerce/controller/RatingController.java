package com.example.modernecommerce.modernecommerce.controller;

import com.example.modernecommerce.modernecommerce.exception.ProductException;
import com.example.modernecommerce.modernecommerce.exception.UserException;
import com.example.modernecommerce.modernecommerce.model.Rating;
import com.example.modernecommerce.modernecommerce.model.User;
import com.example.modernecommerce.modernecommerce.request.RatingRequest;
import com.example.modernecommerce.modernecommerce.service.RatingService;
import com.example.modernecommerce.modernecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
                                               @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(req, user);
        return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
                                                          @RequestHeader("Authorization") String jwt)
            throws UserException,ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductRatings(productId);
        return new ResponseEntity<>(ratings,HttpStatus.CREATED);

    }


}
