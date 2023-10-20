CREATE OR REPLACE FUNCTION FN_GET_USERNM (
    /**
     * =============================================================
     * title    : 사용자명 조회
     * author   : ntarget
     * date     : 2021.09.14
     * tables   : TB_USER
     * -------------------------------------------------------------
     *
     * @param       vi_userno   사용자번호
     * @return      vo_usernm   사용자명
     * =============================================================
     */
    vi_userno  character varying
)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_usernm VARCHAR(100);
BEGIN
    vo_usernm = null;

    SELECT USER_NM INTO vo_usernm
      FROM TB_USER
     WHERE USER_NO = vi_userno
    ;

    RETURN vo_usernm;
END;
$body$