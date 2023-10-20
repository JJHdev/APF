-- 2023.05.09 사용자 등록 (비밀번호: 1234)
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
;

-- 2023.06.14 사용자 추가 등록 (투자자/경영체/유관기관) (비밀번호: 1234)
INSERT INTO TB_USER(RGTR_NO, REG_YMD, JOIN_YMD, PSWD, USER_NO, MBL_TELNO, USER_ID, EML_ADDR, USER_NM, BZENTY_NO) VALUES 
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'MCo3yuq0K8MfTUCJwIYhwbnier9EdSmVLsAqomd+uks=', NEXTVAL('SEQ_USER'), '01027972071', 'sample10@sample.com', 'sample10@sample.com', '박무자', NULL),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'Y6D6Sd8FIKyKtoWuG3c2z7+DH5HVFvZr082B1lukpwQ=', NEXTVAL('SEQ_USER'), '01019165717', 'sample17@sample.com', 'sample17@sample.com', '피영길', NULL),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '7MbJOQ+uPi7LZDDAmLU+T5lU0AFhBWwuzedyF7p9Fnk=', NEXTVAL('SEQ_USER'), '01029730540', 'sample27@sample.com', 'sample27@sample.com', '오덕진', NULL),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'qZKEyLupSuI9F+R6rkBSGPU18oJPKNruCudxVHQP76I=', NEXTVAL('SEQ_USER'), '01058983111', 'sample01@sample.com', 'sample01@sample.com', '이석희', '500106'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '91ACkAZ0tuPL5cnIe+lWNxW76/i0olM/eViD4lNgcf4=', NEXTVAL('SEQ_USER'), '01034206600', 'sample02@sample.com', 'sample02@sample.com', '김승민', '500106'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'ID6nBXhhR2T1r31QQRGfI/JUisX0LYfENuJzGKAIDXU=', NEXTVAL('SEQ_USER'), '01034082300', 'sample03@sample.com', 'sample03@sample.com', '이내훈', '500106'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'K7Jgl4wR1TLXkysQbvmVqOKi+iVh3i2w29/FgOG7w5o=', NEXTVAL('SEQ_USER'), '01025464141', 'sample04@sample.com', 'sample04@sample.com', '김계현', '500106'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '4tRZJyP4J1EOS5QAstEeh1fwaqjQ7RRFE/xm+E9d8R0=', NEXTVAL('SEQ_USER'), '01025644389', 'sample05@sample.com', 'sample05@sample.com', '양승윤', '500107'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '0W1VzGtTXwHbuiH1yLUYdMYOhMyOYSNHlg+LVyBIGAk=', NEXTVAL('SEQ_USER'), '01027350141', 'sample06@sample.com', 'sample06@sample.com', '전현섭', '500108'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'wzjVawZyyV/ZsZXV+LXtsIPmrLTCkKaw1fHsFtIjdaI=', NEXTVAL('SEQ_USER'), '01034885552', 'sample07@sample.com', 'sample07@sample.com', '김종일', '500109'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'j+8gq2hkPQCw93qabDZnoCspyVLy5AKKZSZFyImzVwk=', NEXTVAL('SEQ_USER'), '01027378822', 'sample08@sample.com', 'sample08@sample.com', '송문현', '500110'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'HW7wXSgV05wH6eQTWY5+JP9Kvlg/tsiPt2WRqQi/DTg=', NEXTVAL('SEQ_USER'), '01024981580', 'sample09@sample.com', 'sample09@sample.com', '김동은', '500111'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'HbYov1HoBQIi/LJisrDJA2POvg6+uaXVveax8sxcr9U=', NEXTVAL('SEQ_USER'), '01021091550', 'sample11@sample.com', 'sample11@sample.com', '윤봉수', '500113'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'jcmajoJ0SBM0T3ZBYPDBwfvDuPHsPCix8OS62EQzQz0=', NEXTVAL('SEQ_USER'), '01037207000', 'sample12@sample.com', 'sample12@sample.com', '서성훈', '500114'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'RzIKDc0U3wDlPxbhKo9PrCZPvNaLrVYSmj+JDe3QtuQ=', NEXTVAL('SEQ_USER'), '01047523151', 'sample13@sample.com', 'sample13@sample.com', '양상현', '500116'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'lL00YgGIRngpPtmxNUra0Bs6GlXOIaHHv5Tw7lIWLMw=', NEXTVAL('SEQ_USER'), '01043307900', 'sample14@sample.com', 'sample14@sample.com', '문상수', '500116'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'Cq3TT6wL7dOkkRnn8rUv41xyyXQZyWLi7yw6uUcru9g=', NEXTVAL('SEQ_USER'), '01027209786', 'sample15@sample.com', 'sample15@sample.com', '권태식', '500116'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'MfS33uMa18C+IG/NKFl1d7K0I5f55aea2R9FwvlM+JI=', NEXTVAL('SEQ_USER'), '01022663577', 'sample16@sample.com', 'sample16@sample.com', '임종서', '500116'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'oQrlwxelg1VFJKFWhsA4HPXLI3wNUQQagfMqz/zW9As=', NEXTVAL('SEQ_USER'), '01012329872', 'sample18@sample.com', 'sample18@sample.com', '조두호', '500121'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'llQyL0F9BiXDuFQ0RVyy4PElZTMv9yCjOQfXI/btYHU=', NEXTVAL('SEQ_USER'), '01034817100', 'sample19@sample.com', 'sample19@sample.com', '이성환', '500123'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'tdM4vJZ9tPLhBZz8IkaSd+3DyCRU76A6D0kLseJ+BHM=', NEXTVAL('SEQ_USER'), '01027531271', 'sample20@sample.com', 'sample20@sample.com', '박영안', '500135'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'oz887LG1vDp/VtjF1FVlAOuWafqb6C+l+82p13v8CtY=', NEXTVAL('SEQ_USER'), '01012720972', 'sample21@sample.com', 'sample21@sample.com', '전병우', '500140'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'TWAAdMhTZOwaMrH63rBcR7bk/ITTmUZEFAcuQeVJ6P4=', NEXTVAL('SEQ_USER'), '01029165350', 'sample22@sample.com', 'sample22@sample.com', '유정식', '500146'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '0mrpr+1Yqvb/KHxr16oEnxKr6coi1tNLTm83cf1244c=', NEXTVAL('SEQ_USER'), '01027358500', 'sample23@sample.com', 'sample23@sample.com', '백순석', '500148'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '/c6m1Qyz5uQWNYz+MPM9lEDmn1XNICaNhJa//MDiKn8=', NEXTVAL('SEQ_USER'), '01034061400', 'sample24@sample.com', 'sample24@sample.com', '최승재', '500149'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'P5EVAr4QeoGyMXWgCbnjeTzXcr/JP6JOGoHBhS1rES0=', NEXTVAL('SEQ_USER'), '01028485104', 'sample25@sample.com', 'sample25@sample.com', '강병윤', '500149'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'DPnLLa/tyzMbOiegXpvWp1YxvN3hdYby6h0usVirdVE=', NEXTVAL('SEQ_USER'), '01014980001', 'sample26@sample.com', 'sample26@sample.com', '이찬호', '500149'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'KkjI8hB6mPp4C6zXjmrLKp5fSrnHSOnZv6H34hSf3oU=', NEXTVAL('SEQ_USER'), '01025791155', 'sample28@sample.com', 'sample28@sample.com', '이종성', '500150'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'Dr9zxJizlBmcCyStjnZJnBDkT/4A4ysuW29xNCdMI8c=', NEXTVAL('SEQ_USER'), '01015859901', 'sample29@sample.com', 'sample29@sample.com', '강준기', '500150'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'xC43AdssOKCEL5uc5rfVdIUvbaoxZLsBEKybSXHUE88=', NEXTVAL('SEQ_USER'), '01017322091', 'sample30@sample.com', 'sample30@sample.com', '변윤재', '500151'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '4NQ/GllQIfs+q6OR9rDq3Q+LklwInA3dSKBfLCAKgAM=', NEXTVAL('SEQ_USER'), '01027300018', 'sample31@sample.com', 'sample31@sample.com', '고종식', '500151'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), '/kGYtjQtRJJYbD+mCwV0vixu4faI0GbScep2lh5MOCk=', NEXTVAL('SEQ_USER'), '01019489300', 'sample32@sample.com', 'sample32@sample.com', '이근우', '500152'),
('system', NOW(), TO_CHAR(NOW(),'YYYYMMDD'), 'hvFZv6t9iE5aDC0yh1orSVODuYRUfSLnC8uw64bpcFw=', NEXTVAL('SEQ_USER'), '01027807559', 'sample33@sample.com', 'sample33@sample.com', '홍병수', '500152')
;

