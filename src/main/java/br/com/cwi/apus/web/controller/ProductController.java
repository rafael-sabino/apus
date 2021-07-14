package br.com.cwi.apus.web.controller;

import br.com.cwi.apus.web.response.ProductDetailResponse;
import br.com.cwi.apus.web.response.ProductResponse;
import br.com.cwi.apus.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDetailResponse detail(@PathVariable Long id){
        return productService.detail(id);
    }

}
