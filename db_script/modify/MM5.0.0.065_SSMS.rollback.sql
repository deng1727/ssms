CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin as
  v_nstatus     number;
  v_nrecod      number;
begin
 -------ִ����Ϸ�񵥼���洢����-----------------------
P_mt_rank_index_fin_game;
 -------ִ������񵥼���洢����-----------------------
P_mt_rank_index_fin_software;

v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','���Ⱥ�����񵥼���');

 --------------���Ȱ񵥸�Ԥ----------------
update mid_table t1
  set t1.hotlist
     = (select t1.hotlist*(decode(t2.type,'1',t2.param,1))
          from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='1')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='1');
 
 --------------���°񵥸�Ԥ----------------
update mid_table t1
  set t1.riselist
     = (select t1.riselist*(decode(t2.type,'2',t2.param,1))
     from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='2')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='2');

 -------����ϸ���ȸ�Ԥ-----------------------
  update mid_table t1
  set t1.hotlist
     = (select t2.M_HOTLIST+(decode(t2.type,'3',t2.param,0))
          from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='3')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='3');

 -------����ϸ���ȸ�Ԥ-----------------------
update mid_table t1
  set t1.riselist
     = (select t2.M_RISELIST+(decode(t2.type,'4',t2.param,0))
     from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='4')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='4');

  -------�����������ݸ�Ԥ-----------------------
update mid_table m1 set m1.hotlist =
 (select (v.hotlist + b.param) h from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v
where gc.contentid = b.contentid and gc.appcateid = v.appcateid and
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5')
 where  exists(select 1 from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v
where gc.contentid = b.contentid and gc.appcateid = v.appcateid and
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5');

 commit;

  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
  
exception
  when others then
    rollback;
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
    
end P_mt_rank_index_fin;

--------------�޸�P_mt_rank_index_fin�洢���̽���--------------------------
delete DBVERSION where PATCHVERSION = 'MM5.0.0.0.069_SSMS' and LASTDBVERSION = 'MM5.0.0.0.059_SSMS';
 commit;