-- 2023.06.14 업체 그룹코드 맵핑
UPDATE tb_ent SET group_cd = 'EBZ0500106' WHERE bzenty_no = '500106';
UPDATE tb_ent SET group_cd = 'EBZ0500107' WHERE bzenty_no = '500107';
UPDATE tb_ent SET group_cd = 'EBZ0500108' WHERE bzenty_no = '500108';
UPDATE tb_ent SET group_cd = 'EBZ0500109' WHERE bzenty_no = '500109';
UPDATE tb_ent SET group_cd = 'EBZ0500110' WHERE bzenty_no = '500110';
UPDATE tb_ent SET group_cd = 'EBZ0500111' WHERE bzenty_no = '500111';
UPDATE tb_ent SET group_cd = 'EBZ0500113' WHERE bzenty_no = '500113';
UPDATE tb_ent SET group_cd = 'EBZ0500114' WHERE bzenty_no = '500114';
UPDATE tb_ent SET group_cd = 'EIV0500116' WHERE bzenty_no = '500116';
UPDATE tb_ent SET group_cd = 'EIV0500121' WHERE bzenty_no = '500121';
UPDATE tb_ent SET group_cd = 'EIV0500123' WHERE bzenty_no = '500123';
UPDATE tb_ent SET group_cd = 'EIV0500135' WHERE bzenty_no = '500135';
UPDATE tb_ent SET group_cd = 'EIV0500140' WHERE bzenty_no = '500140';
UPDATE tb_ent SET group_cd = 'EIV0500146' WHERE bzenty_no = '500146';
UPDATE tb_ent SET group_cd = 'EIV0500148' WHERE bzenty_no = '500148';
UPDATE tb_ent SET group_cd = 'EIS0500149' WHERE bzenty_no = '500149';
UPDATE tb_ent SET group_cd = 'EIS0500150' WHERE bzenty_no = '500150';
UPDATE tb_ent SET group_cd = 'EIS0500151' WHERE bzenty_no = '500151';
UPDATE tb_ent SET group_cd = 'EIS0500152' WHERE bzenty_no = '500152';

-- 2023.06.14 대표계정 맵핑
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'invt@test.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'comp@test.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'inst@test.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample05@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample06@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample07@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample08@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample09@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample11@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample12@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample18@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample19@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample20@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample21@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample22@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample23@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample28@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample30@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'Y','100000',NOW() FROM tb_user WHERE user_id = 'sample32@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample01@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample02@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample03@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample04@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample13@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample14@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample15@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample16@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample24@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample25@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample26@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample29@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample31@sample.com';
INSERT INTO tb_grp_usr (user_no,rprs_yn,rgtr_no,reg_ymd) SELECT user_no,'N','100000',NOW() FROM tb_user WHERE user_id = 'sample33@sample.com';

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

