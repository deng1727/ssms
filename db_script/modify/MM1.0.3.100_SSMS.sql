insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.095_SSMS','MM1.0.3.100_SSMS');

create table t_category_single
(
  ID               VARCHAR2(30),
  NAME             VARCHAR2(100),
  CATEGORYID       VARCHAR2(20),
  PARENTCATEGORYID VARCHAR2(20),
  CATEGORYTYPE     VARCHAR2(2),
  userId VARCHAR2(100),
  type             VARCHAR2(500)
);

comment on column t_category_single.ID is '��������';
comment on column t_category_single.NAME is '��������';
comment on column t_category_single.CATEGORYID is '����ID';
comment on column t_category_single.PARENTCATEGORYID is '���ܸ�ID';
comment on column t_category_single.CATEGORYTYPE is '�������ͣ�0,ֻȡ��������Ϣ;1,ȡ��������Ϣ�Լ�һ���ӻ�����Ϣ;9,ȡ��������Ϣ�Լ��������������Ϣ';
comment on column t_category_single.userId is '�û�����룬��t_category_usergroup���е�code��Ӧ';
comment on column t_category_single.type is '�û�������,1�����ܷ��࣬2��������Ʒ';

create table t_category_usergroup
(
  code             VARCHAR2(100),
  NAME             VARCHAR2(500),
  type             VARCHAR2(500)
);
comment on column t_category_usergroup.code is '�û������';
comment on column t_category_usergroup.NAME is '�û�������';
comment on column t_category_usergroup.type is '�û�������,1�����ܷ��࣬2��������Ʒ';

insert into t_category_usergroup(code,name,type) values('all_category_class','���л��ܷ����û���','1');
insert into t_category_usergroup(code,name,type) values('all_category_content','���л�����Ʒ�û���','2');


insert into t_category_single(id,name,CATEGORYID,PARENTCATEGORYID,CATEGORYTYPE,userid,type) (select t0.id, t1.name,t1.categoryID,'0' PARENTCATEGORYID,'9' CATEGORYTYPE,'all_category_class' userid,'1' type from t_r_base t0, t_r_category t1 where t1.id = t0.id and t0.type like 'nt:category%' and t1.delFlag != '1' and t0.parentid = '701');
insert into t_category_single(id,name,CATEGORYID,PARENTCATEGORYID,CATEGORYTYPE,userid,type) (select t0.id, t1.name,t1.categoryID,'0' PARENTCATEGORYID,'9' CATEGORYTYPE,'all_category_content' userid,'2' type from t_r_base t0, t_r_category t1 where t1.id = t0.id and t0.type like 'nt:category%' and t1.delFlag != '1' and t0.parentid = '701');

-- Create table
create table t_r_SevenDaysCompared
(
  contentId VARCHAR2(30) not null,
  oldNumber number(10),
  newNumber number(10)
)
;
-- Add comments to the columns 
comment on column t_r_SevenDaysCompared.contentId
  is '�������룬��Ӧ������������룬��Ӧ��Ѷ��ý��ID����Ӧȫ���ĸ���ID��';
comment on column t_r_SevenDaysCompared.oldNumber
  is '��һ������������ͳ��';
comment on column t_r_SevenDaysCompared.newNumber
  is '��ǰ����������ͳ��';
-- Create/Recreate primary, unique and foreign key constraints 
alter table t_r_SevenDaysCompared
  add constraint p_SevenDays_pk primary key (CONTENTID);

-- Add/modify columns 
alter table T_R_GCONTENT add comparedNumber NUMBER(10) default 0;
-- Add comments to the columns 
comment on column T_R_GCONTENT.comparedNumber
  is '�����������仯��';


--------------------------�м�����������в���ִ��
create table t_r_sevenday
(
  contentId VARCHAR2(30) not null,
  orderById number(10)
)
;
-- Add comments to the columns 
comment on column t_r_sevenday.contentId
  is '�������룬��Ӧ������������룬��Ӧ��Ѷ��ý��ID����Ӧȫ���ĸ���ID��';
comment on column t_r_sevenday.orderById
  is '��ǰ����������ͳ��';

--------------------------�м�����������в���ִ��

commit;


