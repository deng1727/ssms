

---------------------------------------
---------------------------------------

alter table t_vo_collect drop column collectvid;

delete from  dbversion  t where t.PATCHVERSION='MM3.0.0.0.109_SSMS'; 


commit;


------
