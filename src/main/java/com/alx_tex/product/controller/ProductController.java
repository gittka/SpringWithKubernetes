package com.alx_tex.product.controller;

import com.alx_tex.product.dto.ProductRequest;
import com.alx_tex.product.dto.ProductResponse;
import com.alx_tex.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse creteProduct(@RequestBody ProductRequest productRequest){
      return  productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(){
      return productService.getAllProducts();

    }
}