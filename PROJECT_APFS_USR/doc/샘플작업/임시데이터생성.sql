-- 소송현황
INSERT INTO tb_ent_LWST (bzenty_no,sn,rgtr_no,reg_ymd,acusr_nm,  dfdt_nm,  lwst_cn,  lwst_amt) VALUES
('500106', NEXTVAL('SEQ_ENT_LWST'),'system', NOW(),'EDGC', '솔젠트' , '상환전환우선주(RCPS) 효력정지 가처분 소송', 300000000);

-- 주주현황
INSERT INTO tb_ent_shrholdr (bzenty_no,sn,rgtr_no,reg_ymd,flnm, invt_amt, qota_rt, rel_cn) VALUES
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '석도수', 673200000, 67.32, '본인'),
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '이원다이애그노믹스(주)', 167500000, 16.75, '본인'),
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '임민규', 102500000, 10.25, '본인'),
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '강지훈', 56800000, 5.68, '본인');

-- 임원 및 기술진
INSERT INTO tb_ent_mgmt (bzenty_no,sn,rgtr_no,reg_ymd,jbps_nm, flnm, age, career_cn) VALUES
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '대표이사', '석도수', 60, '솔젠트(주) 설립'),
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '사내이사', '임민규', 55, '솔젠트(주) 설립'),
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '사내이사', '강지훈', 53, '솔젠트(주) 설립'),
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '사외이사', '윤기열', 62, ''),
('500106', NEXTVAL('SEQ_ENT_MGMT'),'system', NOW(), '사외이사', '정희연', 51, '');

-- 대표자이력
INSERT INTO tb_ent_rprsv_hst (bzenty_no,sn,rgtr_no,reg_ymd,bgng_ymd,end_ymd,hstry_cn) VALUES
('500106', NEXTVAL('SEQ_ENT_RPRSV_HST'),'system', NOW(), '19950228', NULL, '선도대학교 농과대학 졸업'),
('500106', NEXTVAL('SEQ_ENT_RPRSV_HST'),'system', NOW(), '19960101', '20001231', '선도농림수산식품 주식회사 근무'),
('500106', NEXTVAL('SEQ_ENT_RPRSV_HST'),'system', NOW(), '20000120', NULL, '솔젠트(주) 설립'),
('500106', NEXTVAL('SEQ_ENT_RPRSV_HST'),'system', NOW(), '20020815', NULL, '보건복지부 장관 표창 수상');

-- 2023.05.06 회사연혁
INSERT INTO tb_ent_co_hist (bzenty_no,sn,rgtr_no,reg_ymd,bgng_ymd,cn) VALUES
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2000','솔젠트(주) 설립'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2001','분자유전연구소 설립'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2004','영남사업소 설립'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2005','수도권사업소 설립'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2005','우량기술기업선정'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2007','유망중소기업선정(대전광역시)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2009','산학협력우수기업 선정'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2010','(주)미르젠 합병'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2011','ISO 13485 인증'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2012','INNO-BIZ기업 선정(5차)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2012','벤처기업 인증(5차)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2012','한우/수입우 검사법 농품원 독점 고시'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2013','GMP인증'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2014','INNO-BIZ기업 선정(6차)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2014','벤처기업 인증(6차)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2015','축산물시험검사기관 지정'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2015','수출유망중소기업 선정(중소기업청)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2016','GMP인증'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2017','INNO-BIZ기업 선정(7차)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2017','벤처기업 인증(7차)'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2017','ISO 9001 인증'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2017','ISO 13485 인증'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2018','2018년산 공공비축 매입 벼의 품종 위탁 검정 사업 수주'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2018','ISO 13485:2016 인증'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2018','ISO 9001:2015 인증'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2018','EDGC와 개발 및 마케팅 MOU 체결'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2019','"2019 4IR 어워즈" 바이오 대상 수상'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2019','중국 라이프리얼사와 업무협약 체결'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2019','전남대학교 산학협력단 기술이전 협약'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2020','"대한민국 중소기업-스타트업 대상" 중소기업벤처부장관상 수상'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2020','코로나19 진단키트 미국 식품의약국(FDA) 긴급사용승인 획득'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2020','코로나19 진단키트 국내 긴급사용 승인 획득'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2021','국립농산물품질관리원(NAQS) "우수 검정기관 선정"'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2021','KMEDIhub로 부터 산화그래핀 관련 전용 실시권 획득'),
('500106', NEXTVAL('SEQ_ENT_CO_HST'),'system', NOW(), '2021','전남대학교로 부터 NADPH관련 전용 실시권 획득')
;

