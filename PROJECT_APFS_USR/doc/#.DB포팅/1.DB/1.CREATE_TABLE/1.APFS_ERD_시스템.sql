
/* Drop Tables */

DROP TABLE IF EXISTS SYS_BATCH_HST;
DROP TABLE IF EXISTS SYS_CNTN_HST;
DROP TABLE IF EXISTS SYS_CODE;
DROP TABLE IF EXISTS SYS_DOWN_HST;
DROP TABLE IF EXISTS SYS_INFO_HST;
DROP TABLE IF EXISTS SYS_INTRFC_HST;
DROP TABLE IF EXISTS SYS_LGN_HST;
DROP TABLE IF EXISTS SYS_MAIL_HST;
DROP TABLE IF EXISTS SYS_ROLE_MENU;
DROP TABLE IF EXISTS SYS_MENU;
DROP TABLE IF EXISTS SYS_ROLE_PROG;
DROP TABLE IF EXISTS SYS_PROG;
DROP TABLE IF EXISTS SYS_ROLE_USER;
DROP TABLE IF EXISTS SYS_ROLE;
DROP TABLE IF EXISTS SYS_SRCHWRD;
DROP TABLE IF EXISTS TB_PAPE_CODE;
DROP TABLE IF EXISTS TB_PAPE_MNG;




/* Create Tables */

