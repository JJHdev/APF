/**
 투자설명회 등록
*/
INSERT INTO TB_EVENT_MNG (
	  EVNT_NO
	, EVNT_NM
	, EVNT_CN
	, EVNT_BGNG_YMD
	, EVNT_END_YMD
	, EVNT_YR
	, USE_YN
	, DEL_YN
	, RGTR_NO
	, REG_YMD
)
SELECT 'E'||SUBSTR(YEAR_NM, 3)||LPAD(COALESCE(RNUM,'1'),7,'0')   	AS EVNT_NO
     , EVNT_NM
	 , ''															AS EVNT_CN
	 , S_DATE														AS EVNT_BGNG_YMD
	 , E_DATE														AS EVNT_END_YMD
	 , YEAR_NM														AS EVNT_YR
	 , 'Y'
	 , 'N'
	 , '100000'
	 , now()
  FROM (
		SELECT (ROW_NUMBER() OVER(ORDER BY B.YEAR_NAME, A.CATEGORY_SEQ))::TEXT AS RNUM
			 , A.CATEGORY_SEQ
			 , A.CATEGORY_NAME						AS EVNT_NM
			 , B.YEAR_NAME||'0101'					AS S_DATE
			 , B.YEAR_NAME||'1231'					AS E_DATE
			 , B.YEAR_NAME							AS YEAR_NM
		  FROM ZTB_OPER_CATEGORY A
			 , ZTB_OPER_YEAR B
		 WHERE A.YEAR_SEQ = B.YEAR_SEQ
	    ) C
		
		
/**
 투자설명회 경영체 정보
 	 - 사업자번호 추후 매핑.
	 - 사업자번호 ztb_oper_data_brno
*/	
INSERT INTO TB_EVENT_ENT (
	  EVNT_PARTCPTN_NO
	, EVNT_NO
	, BZENTY_NO
	, BRNO
	, BZENTY_NM
	, RPRSV_NM
	, TELNO
	, EML_ADDR
	, MAIN_BIZ_CN
	, INQ_CNT
	, RGTR_NO
	, REG_YMD
)
SELECT 'EP'||SUBSTR(D.YEAR_NM, 3)||LPAD(COALESCE(E.DATA_SEQ,'1')::TEXT,6,'0')   AS EVNT_PARTCPTN_NO
     , D.EVNT_NO
	 , NULL					AS BZENTY_NO
	 , NULL					AS BRNO				-- 사업자번호 추후 매핑
     , E.COM_NAME			AS BZENTY_NM
	 , E.COM_CEO			AS RPRSV_NM
	 , CASE WHEN E.DATA_SEQ = 341 
	 		THEN REGEXP_REPLACE(SPLIT_PART(SPLIT_PART(E.EMAIL, '/', 1), ',', 1), '[^0-9]', '', 'g')
			ELSE REGEXP_REPLACE(SPLIT_PART(SPLIT_PART(E.TEL, '/', 1), ',', 1), '[^0-9]', '', 'g')
	   END 					AS TELNO
	 , CASE WHEN E.DATA_SEQ = 341 
	 		THEN E.TEL
			WHEN E.EMAIL	= '-'
			THEN ''
			ELSE E.EMAIL
	   END 					AS EML_ADDR
	 , E.COM_CONTENTS		AS MAIN_BIZ_CN
	 , E.READ_COUNT::INTEGER AS INQ_CNT
	 , '100000'				AS RGTR_NO
	 , CAST(E.CREATE_DATE 	AS timestamp)	AS REG_YMD
  FROM (
		SELECT 'E'||SUBSTR(YEAR_NM, 3)||LPAD(COALESCE(RNUM,'1'),7,'0')   		AS EVNT_NO
			 , CATEGORY_SEQ
			 , YEAR_SEQ
			 , YEAR_NM
		  FROM (
				SELECT (ROW_NUMBER() OVER(ORDER BY B.YEAR_NAME, A.CATEGORY_SEQ))::TEXT AS RNUM
					 , A.CATEGORY_SEQ
					 , B.YEAR_SEQ
					 , B.YEAR_NAME							AS YEAR_NM
				  FROM ZTB_OPER_CATEGORY A
					 , ZTB_OPER_YEAR B
				 WHERE A.YEAR_SEQ = B.YEAR_SEQ
				) C  
  		) D
	  , ZTB_OPER_DATA E
  WHERE D.CATEGORY_SEQ 	= E.CATEGORY_SEQ
    AND D.YEAR_SEQ 		= E.YEAR_SEQ 
  ORDER BY E.DATA_SEQ
;
COMMIT;	


