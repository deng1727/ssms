------触点泛化合作商信息视图-------
create or replace view v_touchspot_ap as
select c.channelsid as apid,c.channelsname as apname 
from t_open_channels c 
-----合作方式包含换量合作----
where c.status = '0' and c.cotype like '%2%';
------触点泛化应用信息视图-------
create or replace view v_touchspot_app as
select g.contentid as appid,c.channelsid as apid,g.name as appname
from t_r_gcontent g,t_open_channels c 
where c.channelsid = g.companyid;

alter table t_r_gcontent add channeldisptype VARCHAR2(1) default '0';
comment on column t_r_gcontent.channeldisptype
  is '触点合作商渠道分发类型：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）';

--------------------在v_cm_content新增字段channeldisptype----------------
alter  table v_cm_content add channeldisptype VARCHAR2(1);
comment on column v_cm_content.channeldisptype
  is '触点合作商渠道分发类型：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）';
  
--------触点泛化-终端公司需求渠道商账号表（T_OPEN_CHANNELS）新增4个字段   开始------------------------------------------------------------------------------------------
alter  table T_OPEN_CHANNELS add cotype VARCHAR2(10);
alter  table T_OPEN_CHANNELS add codate DATE default sysdate;
alter  table T_OPEN_CHANNELS add channelsnumber VARCHAR2(10);
comment on column T_OPEN_CHANNELS.cotype
  is '合作类型：1-内容合作，2-换量合作';
comment on column T_OPEN_CHANNELS.codate
  is '合作日期';
comment on column T_OPEN_CHANNELS.channelsnumber
  is '渠道个数';
  
--------触点泛化-终端公司需求渠道商账号表（T_OPEN_CHANNELS）新增4个字段   结束------------------------------------------------------------------------------------------


--------创建渠道号表（T_OPEN_CHANNEL_NO）   开始--------------------------------------------------------------------------------------------------------------
create table T_OPEN_CHANNEL_NO
(
  channelsno VARCHAR2(20) not null,
  createdate DATE,
  operator   VARCHAR2(30),
  status     VARCHAR2(2) default 2
);
-- Add comments to the table 
comment on table T_OPEN_CHANNEL_NO
  is '渠道号表';
-- Add comments to the columns 
comment on column T_OPEN_CHANNEL_NO.channelsno
  is '渠道号ID';
comment on column T_OPEN_CHANNEL_NO.createdate
  is '导入日期';
comment on column T_OPEN_CHANNEL_NO.operator
  is '操作人';
comment on column T_OPEN_CHANNEL_NO.status
  is '操作状态：1-已使用；2-未使用';
--------创建渠道号表（T_OPEN_CHANNEL_NO）   结束-------------------------------------------------------------------------------------------------------------

--------合作商渠道信息表（T_OPEN_CHANNEL_INFO）   开始---------------
create table T_OPEN_CHANNEL_INFO
(
  channelid   VARCHAR2(20) not null,
  channeltype VARCHAR2(2),
  channelname VARCHAR2(100),
  channeldesc VARCHAR2(200),
  status   VARCHAR2(2),
  createdate  DATE default sysdate,
  updatedate  DATE default sysdate,
  channelsid  VARCHAR2(20) not null
);
-- Add comments to the table 
comment on table T_OPEN_CHANNEL_INFO
  is '合作商渠道信息表';
-- Add comments to the columns 
comment on column T_OPEN_CHANNEL_INFO.channelid
  is '渠道id';
comment on column T_OPEN_CHANNEL_INFO.channeltype
  is '渠道类型：0-客户端,1-数据接口,2-网页';
comment on column T_OPEN_CHANNEL_INFO.channelname
  is '渠道名称';
comment on column T_OPEN_CHANNEL_INFO.channeldesc
  is '渠道描述';
comment on column T_OPEN_CHANNEL_INFO.status
  is '渠道状态：0-正常,1-终止';
comment on column T_OPEN_CHANNEL_INFO.createdate
  is '创建时间';
comment on column T_OPEN_CHANNEL_INFO.updatedate
  is '更新时间';
comment on column T_OPEN_CHANNEL_INFO.channelsid
  is '渠道商id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OPEN_CHANNEL_INFO
  add constraint PK_T_OPEN_CHANNEL_INFO_ID primary key (CHANNELID);
--------合作商渠道信息表（T_OPEN_CHANNEL_INFO）结束---------------  

--------创建同义词cm_ct_program_apply 开始---------
create or replace synonym cm_ct_program_apply
  for cm_ct_program_apply@DL_MM_PPMS_NEW;
--------创建同义词cm_ct_program_apply 结束--------- 
  
---------ppms_v_cm_content 视图开始-----
create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,
              '10',
              c.oviappid,
              '12',
              c.oviappid,
              '16',
              c.oviappid,
              c.contentcode) ContentCode,
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
              f.SubOnlineDate) as marketdate,
       --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM创业计划优秀应用展示',
                     decode(c.devcompanyid,
                            null,
                            e.companyname,
                            (select so.companyname
                               from soft_company so
                              where so.companyid = c.devcompanyid)))) as companyname, --提供商
       decode(c.mapcompanyid,
              null,
              '',
              (select so1.companyname
                 from om_company so1
                where c.mapcompanyid = so1.companyid)) devcompanyname, --代理商
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----增加应用更新时间
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       --decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype) thirdapptype, 定制APP换掉
       decode(c.secondarytype,
              '2',
              '21',
              decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype)) thirdapptype,
       c.pvcid,
       c.citysid as cityid,
       decode(f.chargetime ||
              decode(c.chargetype, '01', '02', c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
              '1',
              '0') as othernet,
       c.ismmtoevent,
       c.COPYRIGHTFLAG,
       decode((select count(pa.channel_disp_type) from cm_ct_program_apply pa where pa.contentid=c.contentid and
       pa.channel_disp_type = '1'),0,'0','1') as channeldisptype
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f --
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or f.status = 5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status = 6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus in ('2', '3', '5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in
       ('1', '2', '7', '11', '12', '13', '14', '16', '21') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'));

 ---------ppms_v_cm_content 视图开始-----
  
  
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_COOPERATION_RESULT_001','新增合作商成功','新增合作商成功'); 
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_COOPERATION_RESULT_002','更新合作商成功','更新合作商成功');


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CHANNELINFO_RESULT_001','申请渠道成功','申请渠道成功'); 
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CHANNELINFO_RESULT_002','更新渠道成功','更新渠道成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CHANNELINFO_RESULT_003','渠道号已用完，不能添加渠道','渠道号已用完，不能添加渠道');
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.095_SSMS','MM4.0.0.0.099_SSMS');

 
commit;