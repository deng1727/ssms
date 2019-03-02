drop sequence SEQ_CATEGORY_VIDEO_BASEID;
delete from t_vo_category where id in('303','404');
commit;

drop table t_vo_video_tra  ;
drop table t_vo_node_tra  ;
drop table t_vo_program_tra ;
drop table t_vo_live_tra ;
drop table t_vo_product_tra ;
drop table t_vo_videodetail_tra ;
drop table t_vo_reference_tra ;
drop table t_vo_category_tra  ;
drop sequence SEQ_VIDEO_SYNC_ID;
commit;

drop index t_idx_vo_live;
drop index t_idx_vo_product;
drop index t_idx_vo_coderate;
drop index t_idx_vo_reference;
drop index t_idx_vo_program;
drop index t_idx_vo_videodetail;
drop index t_idx_vo_category;
drop index t_idx_vo_node;
drop index t_idx_vo_nodeext;
commit;

create index idx_vo_live on t_vo_live (starttime, endtime);
create index index_baseid on t_vo_category (baseid);
create unique index pk_nodeid_1 on t_vo_nodeext (nodeid);
commit;

delete DBVERSION where PATCHVERSION = 'MM1.1.1.079_SSMS' and LASTDBVERSION = 'MM1.1.1.075_SSMS';
commit;