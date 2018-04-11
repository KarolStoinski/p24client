package pl.karolstoinski.p24client.request;

import pl.karolstoinski.p24client.P24Connection;
import pl.karolstoinski.p24client.response.TestResponse;

import java.util.HashMap;
import java.util.Map;

import static pl.karolstoinski.p24client.constant.Endpoint.TEST_CONNECTION;
import static pl.karolstoinski.p24client.constant.RequestField.P24_POS_ID;
import static pl.karolstoinski.p24client.constant.ResponseField.ERROR;
import static pl.karolstoinski.p24client.constant.ResponseField.ERROR_MESSAGE;

public class TestRequest extends AbstractRequest<TestResponse> {

    public TestRequest(P24Connection connection) {
        super(connection, TEST_CONNECTION);
    }

    @Override
    protected String[] signatureFields() {
        return new String[] { P24_POS_ID };
    }

    @Override
    protected String[] requiredFields() {
        return new String[0];
    }

    @Override
    public TestResponse doRequest() {
        Map<String, String> params = new HashMap<>();

        TestResponse response = new TestResponse();

        Map<String, String> result = sendPost(params);
        response.setError("1".equals(result.get(ERROR)) || result.isEmpty());
        response.setErrorMessage(result.get(ERROR_MESSAGE));

        return response;
    }

}
