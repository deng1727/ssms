alter table T_V_CATEGORY drop column video_status;
alter table T_V_CATEGORY drop column goods_status;
alter table T_V_CATEGORY drop column delpro_status;

alter table T_V_REFERENCE drop column verify_status;
alter table T_V_REFERENCE drop column delflag;
alter table T_V_REFERENCE drop column FEETYPE;
alter table T_V_REFERENCE drop column verify_date;

alter table t_v_dprogram drop column FEETYPE;

drop table T_V_CATEGORY_OPERATION;
drop sequence SEQ_V_APPROVAL_ID;

--------------------------
delete t_resource where RESOURCEKEY in('RESOURCE_POMSCATEGORY_RESULT_001','RESOURCE_POMSCATEGORY_RESULT_002','RESOURCE_POMSCATEGORY_RESULT_003','RESOURCE_POMSCATEGORY_RESULT_004','RESOURCE_POMSCATEGORY_RESULT_005','RESOURCE_POMSCATEGORY_RESULT_006');
---------------------------

---------------存储过程  begin----
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
end p_v_program_update;
---------------存储过程  end----

delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.129_SSMS' and LASTDBVERSION = 'MM4.0.0.0.109_SSMS';

commit;