--新增需要定时导出货架商品的货架表
-- Create table
create table T_CATEGORY_EXPORT
(
  CID VARCHAR2(30) not null
);
comment on column  T_CATEGORY_EXPORT.CID  is '需要定时导出货架商品的货架ID';
--- 初始化25个指定货架
insert into t_category_export (cid) values ('1810805');
insert into t_category_export (cid) values ('1815681');
insert into t_category_export (cid) values ('1815682');
insert into t_category_export (cid) values ('1810802');
insert into t_category_export (cid) values ('1810803');
insert into t_category_export (cid) values ('1814487');
insert into t_category_export (cid) values ('1782550');
insert into t_category_export (cid) values ('1782551');
insert into t_category_export (cid) values ('1782548');
insert into t_category_export (cid) values ('1782549');
insert into t_category_export (cid) values ('1257460');
insert into t_category_export (cid) values ('1257457');
insert into t_category_export (cid) values ('1257459');
insert into t_category_export (cid) values ('1257458');
insert into t_category_export (cid) values ('1257461');
insert into t_category_export (cid) values ('1257461');
insert into t_category_export (cid) values ('1257464');
insert into t_category_export (cid) values ('1257463');
insert into t_category_export (cid) values ('1257466');
insert into t_category_export (cid) values ('1257465');
insert into t_category_export (cid) values ('1257467');
insert into t_category_export (cid) values ('1257469');
insert into t_category_export (cid) values ('1257468');
insert into t_category_export (cid) values ('1257471');
insert into t_category_export (cid) values ('1257470');


---------------end-----------------
--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.042_SSMS','MM1.0.0.053_SSMS');


commit;