

-- Create/Recreate indexes 
create unique index pk_GCONTENT_iscpserid_69 on T_R_GCONTENT (icpservid);

-- Add comments to the columns 
comment on column T_TOPLIST.TYPE
  is '榜单类型:1应用人气;2创意孵化人气;3应用星探;4创意孵化星探;5,市场PK收入';

-- Add/modify columns 
alter table T_R_GCONTENT add othernet VARCHAR2(2) default 0;
-- Add comments to the columns 
comment on column T_R_GCONTENT.othernet
  is '1,开放异网用户访问订购下载；0，不对异网用户开放';
  
 -- Add/modify columns 
alter table V_CM_CONTENT add othernet VARCHAR2(2);

 -- Add/modify columns 
alter table V_CM_CONTENT_cy add othernet VARCHAR2(2);

 -- Add/modify columns 
alter table T_R_GCONTENT add RICHAPPDESC clob;
alter table T_R_GCONTENT add ADVERTPIC varchar2(255);
-- Add comments to the columns 
comment on column T_R_GCONTENT.RICHAPPDESC
  is '应用介绍（图文）';
comment on column T_R_GCONTENT.ADVERTPIC
  is '广告图 ';
  
  -----图书货架最后更新时间默认系统时间
  alter table T_RB_CATEGORY modify LUPDATE default sysdate;
  
  drop index IDX_T_RB_REFERENCE_ID;
create unique index IDX_T_RB_REFERENCE_ID on T_RB_REFERENCE (bookid, cid);

drop index IDX_T_RB_REFERENCE_CATE_ID;
create index IDX_T_RB_REFERENCE_CATE_ID on T_RB_REFERENCE (cid);




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
       decode( c.thirdapptype,'12', m.developername, decode(c.companyid,'116216','2010MM创业计划优秀应用展示',e.companyname))  as companyname ,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as plupddate,
       c.lupddate,    -----增加应用更新时间
