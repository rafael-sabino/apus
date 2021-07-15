package br.com.cwi.apus.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BasketDetailResponse {

    private Double totalItems;
    private Long shipping;
    private Long time;
    private Double total;
    private Long volume;
    private List<BasketItemDetailResponse> item = new ArrayList<>();
}
