package br.com.cwi.apus.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Long id;

    private String status;

    @OneToOne
    private Basket basket;
}
