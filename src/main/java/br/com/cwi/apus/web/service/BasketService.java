package br.com.cwi.apus.web.service;

import br.com.cwi.apus.web.domain.Basket;
import br.com.cwi.apus.web.domain.Client;
import br.com.cwi.apus.web.domain.PurchaseOrder;
import br.com.cwi.apus.web.domain.Product;
import br.com.cwi.apus.web.repository.BasketRepository;
import br.com.cwi.apus.web.repository.ClientRepository;
import br.com.cwi.apus.web.repository.PurchaseOrderRepository;
import br.com.cwi.apus.web.repository.ProductRepository;
import br.com.cwi.apus.web.request.BasketAddClientRequest;
import br.com.cwi.apus.web.request.BasketAddItemRequest;
import br.com.cwi.apus.web.request.BasketAddressRequest;
import br.com.cwi.apus.web.request.BasketPaymentRequest;
import br.com.cwi.apus.web.response.BasketDetailResponse;
import br.com.cwi.apus.web.response.BasketResponse;
import br.com.cwi.apus.web.response.PurchaseOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    private Double valorBasket;
    private Long volumeBasket;
    private Double totalBasket;

    public BasketResponse save(){
        Basket basket = new Basket();
        basketRepository.save(basket);
        return new BasketResponse(basket);
    }

    public BasketResponse addItem(Long id, BasketAddItemRequest basketAddItemRequest) {
        Basket basket = basketRepository.getById(id);
        Product product = productRepository.getById(basketAddItemRequest.getId());
        basket.getItem().add(product);


        valorBasket = (null != basket.getTotalItems()) ? basket.getTotalItems() : 0;
        volumeBasket = (null != basket.getVolume()) ? basket.getVolume() : 0;
        totalBasket = (null != basket.getTotal()) ? basket.getTotal() : 0;

        basket.setTotalItems(valorBasket + product.getPrice()*basketAddItemRequest.getQuantity());
        basket.setVolume(volumeBasket + product.getVolume()*basketAddItemRequest.getQuantity());
        basket.setTotal(totalBasket + basket.getTotalItems());

        basketRepository.save(basket);
        return new BasketResponse(basket);
    }

    public BasketDetailResponse detail(Long id) {
        Basket basket = basketRepository.getById(id);
        return new BasketDetailResponse(basket);
    }

    public void addClient(Long id, BasketAddClientRequest basketAddClientRequest) {
        Basket basket = basketRepository.getById(id);
        Client client = new Client();

        client.setEmail(basketAddClientRequest.getEmail());
        client.setName(basketAddClientRequest.getName());
        clientRepository.save(client);

        basket.setClient(client);
        basketRepository.save(basket);
    }

    public void attAddress(Long id, BasketAddressRequest basketAddressRequest) {
        Basket basket = basketRepository.getById(id);
        Client client = clientRepository.getById(basket.getClient().getId());

        client.setZip(basketAddressRequest.getZip());
        client.setAddres(basketAddressRequest.getAddress());

        clientRepository.save(client);
    }

    public void attPayment(Long id, BasketPaymentRequest basketPaymentRequest) {
        Basket basket = basketRepository.getById(id);
        Client client = clientRepository.getById(basket.getClient().getId());

        client.setCard(basketPaymentRequest.getCard());

        clientRepository.save(client);
    }

    public PurchaseOrderResponse newOrder(Long id) {
        Basket basket = basketRepository.getById(id);
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setStatus("CRIADO");
        purchaseOrder.setBasket(basket);

        purchaseOrderRepository.save(purchaseOrder);

        return new PurchaseOrderResponse(purchaseOrder);
    }
}
