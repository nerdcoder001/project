package com.cdacQuerybridge.auth_service.util;

import java.util.Random;

public class CaptchaUtil {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generate() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            captcha.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return captcha.toString();
    }

	public static boolean validate(String captcha) {
		
		return false;
	}
}
