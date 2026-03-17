package org.example.currency_exchange.currency.mapper;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;

public class CodeMapper implements ObjectDtoMapper<String, CodeDTO>  {
    @Override
    public CodeDTO objectToDto(String code) {
        return new CodeDTO(code);
    }

    @Override
    public String dtoToObject(CodeDTO codeDTO) {
        return codeDTO.code();
    }
}
