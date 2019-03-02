-- rollback 
-- Drop columns 
alter table T_R_GCONTENT drop column MODAYORDERTIMES;


update t_caterule_cond t set t.osql = 'DOWNLOADTIMES desc  nulls last ,createdate desc,mobilePrice asc,name asc' where t.ruleid=5 and t.condtype=10;
update t_caterule_cond t set t.osql = 'weekordertimes desc' where t.ruleid=136 and t.condtype=10;
update t_caterule_cond t set t.osql = 'dayOrderTimes desc,createdate desc,mobilePrice asc,name asc' where t.ruleid=85 and t.condtype=10;
update t_caterule_cond t set t.osql = 'dayOrderTimes desc,createdate desc,mobilePrice asc,name asc' where t.ruleid=90 and t.condtype=10;
update t_caterule_cond t set t.osql = 'dayOrderTimes desc,createdate desc,mobilePrice asc,name asc' where t.ruleid=95 and t.condtype=10;
update t_caterule_cond t set t.osql = 'dayOrderTimes desc,createdate desc,mobilePrice asc,name asc' where t.ruleid=122 and t.condtype=10;
update t_caterule_cond t set t.osql = 'dayOrderTimes desc,createdate desc,mobilePrice asc,name asc' where t.ruleid=126 and t.condtype=10;
update t_caterule_cond t set t.osql = 'dayOrderTimes desc,createdate desc,mobilePrice asc,name asc' where t.ruleid=130 and t.condtype=10;
update t_caterule_cond t set t.osql = 'weekordertimes desc,createdate desc,name asc' where t.ruleid=205 and t.condtype=10;
update t_caterule_cond t set t.osql = 'weekordertimes desc,createdate desc,name asc' where t.ruleid=209 and t.condtype=10;

delete DBVERSION where PATCHVERSION = 'MM1.0.3.048_SSMS' and LASTDBVERSION = 'MM1.0.3.045_SSMS';
commit;