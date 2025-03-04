package com.alten.shop.repository.impl;

import java.util.Map;

import com.alten.shop.domain.model.User;
import com.alten.shop.domain.UserPrincipal;
import com.alten.shop.exception.ApiException;
import com.alten.shop.repository.query.UserQuery;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.repository.rowmapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User>,UserDetailsService{
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 private final BCryptPasswordEncoder encoder;

	@Override
	public User create(User user) {
		try {
			if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");

			KeyHolder holder=new GeneratedKeyHolder();
			SqlParameterSource parameters=getSqlParameterSource(user);
			namedParameterJdbcTemplate.update(UserQuery.INSERT_USER_QUERY,parameters,holder,new String[]{"id"});
			user.setId(holder.getKey().longValue());
			return user;
		}catch(Exception exception ) {
			log.error(exception.getMessage());
			throw new ApiException(exception.getMessage());

		}
	}

	@Override
	public User getUserByEmail(String email) {
		try {
			return namedParameterJdbcTemplate.queryForObject(UserQuery.SELECT_USER_BY_EMAIL_QUERY, Map.of("email", email), new UserRowMapper());

		} catch (EmptyResultDataAccessException exception) {
			throw new ApiException("No User found by email: " + email);
		} catch (Exception exception) {
			log.error(exception.getMessage());
			throw new ApiException("An error occurred. Please try again.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = getUserByEmail(email);
		if(user == null) {
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		}
		else {
			log.info("User found in the database: {}", email);
			return new UserPrincipal(user);
		}
	}


	private SqlParameterSource getSqlParameterSource(User user) {
		return new MapSqlParameterSource()
				.addValue("userName", user.getUserName())
				.addValue("firstName", user.getFirstName())
				.addValue("email", user.getEmail())
				.addValue("password", encoder.encode(user.getPassword()));
	}


	private int getEmailCount(String email) {
		return namedParameterJdbcTemplate.queryForObject(UserQuery.COUNT_USER_EMAIL_QUERY, Map.of("email",email),Integer.class);
	}
}
