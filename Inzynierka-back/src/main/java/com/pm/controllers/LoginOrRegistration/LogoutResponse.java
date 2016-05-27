package com.pm.controllers.LoginOrRegistration;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class LogoutResponse {
    private long token;

}
