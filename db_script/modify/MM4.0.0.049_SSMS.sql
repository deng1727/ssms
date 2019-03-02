----------����ԭ�е�ֱ����Ŀ����---------
alter table t_vo_live rename to t_vo_live_bak;
------------���´����µ�ֱ����Ŀ����---------
-- Create table
create table T_VO_LIVE
(
  videoid    VARCHAR2(60) not null,
  livename  VARCHAR2(200) not null,
  starttime VARCHAR2(14) not null,
  endtime   VARCHAR2(14) not null,
  sid       VARCHAR2(200) not null
);
comment on column T_VO_LIVE.videoid
  is 'ֱ��Ƶ��������ID';
comment on column T_VO_LIVE.livename
  is 'ֱ����Ŀ����';
comment on column T_VO_LIVE.starttime
  is 'ֱ��ʱ�ο�ʼʱ��:��ʽ��YYYYMMDDHH24MISS';
comment on column T_VO_LIVE.endtime
  is 'ֱ��ʱ�ν���ʱ��:��ʽ��YYYYMMDDHH24MISS';
comment on column T_VO_LIVE.sid
  is 'Ψһ����,���ֶ�ȡֵΪvideoid+STARTTIME+ENDTIME 3���ֶ����';
alter table T_VO_LIVE
  add constraint PK_T_VO_LIVE_ID primary key (SID);


----------ɾ��ԭ�е�ֱ����Ŀ���м��---------
drop table t_vo_live_mid ;
------------���´����µ�ֱ����Ŀ���м���---------
create table T_VO_LIVE_MID
(
  videoid    VARCHAR2(60) not null,
  livename  VARCHAR2(200) not null,
  starttime VARCHAR2(14) not null,
  endtime   VARCHAR2(14) not null,
  STATUS    CHAR(1),
  sid       VARCHAR2(200) not null
);
comment on column T_VO_LIVE_MID.videoid
  is 'ֱ��Ƶ��������ID';
comment on column T_VO_LIVE_MID.livename
  is 'ֱ����Ŀ����';
comment on column T_VO_LIVE_MID.starttime
  is 'ֱ��ʱ�ο�ʼʱ��:��ʽ��YYYYMMDDHH24MISS';
comment on column T_VO_LIVE_MID.endtime
  is 'ֱ��ʱ�ν���ʱ��:��ʽ��YYYYMMDDHH24MISS';
comment on column T_VO_LIVE_MID.STATUS
  is '״̬:A-����,U-����,D-ɾ��';
comment on column T_VO_LIVE_MID.sid
  is 'Ψһ����,���ֶ�ȡֵΪvideoid+STARTTIME+ENDTIME 3���ֶ����';
  
--------------�������Ȱ񵥸�Ԥ---------------
alter table t_bid_inter add sortid number(8);
comment on column t_bid_inter.type 
  is '��Ԥָ�����ͣ�1�����ȣ�2�������3������ϸ���ȣ�4�����ϸ���ȣ�5,�������Ȱ�';
comment on column t_bid_inter.sortid 
  is 'ָ��Ӧ�÷������Ȱ�����ֻ����typeΪ5ʱ���ֶ���Ч';

---------------����v_appcate_hotblank_sort�������Ȱ�������ͼ��ʼ-------
create view v_appcate_hotblank_sort as
select g.contentid,m.hotlist,row_number() over(partition by g.appcateid order by hotlist desc) sortid,g.appcateid
from mid_table m,t_r_gcontent g where m.appid = g.contentid;
comment on table v_appcate_hotblank_sort is '�������Ȱ�������ͼ';
comment on column v_appcate_hotblank_sort.contentid is 'Ӧ��ID';
comment on column v_appcate_hotblank_sort.hotlist is '���Ȱ�ֵ';
comment on column v_appcate_hotblank_sort.sortid is '�������Ȱ�����'; 
comment on column v_appcate_hotblank_sort.appcateid is 'Ӧ�ö�������id';  

---------------����v_appcate_hotblank_sort�������Ȱ�������ͼ����-------


-- mm��Ϊ��������-���������ϵ�����������ֶ�
alter table T_A_CM_DEVICE_RESOURCE add wapurl VARCHAR2(1024);
alter table T_A_CM_DEVICE_RESOURCE add mourl VARCHAR2(1024);
-- Add comments to the columns 
comment on column T_A_CM_DEVICE_RESOURCE.wapurl
  is 'wap����url��ַ';
