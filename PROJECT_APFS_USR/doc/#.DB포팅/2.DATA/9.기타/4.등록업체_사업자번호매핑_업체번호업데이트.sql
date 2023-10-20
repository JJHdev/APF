/**
 * 대상 테이블
 * 1. TB_EVENT_ENT
 * 2. TB_FUND_INVSTR
 * 3. TB_INVTSPRT_APLY
 * 
 */


-- 펀드투자자
UPDATE TB_FUND_INVSTR A
   SET BZENTY_NO = (SELECT BZENTY_NO
				      FROM TB_ENT
				     WHERE BRNO = A.BRNO
				       AND BZENTY_SE_CD = '10')
	 , MDFR_NO = '100000'
	 , MDFCN_YMD = now()
 WHERE BZENTY_NO IS NULL
   AND BRNO IS NOT NULL
   AND EXISTS (SELECT 1
			     FROM TB_ENT
			    WHERE BRNO = A.BRNO
			      AND BZENTY_SE_CD = '10')
;

-- 투자자계정 생성해서 투자자만 우선 매핑


-- 투자지원신청
UPDATE TB_INVTSPRT_APLY A
   SET APLY_BZENTY_NO = (SELECT BZENTY_NO
				           FROM TB_ENT
				          WHERE BRNO = A.BRNO
				            AND BZENTY_SE_CD = '20')
 WHERE APLY_BZENTY_NO IS NULL
   AND BRNO IS NOT NULL
   AND EXISTS (SELECT 1
			     FROM TB_ENT
			    WHERE BRNO = A.BRNO
			      AND BZENTY_SE_CD = '20')   
;

-- 행사참여경영체
UPDATE TB_EVENT_ENT A
   SET BZENTY_NO = (SELECT BZENTY_NO
				      FROM TB_ENT
				     WHERE BRNO = A.BRNO
				       AND BZENTY_SE_CD = '20')
 WHERE BZENTY_NO IS NULL
   AND BRNO IS NOT NULL
   AND EXISTS (SELECT 1
			     FROM TB_ENT
			    WHERE BRNO = A.BRNO
			      AND BZENTY_SE_CD = '20')      
;

