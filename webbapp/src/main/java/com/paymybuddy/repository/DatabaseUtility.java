package com.paymybuddy.repository;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Log4j2
public class DatabaseUtility {


    public Connection getConnection() {
        try (
                InputStream input = ClassLoader.getSystemResourceAsStream("database.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.username");
            String pass = properties.getProperty("db.password");

            log.info("Connecting to database.");

            Connection con = DriverManager.getConnection(url, user, pass);
            log.info("Connection successful for user: " + user);
            return con;

        } catch (IOException e) {
            log.error("KO - I/O can't open properties. " + e);
        } catch (SQLException e) {
            log.error("KO - can't connect to database. " + e);
        }
        return null;
    }
}
