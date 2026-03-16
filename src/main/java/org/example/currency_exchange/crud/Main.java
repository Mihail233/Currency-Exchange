package org.example.currency_exchange.crud;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.currency_exchange.HikariPool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) throws SQLException {
        Test test = new Test();
        test.test();
    }
}

class Test {
    public void test() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/currencyError.property")){
            properties.load(fileInputStream);

            String url = properties.getProperty("g");
            System.out.println("URL: " + url);


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


//String URL = "jdbc:sqlite:CurrencyExchange.db";
//        HikariConfig hikariConfig = new HikariConfig("src/main/resources/hikari.properties");
//        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
//        try (Connection conn = HikariPool.getConnection()) {
//
//            Statement statement = conn.createStatement();
//            String query = """
//            select * from Currencies;
//            """;
//
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("Code"));
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//            System.out.println(e.getMessage());
//        }