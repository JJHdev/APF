
/* Drop Tables */

DROP TABLE IF EXISTS TB_ENT_CO_HST;
DROP TABLE IF EXISTS TB_ENT_FNNR;
DROP TABLE IF EXISTS TB_ENT_INVT_AMT;
DROP TABLE IF EXISTS TB_ENT_LWST;
DROP TABLE IF EXISTS TB_ENT_MGMT;
DROP TABLE IF EXISTS TB_ENT_OTHSPT_HST;
DROP TABLE IF EXISTS TB_ENT_PTNT;
DROP TABLE IF EXISTS TB_ENT_RPRSV_HST;
DROP TABLE IF EXISTS TB_ENT_SHRHOLDR;
DROP TABLE IF EXISTS TB_ENT_IR;




/* Create Tables */

-- 회사연혁
CREATE TABLE TB_ENT_CO_HST
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	BGNG_YMD varchar(8),
	END_YMD varchar(8),
	CN varchar(4000),
	RMRK varchar(1000),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 재무정보
CREATE TABLE TB_ENT_FNNR
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	FNNR_SE_CD varchar(3),
	FNNR_ACNT_CD varchar(10) NOT NULL,
	DATA_SE_CD varchar(3),
	FNNR_YR char(4),
	FNNR_YMD varchar(8),
	FNNR_AMT numeric(25,10),
	KD_CD varchar(10),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 투자금액
CREATE TABLE TB_ENT_INVT_AMT
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	INVT_SE_CD varchar(3),
	INVT_YR char(4),
	INVT_AMT numeric(15),
	INVT_STEP_CD varchar(10),
	RGTR_NO varchar(20),
	MDFCN_YMD timestamp,
	MDFR_NO varchar(20),
	REG_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 업체IR정보
CREATE TABLE TB_ENT_IR
(
	IR_NO varchar(20) NOT NULL,
	BZENTY_NO varchar(20) NOT NULL,
	MAIN_BIZ_CN varchar(4000),
	CORE_ITM_CN varchar(4000),
	BIZ_CN varchar(4000),
	PIC_NM varchar(20),
	PIC_TELNO varchar(11),
	RLS_YN char(1),
	PR_VIDO_URL varchar(500),
	INQ_CNT numeric(10),
	PRGRS_STTS_CD varchar(10),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO)
) WITHOUT OIDS;


-- 소송현황
CREATE TABLE TB_ENT_LWST
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	ACUSR_NM varchar(20),
	DFDT_NM varchar(20),
	LWST_CN varchar(200),
	LWST_AMT numeric(15),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 경영진정보
CREATE TABLE TB_ENT_MGMT
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	JBPS_NM varchar(20),
	FLNM varchar(20),
	AGE numeric(3),
	CAREER_CN varchar(300),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 정부기타지원이력
CREATE TABLE TB_ENT_OTHSPT_HST
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	BIZ_NM varchar(100),
	INST_NM varchar(100),
	FLD_CN varchar(4000),
	DTL_CN varchar(4000),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 특허상표권현황
CREATE TABLE TB_ENT_PTNT
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	PATENT_SE_CD varchar(3),
	DATA_SE_CD varchar(3),
	APPLNM varchar(200),
	PATNTRT_MAN varchar(200),
	NM varchar(500),
	ILLT_REG_NO varchar(20),
	PATENT_REG_YMD varchar(8),
	KD_CD varchar(10),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 대표자이력
CREATE TABLE TB_ENT_RPRSV_HST
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	BGNG_YMD varchar(8),
	END_YMD varchar(8),
	HSTRY_CN varchar(300),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;


