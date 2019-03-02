

-- Create table
create table T_MSTORE_CATEGORY
(
  CATEGORYID VARCHAR2(30),
  NAME       VARCHAR2(100)
);
-- Add comments to the columns 
comment on column T_MSTORE_CATEGORY.CATEGORYID
  is '货架categoryId';
comment on column T_MSTORE_CATEGORY.NAME
  is '货架名称';


-- Add/modify columns 
alter table T_MB_MUSIC_NEW add PRODUCTMASK varchar2(25);
-- Add comments to the columns 
comment on column T_MB_MUSIC_NEW.PRODUCTMASK
  is '四位数，如‘1101’。四个数依次代表“在线听歌”、“彩铃”、“振铃”、“全曲” 0：没操作版权 1：有操作版权';

-- Add/modify columns 
alter table T_KEY_RESOURCE modify VALUE VARCHAR2(4000);
-- Add comments to the columns 
comment on column T_KEY_BASE.KEYTYPE
  is '域类型，1，普通文本；2，文件域;3，大文本域';
  

-- Add/modify columns 
alter table T_R_CATEGORY add startdate VARCHAR2(30);
alter table T_R_CATEGORY add enddate VARCHAR2(30) default 2099-01-01;
-- Add comments to the columns 
comment on column T_R_CATEGORY.startdate
  is '货架开始生效时间';
comment on column T_R_CATEGORY.enddate
  is '货架结束失效时间';
  update  t_r_category t set t.startdate='2011-10-18',t.enddate='2099-01-01';
  
  
insert into t_mstore_category (CATEGORYID, NAME)
values ('100000441', '月排行');

insert into t_mstore_category (CATEGORYID, NAME)
values ('100000438', '推荐');

insert into t_mstore_category (CATEGORYID, NAME)
values ('100000436', '免费');


--------*******************-------------------------------
----2011-10-15上线的脚本---------------------------------
----------------------------------------

-- Add/modify columns 
alter table T_CY_PRODUCTLIST add totalDOWNLOADUSERNUM NUMBER(12);
alter table T_CY_PRODUCTLIST add totalTESTUSERNUM NUMBER(12);
alter table T_CY_PRODUCTLIST add totalTESTSTAR NUMBER(12);
alter table T_CY_PRODUCTLIST add totalSTARSCORECOUNT NUMBER(12);
alter table T_CY_PRODUCTLIST add totalGLOBALSCORECOUNT NUMBER(12);
-- Add comments to the columns 
comment on column T_CY_PRODUCTLIST.DOWNLOADUSERNUM
  is '月下载用户数';
comment on column T_CY_PRODUCTLIST.TESTUSERNUM
  is '月测评用户数';
comment on column T_CY_PRODUCTLIST.TESTSTAR
  is '月测评星级';
comment on column T_CY_PRODUCTLIST.STARSCORECOUNT
  is '月星探推荐得分';
comment on column T_CY_PRODUCTLIST.GLOBALSCORECOUNT
  is '月人气综合推荐指数';
comment on column T_CY_PRODUCTLIST.totalDOWNLOADUSERNUM
  is '累计下载用户数';
comment on column T_CY_PRODUCTLIST.totalTESTUSERNUM
  is '累计测评用户数';
comment on column T_CY_PRODUCTLIST.totalTESTSTAR
  is '累计测评星级';
comment on column T_CY_PRODUCTLIST.totalSTARSCORECOUNT
  is '累计星探推荐得分';
comment on column T_CY_PRODUCTLIST.totalGLOBALSCORECOUNT
  is '累计人气综合推荐指数';

-- Create the synonym 
create or replace synonym s_report_cy_productlist
  for V_RG_CY2011_CATALOG_LIST_D@REPORT105.ORACLE.COM;

