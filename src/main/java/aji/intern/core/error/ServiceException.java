package aji.intern.core.error;

public abstract class ServiceException extends RuntimeException {
    private final String code;           // e.g. "99" or specific err code
    private final String origin;         // e.g. "ws", "upstream", "core-banking"
    private final boolean clientFault;   // true -> SOAP-ENV:Client, false -> SOAP-ENV:Server

    protected ServiceException(String code, String message) {
        this(code, message, "ws", true);
    }

    protected ServiceException(String code, String message, String origin, boolean clientFault) {
        super(message);
        this.code = code;
        this.origin = origin;
        this.clientFault = clientFault;
    }

    public String getCode() {
        return code;
    }

    public String getOrigin() {
        return origin;
    }

    public boolean isClientFault() {
        return clientFault;
    }
}
