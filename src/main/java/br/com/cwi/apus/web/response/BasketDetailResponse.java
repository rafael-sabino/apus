package br.com.cwi.apus.web.response;

import br.com.cwi.apus.web.domain.Basket;
import br.com.cwi.apus.web.domain.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BasketDetailResponse {

    private Double totalItems;
    private Long shipping;
    private Long time;
    private Double total;
    private Long volume;
    private List<Product> item = new ArrayList<>();

    public BasketDetailResponse(Basket basket){
        this.totalItems = basket.getTotalItems();
        this.shipping = basket.getShipping();
        this.time = basket.getTime();
        this.total = basket.getTotal();
        this.volume = basket.getVolume();
        this.item = basket.getItem();

    }
}
