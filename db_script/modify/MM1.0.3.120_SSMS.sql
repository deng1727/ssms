-- Add/modify columns 
alter table t_caterule add maxGoodsNum number(10);
-- Add comments to the columns 
comment on column t_caterule.maxGoodsNum
  is '-1，表示不限；0，程序不执行压缩；其他正整数就是表示货架的商品总容量';
  
 

----------------------------------------------
-------上升最快7天订购量变化量------------
----------------------------------------------

-- Add/modify columns 
alter table T_R_DOWN_SORT add add_order_count number(12);
alter table T_R_DOWN_SORT add standard_score number;
-- Add comments to the columns 
comment on column T_R_DOWN_SORT.add_order_count
  is '累计订购量';
comment on column T_R_DOWN_SORT.standard_score
  is '上升最快指标得分';
  
  
  -- Add/modify columns 
alter table T_R_DOWN_SORT_NEW add add_order_count NUMBER(12);
-- Add comments to the columns 
comment on column T_R_DOWN_SORT_NEW.add_order_count
  is '累计订购量';
  
  
  -- Add/modify columns 
alter table T_R_DOWN_SORT_OLD add add_order_count number(12);
-- Add comments to the columns 
comment on column T_R_DOWN_SORT_OLD.add_order_count
  is '累计订购量';
  
  
  create or replace view v_cm_content_snapshot as

select b.contentid,c.os_id from v_cm_content a,v_cm_device_resource b,t_device c

 where a.contentid=b.contentid

 and b.device_id=c.device_id and c.os_id in (3,9) and a.status='0006'

group by b.contentid,c.os_id;

---------------------------------
-------存储过程-----------------------
---------------------------------
create or replace procedure p_Binding_sort as
  type type_table_osid is table of number index by binary_integer; --自定义类型
  type type_table_sort is table of number index by binary_integer; --自定义类型
  v_type_nosid   type_table_osid; --OS_ID集合
  v_type_nsort   type_table_sort; --SORT_NUM_OLD集合
  v_nosidi       number; --iphone类型
  v_nosida       number; --Android类型
  v_nsortmaxi    number; --iphone排序最大值
  v_nsortmaxa    number; --Android排序最大值
  v_vsqlinsert   varchar2(1500); --动态SQL
  v_vsqlalter    varchar2(1500); --动态SQL
  v_vsqltruncate varchar2(1500); --动态SQL
  --v_vconstant  varchar(6):='0006'; --常量
  v_nindnum    number;--记录索引是否存在

begin
    --删除前次数据
  v_vsqltruncate := 'truncate table t_r_down_sort_new';
   execute immediate v_vsqltruncate;
   v_vsqltruncate := 'truncate table t_r_down_sort';
  execute immediate v_vsqltruncate;
  --禁止表写在线日志
  execute immediate ' alter table t_r_down_sort_new nologging';
  execute immediate ' alter table t_r_down_sort_old nologging';
  execute immediate ' alter table t_r_down_sort nologging';

  --插入报表的原始数据到本地
  insert /*+ append parallel(t_r_down_sort_new,4) */ into t_r_down_sort_new
    (content_id, os_id, SORT_NUM,down_count,add_order_count)
    select
    /*+  parallel(report_servenday,4) */
     content_id,
     os_id,
     dense_rank() over(partition by a.os_id order by a.add_7days_down_count desc) sort_num,
     add_7days_down_count,add_order_count
      from report_servenday a
     where a.os_id in (9, 3)
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
  end loop;

  --更新、插入报表本次未提供conten_id值的数据
    --插入一条iphone数据
    select count(9) into v_nindnum  from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORT_CONTENTID';
    if v_nindnum=0 then
  execute  immediate   ' create index ind_downsort_contentid on   t_r_down_sort_new(content_id) ';
  end if;
  v_vsqlinsert:= 'insert  into t_r_down_sort_new(content_id,os_id,sort_num,down_count)
select /*+ parallel(v,4) */ v.contentid,3,' || v_nsortmaxi  ||
               '+ 1,0  from v_cm_content_Snapshot v
where v.os_id=3 and  not exists (select 1 from t_r_down_sort_new n where n.content_id=v.contentid and n.os_id=3)';
execute immediate v_vsqlinsert;

    --插入一条android数据
  v_vsqlinsert:= 'insert  into t_r_down_sort_new(content_id,os_id,sort_num,down_count)
