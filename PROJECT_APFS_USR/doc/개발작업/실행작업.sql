-- 2023.04.27 펀드규모 (억단위 -> 백만단위로 업데이트)
UPDATE TB_FUND_INFO SET fund_oper_scale = fund_oper_scale * 100;


-- 2023.04.29 투자서비스 - 매칭서비스 프로그램 권한 제외
DELETE FROM SYS_ROLE_PROG WHERE PRGRM_ID = 'PG_USR_INV0301';
-- 2023.04.29 지원서비스 - 매칭서비스 프로그램 권한 제외
DELETE FROM SYS_ROLE_PROG WHERE PRGRM_ID = 'PG_USR_SUP0201';

-- 2023.04.29 프로그램 URL 수정
-- URL 변경 ( /usr/invest/matching/openMatching.do -> /usr/support/matching/openMatching.do)
-- URL 변경 ( /usr/invest/matching/openMatching.do -> /usr/mypage/matching/openMatchInvest.do)
-- URL 변경 ( /usr/support/matching/openMatching.do -> /usr/mypage/matching/openMatchSupport.do)
UPDATE SYS_PROG SET PRGRM_URL = '/usr/mypage/matching/openMatchInvest.do'  WHERE PRGRM_ID = 'PG_USR_INV0301';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/mypage/matching/openMatchSupport.do' WHERE PRGRM_ID = 'PG_USR_SUP0201';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/mypage/matching/openMatchInvest.do'  WHERE MENU_ID  = 'MU_USR_INV0301';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/mypage/matching/openMatchSupport.do' WHERE MENU_ID  = 'MU_USR_SUP0201';

-- 2023.05.13 계정구분 수정 (3849건)
UPDATE tb_ent_fnnr A 
   SET fnnr_se_cd = (SELECT fnnr_se_cd FROM tb_fnnr_code_ko WHERE fnnr_acnt_cd = A.fnnr_acnt_cd);

-- 2023.05.16 투자자 (나이스파트너스) 홈페이지 링크 업데이트
   UPDATE tb_ent
   SET hmpg_addr = 'http://www.nicefni.co.kr'
WHERE bzenty_no = '500124';

-- 2023.05.18 프로그램 URL 수정 (1:1문의 / 농금원안내) 
UPDATE SYS_MENU SET TRGT_URL  = '/usr/inform/bbs/formQNA.do'     WHERE MENU_ID  = 'MU_USR_CUS0301';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/inform/bbs/formQNA.do'     WHERE PRGRM_ID = 'PG_USR_CUS0301';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/inform/intro/openAPFS.do'  WHERE MENU_ID  = 'MU_USR_INT0201';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/inform/intro/openAPFS.do'  WHERE PRGRM_ID = 'PG_USR_INT0201';
-- 2023.05.18 공지사항 상세정보 프로그램등록
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_CUS0101', 'PG_USR_CUS0102', '고객센터 - 공지사항 - 상세조회', '/usr/inform/bbs/viewNotice.do');

-- 2023.05.18 게시판분류 텍스트오류 수정
UPDATE SYS_CODE SET CD_NM = '경영체대상공고' WHERE UP_CD_ID = 'CT.NTT_CL' AND CD_ID = 'B101';

-- 2023.05.18 자료실 프로그램등록
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INF0301', 'PG_USR_INF0302', '정보서비스 - 자료실 - 상세조회', '/usr/inform/bbs/viewData.do');
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', NULL, 'PG_USR_INF0303', '정보서비스 - 자료실 - 작성팝업', '/usr/inform/bbs/modalData.do');
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', NULL, 'PG_USR_INF0304', '정보서비스 - 자료실 - 저장처리', '/usr/inform/bbs/saveData.do');

-- 2023.05.18 자료실 프로그램 권한등록 (등록/저장은 회원만 가능하도록 설정)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) SELECT 'system', NOW(), ROLE_ID, PRGRM_ID FROM 
(SELECT PRGRM_ID FROM SYS_PROG WHERE PRGRM_ID IN ('PG_USR_INF0303','PG_USR_INF0304')) A, 
(SELECT ROLE_ID  FROM SYS_ROLE WHERE ROLE_ID IN ('ROLE_ADM_SYS','ROLE_ADM_MNG','ROLE_USR_USR','ROLE_USR_EIV','ROLE_USR_EBZ','ROLE_USR_EIS')) B;

