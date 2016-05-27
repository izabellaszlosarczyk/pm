package com.pm.controllers.LoginOrRegistration;

import com.pm.database.ReadFromDatabase;
import com.pm.model.User;

import java.util.Optional;

public class AuthorizationHelper {
    public static Optional<UserLoginData> getUserLoginData(String email, ReadFromDatabase readDatabase) {
        User user = readDatabase.searchOneByEmail(email);
        return  user == null ? Optional.<UserLoginData>empty() : createUserLoginData(user);
    }

    private static Optional<UserLoginData> createUserLoginData(User user) {
        return Optional.of(UserLoginData.builder()
                .id(user. getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .token(AuthorizationToken.USER)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build());
    }
}
