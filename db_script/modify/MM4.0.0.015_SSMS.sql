
-- Create table
create table t_wp_content
(
  ID   VARCHAR2(32)not null,
  appname       VARCHAR2(100)not null,
  appurl        VARCHAR2(512)not null,
  appid         VARCHAR2(50)not null,
  applogo       VARCHAR2(512),
  apptype1      VARCHAR2(30),
  apptype2      VARCHAR2(30),
  appdetail     VARCHAR2(2048),
  appsp         VARCHAR2(100),
  appversion    VARCHAR2(20),
  appprice      NUMBER(10,2),
  appscore      VARCHAR2(20),
  appscorenum   NUMBER(10),
  apppic        VARCHAR2(200),
  appsize       VARCHAR2(20),
  appupdatedate VARCHAR2(20),
  appsupportsys VARCHAR2(50),
  apprelates    VARCHAR2(400),
  Pic1 VARCHAR2(512),
  Pic2 VARCHAR2(512),
  Pic3 VARCHAR2(512),
  Pic4 VARCHAR2(512),
  Pic5 VARCHAR2(512),
  Pic6 VARCHAR2(512),
  Pic7 VARCHAR2(512),
  Pic8 VARCHAR2(512),
  updatetime date
)
-- Add comments to the columns 
comment on column t_wp_content.id
  is '自增主键ID';
comment on column t_wp_content.appname
  is '应用的名称信息';
comment on column t_wp_content.appurl
  is '应用在WP的地址信息';
comment on column t_wp_content.appid
  is '应用的ID';
comment on column t_wp_content.applogo
  is '应用LOGO的文件名称';
comment on column t_wp_content.apptype1
  is '一级分类';
comment on column t_wp_content.apptype2
  is '二级分类';
comment on column t_wp_content.appdetail
  is '说明信息(不包括“详细信息部分”)。输出长度：2048';
comment on column t_wp_content.appsp
  is '发行商';
comment on column t_wp_content.appversion
  is '版本说明';
comment on column t_wp_content.appprice
  is '价格';
comment on column t_wp_content.appscore
  is '评分';
comment on column t_wp_content.appscorenum
  is '评分次数';
comment on column t_wp_content.apppic
  is '截图的文件名称';
comment on column t_wp_content.appsize
  is '下载大小';
comment on column t_wp_content.appupdatedate
  is '更新时间';
comment on column t_wp_content.appsupportsys
  is '系统';
comment on column t_wp_content.apprelates
  is '相关应用';
comment on column t_wp_content.Pic1
  is '截图1';
comment on column t_wp_content.Pic2
  is '截图2';
comment on column t_wp_content.Pic3
  is '截图3';
comment on column t_wp_content.Pic4
  is '截图4';
comment on column t_wp_content.Pic5
  is '截图5';
comment on column t_wp_content.Pic6
  is '截图6';
comment on column t_wp_content.Pic7
  is '截图7';
comment on column t_wp_content.Pic8
  is '截图8';
comment on column t_wp_content.updatetime
  is '更新时间';

-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_content
  add constraint pk_twp_content_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_appid on t_wp_content ( appid);

-- Create table
create sequence SEQ_t_wp_content_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;




