CREATE OR REPLACE PROCEDURE SP_BATCH_KODATA (
	/**
	* ------------------------------------------------------------------------------------------------------
	* Name			: Kodata 리얼 테이블로 이관 (실행 : CALL SP_BATCH_KODATA('B', null, '0', null);)
	* User			: ntarget
	* Date			: 2023.06.26
	* Description	: 	1. 경영체정보			[ IF_50D1	-> TB_ENT ]
	*						: 회원가입 시 경영체 정보는 기 등록되어 있는 상태에서 연계정보 UPDATE
	*				  	2. 대표자정보			[ IF_50D2	-> TB_ENT ]
	*						: 회원가입 시 경영체 정보는 기 등록되어 있는 상태에서 연계정보 UPDATE	
	*				  	3. 재무제표정보		   [ IF_5058   -> TB_ENT_FNNR_KO] [ DELETE, INSERT ]
	*						: (KEDCD, ACCT_DT) 로 전체 삭제 후 전체 등록
	*					4. 특허및실용신안 정보  	 [ IF_50TH	 -> TB_ENT_PTNT_KO] [ DELETE, INSERT ]
	*						: (KODATACD, BASE_DT) 로 전체 삭제 후 전체 등록
	*					5. 디자인및상표권 정보  	 [ IF_50TL	 -> TB_ENT_PTNT_KO] [ DELETE, INSERT ]
	*						: (KODATACD, BASE_DT) 로 전체 삭제 후 전체 등록
	*
	*					* 연계 대상 데이터 등록 완료 후 연계테이블 상태 변경
	*						- IF_50D1
	*						- IF_50D2
	*						- IF_5058
	*						- IF_50TH
	*						- IF_50TL
	* -----------------------------------------------------------------------------------------------------
	* @Param	p_mode		: 프로시저 실행 모드
    *						  1. B : 배치등록
	*						  2. R : 수시등록 (사업자번호로 실행 할 경우)
	* @Param	p_bzno		: 사업자등록번호 (모드가 R인 경우) 
	*	
	*/
	p_mode			IN text,
	p_bzno			IN text,
	o_err_code		INOUT text,
	o_err_msg		INOUT text
)
LANGUAGE PLPGSQL
AS $PROCEDURE$
DECLARE 
	v_rowcount		integer		:= 0;
	v_inst_cnt		integer		:= 0;
	v_updt_cnt		integer		:= 0;
	v_updt_if_cnt	integer		:= 0;
	
	/* Totcount Val */
	v_50D1_inst_cnt	integer		:= 0;
	v_50D1_updt_cnt	integer		:= 0;
	v_50D2_inst_cnt	integer		:= 0;
	v_50D2_updt_cnt	integer		:= 0;
	v_5058_inst_cnt	integer		:= 0;
	v_5058_delt_cnt	integer		:= 0;
	v_50TH_inst_cnt	integer		:= 0;
	v_50TH_delt_cnt	integer		:= 0;
	v_50TL_inst_cnt	integer		:= 0;
	v_50TL_delt_cnt	integer		:= 0;
	
	v_user_id		text 		:= 'batch';
	v_rtn_seq		integer;
	
	v_procnm		text;
	v_batch_code	text;
	curr_time		time		:= now();
	
	v_conv_date		text;
	
	REC_50D1		RECORD;		/* 경영체정보 RECORD */
	REC_50D2		RECORD;		/* 대표자정보 RECORD */
	
	CUR_50D1	cursor for
		SELECT /* 경영체 정보 */
			   A.SN											AS SN				/* 일련번호 */
			 , A.KEDCD										AS KEDCD			/* KED코드 */
			 , A.BZNO										AS BZNO				/* 사업자번호 */
			 , A.CONO_PID									AS CONO_PID			/* 법인등록번호 */
			 , A.ENP_NM										AS ENP_NM			/* 업체명 */	
			 , A.ESTB_DT									AS ESTB_DT			/* 설립일자 */
			 , A.TEL_NO										AS TEL_NO			/* 대표전화번호 */
			 , A.FAX_NO										AS FAX_NO			/* 팩스번호 */
			 , A.BZC_CD										AS BZC_CD 			/* 업종코드 (표준산업분류 10차) */
			 , A.ENP_TYP									AS ENP_TYP			/* 업체유형 */
			 , A.ENP_FCD									AS ENP_FCD			/* 업체형태 */
			 , A.ENP_SZE									AS ENP_SZE			/* 업체규모 */
			 , A.EMAIL										AS EMAIL			/* 이메일주소 */
			 , A.HPAGE_URL									AS HPAGE_URL		/* 홈페이지주소 */
			 , A.LOC_RDNM_ZIP								AS LOC_RDNM_ZIP		/* 우편번호 */
			 , A.LOC_RDNM_ADDRA								AS LOC_RDNM_ADDRA	/* 소재지주소 */
			 , A.LOC_RDNM_ADDRB								AS LOC_RDNM_ADDRB	/* 소재지상세주소 */
			 , A.LABORER_SUM								AS LABORER_SUM		/* 직원수 */
		  FROM (
                SELECT *,
                       ROW_NUMBER() OVER(PARTITION BY KEDCD ORDER BY STD_DT DESC) as IDX
	              FROM IF_50D1		  
		       ) A
         WHERE 0 = 0
		   AND IDX			= 1
		   AND A.LINK_STUS 	= 'N'	
		   --AND A.CONO_PID	= '1101110'
		   AND A.BZNO 		= COALESCE(p_bzno, A.BZNO)
		;
	
	CUR_50D2	cursor for
		SELECT /* 대표자 정보 */
			   A.SN											AS SN				/* 일련번호 */
			 , A.KEDCD										AS KEDCD			/* KED코드 */
			 , A.NAME										AS RPRSV_NM			/* 대표자명 */
			 , A.PID										AS PID				/* 대표자생년월일 */
			 , B.BZNO										AS BZNO				/* 사업자번호 */
			 , B.CONO_PID								    AS CONO_PID			/* 법인등록번호 */
		  FROM IF_50D2 A
		     , IF_50D1 B
         WHERE 0 = 0
		   AND A.KEDCD      = B.KEDCD
		   AND A.LINK_STUS 	= 'N'
		   AND B.BZNO 		= COALESCE(p_bzno, B.BZNO)
		;
				
