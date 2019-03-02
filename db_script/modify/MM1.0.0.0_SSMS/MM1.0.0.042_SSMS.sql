--新增动漫分类
insert into t_r_base(id,parentid,path,type) values('1018','701','{100}.{701}.{1018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1018','动漫频道','动漫频道',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--新增操作货架错误提示信息，可不回滚，只需要插入以下两条记录即可
insert into t_resource (resourcekey,resourcevalue) values('RESOURCE_CATE_BO_CHECK_003','无法添加父货架不包含的关联门店,必须先添加父货架上该关联门店');
insert into t_resource (resourcekey,resourcevalue) values('RESOURCE_CATE_BO_CHECK_004','无法删除子货架包含的关联门店,必须先删除子货架上该关联门店');


----增加动漫支持平台表

-- Create table
create table T_COMIC_PLATFORM
(
  PLATFORMID VARCHAR2(20),
  PLATFORM   VARCHAR2(20)
);
comment on column T_COMIC_PLATFORM.PLATFORMID  is '支持平台ID,取值情况，100，101，102，200，300，400';
comment on column T_COMIC_PLATFORM.PLATFORM    is '支持平台描述取值情况S602nd, S603rd, S605th , WM , Kjava ,OMS';


-- 加长t_r_gcontent 表中的singer字段
alter table T_R_GCONTENT modify singer VARCHAR2(200);

--需要对三个门户，wwwpas，mopas，pcpas，的t_r_gcontent物化视图重新创建。

---wwwpas和pcpas 下执行对物化视图重新编译和刷新
alter materialized view t_r_gcontent compile;
exec dbms_mview.refresh(list=>'t_r_gcontent');

--mopas下执行以下脚本
drop materialized view t_r_gcontent;
create materialized view t_r_gcontent as select * from s_r_gcontent;
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);
alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;



---------------end-----------------
--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.040_SSMS','MM1.0.0.042_SSMS');


commit;