--��ԭ t_category_name_mapping
create table t_category_name_mapping
(
  apptype     number(1) not null,
  appcatename varchar2(200) not null,
  thirdcatename varchar2(200) not null
)
;
create index INDEX_T_CATEGORY_NAME_MAPPING on T_CATEGORY_NAME_MAPPING (apptype, appcatename);

-- Add comments to the columns 
comment on column t_category_name_mapping.apptype
  is 'Ӧ�����ͣ�1 �����2 ��Ϸ��3 ����';
comment on column t_category_name_mapping.appcatename
  is 'ppms ����Ӧ�õĶ����������ƣ���ֵ��Դ�� t_r_gcontent �� appcatename��ֵ';
comment on column t_category_name_mapping.thirdcatename
  is '���£���ѣ��Ƽ����Ǽ������ж�Ӧ����������ʾ���ơ�';

  insert into t_category_name_mapping t values(1,'��ý�����','��ý�����');
  insert into t_category_name_mapping t values(1,'ʵ�����','ʵ�����');
  insert into t_category_name_mapping t values(1,'ͨ�Ÿ���','ͨ�Ÿ���');
  insert into t_category_name_mapping t values(1,'�������','�������');
  insert into t_category_name_mapping t values(1,'ϵͳ����','ϵͳ����');


  insert into t_category_name_mapping t values(2,'���Իغ�','���Իغ�');
  insert into t_category_name_mapping t values(2,'������','������');
  insert into t_category_name_mapping t values(2,'��ɫ����','��ɫ����');
  insert into t_category_name_mapping t values(2,'ð��ģ��','ð��ģ��');
  insert into t_category_name_mapping t values(2,'����','����');
  insert into t_category_name_mapping t values(2,'��������','��������');
  insert into t_category_name_mapping t values(2,'�������','�������');
  insert into t_category_name_mapping t values(2,'��������','��������');
  insert into t_category_name_mapping t values(2,'����Ȥζ','����Ȥζ');


  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'�羰','�羰');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'��ͨ','��ͨ');
  insert into t_category_name_mapping t values(3,'�Ƽ�','�Ƽ�');
  insert into t_category_name_mapping t values(3,'��ͼ','��ͼ');

  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'Ӱ��','Ӱ��');
  insert into t_category_name_mapping t values(3,'��Ϸ','��Ϸ');


drop table t_synctime_tmp;
create table t_synctime_tmp
(
  ID    NUMBER(10) not null,
  contentid  VARCHAR2(12) not null,
  name  VARCHAR2(300) not null,
  status  VARCHAR2(4),
  contentType  VARCHAR2(32),
  LUPDDate date,
  constraint KEY_synctime_tmp primary key (id)
);
--��ԭ v_cm_content
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
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
   and d. ProductStatus  in ('2','3','5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null);

--ɾ���汾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.092_SSMS' and LASTDBVERSION = 'MM1.0.0.090_SSMS';
commit;