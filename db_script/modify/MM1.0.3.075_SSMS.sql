create table T_GAME_ATTR
(
  GAMEID          VARCHAR2(30) not null,
  TESTNUMBER      NUMBER(5),
  USERVALUE       NUMBER(5),
  DOWNLOADCHANGE  NUMBER(5),
  DAYACTIVITYUSER NUMBER(5),
  COMMENDTIME     NUMBER(5)
);

-- Add comments to the columns 
comment on column T_GAME_ATTR.GAMEID
  is '游戏id';
comment on column T_GAME_ATTR.TESTNUMBER
  is '测试评分';
comment on column T_GAME_ATTR.USERVALUE
  is '用户好评';
comment on column T_GAME_ATTR.DOWNLOADCHANGE
  is '下载增长率';
comment on column T_GAME_ATTR.DAYACTIVITYUSER
  is '日用户活跃率';
comment on column T_GAME_ATTR.COMMENDTIME
  is '推荐次数';

alter table T_GAME_ATTR
  add constraint T_GAME_ATTR_pk primary key (GAMEID);

-- Add/modify columns 
alter table T_KEY_BASE add KEYTYPE varchar2(10) default 1;
-- Add comments to the columns 
comment on column T_KEY_BASE.KEYTYPE
  is '域类型，1，普通文本；2，文件域';
  

drop synonym PPMS_PKAPPS_DETAIL;
drop synonym PPMS_V_CM_CONTENT_PKAPPS;

-- Create the synonym 
create or replace synonym S_CM_CONTENT_PKAPPS
  for V_CM_CONTENT_PKAPPS@DL_PPMS_DEVICE;


  -- Create the synonym 
create or replace synonym S_PPMS_PKAPPS_DETAIL
  for V_CM_CONTENT_PKAPPS_DETAIL@DL_PPMS_DEVICE;

create table PPMS_V_CM_CONTENT_PKAPPS as select * from S_CM_CONTENT_PKAPPS where 1=2;
create table PPMS_PKAPPS_DETAIL as select * from S_PPMS_PKAPPS_DETAIL where 1=2;


-----以下需要整体执行
create or replace procedure P_ppms_CY2011 as
  v_dsql_pk        varchar2(1200); ---
  v_dsql_pk        varchar2(1200); ---

   v_dsql_pk_detal        varchar2(1200); ---
  v_dsql_pk_detal        varchar2(1200); --- 
   v_status number;--日志返回

begin
   v_status:=pg_log_manage.f_startlog('P_ppms_CY2011','初始化电子流创业大赛2011扩展表' );
   v_dsql_pk := 'truncate table       PPMS_V_CM_CONTENT_PKAPPS';
   v_dsql_pk := 'insert  /*+ append */ into PPMS_V_CM_CONTENT_PKAPPS  nologging select g.*
                  from S_CM_CONTENT_PKAPPS g ';
   v_dsql_pk_detal := 'truncate table   PPMS_PKAPPS_DETAIL';
   v_dsql_pk_detal := 'insert  /*+ append */ into PPMS_PKAPPS_DETAIL nologging select *
                        from S_PPMS_PKAPPS_DETAIL ';  

    execute immediate v_dsql_pk_detal;
    execute immediate v_dsql_pk_detal;    
     execute immediate v_dsql_pk;
    execute immediate v_csql_pk;
    v_status:= pg_log_manage.f_successlog;
  commit;
exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
end;
  


  insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.070_SSMS','MM1.0.3.075_SSMS');
commit;




------------------------------------------------------------------
------------------------------------------------------------------
------以下不需要执行，现网已经执行过---------------------------
------------------------------------------------------------------
------------------------------------------------------------------

-----修改MM应用同步视图
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
       c.conlupddate as  lupddate,    -----增加应用更新时间,原来是c.lupddate
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


-----修改创业大赛同步视图
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
      c.conlupddate as  lupddate,    -----增加应用更新时间,原来是c.lupddate
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



---新增数据导出菜单权限
insert into t_right r values('1_0813_RES_CONTENTEXT','应用活动属性管理','应用活动属性管理','2_0801_RESOURCE',0);
insert into t_roleright(ROLEID,RIGHTID) values(1,'1_0813_RES_CONTENTEXT');


create table T_CONTENT_EXT
(
  ID          VARCHAR2(30) not null,
  CONTENTID   VARCHAR2(12) not null,
  NAME        VARCHAR2(300),
  SPNAME      VARCHAR2(100),
  TYPE        NUMBER,
  DISCOUNT    NUMBER,
  DATESTART   VARCHAR2(8),
  DATEEND     VARCHAR2(8),
  TIMESTART   VARCHAR2(8),
  TIMEEND     VARCHAR2(8),
  LUPDATE     DATE,
  USERID      VARCHAR2(20) not null,
  MOBILEPRICE NUMBER,
  EXPPRICE    NUMBER,
  ICPCODE     VARCHAR2(20),
  ISRECOMM    VARCHAR2(2) default 0
);
commit;
comment on column T_CONTENT_EXT.ID
  is '序列号';
comment on column T_CONTENT_EXT.CONTENTID
  is '内容ID';
comment on column T_CONTENT_EXT.NAME
  is '内容名称';
comment on column T_CONTENT_EXT.SPNAME
  is '企业名称';
comment on column T_CONTENT_EXT.TYPE
  is '内容类型：1团购，2秒杀';
comment on column T_CONTENT_EXT.DISCOUNT
  is '折扣率：0至100之间';
comment on column T_CONTENT_EXT.DATESTART
  is '开始日期：格式(yyyyMMdd)';
comment on column T_CONTENT_EXT.DATEEND
  is '结束日期：格式(yyyyMMdd)';
comment on column T_CONTENT_EXT.TIMESTART
  is '开始时间：格式(HH:mm:ss)';
comment on column T_CONTENT_EXT.TIMEEND
  is '结束时间：格式(HH:mm:ss)';
comment on column T_CONTENT_EXT.LUPDATE
  is '最后更新时间';
comment on column T_CONTENT_EXT.USERID
  is '操作员ID';
comment on column T_CONTENT_EXT.MOBILEPRICE
  is '原价：单位(厘)';
comment on column T_CONTENT_EXT.EXPPRICE
  is '折扣价=原价X折扣率/100 单位(厘)';
comment on column T_CONTENT_EXT.ICPCODE
  is '企业代码';
comment on column T_CONTENT_EXT.ISRECOMM
  is '是否推荐:0，不推荐；1，推荐';
commit;

-- Create sequence 
create sequence SEQ_CONTENTEXT_ID
minvalue 1
maxvalue 99999999999
start with 13
increment by 1
nocache
cycle;



