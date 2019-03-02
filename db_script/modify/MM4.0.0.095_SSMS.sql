--------触点泛化-终端公司需求渠道商账号表（T_OPEN_CHANNELS）新增渠道商类型字段----
alter  table T_OPEN_CHANNELS add motype VARCHAR2(2) DEFAULT '1';
comment on column T_OPEN_CHANNELS.motype
  is '渠道商类型，0-表示终端公司，1-非终端公司';
--------触点泛化-终端公司需求渠道商根货架关系表(T_OPEN_CHANNELS_CATEGORY)新增企业(ap)代码字段
alter  table T_OPEN_CHANNELS_CATEGORY add icpcode VARCHAR2(20);
comment on column T_OPEN_CHANNELS_CATEGORY.icpcode
  is '企业(ap)代码';
  
---------------------创建TAC码库表开始------------
create table t_tac_code_base
(
  id            VARCHAR2(30),
  taccode       VARCHAR2(30),
  brand         VARCHAR2(100),
  device        VARCHAR2(200),
  createtime    DATE
);

-- Add comments to the table 
comment on table t_tac_code_base
  is 'TAC码库表';
-- Add comments to the columns 
comment on column t_tac_code_base.id
  is '主键Id';
comment on column t_tac_code_base.taccode
  is 'TAC码';
comment on column t_tac_code_base.brand
  is '手机品牌';
comment on column t_tac_code_base.device
  is '手机型号';
comment on column t_tac_code_base.createtime
  is '创建时间';
alter table T_TAC_CODE_BASE
  add constraint PK_T_TAC_CODE_BASE_ID primary key (ID);
---------------------创建TAC码库表结束------------
  
---------------------创建内容推送广告表开始------------
create table t_content_push_adv
(
  id            VARCHAR2(30),
  contentid     VARCHAR2(30),
  title         VARCHAR2(100),
  subtitle      VARCHAR2(100),
  starttime     VARCHAR2(30),
  endtime       VARCHAR2(30),
  rebrand       VARCHAR2(100),
  createtime    DATE
);

-- Add comments to the table 
comment on table t_content_push_adv
  is '内容推送广告表';
-- Add comments to the columns 
comment on column t_content_push_adv.id
  is '主键Id';
comment on column t_content_push_adv.contentid
  is '应用id';
comment on column t_content_push_adv.title
  is '主标题';
comment on column t_content_push_adv.subtitle
  is '副标题';
comment on column t_content_push_adv.starttime
  is '开始时间';
comment on column t_content_push_adv.endtime
  is '结束时间';
comment on column t_content_push_adv.rebrand
  is '推荐品牌';
comment on column t_content_push_adv.createtime
  is '创建时间';
alter table T_CONTENT_PUSH_ADV
  add constraint PK_T_CONTENT_PUSH_ADV_ID primary key (ID);
---------------------创建创建内容推送广告表结束------------
  
create sequence SEQ_T_TAC_CODE_BASE_ID 
start with 1
increment by 1
maxvalue 999999999999
minvalue 1
nocycle
cache 100;

create sequence SEQ_T_CONTENT_PUSH_ADV_ID 
start with 1
increment by 1
maxvalue 999999999999
minvalue 1
nocycle
cache 100;

---------------------------------图书货架表t_rb_category_new 添加三个字段 开始---------------------------------------------------------
 alter  table t_rb_category_new add read_status VARCHAR2(10) DEFAULT '1';
alter  table t_rb_category_new add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_rb_category_new add delpro_status number(2) default 2;
comment on column t_rb_category_new.read_status
  is '货架审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
  comment on column t_rb_category_new.goods_status
  is '商品审批状态--  0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column t_rb_category_new.delpro_status
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
-----------------------------------图书货架表t_rb_category_new 添加三个字段 结束-------------------------------------------------------

-----------------------------------创建图书货架审批表T_RB_CATEGORY_NEW_OPERATION 开始------------------------------------------------
 create table T_RB_CATEGORY_NEW_OPERATION
(
  id            VARCHAR2(30),
  operator      VARCHAR2(30),
  categoryid     VARCHAR2(20),
  operator_time DATE default sysdate,
  approval      VARCHAR2(30),
  approval_time DATE default sysdate,
  operation     VARCHAR2(10)
);

-- Add comments to the table 
comment on table T_RB_CATEGORY_NEW_OPERATION
  is '图书货架审批表';
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW_OPERATION.categoryid
  is '货架编码';
comment on column T_RB_CATEGORY_NEW_OPERATION.operator
  is '操作人';
comment on column T_RB_CATEGORY_NEW_OPERATION.operator_time
  is '操作时间';
comment on column T_RB_CATEGORY_NEW_OPERATION.approval
  is '审批人';
comment on column T_RB_CATEGORY_NEW_OPERATION.approval_time
  is '审批时间';
comment on column T_RB_CATEGORY_NEW_OPERATION.operation
  is '处理方式: 1 图书货架管理; 2 图书商品管理';
-----------------------------------创建图书货架审批表T_RB_CATEGORY_NEW_OPERATION 结束------------------------------------------------  

