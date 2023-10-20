-- PostgreSQL PostGIS Extension 설치
ALTER TABLE tb_ent ALTER COLUMN lcinfo TYPE geometry USING lcinfo::geometry;
ALTER TABLE tb_sgg ALTER COLUMN sgg_lcinfo TYPE geometry USING sgg_lcinfo::geometry;
create extension postgis;