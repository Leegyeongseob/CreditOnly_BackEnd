package com.kh.CreditOnly_BackEnd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RestController
@RequestMapping("/api/spring")
public class ElasticSearchController {

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    private final RestTemplate restTemplate;

    public ElasticSearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 소비자 동향 지수 조회 (IndexBokData)
    @GetMapping("/consumer-trend")
    public ResponseEntity<String> getConsumerTrend(@RequestParam String keyword, @RequestParam String query) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", keyword);

        return searchAndFetch("/api/elastic/ecos", params, query);
    }

    // ... 다른 메서드들 ...

    private ResponseEntity<String> searchAndFetch(String endpoint, MultiValueMap<String, String> params, String query) {
        // 유사도 검색 수행
        ResponseEntity<String> similarityResponse = postSimilaritySearch(query);
        if (similarityResponse.getStatusCode().isError()) {
            return similarityResponse;
        }

        // 실제 데이터 조회 수행
        String url = flaskServerUrl + endpoint;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    private ResponseEntity<String> postSimilaritySearch(String query) {
        String url = flaskServerUrl + "/api/elastic/similarity_search";
        String requestBody = "{\"query\": \"" + query + "\"}";

        try {
            return restTemplate.postForEntity(url, requestBody, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("유사도 검색 중 오류 발생: " + e.getMessage());
        }
    }
}