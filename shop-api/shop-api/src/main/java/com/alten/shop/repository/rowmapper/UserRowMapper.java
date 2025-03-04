package com.alten.shop.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.alten.shop.domain.model.User;
import org.springframework.jdbc.core.RowMapper;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .userName(resultSet.getString("user_name"))
                .firstName(resultSet.getString("first_name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();

    }
}













