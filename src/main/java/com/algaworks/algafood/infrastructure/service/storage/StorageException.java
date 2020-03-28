package com.algaworks.algafood.infrastructure.service.storage;

public class StorageException extends RuntimeException {

    private static final long serialVersionUID = -1496029370216858587L;

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(String message) {
        super(message);
    }
}
