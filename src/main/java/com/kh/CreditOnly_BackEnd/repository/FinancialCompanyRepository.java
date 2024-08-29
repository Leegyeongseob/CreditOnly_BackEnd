package com.kh.CreditOnly_BackEnd.repository;


import com.kh.CreditOnly_BackEnd.entity.FinancialCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinancialCompanyRepository extends JpaRepository<FinancialCompanyEntity,Long> {
    @Query("SELECT f FROM FinancialCompanyEntity f WHERE " +
            "f.fncoNm LIKE %:keyword% OR f.fncoAddr LIKE %:keyword% OR " +
            "f.fncoRprNm LIKE %:keyword% OR CAST(f.basDt AS string) LIKE %:keyword%")
    List<FinancialCompanyEntity> findByAnyFieldContaining(@Param("keyword") String keyword);
}