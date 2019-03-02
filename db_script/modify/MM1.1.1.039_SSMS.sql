insert into T_KEY_BASE (KEYID, KEYNAME, KEYTABLE, KEYDESC, KEYTYPE)
values ('41', 'book_banner_pic', 't_rb_category', '客户端banner图引用', '2');

update T_CATERULE_COND_BASE set BASE_SQL = 'select b.id from t_r_base b, t_r_gcontent g, t_r_down_sort_wap s, t_portal_down_d d, v_service v where b.id = g.id and d.content_id = g.contentid and s.content_id = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 2 and s.os_id = 2 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))' where BASE_ID = 30;

update T_CATERULE_COND_BASE set BASE_SQL = 'select b.id from t_r_base b, t_r_gcontent g, V_CONTENT_TIME d, v_service v where b.id = g.id and d.contentid = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%''  and d.updatetime=sysdate-1 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))' where BASE_ID = 35;

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (32, 'WWW数据免费,付费,总榜单数据信息', 'select b.id from t_r_base b, t_r_gcontent g, t_portal_down_d d, v_service v, V_CONTENT_TIME t where b.id = g.id and d.content_id = g.contentid and t.contentid = g.contentid  and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 1 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');


insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (33, 'WWW数据飙升统计信息', 'select b.id from t_r_base b, t_r_gcontent g, t_r_down_sort_wap s, t_portal_down_d d, v_service v, V_CONTENT_TIME t where b.id = g.id and d.content_id = g.contentid and t.contentid = g.contentid and s.content_id = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%'' and d.portal_id = 1 and s.os_id = 1 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');


insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1032, 'www飙升榜单规则', 0, 0, 1, 0, 0, -1);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1033, 'www免费榜单规则', 0, 0, 1, 0, 0, null);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1034, 'www付费榜单规则', 0, 0, 1, 0, 0, null);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1035, 'www总榜单规则', 0, 0, 1, 0, 0, null);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (1036, 'www最新榜单规则', 7, 0, 1, 0, 5, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1032, null, 0, null, 's.standard_score desc, d.add_7days_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1420, 33);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1033, null, 0, 'mobilePrice=0', 'd.add_7days_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1421, 32);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1034, null, 0, 'mobilePrice>0', 'd.add_7days_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1422, 32);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1035, null, 0, null, 'd.add_order_count desc,t.updatetime desc,g.name asc', -1, 1, 1423, 32);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values (1036, null, 0, 'g.catename in (''软件'', ''游戏'', ''主题'')', 'to_date(substr(d.updatetime,0,10)) desc, decode(g.catename, ''软件'', 1,''游戏'', 1, ''主题'',2) asc,to_date(d.createtime) asc,g.name asc', -1, 1, 1424, 35);



-- Add/modify columns 
alter table T_RB_CATEGORY add SORTID NUMBER(8) default 0;
-- Add comments to the columns 
comment on column T_RB_CATEGORY.SORTID
  is '货架排序';

create table t_vb_program
(
  ProgramID   varchar2(10) not null,
  ProgramNAME varchar2(128) not null,
  ContentID   varchar2(21) not null
)
;
-- Add comments to the columns 
comment on column t_vb_program.ProgramID
  is '节目ID';
comment on column t_vb_program.ProgramNAME
  is '节目中文名称';
comment on column t_vb_program.ContentID
  is '视频ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_vb_program
  add constraint t_vb_program_key primary key (PROGRAMID);

-- Create table
create table t_vb_programDetail
(
  programID   varchar2(10) not null,
  programNAME varchar2(128) not null,
  nodeID      varchar2(21) not null,
  description varchar2(4000) not null,
  filesize        NUMBER(12),
  duration    varchar2(21),
  logoUrl     varchar2(512) not null,
  programUrl  varchar2(512) not null,
  accessType  varchar2(2) not null
)
;
-- Add comments to the columns 
comment on column t_vb_programDetail.programID
  is '节目ID';
comment on column t_vb_programDetail.programNAME
  is '节目中文名称';