-- 2023.05.04 업체정보
UPDATE tb_ent
   SET fxno = '0428645690',
       rprs_telno = '0428645695',
       tpbiz_cd = 'C21102',
       tpbiz_nm = '생물학적 제제 제조업',
       bzenty_type_cd = '01',
       bzenty_stle_cd = 'BT2',
       eml_addr = 'admin@solgent.com',
       hmpg_addr = 'http://www.solgent.com',
       zip = '34014',
       lctn_addr = '대전광역시 유성구 테크노5로',
       lctn_daddr = '43-10(관평동)',
       emp_cnt = 55,
       main_biz_cn = '더 정확한 유전자 분석을 위해 생물체와 유전자, 유전자와 사람, 사람과 더 나은 미래를 이어주는 기본이 튼튼한 기업을 지향합니다.',
       core_itm_cn = '#바이오 인토매틱스 #코로나19 진단키트',
       biz_cn = '솔젠트는 유전정보를 통해 질환의 감염여부와 감염위험성을 진단하기 위한 분자진단키트를 제조하고 있습니다.'
WHERE bzenty_no = '500106';


-- 2023.04.27 경영체와 사용자샘플 맵핑 (500106)
UPDATE TB_USER A SET bzenty_no = (SELECT bzenty_no FROM TB_ENT WHERE brno='1231212345') WHERE user_id = 'comp@test.com';
-- 2023.04.28 투자자와 사용자샘플 맵핑 (500116)
UPDATE TB_USER A SET bzenty_no = (SELECT bzenty_no FROM TB_ENT WHERE brno='1408184025') WHERE user_id = 'invt@test.com';
-- 대표이미지 파일 등록
INSERT INTO tb_ent_file (bzenty_no,sn,doc_se_cd,file_nm,strg_file_nm,file_path,file_sz,rprs_yn,del_yn,rgtr_no,reg_ymd) VALUES
('500106', NEXTVAL('SEQ_ENT_FILE'), '00', 'img1.jpg', 'img1.jpg', '/ent/500106/', 0, 'Y', 'N','system', NOW()),
('500106', NEXTVAL('SEQ_ENT_FILE'), '00', 'img2.png', 'img2.png', '/ent/500106/', 0, 'N', 'N','system', NOW()),
('500106', NEXTVAL('SEQ_ENT_FILE'), '00', 'img3.png', 'img3.png', '/ent/500106/', 0, 'N', 'N','system', NOW());

INSERT INTO tb_ent_file (bzenty_no,sn,doc_se_cd,file_nm,strg_file_nm,file_path,file_sz,rprs_yn,del_yn,rgtr_no,reg_ymd) VALUES
('500106', NEXTVAL('SEQ_ENT_FILE'), '05', '사업계획서.pptx', 'C.pptx', '/ent/500106/', 0, 'Y', 'N','system', NOW()),
('500106', NEXTVAL('SEQ_ENT_FILE'), '06', '2023-24_SolGent_Catalogue.pdf', 'A.pdf', '/ent/500106/', 0, 'Y', 'N','system', NOW()),
('500106', NEXTVAL('SEQ_ENT_FILE'), '06', '2013_SolGent_Molecular_Diagnostics.pdf', 'B.pdf', '/ent/500116/', 0, 'Y', 'N','system', NOW()),
('500106', NEXTVAL('SEQ_ENT_FILE'), '06', '솔젠트_회사소개서.pdf', 'D.pdf', '/ent/500106/', 0, 'Y', 'N','system', NOW());

-- 사업분야/투자분야 등록
INSERT INTO tb_ent_rlm (bzenty_no, fld_se_cd, fld_cd, rgtr_no,reg_ymd) VALUES
( '500116', 'B', 'BR1','system', NOW()),
( '500116', 'B', 'BR3','system', NOW()),
( '500116', 'B', 'BR4','system', NOW()),
( '500116', 'I', 'IRC2302','system', NOW()),
( '500116', 'I', 'IRC2303','system', NOW()),
( '500116', 'I', 'IRC2304','system', NOW());
-- 경영체 투자희망금액 업데이트
UPDATE tb_ent SET invt_hope_amt = 21000 WHERE bzenty_no = '500106';
UPDATE tb_ent SET invt_hope_amt =  6500 WHERE bzenty_no = '500107';
UPDATE tb_ent SET invt_hope_amt = 21600 WHERE bzenty_no = '500108';
UPDATE tb_ent SET invt_hope_amt = 10000 WHERE bzenty_no = '500109';
UPDATE tb_ent SET invt_hope_amt = 10500 WHERE bzenty_no = '500110';
UPDATE tb_ent SET invt_hope_amt = 14000 WHERE bzenty_no = '500111';
UPDATE tb_ent SET invt_hope_amt = 13000 WHERE bzenty_no = '500112';
UPDATE tb_ent SET invt_hope_amt =  9600 WHERE bzenty_no = '500113';
UPDATE tb_ent SET invt_hope_amt = 30000 WHERE bzenty_no = '500114';
-- 투자자 북마크 등록
INSERT INTO tb_bkmk_info(sn,bzenty_no,trgt_bzenty_no,doc_no,bkmk_se_cd,rgtr_no,reg_ymd) VALUES
( NEXTVAL('SEQ_BKMK_INFO'),'500116','500106', NULL, 'BM1', 'system', NOW());

