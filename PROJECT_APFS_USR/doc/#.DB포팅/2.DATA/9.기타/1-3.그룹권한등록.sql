/**
 * 1. 대표계정 (전체) -기존 등록은 제외
 */
INSERT INTO tb_grp_usr (rgtr_no,reg_ymd,user_no,rprs_yn) 
SELECT '100000', NOW(), user_no, 'Y'
  FROM tb_user A
 WHERE USER_NO IN (SELECT USER_NO FROM SYS_ROLE r, SYS_ROLE_USER ru WHERE r.ROLE_ID = ru.ROLE_ID AND r.SYS_SE_CD = 'USR')
   AND NOT EXISTS (SELECT 1 FROM tb_grp_usr WHERE user_no = A.user_no)  ;
 
 
/**
 * 2. 대표계정으로된 계정을 일반계정으로 업데이트
 */
	-- 유관기관
	UPDATE tb_grp_usr
	   SET rprs_yn		= 'N'
	 WHERE user_no IN (
		  '110002'
		, '110003'
		, '110004'
		, '110005'
		, '110006'
		, '110007'
		, '110008'
		, '110009'
		, '110010'
		, '110011'
		, '110012'
		, '110013'
		, '110014'
		, '110015'
		, '110016'
		, '110017'
		, '110018'
		, '110019'
			, '130002'
			, '130003'
			, '130004'
			, '130005'
		, '120001'
		, '120002'
		, '120004'
			, '140001'
			, '140002'
			, '140003'
			, '140004'
		, '150002'
		, '150003'
	 )
 
	-- 투자자
	UPDATE tb_grp_usr
	   SET rprs_yn		= 'N'
	 WHERE user_no IN (
		  '170002'
		, '170004'
		, '170005'
		, '170006'
		, '170008'
		, '170010'
		, '170011'
		, '170012'
		, '170014'
		, '170015'
		, '170017'
		  '170018'
		, '170019'
		, '170020'
		, '170021'
		, '170022'
		, '170024'
		, '170025'
		, '170026'
		, '170028'
		, '170029'
		, '170030'
		, '170032'
		, '170033'
		, '170036'
		, '170037'
		, '170038'
		, '170040'
		, '170041'
		, '170042'
		, '170044'
		, '170045'
		, '170047'
		, '170049'
		, '170050'
		, '170051'
		, '170052'
		, '170053'
		, '170054'
		, '170055'
		, '170058'
		, '170059'
		, '170061'
		, '170062'
		, '170063'
		, '170064'
		, '170067'
		, '170069'
		, '170070'
		, '170072'
		, '170073'
		, '170074'
		, '170076'
		, '170077'
		, '170078'
		, '170080'
		, '170081'
		, '170082'
		, '170083'
		, '170085'
		, '170086'
		, '170088'
		, '170089'
		, '170090'
		, '170091'
		, '170094'
		, '170095'
		, '170096'
		, '170098'
		, '170100'
		, '170101'
		, '170103'
		, '170104'
		, '170106'
		, '170108'
		, '170110'
		, '170111'
		, '170113'
		, '170114'
		, '170116'
		, '170117'
		, '170119'
		, '170120'
		, '170121'
		, '170122'
	 )
 
 
/**
 * 3. 모든계정의 마이페이지 권한생성
 * 	- 1) 전체 삭제 후
 *  - 2) 전체 등록
 */
DELETE FROM tb_grp_auth;

INSERT INTO tb_grp_auth (rgtr_no, reg_ymd, user_no, menu_id, group_authrt_cd )
SELECT '100000', NOW(), U.user_no, M.menu_id, 
	   CASE WHEN U.rprs_yn = 'Y' THEN 'M' 
	   ELSE 'V' END AS auth_cd 
  FROM (SELECT user_no, rprs_yn, (SELECT ru.role_id 
								    FROM sys_role_user ru
								       , sys_role r
								   WHERE ru.user_no = A.user_no
								     AND ru.role_id = r.role_id
								     AND r.sys_se_cd = 'USR'
								 ) AS role_id 
		  FROM tb_grp_usr A) U,
       tb_grp_menu M 
 WHERE M.sys_authrt_info LIKE '%'|| U.role_id || '%' AND (U.rprs_yn = 'Y' or M.rprs_yn = 'N') ;
 