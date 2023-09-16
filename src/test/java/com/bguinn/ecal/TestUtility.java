package com.bguinn.ecal;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class TestUtility {

    private static final String DB_URL = "jdbc:hsqldb:eventsTestDB;shutdown=true";
    private static final String DB_USERNAME = "sa";
    private static final String SQL_CHANGE_PASSWORD = "ALTER USER sa SET PASSWORD 'Pa55word'";
    private static final String SQL_CREATE_TABLE_EVENTS = "DROP SCHEMA PUBLIC CASCADE; CREATE TABLE EVENTS (id BIGINT NOT NULL, name VARCHAR(255), start_date DATETIME, end_date DATETIME, PRIMARY KEY (id))";

    public static void initialiseDatabase() {

        removeDB("eventsTestDB");

        registerDB();

        changePassword();

        populateEventsTable();

    }

    static void populateEventsTable() {

        try (Connection connection = DriverManager
                .getConnection(DB_URL, "sa", "Pa55word")) {

            Statement statement = connection.createStatement();
            statement.execute(SQL_CREATE_TABLE_EVENTS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    static void changePassword() {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, "")) {

            Statement statement = connection.createStatement();
            statement.execute(SQL_CHANGE_PASSWORD);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void removeDB(String dbName) {
        Arrays.asList(".properties", ".script").forEach(ext -> {
            File dnFile = new File(dbName + ext);
            if (dnFile.exists() && !dnFile.isDirectory()) {
                dnFile.delete();
            }
        });
    }

    public static void registerDB() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
