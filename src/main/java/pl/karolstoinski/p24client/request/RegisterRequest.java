package pl.karolstoinski.p24client.request;

import pl.karolstoinski.p24client.P24Connection;
import pl.karolstoinski.p24client.constant.Currency;
import pl.karolstoinski.p24client.constant.Language;
import pl.karolstoinski.p24client.response.RegisterResponse;

import java.util.HashMap;
import java.util.Map;

import static pl.karolstoinski.p24client.constant.Endpoint.REGISTER;
import static pl.karolstoinski.p24client.constant.Endpoint.REQUEST;
import static pl.karolstoinski.p24client.constant.RequestField.*;
import static pl.karolstoinski.p24client.constant.ResponseField.ERROR;
import static pl.karolstoinski.p24client.constant.ResponseField.ERROR_MESSAGE;
import static pl.karolstoinski.p24client.constant.ResponseField.TOKEN;

public class RegisterRequest extends AbstractRequest<RegisterResponse> {

    private Map<String, String> params = new HashMap<>();

    public RegisterRequest(P24Connection connection) {
        super(connection, REGISTER);
    }

    @Override
    protected String[] signatureFields() {
        return new String[] { P24_SESSION_ID, P24_MERCHANT_ID, P24_AMOUNT, P24_CURRENCY };
    }

    @Override
    protected String[] requiredFields() {
        return new String[] { P24_SESSION_ID, P24_AMOUNT, P24_CURRENCY, P24_DESCRIPTION, P24_EMAIL, P24_COUNTRY, P24_URL_RETURN };
    }

    @Override
    public RegisterResponse doRequest() {
        RegisterResponse response = new RegisterResponse();

        Map<String, String> result = sendPost(params);
        response.setError("1".equals(result.get(ERROR)) || result.isEmpty());
        response.setErrorMessage(result.get(ERROR_MESSAGE));
        response.setToken(result.get(TOKEN));
        response.setPaymentUrl(connection.getServerAddress() + REQUEST + result.get(TOKEN));

        return response;
    }

    public RegisterRequest sessionId(String value) {
        params.put(P24_SESSION_ID, value);
        return this;
    }

    public RegisterRequest amount(Integer value) {
        params.put(P24_AMOUNT, String.valueOf(value));
        return this;
    }

    public RegisterRequest currency(Currency value) {
        params.put(P24_CURRENCY, String.valueOf(value));
        return this;
    }

    public RegisterRequest currency(String value) {
        params.put(P24_CURRENCY, value);
        return this;
    }

    public RegisterRequest description(String value) {
        params.put(P24_DESCRIPTION, value);
        return this;
    }

    public RegisterRequest email(String value) {
        params.put(P24_EMAIL, value);
        return this;
    }

    public RegisterRequest client(String value) {
        params.put(P24_CLIENT, value);
        return this;
    }

    public RegisterRequest address(String value) {
        params.put(P24_ADDRESS, value);
        return this;
    }

    public RegisterRequest zip(String value) {
        params.put(P24_ZIP, value);
        return this;
    }

    public RegisterRequest city(String value) {
        params.put(P24_CITY, value);
        return this;
    }

    public RegisterRequest country(String value) {
        params.put(P24_COUNTRY, value);
        return this;
    }

    public RegisterRequest phone(String value) {
        params.put(P24_PHONE, value);
        return this;
    }

    public RegisterRequest language(String value) {
        params.put(P24_LANGUAGE, value);
        return this;
    }

    public RegisterRequest language(Language value) {
        params.put(P24_LANGUAGE, String.valueOf(value).toLowerCase());
        return this;
    }

    public RegisterRequest method(Integer value) {
        params.put(P24_METHOD, String.valueOf(value));
        return this;
    }

    public RegisterRequest urlReturn(String value) {
        params.put(P24_URL_RETURN, value);
        return this;
    }

    public RegisterRequest urlStatus(String value) {
        params.put(P24_URL_STATUS, value);
        return this;
    }

    public RegisterRequest timeLimit(Integer value) {
        params.put(P24_TIME_LIMIT, String.valueOf(value));
        return this;
    }

    public RegisterRequest waitForResult(boolean value) {
        params.put(P24_WAIT_FOR_RESULT, value ? "1" : "0");
        return this;
    }

    public RegisterRequest channel(Integer value) {
        params.put(P24_CHANNEL, String.valueOf(value));
        return this;
    }

    public RegisterRequest shipping(Integer value) {
        params.put(P24_SHIPPING, String.valueOf(value));
        return this;
    }

    public RegisterRequest transferLabel(String value) {
        params.put(P24_TRANSFER_LABEL, value);
        return this;
    }

    public RegisterRequest encoding(String value) {
        params.put(P24_ENCODING, value);
        return this;
    }

    public RegisterRequest shopingListItemName(Integer numberOfItem, String value) {
        params.put(P24_NAME_X + numberOfItem, value);
        return this;
    }

    public RegisterRequest shopingListItemDesctiption(Integer numberOfItem, String value) {
        params.put(P24_DESCRIPTION_X + numberOfItem, value);
        return this;
    }

    public RegisterRequest shopingListItemQuantity(Integer numberOfItem, Integer value) {
        params.put(P24_QUANTITY_X + numberOfItem, String.valueOf(value));
        return this;
    }

    public RegisterRequest shopingListItemPrice(Integer numberOfItem, Integer value) {
        params.put(P24_PRICE_X + numberOfItem, String.valueOf(value));
        return this;
    }

    public RegisterRequest setShopingListItemNumber(Integer numberOfItem, Integer value) {
        params.put(P24_NUMBER_X + numberOfItem, String.valueOf(value));
        return this;
    }
}
