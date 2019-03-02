delete from t_r_base b where b.id in ('1014','1015','1016','2016','1017','2017','2018','2019');
delete from t_r_category b where b.id in ('1014','1015','1016','2016','1017','2017','2018','2019');

drop table t_book_commend;
drop table T_BOOK_AUTHOR;
drop table t_bookcate_mapping;
drop table T_GAME_CATE_MAPPING;

 回滚脚本
-- Add/modify columns 
alter table T_R_GCONTENT modify NAME VARCHAR2(100);
alter table T_R_GCONTENT modify PROVIDER VARCHAR2(60);
 --删除版本信息
delete DBVERSION where PATCHVERSION = 'MM1.0.0.023_SSMS' and LASTDBVERSION = 'MM1.0.0.019_SSMS';


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