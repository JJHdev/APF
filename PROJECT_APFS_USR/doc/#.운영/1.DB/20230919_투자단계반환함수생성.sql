CREATE OR REPLACE FUNCTION FN_GET_INVTSTEP(
    /**
     * =============================================================
     * name   : 누적금액 범위에 따른 투자단계 반환 함수 
     * author : LSH
     * date   : 2023.09.19
     * tables : NONE
     * -------------------------------------------------------------
     *
     * @param     vi_amt  금액
     * @return    vo_code 금액범위에 따른 코드반환
     * =============================================================
     */
    vi_amt numeric
)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_code VARCHAR(10);
    DECLARE vo_unit numeric;
BEGIN

	vo_code = null;
	vo_unit = 100000000;
	
    IF    vi_amt IS NULL         THEN vo_code = 'S0'; -- 시드단계 (5억이하)
	ELSIF vi_amt < (  5*vo_unit) THEN vo_code = 'S0'; -- 시드단계 (5억이하)
	ELSIF vi_amt < ( 50*vo_unit) THEN vo_code = 'SA'; -- 시리즈A (50억미만)
	ELSIF vi_amt < (200*vo_unit) THEN vo_code = 'SB'; -- 시리즈B (200억미만)
	ELSE                    vo_code = 'SC'; -- 시리즈C (200억이상)
	END IF;

    RETURN vo_code;
END;
$body$
