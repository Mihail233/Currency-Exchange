package org.example.currency_exchange.exchange_rates;

import org.example.currency_exchange.Currency;
import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.*;
import org.example.currency_exchange.util.JdbcSqliteUtil;

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
    public ExchangeRate findExchangeRateByCurrencyPair(String base, String target) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {
            connection.setAutoCommit(false);

            String query = """
                    select * from ExchangeRates
                    where BaseCurrencyId  = (Select id from Currencies where Code = (?))
                    and TargetCurrencyId = (Select id from Currencies where Code = (?));
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, base);
            preparedStatement.setString(2, target);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new ExchangeRateNotFoundException("Обменный курс для пары не найден");
            }

            return getExchangeRateFromResultSet(resultSet, connection);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public ExchangeRate saveExchangeRate(String base, String target, String rate) throws DataBaseUnavailableException, OneOrBothCurrenciesFromPairNotExistInDatabase, CurrencyPairWithThisCodeAlreadyExists {
        try (Connection connection = HikariPool.getConnection()) {
            //проверка на количество записей (2)
            //вставка с ретернингом -> если запись уже была не вставиться
            String queryToCurrencies = """
                    select * from Currencies
                    where code = (?) or code = (?)
                    """;
            PreparedStatement preparedStatementToCurrencies = connection.prepareStatement(queryToCurrencies);
            preparedStatementToCurrencies.setString(1, base);
            preparedStatementToCurrencies.setString(2, target);
            ResultSet resultSetFromCurrencies = preparedStatementToCurrencies.executeQuery();

            //а если вернуть Currency и пройти по массиву, и просто вернуть IDs
            int requiredCountOfCurrency = 2;
            List<Currency> currencies = JdbcSqliteUtil.getCurrenciesFromResultSet(resultSetFromCurrencies);
            checkForRequiredCount(requiredCountOfCurrency, currencies);
            int baseId = getIdFromCurrenciesByCode(currencies, base);
            int targetId = getIdFromCurrenciesByCode(currencies, target);

            String queryToExchangeRates = """
                    insert into ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
                    select (?), (?), (?)
                    where not exists (select 1 from ExchangeRates where BaseCurrencyId = (?) AND TargetCurrencyId = (?))
                    limit 1
                    Returning *
                    """;
            PreparedStatement preparedStatementToExchangeRates = connection.prepareStatement(queryToExchangeRates);
            preparedStatementToExchangeRates.setInt(1, baseId);
            preparedStatementToExchangeRates.setInt(2, targetId);
            preparedStatementToExchangeRates.setString(3, rate);
            preparedStatementToExchangeRates.setInt(4, baseId);
            preparedStatementToExchangeRates.setInt(5, targetId);

            ResultSet resultSetFromExchangeRate = preparedStatementToExchangeRates.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSetFromExchangeRate)) {
                throw new CurrencyPairWithThisCodeAlreadyExists("Текущая пара уже существует");
            }
            return getExchangeRateFromResultSet(resultSetFromExchangeRate, connection);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
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

    private Currency findCurrencyById(int currencyId, Connection connection) throws SQLException {
        String queryWithCurrencyId = """
                            select * from Currencies
                            where ID = (?)
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(queryWithCurrencyId);
        preparedStatement.setInt(1, currencyId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return JdbcSqliteUtil.getCurrencyFromResultSet(resultSet);
    }

    private void checkForRequiredCount(int requiredCountOfCurrency, List<Currency> currencyPair) throws OneOrBothCurrenciesFromPairNotExistInDatabase {
        if (currencyPair.size() != requiredCountOfCurrency) {
            throw new OneOrBothCurrenciesFromPairNotExistInDatabase("Не найдено одна или обе валюты");
        }
    }

    private int getIdFromCurrenciesByCode(List<Currency> currencies, String currencyCode) {
        int id = -1;
        for (Currency currency: currencies) {
            if (currency.getCode().equals(currencyCode)) {
                id = currency.getId();
            }
        }
        return id;
    }
}
