package org.example;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.util.Arrays;

public class MapperInjectExtension implements TestInstancePostProcessor, AfterAllCallback {
    private SqlSession session;

    private SqlSession openSession() {
        var ds = DataSourceCreator.create();
        var env = new Environment("unit", new JdbcTransactionFactory(), ds);
        var config = new Configuration(env);
        config.addMappers("org.example");
        var factory = new SqlSessionFactoryBuilder().build(config);
        return factory.openSession(true);
    }

    @Override
    public void postProcessTestInstance(Object o, ExtensionContext context) throws Exception {
        session = openSession();
        var fields = o.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter((f) -> f.isAnnotationPresent(MapperInject.class))
                .forEach((f) -> {
                    try {
                        f.setAccessible(true);
                        f.set(o, session.getMapper(f.getType()));
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        throw new IllegalStateException(e);
                    }
                });
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (session != null) {
            session.close();
        }
    }
}
