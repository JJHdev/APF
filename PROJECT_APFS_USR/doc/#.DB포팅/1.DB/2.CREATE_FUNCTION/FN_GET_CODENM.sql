CREATE OR REPLACE FUNCTION FN_GET_CODENM (
    /**
     * =============================================================
     * title    : 공통코드 조회
     * author   : ntarget
     * date     : 2021.09.14
     * tables   : SYS_CODE
     * -------------------------------------------------------------
     *
     * @param       vi_upper    상위코드
     * @param       vi_code     코드
     * @return      vo_codenm   코드명
     * =============================================================
     */
    vi_upper character varying,
    vi_code   character varying
)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_codenm VARCHAR(100);
BEGIN
    vo_codenm = null;

    SELECT CD_NM INTO vo_codenm
      FROM SYS_CODE
     WHERE UP_CD_ID     = vi_upper
       AND CD_ID        = vi_code
       AND USE_YN       = 'Y'
    ;

    RETURN vo_codenm;
END;
$body$