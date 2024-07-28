package com.example.foreignexchange.service.factory;

import com.example.foreignexchange.data.enums.ExchangeRateProvider;
import com.example.foreignexchange.exception.MissingServiceException;
import com.example.foreignexchange.exception.UnsupportedServiceTypeException;
import com.example.foreignexchange.provider.exchangerate.ExchangeRateProviderExchangeRateApi;
import com.example.foreignexchange.provider.exchangerate.ExchangeRateProviderFrankfurter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ExchangeRateProviderFactoryTest {
    @Autowired
    ExchangeRateProviderFactory exchangeRateProviderFactory;

    private static Stream<Arguments> should_throw_unsupported_service_type_exception() {
        return Stream.of(
                Arguments.of(ExchangeRateProvider.exchangeRateApi, ExchangeRateProvider.exchangeRateApi.toString()),
                Arguments.of(ExchangeRateProvider.frankfurter, ExchangeRateProvider.frankfurter.toString()),
                Arguments.of(null, null)
        );
    }

    @Test
    void should_return_exchange_rate_provider_exchangeRateApi_object() {
        exchangeRateProviderFactory.setRegisteredServiceTypes(
                Map.of(
                        ExchangeRateProvider.exchangeRateApi, ExchangeRateProviderExchangeRateApi.class
                )
        );
        var service = exchangeRateProviderFactory.getService(ExchangeRateProvider.exchangeRateApi);
        assertThat(service).isInstanceOf(ExchangeRateProviderExchangeRateApi.class);
    }

    @Test
    void should_return_exchange_rate_provider_frankfurter_object() {
        exchangeRateProviderFactory.setRegisteredServiceTypes(
                Map.of(
                        ExchangeRateProvider.frankfurter, ExchangeRateProviderFrankfurter.class
                )
        );
        var service = exchangeRateProviderFactory.getService(ExchangeRateProvider.frankfurter);
        assertThat(service).isInstanceOf(ExchangeRateProviderFrankfurter.class);
    }

    @MethodSource
    @ParameterizedTest
    void should_throw_unsupported_service_type_exception(ExchangeRateProvider exchangeRateProvider, String exceptionMessage) {
        exchangeRateProviderFactory.setRegisteredServiceTypes(
                Map.of()
        );

        Exception exception = assertThrows(
                UnsupportedServiceTypeException.class, () -> exchangeRateProviderFactory.getService(exchangeRateProvider)
        );
        assertThat(exception.getMessage()).isEqualTo(exceptionMessage);
    }

    @Test
    void should_throws_missing_service_exception() {
        exchangeRateProviderFactory.setRegisteredServiceTypes(
                Map.of(
                        ExchangeRateProvider.exchangeRateApi, MockExchangeRateProviderExistingInIoc.class
                )
        );
        Exception exception = assertThrows(
                MissingServiceException.class, () -> exchangeRateProviderFactory.getService(ExchangeRateProvider.exchangeRateApi)
        );
        assertInstanceOf(BeansException.class, exception.getCause());
    }
}
