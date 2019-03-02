--------�������ṩ�����ܵĳ������ͼ��v_dc_cm_device_resource��������װ��MD5���ֶ�----
alter  table T_A_CM_DEVICE_RESOURCE add MD5CODE VARCHAR2(32);
comment on column T_A_CM_DEVICE_RESOURCE.MD5CODE
  is '��װ��MD5��';

---------------------------------�������ܱ�t_cb_category ��������ֶ� ��ʼ---------------------------------------------------------
 alter  table t_cb_category add anime_status VARCHAR2(10) DEFAULT '1';
alter  table t_cb_category add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_cb_category add delpro_status number(2) default 2;
comment on column t_cb_category.anime_status
  is '��������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
  comment on column t_cb_category.goods_status
  is '��Ʒ����״̬--  0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column t_cb_category.delpro_status
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
-----------------------------------�������ܱ�t_cb_category ��������ֶ� ����-------------------------------------------------------

-----------------------------------������������������T_CB_CATEGORY_OPERATION ��ʼ------------------------------------------------
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
  is '��������������';
-- Add comments to the columns 
comment on column T_CB_CATEGORY_OPERATION.categoryid
  is '���ܱ���';
comment on column T_CB_CATEGORY_OPERATION.operator
  is '������';
comment on column T_CB_CATEGORY_OPERATION.operator_time
  is '����ʱ��';
comment on column T_CB_CATEGORY_OPERATION.approval
  is '������';
comment on column T_CB_CATEGORY_OPERATION.approval_time
  is '����ʱ��';
comment on column T_CB_CATEGORY_OPERATION.operation
  is '����ʽ: 1 �������ܹ���; 2 ������Ʒ����';
-----------------------------------������������������T_CB_CATEGORY_OPERATION ����------------------------------------------------  

--------------------���������������������к� SEQ_CB_APPROVAL_ID ��ʼ----------------------------------------------------------------
create sequence SEQ_CB_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------���������������������к� SEQ_CB_APPROVAL_ID ����----------------------------------------------------------------

---------------------------------����������Ʒ��t_cb_reference ��������ֶ� ��ʼ-------------------------------------------------------
alter  table t_cb_reference add VERIFY_DATE DATE default sysdate;
alter  table t_cb_reference add VERIFY_STATUS number(2) default 1;
alter  table t_cb_reference add DELFLAG number(2) default 2;
comment on column t_cb_reference.VERIFY_DATE
  is '���ʱ��';
comment on column t_cb_reference.VERIFY_STATUS
  is '����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column t_cb_reference.DELFLAG
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
---------------------------------����������Ʒ��t_cb_reference ��������ֶ� ����-------------------------------------------------------

---------------------------------��Ƶ���ܱ�t_vo_category ��������ֶ� ��ʼ------------------------------------------------------------
alter  table t_vo_category add video_status VARCHAR2(10) DEFAULT '1';
alter  table t_vo_category add goods_status VARCHAR2(10) DEFAULT '1';
alter  table t_vo_category add delpro_status number(2) default 2;
comment on column t_vo_category.video_status
  is '��������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
  comment on column t_vo_category.goods_status
  is '��Ʒ����״̬--  0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column t_vo_category.delpro_status
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
-----------------------------------��Ƶ���ܱ�t_vo_category ��������ֶ� ����-----------------------------------------------------------

-----------------------------------������Ƶ����������T_VO_CATEGORY_OPERATION ��ʼ----------------------------------------------------
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
  is '��Ƶ����������';
-- Add comments to the columns 
comment on column T_VO_CATEGORY_OPERATION.categoryid
  is '���ܱ���';
comment on column T_VO_CATEGORY_OPERATION.operator
  is '������';
comment on column T_VO_CATEGORY_OPERATION.operator_time
  is '����ʱ��';
comment on column T_VO_CATEGORY_OPERATION.approval
  is '������';
comment on column T_VO_CATEGORY_OPERATION.approval_time
  is '����ʱ��';
comment on column T_VO_CATEGORY_OPERATION.operation
  is '����ʽ: 1 ��Ƶ���ܹ���; 2 ��Ƶ��Ʒ����';
-----------------------------------������Ƶ����������T_VO_CATEGORY_OPERATION ����----------------------------------------------------

--------------------������Ƶ�������������к� SEQ_VO_APPROVAL_ID ��ʼ--------------------------------------------------------------------
create sequence SEQ_VO_APPROVAL_ID 
start with 1000
increment by 1
nomaxvalue
minvalue 1
nocycle
cache 100;
---------------------������Ƶ�������������к� SEQ_VO_APPROVAL_ID ����--------------------------------------------------------------------

---------------------------------������Ƶ��Ʒ��t_vo_reference ��������ֶ� ��ʼ----------------------------------------------------------
alter  table t_vo_reference add VERIFY_DATE DATE default sysdate;
alter  table t_vo_reference add VERIFY_STATUS number(2) default 1;
alter  table t_vo_reference add DELFLAG number(2) default 2;
comment on column t_vo_reference.VERIFY_DATE
  is '���ʱ��';
comment on column t_vo_reference.VERIFY_STATUS
  is '����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column t_vo_reference.DELFLAG
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
---------------------------------������Ƶ��Ʒ��t_vo_reference ��������ֶ� ����-----------------------------------------------------------




insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_001','�������ܹ����ύ�����ɹ�','�������ܹ����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_002','�������ܹ������������ɹ�','�������ܹ������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_003','�������ܹ���������ͨ���ɹ�','�������ܹ���������ͨ���ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_004','������Ʒ�����ύ�����ɹ�','������Ʒ�����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_005','������Ʒ�������������ɹ�','������Ʒ�������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_ANIMALCATEGORY_RESULT_006','������Ʒ����������ͨ���ɹ�','������Ʒ����������ͨ���ɹ�');

insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_001','��Ƶ���ܹ����ύ�����ɹ�','��Ƶ���ܹ����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_002','��Ƶ���ܹ������������ɹ�','��Ƶ���ܹ������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_003','��Ƶ���ܹ���������ͨ���ɹ�','��Ƶ���ܹ���������ͨ���ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_004','��Ƶ��Ʒ�����ύ�����ɹ�','��Ƶ��Ʒ�����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_005','��Ƶ��Ʒ�������������ɹ�','��Ƶ��Ʒ�������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_VIDEOCATEGORY_RESULT_006','��Ƶ��Ʒ����������ͨ���ɹ�','��Ƶ��Ʒ����������ͨ���ɹ�');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.085_SSMS','MM4.0.0.0.089_SSMS');

 
commit;