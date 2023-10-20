
-- 2023.04.27 펀드규모 공통코드 백업
-- INSERT INTO sys_code (cd_id,up_cd_id,cd_nm,cd_cn,vld_bgng_ymd,vld_end_ymd,cd_ordr,use_yn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) VALUES
--   ('IH1','CT.FUND_SIZE','10억원 미만','',NULL,NULL,1,'Y','100000',TIMESTAMP '2023-04-20 17:18:25.118039',NULL,NULL),
--   ('IH2','CT.FUND_SIZE','10억원 초과 ~ 50억원 이하','',NULL,NULL,2,'Y','100000',TIMESTAMP '2023-04-20 17:18:25.118039',NULL,NULL),
--   ('IH3','CT.FUND_SIZE','100억원 초과 ~ 200억원 이하','',NULL,NULL,3,'Y','100000',TIMESTAMP '2023-04-20 17:18:25.118039',NULL,NULL),
--   ('IH4','CT.FUND_SIZE','200억원 초과','',NULL,NULL,4,'Y','100000',TIMESTAMP '2023-04-20 17:18:25.118039',NULL,NULL);
UPDATE sys_code SET cd_nm = '10억원 미만' , cd_cn = '10억원 미만'                 WHERE up_cd_id = 'CT.FUND_SIZE' AND cd_id = 'IH1';
UPDATE sys_code SET cd_nm = '50억원 미만' , cd_cn = '10억원 초과 ~ 50억원 이하'   WHERE up_cd_id = 'CT.FUND_SIZE' AND cd_id = 'IH2';
UPDATE sys_code SET cd_nm = '100억원 미만', cd_cn = '50억원 초과 ~ 100억원 이하'  WHERE up_cd_id = 'CT.FUND_SIZE' AND cd_id = 'IH3';
UPDATE sys_code SET cd_nm = '200억원 미만', cd_cn = '100억원 초과 ~ 200억원 이하' WHERE up_cd_id = 'CT.FUND_SIZE' AND cd_id = 'IH4';

-- 2023.04.28 사업분야 설명글 추가
UPDATE SYS_CODE SET CD_CN = '<b>농작물재배업</b><br>식량작물 재배업, 채소작물 재배업, 과실작물 재배업, 화훼작물 재배업, 특용작물 재배업, 약용작물 재배업, 버섯 재배업, 양잠업 및 종자·묘목 재배업 등<br>
<b>임업</b><br>육림업(자연휴양림·자연수목원의 조성·관리·운영업 포함), 임산물 생산·채취업 및 임업용 종자·묘목 재배업 등' 
WHERE UP_CD_ID = 'CT.BIZ_RLM' AND CD_ID = 'BR1';
UPDATE SYS_CODE SET CD_CN = '동물(수생동물 제외)의 사육업·증식업·부화업·종축업(種畜業) 등' 
WHERE UP_CD_ID = 'CT.BIZ_RLM' AND CD_ID = 'BR2';
UPDATE SYS_CODE SET CD_CN = '수산동식물을 포획·채취하거나 양식하는 산업, 염전에서 바닷물을 자연 증발시켜 제조하는 염산업 및 관련 산업 등' 
WHERE UP_CD_ID = 'CT.BIZ_RLM' AND CD_ID = 'BR3';
UPDATE SYS_CODE SET CD_CN = '<b>식품을 생산, 가공, 제조, 조리, 포장, 보관, 수송 또는 판매하는 산업</b><br>농수산물에 인공을 가하여 생산·가공·제조·조리하는 산업 등<br>위 산업으로부터 생산된 산물을 포장·보관·수송 또는 판매하는 산업 등' 
WHERE UP_CD_ID = 'CT.BIZ_RLM' AND CD_ID = 'BR4';
UPDATE SYS_CODE SET CD_CN = '<b>소재</b><br>농림수산식품의 생산에 사용되는 원재료 또는 중간생산물 중 농림수산식품의 고부가가치화에 기여가 큰 것으로 농약, 비료, 양액, 사료, 동물약품, 포대, 필름 또는 포장재와 그 밖에 유사한 것<br>
<b>생산설비</b><br>농소재를 제조·가공하는 설비 중 농림수산식품의 고부가가치화에 기여가 큰 것으로서 기계 또는 공구와 그 밖에 이와 유사한 것<br>
<b>매출액 중 소재 또는 생산설비의 매출액이 50% 이상이고 상호출자제한기업집단에 속하지 않는 기업' 
WHERE UP_CD_ID = 'CT.BIZ_RLM' AND CD_ID = 'BR5';
UPDATE SYS_CODE SET CD_CN = '<b>임업 관련 산업</b><br>농산물 유통·가공업, 농업바이오, 야생조수사육업, 분재생산업, 조경업 등 / 애완동물산업, 말산업, 농촌관광 등 농업 관련 서비스업<br>
<b>임업 관련 산업</b><br>임산물 유통·가공업, 야생조수 사육업, 분재생산업 및 조경업 등 / 수목조사업 등 임업 관련 서비스업<br>
<b>수산업 관련 산업</b><br>수산물 유통·가공업, 수산바이오, 종묘산업, 해양심층수산업 등 / 낚시 등 수산레저산업 및 어업 컨설팅업 등 수산업 관련 서비스업<br>
<b>식품업 관련 산업</b><br>식품포장용기 제조업 및 음식료품 가공기계 제조업 / 음식료품 도소매업 등 식품산업 관련 서비스업<br>
<b>곤충산업</b><br>「곤충산업의 육성 및 지원에 관한 법률」제12조 제1항에 따라 곤충의 사육, 생산, 가공 또는 유통의 신고를 한 자<br>
<b>해외농업개발사업자</b><br>「해외농업개발협력법」제2조 제6호의 해외농업개발사업자<br>
<b>원양산업자</b><br>「원양산업발전법」제2조 제4호의 원양산업자<br>
<b>연구개발(R&D)에 종사하는 자</b><br>「과학기술기본법」제27조 및 같은 법 시행령 41조에 따른 「국가과학기술분류체계」의 농림수산식품분야 연구개발(R&D)에 종사하는 자' 
WHERE UP_CD_ID = 'CT.BIZ_RLM' AND CD_ID = 'BR6';


