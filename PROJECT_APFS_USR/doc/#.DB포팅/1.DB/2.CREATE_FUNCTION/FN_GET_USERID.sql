CREATE OR REPLACE FUNCTION FN_GET_USERID(vi_userno character varying)
    /**
     * =============================================================
     * title    : 사용자아이디 조회
     * author   : hblee
     * date     : 2023.07.13
     * tables   : TB_USER
     * -------------------------------------------------------------
     *
     * @param       vi_userno   사용자번호
     * @return      vo_userid   사용자아이디
     * =============================================================
     */
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
    DECLARE vo_userid VARCHAR(100);
BEGIN
    vo_userid = null;

    SELECT USER_ID INTO vo_userid
      FROM TB_USER
     WHERE USER_NO = vi_userno
    ;

    RETURN vo_userid;
END;
$function$
;
