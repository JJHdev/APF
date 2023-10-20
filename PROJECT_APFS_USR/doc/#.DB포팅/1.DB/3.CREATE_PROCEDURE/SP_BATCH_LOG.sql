CREATE OR REPLACE PROCEDURE SP_BATCH_LOG (
	/**
	* ------------------------------------------------------------------------------------------------------
	* Name			: 배치 로그 프로시저 (실행 : CALL SP_BATCH_LOG('S', v_user_id, v_batch_code, o_err_code, o_err_msg, null);)
	* User			: ntarget
	* Date			: 2023.06.30
	* ------------------------------------------------------------------------------------------------------
	*
    * @param     p_mode     실행타입
    *                       - S : 배치시작로그
    *                       - E : 배치종료로그
    * @param     p_userid   등록ID
    * @param     p_batcode  배치코드
    *                       - B01: KODATA 연계 배치
    * @param     p_errcode  에러코드
    * @param     p_errnote  에러메시지
    * @param     p_seq      배치로그SEQ
    *
    * 배치실행 시작시 로그 등록
    *     ex)  SP_BATCH_LOG('S','100000','B01', '0', NULL, NULL);
    *
    * 배치실행 시작외 (오류, 성공)
    *     ex)  SP_BATCH_LOG('E','100000','B01', o_err_code, o_err_msg, v_rtn_seq);
	*
	*/
	p_mode				IN text,
	p_userid			IN text,
	p_batcode			IN text,
	p_errcode			IN text,
	p_errnote			IN text,	
	p_rtn_seq			INOUT integer	
)
LANGUAGE PLPGSQL
AS $PROCEDURE$
DECLARE 
	v_rowcount		integer		:= 0;
	v_procnm		text;
	v_batch_code	text;
	v_batstat		text;
	v_userid		text;
				
BEGIN

    -- VALIDATION
    IF (p_mode NOT IN ('S','E')) THEN
        RAISE SQLSTATE '90001';
    END IF;
	
    IF (p_userid IS NULL) THEN
        v_userid := 'batch';
    ELSE
        v_userid := p_userid;
    END IF;
	
    IF (p_batcode IS NULL) THEN
        RAISE SQLSTATE '90001';
    END IF;
	
    IF (p_errcode IS NULL) THEN
        RAISE SQLSTATE '90001';
    END IF;
	
    -- 배치시작시 등록처리
    IF (p_mode = 'S') THEN
        v_batstat := '00'; -- 시작상태

        INSERT INTO SYS_BATCH_HST (
			  SN
		  	, BATCH_CD
			, BATCH_YMD
		    , BATCH_PRGRM_NM
		    , BATCH_STTS_CD
		    , BATCH_BGNG_DT
		    , BATCH_END_DT
		    , ERROR_CD
		    , ERROR_CN
		    , RGTR_NO
		    , REG_YMD
        )
        VALUES (
			  NEXTVAL('SEQ_BATCH_HST')
			, p_batcode
			, TO_CHAR(now(), 'YYYYMMDD') 
			, ''
			, v_batstat
			, now()
			, NULL
			, p_errcode
			, NULL
			, v_userid
			, now()
        );

        SELECT  CURRVAL('SEQ_BATCH_HST') INTO p_rtn_seq
        ;

    -- 배치종료시 업데이트
    ELSIF (p_mode = 'E') THEN

        IF (p_errcode = '0') THEN
            v_batstat := '10'; -- 성공상태
        ELSE
            v_batstat := '90'; -- 오류상태
        END IF;

        UPDATE 	SYS_BATCH_HST
           SET 	BATCH_END_DT	= now()
              , BATCH_STTS_CD  	= v_batstat
              ,	ERROR_CD   		= p_errcode
              ,	ERROR_CN   		= p_errnote
              ,	MDFR_NO      	= v_userid
              ,	MDFCN_YMD  		= now()
         WHERE  SN 				= p_rtn_seq;
    END IF;

	RAISE NOTICE '배치로그 = %, %, %',	  p_rtn_seq, p_errcode, p_errnote;


EXCEPTION
	WHEN SQLSTATE '90001'	THEN
		RAISE;
	WHEN OTHERS 			THEN
  		RAISE;
END;
$PROCEDURE$;
