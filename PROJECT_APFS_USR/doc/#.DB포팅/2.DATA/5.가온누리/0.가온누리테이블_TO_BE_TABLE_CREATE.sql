CREATE TABLE ZTB_BOARD_FILE (
   BOARD_SEQ    integer , 
   SEQ    integer , 
   FILE_SEQ    integer , 
   FILE_NAME    varchar(250) , 
   ORIGINAL_NAME    varchar(250) , 
   FILE_SIZE    integer , 
   DOWN_COUNT    integer 
);


CREATE TABLE ZTB_BOARD_TYPE1 (
   BOARD_SEQ    integer , 
   SEQ    integer , 
   TOP_AT    varchar(1) , 
   CATEGORY_SEQ    integer , 
   TITLE    varchar(500) , 
   THUMB_IMAGE    varchar(200) , 
   CONTENTS    text , 
   READ_COUNT    integer , 
   HEART_COUNT    integer , 
   TAG    varchar(250) , 
   LINK_URL    varchar(500) , 
   LICENSE    varchar(10) , 
   DEL_AT    varchar(1) , 
   REGISTER_ID    varchar(50) , 
   REGISTER_NAME    varchar(100) , 
   CREATE_DATE    varchar(100) , 
   UPDATE_DATE    varchar(100) , 
   CHARGER    varchar(100) , 
   CHARGER_TEL    varchar(20) , 
   CHARGER_DEPT    varchar(100) 
);


CREATE TABLE ZTB_BOARD_TYPE4 (
   BOARD_SEQ    integer , 
   SEQ    integer , 
   CATEGORY_SEQ    integer , 
   TITLE    varchar(500) , 
   SUMMARY    varchar(2000) , 
   THUMB_IMAGE    varchar(200) , 
   MOVIE_FILE_NAME    varchar(250) , 
   MOVIE_ORIGINAL_NAME    varchar(250) , 
   MOVIE_FILE_SIZE    integer , 
   SOCIAL_HTML    varchar(1000) , 
   CONTENTS    text , 
   READ_COUNT    integer , 
   HEART_COUNT    integer , 
   TAG    varchar(250) , 
   LINK_URL    varchar(500) , 
   LICENSE    varchar(10) , 
   DEL_AT    varchar(1) , 
   REGISTER_ID    varchar(50) , 
   REGISTER_NAME    varchar(100) , 
   CREATE_DATE    varchar(100) , 
   UPDATE_DATE    varchar(100) , 
   RESERVATION_AT    varchar(1) , 
   CHARGER_TEL    varchar(20) , 
   CHARGER_DEPT    varchar(100) , 
   TOP_AT    varchar(1) 
);


CREATE TABLE ZTB_MEMBER (
   MEMBER_SN    integer , 
   MEMBER_ID    varchar(50) , 
   PASSWD    varchar(250) , 
   NAME    varchar(100) , 
   NICK_NAME    varchar(250) , 
   EMAIL    varchar(250) , 
   EMAIL_AUTH    varchar(1) , 
   POST    varchar(250) , 
   ADDRESS1    varchar(250) , 
   ADDRESS2    varchar(250) , 
   MEMBER_KIND    varchar(1) , 
   MEMBER_KIND_STATUS    varchar(1) , 
   ORG_KIND    varchar(1) , 
   ORG_CODE    varchar(50) , 
   ORG_NAME    varchar(100) , 
   DEPARTMENT    varchar(250) , 
   DROP_AT    varchar(1) , 
   LEVEL_REQ_DATE    varchar(50) , 
   LEVEL_PROC_DATE    varchar(50) , 
   REG_DATE    varchar(100)
);


CREATE TABLE  ZTB_OPER_CATEGORY (
   CATEGORY_SEQ    integer , 
   CATEGORY_NAME    varchar(200) , 
   CATEGORY_USG_AT    varchar(1) , 
   YEAR_SEQ    integer
);


CREATE TABLE ZTB_OPER_DATA (
   DATA_SEQ    integer , 
   YEAR_SEQ    integer , 
   CATEGORY_SEQ    integer , 
   COM_NAME    varchar(100) , 
   COM_CEO    varchar(100) , 
   COM_CONTENTS    varchar(400) , 
   TEL    varchar(50) , 
   EMAIL    varchar(50) , 
   FILE_NAME    varchar(250) , 
   ORIGINAL_NAME    varchar(250) , 
   FILE_NAME2    varchar(250) , 
   ORIGINAL_NAME2    varchar(250) , 
   CREATE_DATE    varchar(100) , 
   READ_COUNT    integer , 
   RECOMEND_AT    varchar(1)
);


CREATE TABLE ZTB_OPER_YEAR (
   YEAR_SEQ    integer , 
   YEAR_NAME    varchar(50) , 
   YEAR_USG_AT    varchar(1)
);


CREATE TABLE ZTB_REQUEST (
   SEQ    integer , 
   GB    varchar(2) , 
   STATUS    varchar(2) , 
   STATUS_DATE    varchar(100) , 
   ANSWER_TITLE    varchar(200) , 
   ANSWER_CONTENTS    text , 
   ANSWER_DATE    varchar(100) , 
   BILL_STATUS    varchar(2) , 
   BILL_STATUS_DATE    varchar(100) , 
   PASSWD    varchar(250) , 
   COM_NAME    varchar(100) , 
   COM_CEO    varchar(100) , 
   COM_BIRTHDAY    varchar(10) , 
   COM_SEX    varchar(1) , 
   COM_LICENSE_NUM    varchar(30) , 
   COM_REGISTER_NUM    varchar(30) , 
   COM_YEAR    varchar(30) , 
   COM_STAFF    varchar(20) , 
   COM_POST    varchar(10) , 
   COM_ADDRESS1    varchar(200) , 
   COM_ADDRESS2    varchar(200) , 
   COM_TEL    varchar(30) , 
   COM_FAX    varchar(30) , 
   CHARGER_NAME    varchar(50) , 
   CHARGER_DEPT    varchar(200) , 
   CHARGER_TEL    varchar(40) , 
   CHARGER_EMAIL    varchar(50) , 
   AGRIFOOD_UNION    varchar(4000) , 
   BIZ_KIND    varchar(20) , 
   BIZ_KIND2    varchar(50) , 
   BIZ_TYPE    varchar(4000) , 			-- json
   BIZ_CONTENTS    varchar(4000) , 
   BIZ_SALES    varchar(4000) , 		-- json
   BIZ_WEBSITE    varchar(200) , 
   BIZ_HOPE_AMOUNT    varchar(50) , 
   APP_TITLE    varchar(200) , 
   APP_CONTENTS    text , 
   APP_METHOD    varchar(50) , 
   APP_DATE    varchar(50) , 
   APP_HOUR    varchar(50) , 
   APP_PATH    varchar(100) , 
   APP_SUPPORT    varchar(4000) , 
   APP_FILE_NAME    varchar(250) , 
   APP_ORIGINAL_NAME    varchar(250) , 
   APP_FILE_NAME2    varchar(250) , 
   APP_ORIGINAL_NAME2    varchar(250) , 
   APP_FILE_NAME3    varchar(250) , 
   APP_ORIGINAL_NAME3    varchar(250) , 
   APP_FILE_NAME4    varchar(250) , 
   APP_ORIGINAL_NAME4    varchar(250) , 
   APP_FILE_NAME5    varchar(250) , 
   APP_ORIGINAL_NAME5    varchar(250) , 
   APP_FILE_NAME6    varchar(250) , 
   APP_ORIGINAL_NAME6    varchar(250) , 
   APP_FILE_NAME7    varchar(250) , 
   APP_ORIGINAL_NAME7    varchar(250) , 
   APP_FILE_NAME8    varchar(250) , 
   APP_ORIGINAL_NAME8    varchar(250) , 
   CREATE_DATE    varchar(100) , 
   APP_URL    varchar(250) , 
   APP_FILE_NAME9    varchar(250) , 
   APP_ORIGINAL_NAME9    varchar(250)
);

