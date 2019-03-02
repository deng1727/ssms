-- ������������PPMS070�汾����
-- Create table
create table T_GAME_SERVICE
(
  ICPCODE     VARCHAR2(16),
  SPNAME      VARCHAR2(200) not null,
  ICPSERVID   VARCHAR2(16) not null,
  SERVNAME    VARCHAR2(50) not null,
  SERVDESC    VARCHAR2(200),
  CHARGETYPE  VARCHAR2(2),
  CHARGEDESC  VARCHAR2(200),
  MOBILEPRICE NUMBER(8),
  LUPDDATE    DATE not null,
  SERVTYPE    NUMBER(2) not null,
  SERVFLAG    NUMBER(1),
  PTYPEID     NUMBER(2) not null
);
-- Add comments to the columns 
comment on column T_GAME_SERVICE.ICPCODE
  is 'CP�ĺ�������';
comment on column T_GAME_SERVICE.SPNAME
  is 'CP����';
comment on column T_GAME_SERVICE.ICPSERVID
  is '��Ʒ��ҵ�����';
comment on column T_GAME_SERVICE.SERVNAME
  is '��Ʒ����';
comment on column T_GAME_SERVICE.SERVDESC
  is '��Ʒ���';
comment on column T_GAME_SERVICE.CHARGETYPE
  is '�Ʒ�����

01 �����
02 ������
03 ������
04 ������
05 ������
06 ���״�ʹ�ð���
07�������ݼƷ�
08�����ζ����ݼƷ�
';
comment on column T_GAME_SERVICE.CHARGEDESC
  is '�Ʒ�����';
comment on column T_GAME_SERVICE.MOBILEPRICE
  is '�ʷѣ���λ��';
comment on column T_GAME_SERVICE.LUPDDATE
  is 'ҵ��������ʱ��';
comment on column T_GAME_SERVICE.SERVTYPE
  is 'ҵ�����͡�1:�ͻ��˵���,2:�ͻ�������,3:WAP����,4:WAP����
ֻ��1�ͻ��˵����ġ�
';
comment on column T_GAME_SERVICE.SERVFLAG
  is 'ҵ���ʶ��0:��ͨҵ��,1:�ײ���ҵ��';
comment on column T_GAME_SERVICE.PTYPEID
  is 'ҵ���ƹ㷽ʽ��1	���ѹ���ͻ��˵�����Ϸ
2	�ͻ������Σ������������
3	�������ι���WAP��Ϸ
4	���WAP��Ϸ
5	WAP���Σ������������
6	����ת������ѹ���ͻ��˵�����Ϸ
7	����������ת������ѹ���ͻ��˵�����Ϸ
8	�����棬���ѹ���ͻ��˵�����Ϸ
9	���Ѱ��ι���WAP��Ϸ
10	���żƷ�����
11	����ת������żƷѵ�����Ϸ
12	�����ۿ۰棬���ѹ���ͻ��˵�����Ϸ
13	����ת���������棬���żƷѵ�����Ϸ
14	5Ԫ�����ײ�����ѿͻ��˵�����Ϸ
15	5Ԫ�����ײ��ڴ��ۿͻ��˵�����Ϸ
16	TDҵ�񣬻��ѹ���ͻ��˵�����Ϸ
';

-- Create/Recreate indexes 
create unique index INDEXT_T_GAME_SERVICE_KEY on T_GAME_SERVICE (ICPCODE, ICPSERVID);

drop materialized view V_SERVICE;
create materialized view V_SERVICE
refresh force on demand
as
select v1.apcode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,
              '00',
              'S',
              '01',
              'W',
              '02',
              'M',
              '10',
              'A',
              '05',
              'E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       v2.ChargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from om_company         v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
      UNION ALL
 select t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--���߼Ʒ�
       null,
       null,
       t.chargetype,
       t.mobileprice,
       null,
       t.chargedesc,
       'B',
       'G',--ȫ��ҵ��
       t.servdesc,
       null,
       t.lupddate
  from t_game_service t   ;

create unique index index_v_service_pk on V_SERVICE (icpcode, icpservid, providertype);

--����ͬ���ĵ�����ҵ����Ϣ�ﻯ��ͼ  
create materialized view V_THIRD_SERVICE
refresh force on demand
as
select v1.apcode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,
              '00',
              'S',
              '01',
              'W',
              '02',
              'M',
              '10',
              'A',
              '05',
              'E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       v2.ChargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from om_company v1, v_om_product v2
 where v1.companyid = v2.CompanyID
   and v1.icptype = 1
   and v2.ProviderType = 'V';

--�ڻ��ܹ���ϵͳ(SSMS)���ݿ��û�&SSMS����Ȩ���ն��Ż�(MOPPS)
grant select on V_THIRD_SERVICE to &portalmo;--�ն��Ż�

