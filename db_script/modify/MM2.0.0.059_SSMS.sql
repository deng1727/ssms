-- Create table
create table t_cm_category
(
  categoryId     number(11) not null,
  categoryName   varchar2(50) not null,
  PcategoryId    number(11),
  sortid         number(11),
  createtime     varchar2(14) not null,
  updatetime     varchar2(14) not null,
  state          number(11) not null,
  categorybanner varchar2(255),
  exporttime     date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_cm_category.categoryId
  is '分类主键';
comment on column t_cm_category.categoryName
  is '分类名称';
comment on column t_cm_category.PcategoryId
  is '父级分类主键';
comment on column t_cm_category.sortid
  is '分类排序字段';
comment on column t_cm_category.createtime
  is '分类创建时间';
comment on column t_cm_category.updatetime
  is '分类修改时间';
comment on column t_cm_category.state
  is '分类状态 0表示未启用 1表示已启用';
comment on column t_cm_category.categorybanner
  is '分类banner';
comment on column t_cm_category.exporttime
  is '导入时间';
-- Create/Recreate indexes 
create index pk_cm_category_id on t_cm_category (categoryid);


-- Create table
create table T_CM_content
(
  id          number(12) not null,
  serverId    number(12) not null,
  contentid   varchar2(100),
  name        varchar2(60),
  DESCRIPTION varchar2(600),
  keyValue    number(12),
  type        number(12),
  sortid      number(12),
  state       number(12) not null,
  iswork      number(5) not null,
  picpath     varchar2(4000) not null,
  showpath    varchar2(4000),
  property    varchar2(4000),
  createtime  varchar2(14),
  updatetime  varchar2(14),
  exporttime  date default sysdate
)
;
-- Add comments to the columns 
comment on column T_CM_content.id
  is '素材ID';
comment on column T_CM_content.serverId
  is '素材业务ID';
comment on column T_CM_content.contentid
  is '素材内容ID';
comment on column T_CM_content.name
  is '素材名称';
comment on column T_CM_content.DESCRIPTION
  is '素材描述';
comment on column T_CM_content.keyValue
  is '素材形象主键';
comment on column T_CM_content.type
  is '素材类型 1 图片 2 音频 3 视频';
comment on column T_CM_content.sortid
  is '素材排序';
comment on column T_CM_content.state
  is '素材状态 0表示禁用、1表示启用';
comment on column T_CM_content.iswork
  is '再加工素材 0-	不可再加工 1-	可以再加工';
comment on column T_CM_content.picpath
  is '素材访问路径 图片列表展示路径和合成预览路径以"|"分割';
comment on column T_CM_content.showpath
  is '素材预览路径';
comment on column T_CM_content.property
  is '素材属性 格式为JSON，目前只有图片使用。';
comment on column T_CM_content.createtime
  is '创建时间';
comment on column T_CM_content.updatetime
  is '修改时间';
comment on column T_CM_content.exporttime
  is '导入时间';
-- Create/Recreate indexes 
create index pk_CM_content_id on T_CM_content (id);



-- Create table
create table t_cm_reference
(
  id         number(11) not null,
  categoryid number(11),
  resourceid number(11),
  sortid     number(11),
  state      number(11),
  createtime varchar2(14),
  updatetime varchar2(14),
  exporttime date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_cm_reference.id
  is '关联主键Id';
comment on column t_cm_reference.categoryid
  is '分类主键';
comment on column t_cm_reference.resourceid
  is '资源主键';
comment on column t_cm_reference.sortid
  is '排序';
comment on column t_cm_reference.state
  is '状态 0-	生效 1-		失效';
comment on column t_cm_reference.createtime
  is '创建时间';
comment on column t_cm_reference.updatetime
  is '修改时间';
comment on column t_cm_reference.exporttime
  is '导入时间';
-- Create/Recreate indexes 
create index PK_CM_REFERENCE_ID on T_CM_REFERENCE (ID, RESOURCEID, CATEGORYID);



-- Create table
create table t_cm_recommend
(
  id          number(12) not null,
  name        varchar2(60) not null,
  DESCRIPTION varchar2(4000),
  recBanner   varchar2(4000),
  type        number(12),
  state       number(12),
  sortid      number(12),
  createtime  varchar2(14),
  updatetime  varchar2(14),
  Placement   varchar2(600),
  urlAddress  varchar2(600),
  exporttime  date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_cm_recommend.id
  is '推荐id';
comment on column t_cm_recommend.name
  is '推荐名称';
comment on column t_cm_recommend.DESCRIPTION
  is '推荐描述';
comment on column t_cm_recommend.recBanner
  is '推荐Banner';
comment on column t_cm_recommend.type
  is '推荐内容类型 1表示图片、2表示形象、3表示短语、4表示栏目';
comment on column t_cm_recommend.state
  is '推荐内容状态 0表示禁用、1表示启用';
comment on column t_cm_recommend.sortid
  is '排序号';
comment on column t_cm_recommend.createtime
  is '创建时间';
comment on column t_cm_recommend.updatetime
  is '修改时间';
comment on column t_cm_recommend.Placement
  is '推荐内容放置位置 1首页、2发送完成页等';
comment on column t_cm_recommend.urlAddress
  is '外部链接URL';
comment on column t_cm_recommend.exporttime
  is '导入时间';
-- Create/Recreate indexes 
create index pk_cm_recommend_id on t_cm_recommend (id);


-- Create table
create table T_CM_RECOMMEND_LINK
(
  id          number(12) not null,
  type        number(12) not null,
  recommendID number(12),
  OtherID     number(12),
  sortid      number(12),
  exporttime  date default sysdate not null
)
;
-- Add comments to the columns 
comment on column T_CM_RECOMMEND_LINK.id
  is '关联主键Id';
comment on column T_CM_RECOMMEND_LINK.type
  is '关联类型 1表示素材、2表示形象、3表示短语、4表示分类';
comment on column T_CM_RECOMMEND_LINK.recommendID
  is '推荐主键Id';
comment on column T_CM_RECOMMEND_LINK.OtherID
  is '推荐外部主键Id  可以是素材、形象、短语和分类ID';
comment on column T_CM_RECOMMEND_LINK.sortid
  is '排序id';
comment on column T_CM_RECOMMEND_LINK.exporttime
  is '导入时间';
-- Create/Recreate indexes 
create index PK__CM_RECOMMEND_LINK_ID on T_CM_RECOMMEND_LINK (ID, TYPE, RECOMMENDID, OTHERID);


-------------------------------------
---分省权限脚本----------------------------
----相应的货架ID为现网货架ID-----------------------------
------js 江苏------
----已经在现网手动创建---
------sh 上海------

insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SH', '上海专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SH', '上海专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938313', '上海专区',t.categoryid  , '0', '9', 'ssms_category_content_SH', '2' from t_r_category t where t.id='703938313';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938313', '上海专区',t.categoryid  , '0', '9', 'ssms_category_class_SH', '1' from t_r_category t where t.id='703938313';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844257', '上海专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SH', '2' from t_r_category t where t.id='704844257';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844257', '上海专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SH', '1' from t_r_category t where t.id='704844257';

------fj 福建------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_FJ', '福建专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_FJ', '福建专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938615', '福建专区',t.categoryid  , '0', '9', 'ssms_category_content_FJ', '2' from t_r_category t where t.id='703938615';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938615', '福建专区',t.categoryid  , '0', '9', 'ssms_category_class_FJ', '1' from t_r_category t where t.id='703938615';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844258', '福建专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_FJ', '2' from t_r_category t where t.id='704844258';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844258', '福建专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_FJ', '1' from t_r_category t where t.id='704844258';

------ah 安徽------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_AH', '安徽专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_AH', '安徽专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938616', '安徽专区',t.categoryid  , '0', '9', 'ssms_category_content_AH', '2' from t_r_category t where t.id='703938616';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938616', '安徽专区',t.categoryid  , '0', '9', 'ssms_category_class_AH', '1' from t_r_category t where t.id='703938616';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844259', '安徽专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_AH', '2' from t_r_category t where t.id='704844259';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844259', '安徽专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_AH', '1' from t_r_category t where t.id='704844259';


------jx 江西------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_JX', '江西专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_JX', '江西专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938660', '江西专区',t.categoryid  , '0', '9', 'ssms_category_content_JX', '2' from t_r_category t where t.id='703938660';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938660', '江西专区',t.categoryid  , '0', '9', 'ssms_category_class_JX', '1' from t_r_category t where t.id='703938660';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844260', '江西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_JX', '2' from t_r_category t where t.id='704844260';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844260', '江西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_JX', '1' from t_r_category t where t.id='704844260';


------gd 广东------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GD', '广东专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GD', '广东专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938769', '广东专区',t.categoryid  , '0', '9', 'ssms_category_content_GD', '2' from t_r_category t where t.id='703938769';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938769', '广东专区',t.categoryid  , '0', '9', 'ssms_category_class_GD', '1' from t_r_category t where t.id='703938769';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844261', '广东专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GD', '2' from t_r_category t where t.id='704844261';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844261', '广东专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GD', '1' from t_r_category t where t.id='704844261';


------gx 广西------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GX', '广西专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GX', '广西专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938770', '广西专区',t.categoryid  , '0', '9', 'ssms_category_content_GX', '2' from t_r_category t where t.id='703938770';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938770', '广西专区',t.categoryid  , '0', '9', 'ssms_category_class_GX', '1' from t_r_category t where t.id='703938770';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844262', '广西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GX', '2' from t_r_category t where t.id='704844262';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844262', '广西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GX', '1' from t_r_category t where t.id='704844262';


------hi 海南------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HI', '海南专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HI', '海南专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938826', '海南专区',t.categoryid  , '0', '9', 'ssms_category_content_HI', '2' from t_r_category t where t.id='703938826';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938826', '海南专区',t.categoryid  , '0', '9', 'ssms_category_class_HI', '1' from t_r_category t where t.id='703938826';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844263', '海南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HI', '2' from t_r_category t where t.id='704844263';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844263', '海南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HI', '1' from t_r_category t where t.id='704844263';


------hn 湖南------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HN', '湖南专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HN', '湖南专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938827', '湖南专区',t.categoryid  , '0', '9', 'ssms_category_content_HN', '2' from t_r_category t where t.id='703938827';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938827', '湖南专区',t.categoryid  , '0', '9', 'ssms_category_class_HN', '1' from t_r_category t where t.id='703938827';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844264', '湖南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HN', '2' from t_r_category t where t.id='704844264';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844264', '湖南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HN', '1' from t_r_category t where t.id='704844264';

------hb 湖北------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HB', '湖北专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HB', '湖北专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938828', '湖北专区',t.categoryid  , '0', '9', 'ssms_category_content_HB', '2' from t_r_category t where t.id='703938828';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938828', '湖北专区',t.categoryid  , '0', '9', 'ssms_category_class_HB', '1' from t_r_category t where t.id='703938828';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844265', '湖北专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HB', '2' from t_r_category t where t.id='704844265';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844265', '湖北专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HB', '1' from t_r_category t where t.id='704844265';


------sc 四川------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SC', '四川专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SC', '四川专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938842', '四川专区',t.categoryid  , '0', '9', 'ssms_category_content_SC', '2' from t_r_category t where t.id='703938842';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938842', '四川专区',t.categoryid  , '0', '9', 'ssms_category_class_SC', '1' from t_r_category t where t.id='703938842';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844266', '四川专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SC', '2' from t_r_category t where t.id='704844266';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844266', '四川专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SC', '1' from t_r_category t where t.id='704844266';


------yn 云南------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_YN', '云南专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_YN', '云南专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938856', '云南专区',t.categoryid  , '0', '9', 'ssms_category_content_YN', '2' from t_r_category t where t.id='703938856';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938856', '云南专区',t.categoryid  , '0', '9', 'ssms_category_class_YN', '1' from t_r_category t where t.id='703938856';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844267', '云南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_YN', '2' from t_r_category t where t.id='704844267';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844267', '云南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_YN', '1' from t_r_category t where t.id='704844267';

------gz 贵州------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GZ', '贵州专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GZ', '贵州专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938882', '贵州专区',t.categoryid  , '0', '9', 'ssms_category_content_GZ', '2' from t_r_category t where t.id='703938882';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938882', '贵州专区',t.categoryid  , '0', '9', 'ssms_category_class_GZ', '1' from t_r_category t where t.id='703938882';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844268', '贵州专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GZ', '2' from t_r_category t where t.id='704844268';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844268', '贵州专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GZ', '1' from t_r_category t where t.id='704844268';

------cq 重庆------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_CQ', '重庆专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_CQ', '重庆专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938883', '重庆专区',t.categoryid  , '0', '9', 'ssms_category_content_CQ', '2' from t_r_category t where t.id='703938883';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938883', '重庆专区',t.categoryid  , '0', '9', 'ssms_category_class_CQ', '1' from t_r_category t where t.id='703938883';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844269', '重庆专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_CQ', '2' from t_r_category t where t.id='704844269';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844269', '重庆专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_CQ', '1' from t_r_category t where t.id='704844269';


------sn 陕西------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SN', '陕西专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SN', '陕西专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938939', '陕西专区',t.categoryid  , '0', '9', 'ssms_category_content_SN', '2' from t_r_category t where t.id='703938939';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938939', '陕西专区',t.categoryid  , '0', '9', 'ssms_category_class_SN', '1' from t_r_category t where t.id='703938939';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844270', '陕西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SN', '2' from t_r_category t where t.id='704844270';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844270', '陕西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SN', '1' from t_r_category t where t.id='704844270';

------xz 西藏------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_XZ', '西藏专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_XZ', '西藏专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939050', '西藏专区',t.categoryid  , '0', '9', 'ssms_category_content_XZ', '2' from t_r_category t where t.id='703939050';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939050', '西藏专区',t.categoryid  , '0', '9', 'ssms_category_class_XZ', '1' from t_r_category t where t.id='703939050';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844271', '西藏专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_XZ', '2' from t_r_category t where t.id='704844271';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844271', '西藏专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_XZ', '1' from t_r_category t where t.id='704844271';


------bj 北京------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_BJ', '北京专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_BJ', '北京专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '356996502', '北京专区',t.categoryid  , '0', '9', 'ssms_category_content_BJ', '2' from t_r_category t where t.id='356996502';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '356996502', '北京专区',t.categoryid  , '0', '9', 'ssms_category_class_BJ', '1' from t_r_category t where t.id='356996502';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844272', '北京专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_BJ', '2' from t_r_category t where t.id='704844272';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844272', '北京专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_BJ', '1' from t_r_category t where t.id='704844272';


------tj 天津------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_TJ', '天津专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_TJ', '天津专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939051', '天津专区',t.categoryid  , '0', '9', 'ssms_category_content_TJ', '2' from t_r_category t where t.id='703939051';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939051', '天津专区',t.categoryid  , '0', '9', 'ssms_category_class_TJ', '1' from t_r_category t where t.id='703939051';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844273', '天津专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_TJ', '2' from t_r_category t where t.id='704844273';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844273', '天津专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_TJ', '1' from t_r_category t where t.id='704844273';


------nmg 内蒙古------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_NMG', '内蒙古专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_NMG', '内蒙古专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939052', '内蒙古专区',t.categoryid  , '0', '9', 'ssms_category_content_NMG', '2' from t_r_category t where t.id='703939052';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939052', '内蒙古专区',t.categoryid  , '0', '9', 'ssms_category_class_NMG', '1' from t_r_category t where t.id='703939052';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844274', '内蒙古专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_NMG', '2' from t_r_category t where t.id='704844274';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844274', '内蒙古专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_NMG', '1' from t_r_category t where t.id='704844274';


------sd 山东------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SD', '山东专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SD', '山东专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939053', '山东专区',t.categoryid  , '0', '9', 'ssms_category_content_SD', '2' from t_r_category t where t.id='703939053';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939053', '山东专区',t.categoryid  , '0', '9', 'ssms_category_class_SD', '1' from t_r_category t where t.id='703939053';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844275', '山东专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SD', '2' from t_r_category t where t.id='704844275';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844275', '山东专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SD', '1' from t_r_category t where t.id='704844275';


------ln 辽宁------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_LN', '辽宁专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_LN', '辽宁专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939054', '辽宁专区',t.categoryid  , '0', '9', 'ssms_category_content_LN', '2' from t_r_category t where t.id='703939054';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939054', '辽宁专区',t.categoryid  , '0', '9', 'ssms_category_class_LN', '1' from t_r_category t where t.id='703939054';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844276', '辽宁专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_LN', '2' from t_r_category t where t.id='704844276';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844276', '辽宁专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_LN', '1' from t_r_category t where t.id='704844276';



------jn 吉林------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_JN', '吉林专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_JN', '吉林专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939055', '吉林专区',t.categoryid  , '0', '9', 'ssms_category_content_JN', '2' from t_r_category t where t.id='703939055';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939055', '吉林专区',t.categoryid  , '0', '9', 'ssms_category_class_JN', '1' from t_r_category t where t.id='703939055';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844277', '吉林专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_JN', '2' from t_r_category t where t.id='704844277';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844277', '吉林专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_JN', '1' from t_r_category t where t.id='704844277';


------hlj 黑龙江------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HLJ', '黑龙江专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HLJ', '黑龙江专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939056', '黑龙江专区',t.categoryid  , '0', '9', 'ssms_category_content_HLJ', '2' from t_r_category t where t.id='703939056';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939056', '黑龙江专区',t.categoryid  , '0', '9', 'ssms_category_class_HLJ', '1' from t_r_category t where t.id='703939056';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844278', '黑龙江专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HLJ', '2' from t_r_category t where t.id='704844278';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844278', '黑龙江专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HLJ', '1' from t_r_category t where t.id='704844278';


------ha 河南------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HA', '河南专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HA', '河南专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939057', '河南专区',t.categoryid  , '0', '9', 'ssms_category_content_HA', '2' from t_r_category t where t.id='703939057';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939057', '河南专区',t.categoryid  , '0', '9', 'ssms_category_class_HA', '1' from t_r_category t where t.id='703939057';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844279', '河南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HA', '2' from t_r_category t where t.id='704844279';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844279', '河南专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HA', '1' from t_r_category t where t.id='704844279';

------he 河北------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HE', '河北专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HE', '河北专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939058', '河北专区',t.categoryid  , '0', '9', 'ssms_category_content_HE', '2' from t_r_category t where t.id='703939058';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939058', '河北专区',t.categoryid  , '0', '9', 'ssms_category_class_HE', '1' from t_r_category t where t.id='703939058';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844280', '河北专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HE', '2' from t_r_category t where t.id='704844280';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844280', '河北专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HE', '1' from t_r_category t where t.id='704844280';

------sx 山西------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SX', '山西专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SX', '山西专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939059', '山西专区',t.categoryid  , '0', '9', 'ssms_category_content_SX', '2' from t_r_category t where t.id='703939059';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939059', '山西专区',t.categoryid  , '0', '9', 'ssms_category_class_SX', '1' from t_r_category t where t.id='703939059';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844281', '山西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SX', '2' from t_r_category t where t.id='704844281';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844281', '山西专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SX', '1' from t_r_category t where t.id='704844281';

------nx 宁夏------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_NX', '宁夏专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_NX', '宁夏专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939060', '宁夏专区',t.categoryid  , '0', '9', 'ssms_category_content_NX', '2' from t_r_category t where t.id='703939060';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939060', '宁夏专区',t.categoryid  , '0', '9', 'ssms_category_class_NX', '1' from t_r_category t where t.id='703939060';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844282', '宁夏专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_NX', '2' from t_r_category t where t.id='704844282';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844282', '宁夏专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_NX', '1' from t_r_category t where t.id='704844282';

------gs 甘肃------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GS', '甘肃专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GS', '甘肃专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939061', '甘肃专区',t.categoryid  , '0', '9', 'ssms_category_content_GS', '2' from t_r_category t where t.id='703939061';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939061', '甘肃专区',t.categoryid  , '0', '9', 'ssms_category_class_GS', '1' from t_r_category t where t.id='703939061';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844283', '甘肃专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GS', '2' from t_r_category t where t.id='704844283';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844283', '甘肃专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GS', '1' from t_r_category t where t.id='704844283';


------qh 青海------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_QH', '青海专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_QH', '青海专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939062', '青海专区',t.categoryid  , '0', '9', 'ssms_category_content_QH', '2' from t_r_category t where t.id='703939062';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939062', '青海专区',t.categoryid  , '0', '9', 'ssms_category_class_QH', '1' from t_r_category t where t.id='703939062';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844284', '青海专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_QH', '2' from t_r_category t where t.id='704844284';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844284', '青海专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_QH', '1' from t_r_category t where t.id='704844284';


------xj 新疆------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_XJ', '新疆专区商品管理', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_XJ', '新疆专区货架管理', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939063', '新疆专区',t.categoryid  , '0', '9', 'ssms_category_content_XJ', '2' from t_r_category t where t.id='703939063';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939063', '新疆专区',t.categoryid  , '0', '9', 'ssms_category_class_XJ', '1' from t_r_category t where t.id='703939063';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844285', '新疆专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_XJ', '2' from t_r_category t where t.id='704844285';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844285', '新疆专区(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_XJ', '1' from t_r_category t where t.id='704844285';


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (15, 'select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, '''' as SYNCFLAG from t_r_gcontent c,(select * from t_key_resource t where t.keyid=''435'') k, v_service v where c.contentid = k.tid(+) and c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 UNION ALL select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, '''' as SYNCFLAG from t_r_gcontent c,(select * from t_key_resource t where t.keyid=''435'') k, v_service v, (select distinct tt.contentid, tt.tbtype from V_CM_CONTENT_TB tt ) tb where c.contentid = k.tid(+) and c.icpservid = v.icpservid and c.contentid = tb.contentid and tb.tbtype=''2'' order by contentid', '推广联盟-内容全量数据', '1', 50000, ',', null, 29, 'i_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (16, 'select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, ''A'' as SYNCFLAG from t_r_gcontent c, (select * from t_key_resource t where t.keyid = ''435'') k, v_service v where c.contentid = k.tid(+) and c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and c.plupddate is not null and length(c.plupddate) = 19 UNION ALL select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, ''A'' as SYNCFLAG from t_r_gcontent c, (select * from t_key_resource t where t.keyid = ''435'') k, v_service v, (select distinct tt.contentid, tt.tbtype from V_CM_CONTENT_TB tt ) tb where c.contentid = k.tid(+) and c.icpservid = v.icpservid and c.contentid = tb.contentid and tb.tbtype = ''2'' and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and c.plupddate is not null and length(c.plupddate) = 19 UNION ALL select distinct t.contentid, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', 0, ''D'' from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by contentid', '推广联盟-内容增量数据', '1', 50000, ',', null, 29, 'a_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (17, 'select t.device_id,t.device_name,t.device_desc,t.os_id,t.os_detail,t.brand_id,b.brand_name,u.device_ua,upper(u.device_os_ua) device_os_us,u.device_ua_header from T_device t,t_device_ua u,t_device_brand b where u.device_id = t.device_id and b.brand_id=t.brand_id order by t.device_id', '推广联盟-机型数据', '1', 50000, ',', null, 10, 'i_DVC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (18, 'select t.pid,t.device_id,t.device_name,t.contentid,t.contentname,t.resourceid,t.id,t.absolutepath,t.url,t.programsize,t.createdate,t.prosubmitdate,t.match, v.package, v.version,t.permission,t.iscdn from T_A_CM_DEVICE_RESOURCE t, (select distinct p.contentid, p.version, p.package from V_CM_CONTENT_PACKAGE p) v where t.contentid = v.contentid(+) and t.version = v.version(+) order by t.pid,t.device_id', '推广联盟-安卓适配数据', '1', 50000, ',', null, 17, 'i_TACM_DR_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (19, 'select c.contentid, d.order7day_count, d.add_order_count, c.averagemark from t_r_gcontent c, v_serven_sort d ,v_service v where c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 and c.contentid = d.content_id(+) and d.os_id = ''9'' UNION ALL select c.contentid, d.order7day_count, d.add_order_count, c.averagemark from t_r_gcontent c, v_serven_sort d ,V_CM_CONTENT_TB tb where c.contentid = tb.contentid and tb.tbtype = ''2'' and c.contentid = d.content_id(+) and d.os_id = ''9'' order by contentid', '推广联盟-运营数据', '1', 50000, ',', null, 4, 'i_DRC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (20, 'select v.CONTENTID,v.PRODUCTNAME,v.FEE,v.CHARGETYPE,v.TBTYPE from V_CM_CONTENT_TB v order by v.CONTENTID', '推广联盟-PACKAGE数据', '1', 50000, ',', null, 5, 'i_CTB_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);

commit;



-----------------------------------------------
---下述脚本要在portalwww@MMportal 下整体执行------
-----------------------------------------------

declare
 seq_id  number;
 begin
 	
 	------js 江苏------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '江苏管理员', 'js_manager', 1, '江苏专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_JS');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_JS');


------sh 上海------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '上海管理员', 'sh_manager', 1, '上海专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SH');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SH');


------fj 福建------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '福建管理员', 'fj_manager', 1, '福建专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_FJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_FJ');


------ah 安徽------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '安徽管理员', 'ah_manager', 1, '安徽专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_AH');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_AH');

------jx 江西------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '江西管理员', 'jx_manager', 1, '江西专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_JX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_JX');

------gd 广东------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '广东管理员', 'gd_manager', 1, '广东专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GD');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GD');

