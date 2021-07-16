package br.com.cwi.apus.web;

import br.com.cwi.apus.external.lyra.response.PaymentExternalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${myapp.external.lyra.url}")
    private String lyraUrl;

    @GetMapping("/status")
    public String getStatus(@RequestParam("myid") String id) {
        String url = lyraUrl + "/api/lyra/payment/" + id + "/status";

        try {
            PaymentExternalResponse response = restTemplate.getForObject(url, PaymentExternalResponse.class);
            return response.getStatus();
        } catch (Exception e) {
            System.out.println("Erro ao chamar serviço externo LYRA");
            e.printStackTrace();
            return "Falha no serviço de pagamento";
        }
    }
}