


update T_R_EXPORTSQL t set t.exportbyauto = '1' where t.id >=21;



update t_sync_tactic_cy c set c.categoryid='348842473' where c.appcatename='��Ц';
update t_sync_tactic_cy c set c.categoryid='348842475' where c.appcatename='��Ƶ';
update t_sync_tactic_cy c set c.categoryid='348842476' where c.appcatename='ͨѶ';
update t_sync_tactic_cy c set c.categoryid='348842478' where c.appcatename='����';

update t_sync_tactic_cy c set c.categoryid='348842477' where c.appcatename='����';
update t_sync_tactic_cy c set c.categoryid='348842479' where c.appcatename='���';
update t_sync_tactic_cy c set c.categoryid='348842480' where c.appcatename='��Ϸ';
update t_sync_tactic_cy c set c.categoryid='348842474' where c.appcatename='����';


delete from t_category_carveout_rule r where r.name='201306��Χ';
delete from t_category_carveout_rule r where r.name='201307��Χ';
delete from t_category_carveout_rule r where r.name='201308��Χ';
delete from t_category_carveout_rule r where r.name='201309��Χ';
delete from t_category_carveout_rule r where r.name='201310��Χ';
delete from t_category_carveout_rule r where r.name='201311��Χ';
delete from t_category_carveout_rule r where r.name='201312��Χ';

delete from t_category_carveout_rule r where r.name='201306תMM';
delete from t_category_carveout_rule r where r.name='201307תMM';
delete from t_category_carveout_rule r where r.name='201308תMM';
delete from t_category_carveout_rule r where r.name='201309תMM';
delete from t_category_carveout_rule r where r.name='201310תMM';
delete from t_category_carveout_rule r where r.name='201311תMM';
delete from t_category_carveout_rule r where r.name='201312תMM';



delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.065_SSMS' and LASTDBVERSION = 'MM2.0.0.0.059_SSMS';

drop table T_M_TONEBOX;
drop table T_M_TONEBOX_SONG;
commit;