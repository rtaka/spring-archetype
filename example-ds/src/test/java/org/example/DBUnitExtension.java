package org.example;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Optional;

public class DBUnitExtension implements BeforeEachCallback, AfterEachCallback {
    private DBUnitRunner runner;

    private Optional<DBUnitSetup> findAnnotation(ExtensionContext context) {
        return AnnotationSupport.findAnnotation(context.getElement(), DBUnitSetup.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        findAnnotation(context).ifPresent((an) -> {
            try {
                var init = an.init();
                var expected = an.expected();
                var cleanup = an.cleanup();
                runner = new DBUnitRunner(init, expected, cleanup);
                runner.before();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        findAnnotation(context).ifPresent((an) -> {
            try {
                runner.after();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
