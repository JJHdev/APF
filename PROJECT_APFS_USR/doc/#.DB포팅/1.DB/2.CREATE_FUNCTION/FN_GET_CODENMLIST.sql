CREATE OR REPLACE FUNCTION FN_GET_CODENMLIST(vi_upper character varying, vi_code character varying, sep character varying, sep2 character varying)
    /**
     * =============================================================
     * name   : 코드목록 코드명 조회
     * author : LHB
     * date   : 2023.07.13
     * tables : NONE
     * -------------------------------------------------------------
     *
     * @param     vi_upper				상위코드
     * @param     vi_code				코드목록
     * @param     sep				코드목록을 구분하고 있는 구분자
     * @param     sep2				코드명목록을 구분할 구분자
     * @return    vo_codenmlist		코드명목록
     * =============================================================
     */
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
    DECLARE vo_codenm VARCHAR(100);
    declare vo_codenmlist VARCHAR(1000);
    declare idx integer;
BEGIN
    vo_codenm = 'START';
    vo_codenmlist = '';
    idx = 1;
   
   while (vo_codenm != '')
   loop
      select split_part(vi_code, sep, idx) into vo_codenm;
      idx = idx + 1;
      if vo_codenm != '' then
         select vo_codenmlist||fn_get_codenm(vi_upper, vo_codenm)||sep2 into vo_codenmlist;
      else
      	 vo_codenm = '';
      end if;
   end loop;
  
  vo_codenmlist = trim(vo_codenmlist, sep2);

    RETURN vo_codenmlist;
END;
$function$
;
