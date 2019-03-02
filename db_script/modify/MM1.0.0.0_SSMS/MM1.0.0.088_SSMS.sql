
-- Create table
create table T_CONTENT_COUNT
(
  CONTENTID      NVARCHAR2(30) not null,
  LATESTCOUNT    NUMBER default 0 not null,
  RECOMMENDCOUNT NUMBER default 0 not null,
  COUNTTIME      NVARCHAR2(30)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CONTENT_COUNT  add constraint PK_T_CONTENT_COUNT primary key (CONTENTID)  using index ;
comment on column  T_CONTENT_COUNT.CONTENTID  is 'Ӧ�ñ�ʶID';
comment on column  T_CONTENT_COUNT.LATESTCOUNT  is '�����ۺϵ÷�';
comment on column  T_CONTENT_COUNT.RECOMMENDCOUNT  is '�Ƽ��ۺϵ÷�';
comment on column  T_CONTENT_COUNT.COUNTTIME  is 'ͳ������';

--�����Զ����¹����,ʹ��Ӫ�Ƽ�15�����ܵľ�Ʒ����¹���ʧЧ
create table t_caterule_cond20100210 as select * from t_caterule_cond ;
update t_caterule_cond set cid = '-1' where cid is not null and ruleid  between 84 and 98;

----����
update t_caterule_cond set osql='c.LATESTCOUNT  desc nulls last' where condtype ='10' and   ruleid in ('88','98','93','3');
----�Ƽ�
update t_caterule_cond set osql='c.RECOMMENDCOUNT  desc nulls last' where condtype ='10' and   ruleid in ('86','91','96','4');

-- Create table
create table t_category_name_mapping
(
  apptype     number(1) not null,
  appcatename varchar2(200) not null,
  thirdcatename varchar2(200) not null
)
;
create index INDEX_T_CATEGORY_NAME_MAPPING on T_CATEGORY_NAME_MAPPING (apptype, appcatename);

-- Add comments to the columns 
comment on column t_category_name_mapping.apptype
  is 'Ӧ�����ͣ�1 �����2 ��Ϸ��3 ����';
comment on column t_category_name_mapping.appcatename
  is 'ppms ����Ӧ�õĶ����������ƣ���ֵ��Դ�� t_r_gcontent �� appcatename��ֵ';
comment on column t_category_name_mapping.thirdcatename
  is '���£���ѣ��Ƽ����Ǽ������ж�Ӧ����������ʾ���ơ�';

  insert into t_category_name_mapping t values(1,'��ý�����','��ý�����');
  insert into t_category_name_mapping t values(1,'ʵ�����','ʵ�����');
  insert into t_category_name_mapping t values(1,'ͨ�Ÿ���','ͨ�Ÿ���');
  insert into t_category_name_mapping t values(1,'�������','�������');
  insert into t_category_name_mapping t values(1,'ϵͳ����','ϵͳ����');


  insert into t_category_name_mapping t values(2,'���Իغ�','���Իغ�');
  insert into t_category_name_mapping t values(2,'������','������');
  insert into t_category_name_mapping t values(2,'��ɫ����','��ɫ����');
  insert into t_category_name_mapping t values(2,'ð��ģ��','ð��ģ��');
  insert into t_category_name_mapping t values(2,'����','����');
  insert into t_category_name_mapping t values(2,'��������','��������');
  insert into t_category_name_mapping t values(2,'�������','�������');
  insert into t_category_name_mapping t values(2,'��������','��������');
  insert into t_category_name_mapping t values(2,'����Ȥζ','����Ȥζ');


  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'�羰','�羰');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'��ͨ','��ͨ');
  insert into t_category_name_mapping t values(3,'�Ƽ�','�Ƽ�');
  insert into t_category_name_mapping t values(3,'��ͼ','��ͼ');

  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'����','����');
  insert into t_category_name_mapping t values(3,'Ӱ��','Ӱ��');
  insert into t_category_name_mapping t values(3,'��Ϸ','��Ϸ');

--������Ӫ��Ҫ�����������������
update t_caterule t set t.ruletype=6 where t.ruleid between 84 and 98;
comment on column T_CATERULE.RULETYPE
  is '�������� 0��ˢ�»�������Ʒ��1����������Ʒ����˳��
��5���������ͼ����Ӫ�Ƽ�ͼ�顣6:������Ӫ������������';



--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.080_SSMS','MM1.0.0.088_SSMS');
commit;