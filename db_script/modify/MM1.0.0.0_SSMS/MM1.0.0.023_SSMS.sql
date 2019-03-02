--������Ƶ���࣬��ͼ�����
insert into t_r_base(id,parentid,path,type) values('1014','701','{100}.{701}.{1014}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1014','������ƵƵ��','������ƵƵ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


insert into t_r_base(id,parentid,path,type) values('1015','701','{100}.{701}.{1015}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1015','�㶫ͼ��Ƶ��','�㶫ͼ��Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1016','701','{100}.{701}.{1016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1016','��������Ƶ��','��������Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2016','1016','{100}.{701}.{1016}.{2016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2016','�����������а�','�����������а�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1016') where c.id='2016';

---����ͼ��
insert into t_r_base(id,parentid,path,type) values('1017','701','{100}.{701}.{1017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1017','���ض���Ƶ��','���ض���Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2017','1017','{100}.{701}.{1017}.{2017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2017','�������','���ض������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2017';

insert into t_r_base(id,parentid,path,type) values('2018','1017','{100}.{701}.{1017}.{2018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2018','���ض������а�','���ض������а�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2018';

insert into t_r_base(id,parentid,path,type) values('2019','1017','{100}.{701}.{1017}.{2019}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2019','����ר��','���ض���ר��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2019';

-- Create table
create table t_bookcate_mapping
(
  bookCateid varchar2(25) not null,
  id         varchar2(30) not null,
  primary key (bookCateid)
)
;
-- Add comments to the columns 
comment on column t_bookcate_mapping.bookCateid
  is '����ͼ�����ID';
comment on column t_bookcate_mapping.id
  is '��Ӧ���ܷ���id';



-- Create table
create table t_book_commend
(
  ID      VARCHAR2(30) not null,
  YBOOKID VARCHAR2(30),
  JBOOKID VARCHAR2(30)
)
;
-- Add comments to the columns 
comment on column T_BOOK_COMMEND.ID
  is 'ͼ����ܷ���id';
comment on column T_BOOK_COMMEND.YBOOKID
  is '��Ӫ�Ƽ�ͼ��id';
comment on column T_BOOK_COMMEND.JBOOKID
  is '����ͼ��id';
-- Create/Recreate indexes 
create index t_book_commend_id on t_book_commend (ID);


-- Create table
create table T_BOOK_AUTHOR
(
  AUTHORID   VARCHAR2(25) not null,
  AUTHORNAME VARCHAR2(50),
  AUTHORDESC VARCHAR2(1024),
  primary key (AUTHORID)
);
-- Add comments to the columns 
comment on column T_BOOK_AUTHOR.AUTHORID
  is '����id';
comment on column T_BOOK_AUTHOR.AUTHORNAME
  is '��������';
comment on column T_BOOK_AUTHOR.AUTHORDESC
  is '��������';
create index T_BOOK_AUTHOR_NAME on T_BOOK_AUTHOR (AUTHORNAME);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.019_SSMS','MM1.0.0.023_SSMS');


-- Ϊ����ӦA8���ݣ��޸�t_r_gcontent ��
alter table T_R_GCONTENT modify NAME VARCHAR2(300);
alter table T_R_GCONTENT modify PROVIDER VARCHAR2(200);

--��Ҫ�������Ż���wwwpas��mopas��pcpas����t_r_gcontent�ﻯ��ͼ���´�����
--��ֱ������������û�ִ�����½ű�
drop materialized view t_r_gcontent;

---mopas ��ִ�д����ﻯ��ͼ�ű�
create materialized view t_r_gcontent as select * from s_r_gcontent;
--pcpas��wwwpas��ִ������Ĵ����ﻯ��ͼ�ű�
create materialized view t_r_gcontent as select * from s_r_gcontent v where ascii(substr(v.id,1,1))>47 and ascii(substr(v.id,1,1))<58;--wwwpas���˲������Ѷ����
--���������������ﻯ��ͼ��Ӱ��
alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;
alter materialized view v_advancecontent compile;

alter materialized view v_searchcontent compile;---mopas ����Ҫִ��

--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);



commit;