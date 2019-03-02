-- �������ֶΡ�

alter table t_r_gcontent add funcdesc varchar2(2000);
alter table t_r_category add multiurl varchar2(1000);

-- Add comments to the columns 
comment on column t_r_gcontent.funcdesc
  is '�¹��ܽ���';
comment on column t_r_category.multiurl
  is '����URLΪ��֧���ն��Ż�����url';



-- Create table
create table t_caterule_cond_base
(
  BASE_ID   NUMBER(8) not null,
  BASE_NAME VARCHAR2(100) not null,
  BASE_SQL  VARCHAR2(1000) not null
)
;
-- Add comments to the columns 
comment on column t_caterule_cond_base.base_id
  is '���������';
comment on column t_caterule_cond_base.base_name
  is '�����������';
comment on column t_caterule_cond_base.base_sql
  is '�������';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CATERULE_COND_BASE
  add constraint P_COND_BASE_PK primary key (BASE_ID);


-- Add/modify columns 
alter table T_CATERULE_COND add baseCondId NUMBER(8) default 0 not null;
-- Add comments to the columns 
comment on column T_CATERULE_COND.baseCondId
  is '������������sql���';


update t_caterule_cond t set t.basecondid = t.condtype;


insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (1, '�Ӿ�Ʒ���ȡ', 'select b.id from  t_r_base b,t_r_reference r,  t_r_gcontent g,v_service v where b.id=g.id and r.categoryid =? and r.refnodeid = g.id and  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')  and g.icpcode = v.icpcode(+) and g.icpservid = v.icpservid(+)');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (10, '�Ӳ�Ʒ���ȡ����ҵ���������Ϸ�����⣩������������Ϸ', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid(+) and g.provider !=''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (11, '�Ӳ�Ʒ����ȡ������Ϸҵ��', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid(+) and g.provider=''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (12, '�Ӳ�Ʒ���ȡ������ҵ��', 'select id from t_r_gcontent g where g.subtype is null or  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (13, '��Ʒ�Ƶ���ȡ�ײ�ҵ��', 'select b.id from t_r_base b, t_r_gcontent g, v_service v, t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = c.contentid(+) and g.provider != ''B'' and g.subtype = ''11''');
insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (14, '�ӻ���ҵ���ȡ', 'select b.id from t_r_base b, t_r_gcontent g, v_service v, t_content_count c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = c.contentid(+)');



-- Create table
drop table T_R_SEVENDAYSCOMPARED;

create table T_R_SEVENDAYSCOMPARED
(
  CONTENTID  VARCHAR2(30) not null,
  AOLDNUMBER NUMBER(10),
  ANEWNUMBER NUMBER(10),
  OOLDNUMBER NUMBER(10),
  ONEWNUMBER NUMBER(10)
);

-- Add comments to the columns 
comment on column T_R_SEVENDAYSCOMPARED.CONTENTID
  is '�������룬��Ӧ������������룬��Ӧ��Ѷ��ý��ID����Ӧȫ���ĸ���ID��';
comment on column T_R_SEVENDAYSCOMPARED.AOLDNUMBER
  is '��׿����ϵͳ��һ������������ͳ��';
comment on column T_R_SEVENDAYSCOMPARED.ANEWNUMBER
  is '��׿����ϵͳ��ǰ����������ͳ��';
comment on column T_R_SEVENDAYSCOMPARED.OOLDNUMBER
  is 'ophone����ϵͳ��һ������������ͳ��';
comment on column T_R_SEVENDAYSCOMPARED.ONEWNUMBER
  is 'ophone����ϵͳ��ǰ����������ͳ��';


  -- Create the synonym 
create or replace synonym report_servenday
  for V_PPS_PLATFORM_DOWN_D@REPORT105.ORACLE.COM;

create table t_r_servenday_temp as
  select * from report_servenday where 1 = 2;


-- Create table
create table T_R_SEVENCOMPARED
(
  CONTENTID VARCHAR2(30) not null,
  ONUMBER   NUMBER(10),
  ANUMBER   NUMBER(10)
)
;
-- Add comments to the columns 
comment on column T_R_SEVENCOMPARED.CONTENTID
  is '�������룬��Ӧ������������룬��Ӧ��Ѷ��ý��ID����Ӧȫ���ĸ���ID��';
comment on column T_R_SEVENCOMPARED.ONUMBER
  is 'ophone����ϵͳ��ǰ������������ֵͳ��';
comment on column T_R_SEVENCOMPARED.ANUMBER
  is '��׿����ϵͳ��ǰ������������ֵͳ��';


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.100_SSMS','MM1.0.3.103_SSMS');
commit;