comment on column T_A_CM_DEVICE_RESOURCE.mourl
  is 'mo����url��ַ';

alter table v_cm_device_resource add wapurl VARCHAR2(1024);
alter table v_cm_device_resource add mourl VARCHAR2(1024);
-- Add comments to the columns 
comment on column v_cm_device_resource.wapurl
  is 'wap����url��ַ';
comment on column v_cm_device_resource.mourl
  is 'mo����url��ַ';

alter table v_cm_device_resource_mid add wapurl VARCHAR2(1024);
alter table v_cm_device_resource_mid add mourl VARCHAR2(1024);
-- Add comments to the columns 
comment on column v_cm_device_resource_mid.wapurl
  is 'wap����url��ַ';
comment on column v_cm_device_resource_mid.mourl
  is 'mo����url��ַ';

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.045_SSMS','MM4.0.0.0.049_SSMS');

 
commit;

--------------------�ر�����-----------------
----------------���´洢������ÿ��������������ִ��-----------------------
----------------ÿ���洢���̶��п�ʼ�ͽ�����ǣ��мǲ�Ҫ�������˻��ǿ������˻�����-----------------------
----------------�����濴���ÿ���洢���̵� -----------------------
--------------�޸�p_delete_insert�洢���̿�ʼ------------------------------
create or replace procedure p_delete_insert as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_delete_insert',

                                        '����ͬ����Ƶ�������');
delete T_VO_LIVE a
where exists (
select 1 from T_VO_LIVE_MID b where a.videoid=b.videoid
and a.starttime=b.starttime);
insert into T_VO_LIVE (videoid, LIVENAME, STARTTIME, ENDTIME,SID)
select videoid, LIVENAME, STARTTIME, ENDTIME, SID from (
select videoid, LIVENAME, STARTTIME, ENDTIME, SID,
       row_number()over(partition by a.videoid, starttime order by LIVENAME desc) sort
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
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;

--------------�޸�p_delete_insert�洢���̽���------------------------------



--------------�޸�P_mt_rank_index_fin�洢���̿�ʼ--------------------------
CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin as
  v_nstatus     number;
  v_nrecod      number;
begin
	v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','���Ⱥ�����񵥼���');
 -------ִ����Ϸ�񵥼���洢����-----------------------
P_mt_rank_index_fin_game;
 -------ִ������񵥼���洢����-----------------------
P_mt_rank_index_fin_software;

 --------------���Ȱ񵥸�Ԥ----------------
update mid_table t1
  set t1.hotlist
     = (select t1.hotlist*(decode(t2.type,'1',t2.param,1))
          from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='1')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='1');
 --------------���°񵥸�Ԥ----------------
update mid_table t1
  set t1.riselist
     = (select t1.riselist*(decode(t2.type,'2',t2.param,1))
     from t_bid_inter t2
         where t1.appid = t2.contentid and t2.type='2')
 where exists(select 1 from t_bid_inter t3 where t1.appid = t3.contentid and t3.type='2');

 -------����ϸ���ȸ�Ԥ-----------------------
  update mid_table t1
  set t1.hotlist
     = (select t2.M_HOTLIST+(decode(t2.type,'3',t2.param,0))
          from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='3')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='3');
 -------����ϸ���ȸ�Ԥ-----------------------
update mid_table t1
  set t1.riselist
     = (select t2.M_RISELIST+(decode(t2.type,'4',t2.param,0))
     from v_bid_inters t2
         where t1.appid = t2.contentid and t2.type='4')
 where exists(select 1 from v_bid_inters t3 where t1.appid = t3.contentid and t3.type='4');
 
  -------�����������ݸ�Ԥ-----------------------
update mid_table m1 set m1.hotlist =
 (select (v.hotlist + b.param) h from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v 
where gc.contentid = b.contentid and gc.appcateid = v.appcateid and  
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5') 
 where  exists(select 1 from t_r_gcontent gc,t_bid_inter b,v_appcate_hotblank_sort v 
where gc.contentid = b.contentid and gc.appcateid = v.appcateid and  
 b.contentid = m1.appid and v.sortid = b.sortid and b.type = '5');
 commit;
 
  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;

--------------�޸�P_mt_rank_index_fin�洢���̽���--------------------------

