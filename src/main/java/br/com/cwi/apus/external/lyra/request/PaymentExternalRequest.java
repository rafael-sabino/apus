package br.com.cwi.apus.external.lyra.request;

import lombok.Data;

@Data
public class PaymentExternalRequest {

    public Long id;
    public Double total;
    public String card;
}
