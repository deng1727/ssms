-- Add/modify columns 
alter table V_CM_DEVICE_RESOURCE add VERSIONNAME VARCHAR2(400);
-- Add comments to the columns 
comment on column V_CM_DEVICE_RESOURCE.VERSIONNAME
  is 'VERSIONNAME';


-- Add/modify columns 
alter table T_A_CM_DEVICE_RESOURCE add VERSIONNAME VARCHAR2(400);
-- Add comments to the columns 
comment on column T_A_CM_DEVICE_RESOURCE.VERSIONNAME
  is 'VERSIONNAME';

-------�ֶ�update t_r_exportsql ��׿��������������� + 13 VERSIONNAME  14


-- Create table
create table t_a_auto_category
(
  id           number(5) not null,
  categoryId   VARCHAR2(20) not null,
  isnulltosync VARCHAR2(2) default 1 not null
)
;
-- Add comments to the columns 
comment on column t_a_auto_category.id
  is '��ԴΨһID';
comment on column t_a_auto_category.categoryId
  is '���ܱ���';
comment on column t_a_auto_category.isnulltosync
  is '������Ϊ��ʱ�Ƿ�֪ͨ�Է� 0���ǣ�1����';


create table t_r_reference_auto as select * from t_r_reference where 1=2;

alter table t_r_reference_auto
  add constraint PK_T_R_REFERENCE_auto primary key (ID);
create index IDX_T_R_REF_CATE_auto on t_r_reference_auto (CATEGORYID) ;
create unique index INDX_T_R_REF_GOODSID_auto on t_r_reference_auto (GOODSID) ;
create index IND_T_R_REFERENCE_auto on t_r_reference_auto (REFNODEID) ;


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.079_SSMS','MM2.0.0.0.085_SSMS');

commit;