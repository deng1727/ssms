-- rollback 


-- Add/modify columns 
alter table T_CY_PRODUCTLIST drop column dayDOWNLOADUSERNUM ;
alter table T_CY_PRODUCTLIST drop column dayTESTUSERNUM ;
alter table T_CY_PRODUCTLIST drop column dayTESTSTAR ;
alter table T_CY_PRODUCTLIST drop column daySTARSCORECOUNT ;
alter table T_CY_PRODUCTLIST drop column dayGLOBALSCORECOUNT ;


  -- Add comments to the columns 
comment on column T_CY_PRODUCTLIST.DOWNLOADUSERNUM
  is '下载用户数';
comment on column T_CY_PRODUCTLIST.TESTUSERNUM
  is '测评用户数';
comment on column T_CY_PRODUCTLIST.TESTSTAR
  is '测评星级';
comment on column T_CY_PRODUCTLIST.STARSCORECOUNT
  is '星探推荐得分';
comment on column T_CY_PRODUCTLIST.GLOBALSCORECOUNT
  is '人气综合推荐指数';
  
drop  synonym PPMS_V_CM_CONTENT_PKAPPS;

delete from    t_category_carveout_rule  t where t.id in ('13','14','15','16','17','18','19','20','21','22'); 
delete from    t_category_carveout_sqlbase  t where t.id in ('5','4'); 

-- Drop columns 
alter table T_CATEGORY_CARVEOUT_SQLBASE drop column BASENAME;


-----回滚业务视图
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
   and (c.thirdapptype in ('1', '2','6','7','11','12') or (c.thirdapptype = '5' and c.Jilstatus = '1'))
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
  
  

-----回滚创建创业大赛视图
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
              decode(f.status||f.substatus, '61', '0006','0008'),
              '1015',
              decode(f.status||f.substatus, '61', '0006','0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L',d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,'0006',f.onlinedate,'1006',f.onlinedate,f.SubOnlineDate) as marketdate,--全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       p.developername  companyname,
       c.contestgroup as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as lupddate,
       f.chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid,
       c.contestcode,
       c.contestyear

  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f,
       OM_DEVELOPER_CONTEST p
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' )
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d.ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   --and f.Status  in ('2','3') ----产品上线计费or不计费or  去掉了下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;
   

delete t_right r where r.rightid in ('0_1502_NEW_REFERENCE','0_1502_NEW_CATEGORY');

delete t_roleright r where r.rightid in ('0_1502_NEW_REFERENCE','0_1502_NEW_CATEGORY');


-- Add comments to the columns 
comment on column T_R_GCONTENT.HANDBOOK
  is '操作指南，对应资讯的头条标题;';
  
  

delete DBVERSION where PATCHVERSION = 'MM1.0.3.055_SSMS' and LASTDBVERSION = 'MM1.0.3.050_SSMS';
commit;