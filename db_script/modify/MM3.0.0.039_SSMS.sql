alter table T_VO_NODE_MID add collectflag number(2);
comment on column T_VO_NODE_MID.collectflag
  is '内容集标识， 1 是 2 否 是否内容集';

 alter table T_VO_NODE add collectflag number(2);
 comment on column T_VO_NODE.collectflag
  is '内容集标识 ，1 是 2 否 是否内容集';
  
  -- Create table视频内容集节点表
create table t_vo_collect_node
(
  nodeid       VARCHAR2(60) not null,
  parentnodeid VARCHAR2(60) not null,
  nodetype     varchar2(60) not null,
  nodebyname   varchar2(4000) not null,
  sortid       NUMBER(19),
  nodename     VARCHAR2(128),
  description  VARCHAR2(4000),
  logopath     VARCHAR2(512),
  productid    VARCHAR2(1024),
  collectflag number(2),
  exporttime   DATE default sysdate not null
)
;

-- Add comments to the columns 
comment on column t_vo_collect_node.nodeid
  is '节点标识，对应栏目标识';
comment on column t_vo_collect_node.parentnodeid
  is '父节点标识，顶级节点为-1';
comment on column t_vo_collect_node.nodetype
  is '类型，一级栏目标识';
comment on column t_vo_collect_node.nodebyname
  is '别名，栏目名称';
comment on column t_vo_collect_node.sortid
  is '排序序号';
comment on column t_vo_collect_node.nodename
  is '栏目中文名称';
comment on column t_vo_collect_node.description
  is '栏目介绍';
comment on column t_vo_collect_node.logopath
  is '图片路径';
comment on column t_vo_collect_node.productid
  is '产品标识';
comment on column t_vo_collect_node.collectflag
  is '内容集标识 ，1 是 2 否 是否内容集';
comment on column t_vo_collect_node.exporttime
  is '导入时间';
 
  -- Create table视频内容集表
create table t_vo_collect
(
  collectid     VARCHAR2(60) not null,
  collectname   VARCHAR2(128) not null,
  description  VARCHAR2(4000) not null,
  sortid       NUMBER(10),
  lookflag     number(2),
  property     varchar2(1024),
  image        VARCHAR2(4000),
  exporttime   DATE default sysdate not null
)
;

-- Add comments to the columns 
comment on column t_vo_collect.collectid
  is '内容集标识，对应栏目标识';
comment on column t_vo_collect.collectname
  is '内容集名称';
comment on column t_vo_collect.description
  is '介绍';
comment on column t_vo_collect.sortid
  is '排序序号';
comment on column t_vo_collect.lookflag
  is '查看方式：1:列表式导航，2:标签式导航，3:评书式导航，4:排行榜导航，5:矩阵式内容集，6:列表式内容集，7:音频式内容集，8 图片式内容集';
comment on column t_vo_collect.property
  is '属性';
comment on column t_vo_collect.image
  is '图片';
comment on column t_vo_collect.exporttime
  is '导入时间';

alter table t_vo_collect add property1 varchar2(128);
comment on column t_vo_collect.property1
  is '属性1';
alter table t_vo_collect add property2 varchar2(128);
comment on column t_vo_collect.property2
  is '属性2';
alter table t_vo_collect add property3 varchar2(128);
comment on column t_vo_collect.property3
  is '属性3';
  alter table t_vo_collect add property4 varchar2(128);
comment on column t_vo_collect.property4
  is '属性4';
  alter table t_vo_collect add property5 varchar2(128);
comment on column t_vo_collect.property5
  is '属性5';
alter table t_vo_collect add property6 varchar2(128);
comment on column t_vo_collect.property6
  is '属性6';
alter table t_vo_collect add property7 varchar2(128);
comment on column t_vo_collect.property7
  is '属性7';
alter table t_vo_collect add image1 varchar2(512);
comment on column T_VO_COLLECT.image1
  is '图片1,210 x 141命名规则：http://......名称_VHDPI.JPG';
alter table t_vo_collect add image2 varchar2(512);
comment on column T_VO_COLLECT.image2
  is '图片2,140 x94命名规则：http://......名称_VMDPI.JPG';
alter table t_vo_collect add image3 varchar2(512);
comment on column T_VO_COLLECT.image3
  is '图片3,93 x 63命名规则：http://......名称_VSDPI.JPG';
alter table t_vo_collect add image4 varchar2(512);
comment on column T_VO_COLLECT.image4
  is '图片4,162 x 231命名规则：http://......名称_HHDPI.JPG';
alter table t_vo_collect add image5 varchar2(512);
comment on column T_VO_COLLECT.image5
  is '图片5,108 x 154命名规则：http://......名称_HMDPI.JPG';
alter table t_vo_collect add image6 varchar2(512);
comment on column T_VO_COLLECT.image6
  is '图片6,72 x 103命名规则：http://......名称_HSDPI.JPG';
  

create or replace procedure p_delete_insert as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_delete_insert',
                                        '增量同步视屏相关数据');
delete T_VO_LIVE a
where exists (
select 1 from T_VO_LIVE_MID b where a.nodeid=b.nodeid and a.programid=b.programid
and a.starttime=b.starttime);
insert into T_VO_LIVE (NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME)
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME from (
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME,
       row_number()over(partition by a.programid, starttime order by LIVENAME desc) sort
  from T_VO_LIVE_MID a
where status in ('A', 'U')
)where sort=1
;


delete T_VO_NODE c
where exists(select 1 from T_VO_NODE_MID d where c.nodeid=d.nodeid);
insert into T_VO_NODE(NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME, COLLECTFLAG)
select NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME, COLLECTFLAG from (
select NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME, COLLECTFLAG,
       row_number()over(partition by a.nodeid order by NODENAME desc) sort
  from T_VO_NODE_MID a
where status in ('A', 'U')
)where sort=1
;


