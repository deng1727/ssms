drop table T_CB_ADAPTER;
drop table T_CB_CATEGORY;
drop table T_CB_CHAPTER;
drop table T_CB_CONTENT;

drop table T_CB_CP;
drop table T_CB_DEVICEGROUPITEM;
drop table T_CB_MASTER;
drop table T_CB_REFERENCE;


drop table T_VO_VIDEO;
drop table T_VO_DEVICE;
drop table T_VO_CODERATE;
drop table T_VO_PROGRAM;
drop table T_VO_NODE;
drop table T_VO_LIVE;
drop table T_VO_RANK;
drop table T_VO_PRODUCT;
drop table T_VO_CATEGORY;
drop table T_VO_REFERENCE;
drop table T_VO_VIDEODETAIL;



alter table T_R_GCONTENT drop column logo5;


drop table T_RB_TYPE_NEW;
drop table T_RB_AUTHOR_NEW;
drop table T_RB_BOOK_NEW;
drop table T_RB_RECOMMEND_NEW;
drop table T_RB_BOOKCONTENT_NEW;
drop table T_RB_UPDATEBOOK_NEW;
drop table T_RB_BOOKBAG_NEW;
drop table T_RB_BOOKSCHEDULED_NEW;
drop table T_RB_CATEGORY_NEW;
drop table T_RB_CATE;
drop table T_RB_REFERENCE_NEW;
drop table T_RB_STATISTICS_NEW;

delete DBVERSION where PATCHVERSION = 'MM1.1.1.059_SSMS' and LASTDBVERSION = 'MM1.1.1.049_SSMS';
commit;