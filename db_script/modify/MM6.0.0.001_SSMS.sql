CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin_software
as
     z NUMBER(12,6);
     z1 NUMBER(12,6);
     z2 NUMBER(12,6);
     b NUMBER(14,6);
     d1 NUMBER(12,6);
     d2 NUMBER(12,6);
     d3 NUMBER(14,6);
     m1 NUMBER(12,6);
     m2 NUMBER(12,6);
     m1max NUMBER(12,6);
     m1min NUMBER(12,6);
     m2max NUMBER(12,6);
     m2min NUMBER(12,6);
     a NUMBER(12,6);
     j NUMBER(12,6);

  v_nstatus number;
    v_nrecod  number;

cursor s_cur is
        select * from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID='1001' and content_id in (select contentid from t_r_gcontent);
begin
 v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','新榜单计算P_mt_rank_index_fin_software');
  -- delete from MID_O_TABLE
     execute immediate'TRUNCATE TABLE MID_O_TABLE';
   Insert into MID_O_TABLE(PRD_TYPE_ID,b,m1max,m1min,m2max,m2min) select PRD_TYPE_ID,sum(d3),
max((d1+d2+d3)/(ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(ADD_DL_CNT*0.2+d1+d2+d3+1)),
min((d1+d2+d3)/(ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(ADD_DL_CNT*0.2+d1+d2+d3+1)),
max(((fee+fee1+fee2)/(add_fee+1))*ln(add_fee+1)),
min(((fee+fee1+fee2)/(add_fee+1))*ln(add_fee+1))
 from(
select l.PRD_TYPE_ID PRD_TYPE_ID,fee,fee1,fee2,add_fee,ADD_DL_CNT,(r1.CLASS_DL_CNT*0.15+r1.SEARCH_DL_CNT*0.4+r1.HOT_DL_CNT*0.25+r1.MAN_DL_CNT*0.2)*exp(r1.COM_CNT/a1) a,
    (r1.CLASS_DL_CNT*0.15+r1.SEARCH_DL_CNT*0.4+r1.HOT_DL_CNT*0.25+r1.MAN_DL_CNT*0.2)*exp(r1.COM_CNT/a2) d3,
    (r1.CLASS_DL_CNT1*0.15+r1.SEARCH_DL_CNT1*0.4+r1.HOT_DL_CNT1*0.25+r1.MAN_DL_CNT1*0.2)*exp(r1.COM_CNT1/a3) d2,
    (r1.CLASS_DL_CNT2*0.15+r1.SEARCH_DL_CNT2*0.4+r1.HOT_DL_CNT2*0.25+r1.MAN_DL_CNT2*0.2)*exp(r1.COM_CNT2/a4) d1 from t_mt_rank_index_d r1,(
    select PRD_TYPE_ID, sum(COM_CNT) a1,
   sum(COM_CNT) a2,
    sum(COM_CNT1) a3,
   sum(COM_CNT2) a4
    from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID in (1001,1003)
    group by PRD_TYPE_ID
    )l
    where r1.dl_15days_cnt>50
    and r1.prd_type_id=l.PRD_TYPE_ID)o group by PRD_TYPE_ID;


  --execute immediate'TRUNCATE TABLE MID_TABLE';
  --delete from MID_TABLE where PRD_TYPE_ID='1001';
 for r in s_cur loop

 select  PRD_TYPE_ID,b,m1max,m1min ,m2max,m2min
into j,b,m1max,m1min,m2max,m2min
 from MID_O_TABLE where PRD_TYPE_ID=r.PRD_TYPE_ID;

    select (r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT))/b
    *ln(b),r.fee/sum(fee)*ln(sum(fee)),(r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT)),
     (r.CLASS_DL_CNT1*0.15+r.SEARCH_DL_CNT1*0.4+r.HOT_DL_CNT1*0.25+r.MAN_DL_CNT1*0.2)*exp(r.COM_CNT1/sum(COM_CNT1)),
     (r.CLASS_DL_CNT2*0.15+r.SEARCH_DL_CNT2*0.4+r.HOT_DL_CNT2*0.25+r.MAN_DL_CNT2*0.2)*exp(r.COM_CNT2/sum(COM_CNT2))
    into  z1,z2,d3,d2,d1 from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID=r.PRD_TYPE_ID;
     z:=z2*0.05+z1*0.95;
     m1:=(d1+d2+d3)/(r.ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(r.ADD_DL_CNT*0.2+d1+d2+d3+1);
     m2:=((r.fee+r.fee1+r.fee2)/(r.add_fee+1))*ln(r.add_fee+1);
    insert into MID_TABLE values(r.content_id,z,((m1-m1min) / (m1max-m1min))*0.7+((m2-m2min) / (m2max-m2min))*0.3);
    end loop;
    commit;
     v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    exception
    when others then
    rollback;
     v_nstatus := pg_log_manage.f_errorlog;
end;




CREATE OR REPLACE PROCEDURE P_mt_rank_index_fin_game
as
     z NUMBER(12,6);
     z1 NUMBER(12,6);
     z2 NUMBER(12,6);
     b NUMBER(14,6);
     d1 NUMBER(12,6);
     d2 NUMBER(12,6);
     d3 NUMBER(14,6);
     m1 NUMBER(12,6);
     m2 NUMBER(12,6);
     m1max NUMBER(12,6);
     m1min NUMBER(12,6);
     m2max NUMBER(12,6);
     m2min NUMBER(12,6);
     a NUMBER(12,6);
     j NUMBER(12,6);

  v_nstatus number;
    v_nrecod  number;

cursor s_cur is
        select * from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID='1003' and content_id in (select contentid from t_r_gcontent);

begin
 v_nstatus := pg_log_manage.f_startlog('P_mt_rank_index_fin','新榜单计算P_mt_rank_index_fin_game');
  -- delete from MID_O_TABLE
     execute immediate'TRUNCATE TABLE MID_O_TABLE';
   Insert into MID_O_TABLE(PRD_TYPE_ID,b,m1max,m1min,m2max,m2min) select PRD_TYPE_ID,sum(d3),
max((d1+d2+d3)/(ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(ADD_DL_CNT*0.2+d1+d2+d3+1)),
min((d1+d2+d3)/(ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(ADD_DL_CNT*0.2+d1+d2+d3+1)),
max(((fee+fee1+fee2)/(add_fee+1))*ln(add_fee+1)),
min(((fee+fee1+fee2)/(add_fee+1))*ln(add_fee+1))
 from(
select l.PRD_TYPE_ID PRD_TYPE_ID,fee,fee1,fee2,add_fee,ADD_DL_CNT,(r1.CLASS_DL_CNT*0.15+r1.SEARCH_DL_CNT*0.4+r1.HOT_DL_CNT*0.25+r1.MAN_DL_CNT*0.2)*exp(r1.COM_CNT/a1) a,
    (r1.CLASS_DL_CNT*0.15+r1.SEARCH_DL_CNT*0.4+r1.HOT_DL_CNT*0.25+r1.MAN_DL_CNT*0.2)*exp(r1.COM_CNT/a2) d3,
    (r1.CLASS_DL_CNT1*0.15+r1.SEARCH_DL_CNT1*0.4+r1.HOT_DL_CNT1*0.25+r1.MAN_DL_CNT1*0.2)*exp(r1.COM_CNT1/a3) d2,
    (r1.CLASS_DL_CNT2*0.15+r1.SEARCH_DL_CNT2*0.4+r1.HOT_DL_CNT2*0.25+r1.MAN_DL_CNT2*0.2)*exp(r1.COM_CNT2/a4) d1 from t_mt_rank_index_d r1,(
    select PRD_TYPE_ID, sum(COM_CNT) a1,
   sum(COM_CNT) a2,
    sum(COM_CNT1) a3,
   sum(COM_CNT2) a4
    from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID in (1001,1003)
    group by PRD_TYPE_ID
    )l
    where r1.dl_15days_cnt>50
    and r1.prd_type_id=l.PRD_TYPE_ID)o group by PRD_TYPE_ID;


  execute immediate'TRUNCATE TABLE MID_TABLE';
  --delete  from MID_TABLE where PRD_TYPE_ID='1003';
 for r in s_cur loop

 select  PRD_TYPE_ID,b,m1max,m1min ,m2max,m2min
into j,b,m1max,m1min,m2max,m2min
 from MID_O_TABLE where PRD_TYPE_ID=r.PRD_TYPE_ID;

    select (r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT))/b
    *ln(b),r.fee/sum(fee)*ln(sum(fee)),(r.CLASS_DL_CNT*0.15+r.SEARCH_DL_CNT*0.4+r.HOT_DL_CNT*0.25+r.MAN_DL_CNT*0.2)*exp(r.COM_CNT/sum(COM_CNT)),
     (r.CLASS_DL_CNT1*0.15+r.SEARCH_DL_CNT1*0.4+r.HOT_DL_CNT1*0.25+r.MAN_DL_CNT1*0.2)*exp(r.COM_CNT1/sum(COM_CNT1)),
     (r.CLASS_DL_CNT2*0.15+r.SEARCH_DL_CNT2*0.4+r.HOT_DL_CNT2*0.25+r.MAN_DL_CNT2*0.2)*exp(r.COM_CNT2/sum(COM_CNT2))
    into  z1,z2,d3,d2,d1 from t_mt_rank_index_d where DL_15DAYS_CNT>50 and PRD_TYPE_ID=r.PRD_TYPE_ID;
     z:=z2*0.35+z1*0.65;
     m1:=(d1+d2+d3)/(r.ADD_DL_CNT*0.2+d1+d2+d3+1)*ln(r.ADD_DL_CNT*0.2+d1+d2+d3+1);
     m2:=((r.fee+r.fee1+r.fee2)/(r.add_fee+1))*ln(r.add_fee+1);
    insert into MID_TABLE values(r.content_id,z,((m1-m1min) / (m1max-m1min))*0.7+((m2-m2min) / (m2max-m2min))*0.3);
    end loop;
    commit;
     v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
    exception
    when others then
    rollback;
     v_nstatus := pg_log_manage.f_errorlog;
end;
