package org.example.currency_exchange.entity;

public record Currency(Integer id, String name, String code, String sign) {
    public final static int CODE_SIZE = 3;
    public final static int MAX_SIGN_SIZE = 3;
}
