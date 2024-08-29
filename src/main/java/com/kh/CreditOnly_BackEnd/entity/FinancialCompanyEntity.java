package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "FinancialCompany_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialCompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "fnco_nm")
    private String fncoNm;

    @Column(name = "fnco_addr")
    private String fncoAddr;

    @Column(name = "fnco_rpr_nm")
    private String fncoRprNm;

    @Column(name = "bas_dt")
    private LocalDate basDt;
}