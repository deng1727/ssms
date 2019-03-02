
---------------------------------------




drop view v_maxcate_hot_rise ;
drop view v_bid_inters ;
 
 
 --------------存储过程，下述脚本需要一起执行-------
CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin
as
begin
P_mt_rank_index_fin_game;
P_mt_rank_index_fin_software;

 update mid_table t1
  set t1.hotlist
     = (select t1.hotlist*(decode(t2.type,'1',t2.param,1))
          from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='1')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='1');

update mid_table t1
  set t1.riselist
     = (select t1.riselist*(decode(t2.type,'2',t2.param,1))
     from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='2')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='2');
 
 commit;
end;


------------------存储过程结束---------------

drop PROCEDURE P_VO_COLLECT_DELETE;

------------------视图v_d_reference开始---------------
create or replace view v_d_reference as
select t1."ID",
       t1."REFNODEID",
       t1."SORTID",
       t1."GOODSID",
       t1."CATEGORYID",
       t1."LOADDATE",
       t1."VARIATION",
       t2.id as parentid,
       substr(t1.goodsid, -12) as contentid
  from t_r_reference t1, t_r_category t2
 where t1.categoryid = t2.categoryid;

------------------视图v_d_reference结束---------------
delete from t_r_Exportsql where id = 82;
delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.039_SSMS' and LASTDBVERSION = 'MM4.0.0.0.035_SSMS';
commit;



------


