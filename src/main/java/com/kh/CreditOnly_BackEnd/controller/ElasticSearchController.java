package com.kh.CreditOnly_BackEnd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RestController
@RequestMapping("/api/elastic")
public class ElasticSearchController {

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    private final RestTemplate restTemplate;

    public ElasticSearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 통합 검색 메서드 (최적화된 유사도 검색 및 데이터 조회)
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

    // 소비자 동향 지수 조회 (IndexBokData)
    @GetMapping("/economic/consumer_trend")
    public ResponseEntity<String> getConsumerTrend(@RequestParam String keyword, @RequestParam String query) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", keyword);
        return searchAndFetch("/api/elastic/economic/consumer_trend", params, query);
    }

    // 금융 회사 조회 (IndexAllFinancialData)
    @GetMapping("/economic/financial_company")
    public ResponseEntity<String> getFinancialCompany(@RequestParam String fncoNm, @RequestParam String query) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fncoNm", fncoNm);
        return searchAndFetch("/api/elastic/economic/financial_company", params, query);
    }

    // 기업 개황 조회 (IndexDartData)
    @PostMapping("/company/dart")
    public ResponseEntity<String> postDartData(@RequestBody String requestBody, @RequestParam String query) {
        ResponseEntity<String> similarityResponse = postSimilaritySearch(query);
        if (similarityResponse.getStatusCode().isError()) {
            return similarityResponse;
        }

        String url = flaskServerUrl + "/api/elastic/company/dart";
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // 유사도 검색 (각 API가 호출하는 공통 메서드)
    private ResponseEntity<String> postSimilaritySearch(String query) {
        String url = flaskServerUrl + "/api/elastic/similarity_search";
        String requestBody = "{\"query\": \"" + query + "\"}";
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
        return response;
    }
}
