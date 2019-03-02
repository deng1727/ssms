-------计算飙升的视图-------------
create or replace view v_content_newscore as
select  t.contentid, (VIEW_7DAYS_CNT+DL_7DAYS_CNT+FEE_7DAYS_CNT)*111 as hotscore,0 as newRANK_HOT,0 as souar 
from T_MT_RANK_INDEX_D d,t_unified_packname_app_ref t
where d.content_id=t.appid;

-----------计算软件的存储过程做修改,把精度提升到15位---------------

CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin_software
as
     z NUMBER(15,6);
     z1 NUMBER(15,6);
     z2 NUMBER(15,6);
     b NUMBER(20,6);
     d1 NUMBER(15,6);
     d2 NUMBER(15,6);
     d3 NUMBER(15,6);
     m1 NUMBER(15,6);
     m2 NUMBER(15,6);
     m1max NUMBER(15,6);
     m1min NUMBER(15,6);
     m2max NUMBER(15,6);
     m2min NUMBER(15,6);
     a NUMBER(15,6);
     j NUMBER(15,6);

  v_nstatus number;
    v_nrecod  number;

cursor s_cur is
        select * from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID='1001';
begin
 v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','新榜单计算P_mt_rank_index_fin_software');
  -- delete from MID_O_TABLE
     execute immediate'TRUNCATE TABLE MID_O_TABLE';
   Insert into MID_O_TABLE(PRD_TYPE_ID,b,m1max,m1min,m2max,m2min) select PRD_TYPE_ID,sum(d3),
max((d1+d2+d3)/(ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(ADD_DL_CNT*0.2+d1+d2+d3+1)),
min((d1+d2+d3)/(ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(ADD_DL_CNT*0.2+d1+d2+d3+1)),
max(((fee+fee1+fee2)/(add_fee+1))*ln(add_fee+1)),
min(((fee+fee1+fee2)/(add_fee+1))*ln(add_fee+1))
 from(
select l.PRD_TYPE_ID PRD_TYPE_ID,fee,fee1,fee2,add_fee,ADD_DL_CNT,(r1.CLASS_DL_CNT*0.15+r1.SEARCH_DL_CNT*0.4+r1.HOT_DL_CNT*0.25+r1.MAN_DL_CNT*0.2)*exp(r1.COM_CNT/a1) a,
    (r1.CLASS_DL_CNT*0.15+r1.SEARCH_DL_CNT*0.4+r1.HOT_DL_CNT*0.25+r1.MAN_DL_CNT*0.2)*exp(r1.COM_CNT/a2) d3,
    (r1.CLASS_DL_CNT1*0.15+r1.SEARCH_DL_CNT1*0.4+r1.HOT_DL_CNT1*0.25+r1.MAN_DL_CNT1*0.2)*exp(r1.COM_CNT1/a3) d2,
    (r1.CLASS_DL_CNT2*0.15+r1.SEARCH_DL_CNT2*0.4+r1.HOT_DL_CNT2*0.25+r1.MAN_DL_CNT2*0.2)*exp(r1.COM_CNT2/a4) d1 from t_mt_rank_index_d r1,(
    select PRD_TYPE_ID, sum(COM_CNT) a1,
   sum(COM_CNT) a2,
    sum(COM_CNT1) a3,
   sum(COM_CNT2) a4
    from t_mt_rank_index_d where DL_15DAYS_CNT>50  and PRD_TYPE_ID in (1001,1003)
    group by PRD_TYPE_ID
    )l
    where r1.dl_15days_cnt>50
    and r1.prd_type_id=l.PRD_TYPE_ID)o group by PRD_TYPE_ID;


  --execute immediate'TRUNCATE TABLE MID_TABLE';
  --delete from MID_TABLE where PRD_TYPE_ID='1001';
 for r in s_cur loop

 select  PRD_TYPE_ID,b,m1max,m1min ,m2max,m2min
into j,b,m1max,m1min,m2max,m2min
 from MID_O_TABLE where PRD_TYPE_ID=r.PRD_TYPE_ID;

    select (r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT))/b
    *ln(b),r.fee/sum(fee)*ln(sum(fee)),(r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT)),
     (r.CLASS_DL_CNT1*0.15+r.SEARCH_DL_CNT1*0.4+r.HOT_DL_CNT1*0.25+r.MAN_DL_CNT1*0.2)*exp(r.COM_CNT1/sum(COM_CNT1)),
     (r.CLASS_DL_CNT2*0.15+r.SEARCH_DL_CNT2*0.4+r.HOT_DL_CNT2*0.25+r.MAN_DL_CNT2*0.2)*exp(r.COM_CNT2/sum(COM_CNT2))
    into  z1,z2,d3,d2,d1 from t_mt_rank_index_d where DL_15DAYS_CNT>50  and PRD_TYPE_ID=r.PRD_TYPE_ID;
     z:=z2*0.05+z1*0.95;
     m1:=(d1+d2+d3)/(r.ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(r.ADD_DL_CNT*0.2+d1+d2+d3+1);
     m2:=((r.fee+r.fee1+r.fee2)/(r.add_fee+1))*ln(r.add_fee+1);
    insert into MID_TEMP_TABLE values(r.content_id,z,((m1-m1min) / (m1max-m1min))*0.7+((m2-m2min) / (m2max-m2min))*0.3,z1,z2,d3,'1001');
    end loop;
    commit;

    execute immediate'TRUNCATE TABLE MID_TABLE';
    insert into MID_TABLE(APPID,HOTLIST,RISELIST,Z1,Z2,D3,PRD_TYPE_ID) select APPID,HOTLIST,RISELIST,Z1,Z2,D3,PRD_TYPE_ID from MID_TEMP_TABLE;
    commit;

     v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    exception
    when others then
    rollback;
     v_nstatus := pg_log_manage.f_errorlog;
end;

-------------修改V_BID_INTERS视图,trgcontent表中的appid维度做关联---------------------------

create or replace view v_bid_inters as
select r."M_HOTLIST",r."M_RISELIST",r."APPCATEID",b."ID",b."CONTENTID",b."PARAM",b."TYPE",b."SORTID" from v_maxcate_hot_rise r,t_bid_inter b,t_r_gcontent g where b.contentid=g.appid and r.appcateid=g.appcateid;


---------------修改视图v_appcate_hotblank_sort  appid和中间表的appid做关联------------------

create or replace view v_appcate_hotblank_sort as
select g.contentid,m.hotlist,row_number() over(partition by g.appcateid order by hotlist desc) sortid,g.appcateid
from mid_table m,t_r_gcontent g where m.appid = g.appid;
comment on column V_APPCATE_HOTBLANK_SORT.CONTENTID is '应用ID';
comment on column V_APPCATE_HOTBLANK_SORT.HOTLIST is '最热榜单值';
comment on column V_APPCATE_HOTBLANK_SORT.SORTID is '分类最热榜单排序';
comment on column V_APPCATE_HOTBLANK_SORT.APPCATEID is '应用二级分类id';


--------------------计算榜单的存储过程trgcontent表中appid关联-------------------------------------------

CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin as
  v_nstatus     number;
  v_nrecod      number;
begin

update t_mt_rank_index_d set fee = fee+0.1 where DL_15DAYS_CNT>50 and PRD_TYPE_ID='1001';
 -------执行游戏榜单计算存储过程-----------------------
P_mt_rank_index_fin_game;
 -------执行软件榜单计算存储过程-----------------------
P_mt_rank_index_fin_software; 

v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','最热和飙升榜单计算');

 --------------最热榜单干预----------------
update mid_table t1
  set t1.hotlist
     = (select t1.hotlist*(decode(t2.type,'1',t2.param,1))
          from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='1')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='1');

 --------------最新榜单干预----------------
