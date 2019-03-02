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
  is '��������';
comment on column t_cm_category.categoryName
  is '��������';
comment on column t_cm_category.PcategoryId
  is '������������';
comment on column t_cm_category.sortid
  is '���������ֶ�';
comment on column t_cm_category.createtime
  is '���ഴ��ʱ��';
comment on column t_cm_category.updatetime
  is '�����޸�ʱ��';
comment on column t_cm_category.state
  is '����״̬ 0��ʾδ���� 1��ʾ������';
comment on column t_cm_category.categorybanner
  is '����banner';
comment on column t_cm_category.exporttime
  is '����ʱ��';
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
  is '�ز�ID';
comment on column T_CM_content.serverId
  is '�ز�ҵ��ID';
comment on column T_CM_content.contentid
  is '�ز�����ID';
comment on column T_CM_content.name
  is '�ز�����';
comment on column T_CM_content.DESCRIPTION
  is '�ز�����';
comment on column T_CM_content.keyValue
  is '�ز���������';
comment on column T_CM_content.type
  is '�ز����� 1 ͼƬ 2 ��Ƶ 3 ��Ƶ';
comment on column T_CM_content.sortid
  is '�ز�����';
comment on column T_CM_content.state
  is '�ز�״̬ 0��ʾ���á�1��ʾ����';
comment on column T_CM_content.iswork
  is '�ټӹ��ز� 0-	�����ټӹ� 1-	�����ټӹ�';
comment on column T_CM_content.picpath
  is '�زķ���·�� ͼƬ�б�չʾ·���ͺϳ�Ԥ��·����"|"�ָ�';
comment on column T_CM_content.showpath
  is '�ز�Ԥ��·��';
comment on column T_CM_content.property
  is '�ز����� ��ʽΪJSON��Ŀǰֻ��ͼƬʹ�á�';
comment on column T_CM_content.createtime
  is '����ʱ��';
comment on column T_CM_content.updatetime
  is '�޸�ʱ��';
comment on column T_CM_content.exporttime
  is '����ʱ��';
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
  is '��������Id';
comment on column t_cm_reference.categoryid
  is '��������';
comment on column t_cm_reference.resourceid
  is '��Դ����';
comment on column t_cm_reference.sortid
  is '����';
comment on column t_cm_reference.state
  is '״̬ 0-	��Ч 1-		ʧЧ';
comment on column t_cm_reference.createtime
  is '����ʱ��';
comment on column t_cm_reference.updatetime
  is '�޸�ʱ��';
comment on column t_cm_reference.exporttime
  is '����ʱ��';
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
  is '�Ƽ�id';
comment on column t_cm_recommend.name
  is '�Ƽ�����';
comment on column t_cm_recommend.DESCRIPTION
  is '�Ƽ�����';
comment on column t_cm_recommend.recBanner
  is '�Ƽ�Banner';
comment on column t_cm_recommend.type
  is '�Ƽ��������� 1��ʾͼƬ��2��ʾ����3��ʾ���4��ʾ��Ŀ';
comment on column t_cm_recommend.state
  is '�Ƽ�����״̬ 0��ʾ���á�1��ʾ����';
comment on column t_cm_recommend.sortid
  is '�����';
comment on column t_cm_recommend.createtime
  is '����ʱ��';
comment on column t_cm_recommend.updatetime
  is '�޸�ʱ��';
comment on column t_cm_recommend.Placement
  is '�Ƽ����ݷ���λ�� 1��ҳ��2�������ҳ��';
comment on column t_cm_recommend.urlAddress
  is '�ⲿ����URL';
comment on column t_cm_recommend.exporttime
  is '����ʱ��';
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
  is '��������Id';
comment on column T_CM_RECOMMEND_LINK.type
  is '�������� 1��ʾ�زġ�2��ʾ����3��ʾ���4��ʾ����';
comment on column T_CM_RECOMMEND_LINK.recommendID
  is '�Ƽ�����Id';
comment on column T_CM_RECOMMEND_LINK.OtherID
  is '�Ƽ��ⲿ����Id  �������زġ����󡢶���ͷ���ID';
