-- 201412基地视频改版

----------创建存储过程 开始---------

---视频节目详情数据中间表到正式表切换存储过程---

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
                         PIC_04_HSJ720H, PIC_05_HSJ720V, PIC_06, PIC_07, PIC_08, PIC_99, LUPDATE, EXETIME)
select ID, PROGRAMID, PIC_00_TV, PIC_01_V, PIC_02_HSJ1080H, PIC_03_HSJ1080V,
       PIC_04_HSJ720H, PIC_05_HSJ720V, PIC_06, PIC_07, PIC_08, PIC_99, LUPDATE, EXETIME
from (
       select ID, PROGRAMID,PIC_00_TV, PIC_01_V, PIC_02_HSJ1080H, PIC_03_HSJ1080V,
              PIC_04_HSJ720H, PIC_05_HSJ720V, PIC_06, PIC_07, PIC_08, PIC_99, LUPDATE, EXETIME,
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



---视频节目上架存储过程---

create or replace procedure p_v_program_update as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_v_program_update',

                                        '基地新视频商品上架更新');


-----删除下线的
----------
delete from t_v_reference t where not exists(select 1 from t_v_dprogram d where d.programid=t.programid);

----插入上架未上架一级分类货架的节目到商品表---
----
insert into t_v_reference s 
select SEQ_T_V_REFERENCE_ID.NEXTVAL as id, d.programid,m1.categoryid,d.cmsid as cms_id,d.name as pname,rownum as sortid,sysdate as lupdate
  from t_v_dprogram d,t_v_hotcatemap m1
 where d.displaytype=to_number(m1.titleid) and  not exists (select 1
          from t_v_reference r, t_v_hotcatemap m
         where r.categoryid = m.categoryid
           and m.type = '2'
           and to_number(m.titleid) = d.displaytype 
            and r.programid=d.programid) ;



 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;


---视频热点主题货架更新存储过程---

create or replace procedure p_v_hotcatemap_update as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_v_hotcatemap_update',

                                        '基地新视频热点主题货架关系更新');


-----删除不存在的热点出题货架关系----
delete from t_v_hotcatemap m where m.type = '1' and not exists(select 1 from t_v_hotcontent h where h.titleid=m.titleid);
-----删除热点出题货架列表----
delete from t_v_category c where c.parentcid = '102' ;

----插入新增加的热点主题货架关系---

insert into t_v_hotcatemap(id,titleid,categoryid,type,lupdate)
select SEQ_T_V_HOTCATEMAP_ID.NEXTVAL as id, h.titleid,SEQ_T_V_CATEGORY_CID.NEXTVAL as categoryid,'1',sysdate as lupdate
  from t_v_hotcontent h
 where  not exists (select 1
          from t_v_hotcatemap m
         where h.titleid = m.titleid
           and m.type = '1') ;

----插入热点主题货架列表----
insert into t_v_category(id,categoryid,parentcid,cname,cdesc,isshow,sortid,lupdate)
select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,m.categoryid,'102', h.titlename,h.titlename,'1','1',sysdate as lupdate
  from t_v_hotcatemap m,t_v_hotcontent h
 where m.titleid = h.titleid and m.type = '1' and   not exists (select 1
          from t_v_category c
         where m.categoryid = c.categoryid) ;

 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;


----------创建存储过程 结束---------
