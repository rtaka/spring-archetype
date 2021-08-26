package org.example.ds;

import org.dbunit.*;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class DBUnitRunner {
    private static final String PATH_PREFIX = "src/test/resources";
    private final DefaultPrepAndExpectedTestCase testCase;
    private final String initPath;
    private final String expectedPath;
    private final boolean cleanup;

    public DBUnitRunner(String initPath, String expectedPath, boolean cleanup) {
        testCase = new DefaultPrepAndExpectedTestCase();
        var ds = DataSourceCreator.create();
        var tester = new DataSourceDatabaseTester(ds);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        testCase.setDatabaseTester(tester);
        this.initPath = initPath;
        this.expectedPath = expectedPath;
        this.cleanup = cleanup;
    }

    private IDataSet toDataSet(String path) throws Exception {
        var file = Paths.get(PATH_PREFIX, path).toFile();
        return new FlatXmlDataSetBuilder().build(file);
    }

    private VerifyTableDefinition[] toDefinitions() throws Exception {
        if (expectedPath == null || expectedPath.equals("")) {
            return new VerifyTableDefinition[0];
        }
        var dataSet = toDataSet(expectedPath);
        var tables = dataSet.getTableNames();
        return Stream.of(tables).map((table) -> {
            try {
                var meta = dataSet.getTableMetaData(table);
                var columns = Arrays.stream(meta.getColumns())
                        .map(Column::getColumnName).toArray(String[]::new);
                return new VerifyTableDefinition(table, new String[0], columns);
            } catch (DatabaseUnitException e) {
                throw new DatabaseUnitRuntimeException(e);
            }
        }).toArray(VerifyTableDefinition[]::new);
    }

    public void before() throws Exception {
        var hasInitPath = initPath != null && !initPath.equals("");
        var hasExpectedPath = expectedPath != null && !expectedPath.equals("");
        if (hasInitPath) {
            testCase.setPrepDs(toDataSet(initPath));
        }
        if (hasExpectedPath) {
            testCase.setExpectedDs(toDataSet(expectedPath));
        }
        if (cleanup && (hasInitPath || hasExpectedPath)) {
            testCase.cleanupData();
        }
        if (hasInitPath) {
            testCase.setupData();
        }
    }

    public void after() throws Exception {
        if (expectedPath == null || expectedPath.equals("")) {
            return;
        }
        testCase.setVerifyTableDefs(toDefinitions());
        testCase.verifyData();
    }
}
