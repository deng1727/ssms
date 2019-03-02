drop table T_CB_CHAPTER;
drop table T_CB_CONTENT;


drop view v_ssms_music_category;
drop view v_ssms_music_reference;

drop view v_ssms_book_category;
drop view v_ssms_book_reference;

drop view v_ssms_cat_category;
drop view v_ssms_cat_reference;

drop view v_ssms_video_category;
drop view v_ssms_video_reference;



delete DBVERSION where PATCHVERSION = 'MM1.1.1.065_SSMS' and LASTDBVERSION = 'MM1.1.1.059_SSMS';
commit;