-- 2023.06.14 마이페이지 메뉴별 접근가능한 사용자 및 메뉴 조회
SELECT U.user_no, U.role_id, M.menu_id, M.menu_nm, U.rprs_yn AS user_rprs_yn, M.rprs_yn AS menu_rprs_only 
FROM (SELECT user_no, rprs_yn, (SELECT role_id FROM sys_role_user WHERE user_no = A.user_no) AS role_id FROM tb_grp_usr A) U,
     tb_grp_menu M
WHERE M.sys_authrt_info LIKE '%'|| U.role_id || '%'
  AND (U.rprs_yn = 'Y' or M.rprs_yn = 'N') 
;

-- 2023.06.14 마이페이지 메뉴별 사용자 권한 등록 (업체회원)
INSERT 
  INTO tb_grp_auth 
      (rgtr_no, reg_ymd, user_no, menu_id, group_authrt_cd )
SELECT '100000', NOW(), U.user_no, M.menu_id, CASE WHEN U.rprs_yn = 'Y' THEN 'M' ELSE 'V' END AS auth_cd 
  FROM (SELECT user_no, rprs_yn, (SELECT role_id FROM sys_role_user WHERE user_no = A.user_no) AS role_id FROM tb_grp_usr A) U,
       tb_grp_menu M
 WHERE M.sys_authrt_info LIKE '%'|| U.role_id || '%'
  AND (U.rprs_yn = 'Y' or M.rprs_yn = 'N') 
;

-- 2023.06.15 마이페이지 사용자 등록 (관리자/일반회원)
INSERT 
  INTO tb_grp_usr 
      (rgtr_no,reg_ymd,user_no,rprs_yn)
SELECT '100000', NOW(), user_no, 'N'
  FROM tb_user A 
 WHERE NOT EXISTS (SELECT 1 FROM tb_grp_usr WHERE user_no = A.user_no)
;

-- 2023.06.15 마이페이지 메뉴별 사용자 권한 등록 (관리자/일반회원)
INSERT 
  INTO tb_grp_auth 
      (rgtr_no,reg_ymd,user_no,menu_id,group_authrt_cd)
SELECT '100000', NOW(), U.user_no, M.menu_id, 'M' AS auth_cd
  FROM (SELECT user_no,
               (SELECT role_id FROM sys_role_user WHERE user_no = A.user_no) AS role_id
          FROM tb_user A 
         WHERE NOT EXISTS (SELECT 1 FROM tb_grp_auth WHERE user_no = A.user_no)
       ) U,
       tb_grp_menu M
 WHERE M.sys_authrt_info LIKE '%'|| U.role_id || '%'
   AND M.rprs_yn = 'N'
;

-- 2023.06.15 관리자메인 프로그램의 메뉴 ID 수정
UPDATE SYS_PROG SET menu_id = 'MU_ADM_MAN' WHERE prgrm_id = 'PG_ADM_MAN0101';

-- 2023.06.16 투자서비스 - 매칭서비스 URL 수정
UPDATE SYS_MENU SET TRGT_URL  = '/usr/invest/matching/openMatchingInvt.do' WHERE MENU_ID  = 'MU_USR_INV0301';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/invest/matching/openMatchingInvt.do' WHERE PRGRM_ID = 'PG_USR_INV0301';

-- 2023.06.16 지원서비스 - 매칭서비스 URL 수정
UPDATE SYS_MENU SET TRGT_URL  = '/usr/support/matching/openMatchingSprt.do' WHERE MENU_ID  = 'MU_USR_SUP0201';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/support/matching/openMatchingSprt.do' WHERE PRGRM_ID = 'PG_USR_SUP0201';

INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INV0301', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INV0301', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INV0301', 'ROLE_USR_EIS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INV0301', 'ROLE_USR_EIV', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INV0301', 'ROLE_USR_EBZ', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INV0301', 'ROLE_USR_RESTRICTED', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INV0301', 'ROLE_USR_USR', '100000', now());


-- 2023.06.19 경영체의 IR지원현황건수
SELECT COUNT(*)
  FROM tb_fund_info F1
     , tb_fund_sprt F2
 WHERE F1.fund_no = F2.fund_no
   AND F1.del_yn  = 'N'
   AND F1.use_yn  = 'Y'
   AND F2.bzenty_no  = ''
   
