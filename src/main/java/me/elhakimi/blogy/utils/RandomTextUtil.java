package me.elhakimi.blogy.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomTextUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    public static String generateRandomText(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be at least 1.");
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        String randomText = builder.toString();

        return BCrypt.hashpw(randomText, BCrypt.gensalt());
    }
}
