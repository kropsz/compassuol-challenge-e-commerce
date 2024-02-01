package com.compassuol.sp.challenge.ecommerce.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MoneySerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {

            String formattedValue = formatarBigDecimal(value);
            gen.writeString(formattedValue);
        }
    }

    private String formatarBigDecimal(BigDecimal valor) {
        if (valor == null) {
            return "0,00";  
        }

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat formatoBrasileiro = new DecimalFormat("#,##0.00", symbols);

        return formatoBrasileiro.format(valor);
    }
}

