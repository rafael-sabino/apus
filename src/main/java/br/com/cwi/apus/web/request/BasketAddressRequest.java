package br.com.cwi.apus.web.request;

import lombok.Data;

@Data
public class BasketAddressRequest {

    private String zip;
    private String address;
}
