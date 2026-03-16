package org.example.currency_exchange.currency;

import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.crud.CurrencyDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteCurrencyDAO implements CurrencyDAO<Currency> {

    public List<Currency> findCurrencies() throws DataBaseUnavailableException {
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = HikariPool.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "select * from Currencies";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Currency currency = new Currency
                        (
                                resultSet.getInt("ID"),
                                resultSet.getString("Code"),
                                resultSet.getString("FullName"),
                                resultSet.getString("Sign")
                        );
                currencies.add(currency);
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new DataBaseUnavailableException("База данных недоступна");
        }
        return currencies;
    }

    @Override
    public Currency findByCode(String code) {
        return null;
    }

    @Override
    public void save() {
    }
}
