alter table t_game_tw_new add othernet number(2);

comment on column T_GAME_TW_NEW.OTHERNET
  is '接入方式(	0:其他
		1:CMWAP
		2:CMNET
		3:CMWAP,CMNET)';

alter table t_game_tw_new add scale varchar2(10);
comment on column t_game_tw_new.scale
  is '分成比例';


alter table t_gamestop add  mmprovinceid varchar2(4);
alter table t_game_tw_new add  countFlag varchar2(2);



--货架提供给报表：给货架设定SORTID开始
create or replace view  vr_mo_app_category  as 
select c.categoryid from t_r_category  c where c.parentcategoryid in('100000008','100000009','100000010');

create table V_CATEGORY_SORTID as select * from V_CATEGORY_ORDER_STAT_D@REPORT105.ORACLE.COM where 1=2;
create table V_CATEGORY_SORTID_TRA as select * from V_CATEGORY_ORDER_STAT_D@REPORT105.ORACLE.COM where 1=2;




--给货架的某些父货架设定SORTID序号，TMD都一个月运行这东西一次，有用吗？
create or replace procedure p_category_sortid as
  v_sql_f varchar2(1200);
   v_nindnum    number;--记录数据是否存在
  v_nstatus     number;
  v_nrecod      number;
  registCategory varchar2(128);
  startIndex   number;
  endIndex number;
  arrStr varchar2(50);
begin

  
  v_nstatus := pg_log_manage.f_startlog('p_category_sortid',
                                        '更新货架SORTID');
                                        
  registCategory :='100000008|100000009|100000010';
  execute immediate 'truncate table V_CATEGORY_SORTID_TRA';
  --清空结果历史表数据

  insert into V_CATEGORY_SORTID_TRA select * from V_CATEGORY_ORDER_STAT_D@REPORT105.ORACLE.COM t  where  stat_time=(select max(stat_time) from V_CATEGORY_ORDER_STAT_D@REPORT105.ORACLE.COM);
v_nrecod:=SQL%ROWCOUNT;
  --execute immediate v_sql_f;
select count(9) into v_nindnum  from V_CATEGORY_SORTID_TRA;

    if v_nindnum>0 then
  execute  immediate   'alter table V_CATEGORY_SORTID rename to V_CATEGORY_SORTID_BAK';
  execute  immediate   'alter table V_CATEGORY_SORTID_TRA rename to V_CATEGORY_SORTID';
  execute  immediate   'alter table V_CATEGORY_SORTID_BAK rename to V_CATEGORY_SORTID_TRA';
   --如果成功，将执行情况写入日志

  commit; 
  else
     raise_application_error(-20088,'电子流提供数据为空');
  end if;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
   
   --dbms_output.put_line('hhe..');
   
   startIndex:=1;
   loop
        endIndex:=instr(registCategory,'|',startIndex);
        if endIndex=0  then
        arrStr:= substr(registCategory,startIndex);
        else 
        arrStr:=substr(registCategory,startIndex,endIndex-startIndex);
        end if;
        
       -- dbms_output.put_line(arrStr);
        
           update t_r_category c  set c.sortid= nvl((
   select tt.num*10 
   
   from (select t.categoryid ,rownum as num from (select a.categoryid,rownum from 
            (select categoryid from t_r_category c where c.parentcategoryid=arrStr) a,
 
 V_CATEGORY_SORTID b 
 
 where a.categoryid = b.category_id order by  b.add_30day_order_count asc) t)tt
where tt.categoryid=c.categoryid

),0) where c.parentcategoryid=arrStr;
        
        if endIndex=0 then 
        exit;
        end if;
        
        startIndex :=endIndex+1;


   
  


end loop;
  commit; 
   
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;

----------------------------------------job定时任务 add by aiyan 2012-12-15

---若手动执行一下p_category_sortid存储过程，建议把T_R_CATEGORY备份一下。这个存储过程是想把"终端应用"货架下的子孙货架们的sortid按照
--报表提供的V_CATEGORY_ORDER_STAT_D@REPORT105.ORACLE.COM视图更新一下。为安全，建议备份T_R_CATEGORY。
variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'p_category_sortid;',
                      next_date => to_date('30-11-2012 23:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 23:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/
--给货架设定SORTID结束

-----------基地阅读
-- Add/modify columns 
alter table T_RB_CATE modify CATEDESC null;
alter table T_RB_CATE modify CATEPIC null;
alter table T_RB_CATE modify BUSINESSTIME null;


------------渠道运营应用
-- Add/modify columns 
alter table T_R_OPENCRTE_MAP add RULEID VARCHAR2(8);
-- Add comments to the columns 
comment on column T_R_OPENCRTE_MAP.RULEID
  is '对应的规则id';

update t_caterule_cond_base b
   set b.base_sql = 'select b.id
  from t_r_base b,
       t_r_gcontent g,
       V_CM_OPEN_CONTEN s,
       V_CM_OPEN_CHANNEL o,
       T_R_OPENCRTE_MAP m,
       t_r_category c
 where b.id = g.id
   and m.categoryid = c.id
   and m.openchannelcode = o.openchannelcode
   and o.channelid = s.channelid
   and g.contentid = s.contentid
   and (g.subtype is null or
       g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))'
 where b.base_id = '41';


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.109_SSMS','MM1.1.1.115_SSMS');


commit;