create or replace view v_report_cy_productlist as
select t.stat_time as STATTIME,
         t.content_id as contentid,
          t.content_name as  CONTENTNAME,
          t.hist_down_user_num as TOTALDOWNLOADUSERNUM,
          t.hist_test_user_num as TOTALTESTUSERNUM,
          t.hist_test_star as TOTALTESTSTAR,
          t.hist_star_recomm_point as TOTALSTARSCORECOUNT,
          t.hist_user_point_total as TOTALGLOBALSCORECOUNT,
          t.m_down_user_num as DOWNLOADUSERNUM,
          t.m_test_user_num as TESTUSERNUM,
          t.m_test_star as TESTSTAR,
          t.m_star_recomm_point as STARSCORECOUNT,
          t.m_user_point_total as GLOBALSCORECOUNT,
           t.down_user_num as dayDOWNLOADUSERNUM,
          t.test_user_num as dayTESTUSERNUM,
          t.test_star as dayTESTSTAR,
          t.star_recomm_point as daySTARSCORECOUNT,
          t.user_point_total as dayGLOBALSCORECOUNT,
          sysdate as UPDATETIME
      from s_report_cy_productlist t;
      
 ---------------------------------------------------------
 -----------以下为存储过程，需要一起执行-------------------     
      create or replace procedure p_refresh_cy_productlist as
  v_dsql        varchar2(1200); ---
 v_csql        varchar2(1200); ---
   v_status number;--日志返回
begin
   v_status:=pg_log_manage.f_startlog('p_refresh_cy_productlist','刷新创业大赛日统计报表数据' );

   v_dsql := 'truncate table       t_cy_productlist';
   v_csql := 'insert  /*+ append */ into t_cy_productlist  nologging select g.*
                  from  v_report_cy_productlist g ';
    execute immediate v_dsql;
    execute immediate v_csql;
    v_status:= pg_log_manage.f_successlog;

  commit;
exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
end ;
---end------------------------------------------------------
---------------------------------------------------------

------*******************----------------------------
----2011-10-15上线的脚本end--------------------
----------------------------------------        



-----------------------价格类型应用
-- Add/modify columns 
alter table T_R_GCONTENT add priceType VARCHAR2(2) default 0;
-- Add comments to the columns 
comment on column T_R_GCONTENT.priceType
  is '价格类型 现有类型0:正常 1:vip价格';

-- Create table
create table T_R_PRICETYPE
(
  ICPCODE   VARCHAR2(20) not null,
  COMPANYID VARCHAR2(20) not null,
  SPNAME    VARCHAR2(100) not null,
  PRICETYPE VARCHAR2(2) not null
)
;
-- Add comments to the columns 
comment on column T_R_PRICETYPE.ICPCODE
  is '企业代码';
comment on column T_R_PRICETYPE.COMPANYID
  is '企业内码';
comment on column T_R_PRICETYPE.SPNAME
  is '企业名称';
comment on column T_R_PRICETYPE.PRICETYPE
  is '价格类型 现有类型0:正常 1:vip价格';

alter table T_R_PRICETYPE
  add constraint T_KEY_PRICETYPE_PK primary key (ICPCODE, COMPANYID, SPNAME);

create or replace procedure p_priceType_content as
  v_sql_f varchar2(1200);
  v_sql_s varchar2(1200);
begin
  v_sql_f := 'update t_r_gcontent c
   set c.pricetype = (select p.pricetype
                        from t_r_pricetype p
                       where c.icpcode = p.icpcode
                         and c.companyid = p.companyid
                         and c.spname = p.spname)';
  v_sql_s := 'update t_r_gcontent c set c.pricetype=0 where c.pricetype is null';

  execute immediate v_sql_f;
  execute immediate v_sql_s;

  commit;
exception
  when others then
    rollback;
end;


--------以下job要在command下执行

variable job number;
begin
  sys.dbms_job.submit(job => :job,
                      what => 'p_priceType_content;',
                      next_date => to_date('19-10-2011 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/
-------

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.090_SSMS','MM1.0.3.095_SSMS');
commit;


