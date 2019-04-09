package com.schcilin.tcctransactions.exception;

import com.schcilin.tcctransactions.error.TccErrorResponse;

/**
 * Author: schicilin
 * Date: 2019/4/9 18:50
 * Content:
 */
public class PartialConfirmException extends RuntimeException {
    private static final long serialVersionUID = 3159261668472213935L;

    private TccErrorResponse errorResponse;

    public PartialConfirmException() {
        super();
    }

    public PartialConfirmException(String message) {
        super(message);
    }

    public PartialConfirmException(String message, TccErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public PartialConfirmException(String message, Throwable cause) {
        super(message, cause);
    }

    public PartialConfirmException(Throwable cause) {
        super(cause);
    }

    protected PartialConfirmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TccErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
