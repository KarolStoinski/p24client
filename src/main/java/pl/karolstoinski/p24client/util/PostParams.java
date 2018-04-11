package pl.karolstoinski.p24client.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class PostParams {

    public static String encode(Map<String, String> params) {
        boolean next = false;
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                if (next) {
                    sb.append("&");
                }

                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

                next = true;
            } catch (UnsupportedEncodingException e) {
                // this will never happen...
            }
        }

        return sb.toString();
    }
}