comment on column t_vb_programDetail.nodeID
  is '栏目ID';
comment on column t_vb_programDetail.description
  is '简介';
comment on column t_vb_programDetail.filesize
  is '文件大小';
comment on column t_vb_programDetail.duration
  is '时长';
comment on column t_vb_programDetail.logoUrl
  is 'LOGO URL';
comment on column t_vb_programDetail.programUrl
  is '节目URL';
comment on column t_vb_programDetail.accessType
  is '可适用门户 0.MM客户端 1.wap';

-- Create table
create table t_vb_node
(
  NodeId       varchar2(10) not null,
  NodeName     varchar2(128) not null,
  ClassLevel   number(2) not null,
  ShowPosition varchar2(3),
  accessType   number(2) not null
)
;
-- Add comments to the columns 
comment on column t_vb_node.NodeId
  is '栏目Id';
comment on column t_vb_node.NodeName
  is '栏目中文名称';
comment on column t_vb_node.ClassLevel
  is '层级 限定为1、2、3';
comment on column t_vb_node.ShowPosition
  is 'MM客户端展示情况 0： 没在MM展示 1： 为在MM 客户端展示';
comment on column t_vb_node.accessType
  is '可适用门户: 0.MM客户端 1.wap';





-------------------以下为 存储过程，需要整体执行-----
/*
名    称:  wwwAndwap飙升榜单源数据计算
内容摘要:  主要计算www和wap门户飙升榜单的上升最快指标得分
调    用:  无
被 调 用:  job直接调用
创 建 者:  chenhuoping
创建日期:  2012-03-01
源    表:  report_servenday_wap,v_cm_content
目 标 表:  t_r_down_sort_new_wap,t_r_down_sort_old_wap,t_r_down_sort_wap

更改历史：
2012-03-21     dongke      添加www飙升计算代码
*/

