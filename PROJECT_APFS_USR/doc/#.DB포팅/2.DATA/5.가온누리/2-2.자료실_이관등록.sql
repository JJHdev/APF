/** 게시판 (TB_BOARD_TYPE1)
50 : 자료실 (투자유치)
53 : 자료실 (크라우드펀딩)
*/
INSERT INTO TB_BBS (
	  PST_NO
	, UP_PST_NO
	, BBS_SE_CD
	, PST_CLSF_CD
	, ANNYMTY
	, PST_TTL
	, PST_CN
	, PSTG_BGNG_YMD
	, PSTG_END_YMD
	, PST_LINK_URL
	, EML_ADDR
	, TAG_CN
	, INQ_CNT
	, RCMDTN_CNT
	, PST_PSWD
	, NTC_YN
	, FIXING_YN
	, RLS_YN
	, DEL_YN
	, POPUP_YN
	, POPUP_HG
	, POPUP_AR
	, RGTR_NO
	, REG_YMD
	, MDFR_NO
	, MDFCN_YMD	
)
SELECT (ROW_NUMBER() OVER()) + 100				AS PST_NO			-- 101 번부터 시작
     , 0										AS UP_PST_NO
	 , 'B30'    								AS BBS_SE_CD	 	-- 자료실
	 , CASE WHEN BOARD_SEQ = '50' THEN 'B304'
	   		WHEN BOARD_SEQ = '53' THEN 'B305'
	   END 										AS PST_CLSF_CD		-- 투자유치, 크라우드펀딩
	 , NULL										AS ANNYMTY
	 , TITLE									AS PST_TTL
	 , CONTENTS									AS PST_CN
	 , NULL										AS PSTG_BGNG_YMD
	 , NULL										AS PSTG_END_YMD
	 , LINK_URL									AS PST_LINK_URL
	 , NULL										AS EML_ADDR
	 , TAG										AS TAG_CN
	 , READ_COUNT::INTEGER						AS INQ_CNT
	 , HEART_COUNT::INTEGER						AS RCMDTN_CNT
	 , NULL										AS PST_PSWD
	 , 'N'										AS NTC_YN
	 , 'N'										AS FIXING_YN
	 , 'Y'										AS RLS_YN
	 , 'N'										AS DEL_YN
	 , 'N'										AS POPUP_YN
	 , NULL										AS POPUP_HG
	 , NULL										AS POPUP_AR
	 , '100000'									AS RGTR_NO
	 , CAST(CREATE_DATE AS timestamp) 			AS REG_YMD
	 , NULL										AS MDFR_NO
	 , CAST(UPDATE_DATE AS timestamp) 			AS MDFCN_YMD
  FROM ZTB_BOARD_TYPE1
 WHERE BOARD_SEQ IN ('50', '53')
 ORDER BY BOARD_SEQ, SEQ
;

 
/** 게시판 첨부파일
50 : 자료실 (투자유치)
53 : 자료실 (크라우드펀딩)
*/
INSERT INTO TB_BBS_FILE (
	  SN
	, TASK_SE_CD
	, PST_NO
	, FILE_SE_CD
	, FILE_PATH
	, STRG_FILE_NM
	, FILE_NM	
	, FILE_SZ
	, DWNLD_CNT
	, DEL_YN
	, RGTR_NO
	, REG_YMD
)
SELECT (ROW_NUMBER() OVER()) + 100			AS SN				-- 101 번부터 시작
	 , 'BB1'								AS TASK_SE_CD
	 , A.PST_NO
	 , '01'									AS FILE_SE_CD		-- 파일구분 : 일반
	 , '/inveseum/'||REPLACE(SUBSTR(B.FILE_NAME, 1, 17), '\', '/')   		AS FILE_PATH		-- 확인후 파일구분(PATH) substr(FILE_NAME, 1, 18)
	 , SPLIT_PART(B.FILE_NAME, '\', 4)		AS STRG_FILE_NM   	-- 확인후 파일구분(FILE) split_part(FILE_NAME, '\', 4)
	 , B.ORIGINAL_NAME						AS FILE_NM
	 , B.FILE_SIZE::INTEGER					AS FILE_SZ
	 , B.DOWN_COUNT::INTEGER    			AS DWNLD_CNT
	 , 'N'									AS DEL_YN
	 , '100000'								AS RGTR_NO	 
	 , now()
  FROM (
		SELECT BOARD_SEQ
			 , SEQ
			 , (ROW_NUMBER() OVER()) + 100	AS PST_NO
		  FROM ZTB_BOARD_TYPE1
		 WHERE BOARD_SEQ IN ('50', '53')  
		 ORDER BY BOARD_SEQ, SEQ	  
       ) A
	   JOIN ZTB_BOARD_FILE B 
	   ON A.BOARD_SEQ = B.BOARD_SEQ
	   AND A.SEQ = B.SEQ
 ORDER BY A.BOARD_SEQ, A.SEQ, B.SEQ
;