delete T_VO_PRODUCT e
where exists(select 1 from t_Vo_Product_Mid f where e.productid=f.productid and f.status = 'D');

MERGE INTO t_Vo_Product dd
USING (SELECT PRODUCTID,
              PRODUCTNAME,
              FEE,
              CPID,
              FEETYPE,
              STARTDATE,
              FEEDESC,
              FREETYPE,
              FREEEFFECTIME,
              FREETIMEFAIL
         from t_Vo_Product_Mid m
        where m.status in ('A', 'U')) s
ON (dd.productid = s.PRODUCTID)
WHEN MATCHED THEN
  UPDATE
     SET dd.productname   = s.productname,
         dd.fee           = s.fee,
         dd.cpid          = s.cpid,
         dd.feetype       = s.feetype,
         dd.startdate     = s.startdate,
         dd.freetype      = s.freetype,
         dd.freeeffectime = s.freeeffectime,
         dd.freetimefail  = s.freetimefail
WHEN NOT MATCHED THEN
  INSERT
    (PRODUCTID,
     PRODUCTNAME,
     FEE,
     CPID,
     FEETYPE,
     STARTDATE,
     FEEDESC,
     FREETYPE,
     FREEEFFECTIME,
     FREETIMEFAIL)
  VALUES
    (s.PRODUCTID,
     s.PRODUCTNAME,
     s.FEE,
     s.CPID,
     s.FEETYPE,
     s.STARTDATE,
     s.FEEDESC,
     s.FREETYPE,
     s.FREEEFFECTIME,
     s.FREETIMEFAIL);


delete T_VO_PROGRAM g
where exists (select 1 from T_VO_PROGRAM_MID h where g.programid=h.programid);
insert into T_VO_PROGRAM(PROGRAMID, VIDEOID, PROGRAMNAME, NODEID, DESCRIPTION, LOGOPATH,
 TIMELENGTH, SHOWTIME, LASTUPTIME, PROGRAMTYPE, EXPORTTIME, FTPLOGOPATH, TRUELOGOPATH)
select PROGRAMID,
       VIDEOID,
       PROGRAMNAME,
       NODEID,
       DESCRIPTION,
       LOGOPATH,
       TIMELENGTH,
       SHOWTIME,
       LASTUPTIME,
       PROGRAMTYPE,
       EXPORTTIME,
       FTPLOGOPATH,
       TRUELOGOPATH from (
select PROGRAMID,
       VIDEOID,
       PROGRAMNAME,
       NODEID,
       DESCRIPTION,
       LOGOPATH,
       TIMELENGTH,
       SHOWTIME,
       LASTUPTIME,
       PROGRAMTYPE,
       EXPORTTIME,
       FTPLOGOPATH,
       TRUELOGOPATH,
       row_number()over(partition by a.programid order by to_date(a.lastuptime,'yyyy-mm-dd hh24:mi:ss') desc) sort
  from T_VO_PROGRAM_MID a
where status in ('A', 'U')
)where sort=1
;

delete T_VO_VIDEO i
where exists(select 1 from T_VO_VIDEO_MID j where i.videoid=j.videoid and i.coderateid=j.coderateid);
insert into T_VO_VIDEO(VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, UPDATETIME)
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, UPDATETIME from (
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, UPDATETIME,
       row_number()over(partition by a.videoid,a.coderateid order by FILESIZE desc) sort
  from T_VO_VIDEO_MID a
where status in ('A', 'U')
)where sort=1
;

delete  T_VO_VIDEODETAIL k
where exists(select 1 from T_VO_VIDEODETAIL_MID l where k.programid=l.programid);
insert into T_VO_VIDEODETAIL(PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME)
select PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME from (
select PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME,
       row_number()over(partition by a.programid order by UPDATETIME desc) sort
  from T_VO_VIDEODETAIL_MID a
where status in ('A', 'U')
)where sort=1
;



delete T_VO_PROGRAM g
where not exists (select 1 from t_vo_video v where g.videoid=v.videoid);

delete t_Vo_Reference g
where not exists (select 1 from T_VO_PROGRAM h where g.programid=h.programid);


 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;


-- Add/modify columns 
alter table T_A_CM_DEVICE_RESOURCE add IsWhiteList number(5);
-- Add comments to the columns 
comment on column T_A_CM_DEVICE_RESOURCE.IsWhiteList
  is '是否白名单 0 否 1 是 如果为空则取0';


-- Create the synonym 应用下线删除应用升级信息同义词
create or replace synonym SEQ_CM_UPGRADE_DEL2_ID
  for SEQ_CM_UPGRADE_DEL2_ID@DL_MM_PPMS_NEW;
  
  create or replace synonym s_cm_ct_device_upgrade_del2
  for cm_ct_device_upgrade_del2@DL_MM_PPMS_NEW;
  
 ---------------------------------------------------------
 create table MID_TABLE
(
  appid    VARCHAR2(12) not null,
  hotlist  NUMBER(12,6),
  riselist NUMBER(12,6)
);
create table MID_O_TABLE
(
  PRD_TYPE_ID    VARCHAR2(12) not null,
  b  NUMBER(12,6),
  m1max NUMBER(12,6),
    m1min  NUMBER(12,6),
  m2max NUMBER(12,6),
    m2min  NUMBER(12,6)
);
CREATE OR REPLACE PROCEDURE P_mt_rank_index
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
  
cursor s_cur is
        select * from t_mt_rank_index_d where DL_15DAYS_CNT>50;   
begin
  
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
    exception
    when others then
    rollback;
end;



commit;
