package com.kh.CreditOnly_BackEnd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ElasticSearchService {

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    private final RestTemplate restTemplate;

    public ElasticSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 통합된 유사도 검색 메서드
    public ResponseEntity<String> performSimilaritySearch(String query) {
        String url = flaskServerUrl + "/api/elastic/similarity_search";
        return restTemplate.postForEntity(url, query, String.class);
    }

}