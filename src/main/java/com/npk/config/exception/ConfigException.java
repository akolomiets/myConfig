package com.npk.config.exception;

/**
 * Base class for myConfig API exceptions
 */
public class ConfigException extends Exception {

    private static final long serialVersionUID = -2384115438548899854L;


    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

}