------gx 广西------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '广西管理员', 'gx_manager', 1, '广西专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GX');

------hi 海南------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '海南管理员', 'hi_manager', 1, '海南专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HI');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HI');


------hn 湖南------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '湖南管理员', 'hn_manager', 1, '湖南专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HN');


------hb 湖北------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '湖北管理员', 'hb_manager', 1, '湖北专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HB');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HB');


------sc 四川------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '四川管理员', 'sc_manager', 1, '四川专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SC');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SC');


------yn 云南------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '云南管理员', 'yn_manager', 1, '云南专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_YN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_YN');


------gz 贵州------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '贵州管理员', 'gz_manager', 1, '贵州专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GZ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GZ');

------cq 重庆------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '重庆管理员', 'cq_manager', 1, '重庆专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_CQ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_CQ');


------sn 陕西------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '陕西管理员', 'sn_manager', 1, '陕西专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SN');


------xz 西藏------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '西藏管理员', 'xz_manager', 1, '西藏专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_XZ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_XZ');


------bj 北京------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '北京管理员', 'bj_manager', 1, '北京专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_BJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_BJ');

------tj 天津------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '天津管理员', 'tj_manager', 1, '天津专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_TJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_TJ');

------nmg 内蒙古------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '内蒙古管理员', 'nmg_manager', 1, '内蒙古专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_NMG');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_NMG');

