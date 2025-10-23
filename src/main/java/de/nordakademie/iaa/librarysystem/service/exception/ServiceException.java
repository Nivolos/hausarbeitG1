package de.nordakademie.iaa.librarysystem.service.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(Throwable cause){
        super(cause);
    }

    public ServiceException(String msg){
        super(msg);
    }
}