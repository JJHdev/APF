/**
	투자신청정보 등록
	2023-06-07
*/
INSERT INTO TB_INVTSPRT_APLY (
	  SPRT_APLY_NO
	, SPRT_APLY_SE_CD
	, PRGRM_NO
	, APLY_BZENTY_NO
	, PRVC_CLCT_AGRE_YN
	, UNITY_MNG_SYS_AGRE_YN
	, BZENTY_NM
	, BRNO
	, FNDN_YMD
	, RPRSV_NM
	, EMP_CNT
	, CRNO
	, BRDT
	, SEXDSTN
	, LCTN_ADDR
	, LCTN_DADDR
	, RPRS_TELNO
	, FXNO
	, BZENTY_TYPE_CD
	, BIZ_FLD
	, HMPG_ADDR
	, INVT_HOPE_AMT
	--, MAIN_BIZ_CN
	--, CORE_ITM_CN
	, BIZ_CN
	, PIC_NM
	, PIC_DEPT_NM
	, PIC_TELNO
	, PIC_EML_ADDR
	, FUND_LINK_URL
	--, RCPT_YMD
	--, PRCS_YMD
	--, RMRK
	, PRGRS_STTS_CD
	, PRCS_RSLT_CN
	, DEL_YN
	, RGTR_NO
	, REG_YMD
)
SELECT AA.SPRT_APLY_NO
	 , AA.SPRT_APLY_SE_CD
	 , AA.PRGRM_NO
	 , AA.APLY_BZENTY_NO
	 , AA.PRVC_CLCT_AGRE_YN
	 , AA.UNITY_MNG_SYS_AGRE_YN
	 , AA.BZENTY_NM
	 , AA.BRNO
	 , AA.FNDN_YMD
	 , AA.RPRSV_NM
	 , AA.EMP_CNT
	 , AA.CRNO
	 , AA.BRDT
	 , AA.SEXDSTN
	 , AA.LCTN_ADDR
	 , AA.LCTN_DADDR
	 , AA.RPRS_TELNO
	 , AA.FXNO
	 , AA.BZENTY_TYPE_CD
	 , BB.BIZ_FLD
	 , AA.HMPG_ADDR
	 , AA.INVT_HOPE_AMT
	 , AA.BIZ_CN
	 , AA.PIC_NM
	 , AA.PIC_DEPT_NM
	 , AA.PIC_TELNO
	 , AA.PIC_EML_ADDR
	 , AA.FUND_LINK_URL
	 , AA.PRGRS_STTS_CD
	 , AA.PRCS_RSLT_CN
	 , AA.DEL_YN
	 , AA.RGTR_NO
	 , AA.REG_YMD
  FROM (
		SELECT SEQ
	         , 'S'||SUBSTR(CREATE_DATE, 3,2)||LPAD(COALESCE(SEQ::TEXT,'1'),7,'0')	AS SPRT_APLY_NO
			 , CASE WHEN GB = '2' THEN 'SB'
	  			    WHEN GB = '3' THEN 'SA'
	  			    ELSE 'SC'
	 		   END												AS SPRT_APLY_SE_CD
	         , CASE WHEN GB = '4' THEN 'SC014'
	  				WHEN GB = '5' THEN 'SC015'
	  				WHEN GB = '6' THEN 'SC016'
	  		   END												AS PRGRM_NO
	  		 , NULL												AS APLY_BZENTY_NO				-- *** 업체번호 (추후 사업자번호로 매핑)
			 , 'Y'												AS PRVC_CLCT_AGRE_YN
	  		 , 'Y'												AS UNITY_MNG_SYS_AGRE_YN	  
			 , COM_NAME											AS BZENTY_NM
			 , SUBSTR(FN_FMT_DATA('NUM', COM_LICENSE_NUM), 1, 10)	AS BRNO
	  		 , FN_FMT_DATA('NUM', COM_YEAR)	  					AS FNDN_YMD
			 , COM_CEO											AS RPRSV_NM
			 , CASE WHEN FN_FMT_DATA('NUM', COM_STAFF) = '' THEN NULL
	  				ELSE FN_FMT_DATA('NUM', COM_STAFF)
	  		   END::INTEGER										AS EMP_CNT
			 , SUBSTR(FN_FMT_DATA('NUM', COM_REGISTER_NUM), 1, 13)	AS CRNO
	  		 , CASE WHEN COM_BIRTHDAY <> '' THEN 
					SPLIT_PART(COM_BIRTHDAY, '-', 1)
				   ||LPAD(SPLIT_PART(COM_BIRTHDAY, '-', 2),2,'0')
				   ||LPAD(SPLIT_PART(COM_BIRTHDAY, '-', 3),2,'0')
	  		   END 												AS BRDT
			 , COM_SEX											AS SEXDSTN
			 , COM_ADDRESS1										AS LCTN_ADDR
			 , COM_ADDRESS2										AS LCTN_DADDR
	  		 , SUBSTR(FN_FMT_DATA('NUM', COM_TEL), 1, 11)		AS RPRS_TELNO
			 , FN_FMT_DATA('NUM', COM_FAX)						AS FXNO
			 , CASE WHEN BIZ_KIND = '농어업법인' OR BIZ_KIND = '일반법인' 
					THEN '1'
					WHEN BIZ_KIND = '개인' AND NULLIF(TRIM(COM_LICENSE_NUM),'') IS NOT NULL
					THEN '2'
					WHEN BIZ_KIND = '개인' AND NULLIF(TRIM(COM_LICENSE_NUM),'') IS NULL
					THEN '3'
			   END												AS BZENTY_TYPE_CD		-- 1:법인기업, 2:개인기업, 3:개인회원
			 , BIZ_WEBSITE										AS HMPG_ADDR
			 , CASE WHEN FN_FMT_DATA('NUM', BIZ_HOPE_AMOUNT) = '' THEN NULL
	  				ELSE FN_FMT_DATA('NUM', BIZ_HOPE_AMOUNT)
	  		   END::INTEGER										AS INVT_HOPE_AMT
	  		 , BIZ_CONTENTS										AS BIZ_CN
			 , CHARGER_NAME										AS PIC_NM
			 , CHARGER_DEPT										AS PIC_DEPT_NM
			 , SUBSTR(FN_FMT_DATA('NUM', CHARGER_TEL), 1, 11)   AS PIC_TELNO
			 , CHARGER_EMAIL									AS PIC_EML_ADDR
			 , APP_URL											AS FUND_LINK_URL
	  		 , CASE WHEN GB = '2' AND STATUS = '10' THEN 'B10'
	  				WHEN GB = '2' AND STATUS = '20' THEN 'B20'
	  				WHEN GB = '3' AND STATUS = '10' THEN 'A10'
	  				WHEN GB = '3' AND STATUS = '20' THEN 'A40'
	  				WHEN GB IN ('4','5','6') AND STATUS = '10' THEN 'C10'
	  				WHEN GB IN ('4','5','6') AND STATUS = '20' THEN 'C40'
	  		   END												AS PRGRS_STTS_CD
			 , ANSWER_CONTENTS									AS PRCS_RSLT_CN
	  		 , 'N'												AS DEL_YN
	  		 , '100000'											AS RGTR_NO
			 , CAST(CREATE_DATE 	AS timestamp)				AS REG_YMD
		  FROM ZTB_REQUEST
	  
		 WHERE 0 = 0
		   AND GB <> '1'  
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

 
 
/**
	투자조합 등록	
	2023-06-07
*/
INSERT INTO TB_INVT_MXTR (
	  SN
	, SPRT_APLY_NO
	, INVT_MXTR_NM
	, INVT_YMD
	, INVSTR_NM
	, INVT_AMT
	, RGTR_NO
	, REG_YMD
)  
SELECT (ROW_NUMBER() OVER(ORDER BY SPRT_APLY_NO, LPAD(AGRIFOOD_YEAR, 4, '20')	))		AS RNUM
	 , SPRT_APLY_NO
	 , AGRIFOOD_NM							AS INVT_MXTR_NM
	 , LPAD(AGRIFOOD_YEAR, 4, '20')			AS INVT_YMD
	 , AGRIFOOD_OP							AS INVSTR_NM
	 , AGRIFOOD_AMNT::numeric				AS INVT_AMT
	 , '100000'								AS RGTR_NO
	 ,  CAST(CREATE_DATE 	AS timestamp)	AS REG_YMD
  FROM (
		SELECT 'S'||SUBSTR(CREATE_DATE, 3,2)||LPAD(COALESCE(SEQ::TEXT,'1'),7,'0')	AS SPRT_APLY_NO
			 , JSONB_ARRAY_ELEMENTS(AGRIFOOD_UNION::jsonb) ->> 'nm' 	AS AGRIFOOD_NM
			 , JSONB_ARRAY_ELEMENTS(AGRIFOOD_UNION::jsonb) ->> 'year' 	AS AGRIFOOD_YEAR
			 , JSONB_ARRAY_ELEMENTS(AGRIFOOD_UNION::jsonb) ->> 'op' 	AS AGRIFOOD_OP
			 , JSONB_ARRAY_ELEMENTS(AGRIFOOD_UNION::jsonb) ->> 'amnt' 	AS AGRIFOOD_AMNT
	  		 , CREATE_DATE
		  FROM ZTB_REQUEST  
		 WHERE 0 = 0
		   AND AGRIFOOD_UNION <> '' 
		   AND AGRIFOOD_UNION IS NOT NULL
		 ORDER BY SEQ  
	    ) A

