package pl.karolstoinski.p24client.request;

import pl.karolstoinski.p24client.P24Connection;
import pl.karolstoinski.p24client.exception.RequiredFieldMissingException;
import pl.karolstoinski.p24client.util.PostParams;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static java.lang.String.valueOf;
import static java.util.Arrays.stream;
import static pl.karolstoinski.p24client.constant.RequestField.*;
import static pl.karolstoinski.p24client.util.SignatureCalculator.signature;

public abstract class AbstractRequest<T> {

    protected P24Connection connection;
    protected String endpoint;

    public AbstractRequest(P24Connection connection, String endpoint) {
        this.connection = connection;
        this.endpoint = endpoint;
    }

    private HttpURLConnection httpConnection() throws Exception {
        URL url = new URL(connection.getServerAddress() + endpoint);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty( "charset", "utf-8");

        return connection;
    }

    protected Map<String,String> sendPost(Map<String, String> params) {
        Arrays.stream(requiredFields()).forEach(field -> {
            if (!params.containsKey(field)) {
                throw new RequiredFieldMissingException(field);
            }
        });

        try {
            return sendPostWithExceptions(params);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Map<String,String> sendPostWithExceptions(Map<String, String> params) throws Exception {
        HttpURLConnection httpConnection = httpConnection();

        params.put(P24_MERCHANT_ID, valueOf(connection.getMerchantId()));
        params.put(P24_POS_ID, valueOf(connection.getPosId()));
        params.put(P24_API_VERSION, "3.2");

        String[] signatureFields = signatureFields();
        List<String> signatureFieldValues = new ArrayList<>();

        stream(signatureFields).forEach(field -> signatureFieldValues.add(params.get(field)));
        signatureFieldValues.add(connection.getCrc());

        String signature = signature(signatureFieldValues);
        params.put(P24_SIGN, signature);

        String encodedParams = PostParams.encode(params);

        DataOutputStream out = new DataOutputStream(httpConnection.getOutputStream());
        out.writeBytes(encodedParams);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpConnection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        httpConnection.disconnect();

        return extractResponse(content.toString());
    }

    private Map<String, String> extractResponse(String response) {
        Map<String, String> map = new HashMap<>();

        stream(response.split("&")).forEach(keyValue -> {
            String[] splited = keyValue.split("=");
            map.put(splited[0], splited[1]);
        });

        return map;
    }

    protected abstract String[] signatureFields();

    protected abstract String[] requiredFields();

    public abstract T doRequest();

}
