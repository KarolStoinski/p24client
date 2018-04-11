package pl.karolstoinski.p24client.exception;

public class RequiredFieldMissingException extends RuntimeException {
    public RequiredFieldMissingException(String field) {
        super("Field " + field + " is missing");
    }
}
