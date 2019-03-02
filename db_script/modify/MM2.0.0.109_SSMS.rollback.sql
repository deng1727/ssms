
  alter table t_r_gcontent drop column logo6;
  alter table cm_ct_appgame drop column logo6;
  alter table cm_ct_appsoftware drop column logo6;
  alter table cm_ct_apptheme drop column logo6;
  alter table cm_ct_appgame_tra drop column logo6;
  alter table cm_ct_appsoftware_tra drop column logo6;
  alter table cm_ct_apptheme_tra drop column logo6;
  
  alter table cm_ct_appgame drop column PKGEXTRACTICON;
  alter table cm_ct_appsoftware drop column PKGEXTRACTICON;
  alter table cm_ct_apptheme drop column PKGEXTRACTICON;
  alter table cm_ct_appgame_tra drop column PKGEXTRACTICON;
  alter table cm_ct_appsoftware_tra drop column PKGEXTRACTICON;
  alter table cm_ct_apptheme_tra drop column PKGEXTRACTICON;
  
  
  

  drop TABLE V_ARTICLE;
  drop TABLE V_ARTICLE_REFERENCE;
  delete from t_r_exportsql where id=29;

delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.109_SSMS' and LASTDBVERSION = 'MM2.0.0.0.105_SSMS';
commit;


-----------

alter table t_vo_program drop column SORTID;
alter table t_vo_program drop column isLink;
drop TABLE T_VO_RECOMMEND;
commit;