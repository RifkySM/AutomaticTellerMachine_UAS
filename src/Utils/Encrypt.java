package Utils;

import java.security.MessageDigest;

public class Encrypt {
    public static String encrypt(String text) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(text.getBytes());
            byte[] bytes = m.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean check(String hashedPassword, String password) {
        return password != null && hashedPassword != null && encrypt(password).equals(hashedPassword);
    }
}
