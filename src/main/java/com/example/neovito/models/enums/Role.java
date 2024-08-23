package com.example.neovito.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN; // константы

    @Override
    public String getAuthority() {
        return name();
    }
}
