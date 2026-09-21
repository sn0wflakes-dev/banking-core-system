package aji.intern.core.error.exception.key;

import aji.intern.core.error.ServiceException;

public class ServiceIdNotFound extends ServiceException {
    public ServiceIdNotFound(String serviceId) {
        super("04", String.format("Service with id (%s) is not found", serviceId), "ws", true);
    }
}
