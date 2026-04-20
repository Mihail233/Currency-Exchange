package org.example.currency_exchange.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariPool {
    public static final HikariDataSource DATA_SOURCE;
    private static final String PROPERTY_FILE_NAME = "hikari.properties";
    private static final HikariConfig HIKARI_CONFIG;

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
