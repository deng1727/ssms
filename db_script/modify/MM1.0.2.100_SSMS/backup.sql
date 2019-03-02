create table t_r_reference_bak as select * from t_r_reference;

---Ë÷Òý
alter table t_r_reference_bak
  add constraint PK_t_r_reference_bak primary key (ID);
create index IDX_t_r_reference_bak_CID on t_r_reference_bak (CATEGORYID);
create unique index INDX_T_R_REF_bak_GOSID on t_r_reference_bak (GOODSID);
create index IND_t_r_reference_bak on t_r_reference_bak (REFNODEID);


create table T_R_CATEGORY_bak as select * from T_R_CATEGORY;

alter table T_R_CATEGORY_bak
  add constraint PK_T_R_CATEGORY_bak primary key (ID);
create index IDX_T_R_CATEGORY_bak_CID on T_R_CATEGORY_bak (CATEGORYID);
create index IDX_T_R_CATEGORY_bak_PCID on T_R_CATEGORY_bak (PARENTCATEGORYID);

create table t_r_base_bak as select * from t_r_base;

alter table t_r_base_bak
  add constraint PK_t_r_base_bak primary key (ID);
create index INDEX1_t_r_base_bak on t_r_base_bak (PARENTID);  

create table t_sync_tactic_bak as select * from t_sync_tactic;
alter table t_sync_tactic_bak
  add constraint PK_t_sync_tactic_bak primary key (ID);

rename SEQ_SYNC_TACTIC_ID to SEQ_SYNC_TACTIC_ID_bak;
-- Create sequence 
create sequence SEQ_SYNC_TACTIC_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1;