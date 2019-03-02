--------------------------
--------------------------

-- Create table
create table t_ap_whitelist
(
  companyid  varchar2(20),
  failtime    date,
  createdate date default sysdate
)
;
-- Add comments to the columns 
comment on column t_ap_whitelist.companyid
  is '企业ID';
comment on column t_ap_whitelist.failtime
  is 'AP白名单失效时间';
comment on column t_ap_whitelist.createdate
  is '创建时间';
  




---------------------------------------
---------------------------------------


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM3.0.0.0.055_SSMS','MM3.0.0.0.059_SSMS');



-----配置汇聚文件导出任务开始-----
------以下数据请根据实际情况填写----
-----1、向t_r_exportsql_ftp表中插入数据如下：-----
------ftpip：fip的ip地址，
------ftpport：端口号，
------ftpname：用户名，
------ftpkey：密码，
------ftppath：路径---
------ftpid：根据数据库已有的数据，填写一个不存在的数字，不能重复-----
insert into t_r_exportsql_ftp(ftpip,ftpport,ftpname,ftpkey,ftppath,ftpid)
 values('10.1.3.201','21','max','1qazZAQ!','temp/ssms','3');

-----2、向t_r_exportsql_group表中插入数据如下：-----
------groupid：分组任务id，根据数据库已有的数据，填写一个在数据库中不存在的id，不能重复，-------
------tomail：当前分组任务发送邮件地址，
------mailtitle：当前分组任务发送邮件标题，
------starttime：当前分组任务执行时间，
------timetype：执行时间类型 1:每天 2:每周，
------ftpid：t_r_exportsql_ftp表的ftpid一致---
------每天2次（时间可配置，初步为：07:00和16:00）每次全量同步，根据不同时间创建两个分组
-------创建一条时间点为：0700的分组-----
insert into t_r_exportsql_group(groupid,tomail,mailtitle,starttime,timetype,ftpid) 
    values(6,'dongke@aspirecn.com,baojun@aspirecn.com','汇聚文件导出任务1','0700','1','3');
-------创建一条时间点为：1600的分组-----
insert into t_r_exportsql_group(groupid,tomail,mailtitle,starttime,timetype,ftpid) 
    values(7,'dongke@aspirecn.com,baojun@aspirecn.com','汇聚文件导出任务2','1600','1','3');

-----3、向t_r_exportsql表中插入数据如下：-----
------id：编号id，根据数据库已有的数据，填写一个在数据库中不存在的id，不能重复，-------
------exportsql：导出用sql语句，
------exportname：导出任务名，
------exporttype：导出文件类型 1:csv 2:txt 3:excel，
------exportpagenum：导出内容分页缓存量 0:为不限定 最多999999，
------exporttypeother：如果EXPORTType类型为1,2此字段为数据分隔符。如果EXPORTType为3此字段为选择项1：97-03版，2：07版，
------exportline：导出字段数---
------filename：导出生成文件名默认。后加时间，
------filepath：导出生成文件所在路径---
------encoder：文件编码9，
------exportbyauto：文件导出方式1:只能手动 2:手动自动都可以---
------groupid：t_r_exportsql_group表的groupid一致---

------创建一条时间点为：0700的分组的最新上线的安卓平台应用数据导出数据-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(36,'select g.contentid,g.name,g.spname,g.catename,g.appcatename,substr(g.marketdate,1,10) as mupdate,s.mobileprice,decode(g.chargetime,''2'',''1'',''0'') as IapFlag,decode(g.servattr, ''G'', ''1'',''L'',''0'', ''1'') as servattrScope,g.subtype from t_r_gcontent g,v_a_service s,V_DATACENTER_CM_CONTENT c where s.contentid = g.contentid and c.contentid = g.contentid','最新上线的安卓平台应用数据','2',50000,'0x01',10,'huojia2huijuApp_%YYYYMMDDHH24%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','GB18030','2','6');

------创建一条时间点为：0700的分组的最新上线的安卓平台应用程序包数据导出数据-----
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(37,'select distinct g.contentid,g.pid,g.versionname,substr(g.pkgname,1,150) as Packagename,g.version from t_a_cm_device_resource g,t_r_gcontent c where g.contentid = c.contentid','最新上线的安卓平台应用程序包数据','2',50000,'0x01',5,'huojia2huijuPack_%YYYYMMDDHH24%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','GB18030','2','6');

------创建一条时间点为：1600的分组的最新上线的安卓平台应用数据导出数据-----  
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(38,'select g.contentid,g.name,g.spname,g.catename,g.appcatename,substr(g.marketdate,1,10) as mupdate,s.mobileprice,decode(g.chargetime,''2'',''1'',''0'') as IapFlag,decode(g.servattr, ''G'', ''1'',''L'',''0'', ''1'') as servattrScope,g.subtype from t_r_gcontent g,v_a_service s,V_DATACENTER_CM_CONTENT c where s.contentid = g.contentid and c.contentid = g.contentid','最新上线的安卓平台应用数据','2',50000,'0x01',10,'huojia2huijuApp_%YYYYMMDDHH24%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','GB18030','2','7');

