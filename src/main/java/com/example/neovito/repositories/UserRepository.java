package com.example.neovito.repositories;

import com.example.neovito.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     Метод для конфигурации Spring Security. Мы сами обозначим, что это значение является уникальным

     */
    User findByEmail(String email);
}
