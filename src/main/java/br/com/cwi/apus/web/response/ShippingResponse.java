package br.com.cwi.apus.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShippingResponse {

    private Double total;
    private Long time;
    private String quatationId;
}
