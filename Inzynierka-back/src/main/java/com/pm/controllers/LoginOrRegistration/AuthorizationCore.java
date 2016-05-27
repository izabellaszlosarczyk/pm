package com.pm.controllers.LoginOrRegistration;



import com.pm.database.ReadFromDatabase;

import java.util.Optional;

import static com.pm.controllers.LoginOrRegistration.AuthorizationToken.WRONG_EMAIL;
import static com.pm.controllers.LoginOrRegistration.AuthorizationToken.WRONG_PASSWORD;
public class AuthorizationCore {
    private static AuthorizationCore INSTANCE = new AuthorizationCore();

    private AuthorizationCore() {
    }

    public static AuthorizationCore getInstance() {
        return INSTANCE;
    }

    public LoginResponseModel authorizeUser(LoginRequest loginRequest, ReadFromDatabase readDatabase) {
        Optional<UserLoginData> optionalUserLoginData = AuthorizationHelper.getUserLoginData(loginRequest.getEmail(), readDatabase);
        if (!optionalUserLoginData.isPresent())
            return new LoginResponseModel(WRONG_EMAIL);
        UserLoginData userLoginData = optionalUserLoginData.get();
        if (isPasswordCorrect(loginRequest.getPassword(), userLoginData.getPassword()))
            return new LoginResponseModel(userLoginData);
        return new LoginResponseModel(WRONG_PASSWORD);
    }

    private boolean isPasswordCorrect(String password, String userLoginDataPassword) {
        return password.equals(userLoginDataPassword);
    }
}
