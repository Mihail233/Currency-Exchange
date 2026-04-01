package org.example.currency_exchange.currency;

import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.commons.JdbcSqliteUtil;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteCurrencyDAO implements CurrencyDAO<Currency> {

    @Override
    public List<Currency> findCurrencies() throws DataBaseUnavailableException {
        try (Connection connection = HikariPool.getConnection()) {

            String query = "select * from Currencies";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) {
                Currency currency = JdbcSqliteUtil.getCurrencyFromResultSet(resultSet);
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public Currency findCurrencyByCode(String currencyCode) throws DataBaseUnavailableException, CurrencyNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {

            String query = """
                                select * from Currencies
                                where code = (?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new CurrencyNotFoundException("Валюта не найдена");
            }
            return JdbcSqliteUtil.getCurrencyFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    //делается сложный запрос select + insert, при этом возращается результат вставки -> если ничего не найдено, то не ставлено?
    //выполнить запрос если count(*) < 1,
    @Override
    public Currency saveCurrency(Currency currency) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                    
                    insert into Currencies (Code, FullName, Sign)
                    select (?), (?), (?) from Currencies
                    where not exists (select Code from Currencies where Code = (?))
                    limit 1
                    Returning *
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.setString(4, currency.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new CurrencyWithThisCodeExistsException("Валюта с таким кодом уже существует");
            }
            return JdbcSqliteUtil.getCurrencyFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }
}
