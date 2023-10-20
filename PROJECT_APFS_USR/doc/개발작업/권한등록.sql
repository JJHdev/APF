-- 2023.04.19 권한, 메뉴, 프로그램 등록
INSERT INTO SYS_ROLE (RGTR_NO, REG_YMD, ROLE_ID, UP_ROLE_ID, ROLE_NM) VALUES 
('system', NOW(), 'ROLE_ADM_SYS', 'NONE', '시스템관리자'),
('system', NOW(), 'ROLE_ADM_MNG', 'NONE', '운영관리자'),
('system', NOW(), 'ROLE_USR_USR', 'NONE', '개인회원'),
('system', NOW(), 'ROLE_USR_EIV', 'NONE', '투자자회원'),
('system', NOW(), 'ROLE_USR_EBZ', 'NONE', '경영체회원'),
('system', NOW(), 'ROLE_USR_EIS', 'NONE', '유관기관회원'),
('system', NOW(), 'ROLE_USR_RESTRICTED', 'NONE', '비회원')
;

INSERT INTO SYS_MENU (POPUP_YN, USE_YN, RGTR_NO, REG_YMD, MENU_ORDR, MENU_LEVEL, SYS_SE_CD, UP_MENU_ID, MENU_ID, MENU_NM, TRGT_URL) VALUES 
('N', 'Y', 'system', NOW(), 1, 1, 'USR', 'NONE'      , 'MU_USR_INV'    , '투자서비스'         , '/usr/invest/fund/listFund.do'),
('N', 'Y', 'system', NOW(), 2, 1, 'USR', 'NONE'      , 'MU_USR_SUP'    , '지원서비스'         , '/usr/support/pbanc/listPbanc.do'),
('N', 'Y', 'system', NOW(), 3, 1, 'USR', 'NONE'      , 'MU_USR_INF'    , '정보서비스'         , '/usr/inform/bbs/openInvestGuide.do'),
('N', 'Y', 'system', NOW(), 4, 1, 'USR', 'NONE'      , 'MU_USR_CUS'    , '고객센터'           , '/usr/inform/bbs/listNotice.do'),
('N', 'Y', 'system', NOW(), 5, 1, 'USR', 'NONE'      , 'MU_USR_INT'    , '플랫폼소개'         , '/usr/inform/intro/openPlatform.do'),
('N', 'Y', 'system', NOW(), 11, 1,'USR', 'NONE'      , 'MU_USR_SRC'    , '통합검색'         , '/usr/inform/search/openSearch.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'USR', 'MU_USR_INV', 'MU_USR_INV0101', '투자자검색'         , '/usr/invest/fund/openSearchFund.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'USR', 'MU_USR_INV', 'MU_USR_INV0201', '경영체검색'         , '/usr/invest/ent/openSearchEnt.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'USR', 'MU_USR_INV', 'MU_USR_INV0301', '매칭서비스'         , '/usr/mypage/matching/openMatchInvest.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'USR', 'MU_USR_INV', 'MU_USR_GIS0101', 'GIS'                , '/usr/gis/gis/openGIS.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'USR', 'MU_USR_SUP', 'MU_USR_SUP0101', '지원사업 통합검색'  , '/usr/support/pbanc/listPbanc.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'USR', 'MU_USR_SUP', 'MU_USR_SUP0201', '매칭서비스'         , '/usr/mypage/matching/openMatchSupport.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'USR', 'MU_USR_SUP', 'MU_USR_SUP0301', '농금원 지원사업신청', '/usr/support/support/openSprt.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'USR', 'MU_USR_INF', 'MU_USR_INF0101', '투자유치가이드'     , '/usr/inform/bbs/openInvestGuide.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'USR', 'MU_USR_INF', 'MU_USR_INF0201', '우수투자사례'       , '/usr/inform/bbs/listInvestCase.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'USR', 'MU_USR_INF', 'MU_USR_INF0301', '자료실'             , '/usr/inform/bbs/listData.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'USR', 'MU_USR_INF', 'MU_USR_INF0401', '홍보영상'           , '/usr/inform/bbs/listPromotion.do'),
('N', 'Y', 'system', NOW(), 5, 2, 'USR', 'MU_USR_INF', 'MU_USR_INF0501', '유관기관커뮤니티'   , '/usr/inform/bbs/listCommunity.do'),
('N', 'Y', 'system', NOW(), 6, 2, 'USR', 'MU_USR_INF', 'MU_USR_INF0601', '경영체 데이터 업로드', '/usr/inform/bbs/listSprtUld.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'USR', 'MU_USR_CUS', 'MU_USR_CUS0101', '공지사항'           , '/usr/inform/bbs/listNotice.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'USR', 'MU_USR_CUS', 'MU_USR_CUS0201', '자주묻는질문'       , '/usr/inform/bbs/listFAQ.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'USR', 'MU_USR_CUS', 'MU_USR_CUS0301', '1:1문의'            , '/usr/inform/bbs/formQNA.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'USR', 'MU_USR_CUS', 'MU_USR_CUS0401', '설문조사'           , '/usr/inform/survey/listSurvey.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'USR', 'MU_USR_INT', 'MU_USR_INT0101', '플랫폼 소개'        , '/usr/inform/intro/openPlatform.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'USR', 'MU_USR_INT', 'MU_USR_INT0201', '농금원 안내'        , '/usr/inform/intro/openAPFS.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'USR', 'MU_USR_INT', 'MU_USR_INT0301', '유관기관 안내'      , '/usr/inform/intro/openAgency.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'USR', 'MU_USR_SRC', 'MU_USR_SRC0101', '통합검색'      , '/usr/inform/search/openSearch.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'USR', 'MU_USR_SRC', 'MU_USR_SRC0102', '공지사항'      , '/usr/inform/search/openSearchNotice.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'USR', 'MU_USR_SRC', 'MU_USR_SRC0103', '자료실'      , '/usr/inform/search/openSearchData.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'USR', 'MU_USR_SRC', 'MU_USR_SRC0104', '우수투자사례'      , '/usr/inform/search/openSearchInvestCase.do')
;

INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES
 ('url', 'Y', 'system', NOW(), 0, 'USR',             NULL, 'PG_USR_MAN0101', '사용자메인'                      		, '/usr/main/main.do')
,('url', 'Y', 'system', NOW(), 0, 'USR',             NULL, 'PG_USR_MEM0201', '사용자로그인'                    		, '/com/common/login.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INV0101', 'PG_USR_INV0101', '투자서비스 - 투자자검색'         		, '/usr/invest/fund/listFund.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INV0201', 'PG_USR_INV0201', '투자서비스 - 경영체검색'         		, '/usr/invest/ent/listEnt.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INV0301', 'PG_USR_INV0301', '투자서비스 - 매칭서비스'         		, '/usr/mypage/matching/openMatchInvest.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_GIS0101', 'PG_USR_GIS0101', '투자서비스 - GIS'                		, '/usr/gis/gis/openGIS.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_SUP0101', 'PG_USR_SUP0101', '지원서비스 - 지원사업 통합검색'  			, '/usr/support/pbanc/listPbanc.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_SUP0201', 'PG_USR_SUP0201', '지원서비스 - 매칭서비스'         		, '/usr/mypage/matching/openMatchSupport.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_SUP0301', 'PG_USR_SUP0301', '지원서비스 - 농금원 지원사업신청'			, '/usr/support/support/openSprt.do'),
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INF0101', 'PG_USR_INF0101', '정보서비스 - 투자유치가이드'      		, '/usr/inform/bbs/openInvestGuide.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INF0201', 'PG_USR_INF0201', '정보서비스 - 우수투자사례'       		 	, '/usr/inform/bbs/listInvestCase.do')
,('url', 'Y', 'system', NOW(), 1, 'USR', 'MU_USR_INF0201', 'PG_USR_INF0202', '정보서비스 - 우수투자사례 - 상세조회'		, '/usr/inform/bbs/viewInvestCase.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INF0301', 'PG_USR_INF0301', '정보서비스 - 자료실'             		, '/usr/inform/bbs/listData.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INF0401', 'PG_USR_INF0401', '정보서비스 - 홍보영상'           	    , '/usr/inform/bbs/listPromotion.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INF0501', 'PG_USR_INF0501', '정보서비스 - 유관기관커뮤니티'   	        , '/usr/inform/bbs/listCommunity.do')
,('url', 'Y', 'system', NOW(), 1, 'USR', 'MU_USR_INF0501', 'PG_USR_INF0502', '정보서비스 - 유관기관커뮤니티 - 상세조회'     , '/usr/inform/bbs/viewCommunity.do')
,('url', 'Y', 'system', NOW(), 2, 'USR', 'MU_USR_INF0501', 'PG_USR_INF0503', '정보서비스 - 유관기관커뮤니티 - 등록'        , '/usr/inform/bbs/modalCommunity.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INF0601', 'PG_USR_INF0601', '정보서비스 - 경영체 데이터 업로드'   , '/usr/inform/bbs/listSprtUld.do')
,('url', 'Y', 'system', NOW(), 1, 'USR', 'MU_USR_INF0601', 'PG_USR_INF0602', '정보서비스 - 경영체 데이터 업로드 - 등록'   , '/usr/inform/bbs/modalSprtUld.do')
,('url', 'Y', 'system', NOW(), 2, 'USR', 'MU_USR_INF0601', 'PG_USR_INF0603', '정보서비스 - 경영체 데이터 업로드 - 목록'   , '/usr/inform/bbs/getListSprtUld.do')
,('url', 'Y', 'system', NOW(), 3, 'USR', 'MU_USR_INF0601', 'PG_USR_INF0604', '정보서비스 - 경영체 데이터 업로드 - 저장'   , '/usr/inform/bbs/saveSprtUld.do')
,('url', 'Y', 'system', NOW(), 4, 'USR', 'MU_USR_INF0601', 'PG_USR_INF0605', '정보서비스 - 경영체 데이터 업로드 - 양식 다운로드'   , '/usr/inform/bbs/downloadSprtUld.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_CUS0101', 'PG_USR_CUS0101', '고객센터 - 공지사항'             , '/usr/inform/bbs/listNotice.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_CUS0201', 'PG_USR_CUS0201', '고객센터 - 자주묻는질문'         , '/usr/inform/bbs/listFAQ.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_CUS0301', 'PG_USR_CUS0301', '고객센터 - 1:1문의'              , '/usr/inform/bbs/formQNA.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_CUS0401', 'PG_USR_CUS0401', '고객센터 - 설문조사'             , '/usr/inform/survey/listSurvey.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INT0101', 'PG_USR_INT0101', '플랫폼소개 - 플랫폼 소개'        , '/usr/inform/intro/openPlatform.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INT0201', 'PG_USR_INT0201', '플랫폼소개 - 농금원 안내'        , '/usr/inform/intro/openAPFS.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INT0301', 'PG_USR_INT0301', '플랫폼소개 - 유관기관 안내'      , '/usr/inform/intro/openAgency.do')
,('url', 'Y', 'system', NOW(), 1, 'USR', 'MU_USR_MYP0501', 'PG_USR_MYP0502', '플랫폼소개 - 유관기관 안내 - 상세조회'   , '/usr/mypage/pbanc/viewPbanc.do')
,('url', 'Y', 'system', NOW(), 2, 'USR', 'MU_USR_MYP0501', 'PG_USR_MYP0503', '플랫폼소개 - 유관기관 안내 - 등록'      , '/usr/mypage/pbanc/formPbanc.do')
;

INSERT INTO SYS_MENU (POPUP_YN, USE_YN, RGTR_NO, REG_YMD, MENU_ORDR, MENU_LEVEL, SYS_SE_CD, UP_MENU_ID, MENU_ID, MENU_NM, TRGT_URL) VALUES 
('N', 'Y', 'system', NOW(), 1, 1, 'ADM', 'NONE', 'MU_ADM_MAN', '대시보드'    , '/adm/main/main.do'),
('N', 'Y', 'system', NOW(), 2, 1, 'ADM', 'NONE', 'MU_ADM_MEM', '회원관리'    , '/adm/sys/user/listUserInfo.do'),
('N', 'Y', 'system', NOW(), 3, 1, 'ADM', 'NONE', 'MU_ADM_SUP', '지원사업관리', '/adm/support/support/openAttractBefore.do'),
('N', 'Y', 'system', NOW(), 4, 1, 'ADM', 'NONE', 'MU_ADM_INV', '투자정보관리', '/adm/invest/fof/openFOF.do'),
('N', 'Y', 'system', NOW(), 6, 1, 'ADM', 'NONE', 'MU_ADM_MNG', '운영관리'    , '/adm/invest/announce/openAnnounce.do'),
('N', 'Y', 'system', NOW(), 6, 1, 'ADM', 'NONE', 'MU_ADM_BBS', '게시판관리'  , '/adm/inform/bbs/openNotice.do'),
('N', 'Y', 'system', NOW(), 5, 1, 'ADM', 'NONE', 'MU_ADM_SVY', '설문관리'    , '/adm/inform/survey/openSurvey.do'),
('N', 'Y', 'system', NOW(), 7, 1, 'ADM', 'NONE', 'MU_ADM_SYS', '시스템관리'  , '/adm/sys/code/listCode.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'ADM', 'MU_ADM_MEM', 'MU_ADM_MEM0101', '사용자관리', '/adm/sys/user/listUserInfo.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'ADM', 'MU_ADM_MEM', 'MU_ADM_MEM0201', '업체관리', '/adm/invest/corp/listCompany.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'ADM', 'MU_ADM_SUP', 'MU_ADM_SUP0101', '세부지원사업관리', '/adm/support/support/openAttractBefore.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'ADM', 'MU_ADM_SUP', 'MU_ADM_SUP0201', '신청현황관리', '/adm/support/apply/openApply.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'ADM', 'MU_ADM_INV', 'MU_ADM_INV0101', '모태펀드등록', '/adm/invest/fof/openFOF.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'ADM', 'MU_ADM_INV', 'MU_ADM_INV0201', '투자설명회등록', '/adm/invest/briefing/openBriefing.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'ADM', 'MU_ADM_INV', 'MU_ADM_INV0301', '참여기업등록', '/adm/invest/company/openCompany.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'ADM', 'MU_ADM_INV', 'MU_ADM_INV0401', 'IR검토의견서관리', '/adm/invest/irreview/openIRreview.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0101', '사업공고관리', '/adm/invest/announce/openAnnounce.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0201', 'IR지원현황', '/adm/support/ir/openIr.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0301', '배너관리', '/adm/inform/banner/openBanner.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0401', '투자분야관리', '/adm/invest/investfield/openInvestField.do'),
('N', 'Y', 'system', NOW(), 5, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0501', '제출서류관리', '/adm/invest/submitfile/openSubmitFile.do'),
('N', 'Y', 'system', NOW(), 6, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0601', '검색어관리', '/adm/inform/bbs/openSrchWord.do'),
('N', 'Y', 'system', NOW(), 7, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0701', '상담일지', '/adm/inform/bbs/openDscsn.do'),
('N', 'Y', 'system', NOW(), 8, 2, 'ADM', 'MU_ADM_MNG', 'MU_ADM_MNG0801', '경영체 데이터 업로드', '/adm/support/biz/openSprtUld.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'ADM', 'MU_ADM_BBS', 'MU_ADM_BBS0101', '공지사항', '/adm/inform/bbs/openNotice.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'ADM', 'MU_ADM_BBS', 'MU_ADM_BBS0201', '질의응답', '/adm/inform/bbs/openQNA.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'ADM', 'MU_ADM_BBS', 'MU_ADM_BBS0301', '자료실', '/adm/inform/bbs/openData.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'ADM', 'MU_ADM_BBS', 'MU_ADM_BBS0401', '우수투자사례', '/adm/inform/bbs/openInvestCase.do'),
('N', 'Y', 'system', NOW(), 5, 2, 'ADM', 'MU_ADM_BBS', 'MU_ADM_BBS0501', '홍보영상', '/adm/inform/bbs/openPromotion.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'ADM', 'MU_ADM_SVY', 'MU_ADM_SVY0101', '설문관리', '/adm/inform/survey/openSurvey.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'ADM', 'MU_ADM_SVY', 'MU_ADM_SVY0201', '설문결과관리', '/adm/inform/survey/openAnswer.do'),
('N', 'Y', 'system', NOW(), 1, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0101', '공통코드관리', '/adm/sys/code/listCode.do'),
('N', 'Y', 'system', NOW(), 2, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0201', '서류코드관리', '/adm/sys/file/listPapeCode.do'),
('N', 'Y', 'system', NOW(), 3, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0301', '권한관리', '/adm/sys/role/listRole.do'),
('N', 'Y', 'system', NOW(), 4, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0401', '메뉴관리', '/adm/sys/menu/listMenu.do'),
('N', 'Y', 'system', NOW(), 5, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0501', '프로그램관리', '/adm/sys/prog/listProg.do'),
('N', 'Y', 'system', NOW(), 6, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0601', '권한별메뉴관리', '/adm/sys/role/listRoleMenu.do'),
('N', 'Y', 'system', NOW(), 7, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0701', '권한별프로그램관리', '/adm/sys/role/listRoleProg.do'),
('N', 'Y', 'system', NOW(), 8, 2, 'ADM', 'MU_ADM_SYS', 'MU_ADM_SYS0801', '접속이력', '/adm/sys/log/listAccessLog.do'),
;


INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
 ('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MAN0101', 'PG_ADM_MAN0101', '대시보드 - ', '/adm/main/main.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MEM0101', 'PG_ADM_MEM0101', '회원관리 - 사용자관리', '/adm/sys/user/listUserInfo.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MEM0201', 'PG_ADM_MEM0201', '회원관리 - 업체관리', '/adm/invest/ent/openEnt.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_MEM0201', 'PG_ADM_MEM0202', '회원관리 - 업체관리 - 등록수정', '/adm/invest/ent/modalEntForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_MEM0201', 'PG_ADM_MEM0203', '회원관리 - 업체관리 - 목록', '/adm/invest/ent/getListEnt.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_MEM0201', 'PG_ADM_MEM0204', '회원관리 - 업체관리 - 저장', '/adm/invest/ent/saveEnt.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SUP0101', 'PG_ADM_SUP0101', '지원사업관리 - 세부지원사업관리', '/adm/support/support/openInvtSprt.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_SUP0101', 'PG_ADM_SUP0102', '지원사업관리 - 세부지원사업관리 - 등록수정', '/adm/support/support/modalInvtSprtForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_SUP0101', 'PG_ADM_SUP0103', '지원사업관리 - 세부지원사업관리 - 목록', '/adm/support/support/getListInvtSprt.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_SUP0101', 'PG_ADM_SUP0104', '지원사업관리 - 세부지원사업관리 - 상세', '/adm/support/support/getInvtSprt.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_SUP0101', 'PG_ADM_SUP0105', '지원사업관리 - 세부지원사업관리 - 저장', '/adm/support/support/saveInvtSprt.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0201', '지원사업관리 - 신청현황관리', '/adm/support/support/openInvtSprtAply.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0202', '지원사업관리 - 신청현황관리 - 등록수정', '/adm/support/support/modalInvtSprtAplyForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0203', '지원사업관리 - 신청현황관리 - 목록', '/adm/support/support/getListInvtSprtAply.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0204', '지원사업관리 - 신청현황관리 - 상세', '/adm/support/support/getInvtSprtAply.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0205', '지원사업관리 - 신청현황관리 - 저장', '/adm/support/support/saveInvtSprtAply.do')
,('url', 'Y', 'system', NOW(), 5, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0206', '지원사업관리 - 신청현황관리 - 매출액 목록', '/adm/support/support/getListInvtSprtSls.do')
,('url', 'Y', 'system', NOW(), 6, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0207', '지원사업관리 - 신청현황관리 - 진행현황 목록', '/adm/support/support/getListSprtBizPrgre.do')
,('url', 'Y', 'system', NOW(), 7, 'ADM', 'MU_ADM_SUP0201', 'PG_ADM_SUP0208', '지원사업관리 - 신청현황관리 - 진행현황 저장', '/adm/support/support/saveSprtBizPrgre.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_INV0101', 'PG_ADM_INV0101', '투자정보관리 - 모태펀드등록', '/adm/invest/fund/openFund.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_INV0101', 'PG_ADM_INV0102', '투자정보관리 - 모태펀드등록 - 등록수정', '/adm/invest/fund/modalFundForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_INV0101', 'PG_ADM_INV0103', '투자정보관리 - 모태펀드등록 - 목록', '/adm/invest/fund/getListFund.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_INV0101', 'PG_ADM_INV0104', '투자정보관리 - 모태펀드등록 - 저장', '/adm/invest/fund/saveFund.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_INV0101', 'PG_ADM_INV0105', '투자정보관리 - 모태펀드등록 - 조합원 목록', '/adm/invest/fund/getListInvt.do')
,('url', 'Y', 'system', NOW(), 5, 'ADM', 'MU_ADM_INV0101', 'PG_ADM_INV0106', '투자정보관리 - 모태펀드등록 - 특정 펀드 조합원 목록', '/adm/invest/fund/getListFundInvstr.do')
,('url', 'Y', 'system', NOW(), 6, 'ADM', 'MU_ADM_INV0101', 'PG_ADM_INV0107', '투자정보관리 - 모태펀드등록 - 조합원 저장', '/adm/invest/fund/saveFundInvstr.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_INV0201', 'PG_ADM_INV0201', '투자정보관리 - 투자설명회등록', '/adm/invest/event/openEvent.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_INV0201', 'PG_ADM_INV0202', '투자정보관리 - 투자설명회등록 - 등록수정', '/adm/invest/event/modalEventForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_INV0201', 'PG_ADM_INV0203', '투자정보관리 - 투자설명회등록 - 목록', '/adm/invest/event/getListEvent.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_INV0201', 'PG_ADM_INV0203', '투자정보관리 - 투자설명회등록 - 상세', '/adm/invest/event/getEvent.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_INV0201', 'PG_ADM_INV0204', '투자정보관리 - 투자설명회등록 - 저장', '/adm/invest/event/saveEvent.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_INV0301', 'PG_ADM_INV0301', '투자정보관리 - 참여기업등록', '/adm/invest/event/openEventEnt.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_INV0301', 'PG_ADM_INV0302', '투자정보관리 - 참여기업등록 - 등록수정', '/adm/invest/event/modalEventEntForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_INV0301', 'PG_ADM_INV0303', '투자정보관리 - 참여기업등록 - 목록', '/adm/invest/event/getListEventEnt.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_INV0301', 'PG_ADM_INV0304', '투자정보관리 - 참여기업등록 - 투자자 저장', '/adm/invest/event/saveEventInvt.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_INV0301', 'PG_ADM_INV0305', '투자정보관리 - 참여기업등록 - 경영체 저장', '/adm/invest/event/saveEventEnt.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_INV0401', 'PG_ADM_INV0401', '투자정보관리 - IR검토의견서관리', '/adm/invest/event/openIrOpnn.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_INV0401', 'PG_ADM_INV0402', '투자정보관리 - IR검토의견서관리 - 등록수정', '/adm/invest/event/modalIrOpnnForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_INV0401', 'PG_ADM_INV0403', '투자정보관리 - IR검토의견서관리 - 목록', '/adm/invest/event/getListIrOpnn.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_INV0401', 'PG_ADM_INV0404', '투자정보관리 - IR검토의견서관리 - 상세', '/adm/invest/event/viewIrOpnn.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_INV0401', 'PG_ADM_INV0405', '투자정보관리 - IR검토의견서관리 - 경영체별 상세', '/adm/invest/event/modalIrOpnnEnt.do')
,('url', 'Y', 'system', NOW(), 5, 'ADM', 'MU_ADM_INV0401', 'PG_ADM_INV0406', '투자정보관리 - IR검토의견서관리 - 투자자별 상세', '/adm/invest/event/modalIrOpnnInvt.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0101', 'PG_ADM_MNG0101', '운영관리 - 사업공고관리', '/adm/support/pbanc/openPbanc.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_MNG0101', 'PG_ADM_MNG0102', '운영관리 - 사업공고관리 - 등록수정', '/adm/support/pbanc/modalPbancForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_MNG0101', 'PG_ADM_MNG0103', '운영관리 - 사업공고관리 - 목록', '/adm/support/pbanc/getListPbanc.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_MNG0101', 'PG_ADM_MNG0104', '운영관리 - 사업공고관리 - 상세', '/adm/support/pbanc/getPbanc.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_MNG0101', 'PG_ADM_MNG0105', '운영관리 - 사업공고관리 - 저장', '/adm/support/pbanc/savePbanc.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0201', 'PG_ADM_MNG0201', '운영관리 - IR지원현황', '/adm/support/ir/openIr.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_MNG0201', 'PG_ADM_MNG0202', '운영관리 - IR지원현황 - 등록수정', '/adm/support/ir/modalIrForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_MNG0201', 'PG_ADM_MNG0203', '운영관리 - IR지원현황 - 목록', '/adm/support/ir/getListIr.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0301', 'PG_ADM_MNG0301', '운영관리 - 배너관리', '/adm/inform/banner/openBanner.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0301', 'PG_ADM_MNG0302', '운영관리 - 배너관리 - 등록수정', '/adm/inform/banner/modalBannerForm.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0301', 'PG_ADM_MNG0303', '운영관리 - 배너관리 - 목록', '/adm/inform/banner/getListBanner.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0301', 'PG_ADM_MNG0304', '운영관리 - 배너관리 - 저장', '/adm/inform/banner/saveBanner.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0401', 'PG_ADM_MNG0401', '운영관리 - 투자분야관리', '/adm/invest/investfield/openInvestField.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_MNG0401', 'PG_ADM_MNG0402', '운영관리 - 투자분야관리 - 등록수정', '/adm/invest/investfield/modalInvestFieldForm.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_MNG0401', 'PG_ADM_MNG0403', '운영관리 - 투자분야관리 - 목록', '/adm/invest/investfield/getListInvestField.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_MNG0401', 'PG_ADM_MNG0404', '운영관리 - 투자분야관리 - 저장', '/adm/invest/investfield/saveInvestField.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0501', 'PG_ADM_MNG0501', '운영관리 - 제출서류관리', '/adm/invest/submitfile/openSubmitFile.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_MNG0501', 'PG_ADM_MNG0502', '운영관리 - 제출서류관리 등록수정', '/adm/invest/submitfile/modalSubmitFileForm.do')   --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0601', 'PG_ADM_MNG0601', '운영관리 - 검색어관리', '/adm/inform/bbs/openSrchWord.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0701', 'PG_ADM_MNG0701', '운영관리 - 상담일지', '/adm/invest/dscsn/openDscsn.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0701', 'PG_ADM_MNG0702', '운영관리 - 상담일지 등록수정', '/adm/invest/dscsn/modalDscsnForm.do')   --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_MNG0801', 'PG_ADM_MNG0801', '운영관리 - 경영체 데이터 업로드', '/adm/support/biz/openSprtUld.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_MNG0801', 'PG_ADM_MNG0802', '운영관리 - 경영체 데이터 업로드 - 등록', '/adm/support/biz/modalSprtUld.do')
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_MNG0801', 'PG_ADM_MNG0803', '운영관리 - 경영체 데이터 업로드 - 목록', '/adm/support/biz/getListSprtUld.do')
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_MNG0801', 'PG_ADM_MNG0804', '운영관리 - 경영체 데이터 업로드 - 상세', '/adm/support/biz/modalSprtUldForm.do')
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_MNG0801', 'PG_ADM_MNG0805', '운영관리 - 경영체 데이터 업로드 - 저장', '/adm/support/biz/saveSprtUld.do')
,('url', 'Y', 'system', NOW(), 5, 'ADM', 'MU_ADM_MNG0801', 'PG_ADM_MNG0806', '운영관리 - 경영체 데이터 업로드 - 경영체 목록', '/adm/support/biz/getListSprtBiz.do')
,('url', 'Y', 'system', NOW(), 6, 'ADM', 'MU_ADM_MNG0801', 'PG_ADM_MNG0807', '운영관리 - 경영체 데이터 업로드 - 양식 다운로드', '/adm/support/biz/downloadSprtUld.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_BBS0101', 'PG_ADM_BBS0101', '게시판관리 - 공지사항', '/adm/inform/bbs/openNotice.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_BBS0101', 'PG_ADM_BBS0102', '게시판관리 - 공지사항 상세조회', '/adm/inform/bbs/modalNoticeView.do')  --
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_BBS0101', 'PG_ADM_BBS0103', '게시판관리 - 공지사항 등록수정', '/adm/inform/bbs/modalNoticeForm.do')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_BBS0201', 'PG_ADM_BBS0201', '게시판관리 - 질의응답', '/adm/inform/bbs/openQNA.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_BBS0201', 'PG_ADM_BBS0202', '게시판관리 - 질의응답 1:1문의 상세조회', '/adm/inform/bbs/modalQNAView')  --
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_BBS0201', 'PG_ADM_BBS0203', '게시판관리 - 질의응답 1:1문의 등록수정', '/adm/inform/bbs/modalQNAForm')  --
,('url', 'Y', 'system', NOW(), 3, 'ADM', 'MU_ADM_BBS0201', 'PG_ADM_BBS0204', '게시판관리 - 질의응답 자주묻는질문 상세조회', '/adm/inform/bbs/modalFAQView')  --
,('url', 'Y', 'system', NOW(), 4, 'ADM', 'MU_ADM_BBS0201', 'PG_ADM_BBS0205', '게시판관리 - 질의응답 자주묻는질문 등록수정', '/adm/inform/bbs/modalFAQForm')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_BBS0301', 'PG_ADM_BBS0301', '게시판관리 - 자료실', '/adm/inform/bbs/openData.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_BBS0301', 'PG_ADM_BBS0302', '게시판관리 - 자료실 상세조회', '/adm/inform/bbs/modalDataView')  --
,('url', 'Y', 'system', NOW(), 2, 'ADM', 'MU_ADM_BBS0301', 'PG_ADM_BBS0303', '게시판관리 - 자료실 등록수정', '/adm/inform/bbs/modalDataForm')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_BBS0401', 'PG_ADM_BBS0401', '게시판관리 - 우수투자사례', '/adm/inform/bbs/openInvestCase.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_BBS0401', 'PG_ADM_BBS0402', '게시판관리 - 우수투자사례 상세조회', '/adm/inform/bbs/modalInvestCaseView')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_BBS0401', 'PG_ADM_BBS0403', '게시판관리 - 우수투자사례 등록수정', '/adm/inform/bbs/modalInvestCaseForm')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_BBS0501', 'PG_ADM_BBS0501', '게시판관리 - 홍보영상', '/adm/inform/bbs/openPromotion.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SVY0101', 'PG_ADM_SVY0101', '설문관리 - 설문관리', '/adm/inform/survey/openSurvey.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SVY0201', 'PG_ADM_SVY0201', '설문관리 - 설문결과관리', '/adm/inform/survey/openAnswer.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0101', 'PG_ADM_SYS0101', '시스템관리 - 공통코드관리', '/adm/sys/code/listCode.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_SYS0101', 'PG_ADM_SYS0102', '시스템관리 - 공통코드관리 등록수정', '/adm/sys/code/modalCodeForm.do')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0201', 'PG_ADM_SYS0201', '시스템관리 - 서류코드관리', '/adm/sys/file/listPapeCode.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_SYS0201', 'PG_ADM_SYS0202', '시스템관리 - 서류코드관리 등록수정', '/adm/sys/file/modalPapeCodeForm.do')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0301', 'PG_ADM_SYS0301', '시스템관리 - 권한관리', '/adm/sys/role/listRole.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_SYS0301', 'PG_ADM_SYS0302', '시스템관리 - 권한관리 등록수정', '/adm/sys/role/modalRoleForm.do')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0401', 'PG_ADM_SYS0401', '시스템관리 - 메뉴관리', '/adm/sys/menu/listMenu.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_SYS0401', 'PG_ADM_SYS0402', '시스템관리 - 메뉴관리 등록수정', '/adm/sys/menu/modalMenuForm.do')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0501', 'PG_ADM_SYS0501', '시스템관리 - 프로그램관리', '/adm/sys/prog/listProg.do')
,('url', 'Y', 'system', NOW(), 1, 'ADM', 'MU_ADM_SYS0501', 'PG_ADM_SYS0502', '시스템관리 - 프로그램관리 등록수정', '/adm/sys/prog/modalProgForm.do')  --
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0601', 'PG_ADM_SYS0601', '시스템관리 - 권한별메뉴관리', '/adm/sys/role/listRoleMenu.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0701', 'PG_ADM_SYS0701', '시스템관리 - 권한별프로그램관리', '/adm/sys/role/listRoleProg.do')
,('url', 'Y', 'system', NOW(), 0, 'ADM', 'MU_ADM_SYS0801', 'PG_ADM_SYS0801', '시스템관리 - 접속이력', '/adm/sys/log/listAccessLog.do')
;

-- 회원가입 프로그램등록
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
 ('url', 'Y', 'system', NOW(), 0, 'USR', NULL, 'PG_USR_MEM0305', '회원가입 - 가입회원선택', '/com/user/openJoin.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', NULL, 'PG_USR_MEM0306', '회원가입 - 약관동의'    , '/com/user/openAgree.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', NULL, 'PG_USR_MEM0307', '회원가입 - 기본정보입력', '/com/user/openForm.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', NULL, 'PG_USR_MEM0308', '회원가입 - 기업정보입력', '/com/user/openCorp.do')
,('url', 'Y', 'system', NOW(), 0, 'USR', NULL, 'PG_USR_MEM0309', '회원가입 - 가입완료'    , '/com/user/doneJoin.do')
;

-- 메뉴권한등록
INSERT INTO SYS_ROLE_MENU (RGTR_NO, REG_YMD, ROLE_ID, MENU_ID) SELECT 'system', NOW(), ROLE_ID, MENU_ID FROM (SELECT MENU_ID FROM SYS_MENU WHERE SYS_SE_CD = 'USR') A, (SELECT ROLE_ID FROM SYS_ROLE) B;
INSERT INTO SYS_ROLE_MENU (RGTR_NO, REG_YMD, ROLE_ID, MENU_ID) SELECT 'system', NOW(), ROLE_ID, MENU_ID FROM (SELECT MENU_ID FROM SYS_MENU WHERE SYS_SE_CD = 'ADM') A, (SELECT ROLE_ID FROM SYS_ROLE WHERE ROLE_ID IN ('ROLE_ADM_SYS')) B;
INSERT INTO SYS_ROLE_MENU (RGTR_NO, REG_YMD, ROLE_ID, MENU_ID) SELECT 'system', NOW(), ROLE_ID, MENU_ID FROM (SELECT MENU_ID FROM SYS_MENU WHERE SYS_SE_CD = 'ADM' AND MENU_ID NOT LIKE 'MU_ADM_SYS%') A, (SELECT ROLE_ID FROM SYS_ROLE WHERE ROLE_ID IN ('ROLE_ADM_MNG')) B;

-- 프로그램등록 (관리자시스템,사용자시스템 - 관리자)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) SELECT 'system', NOW(), 'ROLE_ADM_SYS', PRGRM_ID FROM SYS_PROG;
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) SELECT 'system', NOW(), 'ROLE_ADM_MNG', PRGRM_ID FROM SYS_PROG WHERE PRGRM_ID LIKE 'PG_ADM_%' AND PRGRM_ID NOT LIKE 'PG_ADM_SYS%';
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) SELECT 'system', NOW(), 'ROLE_ADM_MNG', PRGRM_ID FROM SYS_PROG WHERE PRGRM_ID LIKE 'PG_USR_%' AND MENU_ID IS NOT NULL;

-- 프로그램등록 (전체권한)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) SELECT 'system', NOW(), ROLE_ID, PRGRM_ID FROM 
(SELECT PRGRM_ID FROM SYS_PROG WHERE PRGRM_ID IN (
	'PG_USR_INV0101','PG_USR_INF0101','PG_USR_INF0201','PG_USR_INF0301','PG_USR_INF0401','PG_USR_SUP0101',
	'PG_USR_CUS0101','PG_USR_CUS0201','PG_USR_CUS0301','PG_USR_INT0101','PG_USR_INT0201','PG_USR_INT0301'
)) A, 
(SELECT ROLE_ID  FROM SYS_ROLE WHERE ROLE_ID IN ('ROLE_USR_USR','ROLE_USR_EIV','ROLE_USR_EBZ','ROLE_USR_EIS','ROLE_USR_RESTRICTED')) B;

-- 프로그램등록 (설문조사)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) SELECT 'system', NOW(), ROLE_ID, 'PG_USR_CUS0401' FROM SYS_ROLE WHERE ROLE_ID IN ('ROLE_USR_USR','ROLE_USR_EIV','ROLE_USR_EBZ','ROLE_USR_EIS');

-- 프로그램등록 (투자서비스-매칭서비스)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) SELECT 'system', NOW(), ROLE_ID, 'PG_USR_INV0301' FROM SYS_ROLE WHERE ROLE_ID IN ('ROLE_USR_EIV','ROLE_USR_EBZ','ROLE_USR_EIS');

-- 프로그램등록 (투자서비스-경영체검색/매칭서비스)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) 
SELECT 'system', NOW(), ROLE_ID, PRGRM_ID FROM 
(SELECT PRGRM_ID FROM SYS_PROG WHERE PRGRM_ID IN ('PG_USR_INV0201','PG_USR_GIS0101')) A, 
(SELECT ROLE_ID  FROM SYS_ROLE WHERE ROLE_ID  IN ('ROLE_USR_EIV'  ,'ROLE_USR_EIS'  )) B;

-- 프로그램등록 (지원서비스)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) 
SELECT 'system', NOW(), ROLE_ID, PRGRM_ID FROM 
(SELECT PRGRM_ID FROM SYS_PROG WHERE PRGRM_ID IN ('PG_USR_SUP0201','PG_USR_SUP0301')) A, 
(SELECT ROLE_ID  FROM SYS_ROLE WHERE ROLE_ID  IN ('ROLE_USR_USR'  ,'ROLE_USR_EBZ'  )) B;

-- 프로그램등록 (유관기관커뮤니티)
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) 
SELECT 'system', NOW(), ROLE_ID, PRGRM_ID FROM 
(SELECT PRGRM_ID FROM SYS_PROG WHERE PRGRM_ID IN ('PG_USR_INF0501')) A, 
(SELECT ROLE_ID  FROM SYS_ROLE WHERE ROLE_ID  IN ('ROLE_USR_EIS'  )) B;

