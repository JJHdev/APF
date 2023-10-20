CREATE OR REPLACE FUNCTION FN_GET_FUNDSIZE(
    /**
     * =============================================================
     * name   : 금액범위에 따른 코드 반환 
     * author : LSH
     * date   : 2023.06.16
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
BEGIN

	vo_code = null;
	
	IF    vi_amt < ( 10*100) THEN vo_code = 'IH1'; -- 10억원 미만
	ELSIF vi_amt < ( 50*100) THEN vo_code = 'IH2'; -- 10억원 초과 ~ 50억원 이하
	ELSIF vi_amt < (100*100) THEN vo_code = 'IH3'; -- 50억원 초과 ~ 100억원 이하
	ELSIF vi_amt < (200*100) THEN vo_code = 'IH4'; -- 100억원 초과 ~ 200억원 이하
	END IF;

    RETURN vo_code;
END;
$body$