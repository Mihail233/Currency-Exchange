package org.example.currency_exchange.commons.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

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