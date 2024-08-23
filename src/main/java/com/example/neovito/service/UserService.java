package com.example.neovito.service;

import com.example.neovito.models.User;
import com.example.neovito.models.enums.Role;
import com.example.neovito.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    /**
     Метод использует findByEmail() класса UserRepository для проверки наличия email в базе.
     */
    public boolean createUser(User user){
        String email = user.getEmail();
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false; // Если email в базе существует - запрещаем регестрацию;
        }
        user.setActive(true); // Переводим флажок в состояние активности;
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Устанавливаем пользователю пароль;
        user.getRoles().add(Role.ROLE_ADMIN); // Устанавливаем роль;
        log.info("Saving new user with email: {}", email);
        userRepository.save(user); // Сохраняем пользователя в базе;
        return true;
    }

    // Список пользователей;
    public List<User> list(){
        return userRepository.findAll();
    }

    /**
     Метод, позволяющий выдать администратора или наоборот, разжаловать.
     */
    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    /**
     Метод сразу реализует бан и разбан, манипулируя флажком активности.
     */
    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            if(user.isActive()) {
                user.setActive(false);
                log.info("User № {} was banned", user.getId());
            } else {
                user.setActive(true);
                log.info("User № {} was unbanned", user.getId());
            }
        }
        userRepository.save(user);
    }

    // Получение пользователя по principal
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }
}