BEGIN
	v_procnm		:= 'Migration KODATA To Real Tables.';
	v_batch_code	:= 'BAT01';

	-- 실행모드 체크
    IF (p_mode NOT IN ('B','R')) THEN
		o_err_code	:= '10001';
		o_err_msg	:= p_mode||' : [p_mode] 파라메터가 일치 하지 않습니다.';
		
        RAISE SQLSTATE '90001';
    END IF;

	-- Null 처리
	IF p_bzno = '' THEN
		p_bzno		:= null;	
	END IF;
	
	-- R인경우 사업자번호 유무 체크
	IF (p_mode = 'R' AND p_bzno IS NULL) THEN
		o_err_code	:= '10002';
		o_err_msg	:= p_mode||', '||p_bzno||' : p_mode가 R 인 경우 사업자등록번호가 필수 값 입니다.' ;
		
        RAISE SQLSTATE '90001';	
	END IF;

	-- R인경우 사업자번호 유효성 체크
	IF (p_mode = 'R' AND p_bzno IS NOT NULL)  THEN
		IF LEGNTH(p_bizno) <> 10 THEN 
			o_err_code	:= '10003';
			o_err_msg	:= p_mode||', '||p_bzno||' : 사업자등록번호 확인 필요.' ;

			RAISE SQLSTATE '90001';	
		END IF;
	END IF;
		
	-- 배치로그 등록 (배치시작)
	v_rtn_seq := NULL;
	CALL SP_BATCH_LOG('S', v_user_id, v_batch_code, '0', NULL, v_rtn_seq);
	
	RAISE NOTICE '배치번호 = %',	  v_rtn_seq;
	
	
	/**
	*	#. 경영체 정보 등록 (Only Update)
	*/
	RAISE NOTICE '경영체정보 등록 시작 = %',	  curr_time;
	OPEN CUR_50D1;	
	LOOP
		FETCH CUR_50D1 INTO REC_50D1;
			EXIT WHEN NOT FOUND; 
			
			/* 
			*	UPDATE (TB_ENT)
			*	업체정보는 신규등록은 회원가입시 받고 UPDATE만 가능
			*/
			UPDATE TB_ENT
			   SET CRNO				= TRIM(REC_50D1.CONO_PID)
			     , BZENTY_NM		= TRIM(REC_50D1.ENP_NM)
				 , FNDN_YMD			= TRIM(REC_50D1.ESTB_DT)
				 , RPRS_TELNO		= FN_FMT_DATA('NUM', TRIM(REC_50D1.TEL_NO))
				 , FXNO				= FN_FMT_DATA('NUM', TRIM(REC_50D1.FAX_NO))
				 , TPBIZ_CD			= TRIM(REC_50D1.BZC_CD)
				 , BZENTY_TYPE_CD	= TRIM(REC_50D1.ENP_TYP)
				 , BZENTY_STLE_CD	= TRIM(REC_50D1.ENP_FCD)
				 , BZENTY_SCALE_CD	= TRIM(REC_50D1.ENP_SZE)
				 , EML_ADDR			= TRIM(REC_50D1.EMAIL)
				 , HMPG_ADDR		= TRIM(REC_50D1.HPAGE_URL)
				 , ZIP				= TRIM(REC_50D1.LOC_RDNM_ZIP)
				 , LCTN_ADDR		= TRIM(REC_50D1.LOC_RDNM_ADDRA)
				 , LCTN_DADDR		= TRIM(REC_50D1.LOC_RDNM_ADDRB)
				 , EMP_CNT			= TRIM(REC_50D1.LABORER_SUM)::NUMERIC
				 , KD_CD			= TRIM(REC_50D1.KEDCD)
				 , MDFR_NO			= v_user_id
				 , MDFCN_YMD		= now()
			 WHERE BRNO 	= TRIM(REC_50D1.BZNO)
			;
			 
			GET DIAGNOSTICS v_updt_cnt := ROW_COUNT;
		
			RAISE NOTICE 'TB_ENT Update Count %',		v_updt_cnt ;
					
			IF v_updt_cnt > 0 	THEN
				v_50D1_updt_cnt 	:= v_50D1_updt_cnt + 1;
			ELSE
				-- INSERT
				INSERT INTO TB_ENT (
					  BZENTY_NO
					, BZENTY_SE_CD
					, BRNO
					, CRNO
					, BZENTY_NM
					, FNDN_YMD
					, RPRS_TELNO
					, FXNO
					, TPBIZ_CD
					, BZENTY_TYPE_CD
					, BZENTY_STLE_CD
					, BZENTY_SCALE_CD
					, EML_ADDR
					, HMPG_ADDR
					, ZIP
					, LCTN_ADDR
					, LCTN_DADDR
					, EMP_CNT
					, KD_CD
					, USE_STTS_CD
					, RGTR_NO
					, REG_YMD
				) VALUES (
					  NEXTVAL('SEQ_ENT')::text
					, '20'
					, TRIM(REC_50D1.BZNO)
					, TRIM(REC_50D1.CONO_PID)
					, TRIM(REC_50D1.ENP_NM)
					, TRIM(REC_50D1.ESTB_DT)
					, FN_FMT_DATA('NUM', TRIM(REC_50D1.TEL_NO))
					, FN_FMT_DATA('NUM', TRIM(REC_50D1.FAX_NO))
					, TRIM(REC_50D1.BZC_CD)
					, TRIM(REC_50D1.ENP_TYP)
					, TRIM(REC_50D1.ENP_FCD)
					, TRIM(REC_50D1.ENP_SZE)
					, TRIM(REC_50D1.EMAIL)
					, TRIM(REC_50D1.HPAGE_URL)
					, TRIM(REC_50D1.LOC_RDNM_ZIP)
					, TRIM(REC_50D1.LOC_RDNM_ADDRA)
					, TRIM(REC_50D1.LOC_RDNM_ADDRB)
					, TRIM(REC_50D1.LABORER_SUM)::NUMERIC
					, TRIM(REC_50D1.KEDCD)
					, '1'
					, v_user_id
					, now()
				)
				;		
			
				GET DIAGNOSTICS v_inst_cnt := ROW_COUNT;
				
				IF v_inst_cnt > 0 	THEN
					v_50D1_inst_cnt 	:= v_50D1_inst_cnt + 1;				
				END IF;
			END IF;
			
			-- 인터페이스 테이블 상태 업데이트
			IF v_updt_cnt > 0 	OR 	v_inst_cnt > 0	THEN
				RAISE NOTICE 'IF_50D1 Stus UPDATE %',		REC_50D1.SN ;
				/* 인터페이스 테이블 (IF_50D1) */
				UPDATE IF_50D1
				   SET LINK_STUS	= '-'
				 WHERE SN			= REC_50D1.SN
				;			
			END IF;
			
			
			/*************************************************************
			-- 업체 IR정보 등록 (Update, Insert)
			**************************************************************/
			UPDATE TB_ENT_IR
			   SET PRGRS_STTS_CD	= '10'
			   	 , MDFR_NO			= v_user_id
			     , MDFCN_YMD		= now()
			 WHERE IR_NO = (
			 				SELECT MAX(A.IR_NO)
				              FROM TB_ENT_IR A
				                 , TB_ENT B
							 WHERE A.BZENTY_NO	= B.BZENTY_NO
				 			   AND B.BRNO		= TRIM(REC_50D1.BZNO)
			 			   )
			;
			 
			GET DIAGNOSTICS v_updt_cnt := ROW_COUNT;
				
			IF v_updt_cnt = 0 	THEN
				INSERT INTO TB_ENT_IR (
					  IR_NO
					, BZENTY_NO
					, RLS_YN
					, PRGRS_STTS_CD
					, RGTR_NO
					, REG_YMD
				) VALUES (
					  'I'||TO_CHAR(now(), 'YY')||LPAD(NEXTVAL('SEQ_ENT_IR')::text, 7, '0')
					, (SELECT MAX(BZENTY_NO)
					     FROM TB_ENT
					    WHERE BRNO		= TRIM(REC_50D1.BZNO))
					, 'Y'
					, '10'
					, v_user_id
					, now()
				)
				;
			END IF;
			
			GET DIAGNOSTICS v_inst_cnt := ROW_COUNT;			
	END LOOP;
	CLOSE CUR_50D1;
	
	/**
	*	#. 대표자 정보 등록 (Only Update)
	*/	
	RAISE NOTICE '대표자정보 등록 시작 = %',	  curr_time;
	OPEN CUR_50D2;
	LOOP
		FETCH CUR_50D2 INTO REC_50D2;
			EXIT WHEN NOT FOUND; 
			
			-- 생년월일 날짜 Conv.
			v_conv_date	:= FN_FMT_DATA('NUM', TRIM(REC_50D2.PID));
			
			IF v_conv_date = '' OR v_conv_date IS NULL THEN
				v_conv_date	:= NULL;
			ELSIF LENGTH(v_conv_date) <> 8 THEN
				IF LENGTH(v_conv_date) = 7 THEN
					v_conv_date = SUBSTR(v_conv_date, 1, 6);
				END IF;

				IF SUBSTR(v_conv_date, 1, 2)::integer < 20 		AND LENGTH(v_conv_date) = 6 THEN
					v_conv_date	:= '20'||v_conv_date;
				ELSIF SUBSTR(v_conv_date, 1, 2)::integer >= 20 	AND LENGTH(v_conv_date) = 6 THEN
					v_conv_date	:= '19'||v_conv_date;
				END IF;
			END IF;
			
			/* 
			*	UPDATE (TB_ENT)	- 대표자정보
			*/
			UPDATE TB_ENT
			   SET RPRSV_NM			= TRIM(REC_50D2.RPRSV_NM)
				 , RPRSV_BRDT		= v_conv_date
				 , MDFR_NO			= v_user_id
				 , MDFCN_YMD		= now()
			 WHERE KD_CD 			= TRIM(REC_50D2.KEDCD)
			;
			 
			GET DIAGNOSTICS v_updt_cnt := ROW_COUNT;
		
			RAISE NOTICE 'TB_ENT(50D2) Update Count %',		v_updt_cnt ;
						
			IF v_updt_cnt > 0 	THEN
				v_50D2_updt_cnt 	:= v_50D2_updt_cnt + 1;
			END IF;

			-- 인터페이스테이블 상태 업데이트
			IF v_updt_cnt> 0 	OR 	v_inst_cnt > 0	THEN
				RAISE NOTICE 'IF_50D2 Stus UPDATE %',		REC_50D2.SN ;
				/* 인터페이스 테이블 (IF_50D2) 상태값 업데이트 */
				UPDATE IF_50D2
				   SET LINK_STUS	= '-'
				 WHERE SN			= REC_50D2.SN
				;			
			END IF;
			
	END LOOP;
	CLOSE CUR_50D2;
	
	
	/**
	*	#. 재무제표 정보 등록 (Update, Insert)
	*/	
	RAISE NOTICE '재무제표정보 등록 시작 = %',	  curr_time;
	
		-- 대상 데이터 삭제
		DELETE FROM TB_ENT_FNNR
		WHERE (IR_NO, FNNR_YMD) IN (
									SELECT A.IR_NO
										 , C.ACCT_DT
									  FROM TB_ENT_IR A
										 , TB_ENT B
										 , IF_5058 C
										 , TB_FNNR_CODE_KO D
									 WHERE 0 = 0
									   AND A.BZENTY_NO 		= B.BZENTY_NO
									   AND B.KD_CD			= C.KEDCD
									   AND C.ACC_CD_FULL	= D.FNNR_ACNT_CD
									   AND B.BRNO      		= COALESCE(p_bzno, B.BRNO)
									   AND C.LINK_STUS 		= 'N'
									 GROUP BY A.IR_NO, C.ACCT_DT
								   )
		;
		
		GET DIAGNOSTICS v_5058_delt_cnt := ROW_COUNT;

		-- 대상 데이터 등록
		INSERT INTO TB_ENT_FNNR (
			  IR_NO
			, SN
			, FNNR_SE_CD
			, FNNR_ACNT_CD
			, DATA_SE_CD
			, FNNR_YR
			, FNNR_YMD			
			, FNNR_AMT
			, KD_CD
			, RGTR_NO
			, REG_YMD
		) 
		SELECT D.IR_NO
			 , NEXTVAL('SEQ_ENT_FNNR')
			 , A.FS_CCD
			 , A.ACC_CD_FULL
			 , 'K'			 
			 , SUBSTR(A.ACCT_DT, 1, 4)	
			 , A.ACCT_DT
			 , A.VAL::NUMERIC
			 , A.KEDCD
			 , v_user_id
			 , now()
		  FROM IF_5058 A
			 , TB_FNNR_CODE_KO B
			 , TB_ENT C
			 , TB_ENT_IR D
		 WHERE 0 = 0
		   AND A.LINK_STUS 		= 'N'
		   AND A.ACC_CD_FULL 	= B.FNNR_ACNT_CD
		   AND A.KEDCD			= C.KD_CD
		   AND C.BZENTY_NO		= D.BZENTY_NO
		   AND C.BRNO 			= COALESCE(p_bzno, C.BRNO)
		;
		
		GET DIAGNOSTICS v_5058_inst_cnt := ROW_COUNT;
	
		-- 연계 테이블 상태 변경
		UPDATE IF_5058 A
		   SET LINK_STUS 	= '-'
		 WHERE LINK_STUS 	= 'N'
		   AND ACC_CD_FULL  IN (
								SELECT DISTINCT A.FNNR_ACNT_CD
								  FROM TB_FNNR_CODE_KO A
			   					     , TB_ENT_FNNR B
			   					 WHERE A.FNNR_ACNT_CD = B.FNNR_ACNT_CD
			   
							   )
		   AND KEDCD		IN (
								SELECT KD_CD
								  FROM TB_ENT
								 WHERE BRNO 		= COALESCE(p_bzno, BRNO)			   
							   )	
		;	
	/**
	*	#. 특허 및 실용신안 정보 등록
	*/	
	RAISE NOTICE '특허및실용신안 등록 시작 = %',	  curr_time;
		
		-- 대상 데이터 삭제 (전체)
		DELETE FROM TB_ENT_PTNT
		 WHERE 0 = 0
		   AND (IR_NO, KD_CD, PATENT_SE_CD)	IN (
									SELECT DISTINCT C.IR_NO, A.KODATACD, A.IPR_CD
									  FROM (
											SELECT KODATACD
												 , BASE_DT
										  		 , IPR_CD
												 , MAX(LINK_STUS)	AS LINK_STUS
												 , ROW_NUMBER() OVER(PARTITION BY KODATACD ORDER BY BASE_DT DESC) AS IDX
											  FROM IF_50TH
											 WHERE 0 = 0
											 GROUP BY KODATACD, BASE_DT, IPR_CD
											) A
										 , TB_ENT B
										 , TB_ENT_IR C
									 WHERE A.IDX 		= 1
									   AND A.LINK_STUS	= 'N'
									   AND A.KODATACD	= B.KD_CD
									   AND B.BZENTY_NO	= C.BZENTY_NO
									   AND B.BRNO		= COALESCE(p_bzno, B.BRNO)  
									)	
		; 
		
		GET DIAGNOSTICS v_50TH_delt_cnt := ROW_COUNT;
		
		-- 대상 데이터 등록 (최신 기준일자)
		INSERT INTO TB_ENT_PTNT (
			  IR_NO
			, SN
			, PATENT_SE_CD
			, DATA_SE_CD
			, APPLNM
			, PATNTRT_MAN
			, NM
			, ILLT_REG_NO
			, PATENT_REG_YMD
			, KD_CD
			, RGTR_NO
			, REG_YMD
		) 
		SELECT D.IR_NO
			 , NEXTVAL('SEQ_ENT_PTNT')
			 , A.IPR_CD
			 , 'K'
			 , A.PND_P
			 , A.RGT_OWNER
			 , A.NM
			 , A.REG_NO
			 , A.PND_DT
			 , A.KODATACD
			 , v_user_id
			 , now()
		  FROM IF_50TH A
			 , TB_ENT B
			 , (
				SELECT KODATACD
					 , BASE_DT
					 , MAX(LINK_STUS)	AS LINK_STUS
					 , ROW_NUMBER() OVER(PARTITION BY KODATACD ORDER BY BASE_DT DESC) AS IDX
				  FROM IF_50TH
				 WHERE 0 = 0
				 GROUP BY KODATACD, BASE_DT
				) C
			 , TB_ENT_IR D
		 WHERE 0 = 0
		   AND C.IDX 		= 1
		   AND A.LINK_STUS	= 'N'
		   AND A.KODATACD	= B.KD_CD
		   AND A.KODATACD	= C.KODATACD
		   AND A.BASE_DT	= C.BASE_DT
		   AND B.BZENTY_NO	= D.BZENTY_NO
		   AND A.ENP_REL_CD = '01'			-- 2023.09.18 (본인정보만 가져오기) 
		   AND B.BRNO		= COALESCE(p_bzno, B.BRNO)  
		; 		
	
		GET DIAGNOSTICS v_50TH_inst_cnt := ROW_COUNT;		
					
		-- 연계 테이블 상태 변경
		UPDATE IF_50TH A
		   SET LINK_STUS 	= '-'
		 WHERE LINK_STUS 	= 'N'
		   AND (KODATACD, BASE_DT, IPR_CD)  IN (
										SELECT DISTINCT A.KODATACD, A.BASE_DT, A.IPR_CD
										  FROM (
												SELECT KODATACD
													 , BASE_DT
											  		 , IPR_CD
													 , MAX(LINK_STUS)	AS LINK_STUS
													 , ROW_NUMBER() OVER(PARTITION BY KODATACD ORDER BY BASE_DT DESC) AS IDX
												  FROM IF_50TH
												 WHERE 0 = 0
												 GROUP BY KODATACD, BASE_DT, IPR_CD
												) A
											 , TB_ENT B
			   								 , TB_ENT_PTNT C
										 WHERE A.IDX 			= 1
										   AND A.LINK_STUS		= 'N'
										   AND A.KODATACD		= B.KD_CD
			   							   AND A.KODATACD		= C.KD_CD
										   AND B.BRNO			= COALESCE(p_bzno, B.BRNO)  
									    )
	
		;

	
	/**
	*	#. 디자인권및상표권 정보 등록
	*/
	RAISE NOTICE '디자인권및상표권 등록 시작 = %',	  curr_time;
	
		-- 대상 데이터 삭제 (전체) - IF_50TL
		DELETE FROM TB_ENT_PTNT
		 WHERE 0 = 0
		   AND (IR_NO, KD_CD, PATENT_SE_CD)	IN (
									SELECT DISTINCT C.IR_NO, A.KODATACD, IPR_CD
									  FROM (
											SELECT KODATACD
												 , BASE_DT
										  		 , IPR_CD
												 , MAX(LINK_STUS)	AS LINK_STUS
												 , ROW_NUMBER() OVER(PARTITION BY KODATACD ORDER BY BASE_DT DESC) AS IDX
											  FROM IF_50TL
											 WHERE 0 = 0
											 GROUP BY KODATACD, BASE_DT, IPR_CD
											) A
										 , TB_ENT B
										 , TB_ENT_IR C
									 WHERE A.IDX 		= 1
									   AND A.LINK_STUS	= 'N'
									   AND A.KODATACD	= B.KD_CD
									   AND B.BZENTY_NO	= C.BZENTY_NO
									   AND B.BRNO		= COALESCE(p_bzno, B.BRNO)  
									)	
		; 
		
		GET DIAGNOSTICS v_50TL_delt_cnt := ROW_COUNT;
		
		-- 대상 데이터 등록 (최신 기준일자)
		INSERT INTO TB_ENT_PTNT (
			  IR_NO
			, SN
			, PATENT_SE_CD
			, DATA_SE_CD
			, APPLNM
			, PATNTRT_MAN
			, NM
			, ILLT_REG_NO
			, PATENT_REG_YMD
			, KD_CD
			, RGTR_NO
			, REG_YMD
		) 
		SELECT D.IR_NO
			 , NEXTVAL('SEQ_ENT_PTNT')
			 , A.IPR_CD
			 , 'K'
			 , A.PND_P
			 , A.RGT_OWNER
			 , A.NM
			 , A.REG_NO
			 , A.PND_DT
			 , A.KODATACD
			 , v_user_id
			 , now()
		  FROM IF_50TL A
			 , TB_ENT B
			 , (
				SELECT KODATACD
					 , BASE_DT
					 , MAX(LINK_STUS)	AS LINK_STUS
					 , ROW_NUMBER() OVER(PARTITION BY KODATACD ORDER BY BASE_DT DESC) AS IDX
				  FROM IF_50TL
				 WHERE 0 = 0
				 GROUP BY KODATACD, BASE_DT
				) C
			 , TB_ENT_IR D
		 WHERE 0 = 0
		   AND C.IDX 		= 1
		   AND A.LINK_STUS	= 'N'
		   AND A.KODATACD	= B.KD_CD
		   AND A.KODATACD	= C.KODATACD
		   AND A.BASE_DT	= C.BASE_DT
		   AND B.BZENTY_NO	= D.BZENTY_NO
		   AND B.BRNO		= COALESCE(p_bzno, B.BRNO)  
		; 		
	
		GET DIAGNOSTICS v_50TL_inst_cnt := ROW_COUNT;	
					
		-- 연계 테이블 상태 변경
		UPDATE IF_50TL A
		   SET LINK_STUS 	= '-'
		 WHERE LINK_STUS 	= 'N'
		   AND (KODATACD, BASE_DT, IPR_CD)  IN (
										SELECT DISTINCT A.KODATACD, A.BASE_DT, A.IPR_CD
										  FROM (
												SELECT KODATACD
													 , BASE_DT
											  		 , IPR_CD
													 , MAX(LINK_STUS)	AS LINK_STUS
													 , ROW_NUMBER() OVER(PARTITION BY KODATACD ORDER BY BASE_DT DESC) AS IDX
												  FROM IF_50TL
												 WHERE 0 = 0
												 GROUP BY KODATACD, BASE_DT, IPR_CD
												) A
											 , TB_ENT B
			   								 , TB_ENT_PTNT C
										 WHERE A.IDX 			= 1
										   AND A.LINK_STUS		= 'N'
										   AND A.KODATACD		= B.KD_CD
			   							   AND A.KODATACD		= C.KD_CD
										   AND B.BRNO			= COALESCE(p_bzno, B.BRNO)  
									    )
	
		;
	
	
    o_err_code 	:= '0';
    o_err_msg  	:= v_procnm || '('
    		   || p_mode
    		   || ')'
    		   || ':'
               || 'IF_50D1(Update) '|| v_50D1_updt_cnt ||'건,'
			   || 'IF_50D1(Insert) '|| v_50D1_inst_cnt ||'건,'
			   || 'IF_50D2(Update) '|| v_50D2_updt_cnt ||'건,'
			   || 'IF_50D2(Insert) '|| v_50D2_inst_cnt ||'건,'
			   || 'IF_5058(Delete) '|| v_5058_delt_cnt ||'건,'
			   || 'IF_5058(Insert) '|| v_5058_inst_cnt ||'건,'
			   || 'IF_50TH(Delete) '|| v_50TH_delt_cnt ||'건,'
			   || 'IF_50TH(Insert) '|| v_50TH_inst_cnt ||'건,'
			   || 'IF_50TL(Delete) '|| v_50TL_delt_cnt ||'건,'
			   || 'IF_50TL(Insert) '|| v_50TL_inst_cnt ||'건';	
			   
	-- 배치완료 로그 등록
	CALL SP_BATCH_LOG('E', v_user_id, v_batch_code, o_err_code, o_err_msg, v_rtn_seq);
	
EXCEPTION
	WHEN SQLSTATE '90001'	THEN
		RAISE NOTICE '프로시저 실행 중 오류 %, % ',  	o_err_code, o_err_msg;
		
		CALL SP_BATCH_LOG('S', v_user_id, v_batch_code, o_err_code, o_err_msg, v_rtn_seq);
		
		RAISE;
	WHEN OTHERS 			THEN
		RAISE NOTICE '프로시저 실행 중 오류가 발생하였습니다. %, %', sqlstate, sqlerrm;
		
		CALL SP_BATCH_LOG('S', v_user_id, v_batch_code, sqlstate, sqlerrm, v_rtn_seq);
		
  		RAISE;
END;
$PROCEDURE$;
