-- Create table
create table T_GAME_TW
(
  CPID             VARCHAR2(16) not null,
  CPNAME           VARCHAR2(200) not null,
  CPSERVICEID      VARCHAR2(16) not null,
  SERVICENAME      VARCHAR2(400) not null,
  SERVICESHORTNAME VARCHAR2(400) not null,
  SERVICEDESC      VARCHAR2(1000),
  OPERATIONDESC    VARCHAR2(1000),
  SERVICETYPE      NUMBER(2) not null,
  SERVICEPAYTYPE   NUMBER(2) not null,
  SERVICESTARTDATE VARCHAR2(10) not null,
  SERVICEENDDATE   VARCHAR2(10) not null,
  SERVICESTATUS    NUMBER(1) not null,
  FEE              NUMBER(10) not null,
  SERVICEFEEDESC   VARCHAR2(400) not null,
  SERVICE_URL      VARCHAR2(400) not null,
  SERVICEFEETYPE   NUMBER(2) not null,
  GAMETYPE         NUMBER(4) not null,
  MMGAMETYPE       VARCHAR2(100) not null,
  GAMETYPE_DESC    VARCHAR2(40) not null,
  SERVICEFLAG      NUMBER(1),
  PTYPEID          NUMBER(2) not null
)
;
-- Add comments to the columns 
comment on column T_GAME_TW.cpid
  is 'CP的合作代码';
comment on column T_GAME_TW.cpname
  is 'CP名称';
comment on column T_GAME_TW.cpserviceid
  is '产品的业务代码';
comment on column T_GAME_TW.servicename
  is '产品名称';
comment on column T_GAME_TW.serviceshortname
  is '产品简称';
comment on column T_GAME_TW.servicedesc
  is '产品简介';
comment on column T_GAME_TW.operationdesc
  is '操作简介';
comment on column T_GAME_TW.servicetype
  is '业务类型     1:客户端单机,2:客户端网游,3:WAP网游,4:WAP单机';
comment on column T_GAME_TW.servicepaytype
  is '支付方式      1:点数,2:话费';
comment on column T_GAME_TW.servicestartdate
  is '产品生效日期   业务生效日期，格式"yyyy-mm-dd"';
comment on column T_GAME_TW.serviceenddate
  is '产品失效日期   商用业务在失效日期后，自动变为下线业务。"yyyy-mm-dd"';
comment on column T_GAME_TW.servicestatus
  is '业务状态    1:待上传游戏内容,2:待测试,3:商用,4:暂停,5:待下线,0:已下线';
comment on column T_GAME_TW.fee
  is '资费(厘)';
comment on column T_GAME_TW.servicefeedesc
  is '资费描述';
comment on column T_GAME_TW.service_url
  is '入口URL（下载url）  图文游戏的链接地址。';
comment on column T_GAME_TW.servicefeetype
  is '计费方式   0:免费, 
1:包月,
2:客户端单机按次(首次使用时付费), 
3:道具按次,
4:套餐按月,
5:下载时按次计费,
6:Wap按次（每次使用都计费）,
7:客户端单机短信计费,
8:客户端网游短信计费。
只给0、3、6的，
0：免费
3：图文网游
5：图文单机
';
comment on column T_GAME_TW.gametype
  is '游戏分类   游戏分类的可选值只能是以下的其中一个：
MMORPG(网游)
竞速
动作冒险
文字冒险
音乐
动作
棋牌
策略
体育
角色扮演
射击
益智
动作益智
';
comment on column T_GAME_TW.gametype_desc
  is '游戏分类名称';
comment on column T_GAME_TW.serviceflag
  is '0:普通业务,1:套餐内业务';
comment on column T_GAME_TW.ptypeid
  is '业务推广方式';


create table t_game_tw_temp as select * from t_game_tw where 0=1;

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
       c.conlupddate as lupddate,    -----增加应用更新时间
