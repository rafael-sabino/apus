package br.com.cwi.apus.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private Long quantity;

    private Long volume;

    private Double price;
}
