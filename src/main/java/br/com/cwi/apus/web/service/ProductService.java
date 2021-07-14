package br.com.cwi.apus.web.service;

import br.com.cwi.apus.web.domain.Product;
import br.com.cwi.apus.web.repository.ProductRepository;
import br.com.cwi.apus.web.response.ProductDetailResponse;
import br.com.cwi.apus.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponse> findAll() {

        List<Product> products = productRepository.findAll();

        return products.stream().map(p -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(p.getId());
            productResponse.setDescription(p.getDescription());
            return productResponse;
        }).collect(Collectors.toList());
    }

    public ProductDetailResponse detail(Long id){
        Product product = productRepository.getById(id);
        return new ProductDetailResponse(product);
    }
}
