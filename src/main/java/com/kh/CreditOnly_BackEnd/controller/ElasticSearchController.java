package com.kh.CreditOnly_BackEnd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/elastic")
public class ElasticSearchController {

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    private final RestTemplate restTemplate;

    public ElasticSearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/similarity_search")
    public ResponseEntity<String> postSimilaritySearch(@RequestBody String query) {
        String url = flaskServerUrl + "/api/elastic/similarity_search"; // 수정된 경로

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

}