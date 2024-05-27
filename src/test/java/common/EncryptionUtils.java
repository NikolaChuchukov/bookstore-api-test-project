package common;

import java.util.Base64;

    public class EncryptionUtils {

        public static String decode(String encodedString) {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            return new String(decodedBytes);
        }


}