-- Create table
create table t_wp_category
(
  id         varchar2(32),
  categoryid varchar2(32),
  cname      varchar2(500),
  cdesc      varchar2(2000),
  pic        varchar2(500),
  isshow     varchar2(2),
  parentcid  varchar2(32),
  sortid     number(8),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_wp_category.id
  is '自增主键ID';
comment on column t_wp_category.categoryid
  is '货架编码ID';
comment on column t_wp_category.cname
  is '货架名称';
comment on column t_wp_category.cdesc
  is 'wp货架描述';
comment on column t_wp_category.pic
  is 'wp货架图片';
comment on column t_wp_category.isshow
  is '是否在门户展示，1，展示；0，不展示';
comment on column t_wp_category.parentcid
  is '父货架编码ID';
comment on column t_wp_category.sortid
  is '排序号';
comment on column t_wp_category.lupdate
  is '同步最后更新时间';

-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_category
  add constraint pk_twp_cate_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_cateid on t_wp_category ( categoryid);


create sequence SEQ_T_WP_category_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


-- Create table
create table t_wp_reference
(
  id         varchar2(32),
  appid      varchar2(50),
  categoryid varchar2(32),
  cname      varchar2(100),
  sortid     number(8),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_wp_reference.id
  is '自增主键ID';
comment on column t_wp_reference.appid
  is '应用ID';
comment on column t_wp_reference.categoryid
  is '货架ID';
comment on column t_wp_reference.cname
  is '应用名称';
comment on column t_wp_reference.sortid
  is '排序号';
comment on column t_wp_reference.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_reference
  add constraint pk_twp_ref_id primary key (ID);
-- Create/Recreate indexes 
create unique index idx_appid_cateid on t_wp_reference (appid, categoryid);


create sequence SEQ_T_WP_reference_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;


-- Create table
create table t_wp_tactic
(
  id         varchar2(32),
  apptype    varchar2(60),
  categoryid varchar2(32),
  tatype     varchar2(32),
  lupdate    date
)
;
-- Add comments to the columns 
comment on column t_wp_tactic.id
  is '自增主键ID';
comment on column t_wp_tactic.apptype
  is '应用类别';
comment on column t_wp_tactic.categoryid
  is '货架编码ID';
comment on column t_wp_tactic.tatype
  is '策略类型，备用';
comment on column t_wp_tactic.lupdate
  is '同步最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_wp_tactic
  add constraint idx_t_wp_static_id primary key (ID);

create sequence SEQ_T_WP_tactic_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;

-- Create the synonym 
create or replace synonym CM_RESOURCE_SERVER
  for CM_RESOURCE_SERVER@DL_MM_PPMS_NEW;
  


create sequence SEQ_T_WP_category_CID
minvalue 11000
maxvalue 999999999999
start with 11000
increment by 1
nocache
cycle;

-- Add/modify columns 
alter table T_WP_TACTIC add type VARCHAR2(60);
alter table T_WP_TACTIC modify tatype default 0;
alter table T_WP_TACTIC modify lupdate default sysdate;
-- Add comments to the columns 
comment on column T_WP_TACTIC.type
  is '一级分类';
comment on column T_WP_TACTIC.tatype
  is '策略类型，默认0，备用，';
  
 ---------------------------- ----------------------------
 -------------存储过程开始，需要单独执行-----------------
create or replace procedure P_WP_REFRESHCATEREF is
--子定义变量
v_nstatus     number;
begin
--监控存储过程情况的包
  v_nstatus := pg_log_manage.f_startlog('P_WP_REFRESHCATEREF',
                                        'WP应用分类货架商品上架，调用P_WP_REFRESHCATEREF');

   ---- select t.categoryid from T_WP_CATEGORY t,t_wp_tactic s,t_wp_reference r,t_wp_content c where c.appid=r.appid and r.categoryid=t.categoryid and s.categoryid=t.categoryid and t.parentcid='10001' and c.apptype2 != s.apptype
-- 下架变更二级分类后的应用
delete from t_wp_reference where id in (
select r.id from T_WP_CATEGORY t,t_wp_tactic s,t_wp_reference r,t_wp_content c where c.appid=r.appid and r.categoryid=t.categoryid and s.categoryid=t.categoryid and t.parentcid='10001' and c.apptype2 != s.apptype and c.apptype1='应用');
-- 下架变更二级分类后的游戏
delete from t_wp_reference where id in (
select r.id from T_WP_CATEGORY t,t_wp_tactic s,t_wp_reference r,t_wp_content c where c.appid=r.appid and r.categoryid=t.categoryid and s.categoryid=t.categoryid and t.parentcid='10002' and c.apptype2 != s.apptype and c.apptype1='游戏');

-- 根据分类上架策略上架未上架分类货架的应用到分类货架
   for vc in (select t.apptype,t.categoryid,t.type from T_WP_TACTIC t) loop
insert into t_wp_reference
  select SEQ_T_WP_reference_ID.Nextval,
         appid,
         vc.categoryid,
         appname   as cname,
         rownum                as         sortid,
         sysdate                as        lupdate
    from (select * from   (select c.appid, c.appname, c.updatetime
             from t_wp_content c
            where c.apptype2 = vc.apptype
              and c.apptype1 =  vc.type ) s1 where not exists
          (select 1
             from (select r.appid
                     from t_wp_reference r
                    where r.categoryid = vc.categoryid ) s2
            where s1.appid = s2.appid ) order by s1.updatetime asc);

  end loop;
  --- 下架已经下线的应用的商品
  delete from t_wp_reference r where not exists (select 1 from t_wp_content c where c.appid=r.appid);
  
  
  --如果成功，将执行情况写入日志
  v_nstatus := pg_log_manage.f_successlog;
  commit;
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;

 ---------------------------- ----------------------------
 -------------存储过程结束，需要单独执行-----------------
------------初始化WP货架数据开始----------
insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10000', '基础分类货架', '基础分类货架', '', '1', '-1', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10001', '应用', '应用', '', '1', '10000', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10002', '游戏', '游戏', '', '1', '10000', 1, to_date('19-01-2015', 'dd-mm-yyyy'));


insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '娱乐八卦', '娱乐八卦', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '音乐视频', '音乐视频', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '实用工具', '实用工具', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '生活助手', '生活助手', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '育儿应用', '育儿应用', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '新闻天气', '新闻天气', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '旅游导航', '旅游导航', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '健康健身', '健康健身', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '照片壁纸', '照片壁纸', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '社交生活', '社交生活', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '体育运动', '体育运动', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '个人理财', '个人理财', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '商务办公', '商务办公', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '书刊阅读', '书刊阅读', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '教育教学', '教育教学', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '时政要闻', '时政要闻', '', '1', '10001', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '动作冒险', '动作冒险', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '棋牌游戏', '棋牌游戏', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '经典游戏', '经典游戏', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '休闲益智', '休闲益智', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '家庭同乐', '家庭同乐', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '音乐节奏', '音乐节奏', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '平台动作', '平台动作', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '趣味解谜', '趣味解谜', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '赛车飞行', '赛车飞行', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '角色扮演', '角色扮演', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '射击游戏', '射击游戏', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '运动休闲', '运动休闲', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, '战略模拟', '战略模拟', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, SEQ_T_WP_category_CID.Nextval, 'Xbox助手', 'Xbox助手', '', '1', '10002', 1, to_date('19-01-2015', 'dd-mm-yyyy'));


insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10100', '基础榜单货架', '基础榜单货架', '', '1', '-1', 1, to_date('19-01-2015', 'dd-mm-yyyy'));

insert into t_wp_category (ID, CATEGORYID, CNAME, CDESC, PIC, ISSHOW, PARENTCID, SORTID, LUPDATE)
values (SEQ_T_WP_category_ID.Nextval, '10200', '基础专题货架', '基础专题货架', '', '1', '-1', 1, to_date('19-01-2015', 'dd-mm-yyyy'));


insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '娱乐八卦', '11000', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '音乐视频', '11001', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '实用工具', '11002', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '生活助手', '11003', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '育儿应用', '11004', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '新闻天气', '11005', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '旅游导航', '11006', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '健康健身', '11007', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '照片壁纸', '11008', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '社交生活', '11009', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '体育运动', '11010', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '个人理财', '11011', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '商务办公', '11012', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '书刊阅读', '11013', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '教育教学', '11014', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '时政要闻', '11015', '0', to_date('21-01-2015 13:48:33', 'dd-mm-yyyy hh24:mi:ss'), '应用');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '动作冒险', '11016', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '棋牌游戏', '11017', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '经典游戏', '11018', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '休闲益智', '11019', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '家庭同乐', '11020', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '音乐节奏', '11021', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '平台动作', '11022', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '趣味解谜', '11023', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '赛车飞行', '11024', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '角色扮演', '11025', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '射击游戏', '11026', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '运动休闲', '11027', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, '战略模拟', '11028', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

