package com.alten.shop.utils;

import com.alten.shop.domain.UserPrincipal;
import com.alten.shop.domain.dto.UserDTO;
import org.springframework.security.core.Authentication;



public class UserUtils {
    public static UserDTO getLoggedInUser(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUser();
    }
}