-- 사용자메인 프로그램권한 제외
DELETE FROM SYS_ROLE_PROG WHERE PRGRM_ID = 'PG_USR_MAN0101';

-- 2023.04.26 투자자검색 URL변경
UPDATE SYS_MENU SET TRGT_URL  = '/usr/invest/fund/openSearchFund.do' WHERE MENU_ID  = 'MU_USR_INV';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/invest/fund/openSearchFund.do' WHERE MENU_ID  = 'MU_USR_INV0101';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/invest/fund/openSearchFund.do' WHERE PRGRM_ID = 'PG_USR_INV0101';

-- 2023.04.27 투자서비스 - 투자자검색 - 지원하기 팝업 권한설정 (경영체만)
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INV0101', 'PG_USR_INV0102', '투자서비스 - 지원하기', '/usr/invest/fund/modalApplyFund.do');
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) VALUES ('system', NOW(), 'ROLE_USR_EBZ', 'PG_USR_INV0102');

-- 2023.04.28 경영체검색 URL변경
UPDATE SYS_MENU SET TRGT_URL  = '/usr/invest/ent/openSearchEnt.do' WHERE MENU_ID  = 'MU_USR_INV0201';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/invest/ent/openSearchEnt.do' WHERE PRGRM_ID = 'PG_USR_INV0201';

