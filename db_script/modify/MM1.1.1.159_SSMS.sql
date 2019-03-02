-- Create table
create table T_R_EXPORTSQL
(
  ID              NUMBER(5) not null,
  EXPORTSQL       VARCHAR2(2000) not null,
  EXPORTNAME      VARCHAR2(300) not null,
  EXPORTTYPE      VARCHAR2(3) not null,
  EXPORTPAGENUM   NUMBER(6) default 0,
  EXPORTTYPEOTHER VARCHAR2(10) not null,
  LASTTIME        DATE,
  EXPORTLINE      NUMBER(3) not null,
  FILENAME        VARCHAR2(300) not null,
  FILEPATH        VARCHAR2(300) not null,
  ENCODER         VARCHAR2(20),
  EXPORTBYAUTO    VARCHAR2(20) default 3,
  EXPORTBYUSER    VARCHAR2(20) default 1,
  EXECTIME        VARCHAR2(20)
);
-- Add comments to the columns 
comment on column T_R_EXPORTSQL.ID
  is '编号';
comment on column T_R_EXPORTSQL.EXPORTSQL
  is '导出用sql语句';
comment on column T_R_EXPORTSQL.EXPORTNAME
  is '导出任务名';
comment on column T_R_EXPORTSQL.EXPORTTYPE
  is '导出类型 1:csv 2:txt 3:excel';
comment on column T_R_EXPORTSQL.EXPORTPAGENUM
  is '导出内容分页缓存量 0:为不限定 最多999999';
comment on column T_R_EXPORTSQL.EXPORTTYPEOTHER
  is '如果EXPORTType类型为1,2此字段为数据分隔符。如果EXPORTType为3此字段为选择项1：97-03版，2：07版';
comment on column T_R_EXPORTSQL.LASTTIME
  is '最后执行时间';
comment on column T_R_EXPORTSQL.EXPORTLINE
  is '导出字段数';
comment on column T_R_EXPORTSQL.FILENAME
  is '导出生成文件名默认。后加时间';
comment on column T_R_EXPORTSQL.FILEPATH
  is '导出生成文件所在路径';
comment on column T_R_EXPORTSQL.ENCODER
  is '文件编码';
comment on column T_R_EXPORTSQL.EXPORTBYAUTO
  is '文件导出方式1:只能手动 2:手动自动都可以';
comment on column T_R_EXPORTSQL.EXPORTBYUSER
  is '文件导出角色1:系统导出 2:运维导出';
comment on column T_R_EXPORTSQL.EXECTIME
  is '上次执行用时';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_R_EXPORTSQL
  add constraint PK_EXPORTSQL_ID primary key (ID);



insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (7, 'select s.contentid,s.icpcode,s.spname,s.spshortname,s.icpservid,s.servname,s.servstatus,s.umflag,s.servtype,s.chargetype,s.paytype,' || chr(10) || 's.mobileprice,s.dotcardprice,s.chargedesc,s.providertype,s.servattr,s.servdesc,s.pksid,s.lupddate from V_SERVICE s order by s.contentid', '业务信息数据', '1', 50000, ',', null, 19, 'i_SVC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (6, 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,' || chr(10) || 'c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,' || chr(10) || 'c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,' || chr(10) || 'c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,' || chr(10) || 'c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,' || chr(10) || 'c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,' || chr(10) || 'c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'' from t_r_gcontent c where to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') and c.plupddate is not null and length(c.plupddate) = 19 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', t.contentid, '''', '''',  '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null, null, null, null, '''', null, '''', null, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), '''', '''',   to_clob(''''), '''', '''', null, '''', '''', ''D'' from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by id', '内容增量信息数据', '1', 50000, ',', null, 80, 'a_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (4, 'select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,' || chr(10) || 'c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,' || chr(10) || 'c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,' || chr(10) || 'c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,' || chr(10) || 'c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,' || chr(10) || 'c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,' || chr(10) || 'c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),'''' from t_r_gcontent c  order by c.id', '内容全量信息数据', '1', 50000, ',', to_date('21-03-2013 11:47:00', 'dd-mm-yyyy hh24:mi:ss'), 80, 'i_CTN_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '1', '1', '1893960');
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (10, 'select t.ID,t.CONTENTID,t.NAME,t.SPNAME,t.TYPE,t.DISCOUNT,t.DATESTART,t.DATEEND,t.TIMESTART,t.TIMEEND,t.LUPDATE,t.USERID,t.MOBILEPRICE,t.EXPPRICE,' || chr(10) || 't.ICPCODE,t.ISRECOMM from T_CONTENT_EXT t order by t.ID', '营销信息数据', '1', 50000, ',', null, 16, 'i_MKT_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (5, 'select t.id,t.refnodeid,t.sortid,t.goodsid,t.categoryid,t.loaddate,t.variation from T_R_REFERENCE t order by t.id', '商品信息数据', '1', 50000, ',', to_date('21-03-2013 15:33:00', 'dd-mm-yyyy hh24:mi:ss'), 7, 'i_GDS_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', '69749');
insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME)
values (3, 'select c.id,c.name,c.descs,c.sortid,c.ctype,c.categoryid,c.changedate,c.state,c.parentcategoryid,c.relation,c.picurl,c.statistic,' || chr(10) || 'c.devicecategory,c.platform,c.cityid,c.startdate,c.enddate,c.othernet from t_r_category c order by c.id', '' || chr(9) || '货架信息数据', '1', 50000, ',', null, 18, 'i_RSC-CTG_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null);


-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_PACKAGE
  for V_CM_CONTENT_PACKAGENAME@DL_PPMS_DEVICE;

  
  -- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_TB
  for V_CM_CONTENT_TB@DL_PPMS_DEVICE;


create table V_CM_CONTENT_PACKAGE as select * from PPMS_V_CM_CONTENT_PACKAGE where 1=2;
create table V_CM_CONTENT_PACKAGE_TRA as select * from PPMS_V_CM_CONTENT_PACKAGE where 1=2;
create table V_CM_CONTENT_TB as select * from PPMS_V_CM_CONTENT_TB where 1=2;
create table V_CM_CONTENT_TB_TRA as select * from PPMS_V_CM_CONTENT_TB where 1=2;


-- Create table
create table T_R_EXPORTSQL_POR
(
  ID          NUMBER(8) not null,
  NAME        VARCHAR2(300) not null,
  FROM_NAME   VARCHAR2(50) not null,
  TO_NAME     VARCHAR2(50) not null,
  TO_NAME_TRA VARCHAR2(50) not null,
  TO_NAME_BAK VARCHAR2(50) not null
) ;
-- Add comments to the columns 
comment on column T_R_EXPORTSQL_POR.ID
  is '序号';
comment on column T_R_EXPORTSQL_POR.NAME
  is '同步说明';
comment on column T_R_EXPORTSQL_POR.FROM_NAME
  is '从电子流过来的表或视图名';
comment on column T_R_EXPORTSQL_POR.TO_NAME
  is '存放到本地的表名';
comment on column T_R_EXPORTSQL_POR.TO_NAME_TRA
  is '存放到本地的临时表';
comment on column T_R_EXPORTSQL_POR.TO_NAME_BAK
  is '存放到本地的历史表';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_R_EXPORTSQL_POR
  add constraint PK_EXPORTSQL_POR_ID primary key (ID) ;


insert into T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK)
values (1, '同步应用PACKAGE信息', 'PPMS_V_CM_CONTENT_PACKAGE', 'V_CM_CONTENT_PACKAGE', 'V_CM_CONTENT_PACKAGE_tra', 'V_CM_CONTENT_PACKAGE_bak');
insert into T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK)
values (2, '同步应用内计费信息', 'PPMS_V_CM_CONTENT_TB', 'V_CM_CONTENT_TB', 'V_CM_CONTENT_TB_tra', 'V_CM_CONTENT_TB_bak');



