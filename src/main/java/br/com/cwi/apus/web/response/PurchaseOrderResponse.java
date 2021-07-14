package br.com.cwi.apus.web.response;

import br.com.cwi.apus.web.domain.PurchaseOrder;
import lombok.Data;

@Data
public class PurchaseOrderResponse {

    private Long id;

    public PurchaseOrderResponse(PurchaseOrder purchaseOrder){

        this.id = purchaseOrder.getId();
    }
}
