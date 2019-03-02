drop sequence seq_t_free_dl_report_id;
drop sequence seq_t_free_dl_order_id;

drop table t_free_dl_report;
drop table t_free_dl_order;


drop table t_om_developer_company;
drop table t_om_developer_company_tra;
delete from t_r_exportsql_por where id=7;
delete from t_r_exportsql where id = 30;



drop table cm_feixin;
drop table cm_feixin_tra;
delete from t_r_exportsql_por where id= 8;
delete from t_r_exportsql where id = 31;

drop synonym ppms_om_feixin;
drop synonym ppms_om_developer_company;

-- Drop columns 
alter table T_MB_MUSIC_NEW drop column dolbytype;


-------视频增量同步脚本
drop table t_vo_video_mid;
drop table t_vo_live_mid;
drop table t_vo_node_mid;
drop table t_vo_product_mid;
drop table t_vo_program_mid;
drop table T_VO_VIDEODETAIL_MID;


---修改存储过程
create or replace procedure p_pushandreport as
v_nstatus     number;
v_nrecod      number;
begin

  v_nstatus := pg_log_manage.f_startlog('p_pushandreport',
                                        'ANDROID实时下载量表的维护');
--del t_a_push_his -10 day data
--delete from t_a_push_his where trunc(lupdate)<trunc(sysdate-10);
--add push -6 day to t_a_push_his
--insert into t_a_push_his select * from t_a_push where trunc(lupdate)<trunc(sysdate-6);
--del t_a_push -6 day data
--delete from t_a_push where trunc(lupdate)<trunc(sysdate-6);


--del t_a_pushreport_his -4 day data
--delete from t_a_pushreport_his where trunc(lupdate)<trunc(sysdate-4);
--add pushreport status != -1 to t_a_pushreport_his
--insert into t_a_pushreport_his select  * from t_a_pushreport where handle_status=0; --  -1：未处理，0：已经处理
--del t_a_pushreport status != -1
--delete from t_a_pushreport where handle_status =0;

  delete from t_a_report r where r.status!=-1;
  
  v_nrecod:=SQL%ROWCOUNT;
  commit;
  --v_nstatus := pg_log_manage.f_successlog();
  v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;

delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.115_SSMS' and LASTDBVERSION = 'MM2.0.0.0.109_SSMS';
commit;