select /*+ parallel(v_cm_content,4) */ v.contentid,9,' || v_nsortmaxa  ||
               '+ 1,0   from v_cm_content_Snapshot v
where v.os_id=3 and  not exists (select  1 from t_r_down_sort_new n where n.content_id=v.contentid and n.os_id=9)';

execute immediate v_vsqlinsert;
  commit;

  /*  --报表本次提供数据，前次没有数据，补充前次数据
  v_vsqlstr:='insert into t_r_down_sort_old
    (content_id, os_id, sort_num)
    select n.content_id,n.os_id,decode(n.os_id,9,'||v_nsortmaxa||',3,'||v_nsortmaxi||')
      from t_r_down_sort_new n
     where not exists (select 1
              from t_r_down_sort_old o
             where n.content_id = o.content_id
               and n.os_id = n.os_id)';
     execute immediate   v_vsqlstr;*/
     select count(9) into v_nindnum from user_indexes a where a.INDEX_NAME= 'IND_DOWNSORTO_CONTENTID';
     if v_nindnum=0 then  
  execute  immediate   ' create index ind_downsorto_contentid on   t_r_down_sort_old(content_id) parallel';
  end if;
   insert /*+ append parallel(t_r_down_sort,4) */ into t_r_down_sort
    (content_id, os_id, sort_num_new, sort_num_old, minus_sort_null,minus_down_count,add_order_count)
select n.content_id,
           n.os_id,
           n.sort_num,
           o.sort_num,
           o.sort_num-n.sort_num,
           n.down_count - o.down_count,
           n.add_order_count

      from t_r_down_sort_old o, t_r_down_sort_new n
     where o.content_id = n.content_id
       and o.os_id = n.os_id;
  commit;
 execute  immediate ' drop index ind_downsort_contentid';
 execute  immediate ' drop index ind_downsorto_contentid';


  --将本次数据变为前次数据
  v_vsqlalter := 'alter table t_r_down_sort_old rename to t_r_down_sort_old1 ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_new rename to t_r_down_sort_old ';
  execute immediate v_vsqlalter;

  v_vsqlalter := 'alter table t_r_down_sort_old1 rename to t_r_down_sort_new ';
  execute immediate v_vsqlalter;

end;

---------------------------------------------------------------------------------------
---------------存储过程完毕------------------------------------------------------------------------
---------------------------------------------------------------------------------------

-- Create table
create table V_CONTENT_LAST
(
  CONTENTID  VARCHAR2(30),
  OSID       VARCHAR2(20),
  CREATETIME DATE,
  UPDATETIME DATE
);

-- Add comments to the columns 
comment on column V_CONTENT_LAST.CONTENTID
  is '内容ID';
comment on column V_CONTENT_LAST.OSID
  is '操作系统ID';
comment on column V_CONTENT_LAST.CREATETIME
  is '产品的创建时间';
comment on column V_CONTENT_LAST.UPDATETIME
  is '产品的更新时间';
  
  
  

insert into  t_caterule_cond_base values(
19,
'从产品库中获得最新',
'select b.id from t_r_base b, t_r_gcontent g, v_content_last l
 where b.id = g.id
   and b.type like ''nt:gcontent:app%''
   and g.provider != ''B''
   and (g.subtype is null or
       g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))    
   and g.contentid = l.contentid'
);


insert into  t_caterule_cond_base values(
20,
'从产品库中获得免费、付费榜单_android',
'select b.id
  from t_r_base             b,
       t_r_gcontent         g,
       v_service            v,
       t_r_servenday_temp_a c,
       v_content_last    l
 where l.contentid = g.contentid
   and b.id = g.id
   and b.type like ''nt:gcontent:app%''
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = c.CONTENT_ID
   and g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10'')
   and l.osid = ''9'''
);


insert into  t_caterule_cond_base values(
21,
'从产品库中获得免费、付费榜单_ophone',
'select b.id
  from t_r_base             b,
       t_r_gcontent         g,
       v_service            v,
       t_r_servenday_temp_o c,
       v_content_last    l
 where l.contentid = g.contentid
   and b.id = g.id
   and b.type like ''nt:gcontent:app%''
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = c.CONTENT_ID
   and g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10'')
   and l.osid = ''3'''
);


create or replace view t_r_servenday_temp_a as
select "OS_ID","CONTENT_ID",t.down_count as ADD_7DAYS_DOWN_COUNT, t.add_order_count from 
t_r_down_sort_old t where t.os_id=9;

