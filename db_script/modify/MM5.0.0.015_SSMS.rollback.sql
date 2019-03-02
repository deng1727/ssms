

drop table t_v_apiRequestParamter;

alter table t_v_reference drop column broadcast;
alter table t_v_reference drop column countriy;
alter table t_v_reference drop column contentType;

create or replace procedure p_v_program_update as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_v_program_update',

                                        '基地新视频商品上架更新');
---插入新增加货架关系---
insert into t_v_hotcatemap
select SEQ_T_V_HOTCATEMAP_ID.Nextval id,
     d.displaytype,
      SEQ_T_V_CATEGORY_CID.NEXTVAL  as categoryid,
         '2',
         sysdate                       as lupdate
    from (select distinct  d.displaytype  from t_v_dprogram d
   where not exists (select 1
            from t_v_hotcatemap m1
           where d.displaytype = to_number(m1.titleid))) d;


----插入货架列表----
insert into t_v_category(id,categoryid,parentcid,cname,cdesc,isshow,sortid,lupdate)
select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,d.categoryid,'101', d.displayname,d.displayname,'1','1',sysdate as lupdate
from (select distinct m.categoryid,d.displayname
  from t_v_hotcatemap m,t_v_dprogram d
 where to_number(m.titleid) = d.displaytype and m.type = '2' and   not exists (select 1
          from t_v_category c
         where m.categoryid = c.categoryid))d ;


-----删除下线的
----------
delete from t_v_reference t where not exists(select 1 from t_v_dprogram d where d.programid=t.programid);

----插入上架未上架一级分类货架的节目到商品表---
----
insert into t_v_reference(ID,PROGRAMID,CATEGORYID,CMS_ID,PNAME,SORTID,FEETYPE,LUPDATE)
select SEQ_T_V_REFERENCE_ID.NEXTVAL as id, d.programid,d.categoryid, cms_id, pname,rownum as sortid,d.FEETYPE,sysdate as lupdate
from (select distinct d.programid,m1.categoryid,d.cmsid as cms_id,d.name as pname,d.FEETYPE
  from t_v_dprogram d,t_v_hotcatemap m1
 where d.displaytype=to_number(m1.titleid) and  not exists (select 1
          from t_v_reference r, t_v_hotcatemap m
         where r.categoryid = m.categoryid
           and m.type = '2'
           and to_number(m.titleid) = d.displaytype
            and r.programid=d.programid)) d ;



 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end p_v_program_update;

delete DBVERSION where PATCHVERSION = 'MM5.0.0.0.105_SSMS' and LASTDBVERSION = 'MM4.0.0.0.129_SSMS';

commit;