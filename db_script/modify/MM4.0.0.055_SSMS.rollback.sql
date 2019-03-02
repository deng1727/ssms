delete t_caterule_cond_base where base_id = 84;
delete t_caterule where rulename = '安卓全量应用';
delete t_category_rule where cid = '1225519609';
delete t_caterule_cond where basecondid = 84;
delete t_a_auto_category where id = 92;

delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.055_SSMS' and LASTDBVERSION = 'MM4.0.0.0.049_SSMS';

commit;