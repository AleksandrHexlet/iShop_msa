package com.sprng.users.service;

import com.sprng.users.entity.ProductFullInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {
    RestTemplate restTemplate;

    @Autowired
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Page<ProductFullInfo> createRequest(int idCategory) {
     return restTemplate.exchange("http:PRODUCT/api/product/products/category/{idCategory}",
//                new ParameterizedTypeReference<Page<ProductFullInfo>>,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<Page<ProductFullInfo>>() {
                },idCategory
        ).getBody();
    }
}