-- 2023.04.29 매칭서비스 URL변경
UPDATE SYS_PROG SET PRGRM_URL = '/usr/mypage/matching/openMatchInvest.do'  WHERE PRGRM_ID = 'PG_USR_INV0301';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/mypage/matching/openMatchInvest.do'  WHERE MENU_ID  = 'MU_USR_INV0301';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/mypage/matching/openMatchSupport.do' WHERE PRGRM_ID = 'PG_USR_SUP0201';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/mypage/matching/openMatchSupport.do' WHERE MENU_ID  = 'MU_USR_SUP0201';


-- 2023.04.30 지원사업통합검색 URL변경
UPDATE SYS_MENU SET TRGT_URL  = '/usr/support/pbanc/openSearchPbanc.do' WHERE MENU_ID  = 'MU_USR_SUP';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/support/pbanc/openSearchPbanc.do' WHERE MENU_ID  = 'MU_USR_SUP0101';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/support/pbanc/openSearchPbanc.do' WHERE PRGRM_ID = 'PG_USR_SUP0101';

-- 2023.05.02 URL변경
UPDATE SYS_MENU SET TRGT_URL  = '/usr/invest/fund/listFund.do'    WHERE MENU_ID  = 'MU_USR_INV';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/invest/fund/listFund.do'    WHERE MENU_ID  = 'MU_USR_INV0101';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/invest/fund/listFund.do'    WHERE PRGRM_ID = 'PG_USR_INV0101';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/invest/ent/listEnt.do'      WHERE MENU_ID  = 'MU_USR_INV0201';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/invest/ent/listEnt.do'      WHERE PRGRM_ID = 'PG_USR_INV0201';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/support/pbanc/listPbanc.do' WHERE MENU_ID  = 'MU_USR_SUP';
UPDATE SYS_MENU SET TRGT_URL  = '/usr/support/pbanc/listPbanc.do' WHERE MENU_ID  = 'MU_USR_SUP0101';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/support/pbanc/listPbanc.do' WHERE PRGRM_ID = 'PG_USR_SUP0101';

