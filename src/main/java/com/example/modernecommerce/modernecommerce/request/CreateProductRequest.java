package com.example.modernecommerce.modernecommerce.request;

import com.example.modernecommerce.modernecommerce.model.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class  CreateProductRequest {
    private String title;

    private String description;

    private int price;

    private int discountedPrice;
    private int discountedPercent;
    private int quantity;
    private String brand;
    private String color;
    private Set<Size> sizes= new HashSet<>();
    private String imageUrl;

    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;


}
