package com.kh.CreditOnly_BackEnd.repository;

import com.kh.CreditOnly_BackEnd.entity.CompanyOverviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyOverviewRepository extends JpaRepository<CompanyOverviewEntity,Long> {
    @Query("SELECT c FROM CompanyOverviewEntity c WHERE " +
            "c.corpCode LIKE %:keyword% OR c.corpName LIKE %:keyword% OR " +
            "c.corpNameEng LIKE %:keyword% OR c.stockName LIKE %:keyword% OR " +
            "c.stockCode LIKE %:keyword% OR c.ceoNm LIKE %:keyword% OR " +
            "c.corpCls LIKE %:keyword% OR c.jurirNo LIKE %:keyword% OR " +
            "c.bizrNo LIKE %:keyword% OR c.adres LIKE %:keyword% OR " +
            "c.hmUrl LIKE %:keyword% OR c.irUrl LIKE %:keyword% OR " +
            "c.phnNo LIKE %:keyword% OR c.faxNo LIKE %:keyword% OR " +
            "c.indutyCode LIKE %:keyword% OR CAST(c.estDt AS string) LIKE %:keyword% OR " +
            "CAST(c.accMt AS string) LIKE %:keyword%")
    List<CompanyOverviewEntity> findByAnyFieldContaining(@Param("keyword") String keyword);
}
