-- 2023.09.19 17:10 농금원 사용자중 2명의 권한이 ROLE_USR_EIS(유관기관)으로 등록되어 있었음.
-- 500163	helpdesk@apfs.kr	신희준	ROLE_USR_EIS	200001	농업정책보험금융원	30	1		N
-- 500164	dklim@apfs.kr	임동균	ROLE_USR_EIS	200001	농업정책보험금융원	30	1		N

-- 2명의 권한을 ROLE_USR_EIS 에서 ROLE_USR_MNG 로 변경처리
update sys_role_user set role_id = 'ROLE_USR_MNG' where user_no = '500163' and role_id = 'ROLE_USR_EIS';
update sys_role_user set role_id = 'ROLE_USR_MNG' where user_no = '500164' and role_id = 'ROLE_USR_EIS';

-- 변경된 권한에 대한 그룹권한 추가적용처리
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


