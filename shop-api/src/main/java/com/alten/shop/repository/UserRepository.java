package com.alten.shop.repository;

import com.alten.shop.domain.model.User;


public interface UserRepository<T extends User> {
	T create(T data);
	User getUserByEmail(String email);

}
