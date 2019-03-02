

---------------------------------------
---------------------------------------

alter table T_GAME_CONTENT drop column LOGO6;

delete from  dbversion  t where t.PATCHVERSION='MM3.0.0.0.115_SSMS'; 


commit;


------