create or replace view t_r_servenday_temp_o as
select "OS_ID","CONTENT_ID",t.down_count as ADD_7DAYS_DOWN_COUNT, t.add_order_count from 
t_r_down_sort_old t where t.os_id=3;


-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_program
  for MM_PPMS.v_cm_content_program;


----创建存储过程
-----------以下为存储过程，需要一起执行-------------------  
create or replace procedure p_content_last as
  v_sql_f varchar2(1200);
begin

  --清空结果历史表数据
  execute immediate 'truncate table V_CONTENT_LAST';

  v_sql_f := 'insert into V_CONTENT_LAST 
          select t.contentid,
          t.platform as osid,
          t.prosubmitdate as CREATETIME,
          t. prolupddate as UPDATETIME
          from PPMS_V_CM_CONTENT_program t';

  execute immediate v_sql_f;

  commit;
exception
  when others then
    rollback;
end;
----------------存储过程结束-------------

-----------------以下job要在command下执行------
variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'p_content_last;',
                      next_date => to_date('28-12-2011 00:30:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 00:30:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/
-----------------------


--规则、货架规则参考脚本开始（最新榜单、免费、收费、总排行参考）有意注销的，做测试和现网发布使用
--android
--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (299, '榜单最新_android', 7, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (299, '', 19, 'l.osid=9 and updatetime>sysdate-1', 'trunc(l.createtime),decode(catename,''软件'',1,''游戏'',1,''主题'',2,10) ,to_char(l.createtime,''hh24miss''),g.name ,dbms_random.value', -1, null,1419,19);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('218637127', 299, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


--ophone
--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (300, '榜单最新_ophone', 7, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (300, '', 19, 'l.osid=3  and updatetime>sysdate-1', 'trunc(l.createtime),decode(catename,''软件'',1,''游戏'',1,''主题'',2,10) ,to_char(l.createtime,''hh24miss''),g.name ,dbms_random.value', -1, null,1420,19);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('218637128', 300, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));




--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (301, '免费榜单_android', 0, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (301, '', 20, '((mobilePrice>1000 and type=''nt:gcontent:appTheme'' ) or mobilePrice=0) ', 'c.ADD_7DAYS_DOWN_COUNT desc,l.createtime desc,g.name,dbms_random.value', -1, null,1421,20);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('217896797', 301, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (302, '付费榜单_android', 0, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (302, '', 20, '((mobilePrice>0 and mobilePrice<=1000 and type=''nt:gcontent:appTheme'' ) or (type!=''nt:gcontent:appTheme'' and mobilePrice>0 ))', 'c.ADD_7DAYS_DOWN_COUNT desc,l.createtime desc,g.name,dbms_random.value', -1, null,1422,20);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('217896798', 302, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));



--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (303, '总榜单_android', 0, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (303, '', 20, '', 'c.ADD_ORDER_COUNT desc nulls last,l.createtime desc,g.name,dbms_random.value', -1, null,1423,20);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('248230768', 303, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));



--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (304, '免费榜单_ophone', 0, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (304, '', 21, '((mobilePrice>1000 and type=''nt:gcontent:appTheme'' ) or mobilePrice=0) ', 'c.ADD_7DAYS_DOWN_COUNT desc,l.createtime desc,g.name,dbms_random.value', -1, null,1424,21);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('217896800', 304, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (305, '付费榜单_ophone', 0, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (305, '', 21, '((mobilePrice>0 and mobilePrice<=1000 and type=''nt:gcontent:appTheme'' ) or (type!=''nt:gcontent:appTheme'' and mobilePrice>0 ))', 'c.ADD_7DAYS_DOWN_COUNT desc,l.createtime desc,g.name,dbms_random.value', -1, null,1425,21);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('217896801', 305, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


--insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
--values (306, '总榜单_ophone', 0, 0, 1, null, 0);

--insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID,ID,BASECONDID)
--values (306, '', 21, '', 'c.ADD_ORDER_COUNT desc nulls last,l.createtime desc,g.name,dbms_random.value', -1, null,1426,21);

--insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
--values ('248230769', 306, null, to_date('07-05-2010 22:33:52', 'dd-mm-yyyy hh24:mi:ss'));


--规则、货架规则参考脚本结束（最新榜单、免费、收费、总排行参考）






insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.115_SSMS','MM1.0.3.120_SSMS');
commit;
