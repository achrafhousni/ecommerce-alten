package com.alten.shop.security.service;

import com.alten.shop.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SecurityService {
    @Value("${admin.email}")
    private String adressEmail;

    public boolean isAdmin(Authentication authentication) {

        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDTO user) {
            return adressEmail.equalsIgnoreCase(user.getEmail());
        }

        return  false;


    }
}
