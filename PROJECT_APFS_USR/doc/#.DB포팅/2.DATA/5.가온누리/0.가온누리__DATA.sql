
1. TB_BOARD_TYPE1_5053.insert.xlsx				-> 최신정보
2. inveseum_20230421_insert.xlsx				-> 최신정보
3. 가온누리_투자설명회_경영체_사업자번호_추가	-> 조사한 사업자번호 등록 (영쌤)


신규
1. 가온누리 다운받은 Insert Script 
	: d:\ProjectMgt\농금원투자정보플랫폼구축1차\05.개발\DB구축\1.가온누리\자료\inveseum_data\ *
	: *_to_Postgresql 파일로 변환
	: *_to_Postgresql 파일 스크립트 실행
	
	1) dbo.TB_BOARD_FILE.Table_to_Postgresql.sql
	2) dbo.TB_BOARD_TYPE1.Table_to_Postgresql.sql
	3) dbo.TB_BOARD_TYPE4.Table_to_Postgresql.sql
	4) dbo.TB_OPER_CATEGORY.Table_toPostgresql.sql
	5) dbo.TB_OPER_DATA.Table_toPostgresql.sql
	6) dbo.TB_OPER_YEAR.Table_toPostgresql.sql
	7) dbo.TB_REQUEST$$.Table_toPostgresql.sql
	
엑셀 데이터 컨버젼

1. 작은따옴표 ' 를 '' 작은 따옴표 두개로 일괄 변경처리 (전체항목)
2. 사업유형 },] 를 }] 로 일괄변경
2. 농심품투자조합, 사업유형, 매출액, 지원사업 -> 쌍따옴표를 | 로 변경 후 (DB 등록하고 나중에 다시 쌍따옴표로 변경)
3. 날짜형식을 테스트 형식으로 변경 (데이터 > 텍스트 나누기)
4. INSERT 문 앞, 뒤에  " 쌍따옴표 편집기창에서 제거 하기
5. 쌍따옴표 " 를 || 로 변경. (ANSWER_TITLE, ANSWER_CONTENTS, AGRIFOOD_UNION, BIZ_TYPE, BIZ_CONTENTS, BIZ_SALES, APP_CONTENTS, APP_SUPPORT)
	