package br.com.cwi.apus.web.service;

import br.com.cwi.apus.external.cetus.request.ShippingExternalRequest;
import br.com.cwi.apus.external.cetus.response.ShippingExternalResponse;
import br.com.cwi.apus.external.lyra.request.PaymentExternalRequest;
import br.com.cwi.apus.external.lyra.response.PaymentExternalIdResponse;
import br.com.cwi.apus.web.domain.Basket;
import br.com.cwi.apus.web.domain.Client;
import br.com.cwi.apus.web.domain.Product;
import br.com.cwi.apus.web.domain.PurchaseOrder;
import br.com.cwi.apus.web.repository.BasketRepository;
import br.com.cwi.apus.web.repository.ClientRepository;
import br.com.cwi.apus.web.repository.ProductRepository;
import br.com.cwi.apus.web.repository.PurchaseOrderRepository;
import br.com.cwi.apus.web.request.BasketAddClientRequest;
import br.com.cwi.apus.web.request.BasketAddItemRequest;
import br.com.cwi.apus.web.request.BasketAddressRequest;
import br.com.cwi.apus.web.request.BasketPaymentRequest;
import br.com.cwi.apus.web.response.BasketDetailResponse;
import br.com.cwi.apus.web.response.BasketItemDetailResponse;
import br.com.cwi.apus.web.response.BasketResponse;
import br.com.cwi.apus.web.response.PurchaseOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
public class BasketService {

    private static final String ZIP_ORIGIN = "111111";
    public static final long DEFAULT_ID = 1111L;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private SenderMailService senderMailService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${myapp.external.cetus.url}")
    private String cetusUrl;

    @Value("${myapp.external.lyra.url}")
    private String lyraUrl;

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
        return new BasketDetailResponse(basket.getTotalItems(),basket.getShipping(),basket.getTime(),basket.getTotal(),basket.getVolume(),
                basket.getItem().stream().map(p -> {
                    BasketItemDetailResponse basketItemDetailResponse = new BasketItemDetailResponse(p.getId(),p.getQuantity(),p.getVolume());
                    return basketItemDetailResponse;
                }).collect(Collectors.toList())
                );
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

    public ShippingExternalResponse ship(ShippingExternalRequest shippingExternalRequest){

        String url = cetusUrl + "/api/cetus/shipping";

        ShippingExternalResponse shippingExternalResponse = restTemplate.postForObject(url,shippingExternalRequest,ShippingExternalResponse.class);
        return shippingExternalResponse;
    }

    public void attAddress(Long id, BasketAddressRequest basketAddressRequest) {
        Basket basket = basketRepository.getById(id);
        Client client = clientRepository.getById(basket.getClient().getId());
        ShippingExternalRequest shippingExternalRequest = new ShippingExternalRequest();

        client.setZip(basketAddressRequest.getZip());
        client.setAddres(basketAddressRequest.getAddress());

        clientRepository.save(client);

        shippingExternalRequest.setVolume(basket.getVolume());
        shippingExternalRequest.setZipDestination(client.getZip());
        shippingExternalRequest.setZipOrigin(ZIP_ORIGIN);

        ShippingExternalResponse shippingExternalResponse = ship(shippingExternalRequest);

        basket.setShippingId(shippingExternalResponse.getId());
        basket.setTime(shippingExternalResponse.getTime());
        basket.setShipping(shippingExternalResponse.getPrice());
        basket.setTotal(basket.getTotalItems() + basket.getShipping());

        basketRepository.save(basket);
    }

    public void attPayment(Long id, BasketPaymentRequest basketPaymentRequest) {
        Basket basket = basketRepository.getById(id);
        Client client = clientRepository.getById(basket.getClient().getId());

        client.setCard(basketPaymentRequest.getCard());

        clientRepository.save(client);
    }

    public PaymentExternalIdResponse payment(PaymentExternalRequest paymentExternalRequest){

        String url = lyraUrl + "/api/lyra/payment";

        PaymentExternalIdResponse paymentExternalIdResponse = restTemplate.postForObject(url,paymentExternalRequest,PaymentExternalIdResponse.class);
        return paymentExternalIdResponse;
    }

    public PurchaseOrderResponse newOrder(Long id) {
        Basket basket = basketRepository.getById(id);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        PaymentExternalRequest paymentExternalRequest = new PaymentExternalRequest();

        paymentExternalRequest.setId(DEFAULT_ID);
        paymentExternalRequest.setTotal(basket.getTotal());
        paymentExternalRequest.setCard(basket.getClient().getCard());

        PaymentExternalIdResponse paymentExternalIdResponse = payment(paymentExternalRequest);

        purchaseOrder.setPaymentId(paymentExternalIdResponse.getId());
        purchaseOrder.setStatus("CRIADO");
        purchaseOrder.setBasket(basket);

        purchaseOrderRepository.save(purchaseOrder);

        //senderMailService.enviar();

        return new PurchaseOrderResponse(purchaseOrder);
    }
}