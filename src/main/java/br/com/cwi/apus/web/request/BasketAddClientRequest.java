package br.com.cwi.apus.web.request;

import lombok.Data;

@Data
public class BasketAddClientRequest {

    private String email;
    private String name;
}