comment on column T_CM_RECOMMEND_LINK.sortid
  is '����id';
comment on column T_CM_RECOMMEND_LINK.exporttime
  is '����ʱ��';
-- Create/Recreate indexes 
create index PK__CM_RECOMMEND_LINK_ID on T_CM_RECOMMEND_LINK (ID, TYPE, RECOMMENDID, OTHERID);


-------------------------------------
---��ʡȨ�޽ű�----------------------------
----��Ӧ�Ļ���IDΪ��������ID-----------------------------
------js ����------
----�Ѿ��������ֶ�����---
------sh �Ϻ�------

insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SH', '�Ϻ�ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SH', '�Ϻ�ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938313', '�Ϻ�ר��',t.categoryid  , '0', '9', 'ssms_category_content_SH', '2' from t_r_category t where t.id='703938313';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938313', '�Ϻ�ר��',t.categoryid  , '0', '9', 'ssms_category_class_SH', '1' from t_r_category t where t.id='703938313';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844257', '�Ϻ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SH', '2' from t_r_category t where t.id='704844257';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844257', '�Ϻ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SH', '1' from t_r_category t where t.id='704844257';

------fj ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_FJ', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_FJ', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938615', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_FJ', '2' from t_r_category t where t.id='703938615';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938615', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_FJ', '1' from t_r_category t where t.id='703938615';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844258', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_FJ', '2' from t_r_category t where t.id='704844258';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844258', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_FJ', '1' from t_r_category t where t.id='704844258';

------ah ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_AH', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_AH', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938616', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_AH', '2' from t_r_category t where t.id='703938616';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938616', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_AH', '1' from t_r_category t where t.id='703938616';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844259', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_AH', '2' from t_r_category t where t.id='704844259';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844259', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_AH', '1' from t_r_category t where t.id='704844259';


------jx ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_JX', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_JX', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938660', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_JX', '2' from t_r_category t where t.id='703938660';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938660', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_JX', '1' from t_r_category t where t.id='703938660';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844260', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_JX', '2' from t_r_category t where t.id='704844260';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844260', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_JX', '1' from t_r_category t where t.id='704844260';


------gd �㶫------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GD', '�㶫ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GD', '�㶫ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938769', '�㶫ר��',t.categoryid  , '0', '9', 'ssms_category_content_GD', '2' from t_r_category t where t.id='703938769';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938769', '�㶫ר��',t.categoryid  , '0', '9', 'ssms_category_class_GD', '1' from t_r_category t where t.id='703938769';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844261', '�㶫ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GD', '2' from t_r_category t where t.id='704844261';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844261', '�㶫ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GD', '1' from t_r_category t where t.id='704844261';


------gx ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GX', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GX', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938770', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_GX', '2' from t_r_category t where t.id='703938770';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938770', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_GX', '1' from t_r_category t where t.id='703938770';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844262', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GX', '2' from t_r_category t where t.id='704844262';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844262', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GX', '1' from t_r_category t where t.id='704844262';


------hi ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HI', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HI', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938826', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_HI', '2' from t_r_category t where t.id='703938826';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938826', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_HI', '1' from t_r_category t where t.id='703938826';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844263', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HI', '2' from t_r_category t where t.id='704844263';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844263', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HI', '1' from t_r_category t where t.id='704844263';


------hn ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HN', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HN', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938827', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_HN', '2' from t_r_category t where t.id='703938827';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938827', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_HN', '1' from t_r_category t where t.id='703938827';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844264', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HN', '2' from t_r_category t where t.id='704844264';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844264', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HN', '1' from t_r_category t where t.id='704844264';

------hb ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HB', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HB', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938828', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_HB', '2' from t_r_category t where t.id='703938828';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938828', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_HB', '1' from t_r_category t where t.id='703938828';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844265', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HB', '2' from t_r_category t where t.id='704844265';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844265', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HB', '1' from t_r_category t where t.id='704844265';