create or replace procedure P_BINDING_SORT_FINAL_WAPAWWW as
                      type type_table_osid is table of number index by binary_integer; --自定义类型
                      type type_table_sort is table of number index by binary_integer; --自定义类型
                      v_type_nosid   type_table_osid; --OS_ID集合
                      v_type_nsort   type_table_sort; --SORT_NUM_OLD集合
                      v_nosidi       number; --iphone类型
                      v_nosida       number; --wap门户类型
                      v_nosids       number; --www门户类型
                    
                      v_nsortmaxi    number; --iphone排序最大值
                      v_nsortmaxs    number; --www门户排序最大值
                      v_nsortmaxa    number; --wap门户排序最大值
                      v_vsqlinsert   varchar2(1500); --动态SQL
                      v_vsqlalter    varchar2(1500); --动态SQL
                      v_vsqltruncate varchar2(1500); --动态SQL
                      --v_vconstant  varchar(6):='0006'; --常量
                      v_nindnum    number;--记录索引是否存在
                      v_nstatus number;--纪录监控包状态
                      v_ncount number;--记录report_servenday表的记录数
                    
                    begin
                    v_nstatus:=pg_log_manage.f_startlog('P_BINDING_SORT_FINAL_WAP','货架榜单WAPAndWWW');
                    
                    --报表没有提供数据流程结束，抛出例外
                    --1：WWW
                    --2:WAP
                    --10:MOPPS
                    ---999:全部
                    
                    select count(9) into v_ncount from report_servenday_wap a
                         where a.os_id in (2,1)
                          and  rownum<2;
                    if v_ncount=0 then
                      raise_application_error(-20088,'报表提供记录数为0');
                      end if;
                    
                      --删除索引
                        select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID_WAP';
                         if v_nindnum>0 then
                       execute  immediate ' drop index IND_DOWNSORTO_CONTENTID_WAP';
                      end if;
                    
                        select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID_WAP';
                         if v_nindnum>0 then
                     execute  immediate ' drop index IND_DOWNSORT_CONTENTID_WAP';
                      end if;
                    
                        --删除前次数据
                      v_vsqltruncate := 'truncate table t_r_down_sort_new_wap ';
                       execute immediate v_vsqltruncate;
                       v_vsqltruncate := 'truncate table t_r_down_sort_wap ';
                      execute immediate v_vsqltruncate;
                      --禁止表写在线日志
                      execute immediate ' alter table t_r_down_sort_new_wap  nologging';
                      execute immediate ' alter table t_r_down_sort_old_wap  nologging';
                      execute immediate ' alter table t_r_down_sort_wap  nologging';
                    
                      --插入报表的原始数据到本地
                      insert /*+ append parallel(t_r_down_sort_new ,4) */ into t_r_down_sort_new_wap
                        (content_id, os_id, SORT_NUM,down_count,add_order_count)
                        select
                        /*+  parallel(report_servenday,4) */
                         content_id,
                         os_id,
                         dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
                         add_7days_down_count,add_order_count
                          from report_servenday_wap a
                         where a.os_id in(2,1);
                    commit;
                      --根据OS_ID分组获取t_r_down_sort.sort_num_old的最大值，用于更新报表前一次未提供conten_id的排序值
                      select os_id, max(sort_num) bulk collect
                        into v_type_nosid, v_type_nsort
                        from t_r_down_sort_new_wap
                       group by os_id;
                    
                      for i in 1 .. sql%rowcount loop
                        if v_type_nosid(i) = 2 then
                          ----wap门户
                          v_nosida    := 2;
                          v_nsortmaxa := v_type_nsort(i);
                        end if;
                         if v_type_nosid(i) = 1 then
                          ----www门户
                          v_nosids    := 1;
                          v_nsortmaxs := v_type_nsort(i);
                        end if;
                    
                      end loop;
                    
                      --更新、插入报表本次未提供conten_id值的数据
                        --插入一条wap门户数据
                        select count(9) into v_nindnum  from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID_WAP';
                        if v_nindnum=0 then
                      execute  immediate   ' create index IND_DOWNSORT_CONTENTID_WAP  on   t_r_down_sort_new_wap (content_id) ';
                      end if;
                      v_vsqlinsert:= 'insert  into t_r_down_sort_new_wap (content_id,os_id,sort_num,down_count)
                    select /*+ parallel(v,4) */ v.contentid,2,' || v_nsortmaxa  ||
                                   '+ 1,0  from v_cm_content v
                    where v.status=''0006'' and  not exists (select 1 from t_r_down_sort_new_wap  n where n.content_id=v.contentid and n.os_id=2)';
                    
                    execute immediate v_vsqlinsert;
                    
                     --更新、插入报表本次未提供conten_id值的数据
                        --插入一条www门户数据
                        
                      v_vsqlalter:= 'insert  into t_r_down_sort_new_wap (content_id,os_id,sort_num,down_count)
                    select /*+ parallel(v,4) */ v.contentid,1,' || v_nsortmaxs  ||
                                   '+ 1,0  from v_cm_content v
                    where v.status=''0006'' and  not exists (select 1 from t_r_down_sort_new_wap  n where n.content_id=v.contentid and n.os_id=1)';
                    
                    execute immediate v_vsqlalter;
                    
                      
                      commit;
                       /*  --报表本次提供数据，前次没有数据，补充前次数据
                      v_vsqlstr:='insert into t_r_down_sort_old
                        (content_id, os_id, sort_num)
                        select n.content_id,n.os_id,decode(n.os_id,9,'||v_nsortmaxa||',3,'||v_nsortmaxi||')
                          from t_r_down_sort_new  n
                         where not exists (select 1
                                  from t_r_down_sort_old  o
                                 where n.content_id = o.content_id
                                   and n.os_id = n.os_id)';
                         execute immediate   v_vsqlstr;*/
                         select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID_WAP';
                         if v_nindnum=0 then
                      execute  immediate   ' create index IND_DOWNSORTO_CONTENTID_wap  on   t_r_down_sort_old_wap (content_id) parallel';
                      end if;
                       insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort_wap
                        (content_id, os_id, sort_num_new, sort_num_old, minus_sort_null,minus_down_count,add_order_count,Standard_score)
                    select content_id,
                           os_id,
                           sort_num_new,
                           sort_num_old,
                           minus_sort_null,
                           minus_down_count,
                           add_order_count,
                    
                           decode((max(B) over(partition by os_id) - min(B) over( partition by os_id)),0,0, trunc((a -
                           abs(a) * (1 - (B - min(B) over(partition by os_id)) / (max(B) over( partition by os_id) - min(B) over( partition by os_id))))*0.6+b*0.4,3)) Standard_score
                      from (select n.content_id,
                           n.os_id,
                           n.sort_num sort_num_new,
                           o.sort_num sort_num_old,
                           o.sort_num - n.sort_num minus_sort_null,
                           n.down_count - o.down_count minus_down_count,
                    
                           decode(stddev(o.sort_num - n.sort_num) over(partition by o.os_id),
                                  0,
                                  0,
                                  ((o.sort_num - n.sort_num) - avg(o.sort_num - n.sort_num)
                                   over(partition by o.os_id)) / stddev(o.sort_num - n.sort_num) over(partition by o.os_id)) A,
                           decode(stddev(n.down_count-o.down_count) over(partition by n.os_id),
                                  0,
                                  0,
                                  ((n.down_count - o.down_count) -
                                  avg(n.down_count - o.down_count) over(partition by o.os_id)) /
                                  stddev(n.down_count-o.down_count) over(partition by n.os_id)) B,
                    
                           n.add_order_count
                      from t_r_down_sort_old_wap  o, t_r_down_sort_new_wap  n
                     where o.content_id = n.content_id
                       and o.os_id = n.os_id)g;
                       v_nstatus:=pg_log_manage.f_successlog(v_nstatus);
                      commit;
                    /* execute  immediate ' drop index IND_DOWNSORTO_CONTENTID ';
                     execute  immediate ' drop index IND_DOWNSORT_CONTENTID ';*/
                    
                      --将本次数据变为前次数据
                      v_vsqlalter := 'alter table t_r_down_sort_old_wap  rename to t_r_down_sort_old_wap_1 ';
                      execute immediate v_vsqlalter;
                    
                      v_vsqlalter := 'alter table t_r_down_sort_new_wap  rename to t_r_down_sort_old_wap  ';
                      execute immediate v_vsqlalter;
                    
                      v_vsqlalter := 'alter table t_r_down_sort_old_wap_1 rename to t_r_down_sort_new_wap  ';
                      execute immediate v_vsqlalter;
                    
                      exception
                      when others then
                          v_nstatus:=pg_log_manage.f_errorlog;
                    
