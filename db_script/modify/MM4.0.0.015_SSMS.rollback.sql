drop table t_wp_content;
drop table t_wp_category;
drop table t_wp_reference;
drop table t_wp_tactic;

drop  sequence SEQ_t_wp_content_ID;
drop  sequence SEQ_T_WP_category_ID;
drop  sequence SEQ_T_WP_reference_ID;
drop  sequence SEQ_T_WP_tactic_ID;


---------------------------------------
---------------------------------------

delete from  dbversion  t where t.PATCHVERSION='MM4.0.0.0.015_SSMS'; 


commit;


------