------sc �Ĵ�------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SC', '�Ĵ�ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SC', '�Ĵ�ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938842', '�Ĵ�ר��',t.categoryid  , '0', '9', 'ssms_category_content_SC', '2' from t_r_category t where t.id='703938842';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938842', '�Ĵ�ר��',t.categoryid  , '0', '9', 'ssms_category_class_SC', '1' from t_r_category t where t.id='703938842';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844266', '�Ĵ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SC', '2' from t_r_category t where t.id='704844266';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844266', '�Ĵ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SC', '1' from t_r_category t where t.id='704844266';


------yn ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_YN', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_YN', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938856', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_YN', '2' from t_r_category t where t.id='703938856';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938856', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_YN', '1' from t_r_category t where t.id='703938856';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844267', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_YN', '2' from t_r_category t where t.id='704844267';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844267', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_YN', '1' from t_r_category t where t.id='704844267';

------gz ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GZ', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GZ', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938882', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_GZ', '2' from t_r_category t where t.id='703938882';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938882', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_GZ', '1' from t_r_category t where t.id='703938882';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844268', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GZ', '2' from t_r_category t where t.id='704844268';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844268', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GZ', '1' from t_r_category t where t.id='704844268';

------cq ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_CQ', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_CQ', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938883', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_CQ', '2' from t_r_category t where t.id='703938883';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938883', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_CQ', '1' from t_r_category t where t.id='703938883';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844269', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_CQ', '2' from t_r_category t where t.id='704844269';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844269', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_CQ', '1' from t_r_category t where t.id='704844269';


------sn ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SN', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SN', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938939', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_SN', '2' from t_r_category t where t.id='703938939';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703938939', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_SN', '1' from t_r_category t where t.id='703938939';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844270', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SN', '2' from t_r_category t where t.id='704844270';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844270', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SN', '1' from t_r_category t where t.id='704844270';

------xz ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_XZ', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_XZ', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939050', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_XZ', '2' from t_r_category t where t.id='703939050';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939050', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_XZ', '1' from t_r_category t where t.id='703939050';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844271', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_XZ', '2' from t_r_category t where t.id='704844271';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844271', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_XZ', '1' from t_r_category t where t.id='704844271';


------bj ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_BJ', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_BJ', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '356996502', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_BJ', '2' from t_r_category t where t.id='356996502';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '356996502', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_BJ', '1' from t_r_category t where t.id='356996502';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844272', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_BJ', '2' from t_r_category t where t.id='704844272';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844272', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_BJ', '1' from t_r_category t where t.id='704844272';


------tj ���------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_TJ', '���ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_TJ', '���ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939051', '���ר��',t.categoryid  , '0', '9', 'ssms_category_content_TJ', '2' from t_r_category t where t.id='703939051';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939051', '���ר��',t.categoryid  , '0', '9', 'ssms_category_class_TJ', '1' from t_r_category t where t.id='703939051';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844273', '���ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_TJ', '2' from t_r_category t where t.id='704844273';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844273', '���ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_TJ', '1' from t_r_category t where t.id='704844273';


------nmg ���ɹ�------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_NMG', '���ɹ�ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_NMG', '���ɹ�ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939052', '���ɹ�ר��',t.categoryid  , '0', '9', 'ssms_category_content_NMG', '2' from t_r_category t where t.id='703939052';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939052', '���ɹ�ר��',t.categoryid  , '0', '9', 'ssms_category_class_NMG', '1' from t_r_category t where t.id='703939052';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844274', '���ɹ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_NMG', '2' from t_r_category t where t.id='704844274';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844274', '���ɹ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_NMG', '1' from t_r_category t where t.id='704844274';


------sd ɽ��------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SD', 'ɽ��ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SD', 'ɽ��ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939053', 'ɽ��ר��',t.categoryid  , '0', '9', 'ssms_category_content_SD', '2' from t_r_category t where t.id='703939053';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939053', 'ɽ��ר��',t.categoryid  , '0', '9', 'ssms_category_class_SD', '1' from t_r_category t where t.id='703939053';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844275', 'ɽ��ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SD', '2' from t_r_category t where t.id='704844275';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844275', 'ɽ��ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SD', '1' from t_r_category t where t.id='704844275';


------ln ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_LN', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_LN', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939054', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_LN', '2' from t_r_category t where t.id='703939054';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939054', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_LN', '1' from t_r_category t where t.id='703939054';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844276', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_LN', '2' from t_r_category t where t.id='704844276';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844276', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_LN', '1' from t_r_category t where t.id='704844276';



