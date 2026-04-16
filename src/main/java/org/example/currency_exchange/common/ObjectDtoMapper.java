package org.example.currency_exchange.common;

public interface ObjectDtoMapper<T, F> {
    F objectToDto(T object);

    T dtoToObject(F DTO);
}