/**
	투자설명회 - 경영체 정보 첨부파일
*/
INSERT INTO TB_BIZ_FILE (
	  SN
	, TASK_SE_CD
	, DOC_NO
	, DTL_DOC_NO
	, FILE_SE_CD
	, DCMNT_CD
	, FILE_PATH
	, STRG_FILE_NM
	, FILE_NM
	, FILE_SZ
	, PRCS_STTS_CD
	, PRCS_CN
	, DEL_YN
	, RGTR_NO
	, REG_YMD
)
SELECT (ROW_NUMBER() OVER(ORDER BY DATA_SEQ, GBN))									AS RNUM
	 , TASK_SE_CD
	 , EVNT_PARTCPTN_NO
	 , DTL_DOC_NO
	 , FILE_SE_CD
	 , DCMNT_CD
	 , FILE_PATH
	 , STRG_FILE_NM
	 , FILE_NM
	 , NULL			AS FILE_SZ
	 , NULL			AS PRCS_STTS_CD
	 , NULL			AS PRCS_CN
	 , 'N'			AS DEL_YN
	 , '100000'		AS RGTR_NO
	 , now()		AS REG_YMD
  FROM (
		SELECT 1									AS GBN
			 , E.DATA_SEQ							AS DATA_SEQ
			 , 'TS005'																	AS TASK_SE_CD
			 , 'EP'||SUBSTR(D.YEAR_NM, 3)||LPAD(COALESCE(E.DATA_SEQ,'1')::TEXT,6,'0')   AS EVNT_PARTCPTN_NO	 
			 , NULL									AS DTL_DOC_NO
	  		 , '01'									AS FILE_SE_CD
			 , NULL									AS DCMNT_CD
			 , '/inveseum/oper'||SUBSTR(E.FILE_NAME, 1, 9)								AS FILE_PATH
			 , SPLIT_PART(E.FILE_NAME, '/', 3)											AS STRG_FILE_NM
			 , E.ORIGINAL_NAME															AS FILE_NM
		  FROM (
				SELECT A.CATEGORY_SEQ
					 , B.YEAR_SEQ
					 , B.YEAR_NAME 		AS YEAR_NM
				  FROM ZTB_OPER_CATEGORY A
					 , ZTB_OPER_YEAR B
				 WHERE A.YEAR_SEQ = B.YEAR_SEQ
				) D
			  , ZTB_OPER_DATA E
		  WHERE D.CATEGORY_SEQ 	= E.CATEGORY_SEQ
			AND D.YEAR_SEQ 		= E.YEAR_SEQ 

		UNION ALL

		SELECT 0									AS GBN
			 , E.DATA_SEQ							AS DATA_SEQ
			 , 'TS005'																	AS TASK_SE_CD
			 , 'EP'||SUBSTR(D.YEAR_NM, 3)||LPAD(COALESCE(E.DATA_SEQ,'1')::TEXT,6,'0')   AS EVNT_PARTCPTN_NO	 
			 , NULL									AS DTL_DOC_NO
	  		 , '00'									AS FILE_SE_CD
			 , NULL									AS DCMNT_CD
			 , '/inveseum/oper'||SUBSTR(E.FILE_NAME2, 1, 9)								AS FILE_PATH
			 , SPLIT_PART(E.FILE_NAME2, '/', 3)											AS STRG_FILE_NM
			 , E.ORIGINAL_NAME2															AS FILE_NM
		  FROM (
				SELECT A.CATEGORY_SEQ
					 , B.YEAR_SEQ
					 , B.YEAR_NAME 		AS YEAR_NM
				  FROM ZTB_OPER_CATEGORY A
					 , ZTB_OPER_YEAR B
				 WHERE A.YEAR_SEQ = B.YEAR_SEQ
				) D
			  , ZTB_OPER_DATA E
		  WHERE D.CATEGORY_SEQ 	= E.CATEGORY_SEQ
			AND D.YEAR_SEQ 		= E.YEAR_SEQ   
       ) F

COMMIT;
	
/**
	투자설명회 - 사업자번호 업데이트
		1. 사업자번호 임시테이블에 먼저 등록 (가온누리_투자설명회_경영체_사업자번호_추가.xlsx) 
*/
UPDATE TB_EVENT_ENT A
   SET BRNO =  (SELECT BRNO
				  FROM (
						SELECT 'EP'||SUBSTR(D.YEAR_NM, 3)||LPAD(COALESCE(E.DATA_SEQ,'1')::TEXT,6,'0')   AS EVNT_PARTCPTN_NO
							 , F.DATA_SEQ
							 , F.BRNO
						  FROM (
								SELECT A.CATEGORY_SEQ
									 , B.YEAR_SEQ
									 , B.YEAR_NAME							AS YEAR_NM
								  FROM ZTB_OPER_CATEGORY A
									 , ZTB_OPER_YEAR B
								 WHERE A.YEAR_SEQ = B.YEAR_SEQ
								) D
							  , ZTB_OPER_DATA E
							  , ZTB_OPER_DATA_BRNO F
						  WHERE D.CATEGORY_SEQ 	= E.CATEGORY_SEQ
							AND D.YEAR_SEQ 		= E.YEAR_SEQ 
							AND E.DATA_SEQ		= F.DATA_SEQ
					   ) G
				 WHERE G.EVNT_PARTCPTN_NO = A.EVNT_PARTCPTN_NO);

COMMIT;
