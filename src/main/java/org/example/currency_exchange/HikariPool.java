package org.example.currency_exchange;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

public class HikariPool {
    private static final String PROPERTY_FILE_NAME = "hikari.properties";
    private static final HikariConfig HIKARI_CONFIG;
    private static final HikariDataSource DATA_SOURCE;

    static {
        HIKARI_CONFIG = new HikariConfig(PROPERTY_FILE_NAME);
        HIKARI_CONFIG.addDataSourceProperty("foreign_keys", "on");
        HIKARI_CONFIG.setDriverClassName("org.sqlite.JDBC");
        DATA_SOURCE = new HikariDataSource(HIKARI_CONFIG);
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