--------------------创建图书货架审批表序列号 SEQ_RB_APPROVAL_ID 开始----------------------------------------------------------------
create sequence SEQ_RB_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建图书货架审批表序列号 SEQ_RB_APPROVAL_ID 结束----------------------------------------------------------------

---------------------------------图书货架商品表T_RB_REFERENCE_NEW 添加三个字段 开始-------------------------------------------------------
alter  table T_RB_REFERENCE_NEW add VERIFY_DATE DATE default sysdate;
alter  table T_RB_REFERENCE_NEW add VERIFY_STATUS number(2) default 1;
alter  table T_RB_REFERENCE_NEW add DELFLAG number(2) default 2;
comment on column T_RB_REFERENCE_NEW.VERIFY_DATE
  is '审核时间';
comment on column T_RB_REFERENCE_NEW.VERIFY_STATUS
  is '审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column T_RB_REFERENCE_NEW.DELFLAG
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
---------------------------------图书货架商品表T_RB_REFERENCE_NEW 添加三个字段 结束-------------------------------------------------------



alter table t_email_address modify OPERATOREMAIL varchar2(2000);
alter table t_email_address modify APPROVALEMAIL varchar2(2000);

---------------------------------音乐货架表t_mb_category_new 添加三个字段 开始---------------------------------------------------------
alter  table t_mb_category_new add music_status VARCHAR2(10) DEFAULT '1';
alter  table t_mb_category_new add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_mb_category_new add delpro_status number(2) default 2;
comment on column t_mb_category_new.music_status
  is '货架审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
  comment on column t_mb_category_new.goods_status
  is '商品审批状态--  0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column t_mb_category_new.delpro_status
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
-----------------------------------音乐货架表t_mb_category_new 添加三个字段 结束-------------------------------------------------------

-----------------------------------创建音乐货架审批表T_MB_CATEGORY_NEW_OPERATION 开始------------------------------------------------
create table T_MB_CATEGORY_NEW_OPERATION
(
  id            VARCHAR2(30),
  operator      VARCHAR2(30),
  categoryid     VARCHAR2(20),
  operator_time DATE default sysdate,
  approval      VARCHAR2(30),
  approval_time DATE default sysdate,
  operation     VARCHAR2(10)
);

-- Add comments to the table 
comment on table T_MB_CATEGORY_NEW_OPERATION
  is '音乐货架审批表';
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW_OPERATION.categoryid
  is '货架编码';
comment on column T_MB_CATEGORY_NEW_OPERATION.operator
  is '操作人';
comment on column T_MB_CATEGORY_NEW_OPERATION.operator_time
  is '操作时间';
comment on column T_MB_CATEGORY_NEW_OPERATION.approval
  is '审批人';
comment on column T_MB_CATEGORY_NEW_OPERATION.approval_time
  is '审批时间';
comment on column T_MB_CATEGORY_NEW_OPERATION.operation
  is '处理方式: 1 音乐货架管理; 2 音乐商品管理';
-----------------------------------创建音乐货架审批表T_MB_CATEGORY_NEW_OPERATION 结束------------------------------------------------  

--------------------创建音乐货架审批表序列号SEQ_MB_APPROVAL_ID 开始----------------------------------------------------------------
create sequence SEQ_MB_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建音乐货架审批表序列号 SEQ_MB_APPROVAL_ID 结束----------------------------------------------------------------

---------------------------------音乐货架商品表T_MB_REFERENCE_NEW 添加三个字段 开始-------------------------------------------------------
alter  table T_MB_REFERENCE_NEW add VERIFY_DATE DATE default sysdate;
alter  table T_MB_REFERENCE_NEW add VERIFY_STATUS number(2) default 1;
alter  table T_MB_REFERENCE_NEW add DELFLAG number(2) default 2;
comment on column T_MB_REFERENCE_NEW.VERIFY_DATE
  is '审核时间';
comment on column T_MB_REFERENCE_NEW.VERIFY_STATUS
  is '审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column T_MB_REFERENCE_NEW.DELFLAG
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
---------------------------------音乐货架商品表T_MB_REFERENCE_NEW 添加三个字段 结束-------------------------------------------------------


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_001','图书货架管理提交审批成功','图书货架管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_002','图书货架管理审批发布成功','图书货架管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_003','图书货架管理审批不通过成功','图书货架管理审批不通过成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_004','图书商品管理提交审批成功','图书商品管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_005','图书商品管理审批发布成功','图书商品管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BOOKCATEGORY_RESULT_006','图书商品管理审批不通过成功','图书商品管理审批不通过成功');


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_001','音乐货架管理提交审批成功','音乐货架管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_002','音乐货架管理审批发布成功','音乐货架管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_003','音乐货架管理审批不通过成功','音乐货架管理审批不通过成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_004','音乐商品管理提交审批成功','音乐商品管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_005','音乐商品管理审批发布成功','音乐商品管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_MUSICCATEGORY_RESULT_006','音乐商品管理审批不通过成功','音乐商品管理审批不通过成功');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.089_SSMS','MM4.0.0.0.095_SSMS');

 
commit;