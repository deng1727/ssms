
-- 回滚 创业大赛加大创业大赛作品编码
alter table T_R_GCONTENT modify PROGRAMID VARCHAR2(12);

drop table t_r_circle;

delete DBVERSION where PATCHVERSION = 'MM1.0.3.030_SSMS' and LASTDBVERSION = 'MM1.0.3.025_SSMS';
commit;