-- Drop columns 
alter table T_CONTENT_COUNT drop column COMPECOUNT;
---ɾ��MO_widgetר���Զ��ϼܹ���  T_CATEGORY_RULE ��cidΪ���»���
delete from T_CATEGORY_RULE where ruleid='138';
delete from T_CATERULE where ruleid='138';
delete from T_CATERULE_COND where ruleid='138';

  -- Drop columns 
alter table T_R_GCONTENT drop column subtype;

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
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate, nvl(f.onlinedate, to_date('2000-01-01', 'yyyy-mm-dd ')),nvl(f.offlinedate,  to_date('2000-01-01', 'yyyy-mm-dd ')) )as lupddate,
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

update t_r_category c set c.state=1 where c.id in ('1257461','1257465','1257470');
update t_r_category c set c.state=1 where c.id in (select id from t_r_base b where b.parentid in ('1257461','1257465','1257470') and b.type='nt:category');
--ɾ���汾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.095_SSMS' and LASTDBVERSION = 'MM1.0.0.093_SSMS';
commit;