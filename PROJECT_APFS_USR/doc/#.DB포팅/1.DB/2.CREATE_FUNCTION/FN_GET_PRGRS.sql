CREATE OR REPLACE FUNCTION FN_GET_PRGRS (
    /**
     * =============================================================
     * title    : 날짜 범위 기준 진행상태 조회
     * author   : LSH
     * date     : 2023.04.25
     * tables   : tb_ent
     * -------------------------------------------------------------
     *
     * @param       vi_code   코드
     * @return      vo_name   코드명
     * 
     * 2023.06.16 날짜 정형화 함수(FN_GET_FORMDATE) 적용 
     * =============================================================
     */
    vi_bgng_ymd   character varying,
    vi_end_ymd    character varying
)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_code VARCHAR(10);
BEGIN
	-- 종료상태
    vo_code = '90';

	SELECT CASE 
	       WHEN TO_CHAR(NOW(),'YYYYMMDD') BETWEEN FN_GET_FORMDATE(vi_bgng_ymd,'B') AND FN_GET_FORMDATE(vi_end_ymd,'E') THEN '10' -- 모집종
           WHEN TO_CHAR(NOW(),'YYYYMMDD') < FN_GET_FORMDATE(vi_bgng_ymd,'B') THEN '20' -- 모집예정
           ELSE '90' -- 모집종료
           END
      INTO vo_code
    ;
    RETURN vo_code;
END;
$body$