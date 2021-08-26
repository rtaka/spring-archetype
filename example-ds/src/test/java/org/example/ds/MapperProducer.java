package org.example.ds;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.AfterAll;

public class MapperProducer {
    static SqlSession session;

    static {
        var ds = DataSourceCreator.create();
        var env = new Environment("unit", new JdbcTransactionFactory(), ds);
        var config = new Configuration(env);
        config.addMappers("org.example");
        var factory = new SqlSessionFactoryBuilder().build(config);
        session = factory.openSession();
    }

    public static <T> T getMapper(Class<T> mapper) {
        return session.getMapper(mapper);
    }

    @AfterAll
    static void after() {
        session.close();
    }
}