----------存储过程
create or replace procedure P_EXPORTSQL as
  v_nstatus number; --纪录监控包状态
  v_nindnum number; --记录数据是否存在
  v_nrecod  number;

  v_sql_trun  varchar2(1200);
  v_sql_exe   varchar2(1200);
  v_sql_count varchar2(1200);

  v_sql_from   VARCHAR2(50);
  v_sql_to     VARCHAR2(50);
  v_sql_to_tra VARCHAR2(50);
  v_sql_bak    VARCHAR2(50);

  cur_res t_r_exportsql_por%Rowtype;
  CURSOR cur IS
    SELECT * FROM t_r_exportsql_por;
begin

  v_nstatus := pg_log_manage.f_startlog('P_EXPORTSQL',
                                        '用于导出统一控制的所有创建视图');
  for cur_res in cur LOOP
    v_sql_from   := cur_res.from_name;
    v_sql_to     := cur_res.to_name;
    v_sql_to_tra := cur_res.to_name_tra;
    v_sql_bak    := cur_res.to_name_bak;

    --清空结果历史表数据
    v_sql_trun := 'truncate table ' || v_sql_to_tra;
    DBMS_OUTPUT.put_line(v_sql_trun);
    execute immediate v_sql_trun;

    v_sql_exe := 'insert into ' || v_sql_to_tra || ' select * from ' ||
                 v_sql_from;
    DBMS_OUTPUT.put_line(v_sql_exe);
    execute immediate v_sql_exe;

    v_nrecod := SQL%ROWCOUNT;
    v_sql_count := 'select count(9) from '||v_sql_to_tra;
    execute immediate v_sql_count into v_nindnum;
    DBMS_OUTPUT.put_line(v_nindnum);

    if v_nindnum > 0 then
      --如果不为空，将切换表
      execute immediate 'alter table ' || v_sql_to || ' rename to ' ||
                        v_sql_bak;
      execute immediate 'alter table ' || v_sql_to_tra || ' rename to ' ||
                        v_sql_to;
      execute immediate 'alter table ' || v_sql_bak || ' rename to ' ||
                        v_sql_to_tra;
      commit;
    else
      DBMS_OUTPUT.put_line('电子流提供的混合数据为空');
      raise_application_error(-20088, '电子流提供的混合数据为空');
    end if;

  END LOOP;

  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);

exception

  when others then
    rollback;
    --如果失败，将执行情况写入日志
    DBMS_OUTPUT.put_line('出错了');
    v_nstatus := pg_log_manage.f_errorlog;
end;

------------------------存储过程结束



-----------------------JOB
variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'P_EXPORTSQL;',
                      next_date => to_date('25-03-2013 01:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 23:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.155_SSMS','MM1.1.1.159_SSMS');


commit;