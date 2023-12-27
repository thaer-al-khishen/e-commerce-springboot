package com.relatablecode.ecommerce.utils;

import com.relatablecode.ecommerce.auth.role.ERole;
import com.relatablecode.ecommerce.auth.user.User;

import java.util.concurrent.atomic.AtomicBoolean;

public class GeneralUtils {
    public static boolean isAdminUser(User user) {
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        user.getRoles().forEach(role -> {
            if (role.getName().equals(ERole.ADMIN)) {
                isAdmin.set(true);
            }
        });
        return isAdmin.get();
    }
}
