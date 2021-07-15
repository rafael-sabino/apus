package br.com.cwi.apus.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String status;
    private Double totalItems;
    private ShippingResponse shipping;
    private String paymentId;
    private Double total;
    private Long volume;
    private List<BasketItemDetailResponse> item = new ArrayList<>();


}