------jn ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_JN', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_JN', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939055', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_JN', '2' from t_r_category t where t.id='703939055';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939055', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_JN', '1' from t_r_category t where t.id='703939055';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844277', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_JN', '2' from t_r_category t where t.id='704844277';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844277', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_JN', '1' from t_r_category t where t.id='704844277';


------hlj ������------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HLJ', '������ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HLJ', '������ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939056', '������ר��',t.categoryid  , '0', '9', 'ssms_category_content_HLJ', '2' from t_r_category t where t.id='703939056';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939056', '������ר��',t.categoryid  , '0', '9', 'ssms_category_class_HLJ', '1' from t_r_category t where t.id='703939056';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844278', '������ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HLJ', '2' from t_r_category t where t.id='704844278';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844278', '������ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HLJ', '1' from t_r_category t where t.id='704844278';


------ha ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HA', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HA', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939057', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_HA', '2' from t_r_category t where t.id='703939057';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939057', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_HA', '1' from t_r_category t where t.id='703939057';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844279', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HA', '2' from t_r_category t where t.id='704844279';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844279', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HA', '1' from t_r_category t where t.id='704844279';

------he �ӱ�------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_HE', '�ӱ�ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_HE', '�ӱ�ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939058', '�ӱ�ר��',t.categoryid  , '0', '9', 'ssms_category_content_HE', '2' from t_r_category t where t.id='703939058';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939058', '�ӱ�ר��',t.categoryid  , '0', '9', 'ssms_category_class_HE', '1' from t_r_category t where t.id='703939058';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844280', '�ӱ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_HE', '2' from t_r_category t where t.id='704844280';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844280', '�ӱ�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_HE', '1' from t_r_category t where t.id='704844280';

------sx ɽ��------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_SX', 'ɽ��ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_SX', 'ɽ��ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939059', 'ɽ��ר��',t.categoryid  , '0', '9', 'ssms_category_content_SX', '2' from t_r_category t where t.id='703939059';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939059', 'ɽ��ר��',t.categoryid  , '0', '9', 'ssms_category_class_SX', '1' from t_r_category t where t.id='703939059';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844281', 'ɽ��ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_SX', '2' from t_r_category t where t.id='704844281';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844281', 'ɽ��ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_SX', '1' from t_r_category t where t.id='704844281';

------nx ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_NX', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_NX', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939060', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_NX', '2' from t_r_category t where t.id='703939060';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939060', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_NX', '1' from t_r_category t where t.id='703939060';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844282', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_NX', '2' from t_r_category t where t.id='704844282';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844282', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_NX', '1' from t_r_category t where t.id='704844282';

------gs ����------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_GS', '����ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_GS', '����ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939061', '����ר��',t.categoryid  , '0', '9', 'ssms_category_content_GS', '2' from t_r_category t where t.id='703939061';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939061', '����ר��',t.categoryid  , '0', '9', 'ssms_category_class_GS', '1' from t_r_category t where t.id='703939061';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844283', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_GS', '2' from t_r_category t where t.id='704844283';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844283', '����ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_GS', '1' from t_r_category t where t.id='704844283';


------qh �ຣ------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_QH', '�ຣר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_QH', '�ຣר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939062', '�ຣר��',t.categoryid  , '0', '9', 'ssms_category_content_QH', '2' from t_r_category t where t.id='703939062';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939062', '�ຣר��',t.categoryid  , '0', '9', 'ssms_category_class_QH', '1' from t_r_category t where t.id='703939062';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844284', '�ຣר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_QH', '2' from t_r_category t where t.id='704844284';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844284', '�ຣר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_QH', '1' from t_r_category t where t.id='704844284';


------xj �½�------
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_content_XJ', '�½�ר����Ʒ����', '2');
insert into t_category_usergroup (CODE, NAME, TYPE)values ('ssms_category_class_XJ', '�½�ר�����ܹ���', '1');

insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939063', '�½�ר��',t.categoryid  , '0', '9', 'ssms_category_content_XJ', '2' from t_r_category t where t.id='703939063';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '703939063', '�½�ר��',t.categoryid  , '0', '9', 'ssms_category_class_XJ', '1' from t_r_category t where t.id='703939063';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844285', '�½�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_content_XJ', '2' from t_r_category t where t.id='704844285';
insert into t_category_single (ID, NAME, CATEGORYID, PARENTCATEGORYID, CATEGORYTYPE, USERID, TYPE)select '704844285', '�½�ר��(WAP)',t.categoryid  , '0', '9', 'ssms_category_class_XJ', '1' from t_r_category t where t.id='704844285';


insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (15, 'select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, '''' as SYNCFLAG from t_r_gcontent c,(select * from t_key_resource t where t.keyid=''435'') k, v_service v where c.contentid = k.tid(+) and c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 UNION ALL select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, '''' as SYNCFLAG from t_r_gcontent c,(select * from t_key_resource t where t.keyid=''435'') k, v_service v, (select distinct tt.contentid, tt.tbtype from V_CM_CONTENT_TB tt ) tb where c.contentid = k.tid(+) and c.icpservid = v.icpservid and c.contentid = tb.contentid and tb.tbtype=''2'' order by contentid', '�ƹ�����-����ȫ������', '1', 50000, ',', null, 29, 'i_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (16, 'select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, ''A'' as SYNCFLAG from t_r_gcontent c, (select * from t_key_resource t where t.keyid = ''435'') k, v_service v where c.contentid = k.tid(+) and c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and c.plupddate is not null and length(c.plupddate) = 19 UNION ALL select c.contentid, c.name, c.catename, c.appcatename, c.appcateid, c.icpservid, c.introduction, c.keywords, c.createdate, c.marketdate, c.lupddate, c.wwwpropapicture1, c.wwwpropapicture2, c.wwwpropapicture3, c.picture1, c.picture2, c.picture3, c.picture4, c.chargetime, c.servattr, c.subtype, c.pvcid, c.cityid, c.plupddate, c.funcdesc, k.value, c.spname, v.mobileprice, ''A'' as SYNCFLAG from t_r_gcontent c, (select * from t_key_resource t where t.keyid = ''435'') k, v_service v, (select distinct tt.contentid, tt.tbtype from V_CM_CONTENT_TB tt ) tb where c.contentid = k.tid(+) and c.icpservid = v.icpservid and c.contentid = tb.contentid and tb.tbtype = ''2'' and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and c.plupddate is not null and length(c.plupddate) = 19 UNION ALL select distinct t.contentid, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', 0, ''D'' from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''16'') and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by contentid', '�ƹ�����-������������', '1', 50000, ',', null, 29, 'a_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (17, 'select t.device_id,t.device_name,t.device_desc,t.os_id,t.os_detail,t.brand_id,b.brand_name,u.device_ua,upper(u.device_os_ua) device_os_us,u.device_ua_header from T_device t,t_device_ua u,t_device_brand b where u.device_id = t.device_id and b.brand_id=t.brand_id order by t.device_id', '�ƹ�����-��������', '1', 50000, ',', null, 10, 'i_DVC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (18, 'select t.pid,t.device_id,t.device_name,t.contentid,t.contentname,t.resourceid,t.id,t.absolutepath,t.url,t.programsize,t.createdate,t.prosubmitdate,t.match, v.package, v.version,t.permission,t.iscdn from T_A_CM_DEVICE_RESOURCE t, (select distinct p.contentid, p.version, p.package from V_CM_CONTENT_PACKAGE p) v where t.contentid = v.contentid(+) and t.version = v.version(+) order by t.pid,t.device_id', '�ƹ�����-��׿��������', '1', 50000, ',', null, 17, 'i_TACM_DR_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (19, 'select c.contentid, d.order7day_count, d.add_order_count, c.averagemark from t_r_gcontent c, v_serven_sort d ,v_service v where c.icpservid = v.icpservid and v.mobileprice = 0 and c.chargetime = 1 and c.contentid = d.content_id(+) and d.os_id = ''9'' UNION ALL select c.contentid, d.order7day_count, d.add_order_count, c.averagemark from t_r_gcontent c, v_serven_sort d ,V_CM_CONTENT_TB tb where c.contentid = tb.contentid and tb.tbtype = ''2'' and c.contentid = d.content_id(+) and d.os_id = ''9'' order by contentid', '�ƹ�����-��Ӫ����', '1', 50000, ',', null, 4, 'i_DRC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (20, 'select v.CONTENTID,v.PRODUCTNAME,v.FEE,v.CHARGETYPE,v.TBTYPE from V_CM_CONTENT_TB v order by v.CONTENTID', '�ƹ�����-PACKAGE����', '1', 50000, ',', null, 5, 'i_CTB_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_spread', 'UTF-8', '2', '1', null);

commit;



-----------------------------------------------
---�����ű�Ҫ��portalwww@MMportal ������ִ��------
-----------------------------------------------

declare
 seq_id  number;
 begin
 	
 	------js ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���չ���Ա', 'js_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_JS');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_JS');


------sh �Ϻ�------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�Ϻ�����Ա', 'sh_manager', 1, '�Ϻ�ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SH');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SH');


