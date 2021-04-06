package com.ecommerce.microCommerce.dao;

import com.ecommerce.microCommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{

        public static List<Product> products = new ArrayList<>();

        static {
            products.add(new Product(1, "Hughes and Kettner Grandmeister Deluxe 40", 1200, 600));
            products.add(new Product(2, "BlackStar Series One 200", 2000, 600));
            products.add(new Product(3, "BlackStar Pro 412-A", 800, 400));
        }

        @Override
        public List<Product> findAll() {
            return products;
        }

        @Override
        public Product findById(int id) {
            for (Product product : products) {
                if (product.getId() == id) {
                    return product;
                }
            }
            return null;
        }

        @Override
        public Product save(Product product) {
            products.add(product);
            return product;
        }
}
