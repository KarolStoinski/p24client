package pl.karolstoinski.p24client.response;

public class VerifyResponse extends Response {

    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
