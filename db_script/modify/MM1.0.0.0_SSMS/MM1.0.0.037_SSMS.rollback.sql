--�ع�032�汾����
insert into T_CATEGORY_RULE select * from t_category_rule_20090825_bak;
drop table t_category_rule_20090825_bak;

 --ɾ���汾��Ϣ
delete DBVERSION where PATCHVERSION = 'MM1.0.0.037_SSMS' and LASTDBVERSION = 'MM1.0.0.032_SSMS';
commit;