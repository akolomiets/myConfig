package com.npk.config.exception;

@SuppressWarnings("serial")
public class NotConfigClassException extends ConfigException {

    public NotConfigClassException() {
        super("Configuration class must be annotated with @Config");
    }

}