-- 2023.06.19 투자자의 경영체지원현황건수
SELECT COUNT(*)
  FROM tb_fund_info   F1
     , tb_fund_invstr F3
 WHERE F1.fund_no = F3.fund_no
   AND F1.del_yn  = 'N'
   AND F1.use_yn  = 'Y'
   AND F3.bzenty_no  = ''
   
-- 2023.06.19 투자자의 미팅신청건수(TO.경영체)
SELECT COUNT(*)
  FROM tb_mtg_aply
 WHERE aplcnt_no  = '' -- 사용자번호
   
-- 2023.06.19 투자자의 북마크건수(TO.경영체)
SELECT COUNT(*)
  FROM tb_bkmk_info
 WHERE bkmk_se_cd = 'BM1'
   AND bzenty_no  = ''

-- 2023.06.19 마이페이지 신청내역 항목종류코드
INSERT INTO SYS_CODE(CD_ID, UP_CD_ID, CD_NM, CD_CN, CD_ORDR, USE_YN, RGTR_NO, REG_YMD) 
VALUES
('CT.MYPG_APLY_SE', 'NONE', '마이페이지신청항목', '', 0, 'Y', '100000', NOW()),
('SB', 'CT.MYPG_APLY_SE', '투자유치전지원사업', 'ENT', 1, 'Y', '100000', NOW()),
('SA', 'CT.MYPG_APLY_SE', '투자유치후지원사업', 'ENT', 2, 'Y', '100000', NOW()),
('SC', 'CT.MYPG_APLY_SE', '농식품크라우드펀딩', 'ENT', 3, 'Y', '100000', NOW()),
('IR', 'CT.MYPG_APLY_SE', 'IR지원현황', 'ENT', 4, 'Y', '100000', NOW()),
('BM', 'CT.MYPG_APLY_SE', '북마크', 'INV', 5, 'Y', '100000', NOW()),
('MT', 'CT.MYPG_APLY_SE', '미팅신청내역', 'INV', 6, 'Y', '100000', NOW()),
('FD', 'CT.MYPG_APLY_SE', '경영체지원현황', 'INV', 7, 'Y', '100000', NOW());

-- 2023.06.19 마이페이지 버튼탭목록 조회
SELECT A.cd_id,
       A.cd_nm,
       CASE WHEN A.cd_id IN ('SB','SA','SC') -- 지원신청건수 (경영체)
            THEN (SELECT COUNT(*) 
                    FROM tb_invtsprt_aply 
                   WHERE sprt_aply_se_cd = A.cd_id 
                     AND aply_bzenty_no  = '' 
                     AND del_yn          = 'N' 
                     AND prgrs_stts_cd  != 'A00')

            WHEN A.cd_id = 'IR' -- IR지원건수 (경영체)
            THEN (SELECT COUNT(*)
                    FROM tb_fund_info    F1
                       , tb_fund_sprt    F2
                   WHERE F1.fund_no    = F2.fund_no
                     AND F2.bzenty_no  = ''
                     AND F1.use_yn     = 'Y'
                     AND F1.del_yn     = 'N')
					 
            WHEN A.cd_id = 'BM' -- 북마크건수 (투자자)
            THEN (SELECT COUNT(*)
                    FROM tb_bkmk_info
                   WHERE bzenty_no     = ''
                     AND bkmk_se_cd    = 'BM1')

            WHEN A.cd_id = 'MT' -- 미팅신청건수 (투자자)
            THEN (SELECT COUNT(*)
                    FROM tb_mtg_aply
                   WHERE aplcnt_no     = '')

            WHEN A.cd_id = 'FD' -- 경영체지원건수 (투자자)
            THEN (SELECT COUNT(*)
                    FROM tb_fund_info     F1
                       , tb_fund_invstr   F3
                   WHERE F1.fund_no     = F3.fund_no
                     AND F1.del_yn      = 'N'
                     AND F1.use_yn      = 'Y'
                     AND F3.bzenty_no   = '')
            ELSE 0
       END as cd_cnt
  FROM sys_code   A
 WHERE A.up_cd_id = 'CT.MYPG_APLY_SE'
   AND A.use_yn   = 'Y'
 ORDER BY
       A.cd_ordr

