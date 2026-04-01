package org.example.currency_exchange.commons;

import org.example.currency_exchange.currency.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcSqliteUtil {
    public static Currency getCurrencyFromResultSet(ResultSet resultSet) throws SQLException {
        return new Currency
                (
                        resultSet.getInt("ID"),
                        resultSet.getString("Code"),
                        resultSet.getString("FullName"),
                        resultSet.getString("Sign")
                );
    }

    public static boolean isResultNotFound(ResultSet resultSet) throws SQLException {
        return !resultSet.next();
    }
}
