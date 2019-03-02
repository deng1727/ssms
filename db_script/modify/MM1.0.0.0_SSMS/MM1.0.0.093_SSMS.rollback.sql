--还原 提供给报表的视图 vr_category
create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
select categoryID,name,parentCategoryID,State,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');



alter table T_CATERULE drop column RANDOMFACTOR;

--删除版本----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.093_SSMS' and LASTDBVERSION = 'MM1.0.0.092_SSMS';
commit;