-- 2023.04.28 업체파일 구분코드 등록
INSERT INTO sys_code (cd_id,up_cd_id,cd_nm,cd_ordr,use_yn,rgtr_no,reg_ymd) 
VALUES
--('00','CT.FILE_SE','대표파일(썸네일)',1,'Y','100000',NOW()),
--('01','CT.FILE_SE','일반파일',2,'Y','100000',NOW()),
('02','CT.FILE_SE','사업자등록증',3,'Y','100000',NOW()),
('03','CT.FILE_SE','위임장',4,'Y','100000',NOW()),
('04','CT.FILE_SE','재직증명서',5,'Y','100000',NOW()),
('05','CT.FILE_SE','사업계획서',6,'Y','100000',NOW()),
('06','CT.FILE_SE','IR첨부서류',7,'Y','100000',NOW());

-- 2023.04.30 북마크구분코드 변경
UPDATE sys_code SET cd_nm = '경영체 북마크'   WHERE up_cd_id = 'CT.BKMK_SE' AND cd_id = 'BM1';
UPDATE sys_code SET cd_nm = '지원사업 북마크' WHERE up_cd_id = 'CT.BKMK_SE' AND cd_id = 'BM2';

-- 2023.05.09 데이터 구분코드 등록
INSERT INTO sys_code (cd_id,up_cd_id,cd_nm,cd_ordr,use_yn,rgtr_no,reg_ymd) 
VALUES
('CT.DATA_SE','NONE','데이터구분',1,'Y','100000',NOW()),
('K','CT.DATA_SE','KODATA',1,'Y','100000',NOW()),
('M','CT.DATA_SE','수기입력',2,'Y','100000',NOW());

-- 2023.05.09 투자단계코드 등록
INSERT INTO sys_code (cd_id,up_cd_id,cd_nm,cd_cn,cd_ordr,use_yn,rgtr_no,reg_ymd) 
VALUES
('CT.INVT_STEP_SE','NONE','투자단계코드',NULL,1,'Y','100000',NOW()),
('S0','CT.INVT_STEP_SE','시드단계','0 ~ 5억 미만', 1,'Y','100000',NOW()),
('SA','CT.INVT_STEP_SE','시리즈A' ,'5억 이상 ~ 50억 미만',2,'Y','100000',NOW()),
('SB','CT.INVT_STEP_SE','시리즈B' ,'50억 이상 ~ 200억 미만',3,'Y','100000',NOW()),
('SC','CT.INVT_STEP_SE','시리즈C' ,'200억 이상',4,'Y','100000',NOW());

-- 2023.05.09 투자구분코드 등록
INSERT INTO sys_code (cd_id,up_cd_id,cd_nm,cd_ordr,use_yn,rgtr_no,reg_ymd) 
VALUES
('CT.INVT_SE','NONE','투자구분코드',1,'Y','100000',NOW()),
('IAF','CT.INVT_SE','농식품모태펀드 투자금액 ',1,'Y','100000',NOW()),
('IAR','CT.INVT_SE','기타 투자금액' ,2,'Y','100000',NOW());