------创建一条时间点为：1600的分组的最新上线的安卓平台应用程序包数据导出数据-----   
insert into t_r_exportsql(id,exportsql,exportname,exporttype,exportpagenum,exporttypeother,exportline,filename,filepath,encoder,exportbyauto,groupid) 
   values(39,'select distinct g.contentid,g.pid,g.versionname,substr(g.pkgname,1,150) as Packagename,g.version from t_a_cm_device_resource g,t_r_gcontent c where g.contentid = c.contentid','最新上线的安卓平台应用程序包数据','2',50000,'0x01',5,'huojia2huijuPack_%YYYYMMDDHH24%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','GB18030','2','7');

-----配置汇聚文件导出任务结束-----  


-- Add/modify columns 
alter table T_R_CATEGORY modify sortid NUMBER(6);
-- Alter sequence
alter sequence SEQ_CATEGORY_SORT_ID 
maxvalue 999999; 

--该存储过程用来调用P_mt_rank_index_fin_game,P_mt_rank_index_fin_software--
CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin
as
begin
P_mt_rank_index_fin_game;
P_mt_rank_index_fin_software;
end;

CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin_game
as
     z NUMBER(12,6);
     z1 NUMBER(12,6);
     z2 NUMBER(12,6);
     b NUMBER(12,6);
     d1 NUMBER(12,6);
     d2 NUMBER(12,6);
     d3 NUMBER(12,6);
     m1 NUMBER(12,6);
     m2 NUMBER(12,6);
     m1max NUMBER(12,6);
     m1min NUMBER(12,6);
     m2max NUMBER(12,6);
     m2min NUMBER(12,6);
     a NUMBER(12,6);
     j NUMBER(12,6);

  v_nstatus number;
    v_nrecod  number;

cursor s_cur is
        select * from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID='1003';
begin
 v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','新榜单计算P_mt_rank_index_fin');
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
    from t_mt_rank_index_d where DL_15DAYS_CNT>50
    group by PRD_TYPE_ID
    )l
    where r1.dl_15days_cnt>50
    and r1.prd_type_id=l.PRD_TYPE_ID)o group by PRD_TYPE_ID;


  execute immediate'TRUNCATE TABLE MID_TABLE';
  --delete  from MID_TABLE where PRD_TYPE_ID='1003';
 for r in s_cur loop

 select  PRD_TYPE_ID,b,m1max,m1min ,m2max,m2min
into j,b,m1max,m1min,m2max,m2min
 from MID_O_TABLE where PRD_TYPE_ID=r.PRD_TYPE_ID;

    select (r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT))/b
    *ln(b),r.fee/sum(fee)*ln(sum(fee)),(r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT)),
     (r.CLASS_DL_CNT1*0.15+r.SEARCH_DL_CNT1*0.4+r.HOT_DL_CNT1*0.25+r.MAN_DL_CNT1*0.2)*exp(r.COM_CNT1/sum(COM_CNT1)),
     (r.CLASS_DL_CNT2*0.15+r.SEARCH_DL_CNT2*0.4+r.HOT_DL_CNT2*0.25+r.MAN_DL_CNT2*0.2)*exp(r.COM_CNT2/sum(COM_CNT2))
    into  z1,z2,d3,d2,d1 from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID=r.PRD_TYPE_ID;
     z:=z2*0.35+z1*0.65;
     m1:=(d1+d2+d3)/(r.ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(r.ADD_DL_CNT*0.2+d1+d2+d3+1);
     m2:=((r.fee+r.fee1+r.fee2)/(r.add_fee+1))*ln(r.add_fee+1);
    insert into MID_TABLE values(r.content_id,z,((m1-m1min) / (m1max-m1min))*0.7+((m2-m2min) / (m2max-m2min))*0.3);
    end loop;
    commit;
     v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    exception
    when others then
    rollback;
     v_nstatus := pg_log_manage.f_errorlog;
end;


CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin_software
as
     z NUMBER(12,6);
     z1 NUMBER(12,6);
     z2 NUMBER(12,6);
     b NUMBER(12,6);
     d1 NUMBER(12,6);
     d2 NUMBER(12,6);
     d3 NUMBER(12,6);
     m1 NUMBER(12,6);
     m2 NUMBER(12,6);
     m1max NUMBER(12,6);
     m1min NUMBER(12,6);
     m2max NUMBER(12,6);
     m2min NUMBER(12,6);
     a NUMBER(12,6);
     j NUMBER(12,6);

  v_nstatus number;
    v_nrecod  number;

cursor s_cur is
        select * from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID='1001';
begin
 v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','新榜单计算P_mt_rank_index_fin');
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
    from t_mt_rank_index_d where DL_15DAYS_CNT>50
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
    into  z1,z2,d3,d2,d1 from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID=r.PRD_TYPE_ID;
     z:=z2*0.05+z1*0.95;
     m1:=(d1+d2+d3)/(r.ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(r.ADD_DL_CNT*0.2+d1+d2+d3+1);
     m2:=((r.fee+r.fee1+r.fee2)/(r.add_fee+1))*ln(r.add_fee+1);
    insert into MID_TABLE values(r.content_id,z,((m1-m1min) / (m1max-m1min))*0.7+((m2-m2min) / (m2max-m2min))*0.3);
    end loop;
    commit;
     v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    exception
    when others then
    rollback;
     v_nstatus := pg_log_manage.f_errorlog;
end;


commit;


------

