package org.example.currency_exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonConverter {
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public String convertToJSON(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
//        try {
//            return mapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }