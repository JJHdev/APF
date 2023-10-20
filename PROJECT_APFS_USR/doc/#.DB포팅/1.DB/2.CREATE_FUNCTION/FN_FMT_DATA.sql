CREATE OR REPLACE FUNCTION FN_FMT_DATA(
    /**
     * =============================================================
     * name   : 포맷된 데이터 조회(전화번호...)
     * author : ntarget
     * date   : 2021.09.14
     * tables : NONE
     * -------------------------------------------------------------
     *
     * @param     vi_data           입력값
     * @param     vi_type           포맷타입
                                    1. [TEL]    : 전화번호(-사용), 
                                    2. [TEL-M]  : 전화번호[휴대전화번호] 마스킹 (ex 010-****-2222)                                                        
                                    3. [NUM]    : 데이터중 숫자만 남기고 변환
                                    4. [NUM2]   : 데이터중 숫자와소수점만 남기고 변환
                                    5. [RNO]    : 사업자등록번호
                                    6. [RRNO]   : 주민등록번호
                                    7. [RRNO-M] : 주민등록번호 마스킹 (ex 111111-2******)
                                    8. [NM] : 이름 (ex 홍*동)
     * @return    vo_fmt_data       포맷 데이터
     * =============================================================
     */
    vi_type character varying,
    vi_data character varying
)
  RETURNS character varying
  LANGUAGE plpgsql
AS
$body$
    DECLARE vo_fmt_data VARCHAR(100);
BEGIN

	vo_fmt_data = null;

    IF TRIM(vi_data) IS NOT NULL THEN
        IF TRIM(vi_type) = 'TEL' THEN
            --vo_fmt_data = REGEXP_REPLACE(REGEXP_REPLACE(vi_data,'[^:digit:]]', 'g'),'(^02|050[[:digit:]]{1}|[[:digit:]]{3})([[:digit:]]{3,4})([[:digit:]]{4})','\1-\2-\3', 'g');
        	vo_fmt_data = REGEXP_REPLACE(REGEXP_REPLACE(vi_data,'[^:digit:]]', 'g'),'^(02|[3-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})','\1-\2-\3', 'g');
        ELSIF TRIM(vi_type) = 'TEL-M' THEN
            --vo_fmt_data = REGEXP_REPLACE(REGEXP_REPLACE(vi_data,'[^:digit:]]', 'g'),'(^02|050[[:digit:]]{1}|[[:digit:]]{3})([[:digit:]]{3,4})([[:digit:]]{4})','\1-****-\3', 'g');
        	vo_fmt_data = REGEXP_REPLACE(REGEXP_REPLACE(vi_data,'[^:digit:]]', 'g'),'^(02|[3-8][0-5]?|01[01346-9])-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})','\1-****-\3', 'g');
        ELSIF TRIM(vi_type) = 'NUM' THEN
            vo_fmt_data = REGEXP_REPLACE(vi_data, '[^0-9]+', '', 'g');
        ELSIF TRIM(vi_type) = 'NUM2' THEN
            vo_fmt_data = REGEXP_REPLACE(vi_data, '[^.0-9]+', '', 'g');
        ELSIF TRIM(vi_type) = 'RNO' THEN
            vo_fmt_data = REGEXP_REPLACE(vi_data, '^([0-9]{3})-?([0-9]{2})-?([0-9]{5})$', '\1-\2-\3', 'g');
        ELSIF TRIM(vi_type) = 'RRNO' THEN
            vo_fmt_data = REGEXP_REPLACE(vi_data, '(\d{6})(\d{1,7})', '\1-\2', 'g');
        ELSIF TRIM(vi_type) = 'RRNO-M' THEN
            vo_fmt_data = REGEXP_REPLACE(vi_data, '(\d{6})(\d{1})(\d{0,7})', '\1-\2' || '******', 'g');
        ELSIF TRIM(vi_type) = 'NM' THEN
            IF (LENGTH(TRIM(vi_data)) = 2) THEN
                vo_fmt_data = substr(vi_data,1,1)||'*';
            ELSE
                vo_fmt_data = substr(vi_data,1,1)||lpad('*', length(vi_data)-2, '*')||substr(vi_data, length(vi_data), 1);
            END IF;             
        END IF;
    END IF;

    RETURN vo_fmt_data;
END;
$body$