package com.ecommerce.microCommerce.dao;

import com.ecommerce.microCommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    Product findById(int id);

    List<Product> findByNameLike(String search);

    //Framework query example
    List<Product> findByPriceGreaterThan(int priceLimit);

    //Custom query example
    @Query("SELECT p FROM Product p WHERE p.price > :priceLimit")
    List<Product> searchForExpensiveProduct(@Param("priceLimit") int price);


}
