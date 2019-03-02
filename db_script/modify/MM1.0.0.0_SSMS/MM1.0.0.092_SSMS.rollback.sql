--还原 t_category_name_mapping
create table t_category_name_mapping
(
  apptype     number(1) not null,
  appcatename varchar2(200) not null,
  thirdcatename varchar2(200) not null
)
;
create index INDEX_T_CATEGORY_NAME_MAPPING on T_CATEGORY_NAME_MAPPING (apptype, appcatename);

-- Add comments to the columns 
comment on column t_category_name_mapping.apptype
  is '应用类型，1 软件，2 游戏，3 主题';
comment on column t_category_name_mapping.appcatename
  is 'ppms 定义应用的二级分类名称，其值来源于 t_r_gcontent 的 appcatename的值';
comment on column t_category_name_mapping.thirdcatename
  is '最新，免费，推荐，星级，排行对应三级货架显示名称。';

  insert into t_category_name_mapping t values(1,'多媒体软件','多媒体软件');
  insert into t_category_name_mapping t values(1,'实用软件','实用软件');
  insert into t_category_name_mapping t values(1,'通信辅助','通信辅助');
  insert into t_category_name_mapping t values(1,'网络软件','网络软件');
  insert into t_category_name_mapping t values(1,'系统工具','系统工具');


  insert into t_category_name_mapping t values(2,'策略回合','策略回合');
  insert into t_category_name_mapping t values(2,'动作格斗','动作格斗');
  insert into t_category_name_mapping t values(2,'角色扮演','角色扮演');
  insert into t_category_name_mapping t values(2,'冒险模拟','冒险模拟');
  insert into t_category_name_mapping t values(2,'其他','其他');
  insert into t_category_name_mapping t values(2,'棋牌益智','棋牌益智');
  insert into t_category_name_mapping t values(2,'射击飞行','射击飞行');
  insert into t_category_name_mapping t values(2,'体育竞技','体育竞技');
  insert into t_category_name_mapping t values(2,'休闲趣味','休闲趣味');


  insert into t_category_name_mapping t values(3,'动物','动物');
  insert into t_category_name_mapping t values(3,'风景','风景');
  insert into t_category_name_mapping t values(3,'节日','节日');
  insert into t_category_name_mapping t values(3,'卡通','卡通');
  insert into t_category_name_mapping t values(3,'科技','科技');
  insert into t_category_name_mapping t values(3,'酷图','酷图');

  insert into t_category_name_mapping t values(3,'明星','明星');
  insert into t_category_name_mapping t values(3,'汽车','汽车');
  insert into t_category_name_mapping t values(3,'人物','人物');
  insert into t_category_name_mapping t values(3,'体育','体育');
  insert into t_category_name_mapping t values(3,'影视','影视');
  insert into t_category_name_mapping t values(3,'游戏','游戏');


drop table t_synctime_tmp;
create table t_synctime_tmp
(
  ID    NUMBER(10) not null,
  contentid  VARCHAR2(12) not null,
  name  VARCHAR2(300) not null,
  status  VARCHAR2(4),
  contentType  VARCHAR2(32),
  LUPDDate date,
  constraint KEY_synctime_tmp primary key (id)
);
--还原 v_cm_content
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
   and (d.paytype=0 or d.paytype is null);

--删除版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.092_SSMS' and LASTDBVERSION = 'MM1.0.0.090_SSMS';
commit;