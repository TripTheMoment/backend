package com.ssafy.moment.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtil {

    public static boolean checkPassword(String input, String encryptedPassword) {
        return BCrypt.checkpw(input, encryptedPassword);
    }

}