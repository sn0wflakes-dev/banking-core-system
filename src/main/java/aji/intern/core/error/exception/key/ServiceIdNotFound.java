package aji.intern.core.error.exception.key;

import aji.intern.core.error.ServiceException;

public class ServiceIdNotFound extends ServiceException {
    public ServiceIdNotFound(String code, String message, String serviceId) {
        super("11", String.format("Service with id (%s) is not found", serviceId), "ws", true);
    }
}
