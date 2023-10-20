CREATE OR REPLACE VIEW vw_ent
AS 
SELECT A.*
      ,B.ir_no
      ,B.main_biz_cn
      ,B.core_itm_cn
      ,B.biz_cn
      ,B.pic_nm
      ,B.pic_telno
      ,B.rls_yn
      ,B.pr_vido_url
      ,B.inq_cnt
      ,B.prgrs_stts_cd
  FROM tb_ent A
  LEFT OUTER JOIN
      (SELECT *
         FROM tb_ent_ir
        WHERE ir_no IN (SELECT MAX(ir_no) 
                          FROM tb_ent_ir 
                         WHERE prgrs_stts_cd != '00'
                         GROUP BY bzenty_no)
      ) B
    ON A.bzenty_no = B.bzenty_no 
