/**
 * 	1. 시스템관리자 업체정보
 * 	2. 농금원 업체정보
 * 		-> TB_ENT
 * 
 * 		CT.BZENTY_SE  - 30 : 유관기관
 * 
 */

/* 업체정보(유관기관) */
	INSERT INTO tb_ent (bzenty_no, bzenty_se_cd, brno, crno, bzenty_nm, rprsv_nm, use_stts_cd, rgtr_no, reg_ymd)
	VALUES ('200001', '30', '1078210009', '2541710021017', '농업정책보험금융원', '민연태', '1', '100000', now());
	INSERT INTO tb_ent (bzenty_no, bzenty_se_cd, brno, crno, bzenty_nm, rprsv_nm, use_stts_cd, rgtr_no, reg_ymd)
	VALUES ('200002', '30', '1388205117', '1341710003979', '농림식품기술기획평가원', '노수현', '1', '100000', now());
	INSERT INTO tb_ent (bzenty_no, bzenty_se_cd, brno, crno, bzenty_nm, rprsv_nm, use_stts_cd, rgtr_no, reg_ymd)
	VALUES ('200003', '30', '1248218476', '1358710007158', '한국농업기술진흥원', '안호근', '1', '100000', now());
	INSERT INTO tb_ent (bzenty_no, bzenty_se_cd, brno, crno, bzenty_nm, rprsv_nm, use_stts_cd, rgtr_no, reg_ymd)
	VALUES ('200004', '30', '1388205887', '1353710001019', '한국식품산업클러스터진흥원', '김영재', '1', '100000', now());
	INSERT INTO tb_ent (bzenty_no, bzenty_se_cd, brno, crno, bzenty_nm, rprsv_nm, use_stts_cd, rgtr_no, reg_ymd)
	VALUES ('200005', '30', '1048639683', '1101114809450', '농협금융지주주식회사', '이석준', '1', '100000', now());
	
	-- 2023.09.04 추가
	INSERT INTO tb_ent (bzenty_no, bzenty_se_cd, brno, crno, bzenty_nm, rprsv_nm, use_stts_cd, rgtr_no, reg_ymd)
	VALUES ('200006', '30', '1388300346', null, '농림축산식품부', null, '1', '100000', now());

	
/* 업체정보(투자자) */	
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310001', '10', 'BNK벤처투자', '1208738311', '김상윤', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310002', '10', 'CKD창업투자', '3068129961', '김주영', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310003', '10', 'NBH캐피탈', '1428108057', '이범희', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310004', '10', 'NH농협은행', '1048639742', '이석용', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310005', '10', 'NH벤처투자', '7078101642', '김현진', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310006', '10', '가이아벤처파트너스(유)', '2958100169', '김학윤', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310007', '10', '나우아이비캐피탈', '1208718329', '이승원', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310008', '10', '나이스투자파트너스(주)', '1048611007', '정용선', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310009', '10', '넥스트지인베스트먼트', '2178148969', '이귀진', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310010', '10', '농심캐피탈', '6178168340', '이종환', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310011', '10', '대성창업투자(주)', '5038114448', '김영훈', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310012', '10', '동문파트너즈', '2018616564', '이은재', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310013', '10', '동훈인베스트먼트(주)', '2208758581', '김남연', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310014', '10', '롯데벤처스', '5278700355', '전영민', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310015', '10', '마그나인베스트먼트', '1208754252', '박기일', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310016', '10', '마이다스동아인베스트먼트(주)', '1018681009', '이희준', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310017', '10', '메타인베스트먼트(유)', '5738701380', '김준민', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310018', '10', '비하이인베스트먼트', '1108617914', '김중완', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310019', '10', '빌랑스인베스트먼트', '2338802120', '길현범', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310020', '10', '삼천리인베스트먼트', '2188125746', '이장원', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310021', '10', '시그나이트파트너스', '6218617393', '문성욱', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310022', '10', '씨엔티테크', '1198166566', '전화성', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310023', '10', '씨제이인베스트먼트', '2118691293', '김도한', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310024', '10', '아이디벤처스주식회사', '2208838530', '김은섭', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310025', '10', '어니스트벤처스', '8148700356', '백승민', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310026', '10', '엔브이씨파트너스 주식회사', '1468601274', '김경찬', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310027', '10', '오라클벤처투자(주)', '4178602398', '김세현', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310028', '10', '원익투자파트너스', '3148119654', '이용성', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310029', '10', '이수창업투자', '1148197399', '정홍규', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310030', '10', '이크럭스벤처파트너스(유)', '1628101553', '김영호', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310031', '10', '인라이트벤처스', '5758100764', '박문수', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310032', '10', '임팩트파트너스 주식회사', '1338801229', '진기준', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310033', '10', '제이커브인베스트먼트', '5748701933', '박준범', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310034', '10', '지티오인베스트먼트', '3138601836', '송해민', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310035', '10', '케이프투자증권', '1078709962', '임태순', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310036', '10', '패스파인더에이치', '4598600526', '인은식', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310037', '10', '프롤로그벤처스', '6468602375', '신관호', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310038', '10', '하이투자파트너스', '1058808440', '권준희', '100000', now(), '1');
	INSERT INTO TB_ENT (BZENTY_NO, BZENTY_SE_CD, BZENTY_NM, BRNO, RPRSV_NM, RGTR_NO, REG_YMD, use_stts_cd) VALUES ('310039', '10', '현대기술투자주식회사', '2028158790', '권토마스오윤', '100000', now(), '1');

