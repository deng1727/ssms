drop table T_R_GCONTENT_HIS;

delete from t_caterule t where t.RULEID='210';
delete from t_caterule_cond t where t.RULEID='210';
delete from t_category_rule t where t.RULEID='210';

delete DBVERSION where PATCHVERSION = 'MM1.0.3.003_SSMS' and LASTDBVERSION = 'MM1.0.3.001_SSMS';
commit;