--- ����ͼ�Ĵ���������PPMSϵͳ��OM_PRODUCT_CONTENT���substatus�ֶ���ӡ�
--����CMS��������ͼ
create or replace view v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       c.ContentCode,
       c.Keywords,
       decode(c.status,
              '0006',
              decode(f.status, 2, '0006', 5, '0008'),
              '1006',
              decode(f.status, 2, '0006', 5, '0008'),
              '0015',
              decode(f.substatus, '1', '0006', '0', '0008'),
              '1015',
              decode(f.substatus, '1', '0006', '0', '0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L', c.status, 'G') as ServAttr,
       c.createdate,
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--ȫ��ȡonlinedate��ʡ��ȡSubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate) as lupddate,
       f.chargeTime
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company         e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap����
   and (c.status = '0006' or c.status = '1006' or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
   and (d. ProductStatus = '2' or d. ProductStatus = '3') ----��Ʒ���߼Ʒ�or���Ʒ�
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate;


-- ������Ϸ����ӳ���
create table T_GAME_CATE_MAPPING
(
  BASECATENAME VARCHAR2(100) not null,
  MMCATENAME   VARCHAR2(100)
);
-- Add comments to the columns 
comment on column T_GAME_CATE_MAPPING.BASECATENAME
  is '���ط�������';
comment on column T_GAME_CATE_MAPPING.MMCATENAME
  is 'MM��������';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_CATE_MAPPING
  add primary key (BASECATENAME);


insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('MMORPG(����)', '����');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����', '��������');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����ð��', '������');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����ð��', 'ð��ģ��');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����', '����Ȥζ');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����', '������');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����', '��������');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����', '���Իغ�');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����', '��������');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('��ɫ����', '��ɫ����');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('���', '�������');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('����', '��������');
insert into T_GAME_CATE_MAPPING (BASECATENAME, MMCATENAME)
values ('��������', '������');

-- ������ϷUAӳ���
create table T_GAME_UA_MAPPING
(
  BASEUA     VARCHAR2(500) not null,
  DEVICENAME VARCHAR2(500)
);
-- Add comments to the columns 
comment on column T_GAME_UA_MAPPING.BASEUA
  is '����UA��Ϣ';
comment on column T_GAME_UA_MAPPING.DEVICENAME
  is 'MM��Ӧ����UA��devicename';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_GAME_UA_MAPPING
  add primary key (BASEUA);

alter table T_R_GCONTENT add SERVATTR varchar2(1) default 'G';
--mopas ��Ҫ���´���t_r_gcontent �ﻯ��ͼ
--��һ��ȫ��ͬ��
delete from t_lastsynctime;


---######################################
---####   �����Զ����½ű�    ##################
---######################################

-- ȥ�����������͵����ݼ���
alter table T_CATERULE_COND
  drop constraint CKC_CONDTYPE_T_CATERU;

comment on column T_CATERULE_COND.CONDTYPE
  is '�������� 10���Ӳ�Ʒ���ȡ����ҵ���������Ϸ�����⣩��12���Ӳ�Ʒ���ȡ������ҵ��1���Ӿ�Ʒ���ȡ��';
  alter table T_CATERULE_COND modify CONDTYPE NUMBER(2);

  comment on column T_CATERULE_COND.CID
  is '��ȡ���ܵĻ������룬���CondType=1���ֶ���Ч';
  --�޸��������ݡ���������0���10

  comment on column T_CATERULE.RULETYPE
  is '�������� 0��ˢ�»�������Ʒ��1����������Ʒ����˳��
��5���������ͼ����Ӫ�Ƽ�ͼ�顣';

  update t_caterule_cond t set t.condtype=10 where  t.condtype=0;

 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1870595', 100, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826727', 101, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1825622', 102, NULL, SYSDATE);

 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826847', 103, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826846', 104, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('1826845', 105, NULL, SYSDATE);
 Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('2017', 106, NULL, SYSDATE);

//2017  ����ͼ�������ܣ���ʾͼ������Ƽ����ǻ��ܣ�
---------------

   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(100, 'MM�������Ѱ�', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(101, 'MM���ֵ����', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(102, '�����Ƽ�', 0, 0, 1,NULL);

    Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(103, '���䳩�����а�', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(104, '����ԭ�����а�', 0, 0, 1,NULL);
   Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                       Values(105, '����ͼ������', 0, 0, 1,NULL);
    Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                       Values(106, 'ͼ������Ƽ����ǻ��ܣ�', 5, 0, 1,NULL);
------------------------------------------------------------------------------------------------------------


Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(100, '3242950', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(100, null, 12, 'substr(g.id,1,1)=''m''', 'dbms_random.value',20, NULL);
 
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                 Values(101, '3242951', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(101, null, 12, 'substr(g.id,1,1)=''m''', 'dbms_random.value',20, NULL);
 
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(102, '3242952', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(102, null, 12, 'substr(g.id,1,1)=''m''', 'dbms_random.value',10, NULL);

------------------
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(103, '3242947', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(103, null, 12, 'substr(g.id,1,1)=''r''', 'dbms_random.value',20, NULL);

 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                 Values(104, '3242948', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(104, null, 12, 'substr(g.id,1,1)=''r''', 'dbms_random.value',20, NULL);
 
 Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(105, '3242949', 1, null, 'sortID desc,marketDate desc',10, NULL);
 Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                               Values(105, null, 12, 'substr(g.id,1,1)=''r''', 'dbms_random.value',10, NULL);

create table v_cm_device_resource as
select *
  from ppms_CM_DEVICE_RESOURCE p
 where exists
 (select 1 from t_r_gcontent g where g.contentid = p.contentid);

--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.060_SSMS','MM1.0.0.070_SSMS');
commit;

