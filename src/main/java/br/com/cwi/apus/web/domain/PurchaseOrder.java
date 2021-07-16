package br.com.cwi.apus.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Long id;

    private String paymentId;

    private String status;

    @OneToOne
    private Basket basket;
}
