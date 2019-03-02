
alter table t_vo_collect add totalplaynum number(20);
 comment on column t_vo_collect.totalplaynum
  is '�ܲ��Ŵ���';

alter table T_VO_LIVE add sid  VARCHAR2(200);
 comment on column T_VO_LIVE.sid
  is 'Ψһ����,���ֶ�ȡֵΪNODEID+ PROGRAMID+ STARTTIME+ ENDTIME 4���ֶ����';

alter table T_VO_LIVE_MID add sid  VARCHAR2(200);
 comment on column T_VO_LIVE_MID.sid
  is 'Ψһ����,���ֶ�ȡֵΪNODEID+ PROGRAMID+ STARTTIME+ ENDTIME 4���ֶ����';

alter table t_mb_music_new add losslessmusic VARCHAR2(1);
 comment on column t_mb_music_new.losslessmusic
  is '��������,0����֧�֣�1��֧��';

alter table t_mb_music_new add format320kbps VARCHAR2(1);
 comment on column t_mb_music_new.format320kbps
  is '320kbps��ʽ,0����֧�֣�1��֧��';

----�޸���Ʒ��ʷ��ṹ�����ӳ���
alter table T_GOODS_HIS modify GOODSID VARCHAR2(400);
alter table T_GOODS_HIS modify CONTENTID VARCHAR2(200);

-- ��ӱ�t_cb_chapter����Լ��id
alter table t_cb_chapter add constraint pk_t_cb_chapter primary key(chapterid);

-- ��ӱ�t_cb_adapter����Լ��id
alter table t_cb_adapter add constraint pk_t_cb_adapter primary key(id);

------������Ƶ���ݼ����Ŵ���ͳ�ƴ洢����-----
CREATE OR REPLACE PROCEDURE P_VO_COLLECT_TOTALAPPLYNUM as
  v_nstatus number;
  v_nrecod  number;
  totalnum NUMBER(12,6);
cursor s_cur is
        select sum(v.totalplaynum) as totalnum,p.nodeid as collectid from t_vo_program p, t_vo_videodetail v,t_vo_collect c where p.programid = v.programid and c.collectid = p.nodeid group by p.nodeid;
begin
  v_nstatus := pg_log_manage.f_startlog('P_VO_COLLECT_TOTALAPPLYNUM',
                                        '��Ƶ���ݼ����Ŵ���ͳ��');
 for r in s_cur loop
   --select sum(v.totalplaynum) into totalnum from t_vo_program p, t_vo_videodetail v where p.programid = v.programid and p.nodeid=r.collectid;
   update T_VO_COLLECT set totalplaynum = r.totalnum where collectid=r.collectid;
 end loop;
 commit;
 v_nrecod:=SQL%ROWCOUNT;
 v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
 exception
 when others then
 rollback;
 --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;

end;
/

------����job��ʱִ����Ƶ���ݼ����Ŵ���ͳ�ƴ洢����-----
DECLARE
  job numeric;
BEGIN
  sys.dbms_job.submit(job,
                  'P_VO_COLLECT_TOTALAPPLYNUM;',
                  sysdate,
                  'TRUNC(SYSDATE + 1) + (6*60+30)/(24*60)');
END;
/
------����job����-----

 ------����p_delete_insert�洢����-----
----����Ϊp_delete_insert�洢���̣���Ҫһ��ִ��------------
--------------------------------------------------------------
create or replace procedure p_delete_insert as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_delete_insert',

                                        '����ͬ�������������');