-- 2023.06.20 관리자 시스템 메뉴 및 프로그램 등록
-- 관리자시스템 - 운영관리 - 검색어관리
-- 관리자시스템 - 운영관리 - 상담일지
INSERT INTO SYS_MENU (menu_id, up_menu_id, menu_nm, menu_level, menu_ordr, trgt_url, popup_yn, sys_se_cd, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0601', 'MU_ADM_MNG', '검색어관리', 2, 6, '/adm/inform/bbs/openSrchWord.do', 'N', 'ADM', 'Y', '100000', now());
INSERT INTO SYS_MENU (menu_id, up_menu_id, menu_nm, menu_level, menu_ordr, trgt_url, popup_yn, sys_se_cd, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0701', 'MU_ADM_MNG', '상담일지', 2, 7, '/adm/inform/bbs/openDscsn.do', 'N', 'ADM', 'Y', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0601', 'ROLE_ADM_MNG', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0601', 'ROLE_ADM_SYS', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0701', 'ROLE_ADM_MNG', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0701', 'ROLE_ADM_SYS', '', '100000', now());

INSERT INTO SYS_PROG (prgrm_id, prgrm_nm, prgrm_url, prgrm_type, menu_id, prgrm_ordr, sys_se_cd, use_yn, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0601', '운영관리 - 검색어관리', '/adm/inform/bbs/openSrchWord.do', 'url', 'MU_ADM_MNG0601', 0, 'ADM', 'Y', '100000', now());
INSERT INTO SYS_PROG (prgrm_id, prgrm_nm, prgrm_url, prgrm_type, menu_id, prgrm_ordr, sys_se_cd, use_yn, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0701', '운영관리 - 상담일지', '/adm/inform/bbs/openDscsn.do', 'url', 'MU_ADM_MNG0701', 0, 'ADM', 'Y', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0601', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0601', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0701', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0701', 'ROLE_ADM_SYS', '100000', now());

-- 2023.06.21 경영체정보의 사업자번호를 펀드투자자의 사업자번호 변경처리
UPDATE tb_ent R SET brno = (SELECT MAX(brno) FROM tb_fund_invstr WHERE invt_bzenty_nm = R.bzenty_nm)
WHERE EXISTS (
 SELECT 1
   FROM tb_fund_invstr A
      , tb_ent         B
   WHERE A.invt_bzenty_nm = B.bzenty_nm
     AND A.brno != B.brno 
     AND B.bzenty_no = R.bzenty_no
);

-- 2023.06.21 펀드투자자정보의 업체번호를 경영체정보로 맵핑 처리
UPDATE tb_fund_invstr A SET bzenty_no = (SELECT bzenty_no FROM tb_ent WHERE brno = A.brno) WHERE bzenty_no = '' OR bzenty_no IS NULL;


-- 2023.06.27 마이페이지 그룹관리 메뉴목록
INSERT INTO SYS_CODE(CD_ID, UP_CD_ID, CD_NM, CD_CN, CD_ORDR, USE_YN, RGTR_NO, REG_YMD) 
VALUES
('CT.MYPG_GROUP_SE', 'NONE', '마이페이지그룹관리', '', 0, 'Y', '100000', NOW()),
('GC', 'CT.MYPG_GROUP_SE', '그룹코드', '', 1, 'Y', '100000', NOW()),
('GM', 'CT.MYPG_GROUP_SE', '멤버관리', '', 2, 'Y', '100000', NOW()),
('GR', 'CT.MYPG_GROUP_SE', '권한관리', '', 3, 'Y', '100000', NOW());


-- 2023.06.14 마이페이지 메뉴정보
INSERT INTO tb_grp_menu (rgtr_no,reg_ymd,rprs_yn,menu_id,menu_nm,trgt_url,sys_authrt_info) VALUES
('100000',NOW(),'N','MU_USR_MYP0101','IR작성하기','/usr/mypage/ent/openEntIr.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EBZ'),
('100000',NOW(),'N','MU_USR_MYP0201','매칭설정','/usr/mypage/matching/openMatching.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV,ROLE_USR_USR'),
('100000',NOW(),'N','MU_USR_MYP0301','신청내역','/usr/mypage/support/openSprt.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EBZ,ROLE_USR_EIV,ROLE_USR_USR'),
('100000',NOW(),'N','MU_USR_MYP0401','IR검토의견등록','/usr/mypage/opnn/openOpnnIr.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EIV'),
('100000',NOW(),'N','MU_USR_MYP0501','사업공고등록','/usr/mypage/pbanc/openPbanc.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EIS'),
('100000',NOW(),'N','MU_USR_MYP0601','문의내역','/usr/mypage/bbs/openBbs.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV,ROLE_USR_USR'),
('100000',NOW(),'N','MU_USR_MYP0701','기본정보','/usr/mypage/mypage/openInfo.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV,ROLE_USR_USR'),
('100000',NOW(),'Y','MU_USR_MYP0801','그룹관리','/usr/mypage/group/openGroup.do','ROLE_ADM_MNG,ROLE_ADM_SYS,ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV')
;

-- 2023.06.14 마이페이지 메뉴별 사용자 권한 등록 (업체회원)
INSERT 
  INTO tb_grp_auth 
      (rgtr_no, reg_ymd, user_no, menu_id, group_authrt_cd )
SELECT '100000', NOW(), U.user_no, M.menu_id, CASE WHEN U.rprs_yn = 'Y' THEN 'M' ELSE 'V' END AS auth_cd 
  FROM (SELECT user_no, rprs_yn, (SELECT role_id FROM sys_role_user WHERE user_no = A.user_no) AS role_id FROM tb_grp_usr A) U,
       tb_grp_menu M
 WHERE M.sys_authrt_info LIKE '%'|| U.role_id || '%'
  AND (U.rprs_yn = 'Y' or M.rprs_yn = 'N') 
;

-- 2023.06.15 마이페이지 사용자 등록 (관리자/일반회원)
INSERT 
  INTO tb_grp_usr 
      (rgtr_no,reg_ymd,user_no,rprs_yn)
SELECT '100000', NOW(), user_no, 'N'
  FROM tb_user A 
 WHERE NOT EXISTS (SELECT 1 FROM tb_grp_usr WHERE user_no = A.user_no)
;

-- 2023.06.15 마이페이지 메뉴별 사용자 권한 등록 (관리자/일반회원)
INSERT 
  INTO tb_grp_auth 
      (rgtr_no,reg_ymd,user_no,menu_id,group_authrt_cd)
SELECT '100000', NOW(), U.user_no, M.menu_id, 'M' AS auth_cd
  FROM (SELECT user_no,
               (SELECT role_id FROM sys_role_user WHERE user_no = A.user_no) AS role_id
          FROM tb_user A 
         WHERE NOT EXISTS (SELECT 1 FROM tb_grp_auth WHERE user_no = A.user_no)
       ) U,
       tb_grp_menu M
 WHERE M.sys_authrt_info LIKE '%'|| U.role_id || '%'
   AND M.rprs_yn = 'N'
;
-- 2023.06.27 마이페이지 메뉴 추가 (메뉴에는 노출되지 않는 항목)
INSERT INTO tb_grp_menu (rgtr_no,reg_ymd,rprs_yn,menu_id,menu_nm,trgt_url,sys_authrt_info) VALUES
('100000',NOW(),'Y','MU_USR_MYP0001','기업정보',NULL,'ROLE_USR_EBZ')
;
INSERT INTO tb_grp_auth (rgtr_no,reg_ymd,user_no,menu_id,group_authrt_cd)
SELECT '100000', NOW(), A.user_no, 'MU_USR_MYP0001', 'M'
  FROM tb_user A 
 WHERE NOT EXISTS (SELECT 1 FROM tb_grp_auth WHERE user_no = A.user_no)
   AND EXISTS (SELECT 1 FROM sys_role_user WHERE user_no = A.user_no AND role_id = 'ROLE_USR_EBZ')
;
-- 2023.06.27 사용자정보 사용상태코드 업데이트
UPDATE tb_user SET use_stts_cd = '1' WHERE use_stts_cd IS NULL or use_stts_cd = '';

-- 2023.06.28 투자자업체 (동문파트너즈) 사업자등록증 샘플파일 등록
INSERT INTO tb_ent_file (bzenty_no,sn,doc_se_cd,file_nm,strg_file_nm,file_path,file_sz,rprs_yn,del_yn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('500116',NEXTVAL('SEQ_ENT_FILE'),'02','사업자등록증.pdf','BIZFILE.pdf','/ent/sample/',0,'Y','N','system',NOW(),NULL,NULL);

-- 2023.06.28 경영체 대표계정변경시 오류 데이터 수정
UPDATE tb_grp_auth SET group_authrt_cd = 'M' WHERE user_no = '100048';

-- 2023.06.28 그룹관리 항목을 모두 추가함 (그룹권한: 제한)
INSERT INTO tb_grp_auth (  user_no,  menu_id,  group_authrt_cd,  rgtr_no,  reg_ymd)
SELECT DISTINCT user_no, 'MU_USR_MYP0801', 'R', 'system', NOW() FROM tb_grp_auth a 
  WHERE not exists (select 1 from tb_grp_auth where user_no = a.user_no AND menu_id = 'MU_USR_MYP0801')
;

-- 2023.06.29 기업정보 항목 권한추가 (경영체만)
INSERT INTO tb_grp_auth (  user_no,  menu_id,  group_authrt_cd,  rgtr_no,  reg_ymd)
SELECT DISTINCT user_no, 'MU_USR_MYP0001', 'V', 'system', NOW() FROM tb_grp_auth a 
  WHERE not exists (select 1 from tb_grp_auth where user_no = a.user_no AND menu_id = 'MU_USR_MYP0001')
    AND exists (select 1 from sys_role_user where role_id = 'ROLE_USR_EBZ' AND user_no = a.user_no)
;

-- 2023.06.29 기업정보 항목  대표계정의 권한변경
UPDATE tb_grp_auth a SET group_authrt_cd = 'M' 
 WHERE menu_id = 'MU_USR_MYP0001'
   AND exists (select 1 from tb_grp_usr where user_no = a.user_no and rprs_yn = 'Y')
;

--  2023.07.24 솔젠트 특허상표권현황 샘플생성
INSERT INTO tb_ent_ptnt (rgtr_no,reg_ymd,ir_no,sn,data_se_cd,patent_se_cd,patent_reg_ymd,patntrt_man,illt_reg_no,applnm,nm) VALUES
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20181231','솔젠트(주)','제10-1865899호', '솔젠트(주)','결핵균, 비결핵균 및 다제내성결핵균 동시 검출용 진단키트'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20181231','솔젠트(주)','제10-1865898호', '솔젠트(주)','결핵균 및 비결핵균 동시 검출용 진단키트'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20181231','솔젠트(주)','제10-1814740호', '솔젠트(주)','유전자 증폭 방법을 이용한 식중독 유발 세균의 검출 방법 및 이 방법에 사용되는 키트'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20141231','솔젠트(주)','제10-1480522호', '솔젠트(주)','아벨리노각막이상증 판별용 진단 키트'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20131231','농림수산검역검사본부','제10-1374045호', '솔젠트(주)','중합효소연쇄반응을 이용한 생물학적 시료 내에서 퀴놀론 내성 캠필로박터균을 검출하는 방법 및 이에 사용되는 키트'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20131231','국립농산물품질관리원장','제10-1355914호', '솔젠트(주)','한우 및 수입우 판별에 유용한 단일염기 다형성 마커 및 이의 용도'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20101231','솔젠트(주)','제10-1074118호', '솔젠트(주)','생쥐 비만 유전자의 유전자형 분석용 PCR 프라이머 및 이를 포함하는 키트'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20151231','한국기초과학지원연구원','제10-1548167호', '솔젠트(주)','노로바이러스 또는 A형 간염 바이러스 농축 및 검출방법'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20101231','서울대학교','제10-1046960호', '솔젠트(주)','고추에서 CMV 저항성을 진단하기 위한 프라이머 및 이의 용도'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20091231','서울대학교','제10-0920369호', '솔젠트(주)','ChiVMV 저항성 고추 품종을 선별하기 위한 프라이머 세트, 방법 및 키트'),
('system', NOW(), '1',NEXTVAL('SEQ_ENT_PTNT'),'M', '01', '20151231','솔젠트(주)','제10-150127850호', '솔젠트(주)','중합효소 연쇄반응을 이용한 분자진단검사용 중합효소 및 보조효소의 DＮＡ 오염 제거를 위한 정제 방법');

-- 2023.07.24 솔젠트 재무정보 샘플생성
INSERT INTO tb_ent_fnnr (rgtr_no,reg_ymd,ir_no,sn,fnnr_se_cd, data_se_cd,fnnr_acnt_cd,fnnr_yr,fnnr_amt) VALUES
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001110000', '2020',  810000000), -- [재무상태표] 자산총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001150000', '2020',   10000000), -- [재무상태표] 부채총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001170000', '2020',  800000000), -- [재무상태표] 자본총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001110000', '2021', 1240000000), -- [재무상태표] 자산총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001150000', '2021',  180000000), -- [재무상태표] 부채총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001170000', '2021', 1060000000), -- [재무상태표] 자본총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001110000', '2022',  540000000), -- [재무상태표] 자산총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001150000', '2022',   10000000), -- [재무상태표] 부채총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '1', 'M', '7001170000', '2022',  530000000), -- [재무상태표] 자본총계
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001210000', '2020', 3200000000), -- [손익계산서] 매출액
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001230000', '2020',  300000000), -- [손익계산서] 영엽이익
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001290000', '2020',  280000000), -- [손익계산서] 당기순이익
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001210000', '2021', 5370000000), -- [손익계산서] 매출액
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001230000', '2021',  590000000), -- [손익계산서] 영엽이익
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001290000', '2021',  760000000), -- [손익계산서] 당기순이익
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001210000', '2022', 7110000000), -- [손익계산서] 매출액
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001230000', '2022', 1210000000), -- [손익계산서] 영엽이익
('system', NOW(), '1', NEXTVAL('SEQ_ENT_FNNR'), '2', 'M', '7001290000', '2022', 1230000000); -- [손익계산서] 당기순이익

