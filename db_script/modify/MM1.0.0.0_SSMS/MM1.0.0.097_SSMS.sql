-- ������Ϸ����Ϣ��
-- Create table
create table T_GAME_BASE
(
  PKGID       VARCHAR2(25) not null,
  PKGNAME     VARCHAR2(64) not null,
  PKGDESC     VARCHAR2(512) not null,
  CPNAME      VARCHAR2(64),
  SERVICECODE VARCHAR2(30),
  FEE         NUMBER not null,
  PKGURL      VARCHAR2(300) not null,
  PICURL1     VARCHAR2(255) not null,
  PICURL2     VARCHAR2(255) not null,
  PICURL3     VARCHAR2(255) not null,
  PICURL4     VARCHAR2(255) not null,
  UPDATETIME  DATE default sysdate not null,
  CREATETIME  DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_GAME_BASE.PKGID
  is '��Ϸ��id';
comment on column T_GAME_BASE.PKGNAME
  is '��Ϸ������';
comment on column T_GAME_BASE.PKGDESC
  is '��Ϸ������';
comment on column T_GAME_BASE.CPNAME
  is '������';
comment on column T_GAME_BASE.SERVICECODE
  is 'ҵ�����';
comment on column T_GAME_BASE.FEE
  is '����';
comment on column T_GAME_BASE.PKGURL
  is '��Ϸ�����URL';
comment on column T_GAME_BASE.PICURL1
  is '��� 30x30 picture1';
comment on column T_GAME_BASE.PICURL2
  is '��� 34x34 picture2';
comment on column T_GAME_BASE.PICURL3
  is '��� 50x50 picture3';
comment on column T_GAME_BASE.PICURL4
  is '��� 65x65 picture4';
comment on column T_GAME_BASE.UPDATETIME
  is '�������ʱ��';
comment on column T_GAME_BASE.CREATETIME
  is '����ʱ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_BASE
  add primary key (PKGID);

--------ͬһ��ʵ�������
-----��SSMS���ݿ��û�����Ȩ���Ż�PAS
grant select on T_GAME_BASE to &MOPAS;--�ն��Ż�PAS
---���ն�PAS�û��´���ͬ���
create synonym T_GAME_BASE  for &ssms.T_GAME_BASE;
--------ͬһ��ʵ�����end-------




-- Create table�������ܼ�ػ������ܱ�
create table T_CATEGORY_MONITOR
(
  ID               VARCHAR2(30),
  NAME             VARCHAR2(100),
  CATEGORYID       VARCHAR2(20),
  PARENTCATEGORYID VARCHAR2(20),
  CATEGORYTYPE     VARCHAR2(2)
);
create index INDEX_MONITOR_CATEGORYID on T_CATEGORY_MONITOR (CATEGORYID);
-- Add comments to the columns 
comment on column T_CATEGORY_MONITOR.ID
  is '��������ID';
comment on column T_CATEGORY_MONITOR.NAME
  is '��������';
comment on column T_CATEGORY_MONITOR.CATEGORYID
  is '����ID';
comment on column T_CATEGORY_MONITOR.PARENTCATEGORYID
  is '���ܸ�ID';
comment on column T_CATEGORY_MONITOR.CATEGORYTYPE
  is '�������ͣ�0,ֻȡ��������Ϣ;1,ȡ��������Ϣ�Լ�һ���ӻ�����Ϣ;9,ȡ��������Ϣ�Լ��������������Ϣ';

---��ʼ��������������
insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257453', '�ն˼��Ÿ�����', '100000339', '0', '0');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1810801', 'WWW�Ż�������', '100000434', '0', '0');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('2001', 'ȫ��', '100000008', '100000341', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1782547', '������', '100000384', '100000339', '9');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257454', '����', '100000340', '100000339', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257455', '���', '100000341', '100000339', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('1257456', '��Ϸ', '100000342', '100000339', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('2003', 'ȫ��', '100000010', '100000342', '1');

insert into t_category_monitor (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE)
values ('2002', 'ȫ��', '100000009', '100000340', '1');

--�ص���ͻ����ֻ����ظ���ͳ��
--��������Ŀ¼��ʱ���������Ŀ¼ �����Ľṹ��������Ҫ�����ʼ��
create table r_static_category_list (listname1 varchar2(100),listname2 varchar2(100),listname3 varchar2(100),listcat1 varchar2(20),listcat2 varchar2(20),categoryid varchar2(20));
--�������Ŀ¼ ��ʼ�� ������Ļ��ͻ���
insert into r_static_category_list(listcat2,listname3,categoryid) select a.parentcategoryid,a.name,a.categoryid from t_r_category a where a.delflag=0 and a.state='1'  and  a.parentcategoryid in ('100000346',
'100000348',
'100000353',
'100000345',
'100000349',
'100000354',
'100000343',
'100000350',
'100000355',
'100000344',
'100000352',
'100000357',
'100000347',
'100000351',
'100000356');
--���ø����������������
update r_static_category_list a set a.listname2=(select b.name from t_r_category b where a.listcat2=b.categoryid);
update r_static_category_list a set a.listcat1=(select b.parentcategoryid from t_r_category b where a.listcat2=b.categoryid);
update r_static_category_list a set a.listname1=(select b.name from t_r_category b where a.listcat1=b.categoryid);
--��������ʷ��
create table r_charts_turnhis(phdate varchar2(10),id varchar2(20),categoryid varchar2(20),rowlist number);

--������ʱ��1��ǰ30 �洢���������仯��Ӧ������
create table r_charts_turn01 as 
select a.categoryid,count(*) difcounts from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<31
and a.id not in (select b.id from r_charts_turnhis b 
where b.categoryid=a.categoryid and b.phdate=(to_char(sysdate-1,'yyyymmdd'))and b.rowlist<31) group by a.categoryid;
---������ʱ��2��ǰ30 ͳ�ƽ������а񵥵�Ӧ����
create table r_charts_turn02 as select a.categoryid,0 difcounts,count(*) counts,0 allcounts 
from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<31
group by a.categoryid;
--������ʱ��1��ǰ15 �洢���������仯��Ӧ������
create table r_charts_turn101 as 
select a.categoryid,count(*) difcounts from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<16
and a.id not in (select b.id from r_charts_turnhis b 
where b.categoryid=a.categoryid and b.phdate=(to_char(sysdate-1,'yyyymmdd'))and b.rowlist<16) group by a.categoryid;
---������ʱ��2��ǰ15 ͳ�ƽ������а񵥵�Ӧ����
create table r_charts_turn102 as select a.categoryid,0 difcounts,count(*) counts,0 allcounts 
from r_charts_turnhis a where a.phdate=to_char(sysdate,'yyyymmdd') and a.rowlist<16
group by a.categoryid;

-----5802����ͳ�Ʊ�
--����������ݲ�����ʷ��
create table  r_charts01 as select t.sortid,t.refnodeid,t.categoryid,to_char(sysdate,'yyyymmdd') phdate from t_r_reference t where 1 =2;
insert into  r_charts01 
select t.sortid,t.refnodeid,t.categoryid,to_char(sysdate,'yyyymmdd') phdate from t_r_reference t where t.categoryid in ('100000346',
'100000348',
'100000353',
'100000345',
'100000349',
'100000354',
'100000343',
'100000350',
'100000355',
'100000344',
'100000352',
'100000357',
'100000347',
'100000351',
'100000356');

----��5802�İ񵥲�����ʷ��
create table   r_charts02  as
select a.sortid,a.refnodeid,a.categoryid,a.phdate,b.catename,b.name,'δ֪����' catname from r_charts01 a,t_r_gcontent b where a.refnodeid=b.id 
and (b.devicename like '%5802%' or b.devicename02 like '%5802%')  and a.phdate in (to_char(sysdate,'yyyymmdd'),to_char(sysdate-1,'yyyymmdd')) 
order by a.sortid desc;

--��5802�ĵİ���ű�ע
create table r_charts03 as 
select  b.phdate,b.name,b.categoryid,b.catename,b.catname,
row_number() over (partition by b.categoryid,b.phdate order by b.sortid desc) rowlist
from  r_charts02 b;






--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.096_SSMS','MM1.0.0.097_SSMS');
commit;