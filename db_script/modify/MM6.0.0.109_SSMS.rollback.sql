
alter table t_a_ppms_receive_change DROP COLUMN stats;

delete from T_R_EXPORTSQL_GROUP where GROUPID=17; 
delete from t_r_exportsql where ID=102; 



























commit;


