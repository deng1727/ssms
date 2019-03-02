-- ͬ�������
create table T_SYN_RESULT
(
  ID          NUMBER(10) not null,
  CONTENTID   VARCHAR2(30) not null,
  CONTENTNAME VARCHAR2(300) not null,
  SYNTYPE     NUMBER(2) not null,
  SYNTIME     VARCHAR2(30) not null,
  constraint T_SYN_RESULT primary key (ID)
);

-- Add comments to the columns 
comment on column T_SYN_RESULT.CONTENTID
  is '����ID';
comment on column T_SYN_RESULT.CONTENTNAME
  is '��������';
comment on column T_SYN_RESULT.SYNTYPE
  is 'ͬ������: 1����;2����; 3����; 4ʧ�� ';
comment on column T_SYN_RESULT.SYNTIME
  is 'ͬ��ʱ�䣺YYYY-MM-DD hh24:mm:ss';

declare 
ruleid number(8);
begin
--���ػ�Ӧ��������¹���
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule t values(ruleid,'���ػ�Ӧ�����',0,0,1,0,0);
insert into t_caterule_cond values(ruleid,null,10,'type=''nt:gcontent:appSoftWare'' and cityid <> ''0000''',null,-1,1,SEQ_CATERULE_COND_ID.Nextval);
insert into t_category_rule t values('28731389',ruleid,null,sysdate);
--������Ϸ��Ʒ�������¹���
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule t values(ruleid,'������Ϸ��Ʒ����',0,0,1,0,0);
insert into t_caterule_cond values(ruleid,null,11,'chargetime=''1''',null,-1,1,SEQ_CATERULE_COND_ID.Nextval);
insert into t_category_rule t values('28731388',ruleid,null,sysdate);
--������Ϸ����ר�����¹���
select Seq_Caterule_Id.Nextval into ruleid from dual;
insert into t_caterule t values(ruleid,'������Ϸ����ר��',0,0,1,0,0);
insert into t_caterule_cond values(ruleid,null,11,'chargetime=''2''',null,-1,1,SEQ_CATERULE_COND_ID.Nextval);
insert into t_category_rule t values('21665492',ruleid,null,sysdate);
commit;
end;
/
  
-- ͬ�����������id  
create sequence SEQ_T_SYN_RESULT_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
nocache
cycle;

-- ��� ����Ӫ�� �����ݵ����Ƽ�����ѡ������� ����categoryid�ͻ�������
create table T_CATEGORY_TRAIN
(
  CID   VARCHAR2(30),
  CNAME VARCHAR2(200)
);
-- Create/Recreate indexes 
create index CID_INDEX1 on T_CATEGORY_TRAIN (CID);
insert into t_category_train (CID, CNAME)
values ('100000585', '���');

insert into t_category_train (CID, CNAME)
values ('100000584', '�Ƽ�');

insert into t_category_train (CID, CNAME)
values ('100000743', '������');



-------�ṩ��������ϵͳ��ͼ
----mo
CREATE OR REPLACE VIEW V_MO_HOTSER AS
SELECT /*+RULE*/
 r.goodsid,
 g.id productid,
 g.name,
 g.keywords,
 g.catename,
 g.appcatename,
 g.introduction
  from t_r_Gcontent g, t_r_reference r, t_r_category t, t_sync_tactic c
 where t.id = c.Categoryid
   and t.relation like '%O%'
   and g.id = r.refnodeid
   and r.categoryid = t.CATEGORYID;
   
----www
CREATE OR REPLACE VIEW V_WWW_HOTSER AS
SELECT /*+RULE*/
 r.goodsid,
 g.id productid,
 g.name,
 g.keywords,
 g.catename,
 g.appcatename,
 g.introduction
  from t_r_Gcontent g, t_r_reference r, t_r_category t, t_sync_tactic c
 where t.id = c.Categoryid
   and t.relation like '%W%'
   and g.id = r.refnodeid
   and r.categoryid = t.CATEGORYID;

   
-----wap
CREATE OR REPLACE VIEW V_WAP_HOTSER AS
SELECT /*+RULE*/
 r.goodsid,
 g.id productid,
 g.name,
 g.keywords,
 g.catename,
 g.appcatename,
 g.introduction
  from t_r_Gcontent g, t_r_reference r, t_r_category t, t_sync_tactic c
 where t.id = c.Categoryid
   and t.relation like '%A%'
   and g.id = r.refnodeid
   and r.categoryid = t.CATEGORYID;







insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.040_SSMS','MM1.0.2.045_SSMS');
commit;