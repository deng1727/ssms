


update T_R_EXPORTSQL t set t.exportbyauto = '1' where t.id >=21;



update t_sync_tactic_cy c set c.categoryid='348842473' where c.appcatename='搞笑';
update t_sync_tactic_cy c set c.categoryid='348842475' where c.appcatename='视频';
update t_sync_tactic_cy c set c.categoryid='348842476' where c.appcatename='通讯';
update t_sync_tactic_cy c set c.categoryid='348842478' where c.appcatename='音乐';

update t_sync_tactic_cy c set c.categoryid='348842477' where c.appcatename='新闻';
update t_sync_tactic_cy c set c.categoryid='348842479' where c.appcatename='软件';
update t_sync_tactic_cy c set c.categoryid='348842480' where c.appcatename='游戏';
update t_sync_tactic_cy c set c.categoryid='348842474' where c.appcatename='生活';


delete from t_category_carveout_rule r where r.name='201306入围';
delete from t_category_carveout_rule r where r.name='201307入围';
delete from t_category_carveout_rule r where r.name='201308入围';
delete from t_category_carveout_rule r where r.name='201309入围';
delete from t_category_carveout_rule r where r.name='201310入围';
delete from t_category_carveout_rule r where r.name='201311入围';
delete from t_category_carveout_rule r where r.name='201312入围';

delete from t_category_carveout_rule r where r.name='201306转MM';
delete from t_category_carveout_rule r where r.name='201307转MM';
delete from t_category_carveout_rule r where r.name='201308转MM';
delete from t_category_carveout_rule r where r.name='201309转MM';
delete from t_category_carveout_rule r where r.name='201310转MM';
delete from t_category_carveout_rule r where r.name='201311转MM';
delete from t_category_carveout_rule r where r.name='201312转MM';



delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.065_SSMS' and LASTDBVERSION = 'MM2.0.0.0.059_SSMS';

drop table T_M_TONEBOX;
drop table T_M_TONEBOX_SONG;
commit;