




------- 飙升，最热指标干预脚本 ---------------
create or replace view v_maxcate_hot_rise as
select max(t.hotlist) as m_hotlist,max(t.riselist) as m_riselist,g.appcateid from mid_table t,t_r_gcontent g where t.appid=g.contentid group by g.appcateid;

create or replace view v_bid_inters as
 select r.*,b.* from v_maxcate_hot_rise r,t_bid_inter b,t_r_gcontent g where b.contentid=g.contentid and r.appcateid=g.appcateid;
 
 
 
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
 
 -------细粒度干预-----------------------
  update mid_table t1
  set t1.hotlist
     = (select t2.M_HOTLIST+(decode(t2.type,'3',t2.param,0))
          from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='3')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='3');

update mid_table t1
  set t1.riselist
     = (select t2.M_RISELIST+(decode(t2.type,'4',t2.param,0))
     from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='4')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='4');
 commit;
end;

------------------存储过程结束---------------

----------------删除内容集节目为空的内容集和内容集节点    存储过程  开始--------------
CREATE OR REPLACE PROCEDURE P_VO_COLLECT_DELETE as
  v_nstatus number;
  v_nrecod  number;
  
cursor s_cur is
        select n.nodeid from t_vo_collect_node n,
        (select c.collectid from t_vo_collect c where 
          not exists (select 1 from t_vo_program p where p.nodeid = c.collectid) 
        ) a where n.nodeid = a.collectid;
begin
  v_nstatus := pg_log_manage.f_startlog('P_VO_COLLECT_DELETE',
                                        '删除内容集节目为空的内容集和内容集节点');
 -----递归删除内容集节目为空的内容集节点及子节点----
 for r in s_cur loop
   
   delete from t_vo_collect_node where nodeid in 
    (select nodeid from t_vo_collect_node 
       start with nodeid = r.nodeid 
       connect by prior nodeid = parentnodeid);
       
 end loop;
 ------删除内容集节目为空的内容集-----
 delete from t_vo_collect c where not exists (select 1 from t_vo_program p where p.nodeid = c.collectid);
 commit;
 v_nrecod:=SQL%ROWCOUNT;
 v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
 exception
 when others then
 rollback;
 -----如果失败，将执行情况写入日志-----
    v_nstatus := pg_log_manage.f_errorlog;
end;

----------------删除内容集节目为空的内容集和内容集节点    存储过程  结束--------------

------视图v_d_reference开始-----
create or replace view v_d_reference as
select t1."ID",
       t1."REFNODEID",
       t1."SORTID",
       t1."GOODSID",
       t1."CATEGORYID",
       t1."LOADDATE",
       t1."VARIATION",
       t2.id as parentid,
       substr(t1.goodsid, -12) as contentid,
       t3.name as gname
  from t_r_reference t1, t_r_category t2,t_r_gcontent t3
 where t1.categoryid = t2.categoryid and t1.refnodeid = t3.id;
 
------视图v_d_reference结束-----

 
insert into t_r_Exportsql
  (id,exportsql,exportname,exporttype,exportpagenum,exportTypeOther,exportLine,fileName,filePath,ENCODER,exportByAuto,exportByUser,groupId)
values
  (82,'select c.COLLECTID COLLECTID,c.COLLECTNAME  COLLECTNAME,c.DESCRIPTION  DESCRIPTION,nvl(c.TOTALPLAYNUM,0) TOTALPLAYNUM from T_VO_COLLECT c',
   '视频内容集数据同步','2',50000,'0x01',4,'vo_collect','/opt/aspire/product/chroot_panguso/panguso/mo','GB18030','2','1','3');

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.035_SSMS','MM4.0.0.0.039_SSMS');

commit;

------

