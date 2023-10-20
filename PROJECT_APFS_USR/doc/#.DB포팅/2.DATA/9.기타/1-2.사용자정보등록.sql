/**
 * 	1. 시스템관리자 용자정보
 * 	2. 농금원 사용자정보
 * 		-> TB_USER
 * 		-> TB_ROLE_USER
 * 
 * 	#. 농금원
 * 		- User1 이 2개의 ROLE 사용. 사용자시스템 -> 유관기관. 관리자시스템 -> 운영관리자. 
 */

/* 사용자정보 [시스템관리자] */
	-- 시스템관리자 (패스워드 : admin!234)
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd)
	VALUES('100000', '200001', 'sysadmin@apfs.kr', '시스템관리자', 'qYViJgeW1nBuu0Nej2jdOalBpBoxdQSOGsTYuK+RJy4=', '', NULL, '', '20230814', 0, '1', 'N', '100000', now());

	
/* 사용자정보 [테스트] */	
	-- 농금원 운영관리자(테스트용) (패스워드 : admin!234)
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd)
	VALUES('100001', '200001', 'mngadmin@apfs.kr', '운영관리자', 'XGJbLUgfjQJqp9zsx3kANTJI7CqM4xP6o9OpuuTlVLU=', '', NULL, '', '20230814', 0, '1', 'N', '100000', now());

	
	
