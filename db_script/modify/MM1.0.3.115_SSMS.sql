-- Add/modify columns 
alter table T_R_CATEGORY add OTHERNET VARCHAR2(2) default 1;
-- Add comments to the columns 
comment on column T_R_CATEGORY.OTHERNET
  is '1��֧��������0����֧������';

-- Create table
create table T_R_DOWN_SORT
(
  content_id       VARCHAR2(12),
  os_id            NUMBER(8),
  sort_num_new     NUMBER,
  sort_num_old     NUMBER,
  minus_sort_null  NUMBER,
  minus_down_count NUMBER
);

-- Create table
create table T_R_DOWN_SORT_NEW
(
  content_id VARCHAR2(12),
  os_id      NUMBER(8),
  sort_num   NUMBER,
  down_count NUMBER
);
-- Create table
create table T_R_DOWN_SORT_OLD
(
  content_id VARCHAR2(12),
  os_id      NUMBER(8),
  sort_num   NUMBER,
  down_count NUMBER
);

---------------------------------------------------------------------------------------
--------��������Ϊ�洢���̣���Ҫȫ��������sql window��һ��ִ��---------------------------------------------------------------------------
---------------------------------------------------------------------------------------

create or replace procedure p_Binding_sort as
  type type_table_osid is table of number index by binary_integer; --�Զ�������
  type type_table_sort is table of number index by binary_integer; --�Զ�������
  v_type_nosid   type_table_osid; --OS_ID����
  v_type_nsort   type_table_sort; --SORT_NUM_OLD����
  v_nosidi       number; --iphone����
  v_nosida       number; --Android����
  v_nsortmaxi    number; --iphone�������ֵ
  v_nsortmaxa    number; --Android�������ֵ
  v_vsqlinsert   varchar2(1500); --��̬SQL
  v_vsqlalter    varchar2(1500); --��̬SQL
  v_vsqltruncate varchar2(1500); --��̬SQL
  v_vconstant  varchar(6):='0006'; --����

begin
    --ɾ��ǰ������
  v_vsqltruncate := 'truncate table t_r_down_sort_new';
   execute immediate v_vsqltruncate;
   v_vsqltruncate := 'truncate table t_r_down_sort';
  execute immediate v_vsqltruncate;
  --��ֹ��д������־
  execute immediate ' alter table t_r_down_sort_new nologging';
  execute immediate ' alter table t_r_down_sort_old nologging';
  execute immediate ' alter table t_r_down_sort nologging';

  --���뱨���ԭʼ���ݵ�����
  insert /*+ append parallel(t_r_down_sort_new,4) */ into t_r_down_sort_new
    (content_id, os_id, SORT_NUM,down_count)
    select
    /*+  parallel(report_servenday,4) */
     content_id,
     os_id,
     dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
     add_7days_down_count
      from report_servenday a
     where a.os_id in (9, 3)
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
  end loop;

  --���¡����뱨����δ�ṩconten_idֵ������
    --����һ��iphone����
  execute  immediate   ' create index ind_downsort_contentid on   t_r_down_sort_new(content_id) ';
  v_vsqlinsert:= 'insert  into t_r_down_sort_new(content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,3,' || v_nsortmaxi  ||
               '+ 1,0  from v_cm_content v
where v.status='||v_vconstant||'  and  not exists (select 1 from t_r_down_sort_new n where n.content_id=v.contentid and n.os_id=3)';
execute immediate v_vsqlinsert;

    --����һ��android����
  v_vsqlinsert:= 'insert  into t_r_down_sort_new(content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,9,' || v_nsortmaxa  ||
               '+ 1,0   from v_cm_content v
where v.status='||v_vconstant||'   and  not exists (select  1 from t_r_down_sort_new n where n.content_id=v.contentid and n.os_id=9)';

execute immediate v_vsqlinsert;
  commit;

  /*  --�������ṩ���ݣ�ǰ��û�����ݣ�����ǰ������
  v_vsqlstr:='insert into t_r_down_sort_old
    (content_id, os_id, sort_num)
    select n.content_id,n.os_id,decode(n.os_id,9,'||v_nsortmaxa||',3,'||v_nsortmaxi||')
      from t_r_down_sort_new n
     where not exists (select 1
              from t_r_down_sort_old o
             where n.content_id = o.content_id
               and n.os_id = n.os_id)';
     execute immediate   v_vsqlstr;*/
  execute  immediate   ' create index ind_downsorto_contentid on   t_r_down_sort_old(content_id) parallel';
  insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort
    (content_id, os_id, sort_num_new, sort_num_old, minus_sort_null,minus_down_count)
    select n.content_id,
           n.os_id,
           n.sort_num,
           o.sort_num,
           o.sort_num-n.sort_num,
           o.down_count - n.down_count

      from t_r_down_sort_old o, t_r_down_sort_new n
     where o.content_id = n.content_id
       and o.os_id = n.os_id;
  commit;
 execute  immediate ' drop index ind_downsort_contentid';
 execute  immediate ' drop index ind_downsorto_contentid';


  --���������ݱ�Ϊǰ������
  v_vsqlalter := 'alter table t_r_down_sort_old rename to t_r_down_sort_old1 ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_new rename to t_r_down_sort_old ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_old1 rename to t_r_down_sort_new ';
  execute immediate v_vsqlalter;