-- 2023.05.10 재무정보합계 등록
INSERT INTO sys_code (cd_id,up_cd_id,cd_nm,cd_ordr,use_yn,rgtr_no,reg_ymd) 
VALUES
('CT.FNNR_FNTL_SE','NONE','재무상태표 합계항목',1,'Y','100000',NOW()),
('CT.FNNR_PLOS_SE','NONE','손익계산서 합계항목',1,'Y','100000',NOW()),
('7009160000','CT.FNNR_FNTL_SE','자본총계',1,'Y','100000',NOW()),
('7009140000','CT.FNNR_FNTL_SE','부채총계',2,'Y','100000',NOW()),
('7009110000','CT.FNNR_FNTL_SE','자산총계',3,'Y','100000',NOW()),
('7101210000','CT.FNNR_PLOS_SE','매출액' ,1,'Y','100000',NOW()),
('7104250000','CT.FNNR_PLOS_SE','영업이익' ,2,'Y','100000',NOW()),
('7009166500','CT.FNNR_PLOS_SE','당기순이익' ,3,'Y','100000',NOW());

-- 2023.05.13 재무정보합계 샘플기준 계정코드값 조정
DELETE FROM sys_code WHERE up_cd_id IN ('CT.FNNR_FNTL_SE','CT.FNNR_PLOS_SE');
INSERT INTO sys_code (cd_id,up_cd_id,cd_nm,cd_ordr,use_yn,rgtr_no,reg_ymd) 
VALUES
('7001170000','CT.FNNR_FNTL_SE','자본총계',1,'Y','100000',NOW()),
('7001150000','CT.FNNR_FNTL_SE','부채총계',2,'Y','100000',NOW()),
('7001110000','CT.FNNR_FNTL_SE','자산총계',3,'Y','100000',NOW()),
('7001210000','CT.FNNR_PLOS_SE','매출액' ,1,'Y','100000',NOW()),
('7001230000','CT.FNNR_PLOS_SE','영업이익' ,2,'Y','100000',NOW()),
('7001290000','CT.FNNR_PLOS_SE','당기순이익' ,3,'Y','100000',NOW());


/*
 * 프로그램 구분
 * 이현복
 */
INSERT INTO sys_code
VALUES
('CT.PRGRM_SE', 'NONE', '프로그램구분', '', null, null, 0, 'Y', '100000', NOW(), null, null),
('SB01', 'CT.PRGRM_SE', '상담', '투자유치 전', null, null, 1, 'Y', '100000', NOW(), null, null),
('SB02', 'CT.PRGRM_SE', '현장코칭', '투자유치 전', null, null, 2, 'Y', '100000', NOW(), null, null),
('SB03', 'CT.PRGRM_SE', '맞춤형컨설팅', '투자유치 전', null, null, 3, 'Y', '100000', NOW(), null, null),
('SB04', 'CT.PRGRM_SE', 'IR설명회', '투자유치 전', null, null, 4, 'Y', '100000', NOW(), null, null);

/*
 * 진행상태1
 * 이현복
 */
INSERT INTO sys_code
VALUES
('CT.PRGRS_STTS1', 'NONE', '진행상태1', '', null, null, 0, 'Y', '100000', NOW(), null, null),
('B00', 'CT.PRGRS_STTS1', '임시저장', '투자유치 전', null, null, 1, 'Y', '100000', NOW(), null, null),
('B10', 'CT.PRGRS_STTS1', '상담필요', '투자유치 전', null, null, 2, 'Y', '100000', NOW(), null, null),
('B20', 'CT.PRGRS_STTS1', '상담완료', '투자유치 전', null, null, 3, 'Y', '100000', NOW(), null, null),
('B90', 'CT.PRGRS_STTS1', '사업종료', '투자유치 전', null, null, 4, 'Y', '100000', NOW(), null, null),
('A00', 'CT.PRGRS_STTS1', '임시저장', '투자유치 후', null, null, 1, 'Y', '100000', NOW(), null, null),
('A10', 'CT.PRGRS_STTS1', '심사필요', '투자유치 후', null, null, 2, 'Y', '100000', NOW(), null, null),
('A20', 'CT.PRGRS_STTS1', '반려',    '투자유치 후', null, null, 3, 'Y', '100000', NOW(), null, null),
('A30', 'CT.PRGRS_STTS1', '보완요청', '투자유치 후', null, null, 4, 'Y', '100000', NOW(), null, null),
('A40', 'CT.PRGRS_STTS1', '심사완료', '투자유치 후', null, null, 5, 'Y', '100000', NOW(), null, null),
('A90', 'CT.PRGRS_STTS1', '사업종료', '투자유치 후', null, null, 8, 'Y', '100000', NOW(), null, null),
('C00', 'CT.PRGRS_STTS1', '임시저장', '크라우드 펀딩', null, null, 1, 'Y', '100000', NOW(), null, null),
('C10', 'CT.PRGRS_STTS1', '심사필요', '크라우드 펀딩', null, null, 2, 'Y', '100000', NOW(), null, null),
('C20', 'CT.PRGRS_STTS1', '반려',    '크라우드 펀딩', null, null, 3, 'Y', '100000', NOW(), null, null),
('C30', 'CT.PRGRS_STTS1', '보완요청', '크라우드 펀딩', null, null, 4, 'Y', '100000', NOW(), null, null),
('C40', 'CT.PRGRS_STTS1', '심사완료', '크라우드 펀딩', null, null, 5, 'Y', '100000', NOW(), null, null);

