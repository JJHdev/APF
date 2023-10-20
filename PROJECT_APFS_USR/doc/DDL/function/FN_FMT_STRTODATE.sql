CREATE OR REPLACE FUNCTION fn_fmt_strtodate(vi_strdate character varying, vi_gbn character varying)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_date VARCHAR(20);
    DECLARE v_strExpDate VARCHAR(20);
BEGIN
    vo_date         = null;
    v_strExpDate    = REGEXP_REPLACE(vi_strDate, '[^0-9]+', '', 'g');

    IF LENGTH(TRIM(v_strExpDate)) = 8 THEN
        vo_date     = REGEXP_REPLACE(v_strExpDate, '(.{4})(.{2})(.{2})', CONCAT('\1',vi_gbn,'\2',vi_gbn,'\3'), 'g');
    ELSIF LENGTH(TRIM(v_strExpDate)) = 6 THEN
        vo_date     = REGEXP_REPLACE(v_strExpDate, '(.{4})(.{2})', CONCAT('\1',vi_gbn,'\2'), 'g');
    ELSE
        vo_date = v_strExpDate;
    END IF;

  RETURN vo_date; 
END;
$body$