

delete from t_right where  rightid='2_1505_COMIC';
delete from t_roleright where roleid='1' and rightid='2_1505_COMIC';


-----应用促销----
drop synonym  ppms_v_t_content_ext;
drop view t_content_ext;
alter  table  t_content_ext_local rename to t_content_ext;






------andoridPad需求-----
drop view t_r_servenday_temp_p;
delete from  t_caterule_cond_base where base_id in (39,40);


------******start*****_-----------
---回滚--为存储过程，需要整体执行-----
---------------------------------

create or replace procedure p_Binding_sort_Final2 as
  type type_table_osid is table of number index by binary_integer; --自定义类型
  type type_table_sort is table of number index by binary_integer; --自定义类型
  v_type_nosid   type_table_osid; --OS_ID集合
  v_type_nsort   type_table_sort; --SORT_NUM_OLD集合
  v_nosidi       number; --iphone类型
  v_nosida       number; --Android类型
  v_nosids       number; --S60类型

  v_nsortmaxi    number; --iphone排序最大值
  v_nsortmaxs    number; --S60排序最大值
  v_nsortmaxa    number; --Android排序最大值
  v_vsqlinsert   varchar2(1500); --动态SQL
  v_vsqlalter    varchar2(1500); --动态SQL
  v_vsqltruncate varchar2(1500); --动态SQL
  --v_vconstant  varchar(6):='0006'; --常量
  v_nindnum    number;--记录索引是否存在
  v_nstatus number;--纪录监控包状态
  v_ncount number;--记录report_servenday表的记录数

begin
v_nstatus:=pg_log_manage.f_startlog('P_BINDING_SORT_FINAL2','货架榜单');

--报表没有提供数据流程结束，抛出例外
select count(9) into v_ncount from report_servenday a
     where a.os_id in (9, 3,1)
       and stat_time = to_char(sysdate-1,'yyyymmdd');
if v_ncount=0 then
  raise_application_error(-20088,'报表提供记录数为0');
  end if;

  --删除索引
    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID';
     if v_nindnum>0 then
   execute  immediate ' drop index IND_DOWNSORTO_CONTENTID';
  end if;

    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID';
     if v_nindnum>0 then
 execute  immediate ' drop index IND_DOWNSORT_CONTENTID';
  end if;

    --删除前次数据
  v_vsqltruncate := 'truncate table t_r_down_sort_new ';
   execute immediate v_vsqltruncate;
   v_vsqltruncate := 'truncate table t_r_down_sort ';
  execute immediate v_vsqltruncate;
  --禁止表写在线日志
  execute immediate ' alter table t_r_down_sort_new  nologging';
  execute immediate ' alter table t_r_down_sort_old  nologging';
  execute immediate ' alter table t_r_down_sort  nologging';

  --插入报表的原始数据到本地
  insert /*+ append parallel(t_r_down_sort_new ,4) */ into t_r_down_sort_new
    (content_id, os_id, SORT_NUM,down_count,add_order_count)
    select
    /*+  parallel(report_servenday,4) */
     content_id,
     os_id,
     dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
     add_7days_down_count,add_order_count
      from report_servenday a
     where a.os_id in (9, 3,1)
       and stat_time = to_char(sysdate-1,'yyyymmdd') ;
commit;
  --根据OS_ID分组获取t_r_down_sort.sort_num_old的最大值，用于更新报表前一次未提供conten_id的排序值
  select os_id, max(sort_num) bulk collect
    into v_type_nosid, v_type_nsort
    from t_r_down_sort_new
   group by os_id;

  for i in 1 .. sql%rowcount loop
    if v_type_nosid(i) = 9 then
      v_nosida    := 9;
      v_nsortmaxa := v_type_nsort(i);
    end if;

    if v_type_nosid(i) = 3 then
      v_nosidi    := 3;
      v_nsortmaxi := v_type_nsort(i);
    end if;
      if v_type_nosid(i) = 1 then
      v_nosids    := 1;
      v_nsortmaxs := v_type_nsort(i);
    end if;

  end loop;

  --更新、插入报表本次未提供conten_id值的数据
    --插入一条iphone数据
    select count(9) into v_nindnum  from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID';
    if v_nindnum=0 then
  execute  immediate   ' create index IND_DOWNSORT_CONTENTID  on   t_r_down_sort_new (content_id) ';
  end if;
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v,4) */ v.contentid,3,' || v_nsortmaxi  ||
               '+ 1,0  from V_CONTENT_LAST v
