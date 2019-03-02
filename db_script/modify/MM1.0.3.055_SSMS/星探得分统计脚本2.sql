create or replace package body pg_log_manage is


--程序开始运行

function f_startlog(
        --vc_programid varchar2,
        vc_programname varchar2,
        vc_progradesc varchar2
)return number
is
vn_LOGID number;
begin
select log_seq.nextval into vn_logid from dual;
insert into SUBS_LOG (logid,taskname,description,taskbegintime,status)
values(vn_logid,upper(vc_programname),vc_progradesc,sysdate,upper('running'));
commit;
return (vn_logid);
exception

  when others then
   return (1);
  end;

--程序运行成功
function f_successlog(
         vn_logid       number,
       -- vc_programid varchar2,
        --vc_programname varchar2,
     --vd_enddate date :=null,
      vn_RECORDNUM number:=null
           )return number
           is
 begin

 update  SUBS_LOG
     set status  = upper('finished'),
    RECORDNUM=vn_RECORDNUM,
    TASKENDTIME = sysdate
    where logid = vn_logid;
    commit;
    return (0);
 exception

   when others then
    return (1);
    end;

--程序运行失败
 function f_errorlog(

     vn_logid       number,
       -- vc_progname    varchar2,
        vc_errmsg      varchar2

 )return number

 is
 begin

 update SUBS_LOG

    set status  = upper('exception'),
    ERRMSG=vc_errmsg,
    taskendtime = sysdate
    where logid = vn_logid;
    return (0);
    commit;
     exception

   when others then
    return (1);


    end;
    end;
