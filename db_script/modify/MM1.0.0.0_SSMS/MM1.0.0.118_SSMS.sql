---�������ݵ����˵�Ȩ��
insert into t_right r values('1_0807_RES_BLACKLIST','�������ݵ���','�������ݵ���','2_0801_RESOURCE',0);
insert into t_roleright(ROLEID,RIGHTID) values(1,'1_0807_RES_BLACKLIST');


alter table T_CONTENT_BACKLIST add create_time DATE default sysdate not null ;
alter table T_CONTENT_BACKLIST add modify_time DATE default sysdate not null ;
alter table T_CONTENT_BACKLIST add black_type NUMBER(2) default 1 not null ;
comment on column T_CONTENT_BACKLIST.create_time is '����ʱ��';
comment on column T_CONTENT_BACKLIST.modify_time is '�޸�ʱ��';
comment on column T_CONTENT_BACKLIST.black_type is '���������ͣ� 1.����ˢ�� 2.�״�ˢ�� 3.���ˢ��  ��2�����ϣ�';

insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ADD_BLACK_OK', '����{0}����������ɹ���', '���ݼ���������ɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_ADD_BLACK_FAIL', '����{0}���������ʧ�ܣ�', '���ݼ��������ʧ��');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_BLACK_EXIST', '����{0}�Ѿ��ں������У�', '�����Ѿ��ں�������');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODIFY_BLACK_OK', '�޸ĺ���������{0}�ɹ���', '�޸ĺ��������ݳɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_MODIFY_BLACK_FAIL', '�޸ĺ���������{0}ʧ�ܣ�', '�޸ĺ���������ʧ��');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DEL_BLACK_OK', 'ɾ������������{0}�ɹ���', 'ɾ�����������ݳɹ�');
insert into t_resource(resourceKey,resourceValue,remard) values('WEB_INF_DEL_BLACK_FAIL', 'ɾ������������{0}ʧ�ܣ�', 'ɾ������������ʧ��');

---�������ַ���
-- Create table
create table T_MB_MUSIC
(
  MUSICID    VARCHAR2(30) not null,
  SONGNAME   VARCHAR2(200),
  SINGER     VARCHAR2(100),
  VALIDITY   VARCHAR2(512),
  UPDATETIME VARCHAR2(30),
  CREATETIME VARCHAR2(30),
  DELFLAG    NUMBER(4) default 0 not null
);
-- Add comments to the columns 
comment on column T_MB_MUSIC.MUSICID
  is '����ID';
comment on column T_MB_MUSIC.SONGNAME
  is '��������';
comment on column T_MB_MUSIC.SINGER
  is '��������';
comment on column T_MB_MUSIC.VALIDITY
  is '��Ч��';
comment on column T_MB_MUSIC.UPDATETIME
  is '����ʱ��';
comment on column T_MB_MUSIC.CREATETIME
  is '����ʱ��';
  comment on column T_MB_MUSIC.DELFLAG
  is 'ɾ����ǣ�0��δɾ����1����ɾ��';
-- Create/Recreate indexes 
create unique index PK_MB_MUSIC_ID on T_MB_MUSIC (MUSICID);

- Create table
create table T_MB_CATEGORY
(
  CATEGORYID       VARCHAR2(30) not null,
  CATEGORYNAME     VARCHAR2(200) not null,
  PARENTCATEGORYID VARCHAR2(30),
  TYPE             VARCHAR2(10),
  DELFLAG          VARCHAR2(2),
  CREATETIME       VARCHAR2(30),
  CATEGORYDESC     VARCHAR2(500),
  SORTID           NUMBER(8),
  SUM              NUMBER(8) default 0 not null
);
-- Add comments to the columns 
comment on column T_MB_CATEGORY.CATEGORYID
  is '����ID';
comment on column T_MB_CATEGORY.CATEGORYNAME
  is '��������';
comment on column T_MB_CATEGORY.PARENTCATEGORYID
  is '���ܸ�ID';
comment on column T_MB_CATEGORY.TYPE
  is '����';
comment on column T_MB_CATEGORY.DELFLAG
  is 'ɾ�����';
comment on column T_MB_CATEGORY.CREATETIME
  is '����ʱ��';
comment on column T_MB_CATEGORY.CATEGORYDESC
  is '��������';
comment on column T_MB_CATEGORY.SORTID
  is '��������';
comment on column T_MB_CATEGORY.SUM
  is '������Ʒ����';
-- Create/Recreate indexes 
create unique index PK_T_MB_CATEGORY_ID on T_MB_CATEGORY (CATEGORYID);

-- Create table
create table T_MB_REFERENCE
(
  MUSICID    VARCHAR2(30) not null,
  CATEGORYID VARCHAR2(30) not null,
  MUSICNAME  VARCHAR2(200),
  CREATETIME VARCHAR2(30),
  SORTID     NUMBER(8)
  
);
-- Add comments to the columns 
comment on column T_MB_REFERENCE.MUSICID
  is '����ID';
comment on column T_MB_REFERENCE.CATEGORYID
  is '����ID';
comment on column T_MB_REFERENCE.CREATETIME
  is '����ʱ��';
comment on column T_MB_REFERENCE.SORTID
  is '����';
comment on column T_MB_REFERENCE.MUSICNAME
  is '��������';
-- Create/Recreate indexes 
create index INDEX_TB_REFERENCE_CAID on T_MB_REFERENCE (CATEGORYID);
create unique index PK_MUSIC_CATEID on T_MB_REFERENCE (MUSICID, CATEGORYID);

