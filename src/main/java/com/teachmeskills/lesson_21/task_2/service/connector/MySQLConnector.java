package com.teachmeskills.lesson_21.task_2.service.connector;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnector {

    private static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DATA_BASE_CONNECTIVITY_PROPERTY_FILE_PATH = "src/main/resources/database.properties";
    private Properties properties;
    private Connection connection;

    public MySQLConnector() {
        init();
    }

//    // закрытие потоков класса + класса UserCRUDService
//    public static void closeThreadsClassMySQLConnector() {
//        try {
//            UserCRUDService service = new UserCRUDService();
//            service.closeThreadsClassCRUD();
//            connection.close();
//            properties = null;
//            service = null;
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//    }

    private void init() {
        try {
            Class.forName(MYSQL_DRIVER_CLASS_NAME).newInstance();
            properties = loadProperties();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("--Unable to load MySQL Driver!--");
            e.printStackTrace();

        }
    }

    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        try {
            return connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password")
            );
        } catch (SQLException e) {
            System.out.println("----Unable to ger connection to MySQL schema!----");
            e.printStackTrace();
        }

        return null;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get(DATA_BASE_CONNECTIVITY_PROPERTY_FILE_PATH))) {
            properties.load(input);

        } catch (IOException e) {
            System.out.println("------Unable to ger connection to MySQL schema!------");
        }
        return properties;
    }

}
