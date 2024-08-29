package com.kh.CreditOnly_BackEnd.service;

import com.kh.CreditOnly_BackEnd.entity.CompanyOverviewEntity;
import com.kh.CreditOnly_BackEnd.entity.ConsumerSurveyIndexEntity;
import com.kh.CreditOnly_BackEnd.entity.FinancialCompanyEntity;
import com.kh.CreditOnly_BackEnd.repository.CompanyOverviewRepository;
import com.kh.CreditOnly_BackEnd.repository.ConsumerSurveyIndexRepository;
import com.kh.CreditOnly_BackEnd.repository.FinancialCompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatBotService {
    private final FinancialCompanyRepository financialCompanyRepository;
    private final ConsumerSurveyIndexRepository consumerSurveyIndexRepository;
    private final CompanyOverviewRepository companyOverviewRepository;
    @PersistenceContext
    EntityManager em;

    public List<Map<String, Object>> searchChatBotDataFetch(String activeTopic,String message) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            // 1. 소비자 동향 지수에서 확인
            if(activeTopic.equals("소비자 동향 지수")){
                Optional<ConsumerSurveyIndexEntity> consumerSurveyIndexEntityOpt = consumerSurveyIndexRepository.findByWordContainingOrContentContaining(message, message);
                consumerSurveyIndexEntityOpt.ifPresent(entity -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", entity.getId());
                    map.put("topic", "ecos_statistic_word");
                    map.put("word", entity.getWord());
                    map.put("content", entity.getContent());
                    resultList.add(map);
                });}
            // 2. 기업 개황에서 확인
            else if(activeTopic.equals("기업 개황")){
                List<CompanyOverviewEntity> companyOverviewEntities =
                        companyOverviewRepository.findByAnyFieldContaining(message);
                for (CompanyOverviewEntity entity : companyOverviewEntities) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", entity.getId());
                    map.put("topic", "dart_company_info");
                    map.put("corpCode", entity.getCorpCode());
                    map.put("corpName", entity.getCorpName());
                    map.put("corpNameEng", entity.getCorpNameEng());
                    map.put("stockName", entity.getStockName());
                    map.put("stockCode", entity.getStockCode());
                    map.put("ceoNm", entity.getCeoNm());
                    map.put("corpCls", entity.getCorpCls());
                    map.put("jurirNo", entity.getJurirNo());
                    map.put("bizrNo", entity.getBizrNo());
                    map.put("adres", entity.getAdres());
                    map.put("hmUrl", entity.getHmUrl());
                    map.put("phnNo", entity.getPhnNo());
                    map.put("faxNo", entity.getFaxNo());
                    map.put("indutyCode", entity.getIndutyCode());
                    map.put("estDt", entity.getEstDt());
                    map.put("accMt", entity.getAccMt());
                    resultList.add(map);
                }
            }

            // 3. 금융회사 정보에서 확인

            else{
                List<FinancialCompanyEntity> financialCompanyEntities =
                        financialCompanyRepository.findByAnyFieldContaining(message);
                for (FinancialCompanyEntity entity : financialCompanyEntities) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", entity.getId());
                    map.put("topic", "financial_data");
                    map.put("fncoNm", entity.getFncoNm());
                    map.put("fncoAddr", entity.getFncoAddr());
                    map.put("fncoRprNm", entity.getFncoRprNm());
                    map.put("basDt", entity.getBasDt());
                    resultList.add(map);
                }

            }

        } catch (Exception e) {
            log.error("Error occurred while searching data: ", e);
            // 에러 발생 시 빈 리스트 반환
        }

        return resultList;
    }
}