-- 배치이력
CREATE TABLE SYS_BATCH_HST
(
	SN numeric(10) NOT NULL,
	BATCH_CD varchar(10),
	BATCH_YMD varchar(8),
	BATCH_PRGRM_NM varchar(200),
	BATCH_STTS_CD varchar(10),
	BATCH_BGNG_DT timestamp,
	BATCH_END_DT timestamp,
	ERROR_CD varchar(10),
	ERROR_CN varchar(1000),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 프로그램접속이력
CREATE TABLE SYS_CNTN_HST
(
	SN numeric(10) NOT NULL,
	PRGRM_URL varchar(500),
	USER_NO varchar(20),
	CNTN_SE_CD varchar(3),
	CNTN_DT timestamp,
	CNTN_YR char(4),
	CNTN_MM char(2),
	CNTN_DAY char(2),
	IP_ADDR varchar(80),
	SRVR_NM varchar(300),
	SYS_SE_CD varchar(3),
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 코드관리
CREATE TABLE SYS_CODE
(
	CD_ID varchar(20) NOT NULL,
	UP_CD_ID varchar(20) NOT NULL,
	CD_NM varchar(200),
	CD_CN varchar(2000),
	VLD_BGNG_YMD varchar(8),
	VLD_END_YMD varchar(8),
	CD_ORDR numeric(5),
	USE_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (CD_ID, UP_CD_ID)
) WITHOUT OIDS;


-- 다운로드이력
CREATE TABLE SYS_DOWN_HST
(
	SN numeric(10) NOT NULL,
	ATCH_FILE_SN numeric(10),
	PRGRM_URL varchar(500),
	USER_NO varchar(20),
	DWNLD_RSN varchar(200),
	CNTN_DT timestamp,
	CNTN_MM char(2),
	CNTN_DAY char(2),
	IP_ADDR varchar(80),
	SRVR_NM varchar(300),
	SYS_SE_CD varchar(3),
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 정보조회이력
CREATE TABLE SYS_INFO_HST
(
	SN numeric(10) NOT NULL,
	USER_NO varchar(20),
	CNTN_SE_NM varchar(200),
	KEY1 varchar(20),
	KEY2 varchar(20),
	KEY3 varchar(20),
	KEY4 varchar(20),
	CNTN_DT timestamp,
	IP_ADDR varchar(80),
	SRVR_NM varchar(300),
	SYS_SE_CD varchar(3),
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 연계이력
CREATE TABLE SYS_INTRFC_HST
(
	SN numeric(10) NOT NULL,
	LINK_CD varchar(3),
	PVSN_INST_ID varchar(20),
	RCPTN_INST_ID varchar(20),
	TRSMRCV_SE_CD varchar(5),
	OCRN_DT timestamp,
	PRCS_RSLT_CD varchar(3),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 로그인이력
CREATE TABLE SYS_LGN_HST
(
	SN numeric(10) NOT NULL,
	USER_NO varchar(20),
	USER_ID varchar(50),
	LGN_DT timestamp,
	LGN_YR char(4),
	LGN_MM char(2),
	LGN_DAY char(2),
	LGN_STTS_CD varchar(2),
	IP_ADDR varchar(80),
	SRVR_NM varchar(300),
	SYS_SE_CD varchar(3),
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 메일발송이력
CREATE TABLE SYS_MAIL_HST
(
	SN numeric(10) NOT NULL,
	USER_NO varchar(20),
	RCPTN_EML_ADDR varchar(100),
	RCVR_NM varchar(20),
	SNDNG_EML_ADDR varchar(100),
	SNDR_NM varchar(20),
	EML_TTL varchar(300),
	EML_CN text,
	TMPLAT_NM varchar(100),
	SNDNG_DT timestamp,
	SNDNG_RSLT_CD varchar(3),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 메뉴관리
CREATE TABLE SYS_MENU
(
	MENU_ID varchar(20) NOT NULL,
	UP_MENU_ID varchar(20),
	MENU_NM varchar(300),
	MENU_LEVEL numeric(3),
	MENU_ORDR numeric(5),
	TRGT_URL varchar(500),
	POPUP_YN char(1),
	SYS_SE_CD varchar(3),
	USE_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (MENU_ID)
) WITHOUT OIDS;


-- 프로그램관리
CREATE TABLE SYS_PROG
(
	PRGRM_ID varchar(20) NOT NULL,
	PRGRM_NM varchar(300),
	PRGRM_URL varchar(500),
	PRGRM_TYPE varchar(5),
	MENU_ID varchar(20),
	PRGRM_ORDR numeric(5),
	SYS_SE_CD varchar(3),
	USE_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (PRGRM_ID)
) WITHOUT OIDS;


-- 역할관리
CREATE TABLE SYS_ROLE
(
	ROLE_ID varchar(20) NOT NULL,
	UP_ROLE_ID varchar(20),
	ROLE_NM varchar(100),
	SYS_SE_CD varchar(3),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (ROLE_ID)
) WITHOUT OIDS;


-- 역할별메뉴관리
CREATE TABLE SYS_ROLE_MENU
(
	ROLE_ID varchar(20) NOT NULL,
	MENU_ID varchar(20) NOT NULL,
	USE_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (ROLE_ID, MENU_ID)
) WITHOUT OIDS;


-- 역할별프로그램관리
CREATE TABLE SYS_ROLE_PROG
(
	ROLE_ID varchar(20) NOT NULL,
	PRGRM_ID varchar(20) NOT NULL,
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (ROLE_ID, PRGRM_ID)
) WITHOUT OIDS;


-- 역할별사용자관리
CREATE TABLE SYS_ROLE_USER
(
	ROLE_ID varchar(20) NOT NULL,
	USER_NO varchar(20) NOT NULL,
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (ROLE_ID, USER_NO)
) WITHOUT OIDS;


-- 검색어관리
CREATE TABLE SYS_SRCHWRD
(
	SN numeric(10) NOT NULL,
	SRCHWRD varchar(50),
	IP_ADDR varchar(80),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (SN)
) WITHOUT OIDS;


-- 서류코드관리
CREATE TABLE TB_PAPE_CODE
(
	DCMNT_CD varchar(10) NOT NULL,
	UP_DCMNT_CD varchar(10) NOT NULL,
	DCMNT_NM varchar(300),
	LMT_CNT numeric(2),
	CD_ORDR numeric(5),
	FILE_NM varchar(200),
	FILE_PATH varchar(200),
	DCMNT_CM_NM varchar(200),
	DCMNT_CN varchar(1000),
	DWNLD_CNT numeric(10),
	DWNLD_TRGT_YN char(1),
	USE_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (DCMNT_CD, UP_DCMNT_CD)
) WITHOUT OIDS;


-- 서류양식관리
CREATE TABLE TB_PAPE_MNG
(
	DCMNT_TASK_SE_CD varchar(10) NOT NULL,
	DTL_SE_CD varchar(10) NOT NULL,
	APLY_SE_CD varchar(3) NOT NULL,
	DCMNT_CD varchar(10) NOT NULL,
	ESNTL_YN char(1),
	USE_YN char(1),
	RGTR_NO varchar(20),
	REG_YMD timestamp,
	MDFR_NO varchar(20),
	MDFCN_YMD timestamp,
	PRIMARY KEY (DCMNT_TASK_SE_CD, DTL_SE_CD, APLY_SE_CD, DCMNT_CD)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE SYS_ROLE_MENU
	ADD FOREIGN KEY (MENU_ID)
	REFERENCES SYS_MENU (MENU_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SYS_ROLE_PROG
	ADD FOREIGN KEY (PRGRM_ID)
	REFERENCES SYS_PROG (PRGRM_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SYS_ROLE_MENU
	ADD FOREIGN KEY (ROLE_ID)
	REFERENCES SYS_ROLE (ROLE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SYS_ROLE_PROG
	ADD FOREIGN KEY (ROLE_ID)
	REFERENCES SYS_ROLE (ROLE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SYS_ROLE_USER
	ADD FOREIGN KEY (ROLE_ID)
	REFERENCES SYS_ROLE (ROLE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON TABLE SYS_BATCH_HST IS '배치이력';
COMMENT ON COLUMN SYS_BATCH_HST.SN IS '일련번호';
COMMENT ON COLUMN SYS_BATCH_HST.BATCH_CD IS '배치코드';
COMMENT ON COLUMN SYS_BATCH_HST.BATCH_YMD IS '배치일자';
COMMENT ON COLUMN SYS_BATCH_HST.BATCH_PRGRM_NM IS '배치프로그램명';
COMMENT ON COLUMN SYS_BATCH_HST.BATCH_STTS_CD IS '배치상태코드';
COMMENT ON COLUMN SYS_BATCH_HST.BATCH_BGNG_DT IS '배치시작일시';
COMMENT ON COLUMN SYS_BATCH_HST.BATCH_END_DT IS '배치종료일시';
COMMENT ON COLUMN SYS_BATCH_HST.ERROR_CD IS '에러코드';
COMMENT ON COLUMN SYS_BATCH_HST.ERROR_CN IS '에러내용';
COMMENT ON COLUMN SYS_BATCH_HST.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_BATCH_HST.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_BATCH_HST.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_BATCH_HST.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_CNTN_HST IS '프로그램접속이력';
COMMENT ON COLUMN SYS_CNTN_HST.SN IS '일련번호';
COMMENT ON COLUMN SYS_CNTN_HST.PRGRM_URL IS '프로그램URL';
COMMENT ON COLUMN SYS_CNTN_HST.USER_NO IS '사용자번호';
COMMENT ON COLUMN SYS_CNTN_HST.CNTN_SE_CD IS '접속구분코드';
COMMENT ON COLUMN SYS_CNTN_HST.CNTN_DT IS '접속일시';
COMMENT ON COLUMN SYS_CNTN_HST.CNTN_YR IS '접속연도';
COMMENT ON COLUMN SYS_CNTN_HST.CNTN_MM IS '접속월';
COMMENT ON COLUMN SYS_CNTN_HST.CNTN_DAY IS '접속일';
COMMENT ON COLUMN SYS_CNTN_HST.IP_ADDR IS 'IP주소';
COMMENT ON COLUMN SYS_CNTN_HST.SRVR_NM IS '서버명';
COMMENT ON COLUMN SYS_CNTN_HST.SYS_SE_CD IS '시스템구분코드';
COMMENT ON TABLE SYS_CODE IS '코드관리';
COMMENT ON COLUMN SYS_CODE.CD_ID IS '코드ID';
COMMENT ON COLUMN SYS_CODE.UP_CD_ID IS '상위코드ID';
COMMENT ON COLUMN SYS_CODE.CD_NM IS '코드명';
COMMENT ON COLUMN SYS_CODE.CD_CN IS '코드내용';
COMMENT ON COLUMN SYS_CODE.VLD_BGNG_YMD IS '유효시작일자';
COMMENT ON COLUMN SYS_CODE.VLD_END_YMD IS '유효종료일자';
COMMENT ON COLUMN SYS_CODE.CD_ORDR IS '코드순서';
COMMENT ON COLUMN SYS_CODE.USE_YN IS '사용여부';
COMMENT ON COLUMN SYS_CODE.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_CODE.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_CODE.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_CODE.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_DOWN_HST IS '다운로드이력';
COMMENT ON COLUMN SYS_DOWN_HST.SN IS '일련번호';
COMMENT ON COLUMN SYS_DOWN_HST.ATCH_FILE_SN IS '첨부파일일련번호';
COMMENT ON COLUMN SYS_DOWN_HST.PRGRM_URL IS '프로그램URL';
COMMENT ON COLUMN SYS_DOWN_HST.USER_NO IS '사용자번호';
COMMENT ON COLUMN SYS_DOWN_HST.DWNLD_RSN IS '다운로드사유';
COMMENT ON COLUMN SYS_DOWN_HST.CNTN_DT IS '접속일시';
COMMENT ON COLUMN SYS_DOWN_HST.CNTN_MM IS '접속월';
COMMENT ON COLUMN SYS_DOWN_HST.CNTN_DAY IS '접속일';
COMMENT ON COLUMN SYS_DOWN_HST.IP_ADDR IS 'IP주소';
COMMENT ON COLUMN SYS_DOWN_HST.SRVR_NM IS '서버명';
COMMENT ON COLUMN SYS_DOWN_HST.SYS_SE_CD IS '시스템구분코드';
COMMENT ON TABLE SYS_INFO_HST IS '정보조회이력';
COMMENT ON COLUMN SYS_INFO_HST.SN IS '일련번호';
COMMENT ON COLUMN SYS_INFO_HST.USER_NO IS '사용자번호';
COMMENT ON COLUMN SYS_INFO_HST.CNTN_SE_NM IS '접속구분명';
COMMENT ON COLUMN SYS_INFO_HST.KEY1 IS 'KEY1';
COMMENT ON COLUMN SYS_INFO_HST.KEY2 IS 'KEY2';
COMMENT ON COLUMN SYS_INFO_HST.KEY3 IS 'KEY3';
COMMENT ON COLUMN SYS_INFO_HST.KEY4 IS 'KEY4';
COMMENT ON COLUMN SYS_INFO_HST.CNTN_DT IS '접속일시';
COMMENT ON COLUMN SYS_INFO_HST.IP_ADDR IS 'IP주소';
COMMENT ON COLUMN SYS_INFO_HST.SRVR_NM IS '서버명';
COMMENT ON COLUMN SYS_INFO_HST.SYS_SE_CD IS '시스템구분코드';
COMMENT ON TABLE SYS_INTRFC_HST IS '연계이력';
COMMENT ON COLUMN SYS_INTRFC_HST.SN IS '일련번호';
COMMENT ON COLUMN SYS_INTRFC_HST.LINK_CD IS '연계코드';
COMMENT ON COLUMN SYS_INTRFC_HST.PVSN_INST_ID IS '제공기관ID';
COMMENT ON COLUMN SYS_INTRFC_HST.RCPTN_INST_ID IS '수신기관ID';
COMMENT ON COLUMN SYS_INTRFC_HST.TRSMRCV_SE_CD IS '송수신구분코드';
COMMENT ON COLUMN SYS_INTRFC_HST.OCRN_DT IS '발생일시';
COMMENT ON COLUMN SYS_INTRFC_HST.PRCS_RSLT_CD IS '처리결과코드';
COMMENT ON COLUMN SYS_INTRFC_HST.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_INTRFC_HST.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_INTRFC_HST.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_INTRFC_HST.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_LGN_HST IS '로그인이력';
COMMENT ON COLUMN SYS_LGN_HST.SN IS '일련번호';
COMMENT ON COLUMN SYS_LGN_HST.USER_NO IS '사용자번호';
COMMENT ON COLUMN SYS_LGN_HST.USER_ID IS '사용자ID';
COMMENT ON COLUMN SYS_LGN_HST.LGN_DT IS '로그인일시';
COMMENT ON COLUMN SYS_LGN_HST.LGN_YR IS '로그인연도';
COMMENT ON COLUMN SYS_LGN_HST.LGN_MM IS '로그인월';
COMMENT ON COLUMN SYS_LGN_HST.LGN_DAY IS '로그인일';
COMMENT ON COLUMN SYS_LGN_HST.LGN_STTS_CD IS '로그인상태코드';
COMMENT ON COLUMN SYS_LGN_HST.IP_ADDR IS 'IP주소';
COMMENT ON COLUMN SYS_LGN_HST.SRVR_NM IS '서버명';
COMMENT ON COLUMN SYS_LGN_HST.SYS_SE_CD IS '시스템구분코드';
COMMENT ON TABLE SYS_MAIL_HST IS '메일발송이력';
COMMENT ON COLUMN SYS_MAIL_HST.SN IS '일련번호';
COMMENT ON COLUMN SYS_MAIL_HST.USER_NO IS '사용자번호';
COMMENT ON COLUMN SYS_MAIL_HST.RCPTN_EML_ADDR IS '수신이메일주소';
COMMENT ON COLUMN SYS_MAIL_HST.RCVR_NM IS '수신자명';
COMMENT ON COLUMN SYS_MAIL_HST.SNDNG_EML_ADDR IS '발송이메일주소';
COMMENT ON COLUMN SYS_MAIL_HST.SNDR_NM IS '발송자명';
COMMENT ON COLUMN SYS_MAIL_HST.EML_TTL IS '이메일제목';
COMMENT ON COLUMN SYS_MAIL_HST.EML_CN IS '이메일내용';
COMMENT ON COLUMN SYS_MAIL_HST.TMPLAT_NM IS '템플릿명';
COMMENT ON COLUMN SYS_MAIL_HST.SNDNG_DT IS '발송일시';
COMMENT ON COLUMN SYS_MAIL_HST.SNDNG_RSLT_CD IS '발송결과코드';
COMMENT ON COLUMN SYS_MAIL_HST.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_MAIL_HST.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_MAIL_HST.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_MAIL_HST.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_MENU IS '메뉴관리';
COMMENT ON COLUMN SYS_MENU.MENU_ID IS '메뉴ID';
COMMENT ON COLUMN SYS_MENU.UP_MENU_ID IS '상위메뉴ID';
COMMENT ON COLUMN SYS_MENU.MENU_NM IS '메뉴명';
COMMENT ON COLUMN SYS_MENU.MENU_LEVEL IS '메뉴레벨';
COMMENT ON COLUMN SYS_MENU.MENU_ORDR IS '메뉴순서';
COMMENT ON COLUMN SYS_MENU.TRGT_URL IS '대상URL';
COMMENT ON COLUMN SYS_MENU.POPUP_YN IS '팝업여부';
COMMENT ON COLUMN SYS_MENU.SYS_SE_CD IS '시스템구분코드';
COMMENT ON COLUMN SYS_MENU.USE_YN IS '사용여부';
COMMENT ON COLUMN SYS_MENU.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_MENU.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_MENU.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_MENU.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_PROG IS '프로그램관리';
COMMENT ON COLUMN SYS_PROG.PRGRM_ID IS '프로그램ID';
COMMENT ON COLUMN SYS_PROG.PRGRM_NM IS '프로그램명';
COMMENT ON COLUMN SYS_PROG.PRGRM_URL IS '프로그램URL';
COMMENT ON COLUMN SYS_PROG.PRGRM_TYPE IS '프로그램유형';
COMMENT ON COLUMN SYS_PROG.MENU_ID IS '메뉴ID';
COMMENT ON COLUMN SYS_PROG.PRGRM_ORDR IS '프로그램순서';
COMMENT ON COLUMN SYS_PROG.SYS_SE_CD IS '시스템구분코드';
COMMENT ON COLUMN SYS_PROG.USE_YN IS '사용여부';
COMMENT ON COLUMN SYS_PROG.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_PROG.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_PROG.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_PROG.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_ROLE IS '역할관리';
COMMENT ON COLUMN SYS_ROLE.ROLE_ID IS '역할ID';
COMMENT ON COLUMN SYS_ROLE.UP_ROLE_ID IS '상위역할ID';
COMMENT ON COLUMN SYS_ROLE.ROLE_NM IS '역할명';
COMMENT ON COLUMN SYS_ROLE.SYS_SE_CD IS '시스템구분코드';
COMMENT ON COLUMN SYS_ROLE.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_ROLE.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_ROLE.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_ROLE.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_ROLE_MENU IS '역할별메뉴관리';
COMMENT ON COLUMN SYS_ROLE_MENU.ROLE_ID IS '역할ID';
COMMENT ON COLUMN SYS_ROLE_MENU.MENU_ID IS '메뉴ID';
COMMENT ON COLUMN SYS_ROLE_MENU.USE_YN IS '사용여부';
COMMENT ON COLUMN SYS_ROLE_MENU.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_ROLE_MENU.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_ROLE_MENU.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_ROLE_MENU.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_ROLE_PROG IS '역할별프로그램관리';
COMMENT ON COLUMN SYS_ROLE_PROG.ROLE_ID IS '역할ID';
COMMENT ON COLUMN SYS_ROLE_PROG.PRGRM_ID IS '프로그램ID';
COMMENT ON COLUMN SYS_ROLE_PROG.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_ROLE_PROG.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_ROLE_PROG.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_ROLE_PROG.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_ROLE_USER IS '역할별사용자관리';
COMMENT ON COLUMN SYS_ROLE_USER.ROLE_ID IS '역할ID';
COMMENT ON COLUMN SYS_ROLE_USER.USER_NO IS '사용자번호';
COMMENT ON COLUMN SYS_ROLE_USER.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_ROLE_USER.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_ROLE_USER.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_ROLE_USER.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE SYS_SRCHWRD IS '검색어관리';
COMMENT ON COLUMN SYS_SRCHWRD.SN IS '일련번호';
COMMENT ON COLUMN SYS_SRCHWRD.SRCHWRD IS '검색어';
COMMENT ON COLUMN SYS_SRCHWRD.IP_ADDR IS 'IP주소';
COMMENT ON COLUMN SYS_SRCHWRD.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN SYS_SRCHWRD.REG_YMD IS '등록일자';
COMMENT ON COLUMN SYS_SRCHWRD.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN SYS_SRCHWRD.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_PAPE_CODE IS '서류코드관리';
COMMENT ON COLUMN TB_PAPE_CODE.DCMNT_CD IS '서류코드';
COMMENT ON COLUMN TB_PAPE_CODE.UP_DCMNT_CD IS '상위서류코드';
COMMENT ON COLUMN TB_PAPE_CODE.DCMNT_NM IS '서류명';
COMMENT ON COLUMN TB_PAPE_CODE.LMT_CNT IS '제한수';
COMMENT ON COLUMN TB_PAPE_CODE.CD_ORDR IS '코드순서';
COMMENT ON COLUMN TB_PAPE_CODE.FILE_NM IS '파일명';
COMMENT ON COLUMN TB_PAPE_CODE.FILE_PATH IS '파일경로';
COMMENT ON COLUMN TB_PAPE_CODE.DCMNT_CM_NM IS '서류주석명';
COMMENT ON COLUMN TB_PAPE_CODE.DCMNT_CN IS '서류내용';
COMMENT ON COLUMN TB_PAPE_CODE.DWNLD_CNT IS '다운로드수';
COMMENT ON COLUMN TB_PAPE_CODE.DWNLD_TRGT_YN IS '다운로드대상여부';
COMMENT ON COLUMN TB_PAPE_CODE.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_PAPE_CODE.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_PAPE_CODE.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_PAPE_CODE.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_PAPE_CODE.MDFCN_YMD IS '수정일자';
COMMENT ON TABLE TB_PAPE_MNG IS '서류양식관리';
COMMENT ON COLUMN TB_PAPE_MNG.DCMNT_TASK_SE_CD IS '서류업무구분코드';
COMMENT ON COLUMN TB_PAPE_MNG.DTL_SE_CD IS '세부구분코드';
COMMENT ON COLUMN TB_PAPE_MNG.APLY_SE_CD IS '신청구분코드';
COMMENT ON COLUMN TB_PAPE_MNG.DCMNT_CD IS '서류코드';
COMMENT ON COLUMN TB_PAPE_MNG.ESNTL_YN IS '필수여부';
COMMENT ON COLUMN TB_PAPE_MNG.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_PAPE_MNG.RGTR_NO IS '등록자번호';
COMMENT ON COLUMN TB_PAPE_MNG.REG_YMD IS '등록일자';
COMMENT ON COLUMN TB_PAPE_MNG.MDFR_NO IS '수정자번호';
COMMENT ON COLUMN TB_PAPE_MNG.MDFCN_YMD IS '수정일자';