delete T_VO_LIVE a
where exists (
select 1 from T_VO_LIVE_MID b where a.nodeid=b.nodeid and a.programid=b.programid
and a.starttime=b.starttime);
insert into T_VO_LIVE (NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME,SID)
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME, SID from (
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME, SID,
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
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;
----p_delete_insert�洢���̽���------------
---------------------------------------
/

--ID,CID��Ҫ������������ȡֵ�滻����ע�⡣����
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('63', '201401תMM', '984609661', 0, 1, 1, 'c.pkdate=''201401''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('64', '201402תMM', '984609662', 0, 1, 1, 'c.pkdate=''201402''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('65', '201403תMM', '984609663', 0, 1, 1, 'c.pkdate=''201403''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('66', '201404תMM', '984609664', 0, 1, 1, 'c.pkdate=''201404''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('67', '201405תMM', '984609665', 0, 1, 1, 'c.pkdate=''201405''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('68', '201406תMM', '984609666', 0, 1, 1, 'c.pkdate=''201406''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('69', '201407תMM', '984609667', 0, 1, 1, 'c.pkdate=''201407''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('70', '201408תMM', '984609668', 0, 1, 1, 'c.pkdate=''201408''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('71', '201409תMM', '984609669', 0, 1, 1, 'c.pkdate=''201409''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('72', '201410תMM', '984609670', 0, 1, 1, 'c.pkdate=''201410''', '', -1, null, to_date('17-04-2014 02:20:33', 'dd-mm-yyyy hh24:mi:ss'), to_date('12-06-2013 18:08:30', 'dd-mm-yyyy hh24:mi:ss'), 0, '5');

commit;

alter table T_APPLY_RECOMMAND add (DESCRIPTION varchar2(400)) ;
alter table T_APPLY_RECOMMAND_tra add (DESCRIPTION varchar2(400)) ;

  comment on column T_APPLY_RECOMMAND.DESCRIPTION 
      is '������Ӧ���Ƽ���';


commit;

 ------����P_MT_APPLY_RECOMMAND�洢����-----
--�����ű����ô洢����P_MT_APPLY_RECOMMAND��P_MT_APPLY_RECOMMAND��ÿ���ýű��ֹ����ã���-----
--����ΪP_MT_APPLY_RECOMMAND�洢����------------
--------------------------------------------------------------
CREATE OR REPLACE PROCEDURE P_MT_APPLY_RECOMMAND
as
     z NUMBER(12,6):=0;
cursor s_cur is
        select * from T_APPLY_RECOMMAND;   
begin   
 for r in s_cur loop
   select count(*) into z from t_key_resource where KEYID='4942' and TID=r.contentid;
  if(z>0) then
   update t_key_resource set VALUE=r.DESCRIPTION,lupdate=sysdate where KEYID='4942' and TID=r.contentid;
   else
  insert into t_key_resource(TID,Keyid,VALUE,lupdate) values(r.contentid,'4942',r.DESCRIPTION,sysdate);
  end if;
    end loop;
    commit;
    exception
    when others then
    rollback;
end;
----p_delete_insert�洢���̽���------------
---------------------------------------

-- Create table
create table T_VO_LIVENODE
(
  id           VARCHAR2(60) not null,
  nodeid       VARCHAR2(60) not null,
  nodename     VARCHAR2(128) not null,
  description  VARCHAR2(4000) not null,
  parentnodeid VARCHAR2(60),
  logopath     VARCHAR2(512),
  sortid       NUMBER(19),
   lupdate      DATE default sysdate not null
);
-- Add comments to the columns 
comment on column T_VO_LIVENODE.id
  is '����ID';
comment on column T_VO_LIVENODE.nodeid
  is '��ĿID';
comment on column T_VO_LIVENODE.nodename
  is '��Ŀ����';
comment on column T_VO_LIVENODE.description
  is '��Ŀ����';
comment on column T_VO_LIVENODE.parentnodeid
  is '����ĿID,0,Ϊ����Ŀ';
comment on column T_VO_LIVENODE.logopath
  is 'ͼƬ';
comment on column T_VO_LIVENODE.sortid
  is '���';
comment on column T_VO_LIVENODE.lupdate
  is '������ʱ��';

-- Create/Recreate primary, unique and foreign key constraints 
alter table T_VO_LIVENODE
  add constraint pk_vo_livenode_id primary key (ID);
  
create sequence SEQ_VO_LIVE_NODEID
minvalue 1
maxvalue 99999999999
start with 1
increment by 1
cache 20;

-----���ڵ�
insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, 'm2643', '����̨', '����̨', '0', '', 80, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, 'm2644', '�ط�̨', '�ط�̨', '0', '', 70, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, 'm2645', '����̨', '����̨', '0', '', 60, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, 'm2646', '��ɫ��Ŀ', '��ɫ��Ŀ', '0', '', 50, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, 'm2647', '���', '���', '0', '', 40, sysdate);

----���ڵ����

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10227218', '������', '����:ranking|desc,�ṩ��:��Ѷ�й�', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/426/607.jpg', 30, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10254911', '�㽭����', '�㽭����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/446/874.jpg', 20130703, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10254911', '�㽭����', '�㽭����', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/446/874.jpg', 20130703, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10249906', '����CCTV12ֱ��', '����CCTV12ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/423/796.png', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10249906', '����CCTV12ֱ��', '����CCTV12ֱ��', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/423/796.png', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259858', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/275.png', 6, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259858', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/275.png', 6, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259860', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/267.png', 4, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259860', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/267.png', 4, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259861', '�������ֱ��', '�������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/258.png', 5, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259861', '�������ֱ��', '�������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/258.png', 5, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259862', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/237.png', 4, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10259862', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/450/237.png', 4, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10263902', '��������', '��������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/461/456.png', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10263902', '��������', '��������', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/461/456.png', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10287449', 'CCTV�ٶ�', 'CCTV�ٶ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/554/880.jpg', 2, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10287449', 'CCTV�ٶ�', 'CCTV�ٶ�', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/554/880.jpg', 2, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10288659', 'Ӣ��ֱ��1', 'Ӣ��ֱ��1', 'm2646', '', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10288661', 'Ӣ��ֱ��3', 'Ӣ��ֱ��3', 'm2646', '', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10288662', 'Ӣ��ֱ��4', 'Ӣ��ֱ��4', 'm2646', '', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10141832', 'ϲ��ֱ����', 'ϲ��ֱ����', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/315/822.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10158863', 'CCTV9����', 'CCTV9����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/429/40.png', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10171269', 'CCTV6��ӰƵ��', 'CCTV6��ӰƵ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/317/947.png', 2, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190132', 'CCTV1', 'CCTV1', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/42.png', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190132', 'CCTV1', 'CCTV1', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/42.png', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190133', 'CCTV2', 'CCTV2', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/44.png', 1, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190133', 'CCTV2', 'CCTV2', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/44.png', 1, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190134', 'CCTV3', 'CCTV3', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/45.png', 2, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190134', 'CCTV3', 'CCTV3', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/45.png', 2, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190135', 'CCTV4', 'CCTV4', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/46.png', 3, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190135', 'CCTV4', 'CCTV4', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/46.png', 3, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190136', 'CCTV5', 'CCTV5', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/47.png', 4, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190137', 'CCTV6', 'CCTV6', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/48.png', 5, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190137', 'CCTV6', 'CCTV6', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/48.png', 5, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190138', 'CCTV8', 'CCTV8', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/49.png', 6, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190138', 'CCTV8', 'CCTV8', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/49.png', 6, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190139', 'CCTV9', 'CCTV9', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/50.png', 7, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190139', 'CCTV9', 'CCTV9', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/50.png', 7, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190140', 'CCTV10', 'CCTV10', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/424/240.jpg', 8, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190140', 'CCTV10', 'CCTV10', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/424/240.jpg', 8, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190141', 'CCTV����', 'CCTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/435/830.jpg', 9, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190141', 'CCTV����', 'CCTV����', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/435/830.jpg', 9, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190142', 'CCTV����', 'CCTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/51.png', 10, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10190142', 'CCTV����', 'CCTV����', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/296/51.png', 10, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10387325', '��������', '��������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/821/582.jpg', 1, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10387325', '��������', '��������', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/821/582.jpg', 1, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301047', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/229.jpg', 17, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301047', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/229.jpg', 17, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301048', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/235.jpg', 16, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301048', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/235.jpg', 16, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301049', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/183.jpg', 20, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301049', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/183.jpg', 20, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301050', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/215.jpg', 18, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301050', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/215.jpg', 18, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301051', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/172.jpg', 21, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301051', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/172.jpg', 21, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301052', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/165.jpg', 22, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301052', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/165.jpg', 22, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301053', '��������ֱ��', '��������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/154.jpg', 23, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301053', '��������ֱ��', '��������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/154.jpg', 23, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301054', '����������ֱ��', '����������ֱ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/199.jpg', 19, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301054', '����������ֱ��', '����������ֱ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/583/199.jpg', 19, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301360', '��������', '��������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/581/501.jpg', 1, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10301360', '��������', '��������', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/581/501.jpg', 1, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10299556', 'CCTV7', 'CCTV7', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/576/703.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10299556', 'CCTV7', 'CCTV7', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/576/703.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10297540', 'CCTV11', 'CCTV11', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/575/565.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10297540', 'CCTV11', 'CCTV11', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/575/565.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10297541', 'CCTVNews', 'CCTVNews', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/575/605.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10297541', 'CCTVNews', 'CCTVNews', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/575/605.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10297542', 'CCTV����', 'CCTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/575/612.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10297542', 'CCTV����', 'CCTV����', 'm2643', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/575/612.jpg', 0, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10351974', '��èƵ��02', '��èƵ��02', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/731/597.jpg', 102, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10351975', '��èƵ��03', '��èƵ��03', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/731/603.jpg', 103, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10351976', '��èƵ��04', '��èƵ��04', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/731/609.jpg', 104, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10351977', '��èƵ��05', '��èƵ��05', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/731/621.jpg', 105, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10353245', '�Ϸ�����̨����Ƶ��', '�Ϸ�����̨����Ƶ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/734/717.jpg', 26, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10353245', '�Ϸ�����̨����Ƶ��', '�Ϸ�����̨����Ƶ��', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/734/717.jpg', 26, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349733', 'BTV����', 'BTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/850.jpg', 208, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349733', 'BTV����', 'BTV����', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/850.jpg', 208, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349734', 'BTVӰ��', 'BTVӰ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/826.jpg', 205, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349734', 'BTVӰ��', 'BTVӰ��', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/826.jpg', 205, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349735', 'BTV����', 'BTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/844.jpg', 207, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349735', 'BTV����', 'BTV����', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/844.jpg', 207, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349736', 'BTV����', 'BTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/757.jpg', 203, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349736', 'BTV����', 'BTV����', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/757.jpg', 203, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349737', 'BTV����', 'BTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/766.jpg', 204, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349737', 'BTV����', 'BTV����', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/766.jpg', 204, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349738', 'BTV�ƽ�', 'BTV�ƽ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/735.jpg', 201, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349738', 'BTV�ƽ�', 'BTV�ƽ�', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/735.jpg', 201, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349739', 'BTV�ƾ�', 'BTV�ƾ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/743.jpg', 202, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349739', 'BTV�ƾ�', 'BTV�ƾ�', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/743.jpg', 202, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349740', 'BTV����', 'BTV����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/793.jpg', 206, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349740', 'BTV����', 'BTV����', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/793.jpg', 206, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349741', 'XJTV2ά����������ۺ�', 'XJTV2ά����������ۺ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/813.jpg', 302, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349741', 'XJTV2ά����������ۺ�', 'XJTV2ά����������ۺ�', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/813.jpg', 302, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349742', 'XJTV3�������������ۺ�', 'XJTV3�������������ۺ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/772.jpg', 303, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349742', 'XJTV3�������������ۺ�', 'XJTV3�������������ۺ�', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/772.jpg', 303, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349743', 'XJTV5ά���������', 'XJTV5ά���������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/807.jpg', 305, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349743', 'XJTV5ά���������', 'XJTV5ά���������', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/807.jpg', 305, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349744', 'XJTV8������������', 'XJTV8������������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/978.jpg', 308, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349744', 'XJTV8������������', 'XJTV8������������', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/978.jpg', 308, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349745', 'XJTV9ά����ﾭ������', 'XJTV9ά����ﾭ������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/972.jpg', 309, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349745', 'XJTV9ά����ﾭ������', 'XJTV9ά����ﾭ������', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/972.jpg', 309, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349746', '��������', '��������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/957.jpg', 2, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349746', '��������', '��������', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/957.jpg', 2, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349747', '����һ��', '����һ��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/951.jpg', 91, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349747', '����һ��', '����һ��', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/951.jpg', 91, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349748', '���Ŷ���', '���Ŷ���', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/726/39.jpg', 92, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349748', '���Ŷ���', '���Ŷ���', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/726/39.jpg', 92, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349749', '��������', '��������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/726/12.jpg', 90, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349749', '��������', '��������', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/726/12.jpg', 90, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349750', '�����������ۺ�', '�����������ۺ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/990.jpg', 86, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349750', '�����������ۺ�', '�����������ۺ�', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/990.jpg', 86, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349751', '����������', '����������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/984.jpg', 85, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349751', '����������', '����������', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/984.jpg', 85, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349752', 'ɽ������', 'ɽ������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/917.jpg', 19, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349752', 'ɽ������', 'ɽ������', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/917.jpg', 19, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349753', '��������', '��������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/905.jpg', 11, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349753', '��������', '��������', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/905.jpg', 11, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349754', '�ӱ�����', '�ӱ�����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/867.jpg', 25, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349754', '�ӱ�����', '�ӱ�����', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/867.jpg', 25, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349755', '�ɶ�����', '�ɶ�����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/861.jpg', 3, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349755', '�ɶ�����', '�ɶ�����', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/861.jpg', 3, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349756', '�ɶ�����', '�ɶ�����', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/942.jpg', 3, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349756', '�ɶ�����', '�ɶ�����', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/942.jpg', 3, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349757', '���������ۺ�', '���������ۺ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/936.jpg', 10, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349757', '���������ۺ�', '���������ۺ�', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/936.jpg', 10, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349758', '��������', '��������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/929.jpg', 19, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349758', '��������', '��������', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/929.jpg', 19, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349759', '����¢��', '����¢��', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/923.jpg', 8, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349759', '����¢��', '����¢��', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/923.jpg', 8, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349760', '��èƵ��', '��èƵ��', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/688.jpg', 101, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349761', '��èƵ��06', '��èƵ��06', 'm2646', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/696.jpg', 106, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349765', '���������ۺ�', '���������ۺ�', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/669.jpg', 24, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349765', '���������ۺ�', '���������ۺ�', 'm2644', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/669.jpg', 24, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349766', '�������', '�������', 'm2647', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/678.jpg', 24, sysdate);

insert into T_VO_LIVENODE (ID, NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, LUPDATE)
values (SEQ_VO_LIVE_NODEID.NEXTVAL, '10349766', '�������', '�������', 'm2645', 'http://vbimg.mmarket.com/res/videobase/depository/image/10/725/678.jpg', 24, sysdate);


commit;

------

