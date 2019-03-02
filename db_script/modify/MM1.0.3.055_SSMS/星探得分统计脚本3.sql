 
create or replace procedure P_TOTAL_SCORE as
  v_ccreatedate varchar2(6); --插入时间
  v_ncount      number; --记录数
  v_cerror      varchar2(200); --异常原因
  v_logid       number; --唯一ID
  v_csql        varchar2(1200); --执行SQL
  V_CSTATUS     CHAR(1); --执行状态
begin
  --写日志
  v_logid := pg_log_manage.f_startlog('P_TOTAL_SCORE', '星探得分统计');

  --获取记录产生的月份
  v_ccreatedate := to_char(add_months(sysdate, -1), 'yyyymm');

  --插入星探得分统计数据 
  v_csql := 'insert/*+ append */into t_total_Score  select msisdn,' || '''' ||
            v_ccreatedate || '''' || ',total from (' ||
            ' select rownum rn, a.msisdn, (a.total / 200) * 40 + (a.total / b.total) * 60 total  ' ||
            '   from v_total_comment_pkapps a, v_total_comment b where a.msisdn = b.msisdn ' ||
            '  order by (a.total / 200) * 40 + (a.total / b.total) * 60 desc)  where rn<=200';
  execute immediate v_csql;
  --sql产生的记录数
  v_ncount := sql%rowcount;

  --执行成功
  V_CSTATUS := pg_log_manage.f_successlog(v_logid);
  commit;
exception
  WHEN OTHERS THEN
    ROLLBACK;
    v_cerror := SQLERRM;
    v_cerror := SUBSTR(v_cerror, 1, 200);
    --记录执行失败的原因
    V_cSTATUS := PG_LOG_MANAGE.F_ERRORLOG(v_logid, v_cerror);
  
end;