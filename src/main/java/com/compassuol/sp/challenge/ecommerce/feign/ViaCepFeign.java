package com.compassuol.sp.challenge.ecommerce.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ViaCep", url = "viacep.com.br/ws")
public interface ViaCepFeign {
    
    @GetMapping("/{postalCode}/json")
    ViaCepDTO bucarEnderecoCep(@PathVariable("postalCode") String postalCode);
}
