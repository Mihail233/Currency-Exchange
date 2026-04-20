package org.example.currency_exchange.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.db.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.dto.exchange.service.currency.CurrencyService;
import org.example.currency_exchange.db.HikariPool;
import org.example.currency_exchange.dto.exchange.service.exchange.ExchangeService;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.db.JdbcSqliteExchangeRateDAO;
import org.example.currency_exchange.dto.exchange.service.exchange.ExchangeRateService;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ExchangeRateDAO<ExchangeRate> exchangeRateDAO = new JdbcSqliteExchangeRateDAO();
        CurrencyDAO<Currency> currencyDAO = new JdbcSqliteCurrencyDAO();
        CurrencyService currencyService = new CurrencyService(currencyDAO);
        ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDAO);
        ExchangeService exchangeService = new ExchangeService(exchangeRateDAO);

        ServletContext context = sce.getServletContext();
        context.setAttribute("exchangeRateDAO", exchangeRateDAO);
        context.setAttribute("currencyDAO", currencyDAO);
        context.setAttribute("currencyService", currencyService);
        context.setAttribute("exchangeRateService", exchangeRateService);
        context.setAttribute("exchangeService", exchangeService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HikariPool.DATA_SOURCE.close();
    }
}
