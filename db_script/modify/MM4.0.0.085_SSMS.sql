
 alter  table t_Content_Backlist add blacklist_status VARCHAR2(10) DEFAULT 1;
comment on column t_Content_Backlist.blacklist_status
  is '审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过';
  
  alter  table t_Content_Backlist add delpro_status number(2) DEFAULT 2;
comment on column t_Content_Backlist.delpro_status
  is '删除之前状态-- 0-编辑；1-已发布;2-正常;3-审批不通过';

--------------------------新增内容黑名单审批表t_Content_Backlist_operation 开始---------------------------------------------
 create table t_Content_Backlist_operation
(
  id            VARCHAR2(30),
  operator      VARCHAR2(30),
  contentId     VARCHAR2(20),
  operator_time DATE,
  approval      VARCHAR2(30),
  approval_time DATE
);

-- Add comments to the table 
comment on table t_Content_Backlist_operation
  is '内容黑名单审批表';
-- Add comments to the columns 
comment on column t_Content_Backlist_operation.contentId
  is '应用内容Id';
comment on column t_Content_Backlist_operation.operator
  is '操作人';
comment on column t_Content_Backlist_operation.operator_time
  is '操作时间';
comment on column t_Content_Backlist_operation.approval
  is '审批人';
comment on column t_Content_Backlist_operation.approval_time
  is '审批时间';
--------------------------新增内容黑名单审批表t_Content_Backlist_operation 结束---------------------------------------------

  
--------------------创建货架审批表序列号 SQL_CONTENT_APPROVAL_ID 开始------------------------------------------------------
create sequence SQL_CONTENT_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------创建货架审批表序列号 SQL_CONTENT_APPROVAL_ID 结束-------------------------------------------------------

  
 insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BLACKLIST_RESULT_001','内容黑名单提交审批成功','内容黑名单提交审批成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BLACKLIST_RESULT_002','内容黑名单审批发布成功','内容黑名单审批发布成功');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_BLACKLIST_RESULT_003','内容黑名单审批不通过成功','内容黑名单审批不通过成功');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.079_SSMS','MM4.0.0.0.085_SSMS');

 
commit;