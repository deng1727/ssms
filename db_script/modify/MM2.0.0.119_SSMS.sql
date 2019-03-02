
----------���������� begin--------

--����seq_t_cb_Black_id�Զ���������
create sequence seq_t_cb_black_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;


-- ������������
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
  is '������������';
-- add comments to the columns 
comment on column t_cb_black.content_id
  is '�������Զ����ɣ�';
comment on column t_cb_black.content_id
  is '����id';
comment on column t_cb_black.content_name
  is '��������';
comment on column t_cb_black.content_type
  is '��������:101:����;115:��Ѷ;116:����Ƭ;220:������';
comment on column t_cb_black.content_portal
  is '���������Ż�����:1:�ͻ���; 2:wap�Ż�;3:����';
comment on column t_cb_black.createdate
  is '���ʱ��';
comment on column t_cb_black.lupdate
  is '����޸�ʱ��';
comment on column t_cb_black.status
  is '0,δ����;1,����';
  
 
----------���������� end--------

  
--------��Ϸ������Ϸ������ begin------------

-----����seq_t_gamebase_black_id�Զ���������
create sequence seq_t_gamebase_black_id
minvalue 1
maxvalue 999999999999
start with 1000
increment by 1
cache 2000
cycle;


-- ��Ϸ������Ϸ��������
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
  is '��Ϸ������Ϸ��������';
-- add comments to the columns 
comment on column t_gamebase_black.id
  is '�������Զ����ɣ�';
comment on column t_gamebase_black.icpservid
  is '��Ϸҵ��ID';
comment on column t_gamebase_black.servname
  is '��Ϸ����';
comment on column t_gamebase_black.servdesc
  is '��Ϸ���';
comment on column t_gamebase_black.oldprice
  is 'ԭ���ʷ�(��)';
comment on column t_gamebase_black.mobileprice
  is '�ּ��ʷ�(��)';
comment on column t_gamebase_black.createdate
  is '���ʱ��';
comment on column t_gamebase_black.lupdate
  is '����޸�ʱ��';
comment on column t_gamebase_black.status
  is '0,δ����;1,����';
  
--------��Ϸ������Ϸ������ end------------









-----------��Ƶ�洢�����޸�
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
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;

------�Ķ�ͼƬ�޸�
update  t_rb_book_new t set t.bookpic='http://rs.base.mmarket.com/read/logo/'||t.bookid||'.jpg';



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.115_SSMS','MM2.0.0.0.119_SSMS');

commit;



create or replace procedure p_del_gamebase_black_rel as
  v_nstatus number; --��¼��ذ�״̬
  v_nrecod  number;
  v_sql_1   varchar2(1600);
  v_sql_2   varchar2(1600);
  v_sql_3   varchar2(1600);
  v_sql_4   varchar2(1600);
  v_sql_5   varchar2(1600);
  v_sql_6   varchar2(1600);
begin
  v_nstatus := pg_log_manage.f_startlog('p_del_gamebase_black_rel',
                                        'ͬ��������Ϸ��ɾ�������������Ϣ');

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
    --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;

commit;
