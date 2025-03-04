package com.alten.shop.service;

import com.alten.shop.domain.model.User;
import com.alten.shop.domain.dto.UserDTO;


public interface UserService {
	User createUser(UserDTO userDto);
    UserDTO getUserByEmail(String email);



}