decode(c.thirdapptype,'7','2',f.chargeTime)  chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid,
       decode(f.chargetime || decode(c.chargetype,'01','02',c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
             '1',
              '0') as othernet
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f,
       s_cm_content_motoext      m
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
      and c.oviappid=m.appid(+) ---- MOTO devpname
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
      greatest(c.lupddate,f.lastupdatedate,f.contentlupddate,e.lupddate)as plupddate,
       c.lupddate,
       f.chargeTime,
       c.thirdapptype,
       c.pvcid,
       c.cityid,
       c.contestcode,
       c.contestyear,
       p.college,
       decode(f.chargetime || decode(c.chargetype,'01','02',c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
             '1',
              '0') as othernet
              
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

insert into t_right (rightid,name,descs,parentid,levels) values('0_1503_CATEGORY','图书货架管理','图书货架管理','2_1503_BOOK','0');

insert into t_right (rightid,name,descs,parentid,levels) values('0_1503_REFERENCE','图书商品管理','图书商品管理','2_1503_BOOK','0');

insert into t_roleright (roleid,rightid) values(1,'0_1503_CATEGORY');
insert into t_roleright (roleid,rightid) values(1,'0_1503_REFERENCE');

alter table t_rb_category add PLATFORM VARCHAR2(200) default '{0000}';
alter table t_rb_category add CITYID VARCHAR2(4000) default '{0000}';

-- Add comments to the columns 
comment on column t_rb_category.PLATFORM
  is '平台适配关系';
comment on column t_rb_category.CITYID
  is '地市适配关系';

-- Add/modify columns 
alter table T_RB_CATEGORY add type VARCHAR2(2) default 1;
-- Add comments to the columns 
comment on column T_RB_CATEGORY.type
  is '是否在门户展示 1：是 0：否';

create table t_rb_category_temp as select * from t_rb_category;

update t_rb_category t
   set t.parentid = (select c.id from t_rb_category c where c.categoryid = t.parentid) 
   where t.parentid <> '0';

-- Add/modify columns 
alter table T_RB_CATEGORY modify CATEGORYID null;

-- Add/modify columns 
alter table T_RB_REFERENCE modify CATEGORYID null;

insert into t_right (rightid,name,descs,parentid,levels) values('1_0810_RES_SYS_DATA','紧急上线内容管理','紧急上线内容管理','2_0801_RESOURCE','0');
insert into t_roleright (roleid,rightid) values(1,'1_0810_RES_SYS_DATA');

-- Create table
create table T_ExigenceContent
(
  CONTENTID VARCHAR2(12) not null,
  dateTime  date not null
);

-- Add/modify columns 
alter table T_RB_CATEGORY modify PARENTID null;

update t_rb_category c set c.parentid = '' where c.parentid = '0';
  
------------------------------------------------------
---------解决跨实例clob字段查询问题   start-------------------
------------------------------------------------------

---修改原来的同义词
--------------------
drop synonym CM_CT_APPGAME ;
create or replace synonym s_CM_CT_APPGAME
  for CM_CT_APPGAME@DL_PPMS_DEVICE;

drop synonym CM_CT_APPSOFTWARE ;
create or replace synonym s_CM_CT_APPSOFTWARE
  for CM_CT_APPSOFTWARE@DL_PPMS_DEVICE;

drop synonym CM_CT_APPTHEME ;
create or replace synonym s_CM_CT_APPTHEME
  for CM_CT_APPTHEME@DL_PPMS_DEVICE;

  -------创建带索引的空表
  --------------------------
 create table CM_CT_APPGAME as select * from s_CM_CT_APPGAME  where 1=2;
alter table CM_CT_APPGAME
  add constraint PK_CM_CT_APPGAME primary key (CONTENTID)
  using index;
  
  create table CM_CT_APPSOFTWARE as select * from s_CM_CT_APPSOFTWARE  where 1=2;
  
  alter table CM_CT_APPSOFTWARE
  add constraint PK_CM_CT_APPSOFTWARE primary key (CONTENTID)
  using index ;
  
   create table CM_CT_APPTHEME as select * from s_CM_CT_APPTHEME  where 1=2;
   
 alter table CM_CT_APPTHEME
  add constraint PK_CM_CT_APPTHEME primary key (CONTENTID)
  using index ;


-----创建存储过程，在本地建立含有clob字段的临时表
---------------------------------
create or replace procedure p_cm__ct as

  v_dsql_GAME        varchar2(1200); ---游戏扩展表清理语句
  v_csql_GAME        varchar2(1200); ---游戏扩展表插入语句
  
   v_dsql_Software        varchar2(1200); ---软件扩展表清理语句
  v_csql_Software        varchar2(1200); ---软件扩展表插入语句
  
    v_dsql_Theme        varchar2(1200); ---主题扩展表清理语句
   v_csql_Theme        varchar2(1200); ---主题扩展表插入语句

begin

   v_dsql_GAME := 'delete  CM_CT_APPGAME';
   v_csql_GAME := 'insert  into CM_CT_APPGAME select g.*
                  from s_CM_CT_APPGAME g, ppms_v_cm_content p, v_OM_DICTIONARY d
                   where g.contentid = p.contentid
                     and g.type = d.id';

   v_dsql_Software := 'delete  CM_CT_APPSoftware';
   v_csql_Software := 'insert into CM_CT_APPSoftware select g.*
                        from s_CM_CT_APPSoftware g, ppms_v_cm_content p, v_OM_DICTIONARY d
                        where g.contentid = p.contentid
                               and g.type = d.id';

   v_dsql_Theme := 'delete  CM_CT_APPTheme';
   v_csql_Theme := 'insert  into CM_CT_APPTheme select g.*
   from s_CM_CT_APPTheme g, ppms_v_cm_content p, v_OM_DICTIONARY d
  where g.contentid = p.contentid
    and g.type = d.id';

    execute immediate v_dsql_GAME;
    execute immediate v_csql_GAME;

    execute immediate v_dsql_Software;
    execute immediate v_csql_Software;

    execute immediate v_dsql_Theme;
    execute immediate v_csql_Theme;
  commit;
end;


------创建job调用上述存储过程定时执行
---------------------------
variable v_cm_njob  number;
begin
  sys.dbms_job.submit(job => :v_cm_njob,
                       what => 'p_cm__ct;',
                         next_date => to_date('16-07-2011 00:30:00', 'dd-mm-yyyy hh24:mi:ss'),
                        interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 00:30:00'',''yyyy/mm/dd hh24:mi:ss'')');
    commit;
  end;
  /


------------------------------------------------------
---------解决跨实例clob字段查询问题   end-------------------
------------------------------------------------------



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.060_SSMS','MM1.0.3.065_SSMS');
commit;