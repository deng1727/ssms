---------------------------在T_V_VIDEOPIC表中，新增4个字段  开始--------------------------------------------------------------
alter table T_V_VIDEOPIC add PIC_06_sc varchar2(500);
alter table T_V_VIDEOPIC add PIC_07_V34_sc varchar2(500);
alter table T_V_VIDEOPIC add PIC_08_V23_sc varchar2(500);
alter table T_V_VIDEOPIC add PIC_09_H32_sc varchar2(500);

comment on column T_V_VIDEOPIC.PIC_06_SC is '330x220 XX_sc.jpg';
comment on column T_V_VIDEOPIC.PIC_07_V34_sc is '360x480及等比以上 xxxx_V34_sc.jpg';
comment on column T_V_VIDEOPIC.PIC_08_V23_sc is '240x360及等比以上 xxxx_V23_sc.jpg';
comment on column T_V_VIDEOPIC.PIC_09_H32_sc is '510x340及等比以上 xxxx_V23_sc.jpg';

---------------------------在T_V_VIDEOPIC表中，新增4个字段  结束--------------------------------------------------------------


---------------------------在T_V_VIDEOPIC_MID表中，新增4个字段  开始----------------------------------------------------------
alter table T_V_VIDEOPIC_MID add PIC_06_sc varchar2(500);
alter table T_V_VIDEOPIC_MID add PIC_07_V34_sc varchar2(500);
alter table T_V_VIDEOPIC_MID add PIC_08_V23_sc varchar2(500);
alter table T_V_VIDEOPIC_MID add PIC_09_H32_sc varchar2(500);

comment on column T_V_VIDEOPIC_MID.PIC_06_SC is '330x220 XX_sc.jpg';
comment on column T_V_VIDEOPIC_MID.PIC_07_V34_sc is '360x480及等比以上 xxxx_V34_sc.jpg';
comment on column T_V_VIDEOPIC_MID.PIC_08_V23_sc is '240x360及等比以上 xxxx_V23_sc.jpg';
comment on column T_V_VIDEOPIC_MID.PIC_09_H32_sc is '510x340及等比以上 xxxx_V23_sc.jpg';
---------------------------在T_V_VIDEOPIC_MID表中，新增4个字段  结束-----------------------------------------------------------

---------------------------在t_v_dprogram表中，新增1个字段  开始--------------------------------------------------------------
alter table t_v_dprogram add detail varchar2(3000); 

comment on column t_v_dprogram.detail is '内容说明';
---------------------------在t_v_dprogram表中，新增1个字段  结束--------------------------------------------------------------

---------------------------修改存储过程p_v_pdetail_delete_insert 开始-------------------------------------------------------
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

delete T_V_DPROGRAM m
where not exists (select 1 from T_V_SPROGRAM n where m.programid=n.programid);

delete T_V_DPROGRAM m
where exists (select 1 from T_V_SPROGRAM n where m.programid=n.programid and n.status = '22');

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

 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;
---------------------------修改存储过程p_v_pdetail_delete_insert 结束 -------------------------------------------------------

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM5.0.0.0.305_SSMS','MM5.0.0.0.309_SSMS');
commit;