package com.npk.config;

import com.npk.config.annotation.Config;
import com.npk.config.exception.NotConfigClassException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ConfigEngineTest {

    // region Config class declarations

    private static class BadConfigClass {
        /* Some fields ... */
    }

    @Config
    private static class GoodConfigClass {
        /* Some fields ... */
    }

    // endregion

    @Test(expected = NotConfigClassException.class)
    public void badConfigClass__onGetInstance__shouldThrowConfigException() throws Exception {
        ConfigEngine.getInstance(BadConfigClass.class);
    }

    @Test
    public void goodConfigClass__onGetInstance__shouldBeInstanceOfConfigEngine() throws Exception {
        ConfigEngine<GoodConfigClass> configEngine = ConfigEngine.getInstance(GoodConfigClass.class);
        assertThat(configEngine, notNullValue());
    }

}
