1. JSON 데이터 마지막 컴마 있는 오류 데이터 수정
       UPDATE ZTB_REQUEST
            SET BIZ_TYPE = REPLACE(BIZ_TYPE, '},]', '}]')
       ;

2. 쌍따옴표 처리 수정
      UPDATE ZTB_REQUEST
           SET ANSWER_TITLE  = REPLACE(ANSWER_TITLE, '||', '"')
              , ANSWER_CONTENTS = REPLACE(ANSWER_CONTENTS, '||', '"')
              , AGRIFOOD_UNION = REPLACE(AGRIFOOD_UNION, '||', '"')
              , BIZ_TYPE   = REPLACE(BIZ_TYPE, '||', '"')
              , BIZ_CONTENTS  = REPLACE(BIZ_CONTENTS, '||', '"')
              , BIZ_SALES  = REPLACE(BIZ_SALES, '||', '"')
              , APP_CONTENTS  = REPLACE(APP_CONTENTS, '||', '"')
              , APP_SUPPORT  = REPLACE(APP_SUPPORT, '||', '"')
      ;