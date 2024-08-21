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
        String url = flaskServerUrl + "/api/elastic/similarity_search";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(query, headers);

        // Flask 서버에 POST 요청을 보내고 결과를 받아옴
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Flask 서버로부터 받은 데이터를 그대로 React로 전달
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
