package com.kh.CreditOnly_BackEnd.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
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
    @Column(name = "_id")
    private Long id;

    @Column(name = "_index")
    private String index;

    @Column(name = "_score")
    private String score;

    @Column(name = "_type")
    private String type;

    @Column(name = "actnAudpnNm")
    private String actnAudpnNm;

    @Column(name = "actnAudpnNm_keyword")
    private String actnAudpnNmKeyword;

    @Column(name = "audtRptOpnnCtt")
    private String audtRptOpnnCtt;

    @Column(name = "audtRptOpnnCtt_keyword")
    private String audtRptOpnnCttKeyword;

    @Column(name = "basDt")
    private LocalDate basDt;

    @Column(name = "basDt_keyword")
    private String basDtKeyword;

    @Column(name = "bzno")
    private Long bzno;

    @Column(name = "bzno_keyword")
    private String bznoKeyword;

    @Column(name = "corpRegMrktDcd")
    private String corpRegMrktDcd;

    @Column(name = "corpRegMrktDcdNm")
    private String corpRegMrktDcdNm;

    @Column(name = "corpRegMrktDcdNm_keyword")
    private String corpRegMrktDcdNmKeyword;

    @Column(name = "crno")
    private String crno;

    @Column(name = "crno_keyword")
    private String crnoKeyword;

    @Column(name = "empeAvgCnwkTermCtt")
    private String empeAvgCnwkTermCtt;

    @Column(name = "empeAvgCnwkTermCtt_keyword")
    private String empeAvgCnwkTermCttKeyword;

    @Column(name = "fncoAdr")
    private String fncoAdr;

    @Column(name = "fncoAdr_keyword")
    private String fncoAdrKeyword;

    @Column(name = "fncoEmpeAvgSlryAmt")
    private BigDecimal fncoEmpeAvgSlryAmt;

    @Column(name = "fncoEmpeAvgSlryAmt_keyword")
    private String fncoEmpeAvgSlryAmtKeyword;

    @Column(name = "fncoEmpeCnt")
    private Integer fncoEmpeCnt;

    @Column(name = "fncoEmpeCnt_keyword")
    private String fncoEmpeCntKeyword;

    @Column(name = "fncoEnsnNm")
    private String fncoEnsnNm;

    @Column(name = "fncoEnsnNm_keyword")
    private String fncoEnsnNmKeyword;

    @Column(name = "fncoEstbDt")
    private LocalDate fncoEstbDt;

    @Column(name = "fncoEstbDt_keyword")
    private String fncoEstbDtKeyword;

    @Column(name = "fncoFxno")
    private String fncoFxno;

    @Column(name = "fncoFxno_keyword")
    private String fncoFxnoKeyword;

    @Column(name = "fncoHmpgUrl")
    private String fncoHmpgUrl;

    @Column(name = "fncoHmpgUrl_keyword")
    private String fncoHmpgUrlKeyword;

    @Column(name = "fncoKosdaqLstgAbolDt")
    private LocalDate fncoKosdaqLstgAbolDt;

    @Column(name = "fncoKosdaqLstgAbolDt_keyword")
    private String fncoKosdaqLstgAbolDtKeyword;

    @Column(name = "fncoKosdaqLstgDt")
    private LocalDate fncoKosdaqLstgDt;

    @Column(name = "fncoKosdaqLstgDt_keyword")
    private String fncoKosdaqLstgDtKeyword;

    @Column(name = "fncoKrxLstgAbolDt")
    private LocalDate fncoKrxLstgAbolDt;

    @Column(name = "fncoKrxLstgAbolDt_keyword")
    private String fncoKrxLstgAbolDtKeyword;

    @Column(name = "fncoKrxLstgDt")
    private LocalDate fncoKrxLstgDt;

    @Column(name = "fncoKrxLstgDt_keyword")
    private String fncoKrxLstgDtKeyword;

    @Column(name = "fncoMainBizNm")
    private String fncoMainBizNm;

    @Column(name = "fncoMainBizNm_keyword")
    private String fncoMainBizNmKeyword;

    @Column(name = "fncoNm")
    private String fncoNm;

    @Column(name = "fncoRprNm")
    private String fncoRprNm;

    @Column(name = "fncoRprNm_keyword")
    private String fncoRprNmKeyword;

    @Column(name = "fncoSmenpYn")
    private String fncoSmenpYn;

    @Column(name = "fncoSmenpYn_keyword")
    private String fncoSmenpYnKeyword;

    @Column(name = "fncoStacMm")
    private String fncoStacMm;

    @Column(name = "fncoStacMm_keyword")
    private String fncoStacMmKeyword;

    @Column(name = "fncoTlno")
    private String fncoTlno;

    @Column(name = "fncoTlno_keyword")
    private String fncoTlnoKeyword;

    @Column(name = "fncoXchgLstgAbolDt")
    private LocalDate fncoXchgLstgAbolDt;

    @Column(name = "fncoXchgLstgAbolDt_keyword")
    private String fncoXchgLstgAbolDtKeyword;

    @Column(name = "fncoXchgLstgDt")
    private LocalDate fncoXchgLstgDt;

    @Column(name = "fncoXchgLstgDt_keyword")
    private String fncoXchgLstgDtKeyword;

    @Column(name = "fncoZpcd")
    private String fncoZpcd;

    @Column(name = "fncoZpcd_keyword")
    private String fncoZpcdKeyword;

    @Column(name = "fssCorpChgDtm")
    private LocalDate fssCorpChgDtm;

    @Column(name = "fssCorpUnqNo")
    private Long fssCorpUnqNo;

    @Column(name = "fssCorpUnqNo_keyword")
    private String fssCorpUnqNoKeyword;

    @Column(name = "isinCd")
    private String isinCd;

    @Column(name = "isinCd_keyword")
    private String isinCdKeyword;

    @Column(name = "isinCdNm")
    private String isinCdNm;

    @Column(name = "isinCdNm_keyword")
    private String isinCdNmKeyword;

    @Column(name = "mntrFcnFncoCd")
    private String mntrFcnFncoCd;

    @Column(name = "mntrFcnFncoCd_keyword")
    private String mntrFcnFncoCdKeyword;

    @Column(name = "mntrFcnFncoCdNm")
    private String mntrFcnFncoCdNm;

    @Column(name = "mntrFcnFncoCdNm_keyword")
    private String mntrFcnFncoCdNmKeyword;

    @Column(name = "sicCd")
    private String sicCd;

    @Column(name = "sicCd_keyword")
    private String sicCdKeyword;

    @Column(name = "sicNm")
    private String sicNm;

    @Column(name = "sicNm_keyword")
    private String sicNmKeyword;
}
