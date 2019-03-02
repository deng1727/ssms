

-- Add/modify columns 
alter table T_CY_PRODUCTLIST add dayDOWNLOADUSERNUM NUMBER(12);
alter table T_CY_PRODUCTLIST add dayTESTUSERNUM NUMBER(12);
alter table T_CY_PRODUCTLIST add dayTESTSTAR NUMBER(12);
alter table T_CY_PRODUCTLIST add daySTARSCORECOUNT NUMBER(12);
alter table T_CY_PRODUCTLIST add dayGLOBALSCORECOUNT NUMBER(12);
-- Add comments to the columns 
comment on column T_CY_PRODUCTLIST.dayDOWNLOADUSERNUM
  is '日下载用户数';
comment on column T_CY_PRODUCTLIST.dayTESTUSERNUM
  is '日测评用户数';
comment on column T_CY_PRODUCTLIST.dayTESTSTAR
  is '日测评星级';
comment on column T_CY_PRODUCTLIST.daySTARSCORECOUNT
  is '日星探推荐得分';
comment on column T_CY_PRODUCTLIST.dayGLOBALSCORECOUNT
  is '日人气综合推荐指数';
  -- Add comments to the columns 
comment on column T_CY_PRODUCTLIST.DOWNLOADUSERNUM
  is '累计下载用户数';
comment on column T_CY_PRODUCTLIST.TESTUSERNUM
  is '累计测评用户数';
comment on column T_CY_PRODUCTLIST.TESTSTAR
  is '累计测评星级';
comment on column T_CY_PRODUCTLIST.STARSCORECOUNT
  is '累计星探推荐得分';
comment on column T_CY_PRODUCTLIST.GLOBALSCORECOUNT
  is '累计人气综合推荐指数';


---------------------------------------------------------
-- Create the synonym 按照现网环境通过dblink创建同义词------
--------------------------------------------------------- 
create or replace synonym PPMS_V_CM_CONTENT_PKAPPS
  for V_CM_CONTENT_PKAPPS@DL_PPMS_DEVICE;  
  
  
  -- Add/modify columns 
alter table T_CATEGORY_CARVEOUT_SQLBASE add basename varchar2(500);
-- Add comments to the columns 
comment on column T_CATEGORY_CARVEOUT_SQLBASE.basename
  is '基础语句描述';



  
update t_category_carveout_sqlbase t set t.basename='创业大赛常规排序指标基础语句'  where t.id=1;
update t_category_carveout_sqlbase t set t.basename='创业大赛TOP榜基础语句'  where t.id=2;
update t_category_carveout_sqlbase t set t.basename='创业大赛货架商品重排序基础语句'  where t.id=3;

  
insert into t_category_carveout_sqlbase (ID, BASESQL, BASENAME)
values ('5', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,ppms_v_CM_CONTENT_PKAPPS c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.mmcontentid and  g.subtype = ''6'' and g.NAMELETTER=''2011''', '创业大赛转MM作品基础语句');

insert into t_category_carveout_sqlbase (ID, BASESQL, BASENAME)
values ('4', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,ppms_v_CM_CONTENT_PKAPPS c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid and  g.subtype = ''6'' and g.NAMELETTER=''2011''', '创业大赛入围作品基础语句');


-----入围作品货架ID为现网货架ID,-----------------
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('13', '201105入围', '127930029', 2, 1, 1, 'c.pkdate=''201105''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '4');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('14', '201106入围', '127930030', 2, 1, 1, 'c.pkdate=''201106''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '4');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('15', '201107入围', '127930031', 2, 1, 1, 'c.pkdate=''201107''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '4');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('16', '201108入围', '127930032', 2, 1, 1, 'c.pkdate=''201108''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '4');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('17', '201109入围', '127930033', 2, 1, 1, 'c.pkdate=''201109''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '4');

-----转MM作品货架ID为现网货架ID,-----------------

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('18', '201105转MM', '127930034', 2, 1, 1, 'c.pkdate=''201105''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('19', '201106转MM', '127930035', 2, 1, 1, 'c.pkdate=''201106''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('20', '201107转MM', '127930036', 2, 1, 1, 'c.pkdate=''201107''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('21', '201108转MM', '127930037', 2, 1, 1, 'c.pkdate=''201108''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('22', '201109转MM', '127930038', 2, 1, 1, 'c.pkdate=''201109''', '', -1, null, to_date('10-06-2011 14:03:21', 'dd-mm-yyyy hh24:mi:ss'), to_date('08-06-2011 11:59:59', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');




-----创建业务视图
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
       decode(c.thirdapptype,
              '11',
              c.pkgfee||'',
              decode(p.chargetime || v2.paytype,
                     '20',
                     p.feedesc,
                     v2.chargedesc)) as chargeDesc,
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
 
----------------------------------- 
-----重新创建创业大赛视图----------
-----------------------------------
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
       c.contestyear,
       p.college

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
   --and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.Status  in ('2','3') ----产品上线计费or不计费or  去掉了下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype=0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid;
   

insert into t_right (rightid,name,descs,parentid,levels) values('0_1502_NEW_CATEGORY','货架管理','基地音乐管理','2_1502_MUSIC','0');

insert into t_right (rightid,name,descs,parentid,levels) values('0_1502_NEW_REFERENCE','商品管理','基地音乐管理','2_1502_MUSIC','0');

insert into t_roleright (roleid,rightid) values(1,'0_1502_NEW_REFERENCE');
insert into t_roleright (roleid,rightid) values(1,'0_1502_NEW_CATEGORY');

-- Add comments to the columns 
comment on column T_R_GCONTENT.HANDBOOK
  is '操作指南，对应资讯的头条标题;创业大赛的保存参赛者校园信息';
  
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.050_SSMS','MM1.0.3.055_SSMS');
commit;