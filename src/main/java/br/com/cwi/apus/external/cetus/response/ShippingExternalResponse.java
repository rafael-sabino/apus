package br.com.cwi.apus.external.cetus.response;

import lombok.Data;

@Data
public class ShippingExternalResponse {

    private String id;
    private Double price;
    private Long time;
}
