-- Drop columns 
alter table T_CONTENT_BACKLIST drop column CONTENTTYPE;

delete from t_category_carveout_rule;

delete DBVERSION where PATCHVERSION = 'MM1.0.2.095_SSMS' and LASTDBVERSION = 'MM1.0.2.090_SSMS';
commit;