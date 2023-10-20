-- 2023.05.09 등록
INSERT INTO TB_USER(RGTR_NO, REG_YMD, JOIN_YMD, PSWD, USER_NO, MBL_TELNO, USER_ID, EML_ADDR, USER_NM) VALUES 
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'EBkBXJGam/+4Uy/658gVt0MY2lPZw1b/0VUvt2ja6XU=', '100001', '01011112221', 'system@test.com','system@test.com','시스템관리자'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '+LxGnVhG1DoRI5EeXPCyyYxOc1Qmj0zSG/2H1u9vg+A=', '100002', '01011112222',  'admin@test.com', 'admin@test.com','운영관리자'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'ZLn01OTh3GT4hHy94P0Z2w/60xKsN8NSQUsE739SufM=', '100003', '01011112223',   'user@test.com',  'user@test.com','일반사용자'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'OeJYhQien7MK226AKjcfK4hDwJl5iCnDd0dmbxPcde4=', '100004', '01011112224',   'invt@test.com',  'invt@test.com','투자자사용자'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'jbfQKW8Hh0KFhIiYGERvU/rJXbf9drhPy2pPYsr/dvs=', '100005', '01011112225',   'comp@test.com',  'comp@test.com','경영체사용자'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '2ObKXulYRy1Gugs1lomJ1OzMB8AhZHYyQux1Yurh1tA=', '100006', '01011112226',   'inst@test.com',  'inst@test.com','유관기관사용자')
;
-- 투자자 업체번호 맵핑 (동문파트너즈)
UPDATE tb_user SET bzenty_no = '500116' WHERE user_no = '100004';
-- 경영체 업체번호 맵핑 (솔젠트)
UPDATE tb_user SET bzenty_no = '500106' WHERE user_no = '100005';
-- 유관기관 업체번호 맵핑 (농업정책보험금융원)
UPDATE tb_user SET bzenty_no = '500149' WHERE user_no = '100006';

