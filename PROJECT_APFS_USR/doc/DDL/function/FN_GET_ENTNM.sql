CREATE OR REPLACE FUNCTION FN_GET_ENTNM (
    /**
     * =============================================================
     * title    : 업체 명칭조회
     * author   : LSH
     * date     : 2023.04.24
     * tables   : tb_ent
     * -------------------------------------------------------------
     *
     * @param       vi_code   코드
     * @return      vo_name   코드명
     * =============================================================
     */
    vi_code   character varying
)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_name VARCHAR(100);
BEGIN
    vo_name = null;

    SELECT bzenty_nm 
      INTO vo_name
      FROM tb_ent
     WHERE bzenty_no  = vi_code
    ;

    RETURN vo_name;
END;
$body$