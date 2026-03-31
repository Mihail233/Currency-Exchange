package org.example.currency_exchange.commons.dao;

import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                    insert INTO ExchangeRates(BaseCurrencyId, TargetCurrencyId)
                    Values(2, 1)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

             preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
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