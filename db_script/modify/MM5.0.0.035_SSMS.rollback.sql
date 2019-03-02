
delete t_r_Exportsql where id = 112;
delete t_r_Exportsql where id = 113;

delete DBVERSION where PATCHVERSION = 'MM5.0.0.0.305_SSMS' and LASTDBVERSION = 'MM5.0.0.0.105_SSMS';

commit;