create table T_R_GCONTENT_HIS
(
  CONTENTID VARCHAR2(30),
  DELTIME   VARCHAR2(20),
  TYPE      VARCHAR2(2)
);
-- Add comments to the columns 
comment on column T_R_GCONTENT_HIS.CONTENTID
  is '��������';
comment on column T_R_GCONTENT_HIS.DELTIME
  is 'ɾ������';
comment on column T_R_GCONTENT_HIS.TYPE
  is '�������͡�1��mmӦ�� 2����ҵ����Ӧ�� 3����ϷӦ��';


insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (210, '������Ϸ����', 0, 0, 1, 0, 0);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (210, '', 11, 'onLinetype=''2''', '', -1, 1, 1432);


----����IDΪ��������ID
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('72569574', 210, to_date('07-01-2011 09:34:46', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2011 15:09:08', 'dd-mm-yyyy hh24:mi:ss'));




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.001_SSMS','MM1.0.3.003_SSMS');
commit;