end;
------------------------------------------------------------------------
--主题大于1000的数据库修改开始
update t_caterule_cond set wsql='type=''nt:gcontent:appTheme'' and mobilePrice>0' where ruleid = 222;
update t_caterule_cond set wsql='mobilePrice = 0 and type=''nt:gcontent:appTheme''' where ruleid=94;
update t_caterule_cond set wsql='mobilePrice = 0' where ruleid=2;
update t_caterule_cond set wsql='mobilePrice>1000 and mobilePrice<=2000' where ruleid=149 and condtype=10;
update t_caterule_cond set wsql='mobilePrice>0' where ruleid=411;
update t_caterule_cond set wsql='mobilePrice=0' where ruleid=413;
update t_caterule_cond set wsql='mobilePrice=0' where ruleid=283;
update t_caterule_cond set wsql='mobilePrice>0' where ruleid=284;
update t_caterule_cond set wsql='mobilePrice=0' where ruleid=285;
update t_caterule_cond set wsql='mobilePrice>0' where ruleid=286;
update t_caterule_cond set wsql='mobilePrice>1000 and mobilePrice<=2000' where ruleid=111 and condtype=10;
--主题大于1000的数据库修改结束
------------------------------------------------------------------------
--动漫基地导入开始
create table T_CB_COMICSERIES
(
  CONTENTCODE        VARCHAR2(60) not null,
  COUNT              NUMBER(12) not null,
  COMICSERISCATEGORY VARCHAR2(128) not null,
  COMICSERISTYPE     VARCHAR2(50) not null,
  STYLE              VARCHAR2(50) not null,
  COLOUR             VARCHAR2(50) not null,
  PRODUCTZONE        VARCHAR2(8) not null,
  LANGUAGE           VARCHAR2(50) not null,
  PUBLISHDATE        VARCHAR2(8),
  SERIALISESTATUS    VARCHAR2(8),
  CANTO              VARCHAR2(8) not null,
  primary key (CONTENTCODE)
);

