package org.example.ds;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DataSourceCreator {
    private static final PGSimpleDataSource ds;

    static {
        ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/testdb");
        ds.setUser("root");
        ds.setPassword("root");
    }

    public static DataSource create() {
        return ds;
    }
}