-- 2023.05.06 경영체 상세정보 권한설정
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_INV0201', 'PG_USR_INV0202', '투자서비스 - 경영체검색 - 상세조회', '/usr/invest/ent/viewEnt.do');
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) VALUES ('system', NOW(), 'ROLE_USR_EIV', 'PG_USR_INV0202');
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) VALUES ('system', NOW(), 'ROLE_USR_EIS', 'PG_USR_INV0202');

-- 2023.05.18 공지사항 상세정보 프로그램등록
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_CUS0101', 'PG_USR_CUS0102', '고객센터 - 공지사항 - 상세조회', '/usr/inform/bbs/viewNotice.do');

-- 2023.05.22 지원사업신청 URL변경
UPDATE SYS_MENU SET TRGT_URL  = '/usr/support/support/openSprt.do' WHERE MENU_ID  = 'MU_USR_SUP0301';
UPDATE SYS_PROG SET PRGRM_URL = '/usr/support/support/openSprt.do' WHERE PRGRM_ID = 'PG_USR_SUP0301';
-- 2023.05.22 지원사업신청 프로그램등록
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_SUP0301', 'PG_USR_SUP0302', '지원서비스 - 농금원 지원사업신청 - 신청폼', '/usr/support/support/formSprt.do');
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) 
SELECT 'system', NOW(), ROLE_ID, 'PG_USR_SUP0302' FROM SYS_ROLE_PROG WHERE PRGRM_ID = 'PG_USR_SUP0301';

