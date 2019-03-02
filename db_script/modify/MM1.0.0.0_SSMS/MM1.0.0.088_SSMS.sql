
-- Create table
create table T_CONTENT_COUNT
(
  CONTENTID      NVARCHAR2(30) not null,
  LATESTCOUNT    NUMBER default 0 not null,
  RECOMMENDCOUNT NUMBER default 0 not null,
  COUNTTIME      NVARCHAR2(30)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CONTENT_COUNT  add constraint PK_T_CONTENT_COUNT primary key (CONTENTID)  using index ;
comment on column  T_CONTENT_COUNT.CONTENTID  is '应用标识ID';
comment on column  T_CONTENT_COUNT.LATESTCOUNT  is '最新综合得分';
comment on column  T_CONTENT_COUNT.RECOMMENDCOUNT  is '推荐综合得分';
comment on column  T_CONTENT_COUNT.COUNTTIME  is '统计日期';

--备份自动更新规则表,使运营推荐15个货架的精品库更新规则失效
create table t_caterule_cond20100210 as select * from t_caterule_cond ;
update t_caterule_cond set cid = '-1' where cid is not null and ruleid  between 84 and 98;

----最新
update t_caterule_cond set osql='c.LATESTCOUNT  desc nulls last' where condtype ='10' and   ruleid in ('88','98','93','3');
----推荐
update t_caterule_cond set osql='c.RECOMMENDCOUNT  desc nulls last' where condtype ='10' and   ruleid in ('86','91','96','4');

-- Create table
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

--更新运营需要变更的三级货架类型
update t_caterule t set t.ruletype=6 where t.ruleid between 84 and 98;
comment on column T_CATERULE.RULETYPE
  is '规则类型 0：刷新货架下商品；1：货架下商品重排顺序
；5：处理基地图书运营推荐图书。6:处理运营三级货架类型';



--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.080_SSMS','MM1.0.0.088_SSMS');
commit;