/* 사용자정보 [유관기관] form [(유관기관) 투자정보 플랫폼 계정 생성 요청 목록.xlsx] */
	-- 농금원
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110001', '200001', 'outerrrr@apfs.kr', '정원식', '44E7zav10gLRUI8cFWK0n/j+Pi9x5ejhgBcZX5/Iyt0=', 'outerrrr@apfs.kr', NULL, '01077995365', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110002', '200001', 'lks2670@apfs.kr', '이공섭', 'ZYMfhAVCWCUewOEC7sQfdsfyn3r7179aBZ0gMg5K89E=', 'lks2670@apfs.kr', NULL, '01046592246', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110003', '200001', 'psj17833@apfs.kr', '박수정', 'aT8oOq/CA1uZOnwlVjrRUJJzfwnEGY9Glnc8XyDsk08=', 'psj17833@apfs.kr', NULL, '01021250847', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110004', '200001', 'ssoojp@apfs.kr', '박수정', 'QrsnqhJM5pKUihn4czetNWjcLZQTNnia/sFh7VhILsw=', 'ssoojp@apfs.kr', NULL, '01085038685', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110005', '200001', '805@apfs.kr', '유다인', 'zZvhOxalVPkChdUBAl60fa8nbQwqWtPKeqxxV8m1NcA=', '805@apfs.kr', NULL, '01095999563', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110006', '200001', 'bge0722_@apfs.kr', '배규은', '+KSmqJzCnezSY7UyYBNDRlH2JIizf3larb6H9omz1rk=', 'bge0722_@apfs.kr', NULL, '01056480772', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110007', '200001', 'bjc@apfs.kr', '백종철', '4bdfhGu8uv7HLSepJkgMMrI3scU64Sbx4BcJLjzedhk=', 'bjc@apfs.kr', NULL, '01024428531', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110008', '200001', 'jmchoi@apfs.kr', '최재명', 'jpH3nvMg3Jho0VGuddwXqvtRl8+c5crVmpZT8jzCKnE=', 'jmchoi@apfs.kr', NULL, '01071787796', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110009', '200001', 'syg@apfs.kr', '서영규', '84Xu9lWRJW83q6LaukqlSLxV2Fmw01rnBCLetmj/wjo=', 'syg@apfs.kr', NULL, '01048780979', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110010', '200001', 'sss@apfs.kr', '심상석', 'n/o/4cuT5WFLo/l6x6UhT0YVX9I8xmQ8Y3BbxGJWK2E=', 'sss@apfs.kr', NULL, '01073110272', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110011', '200001', 'hykim@apfs.kr', '김형연', 'ODNimgPAMk4mtCnMgMQv6ZxU45SqSUsdgLbNCOuP9j8=', 'hykim@apfs.kr', NULL, '01028591893', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110012', '200001', 'stella@apfs.kr', '최솜이', 'wSnyyz8vAGV0c4p2ZYpHv63aczp+Cxn+npcSf3lbVuQ=', 'stella@apfs.kr', NULL, '01027022388', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110013', '200001', 'leesh@apfs.kr', '이성훈', 'GgJSbPNhdbM36/acjxENFUZUM7ycmxu7cpyUe12elYc=', 'leesh@apfs.kr', NULL, '01065471992', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110014', '200001', 'ykc@apfs.kr', '유근철', 'VrmlMMP1WNkMcPhsgU/kp6+Qw+Ow08a1oGz40LbOa3k=', 'ykc@apfs.kr', NULL, '01087205877', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110015', '200001', 'yhs8566@apfs.kr', '양한슬', 'GyUmczdT4rDhOa1dIEJYs7SWyREDtlVVsdd3t0E6KGw=', 'yhs8566@apfs.kr', NULL, '01026990978', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110016', '200001', 'seon@apfs.kr', '엄선래', 'oZZq3rDsW37tZA4TEl6cm0HrFX8q3GmHezKgHmFlmhE=', 'seon@apfs.kr', NULL, '01029076876', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110017', '200001', 'shjeong@apfs.kr', '정순현', '9Ak2Yb4Czney8zXvMMR2OnRzGjiN7iW96Yf9vScQZCc=', 'shjeong@apfs.kr', NULL, '01026912493', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110018', '200001', 'yschoi@apfs.kr', '최유식', 'NehHYxs+Icqp7wbCEuhUqcyD3LgIXzMlLdU1M+0GR5Y=', 'yschoi@apfs.kr', NULL, '01091510501', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('110019', '200001', 'chadeng@apfs.kr', '차경식', 'NzzXXiyymic4ErteCaQteKMVFtB4bLBgZS526KPxOho=', 'chadeng@apfs.kr', NULL, '01027827610', '20230814', 0, '1', 'N', '100000', now());

	-- 농진원
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('130001', '200003', 'yieun8044@koat.or.kr', '이지은', '1/xb18rXdya0ruKQlyDZhgZK9c54AAzynEKmnxptfxw=', 'yieun8044@koat.or.kr', NULL, '01025128044', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('130002', '200003', 'kimhw@koat.or.kr', '김혜원', 'Yos7PHab8/q4HCqdHUglX/MnlF5ljgueAplPsZAts/U=', 'kimhw@koat.or.kr', NULL, '01095046718', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('130003', '200003', 'june0401@koat.or.kr', '김성준', 'USxyMPNg/Bhs6DQQOsFD1mKVhoEvVvrgmqUta4yKCDI=', 'june0401@koat.or.kr', NULL, '01063576200', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('130004', '200003', 'ahra0922@koat.or.kr', '구아라', 'pQV1By4WH6xORuMPccGAeDY5YNwbK0qH8ps8yU0Jy2s=', 'ahra0922@koat.or.kr', NULL, '01099220997', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('130005', '200003', 'ectoplasm@koat.or.kr', '임동하', 'ycUbr69/Pt/V+WwNVlvOEQIicg8Cs4b6st9aeCRp5vw=', 'ectoplasm@koat.or.kr', NULL, '01029837077', '20230814', 0, '1', 'N', '100000', now());

	-- 농기평 
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('120001', '200002', 'renoham@ipet.re.kr', '함민석', 'KlcIiNgO5WcxWweAdWGKIhPnSxadg9cOOmvI42Rttr8=', 'renoham@ipet.re.kr', NULL, '01022466584', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('120002', '200002', 'kimys85@ipet.re.kr', '김윤석', '2lhLMVFmh1I1uEuBq4/WYi80gfXTbSSuniJIs4//IAw=', 'kimys85@ipet.re.kr', NULL, '01027728548', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('120003', '200002', 'jdaeun1@ipet.re.kr', '정다은', 'fcDgy0jHjFATu65tsgZNWZpjI7d80jZj3cqL1KZXehM=', 'jdaeun1@ipet.re.kr', NULL, '01055079332', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('120004', '200002', 'ipet@ipet.re.kr', '농기평', 'srwD7SzWAuaHs/TSj2V7DUOGjHuVhwTKClPoI9pSkHA=', 'ipet@ipet.re.kr', NULL, '0613389801', '20230814', 0, '1', 'N', '100000', now());

	-- 식품진흥원
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('140001', '200004', 'kfood365@foodpolis.kr', '문태곤', '+ERfTISl+puo9kF3Z2w22an1DjrNtI8RpU5j9e9aFqs=', 'kfood365@foodpolis.kr', NULL, '01031663492', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('140002', '200004', 'kjy@foodpolis.kr', '강전영', 'tFXADrupXH8LAz1P/ZLCYvuPvQx9gB7An/FypE9E+t4=', 'kjy@foodpolis.kr', NULL, '01038853669', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('140003', '200004', 'red1113@foodpolis.kr', '이창수', 'K8p+3f89fHCQDHMcTKCVa/JqJ71P6FqrqEAjOAynGSE=', 'red1113@foodpolis.kr', NULL, '01037891961', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('140004', '200004', 'ygg8014@foodpolis.kr', '유검근', 'YYxEhQp1/4sqgbz6E/UjbSvu8BtJ+MOP9QDpJoOwrus=', 'ygg8014@foodpolis.kr', NULL, '01067788014', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('140005', '200004', 'kigami1211@foodpolis.kr', '김지훈', 'lo0cliTFc1HL2E88MuaQ3IoMJdVacjU9mCH2kFrhTSI=', 'kigami1211@foodpolis.kr', NULL, '01024921151', '20230814', 0, '1', 'N', '100000', now());
	
	-- NH금융연구소
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('150001', '200005', 'kgh1851@naver.com', '김기환', 'cgRlC1OcCTwqhfg1wtShT/QUCNskxWd7j3hw1us6UxM=', 'kgh1851@naver.com', NULL, '01032435096', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('150002', '200005', 'skysea0428@naver.com', '이소연', 'weYR2sCMd2PxJGoYc/pzTloCcngjekVKYvCaqUE2xUA=', 'skysea0428@naver.com', NULL, '01024728240', '20230814', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('150003', '200005', 'ui90ui90@naver.com', '최원준', 'gia2B6ij4F47vGxRP+hhSrNEo8rRgL/ljBBltD0JijA=', 'ui90ui90@naver.com', NULL, '01026552860', '20230814', 0, '1', 'N', '100000', now());
		
	
/* 사용자정보 [투자자] */
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170001', '310001', 'yihahn@bnkvc.co.kr', '한영일', 'GWALaM5kmIvNMmzdFrJEp777pH3kmwAqe7ANs7w8a5I=', 'yihahn@bnkvc.co.kr', null, '01090817128', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170002', '310001', 'jmcho@bnkvc.co.kr', '조재만', 'ZEm1U4oEB7Ok5KORnD/wS0Elra3ylxZcWs69IKu/HFM=', 'jmcho@bnkvc.co.kr', null, '01092783682', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170003', '310002', 'chkim@ckdvc.co.kr', '김창희', 'V7oD+lByffaDtW2ZHIzBxtVE0JqeI9nxg5fJ/0enx/E=', 'chkim@ckdvc.co.kr', null, '01099558526', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170004', '310002', 'songms@ckdvc.co.kr', '송명석', '21hzL1TsMhGXblP1zlGCuGwmNvfbMRE0DMIU9THG0Ow=', 'songms@ckdvc.co.kr', null, '01059399778', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170005', '310002', 'jykim63@ckdvc.co.kr', '김주영', 'lqQh9qjvOpZ6mINRMvRSO2qSnUlrEnfcAs4thGX2tSU=', 'jykim63@ckdvc.co.kr', null, '01033103909', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170006', '310002', 'hwkim@ckdvc.co.kr', '김현우', 'plGrUrTsKpv0OUsMhW9tN9ci5LIUsRCm7qaLA9F70iI=', 'hwkim@ckdvc.co.kr', null, '01027352733', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170007', '310003', 'koolio1225@gmail.com', '고성재', 'uZtY7tnPiP3wSeWeZNUHz+ga1tX16gG7SxubvRM2m+8=', 'koolio1225@gmail.com', null, '01088441225', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170008', '310003', 'hass1969@gmail.com', '하승수', 'A5Z1dMES3HLZnsDEKNYD009ronvOH5s6IaDwh73GG2w=', 'hass1969@gmail.com', null, '01025755419', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170009', '310004', 'mj4804@nonghyup.com', '박미정', 'Zf6kTfHguGndVsOm77KctKwe8882HTB9f9jcsj6xBEQ=', 'mj4804@nonghyup.com', null, '01085266132', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170010', '310004', 'myshkin@nonghyup.com', '박민욱', 'ibGYsxklY6Pozt1HiVncjVhqDPrzAadPSMJ3L3BUS4Y=', 'myshkin@nonghyup.com', null, '01026200424', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170011', '310004', 'hongkr@naver.com', '홍광락', 'AscytnCoLWlfpFM9OMVYuCDrAXnFTpdXsInqF2qYl44=', 'hongkr@naver.com', null, '01062944860', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170012', '310004', 'jiyun_nam@nonghyup.com', '남지윤', 'DAHsFzv8bTYKZH9bRH8fqL75xcEGXVJG/Y+287CpsRo=', 'jiyun_nam@nonghyup.com', null, '01030411458', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170013', '310005', 'offic@nhvic.com', '순민영', 'irdzZ0D6KymCjE1gUl+gD2WHkIpsqCdjIdbvOYnJ3VA=', 'offic@nhvic.com', null, '01071680181', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170014', '310005', 'ar@nhvic.com', '강아름', 'QiUSgGXAVnJ4XZF1cndFRN0PTSIF8+pMsM1NvPURLjk=', 'ar@nhvic.com', null, '01031678834', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170015', '310005', 'junsukbyun91@nhvic.com', '변준석', 'msq6mdgBblSvJNOJ8BHHOSjIaFzsZQNuph+AVwNijT4=', 'junsukbyun91@nhvic.com', null, '01030164547', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170016', '310006', 'minj@gaiavp.com', '김민정', 'yYae9e/7Cw0B5OHiKozQnIckk5K1E5HkO5/58et8E44=', 'minj@gaiavp.com', null, '01064891411', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170017', '310006', 'jhyim@gaiavp.com', '임정훈', '5w6DYG6JKbx3jZKHUz2y0s6wYzry7L4cPPoX3mu7bwI=', 'jhyim@gaiavp.com', null, '01090634191', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170018', '310006', 'djdo@gaiavp.com', '김동준', 'cMF4dAhC1nwyFy2yAnZ+AJxYYZcu4wt04PCeklhFK94=', 'djdo@gaiavp.com', null, '01063682962', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170019', '310006', 'silner@gaiavp.com', '이혁준', '4DtTq36YE35bmVXWE99sAtQnDiTA+qty9tiSsvIu3oA=', 'silner@gaiavp.com', null, '01090417825', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170020', '310006', 'hykim@gaiavp.com', '김학윤', 'pz3Jo7eAC7TonAvtFJY8TPfPSKagxLBMkXB5S0rl7Lo=', 'hykim@gaiavp.com', null, '01028451985', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170021', '310006', 'campo@naver.com', '이동준', 'nk22UiD1LOMyRsi+X2XeZgFRtOMq9oo+bXgZrfjgC00=', 'campo@naver.com', null, '01062936067', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170022', '310006', 'shsong@gaiavp.com', '송성호', 'zIzwZfoAjrxGEIgHGgyHPDE46oYA2ZeW2cTdxzt0DGM=', 'shsong@gaiavp.com', null, '01064845609', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170023', '310007', 'kim0516@nauib.com', '김진경', '3k0wDUiJ0raGXUyC6CX+3GovBjVDf7JDnwQ2RknQkiQ=', 'kim0516@nauib.com', null, '01044080801', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170024', '310007', 'hklee@nauib.com', '이형국', 'VkS9/D1Bi0WZCdPpHDqMvhM5kPBuYtRsYCYzi8satdU=', 'hklee@nauib.com', null, '01052741904', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170025', '310007', 'pkd@nauib.com', '박경덕', 'dWsjEThGe6ti/A+p4Pmz/IqOphn2e9SFj1P1p5eOSGg=', 'pkd@nauib.com', null, '01071094171', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170026', '310007', 'jsmoon@nauib.com', '문지선', 'kLDyjkBjOmQsOTSz4miZULD3L5FB9MoKiawxHde0JRg=', 'jsmoon@nauib.com', null, '01076380056', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170027', '310008', 'dirthr214@nice.co.kr', '김주현', 'XHO5oxnqU//6w2t07xfNPggqvkdYS8KgpK9dqGeuK04=', 'dirthr214@nice.co.kr', null, '01021716877', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170028', '310008', 'ywkim@nice.co.kr', '김윤원', 'stL1BGir9g+8SlzINK5jRqdl/qMgPuMFMV6rk9g8iSM=', 'ywkim@nice.co.kr', null, '01063803092', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170029', '310008', 'thkim82@nice.co.kr', '김태홍', 'dlhLhumPbbuIpgmu7TxOYUhR1W9czbuIOifqmySomhA=', 'thkim82@nice.co.kr', null, '01092334524', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170030', '310008', 'eugene.shin@nice.co.kr', '신유진', 'TpQ1U9e4KRPaGwMbV/csT0DopR+NOATcJCLWtae+1IE=', 'eugene.shin@nice.co.kr', null, '01096160827', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170031', '310009', 'ethan@nextginvest.com', '윤형준', '8AkOUlyqurc3L7Xp3oEylnE3f3m8EUuc2/cQHhbgxsc=', 'ethan@nextginvest.com', null, '01050212708', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170032', '310009', 'sjlee@nextginvest.com', '이승재', '+PTvQ1O27dtV022QkNswTc9wG5qjLthQu4+VLEH8yvA=', 'sjlee@nextginvest.com', null, '01071054669', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170033', '310009', 'kjlee@nextginvest.com', '이귀진', 'GQ/W7nEkk3SGy/zq7eadyz2faQTZXSwDK9yANvVk78U=', 'kjlee@nextginvest.com', null, '01088629559', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170034', '310010', 'leeyj@nscap.kr', '이영주', '4MZIUegNXXC5qG20UCoYhtDF79pTkpKV2QiDZUX09AU=', 'leeyj@nscap.kr', null, '01040430620', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170035', '310011', 'cowptjd3@daesungpe.com', '김민성', 'brAUBtrg4BqPrmoIA8+4TQNmrz0hSW21ITYmGWm6w3I=', 'cowptjd3@daesungpe.com', null, '01075697509', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170036', '310011', 'wskim@daesungpe.com', '김완식', 'BB0I6U5y7Ti05sIP4hKWXZjlDNHzUhjCozG4k4esjPw=', 'wskim@daesungpe.com', null, '01074461650', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170037', '310011', 'kjt8504@daesungpe.com', '김정태', 'VpZyD3Z5Y2vDtEZSYdd3eh4SEcxa8Nlsx6IAcxgLyl8=', 'kjt8504@daesungpe.com', null, '01071068504', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170038', '310011', 'teresa035@daesungpe.com', '김지연', 'D2ozTKa75MBB/SejVm6sHZIiM4tN/ofy52wr6T2kIhQ=', 'teresa035@daesungpe.com', null, '01045985285', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170039', '310012', 'dh1013@egpartners.co.kr', '민다희', 'khVw6KJaMqHOAsqg2wa6SeNgx+U3big/ryEaEcTyXxE=', 'dh1013@egpartners.co.kr', null, '01066351367', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170040', '310012', 'syseo@egpartners.co.kr', '서상영', 'h3ec459VKR96ENfZYrTgN+fAnYZ9c12VXDB+uXPLQnY=', 'syseo@egpartners.co.kr', null, '01037676401', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170041', '310012', 'gentizen@egpartners.co.kr', '이은재', 'rDQrmADNqrrkxdSJKbG/B8cVt0Oz5DQug/HSqowhA9E=', 'gentizen@egpartners.co.kr', null, '01027571394', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170042', '310012', 'hyung@egpartners.co.kr', '이형', 'P6ea91HXu5KifM+7yyCMmqnu+a3gvO3alfVKMlXnx/o=', 'hyung@egpartners.co.kr', null, '01052283023', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170043', '310013', 'info@dhinvestment.co.kr', '정안나', 'LR9kYsbdctu2QTEn0Oh6J5qidfkCVTNWz+ALKQOHN50=', 'info@dhinvestment.co.kr', null, '01038780407', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170044', '310013', 'jwhong@dhinvestment.co.kr', '홍정우', 'IOwEOw7/PHKcPCUaHJLZlhBZucKoIGoGOYOXzE1WC0s=', 'jwhong@dhinvestment.co.kr', null, '01027962617', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170045', '310013', 'mhchoi@dhinvestment.co.kr', '최민혁', 'U931f9liXUSaNbLlZVpHzogpE+q1ZP1Re2n0CXd3Z/M=', 'mhchoi@dhinvestment.co.kr', null, '01052226244', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170046', '310014', 'junelee@lotte.net', '이준혁', 'Z1BkkcMZClUZZXkLXgBxGUJ6iwt3pyJ4dPLFnL0nSEI=', 'junelee@lotte.net', null, '01049366473', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170047', '310014', 'junsung.bae@lotte.net', '배준성', 'wlYqNwgSRWmX3j/9xUFSIUAL6KbQpoE7LmlWuKrU/ZE=', 'junsung.bae@lotte.net', null, '01037410725', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170048', '310015', 'ysjung@mgni.co.kr', '정유숙', '8r6cafI7YgVUwLO4fbcB+UPUGjdj3rOJF2jS9RfTkXQ=', 'ysjung@mgni.co.kr', null, '01030149090', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170049', '310015', 'elsad@mgni.co.kr', '이석배', 'UpZJzS9Ng5JBF1mOtRVNEmsiI3xWSyyCU36qLkDkeMk=', 'elsad@mgni.co.kr', null, '01033496391', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170050', '310015', 'ckpark@mgni.co.kr', '박창기', 'rLaPxsucLecoi1hraH/sVYEVe8bFh4xXGNftTbp2P10=', 'ckpark@mgni.co.kr', null, '01051331019', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170051', '310015', 'sangwoolee@mgni.co.kr', '이상우', 'PqdgylkRpNrRzUcwch2GrxXkhail4/AoMQ5Z7eBUKWs=', 'sangwoolee@mgni.co.kr', null, '01052288072', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170052', '310015', 'heesk@mgni.co.kr', '김희수', '/OJP9CTE8j0TuqRBCxutUPTAMQZAq/rb2Je90Xc298I=', 'heesk@mgni.co.kr', null, '01029138803', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170053', '310015', 'bomy@mgni.co.kr', '이봄', 'quFWfpVPXoQPmXzgUVyFk9Mq2dlfTi8+UBQd9qhp4do=', 'bomy@mgni.co.kr', null, '01024656121', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170054', '310015', 'chun@mgni.co.kr', '전양우', 'bLUySOTXnCxIc8NQWmlCqCyxxdZnMrLmZGb9xURjvS4=', 'chun@mgni.co.kr', null, '01037902021', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170055', '310016', 'namki@donga.com', '김남기', '/wOrW3z8FcujeSjxVEOek8OHbiv96PpgmwrLOZQUJLE=', 'namki@donga.com', null, '01086041540', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170056', '310017', 'titi@metainvestment.co.kr', '김태영', 'Ywb2zPsA8O3i+MqiTlQzMjBz52R32SOA9zbNDgSl5X4=', 'titi@metainvestment.co.kr', null, '01084887432', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170057', '310018', 'behigh@behigh.co.kr', '조대영', '5IwBe47ugmZv3UhFw6o3VHf/VZBp40CaKl/QXzrkfdM=', 'behigh@behigh.co.kr', null, '01082648802', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170058', '310018', 'hwjeong@behigh.co.kr', '정희운', 'Zmi3qTvSmUzszn06w5fpHOAJtlHJqFuQ/p3aDYWebGA=', 'hwjeong@behigh.co.kr', null, '01091012028', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170059', '310018', 'panseok.kim@gmail.com', '김판석', 'Ts0vA0FwacqZfZ6hwn+Gb6YjJ9e1Ndhg7qJKCMwANE4=', 'panseok.kim@gmail.com', null, '01065841479', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170060', '310019', 'shwon@bilanx.co.kr', '원승환', 'eEplj7FfBZUE1SZ9ayPRfj8PdbNE/4CzZWpH0zEd2Cc=', 'shwon@bilanx.co.kr', null, '01086031391', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170061', '310019', 'ch.park@bilanx.co.kr', '박창환', 'Ff26nhG+F7GwPNRYdGbEJ7JVwNVChnxiFLwxKpXcehU=', 'ch.park@bilanx.co.kr', null, '01085152602', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170062', '310019', 'kimjyong1215@bilanx.co.kr', '김진용', 'ycuYLs8jerRu3ICJnkRDTeaQ21N5HUaf2pGm5Zk7xfc=', 'kimjyong1215@bilanx.co.kr', null, '01065895178', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170063', '310019', 'jwj48260@bilanx.co.kr', '정우진', 'MRxH1BBYd8+l2bczEZoRmfsgii4UQFsx5XzoqylBq2s=', 'jwj48260@bilanx.co.kr', null, '01086381388', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170064', '310019', 'ghb7425@gmail.com', '길현범', 'vvX3t+UHuyiKzzPZQGOeZKmbY8hRym50097ztpPeG/M=', 'ghb7425@gmail.com', null, '01099887425', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170065', '310020', 'ticket@sclinvestment.com', '표지희', 'np4sgBAqXbH4YNlcyLqO6U7DrSajSuw1rTuKOCbFBT8=', 'ticket@sclinvestment.com', null, '01099212864', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170066', '310021', 'soohyoungkim@signite.com', '김수형', 'QzvIZdwSKMso20s0Q04nbNKho58S2GBlCY/b/KD6p+o=', 'soohyoungkim@signite.com', null, '01087487355', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170067', '310021', 'ashley.sb.lee@gmail.com', '이새봄', 'neglFYZPMVDbKZzgbn87iB17wFs9qU02KPbPM967X/I=', 'ashley.sb.lee@gmail.com', null, '01051712011', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170068', '310022', 'positive_kt@cntt.co.kr', '박규태', '7kHCV2xe5hwMkhlWNo6KoEPhMnfXNLV+pDjB0u/sfAo=', 'positive_kt@cntt.co.kr', null, '01062181875', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170069', '310022', 'jenny@cntt.co.kr', '오지은', '31YanWp9vNDlwyKhpZ2ZsydN+nf6A0UC/nFAqQNQzkI=', 'jenny@cntt.co.kr', null, '01065300713', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170070', '310022', 'myjeon@cntt.co.kr', '전민영', 'Fs/Q7EjdgfntTE98BjJCXlWMXqnkGHWBxNn8yS+mf94=', 'myjeon@cntt.co.kr', null, '01033055039', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170071', '310023', 'cjinvestment@cj.net', '김민경', 'mVEkAKDI6yrh+dALi+9wiYZ2hvnWYkcBPrWCmtE39Dw=', 'cjinvestment@cj.net', null, '01098961643', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170072', '310023', 'junsik.kim1@cj.net', '김준식', 'OPSQ4mxATAQDcPapa1mnNYkJP27E3ctmkSeBqEB7Oic=', 'junsik.kim1@cj.net', null, '01024620792', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170073', '310023', 'okgil.kim@cj.net', '김옥길', 'L7/r9npAOkIX72K6d9zWegeoqSrkTY/TRm+3ny2jgoM=', 'okgil.kim@cj.net', null, '01027088527', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170074', '310023', 'hyosebkim@cj.net', '김효섭', 'BkdwXrT+9FcEYpPNUOtDxhqeXIESDMl3uwAYe6Jgkq8=', 'hyosebkim@cj.net', null, '01092739346', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170075', '310024', 'hr.lee@id-vc.com', '이혜란', 'YO/eKjqtkiT4RtVaHa1adBHhJcKuZFs8P3aCqOFGFLE=', 'hr.lee@id-vc.com', null, '01020675463', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170076', '310024', 'cm.heo@id-vc.com', '허창민', 'M3SB83SYAGtDz8uB3YMmqOtUBsAwGDrDFXhPyMYpkWc=', 'cm.heo@id-vc.com', null, '01086898637', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170077', '310024', 'info@id-vc.com', '박이슬', 'agjarhu1tks9fQVK6OKBF1pk+6NYEFQXNKk43+RqN+A=', 'info@id-vc.com', null, '01089274418', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170078', '310024', 'kdojoon@hanmail.net', '김은섭', 'REUbf8L84n8i+78OmGfycw/XEBckVyTR4V/DjrZgYGA=', 'kdojoon@hanmail.net', null, '01062353184', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170079', '310025', 'hjs5532@honest.ventures', '하종삼', 'zYLcntz8c+HKmD2POLiWwJzBbGso1MagqQ7pS3HcgKs=', 'hjs5532@honest.ventures', null, '01074125532', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170080', '310025', 'khoh@honest.ventures', '오규희', 'VdjW7x1ov53ZX5VtPY+AwbYG/hugZTRX+hb1NQz6XYQ=', 'khoh@honest.ventures', null, '01093515920', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170081', '310025', 'hyeran@honest.ventures', '서혜란', 'uhoNsbmbRTBTHgFhL00l0dZq711Uf1/FfiESLWdvfEs=', 'hyeran@honest.ventures', null, '01029508310', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170082', '310025', 'simpo6@honest.ventures', '김경태', 'JD3vO2VsNo2L7WEl8VD3RR+voy6PKu5FgzbOotUbPcU=', 'simpo6@honest.ventures', null, '01067128739', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170083', '310025', 'shleevc@naver.com', '이승흠', '0GCdmyAlCWRVw4P6NaMulFpZjsuDW6lGzo4rUYDLYuk=', 'shleevc@naver.com', null, '01047063355', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170084', '310026', 'yoon3403@nvcp.kr', '윤우철', 'htw2XoBYr5ElcVw3u4Z0v8XcEsfQvNvb8YtcbjoBcuk=', 'yoon3403@nvcp.kr', null, '01083263704', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170085', '310026', 'sdlee@nvcp.kr', '이상동', 'W32X/O7ziUl3QVUACX3ZzAV1pgIsEn/Jc/PucF9mu2I=', 'sdlee@nvcp.kr', null, '01055386759', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170086', '310026', 'kyungchan.kim@nvcp.kr', '김경찬', 'oprTyp7VsWzFSIWoU6Kg2hQrH6j37Dm3WYSW/5hpHQQ=', 'kyungchan.kim@nvcp.kr', null, '01049520957', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170087', '310027', 'dorothy1121.yun@gmail.com', '윤지선', 'pjnIoNaYNogiQ9RgyFHWymNAx/C+tPrMSG7YYXw08qg=', 'dorothy1121.yun@gmail.com', null, '01020249515', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170088', '310027', 'chosh1001@naver.com', '조상현', 'VS8LryeNC+iXZ7H3T/V7BhqDAYjJrFndJqrr782JcYw=', 'chosh1001@naver.com', null, '01088629578', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170089', '310027', 'iwkkh@naver.com', '김혁', 'Ke1uejafQT/SmHO+qMYUEaglqX+8LXx0JczJ6Ka84ws=', 'iwkkh@naver.com', null, '01053579805', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170090', '310027', 'sekimz@naver.com', '김세현', 'Z613B+flVRJLqurbDSngTOIcf63MqvsBVq654wY3UjI=', 'sekimz@naver.com', null, '01067051819', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170091', '310027', 'korigin00@gmail.com', '김기원', 'rYmzfD/6hLgAVGn+xkbDshlrUvmdMiF9NeMDW57Gw9g=', 'korigin00@gmail.com', null, '01036377731', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170092', '310028', 'hskim7971@wonik.com', '김호성', 'vjWgvx5/B5utbpPzZp2zo1W46XUpR3C1ynTRRGqRS0E=', 'hskim7971@wonik.com', null, '01052070223', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170093', '310029', 'isuvc@isu.co.kr', '김경문', 'JBW3coIuasjpXPPoA7QCRIo7h1pdcOjuNs9rUBcQ9Kk=', 'isuvc@isu.co.kr', null, '01093163750', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170094', '310029', 'hkc@isu.co.kr', '정홍규', 'oDRlYZt17QFSs1wJFEwzf8cRXFXZ32DXGt2diAeGpdk=', 'hkc@isu.co.kr', null, '01064785710', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170095', '310029', 'stabus19@isu.co.kr', '이진오', 'msxHzqObsATVogSE75pbo38Be5G/6v1wZqhVqdTkGHQ=', 'stabus19@isu.co.kr', null, '01071475403', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170096', '310029', 'jh.choi@isu.co.kr', '최종혁', 'sgOcFRv0eM3WzHfMqoeO74+dPau/QT+hijvYjoW6STE=', 'jh.choi@isu.co.kr', null, '01027375426', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170097', '310030', 'hjmin@ecurxvp.com', '민혜진', 'Z0TXP3RutJBtk27qrU/F5UyRm05AiyJf/cwnrCYR1WY=', 'hjmin@ecurxvp.com', null, '01087623528', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170098', '310030', 'lairwin@ecruxvp.com', '김종호', 'AI+OEwunf0MvPdnsVaZw9k07E/mrzPr10UVtJsqa7MU=', 'lairwin@ecruxvp.com', null, '01033897720', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170099', '310031', 'hblee@enlightvc.com', '이효빈', 'tX83j6BESaxsuvoRCSHqxpPhjZEUlN4f5g4jOAgaKLs=', 'hblee@enlightvc.com', null, '01024102988', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170100', '310031', 'sjlee@enlightvc.com', '이승재', 'Y2NEjZ2trS+PxFutmE2bEoPgz/S+lAUKwgiDs+trfxI=', 'sjlee@enlightvc.com', null, '01026538550', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170101', '310031', 'jyh7494@enlightvc.com', '조영호', 'm8R9m1tllhlNcPbaV2B7IpRosSgP89+2ETh50xfHdkQ=', 'jyh7494@enlightvc.com', null, '01033327494', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170102', '310032', 'livesmile@impactpartners.kr', '정형민', 'h4ufLFNhBxRiOcfvc+WGP8ZqdIuk+R7hcYehzlMXMMg=', 'livesmile@impactpartners.kr', null, '01040281003', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170103', '310032', 'hswman@naver.com', '한순원', '4+x8x/SdvQx7f5BsaakB/51UB4EY77nWhtuF+MaPGM4=', 'hswman@naver.com', null, '01087627736', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170104', '310032', 'impact@impactpartners.kr', '진기준', 'X1yGjbP9ZLpaPEZNSFeQSi9jPwIxfaXKMPY52QrtuSg=', 'impact@impactpartners.kr', null, '01051144868', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170105', '310033', 'sh.lee@jcurveinvest.com', '이시현', 'NpODWolTx0hwoVrKwKLvysG/JD6B1p4KA4Scn/xOCyM=', 'sh.lee@jcurveinvest.com', null, '01077285286', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170106', '310033', 'mikekim@jcurveinvest.com', '김형준', 'K1p0hLex/Wv+VaZA3DWYotmGQDuXC8nd70aRjoY4SAY=', 'mikekim@jcurveinvest.com', null, '01033518846', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170107', '310034', 'jwseo@gtoinvest.com', '서준원', 'luDPghzPgWvCSTLsOlt4Y6d/SFhWOrCkbCpJ7TFqNyA=', 'jwseo@gtoinvest.com', null, '01063182420', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170108', '310034', 'henry@gtoinvest.com', '송해민', 'P9pj+7VqF+xILv5i75aRRcvxkOt5UnJaoYytBJmWxwI=', 'henry@gtoinvest.com', null, '01096591777', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170109', '310035', 'park1737@capefn.com', '박미현', 'parnDHDdJv4cdph848pHjSzt7KDNrwQHSR5xPHRy1Y4=', 'park1737@capefn.com', null, '01024171737', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170110', '310035', 'jjmoon@capefn.com', '문준혁', 'DDMtvxcNacI9cAbErj+TBqWg7wa9iLixzIsFnuG5Sk0=', 'jjmoon@capefn.com', null, '01075927192', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170111', '310035', 'hjy6415@capefn.com', '하재영', '0tL2zd6AZLo2DnbPK5QzRqmCZpS/5gaajYk7g0NNveU=', 'hjy6415@capefn.com', null, '01071136415', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170112', '310036', 'rooji.lee@pathfinderh.com', '이루지', 'kbh+cybzMYy7fUZZYHr/KlaFc7CJ9xhbIncBjDW5I8o=', 'rooji.lee@pathfinderh.com', null, '01032660264', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170113', '310036', 'jinho.park@pathfinderh.com', '박진호', 'IBmmHHxJ3tromefOARBA2cRBPlMzGf752z+FL1Y+OGk=', 'jinho.park@pathfinderh.com', null, '01094959597', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170114', '310036', 'jongoh.lee@pathfinderh.com', '이종오', 'roUdIM94qC+yOr3W6ujJ22PL/ewGn4Bjx4GRjnvpNz0=', 'jongoh.lee@pathfinderh.com', null, '01040460868', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170115', '310037', 'plvc@prologuevc.co.kr', '강지애', 'fZWjgzNzCQtWQ1YFL/23Nq6dnZy1GZA+kNJoGP1yKpQ=', 'plvc@prologuevc.co.kr', null, '01088105666', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170116', '310037', 'sungwon.suh09@gmail.com', '서성원', '7IERVVqZnqxKyfGfFhSIW9N1IfIoGTwu966/fd36SS4=', 'sungwon.suh09@gmail.com', null, '01048229616', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170117', '310037', 'vckjh@prologuevc.co.kr', '김종헌', 'VcihGKGd5vmYn929+IAhxdR3PzpRNbWqd7jSswYTQBo=', 'vckjh@prologuevc.co.kr', null, '01075902447', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170118', '310038', 'kwak36@hi-investment.com', '곽진걸', 'O+4uY7GFwqUEtZvWQ2pWdldoBEpTBBKF42gqYgav8ew=', 'kwak36@hi-investment.com', null, '01050355060', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170119', '310038', 'jyyang@hi-investment.com', '양준영', '910TyWxg1Bxx05xqb0p5lITeaJw6quPDue8HyCdivbw=', 'jyyang@hi-investment.com', null, '01066487743', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170120', '310038', 'whpark@hi-investment.com', '박원하', 'y598clQQs0sOF59J2+1Rq36QXc6WBb1M542/y+R9x9I=', 'whpark@hi-investment.com', null, '01042127389', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170121', '310038', 'jhkwon107@hi-investment.com', '권준희', 'wEMkuGpCrZeo7yYx8P4AptExODMKXxKXSxQaZ8E0Yzc=', 'jhkwon107@hi-investment.com', null, '01090475966', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170122', '310038', 'nku12@hi-investment.com', '노경욱', 'PrwmSdJsKMKZG/laMW1j3KLLyTJqmL435PeAUAu/TvY=', 'nku12@hi-investment.com', null, '01088923672', '20230821', 0, '1', 'N', '100000', now());
	INSERT INTO tb_user (user_no, bzenty_no, user_id, user_nm, pswd, eml_addr, telno, mbl_telno, join_ymd, pswd_err_cnt, use_stts_cd, test_use_yn, rgtr_no, reg_ymd) VALUES('170123', '310039', 'dijychoi@hvic.co.kr', '최주열', 'CzbP04/56wyihiBLmMK60A9Cm716taziMNjZ6hC0PjQ=', 'dijychoi@hvic.co.kr', null, '01090192319', '20230821', 0, '1', 'N', '100000', now());

	

	
	
	

	
/* 
 * 사용자 권한 등록
 * 		- ROLE_ADM_SYS : 시스템관리자
 * 		- ROLE_ADM_MNG : 운영관리자
 * 		- ROLE_USR_EIS : 유관기관
 * 		- ROLE_USR_EIV : 투자자 
 * 		- ROLE_USR_EBZ : 경영체
*/
	-- 시스템관리자
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd)	VALUES ('100000', 'ROLE_ADM_SYS', '100000', now());
	INSERT INTO SYS_ROLE_USER (role_id, user_no, rgtr_no, reg_ymd) VALUES ('ROLE_USR_SYS', '100000', '100000',now());
	
	-- 농금원 담당자 (테스트)
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd)	VALUES ('100001', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd)	VALUES ('100001', 'ROLE_USR_EIS', '100000', now());

	
	-- 농금원 (관리자)
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110001', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110002', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110003', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110004', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110005', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110006', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110007', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110008', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110009', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110010', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110011', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110012', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110013', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110014', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110015', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110016', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110017', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110018', 'ROLE_ADM_MNG', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110019', 'ROLE_ADM_MNG', '100000', now());	

	-- 농금원 (사용자)
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110001', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110002', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110003', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110004', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110005', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110006', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110007', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110008', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110009', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110010', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110011', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110012', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110013', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110014', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110015', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110016', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110017', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110018', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('110019', 'ROLE_USR_EIS', '100000', now());
		
	-- 농진원 (사용자)
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('130001', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('130002', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('130003', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('130004', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('130005', 'ROLE_USR_EIS', '100000', now());
	
	-- 농기평 (사용자)
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('120001', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('120002', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('120003', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('120004', 'ROLE_USR_EIS', '100000', now());
	
	-- 식품진흥원 (사용자)
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('140001', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('140002', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('140003', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('140004', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('140005', 'ROLE_USR_EIS', '100000', now());

	-- NH금융연구소 (사용자)
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('150001', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('150002', 'ROLE_USR_EIS', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('150003', 'ROLE_USR_EIS', '100000', now());

	
/* [투자자] */
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170001', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170002', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170003', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170004', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170005', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170006', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170007', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170008', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170009', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170010', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170011', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170012', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170013', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170014', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170015', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170016', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170017', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170018', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170019', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170020', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170021', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170022', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170023', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170024', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170025', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170026', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170027', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170028', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170029', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170030', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170031', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170032', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170033', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170034', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170035', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170036', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170037', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170038', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170039', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170040', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170041', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170042', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170043', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170044', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170045', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170046', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170047', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170048', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170049', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170050', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170051', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170052', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170053', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170054', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170055', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170056', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170057', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170058', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170059', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170060', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170061', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170062', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170063', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170064', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170065', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170066', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170067', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170068', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170069', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170070', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170071', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170072', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170073', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170074', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170075', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170076', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170077', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170078', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170079', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170080', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170081', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170082', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170083', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170084', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170085', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170086', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170087', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170088', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170089', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170090', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170091', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170092', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170093', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170094', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170095', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170096', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170097', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170098', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170099', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170100', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170101', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170102', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170103', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170104', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170105', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170106', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170107', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170108', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170109', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170110', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170111', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170112', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170113', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170114', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170115', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170116', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170117', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170118', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170119', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170120', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170121', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170122', 'ROLE_USR_EIV', '100000', now());
	INSERT INTO sys_role_user(user_no, role_id, rgtr_no, reg_ymd) VALUES ('170123', 'ROLE_USR_EIV', '100000', now());
	
	