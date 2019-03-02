--删除权限
delete form t_right where rightid='2_0601_DISSERTATION';
delete form t_right where rightid='0_0601_DISS_ADMIN';
--删除角色权限
delete form t_roleright where rightid='2_0601_DISSERTATION';
delete form t_roleright where rightid='0_0601_DISS_ADMIN';

--删除专题表
--Drop table 
drop table T_DISSERTATION;

--drop sequence
drop sequence SEQ_T_DISSERTATION_ID;

delete DBVERSION where PATCHVERSION = 'MM1.0.3.025_SSMS' and LASTDBVERSION = 'MM1.0.3.020_SSMS';
commit;