-- 2023.07.31 업무메일구분 수정 및 추가등록
DELETE FROM SYS_CODE WHERE UP_CD_ID = 'CT.BIZMAIL_SE' AND CD_ID = 'BM01001';
INSERT INTO SYS_CODE(UP_CD_ID, USE_YN, RGTR_NO, REG_YMD, CD_ID, CD_ORDR, CD_NM, CD_CN) 
VALUES
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM01001', 1, '[{platform}] 투자자가 ${bzentyNm}을 원하고 있어요!', 
'[{invtBzentyNm}]에서 [{bzentyNm}]에 미팅을 신청했습니다.<br><br> 
지금 바로 미팅 신청 내용을 확인해 보세요!<br><br>
<a href="{homeUrl}" target="_blank">[미팅신청 확인하기]</a>'
),
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM01002', 2, '[{platform}] 새로운 멤버가 가입했어요!', 
'{userNm}님이 ${bzentyNm} 소속으로 회원가입했어요.<br><br>
{userNm}님의 권한을 설정하면 공동작업할 수 있어요. 메뉴별로도 권한 설정이 가능해요!<br><br>
<a href="{homeUrl}" target="_blank">[권한설정하기]</a>'
),
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM02001', 3, '[{platform}] 기업회원으로 승인되었습니다.', 
'안녕하세요.<br>
[{platform}]입니다.<br><br> 
{userNm}님, 기업회원 정상적으로 승인되어 기업서비스 이용이 가능합니다.<br><br> 
지금 바로 로그인하여 기업회원 대상 맞춤서비스를 이용해 보세요.<br><br> 
감사합니다.<br><br> 
<a href="{homeUrl}" target="_blank">[{platform}으로 이동]</a>'	
),
('CT.BIZMAIL_SE', 'Y', '100000', NOW(), 'BM02002', 4, '[{platform}] 기업회원이 승인되지 않았습니다.', 
'안녕하세요.<br>
[{platform}]입니다.<br><br> 
요청하신 기업회원 승인이 반려되었습니다.<br><br>
기업회원으로 승인되기 위한 조건은 다음과 같습니다.<br><br> 
사업자등록번호를 보유한<br>  
1. 농림수산식품경영체(법인사업자/개인사업자)<br>
2. 투자자<br>
3. 유관기관<br><br>
관련하여 문의사항이 있으실 경우 언제든지 고객센터>1:1문의 남겨주세요.<br> 
감사합니다.<br> 
<a href="{homeUrl}" target="_blank">[{platform}으로 이동]</a>'	
);

-- 2023.08.03 업체승인상태가 NULL인 업체정보의 승인상태를 1로 변경처리
update tb_ent set use_stts_cd = '1' where use_stts_cd is null;

-- 2023.08.03 일반회원 사용자의 그룹권한을 M(수정)으로 일괄변경처리
UPDATE tb_grp_auth a SET group_authrt_cd = 'M' WHERE exists (select 1 from sys_role_user where user_no = a.user_no AND role_id = 'ROLE_USR_USR')

-- 2023.08.03 일반회원 사용자의 마이페이지 - 신청내역 모달창 관련 권한 추가
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0311', 'ROLE_USR_USR', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0312', 'ROLE_USR_USR', '100000', now());