------sd 山东------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '山东管理员', 'sd_manager', 1, '山东专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SD');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SD');

------ln 辽宁------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '辽宁管理员', 'ln_manager', 1, '辽宁专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_LN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_LN');

------jn 吉林------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '吉林管理员', 'jn_manager', 1, '吉林专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_JN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_JN');

------hlj 黑龙江------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '黑龙江管理员', 'hlj_manager', 1, '黑龙江专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HLJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HLJ');

------ha 河南------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '河南管理员', 'ha_manager', 1, '河南专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HA');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HA');

------he 河北------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '河北管理员', 'he_manager', 1, '河北专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HE');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HE');

------sx 山西------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '山西管理员', 'sx_manager', 1, '山西专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SX');


------nx 宁夏------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '宁夏管理员', 'nx_manager', 1, '宁夏专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_NX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_NX');


------gs 甘肃------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '甘肃管理员', 'gs_manager', 1, '甘肃专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GS');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GS');

------qh 青海------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '青海管理员', 'qh_manager', 1, '青海专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_QH');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_QH');


------xj 新疆------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '新疆管理员', 'xj_manager', 1, '新疆专区货架管理员', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_XJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_XJ');

end;

commit;





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.047_SSMS','MM2.0.0.0.059_SSMS');


commit;