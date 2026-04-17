package org.example.currency_exchange;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.currency.CurrencyService;
import org.example.currency_exchange.exchange_rate.ExchangeRate;
import org.example.currency_exchange.exchange_rate.JdbcSqliteExchangeRateDAO;
import org.example.currency_exchange.exchange_rate.service.ExchangeRateService;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ExchangeRateDAO<ExchangeRate> exchangeRateDAO = new JdbcSqliteExchangeRateDAO();
        CurrencyDAO<Currency> currencyDAO = new JdbcSqliteCurrencyDAO();
        CurrencyService currencyService = new CurrencyService(currencyDAO);
        ExchangeRateService exchangeRateService = new ExchangeRateService();

        ServletContext context = sce.getServletContext();
        context.setAttribute("currencyService", currencyService);
        context.setAttribute("exchangeRateService", exchangeRateService);
        context.setAttribute("exchangeRateDAO", exchangeRateDAO);
        context.setAttribute("currencyDAO", currencyDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HikariPool.DATA_SOURCE.close();
    }
}
