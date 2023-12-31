/**
 * 경영체 IR 등록업체 - 경영체정보 등록 
 */
-- 경영체정보
INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, CRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd, INVT_HOPE_AMT) VALUES ('301001','20','미스터밀크','2178145768', '1101115475979', '신세호', '100000', now(), '1', 3000);	-- 미스터밀크

-- 사업분야
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('301001', 'B', 'BR4', '100000', now());	-- 미스트밀크
-- 사업분야
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300344', 'B', 'BR6', '100000', now());	-- 진원온원 (누락건)



/**
 * 경영체 IR 등록업체 - 투자분야 등록
 */

INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300096', 'I', 'IRC2307', '100000', now());	-- 비타카페
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('301001', 'I', 'IRC2320', '100000', now());	-- 미스터밀크
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300344', 'I', 'IRC2318', '100000', now());	-- 진원온원
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300372', 'I', 'IRC2318', '100000', now());	-- 메디프레소
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300320', 'I', 'IRC2308', '100000', now());	-- 푸디웜
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300320', 'I', 'IRC2307', '100000', now());	-- 푸디웜
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300333', 'I', 'IRC2320', '100000', now());	-- 마스팜
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300333', 'I', 'IRC2308', '100000', now());	-- 마스팜
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300182', 'I', 'IRC2320', '100000', now());	-- 푸드클로버
INSERT INTO TB_ENT_RLM (BZENTY_NO, FLD_SE_CD, FLD_CD, RGTR_NO, REG_YMD) VALUES ('300182', 'I', 'IRC2309', '100000', now());	-- 푸드클로버



/**
 * 경영체 IR 등록업체 - 투자희망금액 등록 (미스터밀크는 경영체 등록하면서 같이 등록)
 */
UPDATE TB_ENT 	SET INVT_HOPE_AMT = 2000 		WHERE BZENTY_NO = '300096';	-- 비타카페
UPDATE TB_ENT 	SET INVT_HOPE_AMT = 100 		WHERE BZENTY_NO = '300344';	-- 진원온원
UPDATE TB_ENT 	SET INVT_HOPE_AMT = 1000 		WHERE BZENTY_NO = '300372';	-- 메디프레소
UPDATE TB_ENT 	SET INVT_HOPE_AMT = 5000 		WHERE BZENTY_NO = '300320';	-- 푸디웜
UPDATE TB_ENT 	SET INVT_HOPE_AMT = 650 		WHERE BZENTY_NO = '300333';	-- 마스팜
UPDATE TB_ENT 	SET INVT_HOPE_AMT = 500 		WHERE BZENTY_NO = '300182';	-- 푸드클로버
