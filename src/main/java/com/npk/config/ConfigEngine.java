package com.npk.config;

import com.npk.config.annotation.Config;
import com.npk.config.exception.ConfigException;
import com.npk.config.exception.NotConfigClassException;

import javax.annotation.Nonnull;

public class ConfigEngine<T> {

    @Nonnull
    public static <T> ConfigEngine<T> getInstance(@Nonnull Class<T> configClass) throws ConfigException {
        ConfigEngine<T> configEngine = new ConfigEngine<T>(configClass);
        configEngine.validateConfigClass();
        return configEngine;
    }


    private final Class<T> configClass;

    protected ConfigEngine(@Nonnull Class<T> configClass) {
        this.configClass = configClass;
    }


    protected void validateConfigClass() throws ConfigException {
        validateConfigAnnotation(configClass);
    }


    private void validateConfigAnnotation(Class<T> clazz) throws NotConfigClassException {
        if (clazz.getAnnotation(Config.class) == null) {
            throw new NotConfigClassException();
        }
    }

}