decode(c.thirdapptype,'7','2',f.chargeTime)  chargeTime,
      decode(c.thirdapptype,'13','1','14','1', c.thirdapptype) thirdapptype,
       c.pvcid,
       c.citysid as cityid,
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
   and (c.thirdapptype in ('1','2','7','11','12','13','14')
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
   and (c.thirdapptype in ('1', '2','6','7','11','12','13','14') or (c.thirdapptype = '5' and c.Jilstatus = '1'))
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

-------------------------------------
---游戏基地融合需求------------------------
-------------------------------------
-------------------------------------
-------------------------------------

-- Create table
create table T_GAME_CFG
(
  TYPE_ID           NUMBER not null,
  TYPE_NAME         VARCHAR2(30),
  TEST_PERCENT      NUMBER,
  UP_PERCENT        NUMBER,
  CUSTOM_PERCENT    NUMBER,
  DOWNLOAD_PERCENT  NUMBER,
  USER_PERCENT      NUMBER,
  RECOMMEND_PERCENT NUMBER,
  DAY_DOWNLOAD      NUMBER
);
-------------------------------------
-------------------------------------
-------------------------------------
-- Add comments to the columns 
comment on column T_GAME_CFG.TYPE_ID
  is '榜单类型ID';

-- Create table
create table T_GAME_TOTAL
(
  CREATEDATE      VARCHAR2(8),
  GAMEID          VARCHAR2(30) not null,
  BESTNETTOTAL    NUMBER,
  RECOMMENDTOTAL  NUMBER,
  OPERATIONSTOTAL NUMBER
);
-- Add comments to the columns 
comment on column T_GAME_TOTAL.CREATEDATE
  is '时间';
comment on column T_GAME_TOTAL.GAMEID
  is '游戏id';
comment on column T_GAME_TOTAL.BESTNETTOTAL
  is '最新榜综合得分';
comment on column T_GAME_TOTAL.RECOMMENDTOTAL
  is '推荐综合得分';
comment on column T_GAME_TOTAL.OPERATIONSTOTAL
  is '运营综合得分';
-------------------------------------
-------------------------------------
-------------------------------------


insert into t_game_cfg (TYPE_ID, TYPE_NAME, TEST_PERCENT, UP_PERCENT, CUSTOM_PERCENT, DOWNLOAD_PERCENT, USER_PERCENT, RECOMMEND_PERCENT, DAY_DOWNLOAD)
values (101, '最新榜', 0.3, 0.6, 0.05, 0.05, null, null, null);

insert into t_game_cfg (TYPE_ID, TYPE_NAME, TEST_PERCENT, UP_PERCENT, CUSTOM_PERCENT, DOWNLOAD_PERCENT, USER_PERCENT, RECOMMEND_PERCENT, DAY_DOWNLOAD)
values (102, '推荐榜', 0.2, null, 0.2, 0.2, 0.2, 0.2, null);

insert into t_game_cfg (TYPE_ID, TYPE_NAME, TEST_PERCENT, UP_PERCENT, CUSTOM_PERCENT, DOWNLOAD_PERCENT, USER_PERCENT, RECOMMEND_PERCENT, DAY_DOWNLOAD)
values (103, '运营分析评分榜', null, null, 0.25, 0.25, null, 0, 0.5);

-------------------------------------
-------------------------------------
-------------------------------------


create or replace procedure p_shelf_Integralsort as

  --自定义一个行类型，将配置表数据赋值给此类型变量
  type type_table_gamercfy is table of t_game_cfg%rowtype index by binary_integer;
  v_gamecfy type_table_gamercfy;

  -- 需动态执行的SQL语句
  v_dynamic_SQL1 varchar2(1000);
  v_dynamic_SQL2 varchar2(1000);
  v_dynamic_SQL3 varchar2(1000);
  v_nstatus      number;
begin
  v_nstatus := pg_log_manage.f_startlog('P_SHELF_INTEGRALSORT',
                                        '货架榜单综合得分统计');
  select * bulk collect into v_gamecfy from t_game_cfg;
  for i in 1 .. sql%rowcount loop
  
    --拼装最新榜综合得分公式
    if v_gamecfy(i).type_id = 101 then
    
      v_dynamic_SQL1 := 'a.testnumber * ' || v_gamecfy(i).test_percent ||
                        '+nvl(c.programid, 0) * ' || v_gamecfy(i)
                       .up_percent || '+ a.uservalue *' || v_gamecfy(i)
                       .custom_percent || '+a.downloadchange * ' || v_gamecfy(i)
                       .download_percent;
    end if;
  
    --拼装推荐榜综合得分公式
    if v_gamecfy(i).type_id = 102 then
      v_dynamic_SQL2 := 'a.testnumber * ' || v_gamecfy(i).test_percent ||
                        '+DAYACTIVITYUSER * ' || v_gamecfy(i).USER_PERCENT ||
                        '+ a.uservalue *' || v_gamecfy(i).custom_percent ||
                        '+a.downloadchange * ' || v_gamecfy(i)
                       .download_percent || '+COMMENDTIME * ' || v_gamecfy(i)
                       .RECOMMEND_PERCENT;
    end if;
    --拼装运营分析评分榜单综合得分公式
    if v_gamecfy(i).type_id = 103 then
      v_dynamic_SQL3 := 'DAYORDERTIMES * ' || v_gamecfy(i).DAY_DOWNLOAD ||
                        '+ a.uservalue *' || v_gamecfy(i).custom_percent ||
                        '+a.downloadchange * ' || v_gamecfy(i)
                       .download_percent || '+COMMENDTIME * ' || v_gamecfy(i)
                       .RECOMMEND_PERCENT;
    end if;
  
  end loop;

  --将计算结果放入表t_game_total

  v_dynamic_SQL1 := 'insert into t_game_total (CREATEDATE,GAMEID,BESTNETTOTAL,RECOMMENDTOTAL,OPERATIONSTOTAL)  ' ||
                    'select to_char(sysdate,' || '''' || 'yyyymmdd' || '''' || ') ,
a.gameid,' || v_dynamic_SQL1 || ',' || v_dynamic_SQL2 || ',' ||
                    v_dynamic_SQL3 ||
                    '  from  t_game_attr a, t_r_gcontent c

where  a.gameid = c.contentid and c.provider=' || '''' || 'B' || '''';

  execute immediate v_dynamic_SQL1;

--将结果更新到表t_content_count，如果表中存在应用ID就更新，否则就插入
  merge into t_content_count a
  using t_game_total b
  on (b.gameid = a.contentid)
  when matched then
    update
       set a.LATESTCOUNT     = b.bestnettotal,
           a.recommend_grade = b.recommendtotal,
           a.compecount      = b.operationstotal,
           a.counttime       = b.createdate
  when not matched then
    insert
      (a.contentid,
       a.latestcount,
       a.recommendcount,
       a.counttime,
       a.compecount)
    values
      (b.gameid,
       b.bestnettotal,
       b.recommendtotal,
       b.createdate,
       b.operationstotal);
  v_nstatus := pg_log_manage.f_successlog;
  commit;
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;
-------------------------------------
-------------------------------------
-------------------------------------



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.085_SSMS','MM1.0.3.090_SSMS');
commit;


