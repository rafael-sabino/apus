package br.com.cwi.apus.web.response;

import br.com.cwi.apus.web.domain.Product;
import lombok.Data;

@Data
public class ProductDetailResponse {

    private Long id;
    private String description;
    private Long quantity;
    private Long volume;
    private Double price;

    public ProductDetailResponse(Product product){
        this.id = product.getId();
        this.description = product.getDescription();
        this.quantity = product.getQuantity();
        this.volume = product.getVolume();
        this.price = product.getPrice();


    }

}
