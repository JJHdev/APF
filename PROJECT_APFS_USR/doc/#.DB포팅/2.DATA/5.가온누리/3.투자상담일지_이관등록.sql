/**
	모태펀드 상담일지
	2023-06-09
*/
INSERT INTO TB_DSCSN_DIARY (
	  SN
	, DSCSN_CNTR_CD
	, DSCSN_YMD
	, DSCSN_MTHD_CD
	, CNSLR_NM
	, BZENTY_NM
	, BRNO
	, RPRSV_NM
	, BRDT
	, SEXDSTN
	, PIC_NM
	, PIC_DEPT_NM
	, PIC_TELNO
	, PIC_EML_ADDR
	, FNDN_YMD
	, EMP_CNT
	, BZENTY_TYPE_CD
	, BIZ_FLD
	, INVT_FLD_NM
	, TPBIZ_SE_NM
	, INDUST_SE_NM
	, SLS_AMT
	, HMPG_ADDR
	, LCTN_ADDR1
	, LCTN_ADDR2
	, BIZ_CN
	, DSCSN_CN
	, PRGRS_STTS_CD
	, RGTR_NO
	, REG_YMD
)
SELECT AA.SEQ
	 , NULL					AS DSCSN_CNTR_CD
	 , AA.DSCSN_YMD
	 , AA.DSCSN_MTHD_CD
	 , NULL					AS CNSLR_NM
	 , AA.BZENTY_NM
	 , AA.BRNO
	 , AA.RPRSV_NM
	 , AA.BRDT
	 , AA.SEXDSTN
	 , AA.PIC_NM
	 , AA.PIC_DEPT_NM
	 , AA.PIC_TELNO
	 , AA.PIC_EML_ADDR
	 , AA.FNDN_YMD
	 , AA.EMP_CNT
	 , AA.BZENTY_TYPE_CD
	 , BB.BIZ_FLD
	 , NULL					AS INVT_FLD_NM
	 , NULL					AS TPBIZ_SE_NM
	 , NULL					AS INDUST_SE_NM
	 , NULL					AS SLS_AMT
	 , AA.HMPG_ADDR
	 , AA.LCTN_ADDR1
	 , NULL					AS LCTN_ADDR2
	 , AA.BIZ_CN
	 , AA.DSCSN_CN
	 , AA.PRGRS_STTS_CD
	 , AA.RGTR_NO
	 , AA.REG_YMD
  FROM (
		SELECT SEQ
	  		 , FN_FMT_DATA('NUM', SUBSTR(ANSWER_DATE, 1, 10))	AS DSCSN_YMD
	  		 , CASE WHEN APP_METHOD = '유선상담' THEN '10'
	  				WHEN APP_METHOD = '방문상담' THEN '20'
	  		   END												AS DSCSN_MTHD_CD
			 , COM_NAME											AS BZENTY_NM
			 , SUBSTR(FN_FMT_DATA('NUM', COM_LICENSE_NUM), 1, 10)				AS BRNO
	  		 , COM_CEO											AS RPRSV_NM
	  		 , CASE WHEN COM_BIRTHDAY <> '' THEN 
					SPLIT_PART(COM_BIRTHDAY, '-', 1)
				   ||LPAD(SPLIT_PART(COM_BIRTHDAY, '-', 2),2,'0')
				   ||LPAD(SPLIT_PART(COM_BIRTHDAY, '-', 3),2,'0')
	  		   END 												AS BRDT	  
	  		 , COM_SEX											AS SEXDSTN
			 , CHARGER_NAME										AS PIC_NM
			 , CHARGER_DEPT										AS PIC_DEPT_NM
			 , SUBSTR(FN_FMT_DATA('NUM', CHARGER_TEL), 1, 11)   AS PIC_TELNO
			 , CHARGER_EMAIL									AS PIC_EML_ADDR
	   		 , FN_FMT_DATA('NUM', SUBSTR(COM_YEAR, 1, 4))		AS FNDN_YMD
			 , CASE WHEN FN_FMT_DATA('NUM', COM_STAFF) = '' THEN NULL
	  				ELSE FN_FMT_DATA('NUM', COM_STAFF)
	  		   END::INTEGER										AS EMP_CNT
			 , CASE WHEN BIZ_KIND = '농어업법인' OR BIZ_KIND = '일반법인' 
					THEN '1'
					WHEN BIZ_KIND = '개인' AND NULLIF(TRIM(COM_LICENSE_NUM),'') IS NOT NULL
					THEN '2'
					WHEN BIZ_KIND = '개인' AND NULLIF(TRIM(COM_LICENSE_NUM),'') IS NULL
					THEN '3'
			   END												AS BZENTY_TYPE_CD		-- 1:법인기업, 2:개인기업, 3:개인회원	  
	  		 , BIZ_WEBSITE										AS HMPG_ADDR
			 , COM_ADDRESS1||' '||COM_ADDRESS2					AS LCTN_ADDR1
	  		 , BIZ_CONTENTS										AS BIZ_CN
			 , APP_CONTENTS										AS DSCSN_CN
	  		 , CASE WHEN STATUS = '11' THEN 'D10'
	  				WHEN STATUS = '21' THEN 'D20'
	  		   END												AS PRGRS_STTS_CD
	  		 , '100000'											AS RGTR_NO
			 , CAST(CREATE_DATE 	AS timestamp)				AS REG_YMD
		  FROM ZTB_REQUEST
	     WHERE 0 = 0
		   AND GB = '1'  
       ) AA
	   LEFT JOIN 
	   (
		-- *** 사업유형 (매핑데이터 생성 후 데이터 등록)
		SELECT SEQ
			 , array_to_string(array_agg(distinct BTYPE order by BTYPE), ',')  AS BIZ_FLD
		  FROM (
				SELECT distinct SEQ
					 , unnest(string_to_array(trim(regexp_replace(BIZ_TYPE_STR, '(,)+', ',', 'g'), ','), ',')) AS BTYPE
				  FROM (
						SELECT SEQ
							 , trim(regexp_replace(array_to_string(array_agg(distinct BIZ_TYPE order by BIZ_TYPE), ''), '(,)+', ',', 'g'), ',') AS BIZ_TYPE_STR
						  FROM (
								SELECT A.SEQ
									 , B.BIZ_TYPE
								  FROM (
										SELECT SEQ
											 , JSONB_ARRAY_ELEMENTS(BIZ_TYPE::jsonb) ->> 'type' AS BIZ_TYPE_NM
										  FROM ZTB_REQUEST
										 WHERE 0 = 0
										   AND BIZ_TYPE IS NOT NULL
										   AND BIZ_TYPE <> ''	
										 ORDER BY SEQ		  	   
									   ) A
									 , ZTB_REQUEST_BIZ_TYPE B
								 WHERE A.BIZ_TYPE_NM = B.BIZ_TYPE_NM
							   ) C
						 GROUP BY SEQ  	  
					   ) D	  
			   ) E
		 GROUP BY SEQ  	   
	   ) BB 
	   ON AA.SEQ = BB.SEQ
 WHERE 0 = 0
   --AND AA.SEQ >= '2701'
   --AND AA.SEQ <= '2700'
 ORDER BY AA.SEQ