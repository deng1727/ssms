drop  synonym REPORT_2012_CYLIST;

drop  table T_CYLIST;

drop  table T_CYLIST_TRA;

update t_r_gcontent c set c.provider='' where c.subtype='6';


create or replace view ppms_v_cm_content_cy as
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
              decode(f.status || f.substatus, '61', '0006', '0008'),
              '1015',
              decode(f.status || f.substatus, '61', '0006', '0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L', d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,
              '0006',
              f.onlinedate,
              '1006',
              f.onlinedate,
              f.SubOnlineDate) as marketdate, --ȫ��ȡonlinedate��ʡ��ȡSubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       p.developername companyname,
       p.contestgroup as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate,
       f.chargeTime,
       c.thirdapptype,
       p.province as pvcid,
       p.cityid,
       c.contestcode,
       c.contestyear,
       q.COLLEGE as college,
       q.COLLEGEID as collegeId,
       decode(c.hatchappid, null, decode(c.contenttype, '1002', '0', '1'), '0') as othernet

  from cm_content_type      a,
       cm_catalog           b,
       cm_content           c,
       v_om_product         d,
       v_valid_company      e,
       OM_PRODUCT_CONTENT   f,
       OM_DEVELOPER_CONTEST p,
       cm_content_college   q
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and p.college = q.COLLEGEID(+)
   and c.companyid = e.companyid ----ap����
   and (c.status = '0006' or c.status = '1006')
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
      --and d. ProductStatus  in ('2','3','5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.Status in ('2', '3') ----��Ʒ���߼Ʒ�or���Ʒ�or  ȥ��������
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;

---�������ػع���ʼ
drop sequence SEQ_CB_CATEGORY_ID;
drop table T_CB_CATEGORY;

---�������ػع�����

-----��������IDΪ�����Ļ���ID----

update t_sync_tactic_cy  t set t.categoryid='109990032' where t.id='7';---��Ц
update t_sync_tactic_cy  t set t.categoryid='109990034' where t.id='6';---��Ƶ
update t_sync_tactic_cy  t set t.categoryid='109990035' where t.id='5';---ͨѶ
update t_sync_tactic_cy  t set t.categoryid='109990037' where t.id='4';---����
update t_sync_tactic_cy  t set t.categoryid='109990036' where t.id='8';---����
update t_sync_tactic_cy  t set t.categoryid='109990030' where t.id='1';---���
update t_sync_tactic_cy  t set t.categoryid='109990031' where t.id='2';---��Ϸ
update t_sync_tactic_cy  t set t.categoryid='109990033' where t.id='3';---����

delete from  t_category_carveout_sqlbase  where id='7';

delete from t_category_carveout_rule where id in (35,36,37,38,39,40);


delete DBVERSION where PATCHVERSION = 'MM1.1.1.049_SSMS' and LASTDBVERSION = 'MM1.1.1.039_SSMS';
commit;