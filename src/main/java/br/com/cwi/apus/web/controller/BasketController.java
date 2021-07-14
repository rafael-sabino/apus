package br.com.cwi.apus.web.controller;



import br.com.cwi.apus.web.request.BasketAddClientRequest;
import br.com.cwi.apus.web.request.BasketAddItemRequest;
import br.com.cwi.apus.web.request.BasketAddressRequest;
import br.com.cwi.apus.web.request.BasketPaymentRequest;
import br.com.cwi.apus.web.response.BasketDetailResponse;
import br.com.cwi.apus.web.response.BasketResponse;
import br.com.cwi.apus.web.response.PurchaseOrderResponse;
import br.com.cwi.apus.web.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping
    public BasketResponse save(){
        // Basket basket = basketService.save();
        return basketService.save();
    }

    @PutMapping("/{id}/item")
    public void addItem(@PathVariable Long id, @RequestBody BasketAddItemRequest basketAddItemRequest){

        basketService.addItem(id,basketAddItemRequest);
    }

    @GetMapping("/{id}")
    public BasketDetailResponse detail(@PathVariable Long id) {
        return basketService.detail(id);
    }

    @PutMapping("/{id}/identity")
    public void addClient(@PathVariable Long id, @RequestBody BasketAddClientRequest basketAddClientRequest){

        basketService.addClient(id,basketAddClientRequest);
    }

    @PutMapping("/{id}/address")
    public void attAddress(@PathVariable Long id, @RequestBody BasketAddressRequest basketAddressRequest) {

        basketService.attAddress(id, basketAddressRequest);
    }

    @PutMapping("/{id}/payment")
    public void attPayment(@PathVariable Long id, @RequestBody BasketPaymentRequest basketPaymentRequest){

        basketService.attPayment(id, basketPaymentRequest);
    }

    @PutMapping("/{id}/checkout")
    public PurchaseOrderResponse newOrder(@PathVariable Long id){

        return basketService.newOrder(id);
    }
}
