--------电子流提供给货架的程序包视图（v_dc_cm_device_resource）新增证书MD5字段----
alter  table T_A_CM_DEVICE_RESOURCE add cermd5 VARCHAR2(40);
comment on column T_A_CM_DEVICE_RESOURCE.cermd5
  is '证书MD5';
  
----------------------货架表t_r_category 添加两个字段 开始------------------------------------------------------
alter  table t_r_category add classify_status VARCHAR2(10) DEFAULT '1';
alter  table t_r_category add goods_status VARCHAR2(10) DEFAULT '1';
comment on column t_r_category.classify_status
  is '审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
  comment on column t_r_category.goods_status
  is '审批状态--  0 编辑；1 已发布；2 待审批;3 审批不通过';
  

----------------------货架表t_r_category 添加两个字段 结束--------------------------------------------------------

  ----------------------商品表t_r_reference 添加一个字段 开始------------------------------------------------------
alter  table t_r_reference add delflag number(2) DEFAULT 0;
comment on column t_r_reference.delflag
  is '删除标记位： 0-正常；1-审核通过删除，2-审批不通过删除，3-编辑删除，';
comment on column T_R_REFERENCE.VERIFY_STATUS
  is '审核状态-- 0 审核中；1 审核通过；2 审核不通过；3-编辑';
----------------------商品表t_r_reference 添加一个字段 结束-------------------------------

alter  table t_r_category add delpro_status number(2);
comment on column t_r_category.delpro_status
  is '删除之前状态-- 0-编辑；1-已发布;3-审批不通过';
  
  
--------------------------新增货架审批表T_R_CATEGORY_OPERATION 开始---------------------------------------------
create table T_R_CATEGORY_OPERATION
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
comment on table T_R_CATEGORY_OPERATION
  is '货架审批表';
-- Add comments to the columns 
comment on column T_R_CATEGORY_OPERATION.categoryid
  is '货架编码';
comment on column T_R_CATEGORY_OPERATION.operator
  is '操作人';
comment on column T_R_CATEGORY_OPERATION.operator_time
  is '操作时间';
comment on column T_R_CATEGORY_OPERATION.approval
  is '审批人';
comment on column T_R_CATEGORY_OPERATION.approval_time
  is '审批时间';
comment on column T_R_CATEGORY_OPERATION.operation
  is '处理方式: 1 货架分类管理; 2 货架商品管理';
--------------------------新增货架审批表T_R_CATEGORY_OPERATION 结束---------------------------------------------

--------------------创建货架审批表序列号 SEQ_APPROVAL_ID 开始------------------------------------------------------
create sequence SEQ_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建货架审批表序列号 SEQ_APPROVAL_ID 结束-------------------------------------------------------


---------------------创建运营操作日志表T_R_LOGGER 开始--------------------------------------------------------------
-- Create table
create table T_R_LOGGER
(
  id               VARCHAR2(30),
  operationtype    VARCHAR2(30),
  operator         VARCHAR2(20),
  operationobj     VARCHAR2(20),
  operationtime    DATE default sysdate,
  operationobjtype VARCHAR2(200)
);

-- Add comments to the table 
comment on table T_R_LOGGER
  is '运营操作日志';
-- Add comments to the columns 
comment on column T_R_LOGGER.operationtype
  is '操作类型';
comment on column T_R_LOGGER.operator
  is '操作人';
comment on column T_R_LOGGER.operationobj
  is '操作对象';
comment on column T_R_LOGGER.operationtime
  is '操作时间';
comment on column T_R_LOGGER.operationobjtype
  is '操作对象类型';
------------------------创建运营操作日志表T_R_LOGGER 开始----------------------------------------------------------------

  
--------------------创建运营操作日志表序列号 SEQ_LOGGER_ID 开始-------------------------------------------------------------
create sequence SEQ_LOGGER_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建运营操作日志表序列号 SEQ_LOGGER_ID 结束-------------------------------------------------------------

---------------------创建操作人邮箱地址表 T_EMAIL_ADDRESS 开始 -------------------------------------------------------------
-- Create table
create table T_EMAIL_ADDRESS
(
  id            VARCHAR2(30),
  operatoremail VARCHAR2(50),
  categoryid    VARCHAR2(20),
  approvalemail VARCHAR2(50),
  status        VARCHAR2(10)
);
-- Add comments to the table 
comment on table T_EMAIL_ADDRESS
  is '邮件地址';
-- Add comments to the columns 
comment on column T_EMAIL_ADDRESS.operatoremail
  is '编辑人员邮箱地址';
comment on column T_EMAIL_ADDRESS.categoryid
  is '货架编码';
comment on column T_EMAIL_ADDRESS.approvalemail
  is '审批人员邮箱地址';
comment on column T_EMAIL_ADDRESS.status
  is '处理方式,00：默认，10:MM货架，20：内容黑名单，30：基地游戏货架，40：基地阅读货架，50：基地音乐货架，60：基地视频货架，70：基地动漫货架';

---------------------创建操作人邮箱地址表 T_EMAIL_ADDRESS 结束 -------------------------------------------------------------

  
--------------------创建运营操作日志表序列号 SEQ_EMAIL_ID 开始-------------------------------------------------------------
create sequence SEQ_EMAIL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建运营操作日志表序列号 SEQ_EMAIL_ID 结束-------------------------------------------------------------


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CATE_RESULT_004','货架商品提交审批成功','货架商品提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CATE_RESULT_005','货架商品审批发布成功','货架商品审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CATE_RESULT_006','货架商品审批不通过成功','货架商品审批审批不通过成功');


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.075_SSMS','MM4.0.0.0.079_SSMS');

 
commit;