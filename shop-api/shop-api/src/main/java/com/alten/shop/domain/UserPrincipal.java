package com.alten.shop.domain;

import java.util.Collection;
import java.util.Collections;

import com.alten.shop.domain.model.User;
import com.alten.shop.domain.dto.UserDTO;
import com.alten.shop.domain.mapper.UserDTOMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final User user;
     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDTO getUser() {
        return UserDTOMapper.fromUser(this.user);
    }
}
