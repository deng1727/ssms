drop table t_v_sprogram;
drop table t_v_dprogram;
drop table t_v_videoPic;
drop table T_V_LABLES;
drop table t_v_videoMedia;
drop table t_v_videosPropertys;
drop table t_v_category;
drop table t_v_reference;
drop table t_v_propkg;
drop table t_v_PRODUCT;
drop table T_V_LIVE;
drop table t_v_hotcontent;
drop table t_v_hotcatemap;
drop table t_v_type;
drop table t_v_PKGsales;
drop table t_v_videoPic_mid;
drop table T_V_LABLES_mid;
drop table t_v_videoMedia_mid;
drop table t_v_videosPropertys_mid;
drop table t_v_lasttime;

drop  sequence SEQ_T_V_SPROGRAM_ID;
drop  sequence SEQ_T_V_DPROGRAM_ID;
drop  sequence SEQ_T_V_VIDEOPIC_ID;
drop  sequence SEQ_T_V_LABLES_ID;
drop  sequence SEQ_T_V_VIDEOMEDIA_ID;
drop  sequence SEQ_T_V_VIDEOSPROP_ID;
drop  sequence SEQ_T_V_CATEGORY_ID;
drop  sequence SEQ_T_V_CATEGORY_CID;
drop  sequence SEQ_T_V_REFERENCE_ID;
drop  sequence SEQ_T_V_PROPKG_ID;
drop  sequence SEQ_T_V_PRODUCT_ID;
drop  sequence SEQ_T_V_LIVE_ID;
drop  sequence SEQ_T_V_HOTCONT_ID;
drop  sequence SEQ_T_V_HOTCATEMAP_ID;
drop  sequence SEQ_T_V_TYPE_ID;
drop  sequence SEQ_T_V_PKGSALES_ID;
drop  sequence SEQ_T_V_lasttime_ID;

drop  procedure p_v_pdetail_delete_insert;
drop  procedure p_v_program_update;

---------------------------------------
---------------------------------------

delete from  dbversion  t where t.PATCHVERSION='MM3.0.0.0.129_SSMS'; 


commit;


------
