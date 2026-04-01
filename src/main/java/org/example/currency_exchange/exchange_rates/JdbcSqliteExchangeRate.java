package org.example.currency_exchange.exchange_rates;

import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.commons.JdbcSqliteUtil;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteExchangeRate implements ExchangeRateDAO<ExchangeRate> {
    @Override
    public List<ExchangeRate> findExchangeRates() throws DataBaseUnavailableException {
        try (Connection connection = HikariPool.getConnection()) {
            connection.setAutoCommit(false);

            String queryToExchangeRate = """
                                select * from ExchangeRates
                    """;
            Statement statement = connection.createStatement();
            ResultSet exchangeRateResultSet = statement.executeQuery(queryToExchangeRate);
            List<ExchangeRate> exchangeRates = new ArrayList<>();

            while (exchangeRateResultSet.next()) {
                exchangeRates.add(getExchangeRateFromResultSet(exchangeRateResultSet, connection));
            }

            connection.commit();
            return exchangeRates;
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public ExchangeRate findExchangeRateByCurrencyPair(String fromCurrency, String toCurrency) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {
            connection.setAutoCommit(false);

            String query = """
                    select * from ExchangeRates
                    where BaseCurrencyId  = (Select id from Currencies where Code = (?))
                    and TargetCurrencyId = (Select id from Currencies where Code = (?));
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fromCurrency);
            preparedStatement.setString(2, toCurrency);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new ExchangeRateNotFoundException("Обменный курс для пары не найден");
            }
            connection.commit();
            return getExchangeRateFromResultSet(resultSet, connection);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public ExchangeRate saveExchangeRate(Currency currency) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        return null;
    }

    @Override
    public void updateExchangeRate() {

    }

    private ExchangeRate getExchangeRateFromResultSet(ResultSet resultSet, Connection connection) throws SQLException {
        int id = resultSet.getInt("ID");
        String rate = resultSet.getString("Rate");
        int baseCurrencyId = resultSet.getInt("BaseCurrencyId");
        int targetCurrencyId = resultSet.getInt("TargetCurrencyId");

        Currency baseCurrency = findCurrencyById(baseCurrencyId, connection);
        Currency targetCurrency = findCurrencyById(targetCurrencyId, connection);

        return new ExchangeRate(id, baseCurrency, targetCurrency, rate);
    }

    private Currency findCurrencyById(int baseCurrencyId, Connection connection) throws SQLException {
        String queryWithCurrencyId = """
                            select * from Currencies
                            where ID = (?)
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(queryWithCurrencyId);
        preparedStatement.setInt(1, baseCurrencyId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return JdbcSqliteUtil.getCurrencyFromResultSet(resultSet);
    }
}
