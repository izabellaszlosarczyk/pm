package com.pm.controllers.LoginOrRegistration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.database.ReadFromDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthorizationService {
    private static final HttpStatus STATUS_FOR_ERROR = HttpStatus.UNAUTHORIZED;
    private final ReadFromDatabase readForUser;

    public AuthorizationService(ReadFromDatabase readForUser) {
        this.readForUser = readForUser;
    }

    public ResponseEntity<String> getServerLoginResponse(LoginRequest loginRequest, AuthorizationCore authorizationCore) {
        return createResponseEntity(loginRequest, authorizationCore);
    }

    private ResponseEntity<String> createResponseEntity(LoginRequest loginRequest, AuthorizationCore authorizationCore) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginResponseModel loginResponseModel = authorizationCore.authorizeUser(loginRequest, readForUser);
        LoginResponse loginResponse =
                new LoginResponse(loginResponseModel);
        HttpStatus httpStatus = getHttpStatus(loginResponseModel.getToken());
        String jsonResponse;
        try {
            jsonResponse = objectMapper.writeValueAsString(loginResponse);
        } catch (JsonProcessingException e) {
            jsonResponse = "{\"userId\":\"\",\"token\":\"UNKNOWN_ERROR\"}";
            httpStatus = STATUS_FOR_ERROR;
        }
        System.out.println(jsonResponse);
        return ResponseEntity.status(httpStatus).body(jsonResponse);
    }

    private HttpStatus getHttpStatus(AuthorizationToken token) {
        switch (token) {
            case WRONG_EMAIL:
            case WRONG_PASSWORD:
                return STATUS_FOR_ERROR;
            default:
                return HttpStatus.OK;
        }
    }
}