-- Create table
create table T_CB_CP
(
  CPID   VARCHAR2(60) not null,
  CPNAME VARCHAR2(60) not null,
  primary key (CPID)
);
create table T_CB_DM
(
  CONTENTCODE      VARCHAR2(60) not null,
  NAME             VARCHAR2(512) not null,
  DESCRIPTION      VARCHAR2(1024) not null,
  CPID             VARCHAR2(60) not null,
  AUTHOR           VARCHAR2(60) not null,
  TYPE             VARCHAR2(8) not null,
  KEYWORD          VARCHAR2(4000),
  EXPIRETIME       VARCHAR2(20) not null,
  DEFAULTPRICEINFO VARCHAR2(4000),
  OWNERTYPE        VARCHAR2(1),
  CONTENTHLRCODE   VARCHAR2(2) not null,
  CONTENTFIRSTCODE VARCHAR2(60) not null,
  SIMGURL1         VARCHAR2(512),
  SIMGURL2         VARCHAR2(512),
  SIMGURL3         VARCHAR2(512),
  SIMGURL4         VARCHAR2(512),
  OPERATE          VARCHAR2(1) not null,
  BOSSSERCODE      VARCHAR2(60),
  URL1             VARCHAR2(512) not null,
  URL2             VARCHAR2(512),
  URL3             VARCHAR2(512),
  CREATETIME       VARCHAR2(14) not null,
  UPDATETIME       VARCHAR2(14) not null,
  DELFLAG          NUMBER(1) default 0 not null,
  primary key (CONTENTCODE)

);

create table T_CB_INFO
(
  CONTENTCODE VARCHAR2(60) not null,
  CONTENTS    VARCHAR2(4000) not null,
  PICTURE     VARCHAR2(4000),
  SOURCE      VARCHAR2(60),
  primary key (CONTENTCODE)
);
create table T_CB_RANK
(
  RANKID      VARCHAR2(20) not null,
  RANKNAME    VARCHAR2(60) not null,
  CONTENTCODE VARCHAR2(20) not null,
  SORTNUMBER  NUMBER(6) not null,
  primary key (RANKID, CONTENTCODE)
);
create table T_CB_THEME
(
  THEMECATEGORY VARCHAR2(100) not null,
  CONTENTCODE   VARCHAR2(60) not null,
primary key (CONTENTCODE)
);
create table T_CB_TVSERIES
(
  CONTENTCODE      VARCHAR2(60) not null,
  COUNT            NUMBER(12),
  TVSERISCATEGORY  VARCHAR2(100),
  DIRECTOR         VARCHAR2(4000),
  LEADINGACTOR     VARCHAR2(4000),
  OTHERACTOR       VARCHAR2(4000),
  PRODUCTZONE      VARCHAR2(50),
  LANGUAGE         VARCHAR2(50),
  PUBLISHDATE      VARCHAR2(8),
  TVSERISSERISTYPE VARCHAR2(50),
  STYLE            VARCHAR2(50),
  COLOUR           VARCHAR2(50),
  SERIALISESTATUS  VARCHAR2(4),
  CANTO            VARCHAR2(4),
  primary key (CONTENTCODE)
);

-- Add comments to the columns 
comment on column T_CB_DM.CONTENTCODE
  is '内容唯一标识';
comment on column T_CB_DM.NAME
  is '内容名称';
comment on column T_CB_DM.DESCRIPTION
  is '内容描述';
comment on column T_CB_DM.CPID
  is '内容提供方';
comment on column T_CB_DM.AUTHOR
  is '内容原创者，作者名称';
