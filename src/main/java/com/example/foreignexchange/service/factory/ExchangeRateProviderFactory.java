package com.example.foreignexchange.service.factory;

import com.example.foreignexchange.data.enums.ExchangeRateProvider;
import com.example.foreignexchange.provider.exchangerate.BaseExchangeRateProvider;
import com.example.foreignexchange.provider.exchangerate.ExchangeRateProviderExchangeRateApi;
import com.example.foreignexchange.provider.exchangerate.ExchangeRateProviderFrankfurter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

@Component
public class ExchangeRateProviderFactory extends ServiceFactory<ExchangeRateProvider, BaseExchangeRateProvider>{
    @Autowired
    public ExchangeRateProviderFactory(ApplicationContext context) {
        super(context);
        Map<ExchangeRateProvider, Type> map = Map.of(
                ExchangeRateProvider.exchangeRateApi, ExchangeRateProviderExchangeRateApi.class,
                ExchangeRateProvider.frankfurter, ExchangeRateProviderFrankfurter.class
        );
        setRegisteredServiceTypes(map);
     }
}
