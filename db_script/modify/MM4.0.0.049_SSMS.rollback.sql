alter table t_vo_live_bak rename to t_vo_live;
alter table t_bid_inter drop column sortid;
 drop view v_appcate_hotblank_sort;

alter table T_A_CM_DEVICE_RESOURCE drop column wapurl;
alter table T_A_CM_DEVICE_RESOURCE drop column mourl;
alter table v_cm_device_resource drop column wapurl;
alter table v_cm_device_resource drop column mourl;
alter table v_cm_device_resource_mid drop column wapurl;
alter table v_cm_device_resource_mid drop column mourl;
delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.049_SSMS' and LASTDBVERSION = 'MM4.0.0.0.045_SSMS';


--------------p_delete_insert存储过程开始------------------------------
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
where  exists (select 1 from T_VO_PROGRAM_MID h where g.programid=h.programid and g.nodeid=h.nodeid);
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
       row_number()over(partition by a.programid,a.nodeid order by to_number(a.lastuptime) desc) sort
  from T_VO_PROGRAM_MID a
where status in ('A', 'U')
)where sort=1
;


delete T_VO_VIDEO i
where exists(select 1 from T_VO_VIDEO_MID j where i.videoid=j.videoid and i.coderateid=j.coderateid);
insert into T_VO_VIDEO(VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, HASHC, UPDATETIME)
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, HASHC, UPDATETIME from (
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, HASHC, UPDATETIME,
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

--------------p_delete_insert存储过程结束------------------------------



--------------执行P_mt_rank_index_fin存储过程开始--------------------------
CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin
as
begin
P_mt_rank_index_fin_game;
P_mt_rank_index_fin_software;

update mid_table t1
  set t1.hotlist
     = (select t1.hotlist*(decode(t2.type,'1',t2.param,1))
          from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='1')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='1');

update mid_table t1
  set t1.riselist
     = (select t1.riselist*(decode(t2.type,'2',t2.param,1))
     from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='2')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='2');

 -------细粒度干预-----------------------
  update mid_table t1
  set t1.hotlist
     = (select t2.M_HOTLIST+(decode(t2.type,'3',t2.param,0))
          from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='3')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='3');

update mid_table t1
  set t1.riselist
     = (select t2.M_RISELIST+(decode(t2.type,'4',t2.param,0))
     from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='4')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='4');
 commit;
end;

--------------执行P_mt_rank_index_fin存储过程结束--------------------------


commit;
----------