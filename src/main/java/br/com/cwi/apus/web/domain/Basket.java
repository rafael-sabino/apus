package br.com.cwi.apus.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Basket {
    @Id
    @GeneratedValue
    private Long id;

    private Double totalItems;

    private Long shipping;

    private Double total;

    private Long volume;

    private Long time;

    @ManyToOne
    private Client client;

    @ManyToMany
    @JoinTable(name = "basket_product", joinColumns = @JoinColumn(name = "basket_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> item = new ArrayList<>();

}
