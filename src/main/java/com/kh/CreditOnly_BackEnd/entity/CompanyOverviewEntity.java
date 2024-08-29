package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "CompanyOverview_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyOverviewEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "_index")
    private String index;

    @Column(name = "_score")
    private Double score;

    @Column(name = "_type")
    private String type;

    @Column(name = "acc_mt")
    private Integer accMt;

    @Column(name = "adres")
    private String adres;

    @Column(name = "bizr_no")
    private String bizrNo;

    @Column(name = "ceo_nm")
    private String ceoNm;

    @Column(name = "corp_cls")
    private String corpCls;

    @Column(name = "corp_code")
    private String corpCode;

    @Column(name = "corp_name")
    private String corpName;

    @Column(name = "corp_name_eng")
    private String corpNameEng;

    @Column(name = "est_dt")
    private LocalDate estDt;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "hm_url")
    private String hmUrl;

    @Column(name = "induty_code")
    private String indutyCode;

    @Column(name = "ir_url")
    private String irUrl;

    @Column(name = "jurir_no")
    private String jurirNo;

    @Column(name = "message")
    private String message;

    @Column(name = "phn_no")
    private String phnNo;

    @Column(name = "status")
    private String status;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "stock_name")
    private String stockName;
}
