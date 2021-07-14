package br.com.cwi.apus.web.response;

import br.com.cwi.apus.web.domain.Basket;
import lombok.Data;

@Data
public class BasketResponse {

    private Long id;

    public BasketResponse(Basket basket){

        this.id = basket.getId();
    }
}
