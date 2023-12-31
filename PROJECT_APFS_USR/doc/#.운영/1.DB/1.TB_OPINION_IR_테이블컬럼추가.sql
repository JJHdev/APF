/* 데이터 백업 */
CREATE TABLE TB_OPINION_IR_BAK
AS SELECT * FROM TB_OPINION_IR
;


/* Drop Tables */
DROP TABLE IF EXISTS TB_OPINION_IR;


-- 기업IR의견검토
CREATE TABLE TB_OPINION_IR
(
	EVNT_NO varchar(20) NOT NULL,
	BZENTY_NO varchar(20) NOT NULL,
	USER_NO varchar(20) NOT NULL,
	EVNT_PARTCPTN_NO varchar(20) NOT NULL,
	BIZ_CN varchar(4000),
	PRDCT_CN varchar(2000),
	CO_CN varchar(2000),
	GNRLZ_OPNN varchar(2000),
	INVT_ITRST_DGREE_CD varchar(3),
	FLLW_MTG_INTEN_YN char(1),
	PRGRS_STTS_CD varchar(10),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (EVNT_NO, BZENTY_NO, USER_NO, EVNT_PARTCPTN_NO)
) WITHOUT OIDS;


/* Create Foreign Keys */

ALTER TABLE TB_OPINION_IR
	ADD FOREIGN KEY (EVNT_PARTCPTN_NO)
	REFERENCES TB_EVENT_ENT (EVNT_PARTCPTN_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_OPINION_IR
	ADD FOREIGN KEY (EVNT_NO, BZENTY_NO)
	REFERENCES TB_EVENT_INVT (EVNT_NO, BZENTY_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

COMMENT ON TABLE TB_OPINION_IR IS '기업IR의견검토';
COMMENT ON COLUMN TB_OPINION_IR.EVNT_NO IS '행사번호';
COMMENT ON COLUMN TB_OPINION_IR.BZENTY_NO IS '업체번호';
COMMENT ON COLUMN TB_OPINION_IR.USER_NO IS '사용자번호';
COMMENT ON COLUMN TB_OPINION_IR.EVNT_PARTCPTN_NO IS '행사참여번호';
COMMENT ON COLUMN TB_OPINION_IR.BIZ_CN IS '사업내용';
COMMENT ON COLUMN TB_OPINION_IR.PRDCT_CN IS '제품내용';
COMMENT ON COLUMN TB_OPINION_IR.CO_CN IS '회사내용';
COMMENT ON COLUMN TB_OPINION_IR.GNRLZ_OPNN IS '종합의견';
COMMENT ON COLUMN TB_OPINION_IR.INVT_ITRST_DGREE_CD IS '투자관심정도코드';
COMMENT ON COLUMN TB_OPINION_IR.FLLW_MTG_INTEN_YN IS '후속회의의향여부';
COMMENT ON COLUMN TB_OPINION_IR.PRGRS_STTS_CD IS '진행상태코드';
COMMENT ON COLUMN TB_OPINION_IR.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_OPINION_IR.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_OPINION_IR.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_OPINION_IR.MDFCN_YMD IS '수정일자';


/* 데이터 등록 */
INSERT INTO TB_OPINION_IR
SELECT EVNT_NO
     , BZENTY_NO
	 , RGTR_NO
	 , EVNT_PARTCPTN_NO
	 , BIZ_CN
	 , PRDCT_CN
	 , CO_CN
	 , gnrlz_opnn
	 , invt_itrst_dgree_cd
	 , fllw_mtg_inten_yn
	 , prgrs_stts_cd
	 , rgtr_no
	 , reg_ymd
	 , mdfr_no
	 , mdfcn_ymd
  FROM TB_OPINION_IR_BAK
;

