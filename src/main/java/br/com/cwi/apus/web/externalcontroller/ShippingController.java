package br.com.cwi.apus.web.externalcontroller;

import br.com.cwi.apus.external.cetus.request.ShippingExternalRequest;
import br.com.cwi.apus.external.cetus.response.ShippingExternalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${myapp.external.cetus.url}")
    private String cetusUrl;

    @PostMapping
    public ShippingExternalResponse ship(@RequestBody ShippingExternalRequest shippingExternalRequest){

        String url = cetusUrl + "/api/cetus/shipping";

        ShippingExternalResponse shippingExternalResponse = restTemplate.postForObject(url,shippingExternalRequest,ShippingExternalResponse.class);
        return shippingExternalResponse;
    }
}
