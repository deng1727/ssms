--------------------------
--------------------------
delete from t_r_exportsql_ftp f where f.ftpid = '5';
delete from t_r_exportsql_group g where g.groupid = 15;
delete from t_r_exportsql e where e.id in (63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81);
---------------------------------------
---------------------------------------


delete from  dbversion  t where t.PATCHVERSION='MM4.0.0.0.035_SSMS'; 


commit;


------

