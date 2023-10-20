/*
 * 등록된 유관기관 업체번호 확인 후 수정해서 등록
 * 
 */
-- 2023.05.16 유관기관 업체번호 맵핑
UPDATE sys_code SET cd_cn = '500149' WHERE up_cd_id = 'CT.CRDNS_SE' AND cd_id = '01'; -- 농업정책보험금융원
UPDATE sys_code SET cd_cn = '500150' WHERE up_cd_id = 'CT.CRDNS_SE' AND cd_id = '02'; -- 농림식품기술기획평가원
UPDATE sys_code SET cd_cn = '500151' WHERE up_cd_id = 'CT.CRDNS_SE' AND cd_id = '03'; -- 한국농업기술진흥원
UPDATE sys_code SET cd_cn = '500152' WHERE up_cd_id = 'CT.CRDNS_SE' AND cd_id = '04'; -- 한국식품산업클러스터진흥원