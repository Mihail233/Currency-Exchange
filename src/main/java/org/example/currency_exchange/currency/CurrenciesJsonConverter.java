package org.example.currency_exchange.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.currency_exchange.commons.JsonConverter;

import java.util.List;

public class CurrenciesJsonConverter extends JsonConverter<List<CurrencyDTO>> {
    private static final String PATH_TO_ERROR_MESSAGES = "apiErrorCodes/currencyError.property";

    public CurrenciesJsonConverter() {
        loadErrorMessages(PATH_TO_ERROR_MESSAGES);
    }

    @Override
    public String convertToJSON(List<CurrencyDTO> currencies) {
        String json;
        try {
            json = getMapper().writeValueAsString(currencies);
        } catch (JsonProcessingException e) {
            //не нравится
            throw new RuntimeException(e);
        }
        return json;
    }
}
