package com.compassuol.sp.challenge.ecommerce.util;

import java.math.BigDecimal;

import com.compassuol.sp.challenge.ecommerce.entities.Pedido.PaymentMethod;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DescontoPedido {
    private BigDecimal totalValue;
    private BigDecimal discount;

        public static DescontoPedido conferirPromocao(PaymentMethod paymentMethod, BigDecimal subTotal){
            BigDecimal discount = BigDecimal.ZERO;
            BigDecimal totalValue;
            BigDecimal discountPercentage;
            
            if (paymentMethod == PaymentMethod.PIX) {
                discount = new BigDecimal("0.05");
                discountPercentage = subTotal.multiply(discount);
                totalValue = subTotal.subtract(discountPercentage);
            }else{
                totalValue = subTotal;
            }

            return new DescontoPedido(totalValue, discount);
        }
}
