package com.example.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.exception.LiquibaseException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseRunner {

    public static void main(String[] args) throws LiquibaseException, SQLException {
        String databaseUrl = "jdbc:postgresql://localhost:5432/postgres";
        String databaseUser = "postgres";
        String databasePassword = "";
        String changelogFile = "db/changelog/db.changelog-master.xml";

        try (Connection connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);


            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);

//            update(liquibase, printWriter);

            int rollbackCount = 1; // это количество изменений, которые нужно откатить.
            rollback(liquibase, rollbackCount, printWriter);

            System.out.println(stringWriter.toString());
        }
    }

    public static void update(Liquibase liquibase, PrintWriter printWriter) throws LiquibaseException {
        liquibase.update("", printWriter);  // Выполняет миграции
        System.out.println("Database migration completed successfully.");
    }

    public static void rollback(Liquibase liquibase, int rollbackCount, PrintWriter printWriter) throws LiquibaseException {
        liquibase.rollback(rollbackCount, "", printWriter);  // Откат указанного количества изменений
        System.out.println("Rolled back " + rollbackCount + " changeset(s).");
    }
}