-- 2023.05.22 지원사업신청-상담신청 프로그램등록
INSERT INTO SYS_PROG (PRGRM_TYPE, USE_YN, RGTR_NO, REG_YMD, PRGRM_ORDR, SYS_SE_CD, MENU_ID, PRGRM_ID, PRGRM_NM, PRGRM_URL) VALUES 
('url', 'Y', 'system', NOW(), 0, 'USR', 'MU_USR_SUP0301', 'PG_USR_SUP0303', '지원서비스 - 농금원 지원사업신청 - 상담신청', '/usr/support/support/formDscsn.do');
INSERT INTO SYS_ROLE_PROG (RGTR_NO, REG_YMD, ROLE_ID, PRGRM_ID) 
SELECT 'system', NOW(), ROLE_ID, 'PG_USR_SUP0303' FROM SYS_ROLE_PROG WHERE PRGRM_ID = 'PG_USR_SUP0301';


-- 2023.06.20 운영관리-검색어, 상담일지 관리 프로그램 권한 등록
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0601', 'ROLE_ADM_MNG', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0601', 'ROLE_ADM_SYS', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0701', 'ROLE_ADM_MNG', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_ADM_MNG0701', 'ROLE_ADM_SYS', '', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0601', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0601', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0701', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0701', 'ROLE_ADM_SYS', '100000', now());

