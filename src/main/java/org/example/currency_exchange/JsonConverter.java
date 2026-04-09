package org.example.currency_exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonConverter {
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    //не использовать один маппер и для dto и для json -> snake_case влияет, отправные данные baseTest, поле класса будет base_Test, хотя имя baseTest(из-за этого не смапит)
    //.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    public String convertToJSON(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}