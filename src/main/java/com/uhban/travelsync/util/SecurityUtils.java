package com.uhban.travelsync.util;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof PrincipalDetails) {
            return ((PrincipalDetails) principal).getUserId();
        }

        return null;
    }
}
