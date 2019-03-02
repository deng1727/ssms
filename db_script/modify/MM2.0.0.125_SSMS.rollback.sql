
-- Drop columns 
update t_r_exportsql t set t.EXPORTSQL='select CONTENTID,TITLE,LABEL,PUBTIME from V_ARTICLE   where type = 3  and status = 4' where t.id in ('29','32');


delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.125_SSMS' and LASTDBVERSION = 'MM2.0.0.0.119_SSMS';

commit;