insert into T_WP_TACTIC (ID, APPTYPE, CATEGORYID, TATYPE, LUPDATE, TYPE)
values (SEQ_T_WP_TACTIC_ID.Nextval, 'Xbox助手', '11029', '0', to_date('21-01-2015 13:48:57', 'dd-mm-yyyy hh24:mi:ss'), '游戏');

commit;
------------初始化WP货架数据结束----------


-----MM5.1天天锁屏专区货架自动更新初始化数据开始-----
declare 
  cateruleid number(8);
begin

insert into t_caterule_cond_base(base_id,base_name,base_sql) values(82,'获取MM5.1天天锁屏AP信息','select g.id from t_r_gcontent g where  g.icpcode = ''138690''');

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1天天锁屏专区',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555193',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,null,'g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1天天锁屏专区-爱情星座',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555194',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''爱情星座''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1天天锁屏专区-创意色彩',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555195',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''创意色彩''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1天天锁屏专区-卡通游戏',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555196',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''卡通游戏''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'MM5.1天天锁屏专区-节日搞笑',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1163555197',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.appcatename=''节日搞笑''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,82);

insert into t_a_auto_category(id,categoryid,isnulltosync) values(86,'100038586','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(87,'100038587','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(88,'100038588','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(89,'100038589','1');
insert into t_a_auto_category(id,categoryid,isnulltosync) values(90,'100038590','1');

commit;
end;

-----MM5.1天天锁屏专区货架自动更新初始化数据结束-----




