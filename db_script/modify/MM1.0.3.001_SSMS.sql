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
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,
	--全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
decode(c.thirdapptype,'7','2',f.chargeTime)  chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or  f.status=5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status=6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and (c.thirdapptype in ('1','2','7','11')
       or (c.thirdapptype = '5'
          and c.Jilstatus = '1'));


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
   and (c.thirdapptype in ('1', '2','6','7','11') or (c.thirdapptype = '5' and c.Jilstatus = '1'))
  UNION ALL
 select
       t.contentid,
       t.icpcode,
       t.spname,
       null,
       t.icpservid,
       t.servname,
       'A',--上线计费
       null,
       8,
       t.chargetype,
       null,
       t.mobileprice,
       null,
       t.chargedesc,
       'B',
       'G',--全网业务
       t.servdesc,
       null,
       t.lupddate
  from t_game_service t;

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL,  EXCUTETIME, RANDOMFACTOR)
values (201, '品牌套餐规则', 0, 0, 1, 0, 0);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (201, null, 13, null, null, -1, 1, 1388);

--修改为现网货架id
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65579325', 201, null, to_date('23-12-2010 10:50:35', 'dd-mm-yyyy hh24:mi:ss'));

--修改为现网货架id
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65579325', 201, null, to_date('23-12-2010 10:50:35', 'dd-mm-yyyy hh24:mi:ss'));

--修改为现网货架id
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65579325', 201, null, to_date('23-12-2010 10:50:35', 'dd-mm-yyyy hh24:mi:ss'));


-- Add comments to the columns 
comment on column T_CATERULE_COND.CONDTYPE
  is '条件类型 10：从产品库获取自有业务（软件，游戏，主题）；12：从产品库获取非自有业务；1：从精品库获取。13:获取品牌店套餐业务';

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.100_SSMS','MM1.0.3.001_SSMS');
commit;