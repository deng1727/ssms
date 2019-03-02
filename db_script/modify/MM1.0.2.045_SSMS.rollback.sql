--删除新增table和序列
drop table T_SYN_RESULT;
drop sequence SEQ_T_SYN_RESULT_ID;

declare 
druleid number(8);
begin
--删除本地化应用软件更新规则
select t.ruleid into druleid from t_category_rule t where t.cid='28731389';
delete from t_caterule t where t.ruleid=druleid;
delete from t_category_rule r where r.ruleid=druleid;
delete from t_caterule_cond c where c.ruleid=druleid;
--删除基地游戏精品单机更新规则
select t.ruleid into druleid from t_category_rule t where t.cid='28731388';
delete from t_caterule t where t.ruleid=druleid;
delete from t_category_rule r where r.ruleid=druleid;
delete from t_caterule_cond c where c.ruleid=druleid;
--删除基地游戏试玩专区更新规则
select t.ruleid into druleid from t_category_rule t where t.cid='21665492';
delete from t_caterule t where t.ruleid=druleid;
delete from t_category_rule r where r.ruleid=druleid;
delete from t_caterule_cond c where c.ruleid=druleid;
commit;
end;
/

drop table T_CATEGORY_TRAIN;
drop VIEW V_MO_HOTSER; 
drop VIEW V_WWW_HOTSER;
drop VIEW V_WAP_HOTSER;


delete DBVERSION where PATCHVERSION = 'MM1.0.2.045_SSMS' and LASTDBVERSION = 'MM1.0.2.040_SSMS';
commit;