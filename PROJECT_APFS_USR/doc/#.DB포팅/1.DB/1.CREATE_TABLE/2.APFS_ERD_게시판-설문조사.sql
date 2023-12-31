
/* Drop Tables */

DROP TABLE IF EXISTS TB_BANNER;
DROP TABLE IF EXISTS TB_BBS;
DROP TABLE IF EXISTS TB_BBS_FILE;
DROP TABLE IF EXISTS TB_BBS_INQRY;
DROP TABLE IF EXISTS TB_QESITM_ITEM;
DROP TABLE IF EXISTS TB_QSTNN_QESITM;
DROP TABLE IF EXISTS TB_QSTNN_RSPNS_DTLS;
DROP TABLE IF EXISTS TB_QSTNN_RSPNS;
DROP TABLE IF EXISTS TB_QSTNN_MNG;




/* Create Tables */

-- 배너관리
CREATE TABLE TB_BANNER
(
	BANNER_NO numeric(10) NOT NULL,
	BANNER_NM varchar(100),
	BANNER_URL varchar(500),
	BANNER_EXPLN varchar(1000),
	PSTG_BGNG_YMD varchar(8),
	PSTG_END_YMD varchar(8),
	ORDR numeric(5),
	USE_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (BANNER_NO)
) WITHOUT OIDS;


-- 게시판
CREATE TABLE TB_BBS
(
	PST_NO numeric(10) NOT NULL,
	UP_PST_NO numeric(10),
	BBS_SE_CD varchar(10),
	PST_CLSF_CD varchar(10),
	INQRY_TYPE_CD varchar(3),
	ANNYMTY varchar(50),
	PST_TTL varchar(300),
	PST_CN text,
	PSTG_BGNG_YMD varchar(8),
	PSTG_END_YMD varchar(8),
	FIXING_BGNG_YMD varchar(8),
	FIXING_END_YMD varchar(8),
	PST_LINK_URL varchar(500),
	EML_ADDR varchar(100),
	TAG_CN varchar(1000),
	INQ_CNT numeric(10),
	RCMDTN_CNT numeric(10),
	PST_PSWD varchar(100),
	NTC_YN char(1),
	FIXING_YN char(1),
	RLS_YN char(1),
	DEL_YN char(1),
	POPUP_YN char(1),
	POPUP_HG numeric(10),
	POPUP_AR numeric(10),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (PST_NO)
) WITHOUT OIDS;