INSERT INTO tb_ent (bzenty_no,up_bzenty_no,bzenty_se_cd,bzenty_scale, brno,crno,bzenty_nm,rprsv_nm,fndn_ymd,rprs_telno,fxno,tpbiz_cd,tpbiz_nm,bzenty_type_cd,bzenty_stle_cd,invt_hope_amt,eml_addr,hmpg_addr,zip,lctn_addr,lctn_daddr,emp_cnt,mngr_memo,aprv_ymd,rjct_ymd,use_stts_cd,lcinfo,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('500117',NULL,'10',NULL,'1378615186',NULL,'(주)세종벤처파트너스','박향미','20111122','0313624773',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:19:04.415353',NULL,NULL),
  ('500118',NULL,'10',NULL,'6158127149',NULL,'(주)센트럴투자파트너스','남융희','20020524','0313624774',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:19:04.415353',NULL,NULL),
  ('500119',NULL,'10',NULL,'3448701481',NULL,'(주)이수창업투자','이병구','20190924','0313624775',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:19:04.415353',NULL,NULL),
  ('500120',NULL,'10',NULL,'1318175634',NULL,'(주)이앤인베스트먼트','서민석','20021114','0313624776',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:19:04.415353',NULL,NULL),
  ('500121',NULL,'10',NULL,'1228623762',NULL,'KB인베스트먼트','임규영','20120404','0313624777',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:19:04.415353',NULL,NULL),
  ('500123',NULL,'10',NULL,'5798801804',NULL,'NH벤처투자(주)','허미자','20201022','0313624778',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500124',NULL,'10',NULL,'1498102020',NULL,'나이스투자파트너스(주)','문명일','20210701','0313624779',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500125',NULL,'10',NULL,'1168108325',NULL,'NH투자증권(주)','김기용','19841030','0313624780',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500126',NULL,'10',NULL,'4088162742',NULL,'동훈인베스트먼트','김원영','20040712','0313624781',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500127',NULL,'10',NULL,'4128801122',NULL,'롯데액셀러레이터','김경옥','20180806','0313624782',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500128',NULL,'10',NULL,'3128143672',NULL,'마그나인베스트먼트','고영임','20000808','0313624783',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500129',NULL,'10',NULL,'4028198956',NULL,'메가인베스트먼트(주)','임은환','20121102','0313624784',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500130',NULL,'10',NULL,'1198102499',NULL,'미래에셋벤처투자(주)','이윤우','19710729','0313624785',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500131',NULL,'10',NULL,'3078106942',NULL,'미시간벤처캐피탈(주)','홍무선','19941220','0313624786',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500132',NULL,'10',NULL,'6088148919',NULL,'비하이인베스트먼트(유)','박경현','20020821','0313624787',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500133',NULL,'10',NULL,'3068101245',NULL,'빌랑스인베스트먼트','김진우','19530529','0313624788',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500134',NULL,'10',NULL,'1418124205',NULL,'시그나이트파트너스','손영준','20110531','0313624789',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500135',NULL,'10',NULL,'6088154968',NULL,'씨엔티테크','오윤서','20040212','0313624790',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500136',NULL,'10',NULL,'1258106772',NULL,'씨케이디창업투자','이옥란','19750413','0313624791',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500137',NULL,'10',NULL,'6208126351',NULL,'아이디벤처스(주)','김혁희','20061121','0313624792',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500138',NULL,'10',NULL,'4978800615',NULL,'안강벤처투자(주)','황상원','20170726','0313624793',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500139',NULL,'10',NULL,'6218147887',NULL,'엔브이씨파트너스','김선미','20020206','0313624794',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500140',NULL,'10',NULL,'3018118907',NULL,'오라클벤처투자','이병철','19931201','0313624795',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500141',NULL,'10',NULL,'1348181737',NULL,'원익투자파트너스','배선희','20020328','0313624796',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500142',NULL,'10',NULL,'6208110295',NULL,'유니온투자파트너스','이서헌','19760803','0313624797',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500143',NULL,'10',NULL,'1198125774',NULL,'인라이트벤처스','임연숙','19970621','0313624798',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500144',NULL,'10',NULL,'1218135940',NULL,'임팩트파트너스','오준열','20000315','0313624799',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500145',NULL,'10',NULL,'1378620603',NULL,'타임와이즈인베스트먼트','강인오','20120420','0313624800',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500146',NULL,'10',NULL,'1348104079',NULL,'패스파인더에이치','김종순','19771129','0313624801',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500147',NULL,'10',NULL,'1258625094',NULL,'포스코기술투자(주)','황종수','20140902','0313624802',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500148',NULL,'10',NULL,'1138683602',NULL,'현대기술투자(주)','문영우','20140402','0313624803',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:20:07.102400',NULL,NULL),
  ('500107',NULL,'20',NULL,'4028120860',NULL,'동해금속','서동해','20120930','06522625233',NULL,NULL,NULL,NULL,NULL,6500,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500108',NULL,'20',NULL,'3018193930',NULL,'성원메디칼','이대희','19871202','0432326424',NULL,NULL,NULL,NULL,NULL,21600,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500109',NULL,'20',NULL,'1378161860',NULL,'이큐셀','이헌철','20040630','0312993888',NULL,NULL,NULL,NULL,NULL,10000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500110',NULL,'20',NULL,'1298132781',NULL,'아이티엠반도체','나혁휘','20000223','0432706700',NULL,NULL,NULL,NULL,NULL,10500,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500111',NULL,'20',NULL,'6988800163',NULL,'(주)에이텍모빌리티','신승영','20150720','0316988743',NULL,NULL,NULL,NULL,NULL,14000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500112',NULL,'20',NULL,'1018636260',NULL,'동원팜스','노경탁','20071221','0326803212',NULL,NULL,NULL,NULL,NULL,13000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500113',NULL,'20',NULL,'2138636108',NULL,'엔지스테크널러지','박용선','19980812','0215229060',NULL,NULL,NULL,NULL,NULL,9600,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500114',NULL,'20',NULL,'1198131956',NULL,'두진양행(주)','이욱희','19990826','028577361',NULL,NULL,NULL,NULL,NULL,30000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL),
  ('500149',NULL,'30',NULL,'9001234567',NULL,'농업정책보험금융원','민연태','20000801','0212345678',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-30 10:42:17.571399',NULL,NULL),
  ('500150',NULL,'30',NULL,'9001234568',NULL,'농림식품기술기획평가원','노수현','20120930','0212345679',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-30 10:42:17.571399',NULL,NULL),
  ('500151',NULL,'30',NULL,'9001234569',NULL,'한국농업기술진흥원','안호근','19871202','0212345670',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-30 10:42:17.571399',NULL,NULL),
  ('500152',NULL,'30',NULL,'9001234510',NULL,'한국식품산업클러스터진흥원','김영재','20040630','0212345671',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-30 10:42:17.571399',NULL,NULL),
  ('500116',NULL,'10',NULL,'1408184025',NULL,'(유)동문파트너즈','김정옥','20140303','0428645691',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-30 10:42:17.571399',NULL,NULL)
  ;
INSERT INTO tb_ent (
bzenty_no,up_bzenty_no,bzenty_se_cd,brno,crno,bzenty_nm,rprsv_nm,fndn_ymd,rprs_telno,fxno,tpbiz_cd,tpbiz_nm,bzenty_type_cd,bzenty_stle_cd,bzenty_scale_cd,invt_hope_amt,
eml_addr,hmpg_addr,zip,lctn_addr,lctn_daddr,emp_cnt,mngr_memo,aprv_ymd,rjct_ymd,use_stts_cd,lcinfo,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('500106',NULL,'20','1231212345',NULL,'솔젠트㈜','석도수','20000801','0428645695','0428645690','C21102','생물학적 제제 제조업','1', '01', '03', 21000,
  'admin@solgent.com','http://www.solgent.com','34014','대전광역시 유성구 테크노5로','43-10(관평동)',55,NULL,NULL,NULL,'1',NULL,'system',TIMESTAMP '2023-04-27 11:18:18.845511',NULL,NULL);

INSERT INTO tb_ent_ir (rgtr_no,reg_ymd,prgrs_stts_cd,ir_no,bzenty_no,pic_nm,pic_telno,rls_yn,pr_vido_url,inq_cnt,main_biz_cn,core_itm_cn,biz_cn) VALUES
('system', NOW(), '10', NEXTVAL('SEQ_ENT_IR'),'500106','석도수','0428645695','Y','https://www.youtube.com/embed/pBlBRwLjVWM',0,
'더 정확한 유전자 분석을 위해 생물체와 유전자, 유전자와 사람, 사람과 더 나은 미래를 이어주는 기본이 튼튼한 기업을 지향합니다.',
'#바이오 인토매틱스 #코로나19 진단키트',
'솔젠트는 유전정보를 통해 질환의 감염여부와 감염위험성을 진단하기 위한 분자진단키트를 제조하고 있습니다.');

INSERT INTO tb_ent_co_hst (ir_no,sn,bgng_ymd,end_ymd,cn,rmrk,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('1',1,'2000',NULL,'솔젠트(주) 설립',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',2,'2001',NULL,'분자유전연구소 설립',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',3,'2004',NULL,'영남사업소 설립',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',4,'2005',NULL,'수도권사업소 설립',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',5,'2005',NULL,'우량기술기업선정',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',6,'2007',NULL,'유망중소기업선정(대전광역시)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',7,'2009',NULL,'산학협력우수기업 선정',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',8,'2010',NULL,'(주)미르젠 합병',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',9,'2011',NULL,'ISO 13485 인증',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',10,'2012',NULL,'INNO-BIZ기업 선정(5차)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',11,'2012',NULL,'벤처기업 인증(5차)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',12,'2012',NULL,'한우/수입우 검사법 농품원 독점 고시',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',13,'2013',NULL,'GMP인증',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',14,'2014',NULL,'INNO-BIZ기업 선정(6차)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',15,'2014',NULL,'벤처기업 인증(6차)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',16,'2015',NULL,'축산물시험검사기관 지정',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',17,'2015',NULL,'수출유망중소기업 선정(중소기업청)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',18,'2016',NULL,'GMP인증',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',19,'2017',NULL,'INNO-BIZ기업 선정(7차)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',20,'2017',NULL,'벤처기업 인증(7차)',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',21,'2017',NULL,'ISO 9001 인증',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',22,'2017',NULL,'ISO 13485 인증',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',23,'2018',NULL,'2018년산 공공비축 매입 벼의 품종 위탁 검정 사업 수주',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',24,'2018',NULL,'ISO 13485:2016 인증',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',25,'2018',NULL,'ISO 9001:2015 인증',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',26,'2018',NULL,'EDGC와 개발 및 마케팅 MOU 체결',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',27,'2019',NULL,'"2019 4IR 어워즈" 바이오 대상 수상',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',28,'2019',NULL,'중국 라이프리얼사와 업무협약 체결',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',29,'2019',NULL,'전남대학교 산학협력단 기술이전 협약',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',30,'2020',NULL,'"대한민국 중소기업-스타트업 대상" 중소기업벤처부장관상 수상',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',31,'2020',NULL,'코로나19 진단키트 미국 식품의약국(FDA) 긴급사용승인 획득',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',32,'2020',NULL,'코로나19 진단키트 국내 긴급사용 승인 획득',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',33,'2021',NULL,'국립농산물품질관리원(NAQS) "우수 검정기관 선정"',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',34,'2021',NULL,'KMEDIhub로 부터 산화그래핀 관련 전용 실시권 획득',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL),
  ('1',35,'2021',NULL,'전남대학교로 부터 NADPH관련 전용 실시권 획득',NULL,'system',TIMESTAMP '2023-05-06 12:17:24.320945',NULL,NULL);

INSERT INTO tb_ent_file (bzenty_no,sn,doc_se_cd,file_nm,strg_file_nm,file_path,file_sz,rprs_yn,del_yn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('500106',1,'00','img1.jpg','img1.jpg','/ent/500106/',0,'Y','N','system',TIMESTAMP '2023-04-28 14:07:36.694051',NULL,NULL),
  ('500106',2,'00','img2.png','img2.png','/ent/500106/',0,'N','N','system',TIMESTAMP '2023-05-04 22:47:40.051833',NULL,NULL),
  ('500106',3,'00','img3.png','img3.png','/ent/500106/',0,'N','N','system',TIMESTAMP '2023-05-04 22:47:40.051833',NULL,NULL),
  ('500106',5,'06','2023-24_SolGent_Catalogue.pdf','A.pdf','/ent/500106/',0,'Y','N','system',TIMESTAMP '2023-05-05 23:03:42.852211',NULL,NULL),
  ('500106',6,'06','2013_SolGent_Molecular_Diagnostics.pdf','B.pdf','/ent/500116/',0,'Y','N','system',TIMESTAMP '2023-05-05 23:03:42.852211',NULL,NULL),
  ('500106',7,'06','솔젠트_회사소개서.pdf','D.pdf','/ent/500106/',0,'Y','N','system',TIMESTAMP '2023-05-05 23:03:42.852211',NULL,NULL),
  ('500106',4,'05','사업계획서.pptx','C.pptx','/ent/500106/',0,'Y','N','system',TIMESTAMP '2023-05-05 23:03:42.852211',NULL,NULL);

INSERT INTO tb_ent_lwst (ir_no,sn,acusr_nm,dfdt_nm,lwst_cn,lwst_amt,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('1',1,'EDGC','솔젠트','상환전환우선주(RCPS) 효력정지 가처분 소송',300000000,'system',TIMESTAMP '2023-05-06 14:45:28.883864',NULL,NULL);

INSERT INTO tb_ent_mgmt (ir_no,sn,jbps_nm,flnm,age,career_cn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('1',1,'대표이사','석도수',60,'솔젠트(주) 설립','system',TIMESTAMP '2023-05-06 14:45:28.924565',NULL,NULL),
  ('1',2,'사내이사','임민규',55,'솔젠트(주) 설립','system',TIMESTAMP '2023-05-06 14:45:28.924565',NULL,NULL),
  ('1',3,'사내이사','강지훈',53,'솔젠트(주) 설립','system',TIMESTAMP '2023-05-06 14:45:28.924565',NULL,NULL),
  ('1',4,'사외이사','윤기열',62,'','system',TIMESTAMP '2023-05-06 14:45:28.924565',NULL,NULL),
  ('1',5,'사외이사','정희연',51,'','system',TIMESTAMP '2023-05-06 14:45:28.924565',NULL,NULL);

INSERT INTO tb_ent_rprsv_hst (ir_no,sn,bgng_ymd,end_ymd,hstry_cn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('1',1,'19950228',NULL,'선도대학교 농과대학 졸업','system',TIMESTAMP '2023-05-06 14:45:28.940897',NULL,NULL),
  ('1',2,'19960101','20001231','선도농림수산식품 주식회사 근무','system',TIMESTAMP '2023-05-06 14:45:28.940897',NULL,NULL),
  ('1',3,'20000120',NULL,'솔젠트(주) 설립','system',TIMESTAMP '2023-05-06 14:45:28.940897',NULL,NULL),
  ('1',4,'20020815',NULL,'보건복지부 장관 표창 수상','system',TIMESTAMP '2023-05-06 14:45:28.940897',NULL,NULL);

INSERT INTO tb_ent_shrholdr (ir_no,sn,flnm,invt_amt,qota_rt,rel_cn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('1',1,'석도수',673200000,67.32,'본인','system',TIMESTAMP '2023-05-06 14:45:28.909210',NULL,NULL),
  ('1',2,'이원다이애그노믹스(주)',167500000,16.75,'본인','system',TIMESTAMP '2023-05-06 14:45:28.909210',NULL,NULL),
  ('1',3,'임민규',102500000,10.25,'본인','system',TIMESTAMP '2023-05-06 14:45:28.909210',NULL,NULL),
  ('1',4,'강지훈',56800000,5.68,'본인','system',TIMESTAMP '2023-05-06 14:45:28.909210',NULL,NULL);
  
INSERT INTO tb_ent_ptnt (rgtr_no,reg_ymd,ir_no,sn,data_se_cd,patent_se_cd,patent_reg_ymd,patntrt_man,illt_reg_no,applnm,nm) VALUES
('system', NOW(), '1', 1,'M', '10', '20181231','솔젠트(주)','제10-1865899호', '솔젠트(주)','결핵균, 비결핵균 및 다제내성결핵균 동시 검출용 진단키트'),
('system', NOW(), '1', 2,'M', '10', '20181231','솔젠트(주)','제10-1865898호', '솔젠트(주)','결핵균 및 비결핵균 동시 검출용 진단키트'),
('system', NOW(), '1', 3,'M', '10', '20181231','솔젠트(주)','제10-1814740호', '솔젠트(주)','유전자 증폭 방법을 이용한 식중독 유발 세균의 검출 방법 및 이 방법에 사용되는 키트'),
('system', NOW(), '1', 4,'M', '10', '20141231','솔젠트(주)','제10-1480522호', '솔젠트(주)','아벨리노각막이상증 판별용 진단 키트'),
('system', NOW(), '1', 5,'M', '10', '20131231','농림수산검역검사본부','제10-1374045호', '솔젠트(주)','중합효소연쇄반응을 이용한 생물학적 시료 내에서 퀴놀론 내성 캠필로박터균을 검출하는 방법 및 이에 사용되는 키트'),
('system', NOW(), '1', 6,'M', '10', '20131231','국립농산물품질관리원장','제10-1355914호', '솔젠트(주)','한우 및 수입우 판별에 유용한 단일염기 다형성 마커 및 이의 용도'),
('system', NOW(), '1', 7,'M', '10', '20101231','솔젠트(주)','제10-1074118호', '솔젠트(주)','생쥐 비만 유전자의 유전자형 분석용 PCR 프라이머 및 이를 포함하는 키트'),
('system', NOW(), '1', 8,'M', '10', '20151231','한국기초과학지원연구원','제10-1548167호', '솔젠트(주)','노로바이러스 또는 A형 간염 바이러스 농축 및 검출방법'),
('system', NOW(), '1', 9,'M', '10', '20101231','서울대학교','제10-1046960호', '솔젠트(주)','고추에서 CMV 저항성을 진단하기 위한 프라이머 및 이의 용도'),
('system', NOW(), '1',10,'M', '10', '20091231','서울대학교','제10-0920369호', '솔젠트(주)','ChiVMV 저항성 고추 품종을 선별하기 위한 프라이머 세트, 방법 및 키트'),
('system', NOW(), '1',11,'M', '10', '20151231','솔젠트(주)','제10-150127850호', '솔젠트(주)','중합효소 연쇄반응을 이용한 분자진단검사용 중합효소 및 보조효소의 DＮＡ 오염 제거를 위한 정제 방법');

INSERT INTO tb_ent_othspt_hst (rgtr_no,reg_ymd,ir_no,sn,biz_nm,inst_nm,fld_cn,dtl_cn) VALUES
('system', NOW(), '1', NEXTVAL('SEQ_ENT_OTHSPT_HST'),'청년식품 창업성장지원 사업(초기)', '국가식품클러스터', '스마트 농업','최근 3개년 정보를 선택할 수 있는 버튼으로 해당 연도에 지원했던..'),
('system', NOW(), '1', NEXTVAL('SEQ_ENT_OTHSPT_HST'),'2023년 중소기업 TV홈쇼핑 추가 지원 사업 신청', '중소기업중앙회충북지역본부', '내수','충청북도는 도내 중소기업 제품의 홈쇼핑 방송판매 입점 지원으로 대형유통망 개척 및 판로 확대를 지원하기 위해 아래와 같이 2023년 「충청북도 중소기업 TV홈쇼핑 추가 지원 사업」을 추진하오니 희망하시는 업체는 기한 내 신청하여 주시기 바랍니다.');

INSERT INTO tb_ent_invt_amt (rgtr_no,reg_ymd,ir_no,sn,invt_se_cd,invt_yr,invt_amt,invt_step_cd) VALUES
('system', NOW(), '1', 1,'IAF', '2020',  350000000, 'S0'),
('system', NOW(), '1', 2,'IAF', '2021',  940000000, 'SA'),
('system', NOW(), '1', 3,'IAR', '2020',  120000000, 'SC'),
('system', NOW(), '1', 4,'IAR', '2021', 2100000000, 'SB'),
('system', NOW(), '1', 5,'IAR', '2022',   70000000, 'S0');

INSERT INTO tb_ent_rlm (bzenty_no,fld_se_cd,fld_cd,fld_cn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('500106','B','BR1',NULL,'system',TIMESTAMP '2023-04-28 14:53:54.578834',NULL,NULL),
  ('500106','B','BR3',NULL,'system',TIMESTAMP '2023-04-28 14:53:54.578834',NULL,NULL),
  ('500106','B','BR4',NULL,'system',TIMESTAMP '2023-04-28 14:53:54.578834',NULL,NULL),
  ('500106','I','IRC2302',NULL,'system',TIMESTAMP '2023-04-28 14:53:54.578834',NULL,NULL),
  ('500106','I','IRC2303',NULL,'system',TIMESTAMP '2023-04-28 14:53:54.578834',NULL,NULL),
  ('500106','I','IRC2304',NULL,'system',TIMESTAMP '2023-04-28 14:53:54.578834',NULL,NULL);

INSERT INTO tb_bkmk_info (sn,bzenty_no,trgt_bzenty_no,doc_no,bkmk_se_cd,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  (1,'500116','500106',NULL,'BM1','system',TIMESTAMP '2023-04-29 18:22:58.583030',NULL,NULL),
  (3,'500116','500151','1000013','BM2','100016',TIMESTAMP '2023-05-01 20:01:28.780634',NULL,NULL),
  (4,'500116','500152','1000026','BM2','100016',TIMESTAMP '2023-05-01 20:01:36.397723',NULL,NULL);

  
INSERT INTO tb_sprt_biz(sn,rls_yn,del_yn,rgtr_no,reg_ymd,bzenty_no,bzenty_nm,crdns_bzenty_no,crdns_bzenty_nm,biz_yr,biz_nm,artcl_nm1,artcl_cn1,artcl_nm2,artcl_cn2,artcl_nm3,artcl_cn3,artcl_nm4,artcl_cn4) VALUES
 (NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500150','농림식품기술기획평가원','2018','R&D 지원사업','사업명','농업에너지 자립형산업모델 기술개발','연구과제명','피내접종용 0000000 개발 및 산업화','총연구기간','0000.00.00.~0000.00.00.','연구비 지원','360,000천원')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500150','농림식품기술기획평가원','2019','우수연구개발 혁신제품 지정','구분','패스트트랙Ⅰ','혁신제품명','생분해성 종이 멀칭지','유효기간','0000.00.00.~0000.00.00.','지정번호','2022-000')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500150','농림식품기술기획평가원','2020','농림식품신기술인증제','인증기술분야','유전자원 육종','기술명','Micropig의 질환모델 생산 및 관리기술','인증번호','22-001','유효기간(인증/만료일)','0000.00.00.~0000.00.00.')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500150','농림식품기술기획평가원','2020','농림축산식품 녹색인증제','기관(기업)체명','주식회사 김윤석','기술명칭','Biochar Bead를 이용한 탄소저감형 토양개량 기술','','','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500151','한국농업기술진흥원','2019','농식품 벤처육성 지원사업(창업기업)','유효기간(인증/만료일)','0000.00.00.~0000.00.00.','인증번호','GT-22-12345','','','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500151','한국농업기술진흥원','2019','농식품 벤처육성 지원사업(첨단기술)','사업분야','스마트농업','아이템','농업분야 기술이전 및 창업·사업화지원 등','지원금(백만원)','21','계약일','40063')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500151','한국농업기술진흥원','2020','농식품 기술창업 액셀러레이터 육성지원','분야','스마트','세분야','차세대스마트팜시스템','아이템','스마트농업을 위한 LED 솔루션','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500151','한국농업기술진흥원','2020','대기업 상생 협업 프로그램','담당 액셀러레이터','씨엔티테크','사업분야','그린바이오','아이템','육류 등 식재료 대체 단백질 원료 개발','소재지','서울 강남구')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500151','한국농업기술진흥원','2021','혁신분야 창업패키지(비대면 스타트업 육성)','참여 프로그램 명','미래식단 2기','매칭 계열사','롯데칠성','아이템','다이어트/당뇨 환자를 위한 저당식품','협업과제','저당 탄산/이온음료 공동 개발')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500151','한국농업기술진흥원','2021','농업실용화기술R&D지원(공공R&D)','과제명','환자식 영양정보 데이터 기반 환자별 맞춤형 식단 솔루션','창업일','43101','사업비','100000000','전문기술분야','바이오·의료·생명')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500151','한국농업기술진흥원','2021','농업실용화기술R&D지원(민간R&D)','연구과제명','실시간으로 작물별 CO2를 생성하고 공급할 수 있는 시스템 개발','총 연구기간','2021-01-01~2022-11-30','총연구비','100000000','연구분야','스마트팜')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500152','한국식품산업클러스터진흥원','2019','청년식품 창업성장지원 사업(초기)','업종','부동산, 제조업, 임대업, 기술서비스','사업화 과제명','햄프씨드 빵','사업분야','식품제조','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500152','한국식품산업클러스터진흥원','2019','청년식품 창업성장지원 사업(예비)','업종','식품제조','사업화 과제명','햄프씨드 빵','사업분야','식품제조','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500152','한국식품산업클러스터진흥원','2020','소스·전통장류 혁신성장 지원사업','과제명','햄프씨드 잼','수행기간','협약일로부터 ~ ''23. 11. 11.','세부내용','개발 제품의 품질개선 지원 등','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500152','한국식품산업클러스터진흥원','2020','공동기술개발사업','지원분야','식품 관련 기술/생산 지원 등','과제명','햄프씨드 잼','수행기간','협약일로부터 ~ ''23. 11. 11.','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500149','농업정책보험금융원','2021','투자 전 지원사업','사업년도','2021','사업명','현장코칭','사업내용','전문위원의 사업체 본사/공장 등 방문 컨설팅','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500149','농업정책보험금융원','2021','투자 후 지원사업','사업년도','2022','사업명','국내 온라인 마케팅','세부사업내용','온라인 교육, PR 컨텐츠 마케팅 지원, 인플루언서 마케팅 지원','사업내용','20자 내')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500149','농업정책보험금융원','2021','투자받은 금액','계정','농식품계정 or 수산계정','투자받은 시기','2022.3','금액(만원)','10000','','')
,(NEXTVAL('seq_sprt_biz'),'Y','N','system',NOW(),'500106','솔젠트㈜','500149','농업정책보험금융원','2021','크라우드펀딩 지원사업','사업년도','2023','지원사업 및 사업설명','20자 내','주사업품목','15자 내','청약금액(만원)','2000')
;