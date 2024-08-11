package com.example.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.exception.LiquibaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseRunner {

    public static void main(String[] args) {
        String databaseUrl = "jdbc:postgresql://localhost:5432/postgres";
        String databaseUser = "postgres";
        String databasePassword = "";
        String changelogFile = "db/changelog/db.changelog-master.xml";

        try (Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);
            liquibase.update("");  // Выполняет миграции

            System.out.println("Database migration completed successfully.");
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
