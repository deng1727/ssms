drop index IDX_COND_RULEID;
drop table T_CATERULE_COND cascade constraints;

drop table T_CATERULE cascade constraints;

drop table T_CATEGORY_RULE cascade constraints;
--�ָ�037�汾T_CATEGORY_RULE������
create table T_CATEGORY_RULE as select * from t_category_rule_ssms037_bak;


--��ԭ��ͼ
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
              c.status) as status,
       c.createdate,
       f.onlinedate as marketdate,
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
   and c.companyid = e.companyid
   and (c.status = '0006' or c.status = '1006' or c.status = '0008')
   and d.AuditStatus = '0003'
   and f.ID = d.ID
   and c.contentid = f.contentid;  

 --��wwwpas��mopas��pcpas�û���ִ�У�ɾ����
 drop synonym v_content_device;


 --�ڻ�����ɾ����ͼ

drop view v_content_device;

drop synonym T_DEVICE_BRAND;

--�����������ʧ�ܡ���Ҫ���ն��Ż�mopas��t_r_gcontent�ﻯ��ͼ���´�����
--��ֱ������������û�ִ�����½ű�
---------------start--------------------------------------------------
drop materialized view t_r_gcontent;

---mopas ��ִ�д����ﻯ��ͼ�ű�
create materialized view t_r_gcontent as select * from s_r_gcontent;
--���������������ﻯ��ͼ��Ӱ��

alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;

--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);

----------------end-----------------------------------------------------

-- ɾ�������ݵ������˵�Ȩ��
delete from t_right r where r.rightid='0_0801_RES_DATA_EXPORT';
 --ɾ���汾��Ϣ
delete DBVERSION where PATCHVERSION = 'MM1.0.0.040_SSMS' and LASTDBVERSION = 'MM1.0.0.037_SSMS';
commit;