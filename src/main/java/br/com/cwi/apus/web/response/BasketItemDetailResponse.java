package br.com.cwi.apus.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasketItemDetailResponse {

    private Long id;
    private Long quantity;
    private Long volume;
}
