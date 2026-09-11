package aji.intern.core.error.exception.key;

import aji.intern.core.error.ServiceException;

public class ServiceIdAlreadyReserved extends ServiceException {
    public ServiceIdAlreadyReserved(String serviceId) {
        super("12", String.format("Service with id (%s) is already reserved", serviceId), "ws", true);
    }
}
