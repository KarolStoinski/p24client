package pl.karolstoinski.p24client.request;

import pl.karolstoinski.p24client.P24Connection;
import pl.karolstoinski.p24client.constant.Currency;
import pl.karolstoinski.p24client.response.VerifyResponse;

import java.util.HashMap;
import java.util.Map;

import static pl.karolstoinski.p24client.constant.Endpoint.VERIFY;
import static pl.karolstoinski.p24client.constant.RequestField.*;
import static pl.karolstoinski.p24client.constant.ResponseField.ERROR;
import static pl.karolstoinski.p24client.constant.ResponseField.ERROR_MESSAGE;

public class VerifyRequest extends AbstractRequest<VerifyResponse> {

    private Map<String, String> params = new HashMap<>();

    public VerifyRequest(P24Connection connection) {
        super(connection, VERIFY);
    }

    @Override
    public VerifyResponse doRequest() {
        VerifyResponse response = new VerifyResponse();

        Map<String, String> result = sendPost(params);

        String errorCode = result.get(ERROR);

        response.setError(!"0".equals(errorCode));
        response.setErrorMessage(result.get(ERROR_MESSAGE));

        if (!"0".equals(errorCode)) {
            response.setErrorCode(errorCode);
        }

        return response;
    }

    @Override
    protected String[] signatureFields() {
        return new String[] { P24_SESSION_ID, P24_ORDER_ID, P24_AMOUNT, P24_CURRENCY };
    }

    @Override
    protected String[] requiredFields() {
        return new String[] { P24_SESSION_ID, P24_AMOUNT, P24_CURRENCY, P24_ORDER_ID };
    }

    public VerifyRequest sessionId(String value) {
        params.put(P24_SESSION_ID, value);
        return this;
    }

    public VerifyRequest amount(Integer value) {
        params.put(P24_AMOUNT, String.valueOf(value));
        return this;
    }

    public VerifyRequest currency(Currency value) {
        params.put(P24_CURRENCY, String.valueOf(value));
        return this;
    }

    public VerifyRequest currency(String value) {
        params.put(P24_CURRENCY, value);
        return this;
    }

    public VerifyRequest orderId(Integer value) {
        params.put(P24_ORDER_ID, String.valueOf(value));
        return this;
    }

    public VerifyRequest method(Integer value) {
        params.put(P24_METHOD, String.valueOf(value));
        return this;
    }

    public VerifyRequest statement(Integer value) {
        params.put(P24_STATEMENT, String.valueOf(value));
        return this;
    }

}