-- 2023.04.30 유관기관 업체등록
INSERT INTO TB_ENT (bzenty_no,bzenty_se_cd,use_stts_cd,brno,rprsv_nm,fndn_ymd,rprs_telno,bzenty_nm,rgtr_no,reg_ymd) VALUES 
 (NEXTVAL('SEQ_ENT'),'30','1','9001234567','민연태','20000801','0212345678','농업정책보험금융원', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'30','1','9001234568','노수현','20120930','0212345679','농림식품기술기획평가원', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'30','1','9001234569','안호근','19871202','0212345670','한국농업기술진흥원', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'30','1','9001234510','김영재','20040630','0212345671','한국식품산업클러스터진흥원', 'system', NOW())
;

-- 10	CT.BZENTY_SE	투자자				1	Y	100000	2023-04-20 17:18:25.147093		
-- 20	CT.BZENTY_SE	경영체				2	Y	100000	2023-04-20 17:18:25.147093		
-- 30	CT.BZENTY_SE	유관기관				3	Y	100000	2023-04-27 13:24:30.138935		
-- 40	CT.BZENTY_SE	개인회원				4	Y	100000	2023-04-27 13:24:30.138935		
-- 80	CT.BZENTY_SE	운영관리자				5	Y	100000	2023-04-27 13:24:30.138935		
-- 90	CT.BZENTY_SE	시스템관리자				6	Y	100000	2023-04-27 13:24:30.138935		

-- 2023.04.25 경영체 업체등록
INSERT INTO TB_ENT (bzenty_no,bzenty_se_cd,use_stts_cd,brno,rprsv_nm,fndn_ymd,rprs_telno,bzenty_nm,rgtr_no,reg_ymd) VALUES 
 (NEXTVAL('SEQ_ENT'),'20','1','1231212345','석도수','20000801','15445695','솔젠트㈜', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','4028120860','서동해','20120930','06522625233','동해금속', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','3018193930','이대희','19871202','0432326424','성원메디칼', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','1378161860','이헌철','20040630','0312993888','이큐셀', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','1298132781','나혁휘','20000223','0432706700','아이티엠반도체', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','6988800163','신승영','20150720','0316988743','(주)에이텍모빌리티', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','1018636260','노경탁','20071221','0326803212','동원팜스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','2138636108','박용선','19980812','0215229060','엔지스테크널러지', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'20','1','1198131956','이욱희','19990826','028577361','두진양행(주)', 'system', NOW())
;

-- 2023.04.30 정부지원사업 등록
INSERT INTO tb_biz_pbanc
(biz_pbanc_no,del_yn,rgtr_no,reg_ymd,pbanc_stts_cd,rcpt_se_cd,rcpt_bgng_dt,rcpt_end_dt,crdns_bzenty_no,crdns_bzenty_nm,biz_pbanc_nm) 
VALUES
 (NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230411','20230421', '500149','농업정책보험금융원','2023년 농식품 기술투자 로드쇼 참가업체 모집 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','20','20230101','20231231', '500149','농업정책보험금융원','농식품 크라우드펀딩 플랫폼 구축 및 운영 지원 사업 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230327','20230412','500150','농림식품기술기획평가원','2023년도 농업분야창의도전형융복합모델개발사업(애그테크) 시행계획 추가공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230313','20230317','500150','농림식품기술기획평가원','2023년도 디지털육종전환지원사업(비R&D) 종자기업 2차모집 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230306','20230313','500150','농림식품기술기획평가원','2023년도 기술사업화지원사업 시행계획 재공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230217','20230223','500150','농림식품기술기획평가원','2023년도 농업분야창의도전형융복합모델개발사업(애그테크) 시행계획 재공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230217','20230222','500150','농림식품기술기획평가원','2023년도 밭농업기계화촉진기술개발사업 시행계획 재공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230213','20230224','500150','농림식품기술기획평가원','2023년도 디지털육종전환지원사업(비R&D) 종자기업 모집 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230217','20230303','500150','농림식품기술기획평가원','2023년도 기술사업화지원사업 시행계획 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230201','20230215','500150','농림식품기술기획평가원','2023년도 2025축산현안대응산업화기술개발사업 시행계획 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230201','20230215','500150','농림식품기술기획평가원','2023년도 농업분야창의도전형융복합모델개발사업 시행계획 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230201','20230215','500150','농림식품기술기획평가원','2023년도 국제협력기반수출농업경쟁력강화기술개발사업 시행계획 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','20','20230101','20231231', '500151','한국농업기술진흥원','「2023년 농식품 벤처창업 인턴제」참여인턴 모집 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230213','20230224','500151','한국농업기술진흥원','「2023년「국가별 수출역량 강화 지원사업」 참가기업 추가모집 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230313','20230317','500151','한국농업기술진흥원','「2023 농식품 스타트업 창업페어」 부스 참가기업 모집 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230306','20230313','500151','한국농업기술진흥원','2023년 상반기 기술창업 자금지원(융자)사업 수정공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230217','20230223','500151','한국농업기술진흥원','(추가공고)2023년 목표관리제 온실가스 감축 지원사업 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230217','20230222','500151','한국농업기술진흥원','(추가공고)2023년 배출권거래제 온실가스 감축 지원사업 공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230417','20230521','500151','한국농업기술진흥원','롯데벤처스X한국농업기술진흥원 "미래식단" 3기 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230320','20230410','500152','한국식품산업클러스터진흥원','2023년 제품마켓테스트 지원사업 모집공고(2차)')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230302','20230322','500152','한국식품산업클러스터진흥원','2023년 기업공동사업 모집공고(2차)')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230222','20230313','500152','한국식품산업클러스터진흥원','2023년 식품기업시설인증지원사업(2차) 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230222','20230313','500152','한국식품산업클러스터진흥원','2023년 공동기술개발사업(2차) 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230207','20230220','500152','한국식품산업클러스터진흥원','2023년 소스 전통장류 혁신성장 지원사업 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','10','20230701','20230720','500152','한국식품산업클러스터진흥원','2023년 기업공동사업 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230207','20230220','500152','한국식품산업클러스터진흥원','2023년 제품마켓테스트 지원사업 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230207','20230220','500152','한국식품산업클러스터진흥원','2023년 식품기업 시설인증 지원사업 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230207','20230220','500152','한국식품산업클러스터진흥원','2023년 권역별 산학연 기술 지원사업 모집공고')
