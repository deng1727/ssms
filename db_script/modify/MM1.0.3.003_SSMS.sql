create table T_R_GCONTENT_HIS
(
  CONTENTID VARCHAR2(30),
  DELTIME   VARCHAR2(20),
  TYPE      VARCHAR2(2)
);
-- Add comments to the columns 
comment on column T_R_GCONTENT_HIS.CONTENTID
  is '内容内码';
comment on column T_R_GCONTENT_HIS.DELTIME
  is '删除日期';
comment on column T_R_GCONTENT_HIS.TYPE
  is '内容类型。1：mm应用 2：创业大赛应用 3：游戏应用';


insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (210, '基地游戏网游', 0, 0, 1, 0, 0);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (210, '', 11, 'onLinetype=''2''', '', -1, 1, 1432);


----货架ID为现网货架ID
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('72569574', 210, to_date('07-01-2011 09:34:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2011 15:09:08', 'dd-mm-yyyy hh24:mi:ss'));




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.001_SSMS','MM1.0.3.003_SSMS');
commit;