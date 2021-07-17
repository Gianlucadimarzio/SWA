package rest;

public class RESTCustomException extends Exception {

    public RESTCustomException() {
    }

    public RESTCustomException(String message) {
        super(message);
    }

    public RESTCustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
