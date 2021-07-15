package br.com.cwi.apus.web.service;

import br.com.cwi.apus.web.domain.PurchaseOrder;
import br.com.cwi.apus.web.repository.PurchaseOrderRepository;
import br.com.cwi.apus.web.response.BasketItemDetailResponse;
import br.com.cwi.apus.web.response.OrderResponse;
import br.com.cwi.apus.web.response.ShippingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public OrderResponse detail(Long id){

        PurchaseOrder purchaseOrder = purchaseOrderRepository.getById(id);
        return new OrderResponse(purchaseOrder.getId(), purchaseOrder.getStatus(), purchaseOrder.getBasket().getTotalItems(),
                new ShippingResponse(purchaseOrder.getBasket().getTotal(),purchaseOrder.getBasket().getId(), "11111abcde"),"22222abcde",
                purchaseOrder.getBasket().getTotal(),purchaseOrder.getBasket().getVolume(),

                purchaseOrder.getBasket().getItem().stream().map(p -> {
                    BasketItemDetailResponse basketItemDetailResponse = new BasketItemDetailResponse(p.getId(),p.getQuantity(),p.getVolume());
                    return basketItemDetailResponse;
                }).collect(Collectors.toList())

                );
    }
}