comment on column T_CB_DM.TYPE
  is '内容类型，取值如下:
101:Theme，主题
104:MovieSeries，动画片
115:Information，资讯
221:ComicSeries，漫画书
';
comment on column T_CB_DM.KEYWORD
  is '内容关键字，允许出现多次，多值之间用竖线"|"隔开；';
comment on column T_CB_DM.EXPIRETIME
  is '内容超期时间。门户根据该字段决定何时停止展示内容给终端用户。格式为yyyy-MM-dd''T''HH:mm:ss''Z''
格式：YYYYMMDDHHMMSS
如：20090601010101
';
comment on column T_CB_DM.DEFAULTPRICEINFO
  is '内容的默认资费描述';
comment on column T_CB_DM.OWNERTYPE
  is '内容提供者类型. 取值如下：                                                                  0：SPCP
1：终端用户
2：运营商(预留)
3：集团用户(预留)。
默认表示SPCP。
';
comment on column T_CB_DM.CONTENTHLRCODE
  is '内容归属地：
00:集团(全国)
01:北京
02:天津
03:河北
04:山西
05:内蒙古
06:辽宁
07:吉林
08:黑龙江
09:上海
10:江苏
11:浙江
12:安徽
13:福建
14:江西
15:山东
16:河南
17:湖北
18:湖南
19:广东
20:海南
21:广西
22:重庆
23:四川
24:贵州
25:云南
26:陕西
27:甘肃
28:青海
29:宁夏
30:新疆
31:西藏
';
comment on column T_CB_DM.CONTENTFIRSTCODE
  is '内容的首字母';
comment on column T_CB_DM.SIMGURL1
  is '预览图1（具体的规格大小由MM运营确定）60X80';
comment on column T_CB_DM.SIMGURL2
  is '预览图2150X200';
comment on column T_CB_DM.SIMGURL3
  is '预览图3120X160';
comment on column T_CB_DM.SIMGURL4
  is '预览图4 90X120';
comment on column T_CB_DM.OPERATE
  is '1：新增；
2：更新；
3：下线
用于表示内容的处理状态
';
comment on column T_CB_DM.BOSSSERCODE
  is 'Boss侧的计费代码；（资讯内容为空）
注：动漫基地提供业务代码表
';
comment on column T_CB_DM.URL1
  is '内容详情页URL，用于WAP';
comment on column T_CB_DM.URL2
  is '内容详情页URL，待用';
comment on column T_CB_DM.CREATETIME
  is '内容创建时间，必填,格式：YYYYMMDDHHMMSS
如：20090601010101
';
comment on column T_CB_DM.UPDATETIME
  is '内容最后更新时间，必填,格式：YYYYMMDDHHMMSS
如：20090601010101
';
comment on column T_CB_DM.DELFLAG
  is '0：没有被删除，1：删除';

-- Add comments to the columns 
comment on column T_CB_INFO.CONTENTCODE
  is '内容唯一标识';
comment on column T_CB_INFO.CONTENTS
  is '资讯内容';
comment on column T_CB_INFO.PICTURE
  is '资讯配套的图片，用以支持图文效果展示，，允许出现多次，多值之间用竖线"|"隔开.';
comment on column T_CB_INFO.SOURCE
  is '资讯来源';
  
-- Add comments to the columns 
comment on column T_CB_RANK.RANKID
  is '1500漫画畅销排行榜（周排行）
1501漫画畅销排行榜（月排行）
1502漫画畅销排行榜（总排行）
1503漫画点击排行榜（周排行）
1504漫画点击排行榜（月排行）
1505漫画点击排行榜（总排行）
1506漫画评分排行榜（周排行）
1507漫画评分排行榜（月排行）
1508漫画评分排行榜（总排行）
1524动画畅销排行榜（周排行）
1525动画畅销排行榜（月排行）
1526动画畅销排行榜（总排行）
1527动画点击排行榜（周排行）
1528动画点击排行榜（月排行）
1529动画点击排行榜（总排行）
1530动画评分排行榜（周排行）
1531动画评分排行榜（月排行）
1532动画评分排行榜（总排行）
1572主题畅销排行榜（周排行）
1573主题畅销排行榜（月排行）
1574主题畅销排行榜（总排行）
1575主题点击排行榜（周排行）
1576主题点击排行榜（月排行）
1577主题点击排行榜（总排行）
1578主题评分排行榜（周排行）
1579主题评分排行榜（月排行）
1580主题评分排行榜（总排行）
';
comment on column T_CB_RANK.RANKNAME
  is '专区下的动漫(见rankid的具体名称)
