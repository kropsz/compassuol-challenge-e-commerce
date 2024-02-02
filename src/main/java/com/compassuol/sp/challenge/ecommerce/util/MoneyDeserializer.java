package com.compassuol.sp.challenge.ecommerce.util;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class MoneyDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String valor = p.getText();
        if (valor == null) {
            return null;
        }

        valor = valor.replace(",", ".");
        return new BigDecimal(valor);
    }
}