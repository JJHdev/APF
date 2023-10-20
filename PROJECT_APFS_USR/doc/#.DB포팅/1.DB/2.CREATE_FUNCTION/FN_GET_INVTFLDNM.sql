CREATE OR REPLACE FUNCTION FN_GET_INVTFLDNM (
    /**
     * =============================================================
     * title    : 투자분야 명칭조회
     * author   : LSH
     * date     : 2023.04.24
     * tables   : tb_invtrlm_code
     * 
     * 2023.08.17 LSH use_yn 조건제외함
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

    SELECT invt_fld_nm 
      INTO vo_name
      FROM tb_invtrlm_code
     WHERE invt_fld_cd  = vi_code
    ;

    RETURN vo_name;
END;
$body$