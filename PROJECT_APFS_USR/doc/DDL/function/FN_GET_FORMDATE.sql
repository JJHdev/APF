CREATE OR REPLACE FUNCTION FN_GET_FORMDATE(
    /**
     * =============================================================
     * name   : 날짜 또는 년월 또는 년으로 들어온값을 날짜형태로 정형화하는 함수
     * author : LSH
     * date   : 2023.06.16
     * tables : NONE
     * -------------------------------------------------------------
     *
     * @param     vi_date 날짜문자열 (yyyy or yyyymm or yyyymmdd)
     * @param     vi_flag 시작일(B)/종료일(E) 구분
     * @return    vo_date 정형화한 날짜
     * =============================================================
     */
	vi_date character varying,
	vi_flag character varying
)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_date VARCHAR(20);
BEGIN
	vo_date = REGEXP_REPLACE(vi_date, '[^0-9]+', '', 'g');
	
	IF LENGTH(vo_date) > 8 THEN
	   vo_date = SUBSTR(vo_date,1,8);
	ELSIF LENGTH(vo_date) = 6 THEN
		IF vi_flag = 'E' THEN
		   vo_date = TO_CHAR((date_trunc('MONTH', TO_DATE(vo_date||'01','YYYYMMDD')) + INTERVAL '1 MONTH - 1 day')::date,'YYYYMMDD');
		ELSE
		   vo_date = vo_date || '01';
		END IF;
	ELSIF LENGTH(vo_date) = 4 THEN
		IF vi_flag = 'E' THEN
		   vo_date = vo_date || '1231';
		ELSE
		   vo_date = vo_date || '0101';
		END IF;
	END IF;

    RETURN vo_date;
END;
$body$