-- 게시판첨부파일
CREATE TABLE TB_BBS_FILE
(
	SN numeric(10) NOT NULL,
	TASK_SE_CD varchar(10),
	PST_NO numeric(10),
	FILE_SE_CD varchar(3),
	FILE_PATH varchar(200),
	STRG_FILE_NM varchar(200),
	FILE_NM varchar(200),
	FILE_SZ numeric(14),
	DWNLD_CNT numeric(10),
	DEL_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 문의게시판
CREATE TABLE TB_BBS_INQRY
(
	INQRY_NO numeric(10) NOT NULL,
	UP_INQRY_NO numeric(10),
	TRGT_USER_NO varchar(20),
	TRGT_BZENTY_NO varchar(20),
	APLCNT_NO varchar(20),
	INQRY_TYPE_CD varchar(3),
	PST_TTL varchar(300),
	PST_CN text,
	PRCS_STTS_CD varchar(3),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (INQRY_NO)
) WITHOUT OIDS;


-- 설문문항항목
CREATE TABLE TB_QESITM_ITEM
(
	QITEM_NO numeric(10) NOT NULL,
	ARTCL_NO numeric(10) NOT NULL,
	ARTCL_CN varchar(300),
	MARK_NO varchar(10),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (QITEM_NO, ARTCL_NO)
) WITHOUT OIDS;


-- 설문관리
CREATE TABLE TB_QSTNN_MNG
(
	SRVY_NO varchar(10) NOT NULL,
	SRVY_CN varchar(1000),
	SRVY_TRGT_CD varchar(3),
	SRVY_BGNG_YMD varchar(8),
	SRVY_END_YMD varchar(8),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (SRVY_NO)
) WITHOUT OIDS;


-- 설문문항관리
CREATE TABLE TB_QSTNN_QESITM
(
	QITEM_NO numeric(10) NOT NULL,
	SRVY_NO varchar(10) NOT NULL,
	QITEM_CN varchar(200),
	QITEM_TYPE varchar(5),
	MARK_NO varchar(10),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (QITEM_NO)
) WITHOUT OIDS;


-- 설문응답관리
CREATE TABLE TB_QSTNN_RSPNS
(
	RSPNS_NO numeric(10) NOT NULL,
	SRVY_NO varchar(10) NOT NULL,
	USER_NO varchar(20),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (RSPNS_NO)
) WITHOUT OIDS;


-- 설문응답상세
CREATE TABLE TB_QSTNN_RSPNS_DTLS
(
	SN numeric(10) NOT NULL,
	RSPNS_NO numeric(10) NOT NULL,
	QITEM_NO numeric(10),
	ARTCL_NO numeric(10),
	RSPNS_CN varchar(4000),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (SN)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE TB_QSTNN_QESITM
	ADD FOREIGN KEY (SRVY_NO)
	REFERENCES TB_QSTNN_MNG (SRVY_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_QSTNN_RSPNS
	ADD FOREIGN KEY (SRVY_NO)
	REFERENCES TB_QSTNN_MNG (SRVY_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_QESITM_ITEM
	ADD FOREIGN KEY (QITEM_NO)
	REFERENCES TB_QSTNN_QESITM (QITEM_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TB_QSTNN_RSPNS_DTLS
	ADD FOREIGN KEY (RSPNS_NO)
	REFERENCES TB_QSTNN_RSPNS (RSPNS_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON TABLE TB_BANNER IS '배너관리';
COMMENT ON COLUMN TB_BANNER.BANNER_NO IS '배너번호';
COMMENT ON COLUMN TB_BANNER.BANNER_NM IS '배너명';
COMMENT ON COLUMN TB_BANNER.BANNER_URL IS '배너URL';
COMMENT ON COLUMN TB_BANNER.BANNER_EXPLN IS '배너설명';
COMMENT ON COLUMN TB_BANNER.PSTG_BGNG_YMD IS '게시시작일자';
COMMENT ON COLUMN TB_BANNER.PSTG_END_YMD IS '게시종료일자';
COMMENT ON COLUMN TB_BANNER.ORDR IS '순서';
COMMENT ON COLUMN TB_BANNER.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_BANNER.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_BANNER.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_BANNER.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_BANNER.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_BBS IS '게시판';
COMMENT ON COLUMN TB_BBS.PST_NO IS '게시물번호';
COMMENT ON COLUMN TB_BBS.UP_PST_NO IS '상위게시물번호';
COMMENT ON COLUMN TB_BBS.BBS_SE_CD IS '게시판구분코드';
COMMENT ON COLUMN TB_BBS.PST_CLSF_CD IS '게시물분류코드';
COMMENT ON COLUMN TB_BBS.INQRY_TYPE_CD IS '문의유형코드';
COMMENT ON COLUMN TB_BBS.ANNYMTY IS '익명';
COMMENT ON COLUMN TB_BBS.PST_TTL IS '게시물제목';
COMMENT ON COLUMN TB_BBS.PST_CN IS '게시물내용';
COMMENT ON COLUMN TB_BBS.PSTG_BGNG_YMD IS '게시시작일자';
COMMENT ON COLUMN TB_BBS.PSTG_END_YMD IS '게시종료일자';
COMMENT ON COLUMN TB_BBS.FIXING_BGNG_YMD IS '고정시작일자';
COMMENT ON COLUMN TB_BBS.FIXING_END_YMD IS '고정종료일자';
COMMENT ON COLUMN TB_BBS.PST_LINK_URL IS '게시물연계URL';
COMMENT ON COLUMN TB_BBS.EML_ADDR IS '이메일주소';
COMMENT ON COLUMN TB_BBS.TAG_CN IS '태그내용';
COMMENT ON COLUMN TB_BBS.INQ_CNT IS '조회수';
COMMENT ON COLUMN TB_BBS.RCMDTN_CNT IS '추천수';
COMMENT ON COLUMN TB_BBS.PST_PSWD IS '게시물비밀번호';
COMMENT ON COLUMN TB_BBS.NTC_YN IS '공지여부';
COMMENT ON COLUMN TB_BBS.FIXING_YN IS '고정여부';
COMMENT ON COLUMN TB_BBS.RLS_YN IS '공개여부';
COMMENT ON COLUMN TB_BBS.DEL_YN IS '삭제여부';
COMMENT ON COLUMN TB_BBS.POPUP_YN IS '팝업여부';
COMMENT ON COLUMN TB_BBS.POPUP_HG IS '팝업높이';
COMMENT ON COLUMN TB_BBS.POPUP_AR IS '팝업넓이';
COMMENT ON COLUMN TB_BBS.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_BBS.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_BBS.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_BBS.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_BBS_FILE IS '게시판첨부파일';
COMMENT ON COLUMN TB_BBS_FILE.SN IS '일련번호';
COMMENT ON COLUMN TB_BBS_FILE.TASK_SE_CD IS '업무구분코드';
COMMENT ON COLUMN TB_BBS_FILE.PST_NO IS '게시물번호';
COMMENT ON COLUMN TB_BBS_FILE.FILE_SE_CD IS '파일구분코드';
COMMENT ON COLUMN TB_BBS_FILE.FILE_PATH IS '파일경로';
COMMENT ON COLUMN TB_BBS_FILE.STRG_FILE_NM IS '저장파일명';
COMMENT ON COLUMN TB_BBS_FILE.FILE_NM IS '파일명';
COMMENT ON COLUMN TB_BBS_FILE.FILE_SZ IS '파일크기';
COMMENT ON COLUMN TB_BBS_FILE.DWNLD_CNT IS '다운로드수';
COMMENT ON COLUMN TB_BBS_FILE.DEL_YN IS '삭제여부';
COMMENT ON COLUMN TB_BBS_FILE.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_BBS_FILE.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_BBS_FILE.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_BBS_FILE.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_BBS_INQRY IS '문의게시판';
COMMENT ON COLUMN TB_BBS_INQRY.INQRY_NO IS '문의번호';
COMMENT ON COLUMN TB_BBS_INQRY.UP_INQRY_NO IS '상위문의번호';
COMMENT ON COLUMN TB_BBS_INQRY.TRGT_USER_NO IS '대상사용자번호';
COMMENT ON COLUMN TB_BBS_INQRY.TRGT_BZENTY_NO IS '대상업체번호';
COMMENT ON COLUMN TB_BBS_INQRY.APLCNT_NO IS '신청자번호';
COMMENT ON COLUMN TB_BBS_INQRY.INQRY_TYPE_CD IS '문의유형코드';
COMMENT ON COLUMN TB_BBS_INQRY.PST_TTL IS '게시물제목';
COMMENT ON COLUMN TB_BBS_INQRY.PST_CN IS '게시물내용';
COMMENT ON COLUMN TB_BBS_INQRY.PRCS_STTS_CD IS '처리상태코드';
COMMENT ON COLUMN TB_BBS_INQRY.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_BBS_INQRY.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_BBS_INQRY.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_BBS_INQRY.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_QESITM_ITEM IS '설문문항항목';
COMMENT ON COLUMN TB_QESITM_ITEM.QITEM_NO IS '문항번호';
COMMENT ON COLUMN TB_QESITM_ITEM.ARTCL_NO IS '항목번호';
COMMENT ON COLUMN TB_QESITM_ITEM.ARTCL_CN IS '항목내용';
COMMENT ON COLUMN TB_QESITM_ITEM.MARK_NO IS '표기번호';
COMMENT ON COLUMN TB_QESITM_ITEM.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_QESITM_ITEM.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_QESITM_ITEM.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_QESITM_ITEM.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_QSTNN_MNG IS '설문관리';
COMMENT ON COLUMN TB_QSTNN_MNG.SRVY_NO IS '설문번호';
COMMENT ON COLUMN TB_QSTNN_MNG.SRVY_CN IS '설문내용';
COMMENT ON COLUMN TB_QSTNN_MNG.SRVY_TRGT_CD IS '설문대상코드';
COMMENT ON COLUMN TB_QSTNN_MNG.SRVY_BGNG_YMD IS '설문시작일자';
COMMENT ON COLUMN TB_QSTNN_MNG.SRVY_END_YMD IS '설문종료일자';
COMMENT ON COLUMN TB_QSTNN_MNG.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_QSTNN_MNG.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_QSTNN_MNG.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_QSTNN_MNG.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_QSTNN_QESITM IS '설문문항관리';
COMMENT ON COLUMN TB_QSTNN_QESITM.QITEM_NO IS '문항번호';
COMMENT ON COLUMN TB_QSTNN_QESITM.SRVY_NO IS '설문번호';
COMMENT ON COLUMN TB_QSTNN_QESITM.QITEM_CN IS '문항내용';
COMMENT ON COLUMN TB_QSTNN_QESITM.QITEM_TYPE IS '문항유형';
COMMENT ON COLUMN TB_QSTNN_QESITM.MARK_NO IS '표기번호';
COMMENT ON COLUMN TB_QSTNN_QESITM.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_QSTNN_QESITM.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_QSTNN_QESITM.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_QSTNN_QESITM.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_QSTNN_RSPNS IS '설문응답관리';
COMMENT ON COLUMN TB_QSTNN_RSPNS.RSPNS_NO IS '응답번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS.SRVY_NO IS '설문번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS.USER_NO IS '사용자번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_QSTNN_RSPNS.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_QSTNN_RSPNS_DTLS IS '설문응답상세';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.SN IS '일련번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.RSPNS_NO IS '응답번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.QITEM_NO IS '문항번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.ARTCL_NO IS '항목번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.RSPNS_CN IS '응답내용';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_QSTNN_RSPNS_DTLS.MDFCN_YMD IS '수정일자';