-- 2023.07.13 정보서비스-경영체 데이터 프로그램 권한 등록
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_USR_INF0601', 'ROLE_ADM_SYS', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_USR_INF0601', 'ROLE_ADM_MNG', '', '100000', now());
INSERT INTO SYS_ROLE_MENU (menu_id, role_id, use_yn, rgtr_no, reg_ymd) VALUES ('MU_USR_INF0601', 'ROLE_USR_EIS', '', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0601', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0601', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0601', 'ROLE_USR_EIS', '100000', now());

-- 2023.07.20 게시판, 제출서류관리, 상담일지, 시스템관리 권한 등록 (관리자)
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_BBS0204', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_BBS0204', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_BBS0205', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_BBS0205', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0502', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0502', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0702', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_MNG0702', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_SYS0102', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_SYS0202', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_SYS0202', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_SYS0302', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_SYS0402', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_ADM_SYS0502', 'ROLE_ADM_SYS', '100000', now());

-- 2023.07.20 정보서비스, 고객센터, 마이페이지(사업공고등록), 시스템관리 권한 등록 (사용자)
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0502', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0502', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0502', 'ROLE_USR_EBZ', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0502', 'ROLE_USR_EIS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0502', 'ROLE_USR_EIV', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0502', 'ROLE_USR_RESTRICTED', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0502', 'ROLE_USR_USR', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0503', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0503', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0503', 'ROLE_USR_EBZ', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0503', 'ROLE_USR_EIS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0503', 'ROLE_USR_EIV', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0503', 'ROLE_USR_RESTRICTED', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0503', 'ROLE_USR_USR', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0202', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0202', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0202', 'ROLE_USR_EBZ', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0202', 'ROLE_USR_EIS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0202', 'ROLE_USR_EIV', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0202', 'ROLE_USR_RESTRICTED', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_INF0202', 'ROLE_USR_USR', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0502', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0502', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0502', 'ROLE_USR_EIS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0503', 'ROLE_ADM_MNG', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0503', 'ROLE_ADM_SYS', '100000', now());
INSERT INTO SYS_ROLE_PROG (prgrm_id, role_id, rgtr_no, reg_ymd) VALUES ('PG_USR_MYP0503', 'ROLE_USR_EIS', '100000', now());



