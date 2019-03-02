----回滚动漫分类
delete from t_r_base b where b.id in('1018');
delete from t_r_category b where b.id in ('1018');

----回滚动漫支持平台表

-- drop table
drop table T_COMIC_PLATFORM;

-- 回滚t_r_gcontent 表中的singer字段
alter table T_R_GCONTENT modify singer VARCHAR2(50);

--需要对三个门户，wwwpas，mopas，pcpas，的t_r_gcontent物化视图重新创建。

---wwwpas和pcpas 下执行对物化视图重新编译和刷新
alter materialized view t_r_gcontent compile;
exec dbms_mview.refresh(list=>'t_r_gcontent');

--mopas下执行以下脚本
drop materialized view t_r_gcontent;
create materialized view t_r_gcontent as select * from s_r_gcontent;
alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;


--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);

---------------end-----------------
--回滚版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.042_SSMS' and LASTDBVERSION = 'MM1.0.0.040_SSMS';

commit;