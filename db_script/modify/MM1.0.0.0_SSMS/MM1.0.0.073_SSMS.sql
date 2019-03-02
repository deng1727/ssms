--�޸�v_cm_content��ͼ��֧�ֶ�������
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
   and (c.status = '0006' or c.status = '1006' or f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
   and d. ProductStatus  in ('2','3','5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate;

---������Ϸ�ļƷ������޸�Ϊ��A��
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
       t.servflag,
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

  --���� �����Ӧ��ϵ��
--����aaa����ʹ�������ġ�һԪ�ꡱ�Ļ��ܵ�id�滻��bbb��Ҫʹ�á���Ԫ�ꡱ�Ļ���id�滻 ��ע������id����ָt_r_category ���� id��
Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('aaa', 110, NULL, SYSDATE);
Insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
                                                Values   ('bbb', 111, NULL, SYSDATE);
  
---�����
Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(110, 'һԪ��', 0, 0, 1,NULL);
Insert into T_CATERULE(RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,EXCUTETIME)
                                        Values(111, '��Ԫ��', 0, 0, 1,NULL);
---��������
-----����ccc����ʹ�������ġ�һԪ�꾫Ʒ�⡱�Ļ��ܵ�id�滻��bbb��Ҫʹ�á���Ԫ�꾫Ʒ�⡱�Ļ���id�滻
Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(110, 'ccc', 1, null, 'sortID desc,marketDate desc',-1, NULL);
Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(110, null, 10, 'mobilePrice>0 and mobilePrice<=1000', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',-1, NULL);
                                                
Insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(111, 'ddd', 1, null, 'sortID desc,marketDate desc',-1, NULL);
Insert into T_CATERULE_COND(RULEID, CID, CONDTYPE, WSQL, OSQL,COUNT, SORTID)
                                                Values(111, null, 10, 'mobilePrice>1000 and mobilePrice<=2000', 'dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc',-1, NULL);
--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.070_SSMS','MM1.0.0.073_SSMS');
commit;