/*
 * 진행세부상태코드
 * 이현복
 */
INSERT INTO sys_code
VALUES
('CT.PRGRS_DETAIL_STTS', 'NONE', '진행상태2', '', null, null, 0, 'Y', '100000', NOW(), null, null),
('10', 'CT.PRGRS_DETAIL_STTS', '생략', '투자유치 전', null, null, 1, 'Y', '100000', NOW(), null, null),
('20', 'CT.PRGRS_DETAIL_STTS', '배정', '투자유치 전', null, null, 2, 'Y', '100000', NOW(), null, null),
('30', 'CT.PRGRS_DETAIL_STTS', '중단', '투자유치 전', null, null, 3, 'Y', '100000', NOW(), null, null),
('40', 'CT.PRGRS_DETAIL_STTS', '완료', '투자유치 전', null, null, 4, 'Y', '100000', NOW(), null, null);

/*
 * 2023.05.19 LSH
 * 업무메일구분
 * 
 */
INSERT INTO SYS_CODE(CD_ID, UP_CD_ID, CD_NM, CD_CN, CD_ORDR, USE_YN, RGTR_NO, REG_YMD)
VALUES
('CT.BIZMAIL_SE','NONE','업무메일구분',NULL,1,'Y','100000',NOW()),
('BM01001','CT.BIZMAIL_SE', '미팅요청메일', '{bzentyNm}로부터 미팅이 신청되었습니다.<br>
[마이페이지 - 문의내역 - 투자자 미팅요청내역]에서 확인하시기 바랍니다.', 1,'Y','100000',NOW());

/*
 * 2023.05.22 LHB
 * 차수 코드 (맞춤형 컨설팅)
 */
INSERT INTO sys_code
VALUES
('CT.PRCS_CYCL', 'NONE', '차수', '', null, null, 0, 'Y', '100000', NOW(), null, null),
('1', 'CT.PRCS_CYCL', '1차', '', null, null, 1, 'Y', '100000', NOW(), null, null),
('2', 'CT.PRCS_CYCL', '2차', '', null, null, 2, 'Y', '100000', NOW(), null, null),
('3', 'CT.PRCS_CYCL', '3차', '', null, null, 3, 'Y', '100000', NOW(), null, null),
('4', 'CT.PRCS_CYCL', '4차', '', null, null, 4, 'Y', '100000', NOW(), null, null);

/*
 * 2023.06.09 ntarget
 * 모태펀드 상담 진행 상태코드
 */
INSERT INTO SYS_CODE 
VALUES
('CT.DSCSN_STTS', 'NONE', '상담상태', '', null, null, 0, 'Y', '100000', NOW(), null, null),
('D10', 'CT.DSCSN_STTS', '상담대기', '', null, null, 1, 'Y', '100000', NOW(), null, null),
('D20', 'CT.DSCSN_STTS', '상담완료', '', null, null, 2, 'Y', '100000', NOW(), null, null);

/*
 * 2023.06.16 JH
 * 유관기관 커뮤니티 게시판 구분코드
 */
INSERT INTO SYS_CODE(CD_ID, UP_CD_ID, CD_NM, CD_CN, CD_ORDR, USE_YN, RGTR_NO, REG_YMD) 
VALUES 
('B60', 'CT.BBS_SE', '유관기관커뮤니티', '', 7, 'Y', '100000', NOW());

/*
 * 2023.06.19 LSH
 * 마이페이지 신청항목
 */
INSERT INTO SYS_CODE(CD_ID, UP_CD_ID, CD_NM, CD_CN, CD_ORDR, USE_YN, RGTR_NO, REG_YMD) 
VALUES
('CT.MYPG_APLY_SE', 'NONE', '마이페이지신청항목', '', 0, 'Y', '100000', NOW()),
('SB', 'CT.MYPG_APLY_SE', '투자유치 전 지원사업', 'ENT', 1, 'Y', '100000', NOW()),
('SA', 'CT.MYPG_APLY_SE', '투자유치 후 지원사업', 'ENT', 2, 'Y', '100000', NOW()),
('SC', 'CT.MYPG_APLY_SE', '농식품 크라우드펀딩', 'ENT', 3, 'Y', '100000', NOW()),
('IR', 'CT.MYPG_APLY_SE', 'IR 지원현황', 'ENT', 4, 'Y', '100000', NOW()),
('BM', 'CT.MYPG_APLY_SE', '북마크', 'INV', 5, 'Y', '100000', NOW()),
('MT', 'CT.MYPG_APLY_SE', '미팅 신청 내역', 'INV', 6, 'Y', '100000', NOW()),
('FD', 'CT.MYPG_APLY_SE', '경영체 지원현황', 'INV', 7, 'Y', '100000', NOW());

/*
 * 2023.06.27 LSH
 * 마이페이지 그룹관리 탭항목
 */
INSERT INTO SYS_CODE(CD_ID, UP_CD_ID, CD_NM, CD_CN, CD_ORDR, USE_YN, RGTR_NO, REG_YMD) 
VALUES
('CT.MYPG_GROUP_SE', 'NONE', '마이페이지그룹관리', '', 0, 'Y', '100000', NOW()),
('GC', 'CT.MYPG_GROUP_SE', '그룹코드', '', 1, 'Y', '100000', NOW()),
('GM', 'CT.MYPG_GROUP_SE', '멤버관리', '', 2, 'Y', '100000', NOW()),
('GR', 'CT.MYPG_GROUP_SE', '권한관리', '', 3, 'Y', '100000', NOW());

/*
 * 2023.07.31 LSH
 * 업무메일구분 수정 및 추가등록
 * 2023.08.01 LHB
 * 임시비밀번호 추가등록
 */
DELETE FROM SYS_CODE WHERE UP_CD_ID = 'CT.BIZMAIL_SE';
INSERT INTO SYS_CODE(UP_CD_ID, USE_YN, RGTR_NO, REG_YMD, CD_ID, CD_ORDR, CD_NM, CD_CN) 
VALUES
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM01001', 1, '[{platform}] 투자자가 {bzentyNm}을 원하고 있어요!', '[{invtBzentyNm}]에서 [{bzentyNm}]에 미팅을 신청했습니다.<br><br>지금 바로 미팅 신청 내용을 확인해 보세요!<br><br><a href="{homeUrl}" target="_blank">[미팅신청 확인하기]</a>'),
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM01002', 2, '[{platform}] 새로운 멤버가 가입했어요!', '{userNm}님이 {bzentyNm} 소속으로 회원가입했어요.<br><br>{userNm}님의 권한을 설정하면 공동작업할 수 있어요. 메뉴별로도 권한 설정이 가능해요!<br><br><a href="{homeUrl}" target="_blank">[권한설정하기]</a>'),
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM01003', 3, '[{platform}] 임시비밀번호', '{userId} 계정의 임시 비밀번호입니다.<br><br>임시비밀번호 : ${tempPswd}<br><br><a href="{homeUrl}" target="_blank">[{platform}으로 이동]</a>'),
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM02001', 4, '[{platform}] 기업회원으로 승인되었습니다.', '안녕하세요.<br>[{platform}]입니다.<br><br>{userNm}님, 기업회원 정상적으로 승인되어 기업서비스 이용이 가능합니다.<br><br>지금 바로 로그인하여 기업회원 대상 맞춤서비스를 이용해 보세요.<br><br>감사합니다.<br><br><a href="{homeUrl}" target="_blank">[{platform}으로 이동]</a>'),
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM02002', 5, '[{platform}] 기업회원이 승인되지 않았습니다.', '안녕하세요.<br>[{platform}]입니다.<br><br>요청하신 기업회원 승인이 반려되었습니다.<br><br>기업회원으로 승인되기 위한 조건은 다음과 같습니다.<br><br>사업자등록번호를 보유한<br>1. 농림수산식품경영체(법인사업자/개인사업자)<br>2. 투자자<br>3. 유관기관<br><br>관련하여 문의사항이 있으실 경우 언제든지 고객센터>1:1문의 남겨주세요.<br>감사합니다.<br><a href="{homeUrl}" target="_blank">[{platform}으로 이동]</a>')
;
