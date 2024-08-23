package com.example.neovito.repositories;

import com.example.neovito.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Здесь мы екстендимся от JPARepository - таким образом
 * получаем список готовых решений от Spring для получения, сохранения, удаления
 * товаров.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     У метода не прописано тело - Spring сам, по названию, реализует тело.
     */
    List<Product> findByTitle(String title);


}
