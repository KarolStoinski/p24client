package pl.karolstoinski.p24client;

import pl.karolstoinski.p24client.request.RegisterRequest;
import pl.karolstoinski.p24client.request.TestRequest;
import pl.karolstoinski.p24client.request.VerifyRequest;

import static pl.karolstoinski.p24client.constant.URL.PRODUCTION_URL;
import static pl.karolstoinski.p24client.constant.URL.SANDBOX_URL;

public class P24Connection {

    private Integer merchantId;
    private Integer posId;
    private String crc;
    private Boolean sandbox = false;

    public P24Connection(Integer merchantId, Integer posId, String crc) {
        this.merchantId = merchantId;
        this.posId = posId;
        this.crc = crc;
    }

    public TestRequest testRequest() {
        return new TestRequest(this);
    }

    public RegisterRequest registerRequest() {
        return new RegisterRequest(this);
    }

    public VerifyRequest verifyRequest() {
        return new VerifyRequest(this);
    }

    public Boolean isInSandboxMode() {
        return sandbox;
    }

    public void setSandboxMode(Boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getServerAddress() {
        return sandbox ? SANDBOX_URL : PRODUCTION_URL;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getPosId() {
        return posId;
    }

    public void setPosId(Integer posId) {
        this.posId = posId;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }
}
