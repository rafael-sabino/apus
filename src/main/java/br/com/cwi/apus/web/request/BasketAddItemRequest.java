package br.com.cwi.apus.web.request;

import lombok.Data;

@Data
public class BasketAddItemRequest {
    private Long id;

    private Long quantity;
}
