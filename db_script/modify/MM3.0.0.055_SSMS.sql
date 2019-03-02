--------------------------
--------------------------



create or replace procedure p_delete_insert as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_delete_insert',

                                        '增量同步视屏相关数据');
delete T_VO_LIVE a
where exists (
select 1 from T_VO_LIVE_MID b where a.nodeid=b.nodeid and a.programid=b.programid
and a.starttime=b.starttime and a.nodeid=b.nodeid);
insert into T_VO_LIVE (NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME,SID)
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME, SID from (
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME, SID,
       row_number()over(partition by a.programid, starttime,NODEID order by LIVENAME desc) sort
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
where exists (select 1 from T_VO_PROGRAM_MID h where g.programid=h.programid and g.nodeid=h.nodeid);
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
       row_number()over(partition by a.programid,a.nodeid order by to_date(a.lastuptime,'yyyy-mm-dd hh24:mi:ss') desc) sort
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
where not exists (select 1 from T_VO_PROGRAM h where g.programid=h.programid and g.nodeid=h.nodeid);


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




---------------------------------------
---------------------------------------


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM3.0.0.0.049_SSMS','MM3.0.0.0.055_SSMS');


alter table t_vo_reference add nodeid varchar2(60);
 comment on column t_vo_reference.nodeid
  is '栏目标识ID';
  
--add by lxiin  
  alter table t_vo_collect_node add isshow VARCHAR2(2)default '2' not null;
comment on column t_vo_collect_node.isshow  
          is '是否展示：1展示，2不展示';
          -- Create table
create table t_vo_collect_show
(
  nodeid     nvarchar2(60) not null,
  importdate date
)
;
-- Add comments to the columns 
comment on column t_vo_collect_show.nodeid
  is '节点标识，对应栏目标识';
comment on column t_vo_collect_show.importdate
  is '导入时间';

-- Add/modify columns 
alter table T_VO_COLLECT_SHOW add status number default 0;
-- Add comments to the columns 
comment on column T_VO_COLLECT_SHOW.status
  is '0,不在门户展示;1,在门户展示';
  
--------------p_video_SyncTacticTask--存储过程开始-------------
create or replace procedure p_video_SyncTacticTask as
   v_nindnum    number;--记录数据是否存在
  v_nstatus     number;
  v_nrecod      number;

begin
  v_nstatus := pg_log_manage.f_startlog('p_video_SyncTacticTask',
                                        '视频基地上下架。。。');


        select count(1) into v_nindnum
    from t_vo_program p,v_video_stat_d s
   where p.programid = s.content_id
     and s.stat_time = (select max(stat_time) from v_video_stat_d);


     if v_nindnum>0 then
     begin
       delete from t_vo_reference r where r.categoryid= '2805186';
       insert into t_vo_reference(id, categoryid, baseid, basetype, programid, nodeid, programname, sortid)

       select  SEQ_VO_REFERENCE_ID.NEXTVAL, '2805186'as categoryid,'m1000'as baseid,'2'as basetype, programid, nodeid, programname,(-1*rownum) as sortid from (
        select programid,nodeid,programname from (
        select
           p.programid,
           p.programname,
           s.play_count,
           p.nodeid,
           row_number()over(partition by p.programid order by p.islink asc) sort
      from t_vo_program p,v_video_stat_d s
     where p.programid = s.content_id
         and p.programtype !=1
       and s.stat_time = (select max(stat_time) from v_video_stat_d)
       -- group by programid,programname,play_count
       order by s.play_count desc
       ) where sort = 1
       ) where rownum<3000;
       commit;
     end;

       begin
       delete from t_vo_reference r where r.categoryid= '2805187';
       insert into t_vo_reference(id, categoryid, baseid, basetype, programid, nodeid, programname, sortid)
        select  SEQ_VO_REFERENCE_ID.NEXTVAL, '2805187'as categoryid,'m1001'as baseid,'2'as basetype, programid, nodeid,programname,(-1*rownum) as sortid from (
        select programid,nodeid,programname from (
        select
           p.programid,
           p.programname,
           s.add_30days_play_count,
           p.nodeid,
           row_number()over(partition by p.programid order by p.islink asc) sort
      from t_vo_program p,v_video_stat_d s
     where p.programid = s.content_id
      and p.programtype !=1
       and s.stat_time = (select max(stat_time) from v_video_stat_d)
       -- group by programid,programname,add_30days_play_count
       order by add_30days_play_count desc
       ) where sort = 1
       )where rownum<3000;
       commit;
     end;

      begin
       delete from t_vo_reference r where r.categoryid= '2805188';
       insert into t_vo_reference(id, categoryid, baseid, basetype, programid, nodeid, programname, sortid)
        select  SEQ_VO_REFERENCE_ID.NEXTVAL, '2805188'as categoryid,'m1002'as baseid,'2'as basetype, programid, nodeid,programname,(-1*rownum) as sortid from (
        select programid,nodeid,programname from (
        select
           p.programid,
           p.programname,
           s.add_7days_play_count,
           p.nodeid,
           row_number()over(partition by p.programid order by p.islink asc) sort
      from t_vo_program p,v_video_stat_d s
     where p.programid = s.content_id
      and p.programtype !=1
       and s.stat_time = (select max(stat_time) from v_video_stat_d)
       -- group by programid,programname,add_7days_play_count
       order by  add_7days_play_count desc
       )  where sort = 1
       )where rownum<3000;
       commit;
     end;

  end if;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;
--------------p_video_SyncTacticTask--存储过程结束-------------

commit;


------

