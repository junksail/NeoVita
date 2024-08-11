package com.example.neovito.service;

import com.example.neovito.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private long ID = 0;

    {
        products.add(new Product(++ID,"BMW x5", "Nice car!", 2500000, "Krasnodar", "Ivan1337"));
        products.add(new Product(++ID, "ВАЗ 2107", "Nice car!", 100000, "Krasnodar", "PanKi_v_tanke"));
    }

    public List<Product> showAllProducts() {
        return products;
    }

    public void saveProduct(Product product) {
        product.setId(++ID);
        products.add(product);
    }

    public void deleteProduct(Long id) {
        products.removeIf(product -> product.getId() == id);
    }

    public Product getProductById(Long id){
        for(Product product : products) {
            if(product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
