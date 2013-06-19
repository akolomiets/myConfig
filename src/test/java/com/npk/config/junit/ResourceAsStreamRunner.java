package com.npk.config.junit;

import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class ResourceAsStreamRunner extends BlockJUnit4ClassRunner {

    private boolean isAnnotatedWithResourceAsStream;
    private InputStream inputStream;


    public ResourceAsStreamRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        try {
            beforeRunChild(method);
            super.runChild(method, notifier);
            afterRunChild();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
        if (annotation == Test.class) {
            validateTestMethods(isStatic, errors);
        } else {
            super.validatePublicVoidNoArgMethods(annotation, isStatic, errors);
        }
    }

    @Override
    protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
        return isAnnotatedWithResourceAsStream
            ? new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    method.invokeExplosively(test, inputStream);
                }
            }
            : super.methodInvoker(method, test);
    }


    private void validateTestMethods(boolean isStatic, List<Throwable> errors) {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);
        for (FrameworkMethod eachTestMethod : methods) {
            ResourceAsStream annotation = eachTestMethod.getAnnotation(ResourceAsStream.class);
            if (annotation != null) {
                eachTestMethod.validatePublicVoid(isStatic, errors);

                Method method = eachTestMethod.getMethod();
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length != 1 || parameterTypes[0] != InputStream.class) {
                    errors.add(new Exception("Method " + method.getName() + " should have one InputStream type parameter"));
                }
            } else {
                eachTestMethod.validatePublicVoidNoArg(isStatic, errors);
            }
        }
    }

    private void beforeRunChild(FrameworkMethod method) {
        ResourceAsStream annotation = method.getAnnotation(ResourceAsStream.class);
        if (annotation != null) {
            Class<?> testClass = getTestClass().getJavaClass();
            inputStream = testClass.getResourceAsStream(annotation.value());
            isAnnotatedWithResourceAsStream = true;
        }
    }

    private void afterRunChild() throws Exception {
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
        isAnnotatedWithResourceAsStream = false;
    }

}
