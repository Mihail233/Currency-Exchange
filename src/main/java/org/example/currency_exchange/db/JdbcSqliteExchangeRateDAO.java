package org.example.currency_exchange.db;

import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exception.ExchangeRateNotFoundException;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.util.JdbcSqliteUtil;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSqliteExchangeRateDAO implements ExchangeRateDAO<ExchangeRate> {
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
            throw new DataBaseUnavailableException("The database is unavailable");
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
                throw new ExchangeRateNotFoundException("Exchange rate not found for the pair");
            }

            return getExchangeRateFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("The database is unavailable");
        }
    }

    @Override
    public ExchangeRate saveExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) throws DataBaseUnavailableException, CurrencyPairWithThisCodeAlreadyExists {
        try (Connection connection = HikariPool.getConnection()) {

            String query = """
                            insert into ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
                            select base.ID, target.ID, (?)
                            FROM Currencies base, Currencies target
                            Where base.Code = (?) AND target.Code = (?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBigDecimal(1, rate);
            preparedStatement.setString(2, baseCurrencyCode);
            preparedStatement.setString(3, targetCurrencyCode);
            preparedStatement.executeUpdate();

            return findExchangeRateByCurrencyPair(baseCurrencyCode, targetCurrencyCode);

        } catch (SQLiteException e) {
            SQLiteErrorCode sqLiteErrorCode = e.getResultCode();
            if (sqLiteErrorCode == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE) {
                throw new CurrencyPairWithThisCodeAlreadyExists("A currency pair with this code already exists");
            }
            throw new DataBaseUnavailableException("The database is unavailable");

        } catch (ExchangeRateNotFoundException e) {
            throw new CurrencyNotFoundException("One (or both) currencies from the currency pair do not exist in the database");

        } catch (SQLException e) {
            throw new DataBaseUnavailableException("The database is unavailable");
        }
    }

    @Override
    public ExchangeRate updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        try (Connection connection = HikariPool.getConnection()) {
            String query = """
                    update ExchangeRates
                    Set Rate = (?)
                    where BaseCurrencyId = (select ID from Currencies where Code = (?)) AND
                    	  TargetCurrencyId = (select ID from Currencies where Code = (?))
                    """;


            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBigDecimal(1, rate);
            preparedStatement.setString(2, baseCurrencyCode);
            preparedStatement.setString(3, targetCurrencyCode);

            preparedStatement.executeUpdate();

            return findExchangeRateByCurrencyPair(baseCurrencyCode, targetCurrencyCode);
        } catch (SQLException e) {
            throw new DataBaseUnavailableException("The database is unavailable");
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
            throw new DataBaseUnavailableException("The database is unavailable");
        }
    }

    private ExchangeRate getExchangeRateFromResultSet(ResultSet resultSet) throws SQLException {
        String base = "base";
        String target = "target";
        return new ExchangeRate(
                resultSet.getInt("ID"),
                new Currency(resultSet.getInt(base + "ID"),
                        resultSet.getString(base + "FullName"),
                        resultSet.getString(base + "Code"),
                        resultSet.getString(base + "Sign")
                ),
                new Currency(resultSet.getInt(target + "ID"),
                        resultSet.getString(target + "FullName"),
                        resultSet.getString(target + "Code"),
                        resultSet.getString(target + "Sign")
                ),
                new BigDecimal(resultSet.getString("Rate"))
        );
    }

    private List<ExchangeRate> getExchangeRatesForIndirectExchange(ResultSet resultSet) throws SQLException, ExchangeRateNotFoundException {
        int requiredCount = 2;
        int count = 0;
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        while (count < requiredCount) {
            if (JdbcSqliteUtil.isResultNotFound(resultSet)) {
                throw new ExchangeRateNotFoundException("Exchange rate not found for the pair");
            }
            ExchangeRate exchangeRate = getExchangeRateFromResultSet(resultSet);
            exchangeRates.add(exchangeRate);
            count++;
        }
        return exchangeRates;
    }
}
