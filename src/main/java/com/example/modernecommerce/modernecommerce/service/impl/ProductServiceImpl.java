package com.example.modernecommerce.modernecommerce.service.impl;

import com.example.modernecommerce.modernecommerce.exception.ProductException;
import com.example.modernecommerce.modernecommerce.model.Category;
import com.example.modernecommerce.modernecommerce.model.Product;
import com.example.modernecommerce.modernecommerce.repository.CategoryRepository;
import com.example.modernecommerce.modernecommerce.repository.ProductRepository;
import com.example.modernecommerce.modernecommerce.request.CreateProductRequest;
import com.example.modernecommerce.modernecommerce.service.ProductService;
import com.example.modernecommerce.modernecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req) {
        // top level category
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
        if(topLevel==null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel = categoryRepository.save(topLevelCategory);
        }
        // second level category
        Category secondLevel= categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
        if(secondLevel==null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        // third level category
        Category thirdLevel= categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
        if(thirdLevel==null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setLevel(3);
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setBrand(req.getBrand());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountedPercent(req.getDiscountedPercent());
        product.setImageUrl(req.getImageUrl());
        product.setSizes(req.getSizes());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        product.setCreatedAt(LocalDateTime.now());

        /**
         * If you're adding a new product that is a men's T-shirt, it makes more sense to assign it to the "T-Shirts"
         * category (third-level) rather than just the "Men's Clothing" category (second-level) or the general "Apparel"
         * category (top-level). Assigning the product to the most specific category helps with accurate categorization,
         * filtering, and searching on your e-commerce platform.
         */
        product.setCategory(thirdLevel);
        Product savedProduct = productRepository.save(product);
        System.out.println("Products -"+ product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if(req.getQuantity()!=0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> optional = productRepository.findById(productId);

        if(optional.isPresent()){
            return optional.get();
        }
        throw new ProductException("Product not found with id -"+ productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts;
    }
    @Override
    public Page<Product> getAllProducts(String category,
                                        List<String> colors,
                                        List<String> sizes,
                                        Integer minPrice,
                                        Integer maxPrice,
                                        Integer minDiscount,
                                        String sort,
                                        String stock,
                                        Integer pageNumber,
                                        Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
        if(!colors.isEmpty()){
            products = products.stream().filter(p -> colors.stream()
                            .anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }
        // agar in stock ho raha hai toh greater than quantity honi chahiye

        if(stock!=null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0)
                        .collect(Collectors.toList());
            }
            else if(stock.equals("out_of_stock")){
              products=  products.stream().filter(p->p.getQuantity()<1)
                      .collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
       int endIndex= Math.min(startIndex+pageable.getPageSize(),products.size());
       List<Product> pageContent = products.subList(startIndex,endIndex);

       Page<Product> filterProducts = new PageImpl<>(pageContent,pageable,products.size());
       return filterProducts;

    }
}
