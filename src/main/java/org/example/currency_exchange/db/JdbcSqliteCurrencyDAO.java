package org.example.currency_exchange.db;

import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.exception.*;
import org.example.currency_exchange.util.JdbcSqliteUtil;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

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
                    INSERT INTO Currencies (Code, FullName, Sign)
                    VALUES ((?), (?), (?))
                """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getName());
            preparedStatement.setString(3, currency.getSign());

            preparedStatement.executeUpdate();
            return findCurrencyByCode(currency.getCode());
        } catch (SQLiteException e) {
            SQLiteErrorCode sqLiteErrorCode = e.getResultCode();
            if (sqLiteErrorCode == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE) {
                throw new CurrencyPairWithThisCodeAlreadyExists("A currency pair with this code already exists");
            }
            throw new DataBaseUnavailableException("The database is unavailable");

        } catch (SQLException e) {
            throw new DataBaseUnavailableException("The database is unavailable");
        }
    }
}
