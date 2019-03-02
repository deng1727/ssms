--新增视频分类，和图书分类
insert into t_r_base(id,parentid,path,type) values('1014','701','{100}.{701}.{1014}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1014','基地视频频道','基地视频频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


insert into t_r_base(id,parentid,path,type) values('1015','701','{100}.{701}.{1015}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1015','广东图书频道','广东图书频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1016','701','{100}.{701}.{1016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1016','基地音乐频道','基地音乐频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2016','1016','{100}.{701}.{1016}.{2016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2016','基地音乐排行榜','基地音乐排行榜',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1016') where c.id='2016';

---基地图书
insert into t_r_base(id,parentid,path,type) values('1017','701','{100}.{701}.{1017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1017','基地读书频道','基地读书频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2017','1017','{100}.{701}.{1017}.{2017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2017','读书分类','基地读书分类',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2017';

insert into t_r_base(id,parentid,path,type) values('2018','1017','{100}.{701}.{1017}.{2018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2018','基地读书排行榜','基地读书排行榜',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2018';

insert into t_r_base(id,parentid,path,type) values('2019','1017','{100}.{701}.{1017}.{2019}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2019','读书专区','基地读书专区',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
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
  is '基地图书分类ID';
comment on column t_bookcate_mapping.id
  is '对应货架分类id';



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
  is '图书货架分类id';
comment on column T_BOOK_COMMEND.YBOOKID
  is '运营推荐图书id';
comment on column T_BOOK_COMMEND.JBOOKID
  is '基地图书id';
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
  is '作者id';
comment on column T_BOOK_AUTHOR.AUTHORNAME
  is '作者姓名';
comment on column T_BOOK_AUTHOR.AUTHORDESC
  is '作者描述';
create index T_BOOK_AUTHOR_NAME on T_BOOK_AUTHOR (AUTHORNAME);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.019_SSMS','MM1.0.0.023_SSMS');


-- 为了适应A8数据，修改t_r_gcontent 表
alter table T_R_GCONTENT modify NAME VARCHAR2(300);
alter table T_R_GCONTENT modify PROVIDER VARCHAR2(200);

--需要对三个门户，wwwpas，mopas，pcpas，的t_r_gcontent物化视图重新创建。
--请分别以上面三个用户执行以下脚本
drop materialized view t_r_gcontent;

---mopas 下执行创建物化视图脚本
create materialized view t_r_gcontent as select * from s_r_gcontent;
--pcpas和wwwpas下执行下面的创建物化视图脚本
create materialized view t_r_gcontent as select * from s_r_gcontent v where ascii(substr(v.id,1,1))>47 and ascii(substr(v.id,1,1))<58;--wwwpas过滤彩铃和资讯内容
--更改其他对其他物化视图的影响
alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;
alter materialized view v_advancecontent compile;

alter materialized view v_searchcontent compile;---mopas 不需要执行

--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);



commit;