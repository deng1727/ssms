
-----------------ɾ�� t_vo_collect_show�е�һ��-------------------------

 alter table t_vo_collect_show drop column  parentnodeid;
 
delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.045_SSMS' and LASTDBVERSION = 'MM4.0.0.0.039_SSMS';


commit;
----------