package pl.karolstoinski.p24client.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SignatureCalculator {

    public static String signature(List<String> input) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.size(); i++) {
            if (i > 0) {
                sb.append("|");
            }

            sb.append(input.get(i));
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sb.toString().getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

}
