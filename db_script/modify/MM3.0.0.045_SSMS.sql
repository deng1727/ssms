create or replace view v_iap_monitor as
select  distinct r.refnodeid,d.DEVICE_ID,d.contentid,d.contentname,r.categoryid
from t_r_reference r,t_a_cm_device_resource d,t_r_gcontent g
where r.refnodeid = g.id and g.contentid = d.contentid ;

alter table t_vo_collect modify description varchar2(4000) null;
alter table t_vo_collect modify  property1 varchar2(400);
alter table t_vo_collect modify  property2 varchar2(400);
alter table t_vo_collect modify  property3 varchar2(400);
alter table t_vo_collect modify  property4 varchar2(400);
alter table t_vo_collect modify  property5 varchar2(400);
alter table t_vo_collect modify  property6 varchar2(400);
alter table t_vo_collect modify  property7 varchar2(400);

alter table t_vo_product_mid add productdesc varchar2(1024);
 comment on column t_vo_product_mid.productdesc
  is '产品描述';
  
alter table t_vo_product add productdesc varchar2(1024);
 comment on column t_vo_product.productdesc
  is '产品描述';

alter table t_vo_program_mid add sortid number(10);
 comment on column t_vo_program_mid.sortid
  is '排列序号';
alter table t_vo_program add sortid number(10);
 comment on column t_vo_program.sortid
  is '排列序号';
  
alter table t_vo_program_mid add islink varchar2(10);
 comment on column t_vo_program_mid.islink
  is '是否链接';
alter table t_vo_program add islink varchar2(10);
 comment on column t_vo_program.islink
  is '是否链接';

alter table t_vo_program_mid add productid varchar2(1024);
 comment on column t_vo_program_mid.productid
  is '产品标识';
alter table t_vo_program add productid varchar2(1024);
 comment on column t_vo_program.productid
  is '产品标识';


 ------更新p_delete_insert存储过程-----
----以下为p_delete_insert存储过程，需要一起执行------------
--------------------------------------------------------------
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
              FREETIMEFAIL,
              PRODUCTDESC
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
         dd.freetimefail  = s.freetimefail,
         dd.productdesc   = s.productdesc
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
     FREETIMEFAIL,
     PRODUCTDESC)
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
     s.FREETIMEFAIL,
     s.PRODUCTDESC);


delete T_VO_PROGRAM g
where exists (select 1 from T_VO_PROGRAM_MID h where g.programid=h.programid);
insert into T_VO_PROGRAM(PROGRAMID, VIDEOID, PROGRAMNAME, NODEID, DESCRIPTION, LOGOPATH,
 TIMELENGTH, SHOWTIME, LASTUPTIME, PROGRAMTYPE, EXPORTTIME, FTPLOGOPATH, TRUELOGOPATH,SORTID,ISLINK,PRODUCTID)
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
       SORTID,
       ISLINK,
       PRODUCTID from (
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
       SORTID,
       ISLINK,
       PRODUCTID,
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
----p_delete_insert存储过程结束------------
---------------------------------------


create table T_VO_COST
(
  productid   VARCHAR2(60),
  costid      VARCHAR2(60),
  depositrate VARCHAR2(10),
  createtime  DATE,
  updatetime  DATE
);
commit;


----www增加CDN地址和程序包白名单字段接口
update  t_r_exportsql t set t.exportsql='select t.pid,t.device_id,t.device_name,t.contentid,t.contentname,t.resourceid,t.id,t.programsize,t.createdate,t.prosubmitdate,t.match,t.version,t.versionname,t.permission,t.picture1,t.picture2,t.picture3,t.picture4,t.absolutepath,t.iscdn,CDNURL,ISWHITELIST from T_A_CM_DEVICE_RESOURCE t',t.exportline=22 where t.id='21';


----以下为P_rebuildRB_cateref存储过程，需要一起执行------------
--------------------------------------------------------------
create or replace procedure P_rebuildRB_cateref as
  v_nstatus number; --纪录监控包状态
  v_nindnum number; --记录数据是否存在
  v_nrecod  number;
  v_typeid    VARCHAR2(50);
  v_cid      VARCHAR2(50);
  v_categoryid  VARCHAR2(50);
  v_message varchar2(4000);
   
  CURSOR cur IS
    SELECT * FROM t_rb_type_count order by id asc;
begin
  v_nstatus := pg_log_manage.f_startlog('P_REBUILDRB_CATEREF',
                                       '用于重新上架图书分类货架商品');
  v_message := '';
  for cur_res in cur LOOP
    v_typeid    := cur_res.id;
    v_cid      := cur_res.cid;
    v_categoryid := cur_res.categoryid;
    delete from t_rb_reference_new r where r.cid=v_cid;
    insert into t_rb_reference_new (cid,categoryid,bookid,sortnumber,lupdate,province,city)
    select v_cid as cid,v_categoryid as categoryid,  t.bookid, rownum as sortnumber, sysdate as lupdate, ';000;' as province, ';000;' as city from t_rb_book_new t where t.delflag='0' and t.typeid in (select b.typeid from t_rb_type b where b.parentid =v_typeid);
   DBMS_OUTPUT.put_line('==================================================================================');
  END LOOP;
  if length(v_message)>1 then
    DBMS_OUTPUT.put_line(v_message || '电子流提供的混合数据为空');
    raise_application_error(-20088,
                            v_message || '电子流提供的混合数据为空');
  end if;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    DBMS_OUTPUT.put_line('出错了');
    v_nstatus := pg_log_manage.f_errorlog;
end;
----P_rebuildRB_cateref存储过程结束------------
---------------------------------------


