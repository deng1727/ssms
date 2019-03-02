
----------动漫黑名单 begin--------

--创建seq_t_cb_Black_id自动生成序列
create sequence seq_t_cb_black_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;


-- 动漫黑名单表
create table t_cb_black
(
  id varchar2(20),
  content_id varchar2(60),
  content_name varchar2(255 byte),
  content_type number(6),
  content_portal number(6),
  createdate date,
  lupdate date,
  status number(2)
);
-- add comments to the table 
comment on table t_cb_black
  is '动漫黑名单表';
-- add comments to the columns 
comment on column t_cb_black.content_id
  is '主键（自动生成）';
comment on column t_cb_black.content_id
  is '内容id';
comment on column t_cb_black.content_name
  is '内容名称';
comment on column t_cb_black.content_type
  is '内容类型:101:主题;115:资讯;116:动画片;220:漫画书';
comment on column t_cb_black.content_portal
  is '内容所属门户类型:1:客户端; 2:wap门户;3:所有';
comment on column t_cb_black.createdate
  is '入库时间';
comment on column t_cb_black.lupdate
  is '最后修改时间';
comment on column t_cb_black.status
  is '0,未启用;1,启用';
  
 
----------动漫黑名单 end--------

  
--------游戏基地游戏黑名单 begin------------

-----创建seq_t_gamebase_black_id自动生成序列
create sequence seq_t_gamebase_black_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;


-- 游戏基地游戏黑名单表
create table t_gamebase_black
(
  id varchar2(20),
  icpservid varchar2(60 byte),
  servname varchar2(255 byte),
  servdesc varchar2(255 byte),
  oldprice number(8),
  mobileprice number(8),
  createdate date,
  lupdate date,
  status number(2)
);
-- add comments to the table 
comment on table t_gamebase_black
  is '游戏基地游戏黑名单表';
-- add comments to the columns 
comment on column t_gamebase_black.id
  is '主键（自动生成）';
comment on column t_gamebase_black.icpservid
  is '游戏业务ID';
comment on column t_gamebase_black.servname
  is '游戏名称';
comment on column t_gamebase_black.servdesc
  is '游戏简介';
comment on column t_gamebase_black.oldprice
  is '原价资费(分)';
comment on column t_gamebase_black.mobileprice
  is '现价资费(分)';
comment on column t_gamebase_black.createdate
  is '入库时间';
comment on column t_gamebase_black.lupdate
  is '最后修改时间';
comment on column t_gamebase_black.status
  is '0,未启用;1,启用';
  
--------游戏基地游戏黑名单 end------------









-----------视频存储过程修改
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
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME from T_VO_LIVE_MID b where status in ('A','U');


delete T_VO_NODE c
where exists(select 1 from T_VO_NODE_MID d where c.nodeid=d.nodeid);
insert into T_VO_NODE(NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME)
select NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME from T_VO_NODE_MID
 where status in ('A','U');

delete T_VO_PRODUCT e
where exists(select 1 from t_Vo_Product_Mid f where e.productid=f.productid );
insert into T_VO_PRODUCT(PRODUCTID, PRODUCTNAME, FEE, CPID, FEETYPE, STARTDATE, FEEDESC, FREETYPE, FREEEFFECTIME, FREETIMEFAIL)
select PRODUCTID, PRODUCTNAME, FEE, CPID, FEETYPE, STARTDATE, FEEDESC, FREETYPE, FREEEFFECTIME, FREETIMEFAIL from t_Vo_Product_Mid
 where status in ('A','U');


delete T_VO_PROGRAM g
where exists (select 1 from T_VO_PROGRAM_MID h where g.programid=h.programid);
insert into T_VO_PROGRAM(PROGRAMID, VIDEOID, PROGRAMNAME, NODEID, DESCRIPTION, LOGOPATH,
 TIMELENGTH, SHOWTIME, LASTUPTIME, PROGRAMTYPE, EXPORTTIME, FTPLOGOPATH, TRUELOGOPATH)
select PROGRAMID, VIDEOID, PROGRAMNAME, NODEID, DESCRIPTION, LOGOPATH,
 TIMELENGTH, SHOWTIME, LASTUPTIME, PROGRAMTYPE, EXPORTTIME, FTPLOGOPATH, TRUELOGOPATH from T_VO_PROGRAM_MID where status in ('A','U');

delete T_VO_VIDEO i
where exists(select 1 from T_VO_VIDEO_MID j where i.videoid=j.videoid and i.coderateid=j.coderateid);
insert into T_VO_VIDEO(VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, UPDATETIME)
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, UPDATETIME from T_VO_VIDEO_MID  where status in ('A','U');

delete  T_VO_VIDEODETAIL k
where exists(select 1 from T_VO_VIDEODETAIL_MID l where k.programid=l.programid);
insert into T_VO_VIDEODETAIL(PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME)
select PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME from T_VO_VIDEODETAIL_MID
 where status in ('A','U');
 

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

------阅读图片修改
update  t_rb_book_new t set t.bookpic='http://rs.base.mmarket.com/read/logo/'||t.bookid||'.jpg';



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.115_SSMS','MM2.0.0.0.119_SSMS');

commit;



create or replace procedure p_del_gamebase_black_rel as
  v_nstatus number; --纪录监控包状态
  v_nrecod  number;
  v_sql_1   varchar2(1600);
  v_sql_2   varchar2(1600);
  v_sql_3   varchar2(1600);
  v_sql_4   varchar2(1600);
  v_sql_5   varchar2(1600);
  v_sql_6   varchar2(1600);
begin
  v_nstatus := pg_log_manage.f_startlog('p_del_gamebase_black_rel',
                                        '同步基地游戏后删除黑名单相关信息');

  v_sql_1 := 'delete  from t_r_base b where exists (select 1 from (
select r.id from t_r_reference r 
 where  exists (select 1
          from (select c.id
                  from t_gamebase_black t, t_r_gcontent c
                 where t.icpservid = c.icpservid) t1
         where t1.id = r.refnodeid)) r1 where r1.id=b.id)';
         
   v_sql_2 :='delete from t_r_reference r
 where exists (select 1
          from (select c.id
                  from t_gamebase_black t, t_r_gcontent c
                 where t.icpservid = c.icpservid) t1
         where t1.id = r.refnodeid)';
         
    v_sql_3 := 'delete from t_r_base r
 where exists (select 1
          from (select c.id
                  from t_gamebase_black t, t_r_gcontent c
                 where t.icpservid = c.icpservid) t1
         where t1.id = r.id)';
         
    v_sql_4 := 'delete from t_r_gcontent t
 where exists
 (select 1 from t_gamebase_black b where b.icpservid = t.icpservid)';
 
    v_sql_5 := ' delete from v_service t
 where exists
 (select 1 from t_gamebase_black b where b.icpservid = t.icpservid)';
 
    v_sql_6 := ' delete from t_game_service_new t
 where exists
 (select 1 from t_gamebase_black b where b.icpservid = t.icpservid)';
  execute immediate v_sql_1;
  execute immediate v_sql_2;
  execute immediate v_sql_3;
  execute immediate v_sql_4;
  execute immediate v_sql_5;
  execute immediate v_sql_6;
  commit;
   v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;

commit;
