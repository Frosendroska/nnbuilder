package org.hse.nnbuilder.exception;

public class DatasetNotFoundException extends Exception {
    public DatasetNotFoundException(Exception e, String errorMessage) {
        super(errorMessage,e);
    }
}
