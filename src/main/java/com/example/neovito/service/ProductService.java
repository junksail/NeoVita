package com.example.neovito.service;

import com.example.neovito.models.Image;
import com.example.neovito.models.Product;
import com.example.neovito.models.User;
import com.example.neovito.repositories.ProductRepository;
import com.example.neovito.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    // Метод, возвращающий список продуктов;
    public List<Product> showAllProducts(String title) {
        if (title != null) {
            return productRepository.findByTitle(title);
        }
        return productRepository.findAll();
    }

    public void saveProduct(Principal principal, Product product, MultipartFile... files) throws IOException {
        product.setUser(getUserByPrincipal(principal)); // Устанавливаем текущего пользователя;
        for (int i = 0; i < files.length; i++) {
            if (files[i] != null && files[i].getSize() > 0) {
                Image image = toImageEntity(files[i]);
                if (i == 0) {
                    image.setPreviewImage(true);
                }
                product.addImageToProduct(image);  // Добавляем изображения к товару;
            }
        }
        productRepository.save(product);
        log.info("Saving new Product. Title: {}, Author email: {}", product.getTitle(), product.getUser().getEmail());
    }

    public User getUserByPrincipal(Principal principal) {
        if(principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    // Метод для преобразования изображения в возвращаемый объект;
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    // Метод для удаления товара;
    public void deleteProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId())) {
                productRepository.delete(product);
                log.info("Product with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Product with id = {} is not found", id);
        }
    }

    // Получение товара по id;
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

}
