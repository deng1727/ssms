--------电子流提供给货架的程序包视图（v_dc_cm_device_resource）新增安装包MD5码字段----
alter  table T_A_CM_DEVICE_RESOURCE add MD5CODE VARCHAR2(32);
comment on column T_A_CM_DEVICE_RESOURCE.MD5CODE
  is '安装包MD5码';

---------------------------------动漫货架表t_cb_category 添加三个字段 开始---------------------------------------------------------
 alter  table t_cb_category add anime_status VARCHAR2(10) DEFAULT '1';
alter  table t_cb_category add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_cb_category add delpro_status number(2) default 2;
comment on column t_cb_category.anime_status
  is '货架审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
  comment on column t_cb_category.goods_status
  is '商品审批状态--  0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column t_cb_category.delpro_status
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
-----------------------------------动漫货架表t_cb_category 添加三个字段 结束-------------------------------------------------------

-----------------------------------创建动漫货架审批表T_CB_CATEGORY_OPERATION 开始------------------------------------------------
create table T_CB_CATEGORY_OPERATION
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
comment on table T_CB_CATEGORY_OPERATION
  is '动漫货架审批表';
-- Add comments to the columns 
comment on column T_CB_CATEGORY_OPERATION.categoryid
  is '货架编码';
comment on column T_CB_CATEGORY_OPERATION.operator
  is '操作人';
comment on column T_CB_CATEGORY_OPERATION.operator_time
  is '操作时间';
comment on column T_CB_CATEGORY_OPERATION.approval
  is '审批人';
comment on column T_CB_CATEGORY_OPERATION.approval_time
  is '审批时间';
comment on column T_CB_CATEGORY_OPERATION.operation
  is '处理方式: 1 动漫货架管理; 2 动漫商品管理';
-----------------------------------创建动漫货架审批表T_CB_CATEGORY_OPERATION 结束------------------------------------------------  

--------------------创建动漫货架审批表序列号 SEQ_CB_APPROVAL_ID 开始----------------------------------------------------------------
create sequence SEQ_CB_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建动漫货架审批表序列号 SEQ_CB_APPROVAL_ID 结束----------------------------------------------------------------

---------------------------------动漫货架商品表t_cb_reference 添加三个字段 开始-------------------------------------------------------
alter  table t_cb_reference add VERIFY_DATE DATE default sysdate;
alter  table t_cb_reference add VERIFY_STATUS number(2) default 1;
alter  table t_cb_reference add DELFLAG number(2) default 2;
comment on column t_cb_reference.VERIFY_DATE
  is '审核时间';
comment on column t_cb_reference.VERIFY_STATUS
  is '审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column t_cb_reference.DELFLAG
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
---------------------------------动漫货架商品表t_cb_reference 添加三个字段 结束-------------------------------------------------------

---------------------------------视频货架表t_vo_category 添加三个字段 开始------------------------------------------------------------
alter  table t_vo_category add video_status VARCHAR2(10) DEFAULT '1';
alter  table t_vo_category add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_vo_category add delpro_status number(2) default 2;
comment on column t_vo_category.video_status
  is '货架审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
  comment on column t_vo_category.goods_status
  is '商品审批状态--  0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column t_vo_category.delpro_status
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
-----------------------------------视频货架表t_vo_category 添加三个字段 结束-----------------------------------------------------------

-----------------------------------创建视频货架审批表T_VO_CATEGORY_OPERATION 开始----------------------------------------------------
create table T_VO_CATEGORY_OPERATION
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
comment on table T_VO_CATEGORY_OPERATION
  is '视频货架审批表';
-- Add comments to the columns 
comment on column T_VO_CATEGORY_OPERATION.categoryid
  is '货架编码';
comment on column T_VO_CATEGORY_OPERATION.operator
  is '操作人';
comment on column T_VO_CATEGORY_OPERATION.operator_time
  is '操作时间';
comment on column T_VO_CATEGORY_OPERATION.approval
  is '审批人';
comment on column T_VO_CATEGORY_OPERATION.approval_time
  is '审批时间';
comment on column T_VO_CATEGORY_OPERATION.operation
  is '处理方式: 1 视频货架管理; 2 视频商品管理';
-----------------------------------创建视频货架审批表T_VO_CATEGORY_OPERATION 结束----------------------------------------------------

--------------------创建视频货架审批表序列号 SEQ_VO_APPROVAL_ID 开始--------------------------------------------------------------------
create sequence SEQ_VO_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建视频货架审批表序列号 SEQ_VO_APPROVAL_ID 结束--------------------------------------------------------------------

---------------------------------动漫视频商品表t_vo_reference 添加三个字段 开始----------------------------------------------------------
alter  table t_vo_reference add VERIFY_DATE DATE default sysdate;
alter  table t_vo_reference add VERIFY_STATUS number(2) default 1;
alter  table t_vo_reference add DELFLAG number(2) default 2;
comment on column t_vo_reference.VERIFY_DATE
  is '审核时间';
comment on column t_vo_reference.VERIFY_STATUS
  is '审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
comment on column t_vo_reference.DELFLAG
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';
---------------------------------动漫视频商品表t_vo_reference 添加三个字段 结束-----------------------------------------------------------




insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_001','动漫货架管理提交审批成功','动漫货架管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_002','动漫货架管理审批发布成功','动漫货架管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_003','动漫货架管理审批不通过成功','动漫货架管理审批不通过成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_004','动漫商品管理提交审批成功','动漫商品管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_005','动漫商品管理审批发布成功','动漫商品管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_006','动漫商品管理审批不通过成功','动漫商品管理审批不通过成功');

insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_001','视频货架管理提交审批成功','视频货架管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_002','视频货架管理审批发布成功','视频货架管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_003','视频货架管理审批不通过成功','视频货架管理审批不通过成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_004','视频商品管理提交审批成功','视频商品管理提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_005','视频商品管理审批发布成功','视频商品管理审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_006','视频商品管理审批不通过成功','视频商品管理审批不通过成功');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.085_SSMS','MM4.0.0.0.089_SSMS');

 
commit;