包括
漫画，动画，主题的周排行，月排行，总排行，最新 
';
comment on column T_CB_RANK.CONTENTCODE
  is '内容唯一标识';
comment on column T_CB_RANK.SORTNUMBER
  is '排序序号。从小到大排列。必须是整数，取值范围在-999999到999999之间。';
  
  
-- Add comments to the columns 
comment on column T_CB_THEME.THEMECATEGORY
  is '主题分类，允许出现多次，多值之间用竖线"|"隔开:
15500:搞笑
15501:萌系
15502:唯美
15503:恋爱
15504:节日
15505:校园
15506:星座
15507:运动
15508:游戏
15509:手绘
16017:其他
';
comment on column T_CB_THEME.CONTENTCODE
  is '内容唯一标识';
  
-- Add comments to the columns 
comment on column T_CB_TVSERIES.CONTENTCODE
  is '内容唯一标识';
comment on column T_CB_TVSERIES.COUNT
  is '动画片中单集的个数';
comment on column T_CB_TVSERIES.TVSERISCATEGORY
  is '动画片分类，允许出现多次，多值之间用竖线"|"隔开:
15400:搞笑
15401:冒险
15402:运动
15403:情感
15404:科幻
15405:悬疑
15406:国学
15407:教育
15408:传记
15409:时讯
15410:经典
16017:其他
';
comment on column T_CB_TVSERIES.DIRECTOR
  is '作者，允许出现多次，允许出现多次，多值之间用竖线"|"隔开';
comment on column T_CB_TVSERIES.LEADINGACTOR
  is '主演，允许出现多次，允许出现多次，多值之间用竖线"|"隔开';
comment on column T_CB_TVSERIES.OTHERACTOR
  is '其他演员，允许出现多次，允许出现多次，多值之间用竖线"|"隔开';
comment on column T_CB_TVSERIES.PRODUCTZONE
  is '出品地区:
5460:欧美
5461:大陆
5462:台湾
5463:日本
5464:韩国
5465:其他
5466:香港
5468:澳门
';
comment on column T_CB_TVSERIES.LANGUAGE
  is '语种，允许出现多次，多值之间用竖线"|"隔开:
5420:中文
5421:英文
5422:韩文
5423:日文
5424:其他
';
comment on column T_CB_TVSERIES.PUBLISHDATE
  is '发行日期。格式为YYYY';
comment on column T_CB_TVSERIES.TVSERISSERISTYPE
  is '类型，允许出现多次，多值之间用竖线"|"隔开:
5430:TV版
5431:OVA版
5432:OAD版
5433:剧场版
5434:真人版
5435:特典版
5436:宣传片
5437:其他
';
comment on column T_CB_TVSERIES.STYLE
  is '风格，允许出现多次，多值之间用竖线"|"隔开:
5440:写实
5441:Q版
5442:古典
5443:现代
5444:其他
';
comment on column T_CB_TVSERIES.COLOUR
  is '颜色，允许出现多次，多值之间用竖线"|"隔开:
5450:黑白
5451:全彩
5452:混合
';
comment on column T_CB_TVSERIES.SERIALISESTATUS
  is '连载的状态:
7100:完结
7101:连载
';
comment on column T_CB_TVSERIES.CANTO
  is '篇章类型:
7112:短篇
7111:中篇
7110:长篇
';

--动漫基地导入结束
------------------------------------------------------------------------

------------------------------------------------------------------------
------------------------------------------------------------------------
------------------------------------------------------------------------





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.035_SSMS','MM1.1.1.039_SSMS');
commit;
