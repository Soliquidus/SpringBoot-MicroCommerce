package com.ecommerce.microCommerce.web.controller;

import com.ecommerce.microCommerce.dao.ProductDao;
import com.ecommerce.microCommerce.web.exceptions.ProductNotFoundException;
import com.ecommerce.microCommerce.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "Product manager")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Get product list
    @ApiOperation(value = "Show all existing products")
    @GetMapping(value = "/Products")
    public List<Product> productsList() {

        return productDao.findAll();

    }

    //Get product with ID
    @ApiOperation(value = "Retrieve product with its ID")
    @GetMapping(value = "/Products/{id}")
    public Product showProduct(@PathVariable int id) throws ProductNotFoundException {

        Product product = productDao.findById(id);

        if (product == null) throw new ProductNotFoundException("Product with id " + id + " doesn't exist");

        return product;
    }

    //Add product
    @ApiOperation(value = "Add product")
    @PostMapping(value = "/Products")
    public ResponseEntity<Void> addProduct(@RequestBody @Valid Product product) {
        Product productAdded = productDao.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //Update product
    @ApiOperation(value = "Update product")
    @PutMapping(value = "/Products")
    public void updateProduct(@RequestBody Product product) {

        productDao.save(product);
    }

    //Delete product
    @ApiOperation(value = "Delete product")
    @DeleteMapping(value = "/Products/{id}")
    public void deleteProduct(@PathVariable int id) {

        productDao.deleteById(id);
    }

    //Price filter
    @GetMapping(value = "/search/price/{priceLimit}")
    public List<Product> requestTest(@PathVariable int priceLimit) {
        return productDao.searchForExpensiveProduct(priceLimit);
    }

    //Name search
    @GetMapping(value = "/search/name/{search}")
    public List<Product> requestTest2(@PathVariable String search) {
        return productDao.findByNameLike("%" + search + "%");
    }
}