CREATE TABLE ZTB_REQUEST2 (
   SEQ    integer , 
   GB    varchar(2) , 
   STATUS    varchar(2) , 
   STATUS_DATE    varchar(100) , 
   ANSWER_TITLE    varchar(200) , 
   ANSWER_CONTENTS    text , 
   ANSWER_DATE    varchar(100) , 
   BILL_STATUS    varchar(2) , 
   BILL_STATUS_DATE    varchar(100) , 
   PASSWD    varchar(250) , 
   COM_NAME    varchar(100) , 
   COM_CEO    varchar(100) , 
   COM_BIRTHDAY    varchar(10) , 
   COM_SEX    varchar(1) , 
   COM_LICENSE_NUM    varchar(30) , 
   COM_REGISTER_NUM    varchar(30) , 
   COM_YEAR    varchar(30) , 
   COM_STAFF    varchar(20) , 
   COM_POST    varchar(10) , 
   COM_ADDRESS1    varchar(200) , 
   COM_ADDRESS2    varchar(200) , 
   COM_TEL    varchar(30) , 
   COM_FAX    varchar(30) , 
   CHARGER_NAME    varchar(50) , 
   CHARGER_DEPT    varchar(200) , 
   CHARGER_TEL    varchar(40) , 
   CHARGER_EMAIL    varchar(50) , 
   AGRIFOOD_UNION    varchar(4000) , 
   BIZ_KIND    varchar(20) , 
   BIZ_KIND2    varchar(50) , 
   BIZ_TYPE    varchar(4000) , 			-- json
   BIZ_CONTENTS    varchar(4000) , 
   BIZ_SALES    varchar(4000) , 		-- json
   BIZ_WEBSITE    varchar(200) , 
   BIZ_HOPE_AMOUNT    varchar(50) , 
   APP_TITLE    varchar(200) , 
   APP_CONTENTS    text , 
   APP_METHOD    varchar(50) , 
   APP_DATE    varchar(50) , 
   APP_HOUR    varchar(50) , 
   APP_PATH    varchar(100) , 
   APP_SUPPORT    varchar(4000) , 
   APP_FILE_NAME    varchar(250) , 
   APP_ORIGINAL_NAME    varchar(250) , 
   APP_FILE_NAME2    varchar(250) , 
   APP_ORIGINAL_NAME2    varchar(250) , 
   APP_FILE_NAME3    varchar(250) , 
   APP_ORIGINAL_NAME3    varchar(250) , 
   APP_FILE_NAME4    varchar(250) , 
   APP_ORIGINAL_NAME4    varchar(250) , 
   APP_FILE_NAME5    varchar(250) , 
   APP_ORIGINAL_NAME5    varchar(250) , 
   APP_FILE_NAME6    varchar(250) , 
   APP_ORIGINAL_NAME6    varchar(250) , 
   APP_FILE_NAME7    varchar(250) , 
   APP_ORIGINAL_NAME7    varchar(250) , 
   APP_FILE_NAME8    varchar(250) , 
   APP_ORIGINAL_NAME8    varchar(250) , 
   CREATE_DATE    varchar(100) , 
   APP_URL    varchar(250) , 
   APP_FILE_NAME9    varchar(250) , 
   APP_ORIGINAL_NAME9    varchar(250), 
   RMK_ROWID			integer,
   RMK_COM_BIRTHDAY		varchar(1000),
   RMK_COM_SEX		varchar(1000),
   RMK_CHARGER_NAME		varchar(1000),
   RMK_CHARGER_DEPT		varchar(1000),
   RMK_CHARGER_TEL		varchar(1000),
   RMK_CHARGER_EMAIL		varchar(1000),
   RMK_AGRIFOOD_UNION		varchar(1000)
);

/**
 투자설명회 - 사업자번호 추가정보
*/
CREATE TABLE ZTB_OPER_DATA_BRNO (
   DATA_SEQ    integer , 
   BRNO	       varchar(10), 
   RPRSV_NM    varchar(20)
);


CREATE TABLE ZTB_REQUEST_BIZ_TYPE (
   SEQ    			integer , 
   BIZ_TYPE_NM	    varchar(200), 
   BIZ_TYPE		    varchar(100)
);


