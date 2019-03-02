

delete from t_right where  rightid='2_1505_COMIC';
delete from t_roleright where roleid='1' and rightid='2_1505_COMIC';


-----Ӧ�ô���----
drop synonym  ppms_v_t_content_ext;
drop view t_content_ext;
alter  table  t_content_ext_local rename to t_content_ext;






------andoridPad����-----
drop view t_r_servenday_temp_p;
delete from  t_caterule_cond_base where base_id in (39,40);


------******start*****_-----------
---�ع�--Ϊ�洢���̣���Ҫ����ִ��-----
---------------------------------

create or replace procedure p_Binding_sort_Final2 as
  type type_table_osid is table of number index by binary_integer; --�Զ�������
  type type_table_sort is table of number index by binary_integer; --�Զ�������
  v_type_nosid   type_table_osid; --OS_ID����
  v_type_nsort   type_table_sort; --SORT_NUM_OLD����
  v_nosidi       number; --iphone����
  v_nosida       number; --Android����
  v_nosids       number; --S60����

  v_nsortmaxi    number; --iphone�������ֵ
  v_nsortmaxs    number; --S60�������ֵ
  v_nsortmaxa    number; --Android�������ֵ
  v_vsqlinsert   varchar2(1500); --��̬SQL
  v_vsqlalter    varchar2(1500); --��̬SQL
  v_vsqltruncate varchar2(1500); --��̬SQL
  --v_vconstant  varchar(6):='0006'; --����
  v_nindnum    number;--��¼�����Ƿ����
  v_nstatus number;--��¼��ذ�״̬
  v_ncount number;--��¼report_servenday��ļ�¼��

begin
v_nstatus:=pg_log_manage.f_startlog('P_BINDING_SORT_FINAL2','���ܰ�');

--����û���ṩ�������̽������׳�����
select count(9) into v_ncount from report_servenday a
     where a.os_id in (9, 3,1)
       and stat_time = to_char(sysdate-1,'yyyymmdd');
if v_ncount=0 then
  raise_application_error(-20088,'�����ṩ��¼��Ϊ0');
  end if;

  --ɾ������
    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID';
     if v_nindnum>0 then
   execute  immediate ' drop index IND_DOWNSORTO_CONTENTID';
  end if;

    select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID';
     if v_nindnum>0 then
 execute  immediate ' drop index IND_DOWNSORT_CONTENTID';
  end if;

    --ɾ��ǰ������
  v_vsqltruncate := 'truncate table t_r_down_sort_new ';
   execute immediate v_vsqltruncate;
   v_vsqltruncate := 'truncate table t_r_down_sort ';
  execute immediate v_vsqltruncate;
  --��ֹ��д������־
  execute immediate ' alter table t_r_down_sort_new  nologging';
  execute immediate ' alter table t_r_down_sort_old  nologging';
  execute immediate ' alter table t_r_down_sort  nologging';

  --���뱨���ԭʼ���ݵ�����
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
  --����OS_ID�����ȡt_r_down_sort.sort_num_old�����ֵ�����ڸ��±���ǰһ��δ�ṩconten_id������ֵ
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

  --���¡����뱨����δ�ṩconten_idֵ������
    --����һ��iphone����
    select count(9) into v_nindnum  from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID';
    if v_nindnum=0 then
  execute  immediate   ' create index IND_DOWNSORT_CONTENTID  on   t_r_down_sort_new (content_id) ';
  end if;
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v,4) */ v.contentid,3,' || v_nsortmaxi  ||
               '+ 1,0  from V_CONTENT_LAST v
where v.osid=3 and  not exists (select 1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=3)';
execute immediate v_vsqlinsert;

  --����һ��s60����
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,1,' || v_nsortmaxs  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=1 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=1)';

execute immediate v_vsqlinsert;

    --����һ��android����
  v_vsqlinsert:= 'insert  into t_r_down_sort_new (content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,9,' || v_nsortmaxa  ||
               '+ 1,0   from V_CONTENT_LAST v
where v.osid=9 and  not exists (select  1 from t_r_down_sort_new  n where n.content_id=v.contentid and n.os_id=9)';

execute immediate v_vsqlinsert;
  commit;

  /*  --�������ṩ���ݣ�ǰ��û�����ݣ�����ǰ������
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

  --���������ݱ�Ϊǰ������
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