-- 주주현황
CREATE TABLE TB_ENT_SHRHOLDR
(
	IR_NO varchar(20) NOT NULL,
	SN numeric(10) NOT NULL,
	FLNM varchar(20),
	INVT_AMT numeric(15),
	QOTA_RT numeric(5,2),
	REL_CN varchar(300),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (IR_NO, SN)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE TB_ENT_CO_HST
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_FNNR
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_INVT_AMT
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_LWST
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_MGMT
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_OTHSPT_HST
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_PTNT
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_RPRSV_HST
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_ENT_SHRHOLDR
	ADD FOREIGN KEY (IR_NO)
	REFERENCES TB_ENT_IR (IR_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON TABLE TB_ENT_CO_HST IS '회사연혁';
COMMENT ON COLUMN TB_ENT_CO_HST.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_CO_HST.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_CO_HST.BGNG_YMD IS '시작일자';
COMMENT ON COLUMN TB_ENT_CO_HST.END_YMD IS '종료일자';
COMMENT ON COLUMN TB_ENT_CO_HST.CN IS '내용';
COMMENT ON COLUMN TB_ENT_CO_HST.RMRK IS '비고';
COMMENT ON COLUMN TB_ENT_CO_HST.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_CO_HST.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_CO_HST.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_CO_HST.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_FNNR IS '재무정보';
COMMENT ON COLUMN TB_ENT_FNNR.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_FNNR.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_FNNR.FNNR_SE_CD IS '재무구분코드';
COMMENT ON COLUMN TB_ENT_FNNR.FNNR_ACNT_CD IS '재무계정코드';
COMMENT ON COLUMN TB_ENT_FNNR.DATA_SE_CD IS '데이터구분코드';
COMMENT ON COLUMN TB_ENT_FNNR.FNNR_YR IS '재무연도';
COMMENT ON COLUMN TB_ENT_FNNR.FNNR_YMD IS '재무일자';
COMMENT ON COLUMN TB_ENT_FNNR.FNNR_AMT IS '재무금액';
COMMENT ON COLUMN TB_ENT_FNNR.KD_CD IS 'KODATA코드';
COMMENT ON COLUMN TB_ENT_FNNR.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_FNNR.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_FNNR.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_FNNR.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_INVT_AMT IS '투자금액';
COMMENT ON COLUMN TB_ENT_INVT_AMT.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_INVT_AMT.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_INVT_AMT.INVT_SE_CD IS '투자구분코드';
COMMENT ON COLUMN TB_ENT_INVT_AMT.INVT_YR IS '투자연도';
COMMENT ON COLUMN TB_ENT_INVT_AMT.INVT_AMT IS '투자금액';
COMMENT ON COLUMN TB_ENT_INVT_AMT.INVT_STEP_CD IS '투자단계코드';
COMMENT ON COLUMN TB_ENT_INVT_AMT.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_INVT_AMT.MDFCN_YMD IS '수정일자';
COMMENT ON COLUMN TB_ENT_INVT_AMT.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_INVT_AMT.REG_YMD IS '등록일자';
COMMENT ON TABLE TB_ENT_IR IS '업체IR정보';
COMMENT ON COLUMN TB_ENT_IR.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_IR.BZENTY_NO IS '업체번호';
COMMENT ON COLUMN TB_ENT_IR.MAIN_BIZ_CN IS '주요사업내용';
COMMENT ON COLUMN TB_ENT_IR.CORE_ITM_CN IS '핵심아이템내용';
COMMENT ON COLUMN TB_ENT_IR.BIZ_CN IS '사업내용';
COMMENT ON COLUMN TB_ENT_IR.PIC_NM IS '담당자명';
COMMENT ON COLUMN TB_ENT_IR.PIC_TELNO IS '담당자전화번호';
COMMENT ON COLUMN TB_ENT_IR.RLS_YN IS '공개여부';
COMMENT ON COLUMN TB_ENT_IR.PR_VIDO_URL IS '홍보영상URL';
COMMENT ON COLUMN TB_ENT_IR.INQ_CNT IS '조회수';
COMMENT ON COLUMN TB_ENT_IR.PRGRS_STTS_CD IS '진행상태코드';
COMMENT ON COLUMN TB_ENT_IR.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_IR.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_IR.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_IR.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_LWST IS '소송현황';
COMMENT ON COLUMN TB_ENT_LWST.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_LWST.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_LWST.ACUSR_NM IS '원고명';
COMMENT ON COLUMN TB_ENT_LWST.DFDT_NM IS '피고명';
COMMENT ON COLUMN TB_ENT_LWST.LWST_CN IS '소송내용';
COMMENT ON COLUMN TB_ENT_LWST.LWST_AMT IS '소송금액';
COMMENT ON COLUMN TB_ENT_LWST.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_LWST.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_LWST.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_LWST.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_MGMT IS '경영진정보';
COMMENT ON COLUMN TB_ENT_MGMT.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_MGMT.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_MGMT.JBPS_NM IS '직위명';
COMMENT ON COLUMN TB_ENT_MGMT.FLNM IS '성명';
COMMENT ON COLUMN TB_ENT_MGMT.AGE IS '연령';
COMMENT ON COLUMN TB_ENT_MGMT.CAREER_CN IS '경력내용';
COMMENT ON COLUMN TB_ENT_MGMT.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_MGMT.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_MGMT.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_MGMT.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_OTHSPT_HST IS '정부기타지원이력';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.BIZ_NM IS '사업명';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.INST_NM IS '기관명';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.FLD_CN IS '분야내용';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.DTL_CN IS '세부내용';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_OTHSPT_HST.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_PTNT IS '특허상표권현황';
COMMENT ON COLUMN TB_ENT_PTNT.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_PTNT.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_PTNT.PATENT_SE_CD IS '특허구분코드';
COMMENT ON COLUMN TB_ENT_PTNT.DATA_SE_CD IS '데이터구분코드';
COMMENT ON COLUMN TB_ENT_PTNT.APPLNM IS '출원인';
COMMENT ON COLUMN TB_ENT_PTNT.PATNTRT_MAN IS '특허권자';
COMMENT ON COLUMN TB_ENT_PTNT.NM IS '명칭';
COMMENT ON COLUMN TB_ENT_PTNT.ILLT_REG_NO IS '지적재산권등록번호';
COMMENT ON COLUMN TB_ENT_PTNT.PATENT_REG_YMD IS '특허등록일자';
COMMENT ON COLUMN TB_ENT_PTNT.KD_CD IS 'KODATA코드';
COMMENT ON COLUMN TB_ENT_PTNT.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_PTNT.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_PTNT.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_PTNT.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_RPRSV_HST IS '대표자이력';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.BGNG_YMD IS '시작일자';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.END_YMD IS '종료일자';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.HSTRY_CN IS '이력내용';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_RPRSV_HST.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_ENT_SHRHOLDR IS '주주현황';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.IR_NO IS 'IR번호';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.SN IS '일련번호';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.FLNM IS '성명';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.INVT_AMT IS '투자금액';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.QOTA_RT IS '지분율';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.REL_CN IS '관계내용';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_ENT_SHRHOLDR.MDFCN_YMD IS '수정일자';



