package br.com.cwi.apus.external.cetus.request;

import lombok.Data;

@Data
public class ShippingExternalRequest {

    public String zipOrigin;
    public String zipDestination;
    public Long volume;
}
