package org.example.currency_exchange.util;

import org.example.currency_exchange.entity.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteUtil {
    public static Currency getCurrencyFromResultSet(ResultSet resultSet) throws SQLException {
        return new Currency
                (
                        resultSet.getInt("ID"),
                        resultSet.getString("FullName"),
                        resultSet.getString("Code"),
                        resultSet.getString("Sign")
                );
    }

    public static boolean isResultNotFound(ResultSet resultSet) throws SQLException {
        return !resultSet.next();
    }

    public static List<Currency> getCurrenciesFromResultSet(ResultSet resultSet) throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        while (resultSet.next()) {
            Currency currency = JdbcSqliteUtil.getCurrencyFromResultSet(resultSet);
            currencies.add(currency);
        }
        return currencies;
    }
}
