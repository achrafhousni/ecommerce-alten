package com.alten.shop.service.impl;

import com.alten.shop.domain.model.User;
import com.alten.shop.domain.dto.UserDTO;
import com.alten.shop.domain.mapper.UserDTOMapper;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.service.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository<User> userRepository;

	@Override
	public User createUser(UserDTO userDto) {
		return userRepository.create(UserDTOMapper.toUser(userDto));
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		return mapToUserDTO(userRepository.getUserByEmail(email));
	}

	private UserDTO mapToUserDTO(User user) {
		return UserDTOMapper.fromUser(user);
	}
}
