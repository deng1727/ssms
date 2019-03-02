


drop  function f_update_video_rnum;
drop view v_cm_content_apppay;


delete DBVERSION where PATCHVERSION = 'MM1.1.1.089_SSMS' and LASTDBVERSION = 'MM1.1.1.085_SSMS';
commit;