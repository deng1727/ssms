-- Create table
create table T_V_PROGRAM_SALES
(
  ID             VARCHAR2(32) not null,
  programid     VARCHAR2(21),
  SALESPRODUCTID VARCHAR2(21),
  SALESDISCOUNT  VARCHAR2(20),
  SALESCATEGORY  NUMBER(2),
  SALESSTARTTIME VARCHAR2(19),
  SALESENDTIME   VARCHAR2(19),
  LUPDATE        DATE
);
-- Add comments to the columns 
comment on column T_V_PROGRAM_SALES.ID
  is '主键ID';
comment on column T_V_PROGRAM_SALES.programid
  is '节目ID';
comment on column T_V_PROGRAM_SALES.SALESPRODUCTID
  is '促销产品ID';
comment on column T_V_PROGRAM_SALES.SALESDISCOUNT
  is '折扣';
comment on column T_V_PROGRAM_SALES.SALESCATEGORY
  is '促销类型 1.预售  2.预订   3.限免 ';
comment on column T_V_PROGRAM_SALES.SALESSTARTTIME
  is '促销开始时间 格式如2014-09-19 ';
comment on column T_V_PROGRAM_SALES.SALESENDTIME
  is '促销结束时间 格式如2014-09-19 ';
comment on column T_V_PROGRAM_SALES.LUPDATE
  is '最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_PROGRAM_SALES
  add constraint PK_PROGRAM_SALES_ID primary key (ID);
-- Create/Recreate indexes 
create index IDX_PROGRAM_SALES_PID on T_V_PROGRAM_SALES (programid);




-- Create table
create table T_V_PROGRAM_SALES_MID
(
  ID             VARCHAR2(32) not null,
  programid     VARCHAR2(21),
  SALESPRODUCTID VARCHAR2(21),
  SALESDISCOUNT  VARCHAR2(20),
  SALESCATEGORY  NUMBER(2),
  SALESSTARTTIME VARCHAR2(19),
  SALESENDTIME   VARCHAR2(19),
  LUPDATE        DATE
);
-- Add comments to the columns 
comment on column T_V_PROGRAM_SALES_MID.ID
  is '主键ID';
comment on column T_V_PROGRAM_SALES_MID.programid
  is '节目ID';
comment on column T_V_PROGRAM_SALES_MID.SALESPRODUCTID
  is '促销产品ID';
comment on column T_V_PROGRAM_SALES_MID.SALESDISCOUNT
  is '折扣';
comment on column T_V_PROGRAM_SALES_MID.SALESCATEGORY
  is '促销类型 1.预售  2.预订   3.限免 ';
comment on column T_V_PROGRAM_SALES_MID.SALESSTARTTIME
  is '促销开始时间 格式如2014-09-19 ';
comment on column T_V_PROGRAM_SALES_MID.SALESENDTIME
  is '促销结束时间 格式如2014-09-19 ';
comment on column T_V_PROGRAM_SALES_MID.LUPDATE
  is '最后更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_V_PROGRAM_SALES_MID
  add constraint PK_PROGRAM_SALES_MID_ID primary key (ID);
-- Create/Recreate indexes 
create index IDX_PROGRAM_SALES_MID_PID on T_V_PROGRAM_SALES_MID (programid);


create sequence SEQ_T_V_PROGRAM_SALES_ID
minvalue 1
maxvalue 999999999999
start with 1
increment by 1
nocache
cycle;




create or replace procedure p_v_pdetail_delete_insert as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_v_delete_insert',

                                        '新基地视频节目详情数据中间表到正式表切换');

delete T_V_LABLES a
where exists (select 1 from T_V_LABLES_MID b where a.programid=b.programid);
insert into T_V_LABLES (ID, PROGRAMID, CMSID,LABELID, LABELNAME, LABELZONE,LUPDATE,EXETIME)
select ID, PROGRAMID, CMSID,LABELID, LABELNAME, LABELZONE,LUPDATE,EXETIME from (
select ID, PROGRAMID, CMSID,LABELID, LABELNAME, LABELZONE,LUPDATE,EXETIME,
       row_number()over(partition by c.programid,c.labelid order by LUPDATE desc) sort
  from T_V_LABLES_MID c
) where sort=1
;


delete T_V_VIDEOPIC d
where exists(select 1 from T_V_VIDEOPIC_MID e where d.programid=e.programid);
insert into T_V_VIDEOPIC(ID, PROGRAMID, PIC_00_TV, PIC_01_V, PIC_02_HSJ1080H, PIC_03_HSJ1080V,
                         PIC_04_HSJ720H, PIC_05_HSJ720V, PIC_06_sc, PIC_07_V34_sc, PIC_08_V23_sc, PIC_09_H32_sc, LUPDATE, EXETIME)
select ID, PROGRAMID, PIC_00_TV, PIC_01_V, PIC_02_HSJ1080H, PIC_03_HSJ1080V,
       PIC_04_HSJ720H, PIC_05_HSJ720V, PIC_06_sc, PIC_07_V34_sc, PIC_08_V23_sc, PIC_09_H32_sc, LUPDATE, EXETIME
from (
       select ID, PROGRAMID,PIC_00_TV, PIC_01_V, PIC_02_HSJ1080H, PIC_03_HSJ1080V,
              PIC_04_HSJ720H, PIC_05_HSJ720V, PIC_06_sc, PIC_07_V34_sc, PIC_08_V23_sc, PIC_09_H32_sc, LUPDATE, EXETIME,
              row_number()over(partition by f.programid order by LUPDATE desc) sort
        from T_V_VIDEOPIC_MID f
      )  where sort=1
