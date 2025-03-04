package com.alten.shop.repository.query;

public class UserQuery {


    public static final String INSERT_USER_QUERY = "INSERT INTO Users (email, first_name, user_name, password) VALUES (:email, :firstName, :userName, :password)";
    public static final String SELECT_USER_BY_EMAIL_QUERY="SELECT * from users where email=:email";
    public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email = :email";

}
