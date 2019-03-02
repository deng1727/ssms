

-- Drop columns 
alter table T_GAME_CATE_MAPPING drop column ALILID;

alter table T_R_GCONTENT drop column EXPPRICE;

drop VIEW V_GAME_CATE_MAPPING;

----更新 基地游戏 网游自动更新规则，屏蔽特价,测试环境ruleid相应修改
update t_caterule_cond t set t.wsql='onLinetype=''2'' '  where t.ruleid='210';

----更新 基地游戏 试玩自动更新规则，屏蔽特价,测试环境ruleid相应修改
update t_caterule_cond t set t.wsql='chargetime=''2'' '  where t.ruleid='169';

delete from t_caterule t where t.RULEID='216';
delete from t_caterule_cond t where t.RULEID='216';
delete from t_category_rule t where t.RULEID='216';

alter table T_CATEGORY_RULE drop column lastexcutecount;


-----回滚基地游戏单机精品自动更新规则
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('28731388', 168, to_date('17-01-2011 01:48:32', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-07-2010 22:45:17', 'dd-mm-yyyy hh24:mi:ss'));

insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (168, '基地游戏单机精品', 0, 0, 1, 0, 0);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (168, '', 11, 'chargetime=''1''', '', -1, 1, 1421);


-- Drop columns 
alter table T_GAME_CATE_MAPPING drop column CID;

delete T_CATEGORY_RULE t where t.ruleid in (211,212,213,214,215);

delete T_CATERULE_COND t where t.ruleid in (211,212,213,214,215);

delete T_CATERULE t where t.ruleid in (211,212,213,214,215);

delete DBVERSION where PATCHVERSION = 'MM1.0.3.007_SSMS' and LASTDBVERSION = 'MM1.0.3.003_SSMS';
commit;