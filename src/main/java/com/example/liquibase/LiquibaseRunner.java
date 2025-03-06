package com.example.liquibase;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseRunner {

    private static final String URL = "jdbc:postgresql://localhost:5432/liquibase_sandbox";
    private static final String USER = "romanya";
    private static final String PASSWORD = "romanya";
    private static final String CHANGELOG_MASTER_XML = "db/changelog/changelog-master.xml";

    public static void main(String[] args) throws LiquibaseException, SQLException {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            var liquibase = new Liquibase(CHANGELOG_MASTER_XML, new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts());
//            liquibase.rollback(1, "");

        }
    }


}
