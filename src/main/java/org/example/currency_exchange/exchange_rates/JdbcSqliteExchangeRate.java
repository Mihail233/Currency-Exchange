package org.example.currency_exchange.exchange_rates;

import org.example.currency_exchange.Currency;
import org.example.currency_exchange.HikariPool;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.util.JdbcSqliteUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteExchangeRate implements ExchangeRateDAO<ExchangeRate> {
    @Override
    public List<ExchangeRate> findExchangeRates() throws DataBaseUnavailableException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                                select er.ID,
                                c1.ID as baseID,
                                c1.FullName as baseFullName,
                                c1.code as baseCode,
                                c1.sign as baseSign,
                                c2.ID as targetID,
                                c2.FullName as targetFullName,
                                c2.code as targetCode,
                                c2.sign as targetSign,
                                er.Rate
                                from ExchangeRates as er
                                join Currencies c1 on c1.ID = er.BaseCurrencyId
                                join Currencies c2 on c2.ID = er.TargetCurrencyId
                    """;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<ExchangeRate> exchangeRates = new ArrayList<>();
            while (resultSet.next()) {
                ExchangeRate exchangeRate = getExchangeRateFromResultSet(resultSet);
                exchangeRates.add(exchangeRate);
            }
            return exchangeRates;
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public ExchangeRate findExchangeRateByCurrencyPair(String baseCurrencyCode, String targetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {

            String query = """
                    select er.ID,
                    c1.ID as baseID,
                    c1.FullName as baseFullName,
                    c1.code as baseCode,
                    c1.sign as baseSign,
                    c2.ID as targetID,
                    c2.FullName as targetFullName,
                    c2.code as targetCode,
                    c2.sign as targetSign,
                    er.Rate
                    from ExchangeRates as er
                    join Currencies c1 on c1.ID = er.BaseCurrencyId
                    join Currencies c2 on c2.ID = er.TargetCurrencyId
                    where baseCode = (?) and targetCode = (?);
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new ExchangeRateNotFoundException("Обменный курс для пары не найден");
            }

            return getExchangeRateFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyPairWithThisCodeAlreadyExists {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                    insert into ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
                    select (?), (?), (?)
                    where not exists (select 1 from ExchangeRates where BaseCurrencyId = (?) AND TargetCurrencyId = (?))
                    limit 1
                    Returning ID
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            int baseId = exchangeRate.getBaseCurrency().getId();
            int targetId = exchangeRate.getTargetCurrency().getId();
            String rate = exchangeRate.getRate();

            preparedStatement.setInt(1, baseId);
            preparedStatement.setInt(2, targetId);
            preparedStatement.setString(3, rate);
            preparedStatement.setInt(4, baseId);
            preparedStatement.setInt(5, targetId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new CurrencyPairWithThisCodeAlreadyExists("Текущая пара уже существует");
            }

            int exchangeRateId = resultSet.getInt("ID");
            exchangeRate.setId(exchangeRateId);
            return exchangeRate;
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                    update ExchangeRates
                    Set Rate = (?)
                    where BaseCurrencyId = (?) AND TargetCurrencyId = (?)
                    RETURNING ID;
                    """;


            PreparedStatement preparedStatement = connection.prepareStatement(query);

            int baseId = exchangeRate.getBaseCurrency().getId();
            int targetId = exchangeRate.getTargetCurrency().getId();
            String rate = exchangeRate.getRate();

            preparedStatement.setString(1, rate);
            preparedStatement.setInt(2, baseId);
            preparedStatement.setInt(3, targetId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new ExchangeRateNotFoundException("Валютная пара не существует");
            }

            exchangeRate.setId(resultSet.getInt("ID"));
            return exchangeRate;
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    @Override
    public List<ExchangeRate> findIndirectExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                    select er.ID,
                    	c1.ID as baseID,
                    	c1.FullName as baseFullName,
                    	c1.code as baseCode,
                    	c1.sign as baseSign,
                    	c2.ID as targetID,
                    	c2.FullName as targetFullName,
                    	c2.code as targetCode,
                    	c2.sign as targetSign,
                    	er.Rate
                    from ExchangeRates as er
                    join Currencies c1 on c1.ID = er.BaseCurrencyId
                    join Currencies c2 on c2.ID = er.TargetCurrencyId
                    where baseCode = (
                    	select c3.code as baseCode
                    	from ExchangeRates er2
                    	join Currencies c3 on c3.ID = er2.BaseCurrencyId
                    	join Currencies c4 on c4.ID = er2.TargetCurrencyId
                    	where c4.Code = (?)
                    	INTERSECT
                    	select c5.code as baseCode
                    	from ExchangeRates er3
                    	join Currencies c5 on c5.ID = er3.BaseCurrencyId
                    	join Currencies c6 on c6.ID = er3.TargetCurrencyId
                    	where c6.Code  = (?)
                    	limit 1
                    )
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getExchangeRatesForIndirectExchange(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("База данных недоступна");
        }
    }

    private ExchangeRate getExchangeRateFromResultSet(ResultSet resultSet) throws SQLException {
        String base = "base";
        String target = "target";
        return new ExchangeRate(
                resultSet.getInt("ID"),
                new Currency(resultSet.getInt(base + "ID"),
                        resultSet.getString(base + "Code"),
                        resultSet.getString(base + "FullName"),
                        resultSet.getString(base + "Sign")
                ),
                new Currency(resultSet.getInt(target + "ID"),
                        resultSet.getString(target + "Code"),
                        resultSet.getString(target + "FullName"),
                        resultSet.getString(target + "Sign")
                ),
                resultSet.getString("Rate")
        );
    }

    private List<ExchangeRate> getExchangeRatesForIndirectExchange(ResultSet resultSet) throws SQLException, ExchangeRateNotFoundException {
        int requiredCount = 2;
        int count = 0;
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        while (count < requiredCount) {
            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new ExchangeRateNotFoundException("Валютная пара не существует");
            }
            ExchangeRate exchangeRate = getExchangeRateFromResultSet(resultSet);
            exchangeRates.add(exchangeRate);
            count++;
        }
        return exchangeRates;
    }
}
