package br.com.cwi.apus.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String name;

    private String zip;

    private String addres;

    private String card;
}
