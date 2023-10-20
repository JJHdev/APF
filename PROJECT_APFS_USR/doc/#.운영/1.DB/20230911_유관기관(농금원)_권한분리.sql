-- 농금원 ROLE 추가
INSERT INTO SYS_ROLE (ROLE_ID, UP_ROLE_ID, ROLE_NM, SYS_SE_CD, RGTR_NO, REG_YMD)
VALUES ('ROLE_USR_MNG', 'NONE', '운영관리자', 'USR', '100000', now())
;
	
-- 농금원 사용자 ROLE 변경
UPDATE SYS_ROLE_USER
   SET ROLE_ID = 'ROLE_USR_MNG'
 WHERE USER_NO IN (SELECT USER_NO FROM TB_USER WHERE BZENTY_NO = '200001')
   AND ROLE_ID = 'ROLE_USR_EIS'
;

-- ROLE 메뉴 등록
INSERT INTO SYS_ROLE_MENU (ROLE_ID, MENU_ID, RGTR_NO, REG_YMD)
SELECT DISTINCT 'ROLE_USR_MNG', MENU_ID, '100000', now()
  FROM SYS_ROLE_MENU
 WHERE ROLE_ID IN ('ROLE_USR_SYS', 'ROLE_USR_EIS')
 ORDER BY MENU_ID
;

-- ROLE 프로그램 등록
INSERT INTO SYS_ROLE_PROG (ROLE_ID, PRGRM_ID, RGTR_NO, REG_YMD)
SELECT DISTINCT 'ROLE_USR_MNG', PRGRM_ID, '100000', now()
  FROM SYS_ROLE_PROG
 WHERE ROLE_ID IN ('ROLE_USR_SYS', 'ROLE_USR_EIS')
 ORDER BY PRGRM_ID
;
 
-- 마이페이지 권한 추가
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EBZ,ROLE_USR_EIV,ROLE_USR_EIS,ROLE_USR_MNG,ROLE_USR_SYS,ROLE_USR_USR' WHERE menu_id = 'MU_USR_MYP0201';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EIS,ROLE_USR_MNG'                                                     WHERE menu_id = 'MU_USR_MYP0501';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EBZ,ROLE_USR_EIV,ROLE_USR_EIS,ROLE_USR_MNG,ROLE_USR_SYS,ROLE_USR_USR' WHERE menu_id = 'MU_USR_MYP0701';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EBZ,ROLE_USR_EIV,ROLE_USR_EIS,ROLE_USR_MNG'                           WHERE menu_id = 'MU_USR_MYP0801';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EBZ,ROLE_USR_EIV,ROLE_USR_EIS,ROLE_USR_MNG,ROLE_USR_SYS,ROLE_USR_USR' WHERE menu_id = 'MU_USR_MYP0601';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EBZ,ROLE_USR_MNG,ROLE_USR_SYS'                                        WHERE menu_id = 'MU_USR_MYP0101';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EBZ,ROLE_USR_EIV,ROLE_USR_MNG,ROLE_USR_SYS,ROLE_USR_USR'              WHERE menu_id = 'MU_USR_MYP0301';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EIV,ROLE_USR_MNG,ROLE_USR_SYS'                                        WHERE menu_id = 'MU_USR_MYP0401';
UPDATE tb_grp_menu SET sys_authrt_info = 'ROLE_USR_EBZ,ROLE_USR_MNG,ROLE_USR_SYS'                                        WHERE menu_id = 'MU_USR_MYP0001';

-- 마이페이지 추가된 권한에 대한 그룹권한 등록
INSERT INTO tb_grp_auth (rgtr_no, reg_ymd, user_no, menu_id, group_authrt_cd )
SELECT '100000', NOW(), U.user_no, M.menu_id, 'M'
  FROM (SELECT user_no, rprs_yn, (SELECT ru.role_id 
								    FROM sys_role_user ru
								       , sys_role r
								   WHERE ru.user_no = A.user_no
								     AND ru.role_id = r.role_id
								     AND r.sys_se_cd = 'USR'
								 ) AS role_id 
		  FROM tb_grp_usr A) U,
       tb_grp_menu M 
 WHERE M.sys_authrt_info LIKE '%'|| U.role_id || '%' 
   AND NOT EXISTS (
       SELECT 1
	     FROM tb_grp_auth
		WHERE user_no = U.user_no
		  AND menu_id = M.menu_id
   ) 
;

-- ROLE_USR_MNG의 마이페이지 관련 접근 권한 추가
INSERT INTO SYS_ROLE_PROG (PRGRM_ID, ROLE_ID, RGTR_NO, REG_YMD)
SELECT PRGRM_ID, 'ROLE_USR_MNG', '100000', NOW() FROM SYS_PROG P 
 WHERE PRGRM_ID LIKE 'PG_USR_MYP_%' 
   AND EXISTS (SELECT 1 FROM SYS_ROLE_PROG WHERE PRGRM_ID = P.PRGRM_ID)
   AND NOT EXISTS (SELECT 1 FROM SYS_ROLE_PROG WHERE PRGRM_ID = P.PRGRM_ID AND ROLE_ID = 'ROLE_USR_MNG');

-- ROLE_USR_SYS의 마이페이지 관련 접근 권한 추가
INSERT INTO SYS_ROLE_PROG (PRGRM_ID, ROLE_ID, RGTR_NO, REG_YMD)
SELECT PRGRM_ID, 'ROLE_USR_SYS', '100000', NOW() FROM SYS_PROG P 
 WHERE PRGRM_ID LIKE 'PG_USR_MYP_%' 
   AND EXISTS (SELECT 1 FROM SYS_ROLE_PROG WHERE PRGRM_ID = P.PRGRM_ID)
   AND NOT EXISTS (SELECT 1 FROM SYS_ROLE_PROG WHERE PRGRM_ID = P.PRGRM_ID AND ROLE_ID = 'ROLE_USR_SYS');
