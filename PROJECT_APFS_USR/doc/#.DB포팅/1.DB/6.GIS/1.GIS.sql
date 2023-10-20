-- PostGIS Extension 설치 (서버단)
yum install epel-release
yum install postgis

-- PostgreSQL PostGIS Extension 설치 (PSQL)
CREATE EXTENSION postgis;
ALTER TABLE tb_ent ALTER COLUMN lcinfo TYPE geometry USING lcinfo::geometry;
ALTER TABLE tb_sgg ALTER COLUMN sgg_lcinfo TYPE geometry USING sgg_lcinfo::geometry;