package com.ecommerce.microCommerce.web.controller;

import com.ecommerce.microCommerce.dao.ProductDao;
import com.ecommerce.microCommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    //Get product list
    @RequestMapping(value = "/Products", method = RequestMethod.GET)

    public MappingJacksonValue productsList() {
        List<Product> products = productDao.findAll();

        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter.serializeAllExcept("buyPrice");

        FilterProvider filterList =
                new SimpleFilterProvider().addFilter("dynamicFilter", filter);

        MappingJacksonValue filterProducts =
                new MappingJacksonValue(products);

        filterProducts.setFilters(filterList);

        return filterProducts;
    }

    //Get product with ID
    @RequestMapping(value = "/Products/{id}", method = RequestMethod.GET)

    public Product showProduct(@PathVariable int id) {
        return productDao.findById(id);
    }

    //Add product
    @PostMapping(value = "/Products")

    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        Product productAdded = productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
