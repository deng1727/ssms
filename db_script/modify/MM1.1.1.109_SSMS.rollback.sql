
----回滚新音乐二期融合脚本
drop table  table t_mb_singer_new;

drop table  table T_MB_COLORURL_NEW;
drop table  table T_MB_SINGERTYPE_NEW;

-- Drop columns 
alter table T_MB_CATEGORY_NEW drop column album_upcase;
alter table T_MB_CATEGORY_NEW drop column pubtime;
alter table T_MB_CATEGORY_NEW drop column singersid;
alter table T_MB_CATEGORY_NEW drop column catetype;

-- Drop columns 
alter table T_MB_MUSIC_NEW drop column singersid;
alter table T_MB_MUSIC_NEW drop column pubtime;
alter table T_MB_MUSIC_NEW drop column onlinetype;
alter table T_MB_MUSIC_NEW drop column colortype;
alter table T_MB_MUSIC_NEW drop column ringtype;
alter table T_MB_MUSIC_NEW drop column songtype;

alter table t_cb_content drop column  baseType 



delete DBVERSION where PATCHVERSION = 'MM1.1.1.109_SSMS' and LASTDBVERSION = 'MM1.1.1.105_SSMS';
commit;