,(NEXTVAL('SEQ_BIZ_PBANC'),'N','system',NOW(),'10','30','20230109','20230207','500152','한국식품산업클러스터진흥원','[식품진흥원] 2023년 공동기술개발사업 모집 공고(1차)')
;

-- 경영체와 사용자샘플 맵핑
UPDATE TB_USER A SET bzenty_no = (SELECT bzenty_no FROM TB_ENT WHERE brno='1231212345') WHERE user_id = 'comp@test.com';


-- 2023.04.24 투자자 업체등록
INSERT INTO TB_ENT (bzenty_no,bzenty_se_cd,use_stts_cd,brno,rprsv_nm,fndn_ymd,rprs_telno,bzenty_nm,rgtr_no,reg_ymd) VALUES 
 (NEXTVAL('SEQ_ENT'),'10','1','1408184025','김정옥','20140303','0313624772','(유)동문파트너즈', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1378615186','박향미','20111122','0313624773','(주)세종벤처파트너스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','6158127149','남융희','20020524','0313624774','(주)센트럴투자파트너스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','3448701481','이병구','20190924','0313624775','(주)이수창업투자', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1318175634','서민석','20021114','0313624776','(주)이앤인베스트먼트', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1228623762','임규영','20120404','0313624777','KB인베스트먼트', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','5798801804','허미자','20201022','0313624778','NH벤처투자(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1498102020','문명일','20210701','0313624779','나이스투자파트너스(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1168108325','김기용','19841030','0313624780','NH투자증권(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','4088162742','김원영','20040712','0313624781','동훈인베스트먼트', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','4128801122','김경옥','20180806','0313624782','롯데액셀러레이터', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','3128143672','고영임','20000808','0313624783','마그나인베스트먼트', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','4028198956','임은환','20121102','0313624784','메가인베스트먼트(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1198102499','이윤우','19710729','0313624785','미래에셋벤처투자(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','3078106942','홍무선','19941220','0313624786','미시간벤처캐피탈(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','6088148919','박경현','20020821','0313624787','비하이인베스트먼트(유)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','3068101245','김진우','19530529','0313624788','빌랑스인베스트먼트', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1418124205','손영준','20110531','0313624789','시그나이트파트너스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','6088154968','오윤서','20040212','0313624790','씨엔티테크', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1258106772','이옥란','19750413','0313624791','씨케이디창업투자', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','6208126351','김혁희','20061121','0313624792','아이디벤처스(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','4978800615','황상원','20170726','0313624793','안강벤처투자(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','6218147887','김선미','20020206','0313624794','엔브이씨파트너스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','3018118907','이병철','19931201','0313624795','오라클벤처투자', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1348181737','배선희','20020328','0313624796','원익투자파트너스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','6208110295','이서헌','19760803','0313624797','유니온투자파트너스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1198125774','임연숙','19970621','0313624798','인라이트벤처스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1218135940','오준열','20000315','0313624799','임팩트파트너스', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1378620603','강인오','20120420','0313624800','타임와이즈인베스트먼트', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1348104079','김종순','19771129','0313624801','패스파인더에이치', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1258625094','황종수','20140902','0313624802','포스코기술투자(주)', 'system', NOW())
,(NEXTVAL('SEQ_ENT'),'10','1','1138683602','문영우','20140402','0313624803','현대기술투자(주)', 'system', NOW())
;
INSERT INTO tb_user (user_no,bzenty_no,user_id,user_nm,pswd,eml_addr,telno,mbl_telno,brdt,zip,addr,daddr,sexdstn,dept_cd,dept_nm,empl_no,idntf_id,lgn_se_cd,join_ymd,whdwl_ymd,cntn_psblty_ip_addr,pswd_lock_ymd,pswd_err_cnt,pswd_chg_ymd,pswd_next_ymd,last_lgn_dt,moblphon_rcptn_agre_yn,prvc_clct_agre_yn,thpty_pvsn_agre_yn,use_stts_cd,test_use_yn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('100014',NULL,'admin@test.com','운영관리자','+LxGnVhG1DoRI5EeXPCyyYxOc1Qmj0zSG/2H1u9vg+A=','admin@test.com',NULL,'01011112222',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'20230427',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'system',TIMESTAMP '2023-04-27 11:05:45.117086',NULL,NULL),
  ('100015',NULL,'user@test.com','일반사용자','ZLn01OTh3GT4hHy94P0Z2w/60xKsN8NSQUsE739SufM=','user@test.com',NULL,'01011112223',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'20230427',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'system',TIMESTAMP '2023-04-27 11:05:45.117086',NULL,NULL),
  ('100018',NULL,'inst@test.com','유관기관사용자','2ObKXulYRy1Gugs1lomJ1OzMB8AhZHYyQux1Yurh1tA=','inst@test.com',NULL,'01011112226',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'20230427',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'system',TIMESTAMP '2023-04-27 11:05:45.117086',NULL,NULL),
  ('100016','500116','invt@test.com','투자자사용자','OeJYhQien7MK226AKjcfK4hDwJl5iCnDd0dmbxPcde4=','invt@test.com',NULL,'01011112224',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'20230427',NULL,NULL,NULL,0,NULL,NULL,TIMESTAMP '2023-05-08 12:33:48.331036',NULL,NULL,NULL,NULL,NULL,'system',TIMESTAMP '2023-04-27 11:05:45.117086',NULL,NULL),
  ('100017','500106','comp@test.com','경영체사용자','jbfQKW8Hh0KFhIiYGERvU/rJXbf9drhPy2pPYsr/dvs=','comp@test.com',NULL,'01011112225',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'20230427',NULL,NULL,NULL,0,NULL,NULL,TIMESTAMP '2023-05-08 13:53:26.442971',NULL,NULL,NULL,NULL,NULL,'system',TIMESTAMP '2023-04-27 11:05:45.117086',NULL,NULL),
  ('100013',NULL,'system@test.com','시스템관리자','EBkBXJGam/+4Uy/658gVt0MY2lPZw1b/0VUvt2ja6XU=','system@test.com',NULL,'01011112221',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'20230427',NULL,NULL,NULL,0,NULL,NULL,TIMESTAMP '2023-05-08 13:58:47.404116',NULL,NULL,NULL,NULL,NULL,'system',TIMESTAMP '2023-04-27 11:05:45.117086',NULL,NULL);

-- 2023.04.24 펀드정보 등록
INSERT INTO tb_fund_info (fund_no,del_yn, use_yn, make_yr,invt_fld_cd,invt_bgng_ymd,invt_end_ymd,rprs_telno,fund_oper_scale,fund_nm,rgtr_no,reg_ymd) VALUES 
 ('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2302','20221201','20261202','0237756754',10,'농식품새싹키움매칭펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2304','20221201','20230602','0237756755',200,'비엔케이 그린바이오 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2320','20221201','20261202','0237756756',500,'엔에이치나이스농식품투자조합1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2317','20221001','20261002','0237756757',65,'인라이트 애그테크플러스펀드 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2302','20221001','20261002','0237756758',125,'임팩트-이크럭스 농식품벤처 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2304','20221001','20261002','0237756759',200,'패스파인더 그린바이오 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2303','20221001','20261002','0237756760',200,'CKD Smart Farm 2호 농식품투자조', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2303','20221001','20261002','0237756761',210,'2022 원익 스마트 혁신 Agtech투자', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2317','20221001','20261002','0237756762',65,'오라클프레쉬펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2314','20221001','20250402','0237756763',216,'빌랑스 징검다리 농식품 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2320','20221001','20261002','0237756764',300,'마그나 FUTURE 펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2302','20220801','20260802','0237756765',140,'나우농식품투자펀드 5호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2305','20220701','20260702','0237756766',100,'유니온수산투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2310','20220701','20260702','0237756767',96,'넥스트웨이브 2022 수산벤처 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2302','20220701','20260702','0237756768',130,'세종 농식품 벤처펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2306','20220701','20260702','0237756769',105,'엔에이치영파머스투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2022','IRC2317','20220701','20260702','0237756770',65,'비하이농식품마이크로투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2318','20220101','20260102','0237756771',71,'씨엔티테크 제13호 농식품 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2302','20211201','20251202','0237756772',100,'동훈 농식품벤처스타 2호 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2319','20211201','20250602','0237756773',520,'하이브리드 ESG 세컨더리펀드 제일호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2305','20211101','20251102','0237756774',150,'아이디브이-아이피 수산전문투자조합 3호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2320','20211001','20251002','0237756775',182,'신세계웰니스투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2304','20210901','20250902','0237756776',150,'마그나 GREEN 펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2303','20210901','20250802','0237756777',150,'비엔케이 농식품 투자조합 제3호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2306','20210801','20250802','0237756778',105,'패스파인더 영파머스 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2302','20210801','20250802','0237756779',125,'메가농식품벤처투자조합3호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2317','20210701','20250702','0237756780',65,'빙그레농식품투자조합2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2320','20210701','20250702','0237756781',210,'현대-GS리테일 Agro-Bio펀드 3호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2021','IRC2310','20210701','20250702','0237756782',105,'엔브이씨 2021 수산벤처 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2320','20201201','20241202','0237756783',152,'롯데농식품테크펀드1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2313','20201101','20241102','0237756784',110,'아이에스유-힘내라경북애그리푸드투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2302','20201001','20241002','0237756785',130,'엔에이치농식품벤처투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2306','20201001','20241002','0237756786',100,'나이스앤영파머스투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2320','20200901','20240902','0237756787',600,'엔에이치나우농식품2호 사모투자합자회사', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2317','20200901','20240902','0237756788',65,'인라이트8호 애그테크플러스펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2314','20200901','20240902','0237756789',220,'농식품 스텝업 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2302','20200701','20240702','0237756790',130,'마그나 FRESH펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2310','20200701','20240702','0237756791',150,'가이아수산벤처창업투자조합1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2020','IRC2310','20200701','20240702','0237756792',150,'비엔케이 수산투자조합 제1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2305','20191201','20231202','0237756793',200,'마이다스동아-엔에스씨 수산펀드 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2305','20191201','20231202','0237756794',150,'IDV-IP수산전문투자조합 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2320','20190801','20230802','0237756795',400,'엔에이치나우농식품1호 사모투자합자회사', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2320','20190801','20230802','0237756796',230,'유엔그린시너지투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2302','20190701','20230702','0237756797',125,'타임와이즈농식품벤처펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2317','20190701','20230702','0237756798',63,'동훈 농식품벤처스타 1호 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2319','20190701','20230702','0237756799',100,'현대 Agro-Bio펀드 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2019','IRC2317','20190601','20230602','0237756800',62,'빙그레농식품투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2018','IRC2319','20181201','20221202','0237756801',200,'나우농식품세컨더리투자펀드 1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2018','IRC2319','20181201','20221202','0237756802',100,'킹고 멀티플6 농식품 제1호 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2018','IRC2313','20180801','20220802','0237756803',100,'AJ-ISU경기도애그리푸드투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2018','IRC2302','20180801','20220802','0237756804',125,'미시간글로벌식품산업투자조합3호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2018','IRC2320','20180801','20220802','0237756805',200,'NHC-DTNI 농식품 일반 투자조합2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2018','IRC2305','20180701','20220702','0237756806',150,'KB 신자산어보 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2017','IRC2320','20171201','20211202','0237756807',100,'시너지 농식품 밸류크리에이티브 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2017','IRC2305','20170801','20210802','0237756808',150,'POSCO-NSC 수산투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2017','IRC2319','20170801','20210802','0237756809',100,'패스파인더 6차산업화 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2017','IRC2319','20170801','20210802','0237756810',220,'NHC-DTNI 농식품 ABC 투자조합1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2017','IRC2319','20170801','20210802','0237756811',200,'마그나 ABC 펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2017','IRC2320','20170801','20210802','0237756812',200,'나우농식품투자펀드 4호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2017','IRC2320','20170801','20210802','0237756813',200,'KB 신농사직설 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2320','20170101','20210102','0237756814',330,'GMB-MD농식품투자펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2320','20161001','20201002','0237756815',425,'A&F미래성장투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2308','20160801','20200802','0237756816',200,'스마일게이트 농식품1호 펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2308','20160801','20200802','0237756817',150,'유큐아이피농식품 투자조합 제2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2308','20160801','20200802','0237756818',150,'CKD Smart Farm 1호 농식품투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2305','20160901','20200902','0237756819',200,'K-Innovation 수산전문 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2319','20160801','20200802','0237756820',100,'블루 6차산업화 투자조합1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2016','IRC2301','20160801','20200802','0237756821',100,'포스코 농식품 수출 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2320','20160301','20200302','0237756822',200,'에이치애그리비즈밸류크리에이티브제일호 사모투자합자회사', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2320','20151101','20191102','0237756823',240,'유티씨 그린바이오투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2318','20151001','20191002','0237756824',120,'DTNI-AGRITECH투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2319','20150901','20190902','0237756825',100,'SB 프로젝트투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2305','20150701','20190702','0237756826',150,'캐피탈원 농림수산식품 투자조합 3호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2320','20150801','20190802','0237756827',200,'TWI 농식품상생투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2301','20150801','20190802','0237756828',100,'AJ 농식품수출육성펀드 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2015','IRC2319','20150901','20190902','0237756829',100,'ECO-MGI 6차산업 전문투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2320','20150101','20180702','0237756830',290,'이앤-에이비에프 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2320','20150101','20180702','0237756831',100,'SJ-농림축산식품 투자조합 제1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2301','20141201','20180602','0237756832',130,'AJ-세종 농식품수출육성펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2320','20150101','20180702','0237756833',120,'나우 농식품투자펀드 3호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2319','20150101','20180702','0237756834',100,'센트럴생거진천 농식품투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2320','20141201','20180602','0237756835',100,'현대 Agro-Bio펀드 1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2319','20141101','20180502','0237756836',100,'메가 농축산성장 투자조합 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2319','20140801','20180202','0237756837',100,'컴퍼니케이파트너스 애그로씨드투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2319','20140701','20180102','0237756838',100,'세종 농식품R&D사업화 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2014','IRC2305','20140801','20180202','0237756839',150,'캐피탈원 농림수산식품 투자조합 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2013','IRC2319','20140101','20170702','0237756840',100,'엘엔에스 농식품6차산업화 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2013','IRC2320','20131001','20170402','0237756841',150,'세종 농식품바이오 투자조합 1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2013','IRC2320','20130901','20170302','0237756842',150,'동양 농식품 2호 투자펀드', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2013','IRC2320','20130901','20170302','0237756843',150,'메가 농식품 투자조합 1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2013','IRC2319','20130901','20170302','0237756844',100,'미시간 글로벌식품산업 투자조합 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2013','IRC2305','20130701','20170102','0237756845',150,'IDV-IP 수산전문 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2013','IRC2319','20130701','20170102','0237756846',100,'솔리더스-고창 프로젝트 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2012','IRC2320','20130101','20160702','0237756847',160,'AKGI 애그로상생경제 투자조합 1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2012','IRC2320','20121001','20160402','0237756848',160,'노루-미래에셋 애그로스타프로젝트 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2012','IRC2305','20120701','20160102','0237756849',160,'엘엔에스 농수산업 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2012','IRC2319','20120701','20160102','0237756850',100,'이앤-농식품프로젝트 투자조합', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2012','IRC2319','20120701','20160102','0237756851',100,'유큐아이피 농림수산식품 투자조합 제1호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2012','IRC2303','20120701','20160102','0237756852',160,'나우 농식품 투자펀드 2호', 'system', NOW())
,('F'||TO_CHAR(NOW(),'YY') ||TO_CHAR(NEXTVAL('SEQ_FUND_INFO'),'FM0000000'),'N','Y','2012','IRC2303','20120701','20160102','0237756853',160,'KDBC 식품산업 투자조합 1호', 'system', NOW())
;
-- 펀드규모 업데이트
UPDATE TB_FUND_INFO SET fund_oper_scale = fund_oper_scale * 100;

-- 2023.04.25 펀드-투자자 맵핑정보 등록
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '빙그레농식품투자조합2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%동문파트너즈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '빙그레농식품투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%동문파트너즈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'SJ-농림축산식품 투자조합 제1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%SJ투자파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '블루 6차산업화 투자조합1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%블루그린인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'AJ-세종 농식품수출육성펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%세종벤처파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '센트럴생거진천 농식품투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%센트럴투자파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '아이에스유-힘내라경북애그리푸드투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%이수창업투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '이앤-에이비에프 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%이앤인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'CKD Smart Farm 1호 농식품투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%CKD창업투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'KB 신농사직설 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%KB인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'KB 신자산어보 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%KB인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'KDBC 식품산업 투자조합 1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%KDB캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엔에이치농식품벤처투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%NH벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '유티씨 그린바이오투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%UTC인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '가이아수산벤처창업투자조합1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%가이아벤처파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '나우농식품투자펀드 5호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나우아이비캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엔에이치나우농식품2호 사모투자합자회사') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나우아이비캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엔에이치나우농식품1호 사모투자합자회사') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나우아이비캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '나우농식품세컨더리투자펀드 1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나우아이비캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '나우농식품투자펀드 4호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나우아이비캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '나우 농식품투자펀드 3호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나우아이비캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '나우 농식품 투자펀드 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나우아이비캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엔에이치나이스농식품투자조합1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나이스투자파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '나이스앤영파머스투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%나이스투자파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '농식품새싹키움매칭펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%농업정책보험금융원%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '에이치애그리비즈밸류크리에이티브제일호 사모투자합자회사') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%NH투자증권%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'K-Innovation 수산전문 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%대성창업투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '동훈 농식품벤처스타 2호 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%동훈인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '동훈 농식품벤처스타 1호 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%동훈인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'NHC-DTNI 농식품 일반 투자조합2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%디티앤인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '롯데농식품테크펀드1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%롯데액셀러레이터%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '마그나 FUTURE 펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%마그나인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '마그나 GREEN 펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%마그나인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '마그나 FRESH펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%마그나인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '마그나 ABC 펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%마그나인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '마이다스동아-엔에스씨 수산펀드 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%마이다스동아인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '메가농식품벤처투자조합3호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%메가인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '메가 농축산성장 투자조합 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%메가인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '메가 농식품 투자조합 1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%메가인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '노루-미래에셋 애그로스타프로젝트 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%미래에셋벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '미시간글로벌식품산업투자조합3호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%미시간벤처캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '미시간 글로벌식품산업 투자조합 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%미시간벤처캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '비엔케이 그린바이오 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%비엔케이벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '비엔케이 농식품 투자조합 제3호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%비엔케이벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '비엔케이 수산투자조합 제1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%비엔케이벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '비하이농식품마이크로투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%비하이인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '빌랑스 징검다리 농식품 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%빌랑스인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '세종 농식품 벤처펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%세종벤처파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '농식품 스텝업 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%세종벤처파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '스마일게이트 농식품1호 펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%스마일게이트 인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '신세계웰니스투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%시그나이트파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '시너지 농식품 밸류크리에이티브 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%시너지아이비투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '씨엔티테크 제13호 농식품 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%씨엔티테크%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'CKD Smart Farm 2호 농식품투자조') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%씨케이디창업투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '아이디브이-아이피 수산전문투자조합 3호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%아이디벤처스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'IDV-IP수산전문투자조합 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%아이디벤처스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'IDV-IP 수산전문 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%아이디벤처스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'AKGI 애그로상생경제 투자조합 1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%안강벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '넥스트웨이브 2022 수산벤처 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%엔브이씨파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엔브이씨 2021 수산벤처 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%엔브이씨파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'AJ 농식품수출육성펀드 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%엔비에이치캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'AJ-ISU경기도애그리푸드투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%엔비에이치캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엔에이치영파머스투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%엔에이치벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엘엔에스 농식품6차산업화 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%엘엔에스벤처캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '엘엔에스 농수산업 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%엘엔에스벤처캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '오라클프레쉬펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%오라클벤처투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '2022 원익 스마트 혁신 Agtech투자') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%원익투자파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '유니온수산투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%유니온투자파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '동양 농식품 2호 투자펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%유안타인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '유큐아이피농식품 투자조합 제2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%유큐아이파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '유큐아이피 농림수산식품 투자조합 제1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%유큐아이파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '유엔그린시너지투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%유티씨인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '인라이트 애그테크플러스펀드 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%인라이트벤처스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '인라이트8호 애그테크플러스펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%인라이트벤처스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '임팩트-이크럭스 농식품벤처 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%임팩트파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'DTNI-AGRITECH투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%DTN인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'NHC-DTNI 농식품 ABC 투자조합1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%DTN인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '세종 농식품R&D사업화 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%세종벤처파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '세종 농식품바이오 투자조합 1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%세종벤처파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '솔리더스-고창 프로젝트 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%솔리더스인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'SB 프로젝트투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%에쓰비인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'ECO-MGI 6차산업 전문투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%에코캐피탈%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '이앤-농식품프로젝트 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%이앤인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'TWI 농식품상생투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%타임와이즈인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '패스파인더 6차산업화 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%패스파인더에이치%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'GMB-MD농식품투자펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%지엠비인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '캐피탈원 농림수산식품 투자조합 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%캐피탈원%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '캐피탈원 농림수산식품 투자조합 3호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%캐피탈원%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '컴퍼니케이파트너스 애그로씨드투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%컴퍼니케이파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'A&F미래성장투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%클레어보이언트벤처스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '킹고 멀티플6 농식품 제1호 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%킹고투자파트너스%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '타임와이즈농식품벤처펀드') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%타임와이즈인베스트먼트%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '패스파인더 그린바이오 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%패스파인더에이치%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '패스파인더 영파머스 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%패스파인더에이치%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '하이브리드 ESG 세컨더리펀드 제일호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%포스코기술투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '포스코 농식품 수출 투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%포스코기술투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = 'POSCO-NSC 수산투자조합') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%포스코기술투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '현대-GS리테일 Agro-Bio펀드 3호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%현대기술투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '현대 Agro-Bio펀드 2호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%현대기술투자%' LIMIT 1) B;
INSERT INTO tb_fund_invstr ( fund_no,  bzenty_no,  invt_bzenty_nm,  brno,  rgtr_no,  reg_ymd) SELECT A.FUND_NO, B.BZENTY_NO, B.BZENTY_NM, B.BRNO, 'system', NOW() FROM (SELECT FUND_NO FROM TB_FUND_INFO WHERE FUND_NM = '현대 Agro-Bio펀드 1호') A, (SELECT BZENTY_NO, BZENTY_NM, BRNO FROM TB_ENT WHERE BZENTY_NM LIKE '%현대기술투자%' LIMIT 1) B;
