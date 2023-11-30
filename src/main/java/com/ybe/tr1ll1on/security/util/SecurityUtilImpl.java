package com.ybe.tr1ll1on.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtilImpl implements SecurityUtilProvider{

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}