;


delete T_V_VIDEOSPROPERTYS g
where exists(select 1 from T_V_VIDEOSPROPERTYS_MID h where g.programid=h.programid);
insert into T_V_VIDEOSPROPERTYS(ID, PROGRAMID, CMS_ID, PROPERTYKEY, PROPERTYVALUE, LUPDATE, EXETIME)
select ID, PROGRAMID, CMS_ID, PROPERTYKEY, PROPERTYVALUE, LUPDATE, EXETIME
from ( select ID, PROGRAMID, CMS_ID, PROPERTYKEY, PROPERTYVALUE, LUPDATE, EXETIME,
              row_number()over(partition by i.programid,i.propertykey,i.propertyvalue order by LUPDATE desc) sort
        from T_V_VIDEOSPROPERTYS_MID i
      )  where sort=1
;

delete T_V_VIDEOMEDIA j
where exists(select 1 from T_V_VIDEOMEDIA_MID k where j.programid=k.programid);
insert into T_V_VIDEOMEDIA
      (ID, PROGRAMID, CMS_ID, MEDIAFILEID, MEDIAFILENAME, SOURCEFILENAME, VISITPATH,
       MEDIAFILEPATH, MEDIAFILEPREVIEWPATH, MEDIAFILEACTION, MEDIASIZE, DURATION,
       MEDIATYPE, MEDIAUSAGECODE, MEDIACODEFORMAT, MEDIACONTAINFORMAT, MEDIACODERATE, MEDIANETTYPE,
       MEDIAMIMETYPE, MEDIARESOLUTION, MEDIAPROFILE, MEDIALEVEL, LUPDATE, EXETIME)
select ID, PROGRAMID, CMS_ID, MEDIAFILEID, MEDIAFILENAME, SOURCEFILENAME, VISITPATH,
       MEDIAFILEPATH, MEDIAFILEPREVIEWPATH, MEDIAFILEACTION, MEDIASIZE, DURATION,
       MEDIATYPE, MEDIAUSAGECODE, MEDIACODEFORMAT, MEDIACONTAINFORMAT, MEDIACODERATE, MEDIANETTYPE,
       MEDIAMIMETYPE, MEDIARESOLUTION, MEDIAPROFILE, MEDIALEVEL, LUPDATE, EXETIME
from (
       select ID, PROGRAMID, CMS_ID, MEDIAFILEID, MEDIAFILENAME, SOURCEFILENAME, VISITPATH,
              MEDIAFILEPATH, MEDIAFILEPREVIEWPATH, MEDIAFILEACTION, MEDIASIZE, DURATION,
              MEDIATYPE, MEDIAUSAGECODE, MEDIACODEFORMAT, MEDIACONTAINFORMAT, MEDIACODERATE, MEDIANETTYPE,
              MEDIAMIMETYPE, MEDIARESOLUTION, MEDIAPROFILE, MEDIALEVEL, LUPDATE, EXETIME,
              row_number()over(partition by l.programid,l.mediafileid,l.mediatype order by LUPDATE desc) sort
        from T_V_VIDEOMEDIA_MID l
      )  where sort=1
;


delete T_V_PROGRAM_SALES a
where exists (select 1 from T_V_PROGRAM_SALES_MID b where a.programid=b.programid);
insert into T_V_PROGRAM_SALES (ID, salesdiscount,salescategory,salesstarttime,salesendtime,lupdate,salesproductid,programid)
select ID, salesdiscount,salescategory,salesstarttime,salesendtime,lupdate,salesproductid,programid from (
select ID, salesdiscount,salescategory,salesstarttime,salesendtime,lupdate,salesproductid,programid,
       row_number()over(partition by c.programid,c.SALESPRODUCTID order by LUPDATE desc) sort
  from T_V_PROGRAM_SALES_MID c
) where sort=1
;

delete T_V_DPROGRAM m
where not exists (select 1 from T_V_SPROGRAM n where m.programid=n.programid);

delete T_V_DPROGRAM m
where exists (select 1 from T_V_SPROGRAM n where m.programid=n.programid and n.status = '22' and n.exestatus = '0');

update T_V_SPROGRAM set exestatus = '1',UPDATETIME=sysdate where status = '22' and exestatus = '0';

delete T_V_LABLES m
where not exists (select 1 from T_V_DPROGRAM n where m.programid=n.programid);

delete T_V_VIDEOPIC m
where not exists (select 1 from T_V_DPROGRAM n where m.programid=n.programid);

delete T_V_VIDEOSPROPERTYS m
where not exists (select 1 from T_V_DPROGRAM n where m.programid=n.programid);

delete T_V_VIDEOMEDIA m
where not exists (select 1 from T_V_DPROGRAM n where m.programid=n.programid);

delete T_V_LIVE m
where not exists (select 1 from T_V_DPROGRAM n where m.programid=n.programid);

delete T_V_REFERENCE m
where not exists (select 1 from T_V_DPROGRAM n where m.programid=n.programid);

delete T_V_PROGRAM_SALES m
where not exists (select 1 from T_V_DPROGRAM n where m.programid=n.programid);

 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end p_v_pdetail_delete_insert;

