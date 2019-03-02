--新增 自动更新类型
-- Add comments to the columns 
comment on column T_CATERULE_COND.CONDTYPE
  is '条件类型 10：从产品库获取自有业务（软件，游戏，主题）；12：从产品库获取非自有业务；1：从精品库获取。13:获取品牌店套餐业务。14：从基础业务';


create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,'10',c.oviappid,'12',c.oviappid,c.contentcode ) ContentCode,
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
   and (c.thirdapptype in ('1','2','7','11','12')
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

----自动更新数据
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (1014, 'MOTO最新', 0, 0, 1, 0, 0);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (1015, 'MOTO免费', 0, 0, 1, 0, 0);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (1016, 'MOTO付费', 0, 0, 1, 0, 0);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (1017, 'MOTO最热', 0, 0, 1, 0, 0);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (1014, null, 14, 'g.subtype = ''12''', 'g.Marketdate desc', -1, 1, 1399);
insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (1015, null, 14, 'g.subtype = ''12'' and v.mobileprice = 0', 'dbms_random.value', -1, 1, 1400);
insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (1016, null, 14, 'g.subtype = ''12'' and v.mobileprice > 0', 'dbms_random.value', -1, 1, 1401);
insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (1017, null, 14, 'g.subtype = ''12''', 'dbms_random.value', -1, 1, 1402);


----
-- Create table
create table T_SYNC_TACTIC_MOTO
(
  ID             NUMBER(10) not null,
  CATEGORYID     VARCHAR2(20) not null,
  CONTENTTYPE    VARCHAR2(30) not null,
  UMFLAG         VARCHAR2(1) not null,
  CONTENTTAG     VARCHAR2(200),
  TAGRELATION    NUMBER(2),
  APPCATENAME    VARCHAR2(40),
  CRATETIME      VARCHAR2(20),
  LASTUPDATETIME VARCHAR2(20)
);
-- Add comments to the columns 
comment on column T_SYNC_TACTIC_MOTO.ID
  is '数据库序列，主键';
comment on column T_SYNC_TACTIC_MOTO.CATEGORYID
  is '内容货架分类ID';
comment on column T_SYNC_TACTIC_MOTO.CONTENTTYPE
  is '内容类型';
comment on column T_SYNC_TACTIC_MOTO.UMFLAG
  is '业务通道';
comment on column T_SYNC_TACTIC_MOTO.CONTENTTAG
  is '内容标签';
comment on column T_SYNC_TACTIC_MOTO.TAGRELATION
  is '内容标签逻辑关系';
comment on column T_SYNC_TACTIC_MOTO.APPCATENAME
  is '内容分类';
comment on column T_SYNC_TACTIC_MOTO.CRATETIME
  is '创建时间';
comment on column T_SYNC_TACTIC_MOTO.LASTUPDATETIME
  is '最后修改时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SYNC_TACTIC_MOTO
  add constraint PK_T_SYNC_TACTIC_MOTO primary key (ID)
  using index ;

     -- 同一个实例的情况下 
create or replace synonym S_CM_CONTENT_MOTOEXT
  for   mm_ppms.CM_CONTENT_MOTOEXT;
  
     -- Create the synonym 
create or replace synonym S_CM_EXT_MOTO_TYPE
  for   mm_ppms.CM_EXT_MOTO_TYPE;
  

-- dblink的情况下 
create or replace synonym S_CM_CONTENT_MOTOEXT
  for CM_CONTENT_MOTOEXT@DL_PPMS_DEVICE;
  
  -- Create the synonym 
create or replace synonym S_CM_EXT_MOTO_TYPE
  for CM_EXT_MOTO_TYPE@DL_PPMS_DEVICE;
  
  ----创建MOTO内容分类信息视图
  create view v_cm_motoext as 
    select t.appid,
         t.bcontenttype,
         t.btype,
         t.developername,
         t.onlinedate,
         e.typename,
         e.stypename
    from s_cm_content_motoext t, s_cm_ext_moto_type e
   where t.btype = e.stypeid;
   
   ------
  insert into t_sync_tactic_moto 
select SEQ_SYNC_TACTIC_ID.NEXTVAL  id,  c.id categoryid,'all' contenttype,'0' umflag,null contenttag,'0' tagrelation,c.name appcatename,sysdate createtime,sysdate lastupdatetime from t_r_category c where c.parentcategoryid in ('100003125','100003126','100003127','100003128');
 
create table CM_CONTENT_MOTOEXT as select * from v_cm_motoext;




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.030_SSMS','MM1.0.3.035_SSMS');
commit;