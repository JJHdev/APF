--DELETE FROM TB_GRP_AUTH;
--DELETE FROM TB_GRP_USR;
--DELETE FROM TB_GRP_MENU;

--1. 마이페이지 메뉴 생성
INSERT INTO tb_grp_menu (menu_id,menu_nm,trgt_url,sys_authrt_info,rprs_yn,rgtr_no,reg_ymd,mdfr_no,mdfcn_ymd) 
VALUES
  ('MU_USR_MYP0101','IR작성하기','/usr/mypage/ent/openEntIr.do','ROLE_USR_EBZ','N','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0201','매칭설정','/usr/mypage/matching/openMatching.do','ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV,ROLE_USR_USR','N','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0301','신청내역','/usr/mypage/support/openSprt.do','ROLE_USR_EBZ,ROLE_USR_EIV,ROLE_USR_USR','N','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0401','IR검토의견등록','/usr/mypage/opnn/openOpnnIr.do','ROLE_USR_EIV','N','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0501','사업공고등록','/usr/mypage/pbanc/openPbanc.do','ROLE_USR_EIS','N','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0701','기본정보','/usr/mypage/mypage/openInfo.do','ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV,ROLE_USR_USR','N','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0801','그룹관리','/usr/mypage/group/openGroup.do','ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV','Y','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0601','문의내역','/usr/mypage/bbs/openBbs.do','ROLE_USR_EBZ,ROLE_USR_EIS,ROLE_USR_EIV,ROLE_USR_USR','N','100000',NOW(),NULL,NULL),
  ('MU_USR_MYP0001','기업정보',NULL,'ROLE_USR_EBZ','Y','100000',NOW(),NULL,NULL);
      
  