------fj ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '��������Ա', 'fj_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_FJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_FJ');


------ah ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���չ���Ա', 'ah_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_AH');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_AH');

------jx ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '��������Ա', 'jx_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_JX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_JX');

------gd �㶫------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�㶫����Ա', 'gd_manager', 1, '�㶫ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GD');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GD');

------gx ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '��������Ա', 'gx_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GX');

------hi ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���Ϲ���Ա', 'hi_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HI');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HI');


------hn ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���Ϲ���Ա', 'hn_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HN');


------hb ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '��������Ա', 'hb_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HB');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HB');


------sc �Ĵ�------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�Ĵ�����Ա', 'sc_manager', 1, '�Ĵ�ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SC');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SC');


------yn ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���Ϲ���Ա', 'yn_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_YN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_YN');


------gz ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���ݹ���Ա', 'gz_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GZ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GZ');

------cq ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�������Ա', 'cq_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_CQ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_CQ');


------sn ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '��������Ա', 'sn_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SN');


------xz ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���ع���Ա', 'xz_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_XZ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_XZ');


------bj ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '��������Ա', 'bj_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_BJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_BJ');

------tj ���------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '������Ա', 'tj_manager', 1, '���ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_TJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_TJ');

------nmg ���ɹ�------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���ɹŹ���Ա', 'nmg_manager', 1, '���ɹ�ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_NMG');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_NMG');

------sd ɽ��------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, 'ɽ������Ա', 'sd_manager', 1, 'ɽ��ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SD');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SD');

------ln ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '��������Ա', 'ln_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_LN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_LN');

------jn ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���ֹ���Ա', 'jn_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_JN');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_JN');

------hlj ������------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '����������Ա', 'hlj_manager', 1, '������ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HLJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HLJ');

------ha ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���Ϲ���Ա', 'ha_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HA');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HA');

------he �ӱ�------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�ӱ�����Ա', 'he_manager', 1, '�ӱ�ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_HE');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_HE');

------sx ɽ��------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, 'ɽ������Ա', 'sx_manager', 1, 'ɽ��ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_SX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_SX');


------nx ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '���Ĺ���Ա', 'nx_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_NX');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_NX');


------gs ����------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�������Ա', 'gs_manager', 1, '����ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_GS');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_GS');

------qh �ຣ------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�ຣ����Ա', 'qh_manager', 1, '�ຣר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_QH');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_QH');


------xj �½�------
select seq_mp_t_role_id.nextval into seq_id from dual;

insert into mp_t_role (ROLE_ID, ROLE_NAME, ROLE_KEY, ROLE_TYPE, ROLE_DESC, CREATE_USER, CREATE_DATE, LAST_UPDATE_DATE)
values ('0000'||seq_id, '�½�����Ա', 'xj_manager', 1, '�½�ר�����ܹ���Ա', 'admin', sysdate, sysdate);

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_content_XJ');

insert into mp_t_role_operation (ROLE_ID, RESOURCE_KEY, OPERATION_KEY)
values ('0000'||seq_id, 'ssms_cgy', 'ssms_category_class_XJ');

end;

commit;





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.047_SSMS','MM2.0.0.0.059_SSMS');


commit;