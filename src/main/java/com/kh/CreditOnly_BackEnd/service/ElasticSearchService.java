package com.kh.CreditOnly_BackEnd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ElasticSearchService {

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    private final RestTemplate restTemplate;

    public ElasticSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 유사도 검색 수행
    public ResponseEntity<String> performSimilaritySearch(String query) {
        String url = flaskServerUrl + "/api/elastic/similarity_search";
        String requestBody = "{\"query\": \"" + query + "\"}";
        return restTemplate.postForEntity(url, requestBody, String.class);
    }

    // 소비자 동향 지수 조회
    public ResponseEntity<String> fetchConsumerTrend(String keyword, String query) {
        ResponseEntity<String> similarityResponse = performSimilaritySearch(query);
        if (similarityResponse.getStatusCode().isError()) {
            return similarityResponse;
        }

        String url = flaskServerUrl + "/api/elastic/ecos";  // 엔드포인트 변경
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", keyword);

        return restTemplate.getForEntity(url, String.class, params);
    }

    // 금융 데이터 조회
    public ResponseEntity<String> fetchFinancialData(String fncoNm, String query) {  // 메서드 이름 변경
        ResponseEntity<String> similarityResponse = performSimilaritySearch(query);
        if (similarityResponse.getStatusCode().isError()) {
            return similarityResponse;
        }

        String url = flaskServerUrl + "/api/elastic/economic/financial_data";  // 엔드포인트 변경
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fncoNm", fncoNm);

        return restTemplate.getForEntity(url, String.class, params);
    }

    // 기업 개황 조회
    public ResponseEntity<String> postDartData(String requestBody, String query) {
        ResponseEntity<String> similarityResponse = performSimilaritySearch(query);
        if (similarityResponse.getStatusCode().isError()) {
            return similarityResponse;
        }

        String url = flaskServerUrl + "/api/elastic/company/dart";
        return restTemplate.postForEntity(url, requestBody, String.class);
    }
}