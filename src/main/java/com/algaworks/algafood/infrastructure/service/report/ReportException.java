package com.algaworks.algafood.infrastructure.service.report;

public class ReportException extends RuntimeException {

    public ReportException( String msg, Throwable cause) {
        super(msg, cause);
    }

    public ReportException(String msg) {
        super(msg);
    }
}
