package com.enhinck.sparrow.auth.integration.authenticator;

import org.springframework.stereotype.Component;

@Component
public class VerificationCodeClient {
    public Result<Boolean> validate(String vcToken, String vcCode, Object o) {
        return null;
    }

    public Result<String> getToken(int i, Object o, String s, String phoneNumber, boolean b) {
        return null;
    }
}