------��Ȩ���ն��Ż�ʹ��
grant select on T_MB_MUSIC to &portalmo;--�ն��Ż�
grant select on T_MB_CATEGORY to &portalmo;--�ն��Ż�
grant select on T_MB_REFERENCE to &portalmo;--�ն��Ż�


insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100002166', '����', '', '1', '0', '2010-05-10 09:03:00', '����', 0, 0);

insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100000445', '����', '', '1', '0', '2010-05-10 09:03:00', '����', 0, 0);

insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100001913', 'ר��', '', '1', '0', '2010-05-10 09:03:00', 'ר��', 0, 0);

insert into t_mb_category (CATEGORYID, CATEGORYNAME, PARENTCATEGORYID, TYPE, DELFLAG, CREATETIME, CATEGORYDESC, SORTID, SUM)
values ('100001914', '����', '', '1', '0', '2010-05-10 09:03:00', '����', 0, 0);
---��������
-- Create sequence 
create sequence SEQ_BM_CATEGORY_ID  minvalue 100002167  maxvalue 999999999  start with 100004206  increment by 1  nocache  cycle;



--�޸�ע��
comment on column T_CATERULE_COND.CID is '��ȡ���ܵĻ�������.�����ֶ���Ч';
comment on column T_R_GCONTENT.ONLINETYPE  is '1������Ӧ�ã�2������Ӧ��';

--G+��Ϸ���޸ı�ṹ
alter table T_GAME_BASE add state varchar2(2) default 1 not null;
comment on column T_GAME_BASE.state
  is '��ǰ״̬1:�½�,2:����,3:ɾ��';

--С���Ƽ�
alter table T_CONTENT_COUNT add recommend_grade number(6,2);
comment on column T_CONTENT_COUNT.recommend_grade
  is '�Ƽ�����';

--�����ṹ
alter table T_CONTENT_COUNT modify LATESTCOUNT default null null;
alter table T_CONTENT_COUNT modify RECOMMENDCOUNT default null null;
alter table T_CONTENT_COUNT modify COMPECOUNT default null null;  
  
--ʹ��ԭ��������dblink��PPMSTOSSMS����ͬ���
create or replace synonym ppms_v_cm_content_recommend for v_cm_content_recommend@PPMSTOSSMS;

----ap����Ч�ڹ���Ӧ��
--ʹ��ԭ��������dblink��PPMSTOSSMS����ͬ���
create or replace synonym v_valid_company for v_valid_company@PPMSTOSSMS;

create or replace view ppms_v_cm_content as
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
              decode(f.status||f.substatus, '61', '0006','0008'),
              '1015',
              decode(f.status||f.substatus, '61', '0006','0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L',d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--ȫ��ȡonlinedate��ʡ��ȡSubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap����
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
   and d. ProductStatus  in ('2','3','5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype in ('1','2')--����widgetӦ��;
;
----����ppms_v_service ��ͼ ��ӻ�����Ϸ��ҵ�����ݹ���
create or replace view ppms_v_service as
select p.contentid,
       v1.apcode as icpcode,
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
       v2.paytype,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       decode(p.chargetime || v2.paytype, '20', p.feedesc, v2.chargedesc) as chargeDesc,
       v2.ProviderType,
       v2.servattr,
       v2.Description as servdesc,
       v1.apcode || '_' || v2.ServiceCode as pksid,
       v2.LUPDDate
  from v_valid_company    v1,
       v_om_product       v2,
       OM_PRODUCT_CONTENT p,
       cm_content         c
 where p.contentid = c.contentid
   and c.companyid = v1.companyid
   and p.id = v2.id
   and c.thirdapptype in ('1', '2')
  UNION ALL
 select
       t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--���߼Ʒ�
       null,
       t.servflag,
       t.chargetype,
       null,
       t.mobileprice,
       null,
       t.chargedesc,
       'B',
       'G',--ȫ��ҵ��
       t.servdesc,
       null,
       t.lupddate
  from t_game_service t;



drop materialized view V_THIRD_SERVICE;
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
  from v_valid_company v1, v_om_product v2
 where v1.companyid = v2.CompanyID
   and v1.icptype = 1
   and v2.ProviderType = 'V';

------����5���Զ��ϼܹ���
insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (152, 'WWW�Ż�С���Ƽ�', 0, 0, 1, null, 100);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (152, '', 10, 'c.RECOMMEND_GRADE is not null', '', -1, null);

insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('17657767', 152, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (153, 'WWW�Ż�С���Ƽ�����', 0, 0, 1, null, 0);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (153, '', 10, 'c.RECOMMEND_GRADE is not null', 'dayOrderTimes desc,c.RECOMMEND_GRADE desc,createdate desc,mobilePrice desc,name asc', -1, null);

insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('17657766', 153, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


----�ն��Ż�С���Ƽ��Զ��ϼܹ�����Ҫ�޸�cid17657767

insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (157, '�ն��Ż�С���Ƽ�', 0, 0, 1, null, 100);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (157, '', 10, 'c.RECOMMEND_GRADE is not null', '', -1, null);

---��Ʒ��
insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID)
values (157, '18147540', 1, '', 'sortID desc,marketDate desc', 10, null);

insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('18147539', 157, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));




--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.097_SSMS','MM1.0.0.118_SSMS');
commit;