where v.osid=3 and  not exists (select 1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=3)';
execute immediate v_vsqlinsert;

  --插入一条s60数据
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,1,' || v_nsortmaxs  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=1 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=1)';

execute immediate v_vsqlinsert;

    --插入一条android数据
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,9,' || v_nsortmaxa  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=9 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=9)';

execute immediate v_vsqlinsert;
  commit;

  /*  --报表本次提供数据，前次没有数据，补充前次数据
  v_vsqlstr:='insert into t_r_down_sort_old
    (content_id, os_id, sort_num)
    select n.content_id,n.os_id,decode(n.os_id,9,'||v_nsortmaxa||',3,'||v_nsortmaxi||')
      from t_r_down_sort_new  n
     where not exists (select 1
              from t_r_down_sort_old  o
             where n.content_id = o.content_id
               and n.os_id = n.os_id)';
     execute immediate   v_vsqlstr;*/
     select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID';
     if v_nindnum=0 then
  execute  immediate   ' create index IND_DOWNSORTO_CONTENTID  on   t_r_down_sort_old (content_id) parallel';
  end if;
   insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort
    (content_id, os_id, sort_num_new, sort_num_old, minus_sort_null,minus_down_count,add_order_count,Standard_score)
select content_id,
       os_id,
       sort_num_new,
       sort_num_old,
       minus_sort_null,
       minus_down_count,
       add_order_count,

       decode((max(B) over(partition by os_id) - min(B) over( partition by os_id)),0,0, trunc((a -
       abs(a) * (1 - (B - min(B) over(partition by os_id)) / (max(B) over( partition by os_id) - min(B) over( partition by os_id))))*0.6+b*0.4,3)) Standard_score
  from (select n.content_id,
       n.os_id,
       n.sort_num sort_num_new,
       o.sort_num sort_num_old,
       o.sort_num - n.sort_num minus_sort_null,
       n.down_count - o.down_count minus_down_count,

       decode(stddev(o.sort_num - n.sort_num) over(partition by o.os_id),
              0,
              0,
              ((o.sort_num - n.sort_num) - avg(o.sort_num - n.sort_num)
               over(partition by o.os_id)) / stddev(o.sort_num - n.sort_num) over(partition by o.os_id)) A,
       decode(stddev(n.down_count-o.down_count) over(partition by n.os_id),
              0,
              0,
              ((n.down_count - o.down_count) -
              avg(n.down_count - o.down_count) over(partition by o.os_id)) /
              stddev(n.down_count-o.down_count) over(partition by n.os_id)) B,

       n.add_order_count
  from t_r_down_sort_old  o, t_r_down_sort_new  n
 where o.content_id = n.content_id
   and o.os_id = n.os_id)g;
   v_nstatus:=pg_log_manage.f_successlog;
  commit;
/* execute  immediate ' drop index IND_DOWNSORTO_CONTENTID ';
 execute  immediate ' drop index IND_DOWNSORT_CONTENTID ';*/

  --将本次数据变为前次数据
  v_vsqlalter := 'alter table t_r_down_sort_old  rename to t_r_down_sort_old_1 ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_new  rename to t_r_down_sort_old  ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_old_1 rename to t_r_down_sort_new  ';
  execute immediate v_vsqlalter;

  exception
  when others then
      v_nstatus:=pg_log_manage.f_errorlog;

end;


------******end*****_-----------
  drop  synonym  ppms_v_t_content_ext ;
  drop  synonym  ppms_V_T_CON_EXT_SPECIALFREE ;


delete DBVERSION where PATCHVERSION = 'MM1.1.1.069_SSMS' and LASTDBVERSION = 'MM1.1.1.065_SSMS';
commit;