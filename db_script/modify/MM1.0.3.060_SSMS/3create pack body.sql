create or replace package body pg_log_manage is

  --程序开始运行

  function f_startlog(
                      --vc_programid varchar2,
                      vc_programname varchar2, --过程名
                      vc_progradesc  varchar2, --过程描述与执行程序块
                      vc_remark      varchar2 default null --备注信息，小于等于500个字符
                      ) return number is
    vn_LOGID number;
  begin
    select log_seq.nextval into vn_logid from dual;
    --程序开始运行，在日志表插入一条日志记录
    insert into SUBS_LOG
      (logid, taskname, description, taskbegintime, status, Remark)
    values
      (vn_logid,
       upper(vc_programname),
       vc_progradesc,
       sysdate,
       upper('running'),
       decode(vc_remark, null, '', 'STARTLOG:') || vc_remark);
    commit;
    v_vglogid:=vn_logid;
    return(vn_logid);
  exception

    when others then
      return(1);
  end;

  --程序运行成功
  function f_successlog(vn_logid number default v_vglogid, --日志唯一ID，缺省值是序列
                        -- vc_programid varchar2,
                        --vc_programname varchar2,
                        --vd_enddate date :=null,
                        vn_RECORDNUM number default SQL%ROWCOUNT, --执行程序块执行的记录数
                        vc_remark    varchar2 default null --备注信息，小于等于500个字符
                        ) return number is
  begin
    --程序执行成功后，更新执行程序块执行的记录数和执行程序结束的时间点
    update SUBS_LOG
       set status      = upper('finished'),
           RECORDNUM   = vn_RECORDNUM,
           TASKENDTIME = sysdate,
           remark      = remark||decode(vc_remark, null, '', '--SUCCESSLOG:') ||
                         vc_remark
     where logid = vn_logid;
    commit;
    return(0);
  exception

    when others then
      return(1);
  end;

  --程序运行失败
  function f_errorlog(

                      vn_logid number default v_vglogid,
                      -- vc_progname    varchar2,
                      vc_errmsg varchar2 default SUBSTR(SQLERRM, 1, 200), --异常信息
                      vc_remark varchar2 default null --备注信息,小于等于500个字符

                      ) return number

   is
  begin
    --程序失败后，更新日志表，记录错误原因
    update SUBS_LOG

       set status      = upper('exception'),
           ERRMSG      = vc_errmsg,
           taskendtime = sysdate,
           remark      = remark||decode(vc_remark, null, '', '--ERRORLOG:') ||
                         vc_remark
     where logid = vn_logid;
    return(0);
    commit;
  exception

    when others then
      return(1);

  end;
end;
