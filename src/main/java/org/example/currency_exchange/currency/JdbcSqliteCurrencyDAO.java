package org.example.currency_exchange.currency;

import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.util.JdbcSqliteUtil;

import java.sql.*;
import java.util.List;

public class JdbcSqliteCurrencyDAO implements CurrencyDAO<Currency> {

    @Override
    public List<Currency> findCurrencies() throws DataBaseUnavailableException {
        try (Connection connection = HikariPool.getConnection()) {

            String query = "select * from Currencies";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return JdbcSqliteUtil.getCurrenciesFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("The database is unavailable");
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
                throw new CurrencyNotFoundException("Currency not found");
            }
            return JdbcSqliteUtil.getCurrencyFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("The database is unavailable");
        }
    }

    @Override
    public Currency saveCurrency(Currency currency) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                    
                    insert into Currencies (Code, FullName, Sign)
                    select (?), (?), (?)
                    where not exists (select 1 from Currencies where Code = (?))
                    limit 1
                    Returning ID
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.setString(4, currency.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new CurrencyWithThisCodeExistsException("A currency with this code exists");
            }
            int id = resultSet.getInt("ID");
            currency.setId(id);
            return currency;
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("The database is unavailable");
        }
    }
}
