package org.example.currency_exchange.currency;

import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.UnknownException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteCurrencyDAO implements CurrencyDAO<Currency> {

    @Override
    public List<Currency> findCurrencies() throws DataBaseUnavailableException {
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = HikariPool.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "select * from Currencies";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Currency currency = getCurrencyFromResultSet(resultSet);
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public Currency findByCode(String code) throws DataBaseUnavailableException, CurrencyNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                                select * from Currencies
                                where code = (?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            checkCurrencyAvailability(resultSet);
            return getCurrencyFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public void save() {
    }

    public Currency getCurrencyFromResultSet(ResultSet resultSet) throws SQLException {
        return new Currency
                (
                        resultSet.getInt("ID"),
                        resultSet.getString("Code"),
                        resultSet.getString("FullName"),
                        resultSet.getString("Sign")
                );
    }

    public void checkCurrencyAvailability(ResultSet resultSet) throws SQLException, CurrencyNotFoundException {
        if (!resultSet.next()) {
            throw new CurrencyNotFoundException("Валюта не найдена");
        }
    }
}
