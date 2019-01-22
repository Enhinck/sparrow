package com.enhinck.sparrow.auth.integration.authenticator;

import com.enhinck.sparrow.auth.service.SysUserAuthentication;
import org.springframework.stereotype.Component;

@Component
public class SysUserClient {
    public SysUserAuthentication findUserByUsername(String username) {
        return null;
    }

    public SysUserAuthentication findUserByPhoneNumber(String username) {
            return null;
    }
}