update mid_table t1
  set t1.riselist
     = (select t1.riselist*(decode(t2.type,'2',t2.param,1))
     from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='2')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='2');

 -------最热细粒度干预-----------------------
  update mid_table t1
  set t1.hotlist
     = (select t2.M_HOTLIST+(decode(t2.type,'3',t2.param,0))
          from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='3')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='3');

 -------最新细粒度干预-----------------------
update mid_table t1
  set t1.riselist
     = (select t2.M_RISELIST+(decode(t2.type,'4',t2.param,0))
     from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='4')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='4');

  -------分类最热数据干预-----------------------
update mid_table m1 set m1.hotlist =
 (select (v.hotlist + b.param) h from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v
where gc.appid = b.contentid and gc.appcateid = v.appcateid and
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5')
 where  exists(select 1 from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v
where gc.appid = b.contentid and gc.appcateid = v.appcateid and
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5');

update mid_table m
   set m.hotlist =nvl(m.hotlist*1000 - 0.000001*(select sortid from (select m.appid, row_number() over(order by m.hotlist desc) sortid
   from mid_table m
  where m.prd_type_id = '1003') mg where mg.appid = m.appid),m.hotlist)
  where m.prd_type_id='1003';
    
    
    update mid_table m
   set m.riselist =nvl(m.riselist*1000 - 0.000001*(select sortid from (select m.appid, row_number() over(order by m.riselist desc) sortid
   from mid_table m
  where m.prd_type_id = '1003') mg where mg.appid = m.appid),m.riselist)
    where m.prd_type_id='1003';
    


update mid_table m
   set m.hotlist =nvl(m.hotlist*1000 - 0.000001*(select sortid from (select m.appid, row_number() over(order by m.hotlist desc) sortid
   from mid_table m
  where m.prd_type_id = '1001') mg where mg.appid = m.appid),m.hotlist)
  where m.prd_type_id='1001';
    
    
    update mid_table m
   set m.riselist =nvl(m.riselist*1000 - 0.000001*(select sortid from (select m.appid, row_number() over(order by m.riselist desc) sortid
   from mid_table m
  where m.prd_type_id = '1001') mg where mg.appid = m.appid),m.riselist)
  where m.prd_type_id='1001';

 commit;

  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );  

exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;  

end P_mt_rank_index_fin;

---------------t_r_exportsql_por配置,将商品中心的A表定期同步到本地表-----------------------------------



insert into t_r_exportsql_por (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK, TO_WHERE, TO_COLUMNS, SYNTYPE)
values (11, '商品中心全量A表', 'v_unified_packname_app_ref', 't_unified_packname_app_ref', 't_unified_packname_app_ref_tra', 't_unified_packname_app_ref_bak', 'contentid like ''30%'' or contentid like ''33%''', null, 0);
-------------t_caterule_cond_base规则的基础语句修改content表中appid和midtable中appid关联-------------------------
insert into t_caterule_cond_base (BASE_ID, BASE_NAME, BASE_SQL)
values (80, 'MM4.2最热飙升榜单规则', 'select g.id from mid_table t, t_r_gcontent g where t.appid = g.appid and g.channeldisptype = ''0'' and (g.apptype != ''5'' or g.apptype is null)');





---------------------rank表的结构有改变-------------------------
drop table T_MT_RANK_INDEX_D;
-- Create table
create table T_MT_RANK_INDEX_D
(
  stat_time      NUMBER(8),
  content_id     VARCHAR2(12),
  content_name   VARCHAR2(60),
  prd_type_id    VARCHAR2(10),
  add_dl_cnt     NUMBER(12,2),
  add_fee        NUMBER(12,2),
  dl_15days_cnt  NUMBER(12,2),
  view_7days_cnt NUMBER(12,2),
  dl_7days_cnt   NUMBER(12,2),
  fee_7days_cnt  NUMBER(12,2),
  class_dl_cnt   NUMBER(12,2),
  search_dl_cnt  NUMBER(12,2),
  hot_dl_cnt     NUMBER(12,2),
  man_dl_cnt     NUMBER(12,2),
  dl_cnt         NUMBER(12,2),
  view_cnt       NUMBER(12,2),
  fee            NUMBER(12,2),
  fee_cnt        NUMBER(12,2),
  com_cnt        NUMBER(12,2),
  class_dl_cnt1  NUMBER(12,2),
  search_dl_cnt1 NUMBER(12,2),
  hot_dl_cnt1    NUMBER(12,2),
  man_dl_cnt1    NUMBER(12,2),
  fee1           NUMBER(12,2),
  com_cnt1       NUMBER(12,2),
  class_dl_cnt2  NUMBER(12,2),
  search_dl_cnt2 NUMBER(12,2),
  hot_dl_cnt2    NUMBER(12,2),
  man_dl_cnt2    NUMBER(12,2),
  fee2           NUMBER(12,2),
  com_cnt2       NUMBER(12,2)
);


drop table t_mt_rank_index_d_report;

create table t_mt_rank_index_d_report
(
  stat_time      NUMBER(8),
  content_id     VARCHAR2(12),
  content_name   VARCHAR2(60),
  prd_type_id    VARCHAR2(10),
  add_dl_cnt     NUMBER(12,2),
  add_fee        NUMBER(12,2),
  dl_15days_cnt  NUMBER(12,2),
  view_7days_cnt NUMBER(12,2),
  dl_7days_cnt   NUMBER(12,2),
  fee_7days_cnt  NUMBER(12,2),
  class_dl_cnt   NUMBER(12,2),
  search_dl_cnt  NUMBER(12,2),
  hot_dl_cnt     NUMBER(12,2),
  man_dl_cnt     NUMBER(12,2),
  dl_cnt         NUMBER(12,2),
  view_cnt       NUMBER(12,2),
  fee            NUMBER(12,2),
  fee_cnt        NUMBER(12,2),
  com_cnt        NUMBER(12,2),
  class_dl_cnt1  NUMBER(12,2),
  search_dl_cnt1 NUMBER(12,2),
  hot_dl_cnt1    NUMBER(12,2),
  man_dl_cnt1    NUMBER(12,2),
  fee1           NUMBER(12,2),
  com_cnt1       NUMBER(12,2),
  class_dl_cnt2  NUMBER(12,2),
  search_dl_cnt2 NUMBER(12,2),
  hot_dl_cnt2    NUMBER(12,2),
  man_dl_cnt2    NUMBER(12,2),
  fee2           NUMBER(12,2),
  com_cnt2       NUMBER(12,2)
);

commit;



--------------MID_O_TABLE字段精度扩大---------------------------------

drop table MID_O_TABLE ;

-- Create table
create table MID_O_TABLE
(
  prd_type_id VARCHAR2(12) not null,
  b           NUMBER(15,6),
  m1max       NUMBER(12,6),
  m1min       NUMBER(12,6),
  m2max       NUMBER(12,6),
  m2min       NUMBER(12,6)
)

--------------MID_TABLE字段精度扩大---------------------------------

drop table MID_TABLE ;
-- Create table
create table MID_TABLE
(
  appid       VARCHAR2(12) not null,
  hotlist     NUMBER(12,6),
  riselist    NUMBER(12,6),
  z1          NUMBER(12,6),
  z2          NUMBER(12,6),
  d3          NUMBER(15,6),
  prd_type_id VARCHAR2(12)
)


--------------------------------t_a_cm_device_resource表新增一个字段----------------------------------------------------
alter table t_a_cm_device_resource add (washpackstatus varchar2(1))