end;


---------------------------------------------------------------------------------------
---------------�洢�������------------------------------------------------------------------------
---------------------------------------------------------------------------------------

create or replace view v_r_sevencompared as
select a.content_id contentid,
       max(decode(a.os_id, 3, a.minus_sort_null)) onumber,
       max(decode(a.os_id, 9, a.minus_sort_null)) anumber
  from t_r_down_sort a
 group by a.content_id;

create or replace view t_r_servenday_temp_a as
select "OS_ID","CONTENT_ID",t.down_count as ADD_7DAYS_DOWN_COUNT from 
t_r_down_sort_old t where t.os_id=9;

create or replace view t_r_servenday_temp_o as
select "OS_ID","CONTENT_ID",t.down_count as ADD_7DAYS_DOWN_COUNT from 
t_r_down_sort_old t where t.os_id=3;

update t_caterule_cond_base set base_sql='select b.id from t_r_base b, t_r_gcontent g, v_service v,t_content_count c,V_R_SEVENCOMPARED s where s.CONTENTID=g.contentid and b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid(+) and g.provider !=''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))' where base_id='15';

update t_caterule_cond_base set base_sql='select b.id from t_r_base b, t_r_gcontent g, v_service v,t_r_servenday_temp_a c,V_R_SEVENCOMPARED s where  s.CONTENTID=g.contentid and   b.id = g.id  and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.CONTENT_ID  and  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')' where base_id='16';

update t_caterule_cond_base set base_sql='select b.id from t_r_base b, t_r_gcontent g, v_service v,t_r_servenday_temp_o c,V_R_SEVENCOMPARED s where  s.CONTENTID=g.contentid and   b.id = g.id  and b.type like ''nt:gcontent:app%''  and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.CONTENT_ID  and  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')' where base_id='17';


-- Add comments to the table 
comment on table T_R_DOWN_SORT
  is '�ϴκ͵�ǰ��������Ľ����';
-- Add comments to the columns 
comment on column T_R_DOWN_SORT.CONTENT_ID
  is 'Ӧ��ID';
comment on column T_R_DOWN_SORT.OS_ID
  is 'ƽ̨ID��3��ʾOPhone OS ��9����Android';
comment on column T_R_DOWN_SORT.SORT_NUM_NEW
  is '��ǰ������';
comment on column T_R_DOWN_SORT.SORT_NUM_OLD
  is 'ǰһ������';
comment on column T_R_DOWN_SORT.MINUS_SORT_NULL
  is 'ǰһ���������ǰ����';
comment on column T_R_DOWN_SORT.MINUS_DOWN_COUNT
  is 'ǰһ�����ؼ���ǰ����';


-- Add comments to the table 
comment on table T_R_DOWN_SORT_NEW
  is '��ǰӦ�������';
-- Add comments to the columns 
comment on column T_R_DOWN_SORT_NEW.CONTENT_ID
  is 'Ӧ��ID';
comment on column T_R_DOWN_SORT_NEW.OS_ID
  is 'ƽ̨ID��3��ʾOPhone OS ��9����Android';
comment on column T_R_DOWN_SORT_NEW.SORT_NUM
  is '����';
comment on column T_R_DOWN_SORT_NEW.DOWN_COUNT
  is 'ǰ7�����ش���';

  
-- Add comments to the table 
comment on table T_R_DOWN_SORT_OLD
  is '�ϴ�Ӧ�������';
-- Add comments to the columns 
comment on column T_R_DOWN_SORT_OLD.CONTENT_ID
  is 'Ӧ��ID';
comment on column T_R_DOWN_SORT_OLD.OS_ID
  is 'ƽ̨ID��3��ʾOPhone OS ��9����Android';
comment on column T_R_DOWN_SORT_OLD.SORT_NUM
  is '����';
comment on column T_R_DOWN_SORT_OLD.DOWN_COUNT
  is 'ǰ7�����ش���';

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.110_SSMS','MM1.0.3.115_SSMS');
commit;
