
create or replace view v_v_dprogram_set_fiter as 
 select d.programid COLLECTID,
       d.name COLLECTNAME,
       d.detail DESCRIPTION,
       '' TOTALPLAYNUM,
       p1.propertyvalue Director,
       p2.propertyvalue Actor,
       p3.propertyvalue ContentType,
       p4.pic_01_v pic1,
       p4.pic_00_tv pic2,
       d.displayname Category1,
       d.feetype
  from t_v_dprogram d,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, '|') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = '导演'
         group by t1.programid, t1.cms_id) p1,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, '|') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = '主演'
         group by t1.programid, t1.cms_id) p2,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, '|') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = '内容形态'
         group by t1.programid, t1.cms_id) p3,
         t_v_videopic  p4
 where d.FORMTYPE = 6
   and d.PRDPACK_ID <> '1003221'
    and d.programid = p1.programid(+)
   and d.programid = p2.programid(+)
   and d.programid = p3.programid(+)
   and d.programid = p4.programid(+)
   and d.cmsid = p1.cms_id(+)
   and d.cmsid = p2.cms_id(+)
   and d.cmsid = p3.cms_id(+);
   
  update t_r_exportsql set exportsql='select COLLECTID,COLLECTNAME,DESCRIPTION,TOTALPLAYNUM,Director,Actor,ContentType,pic1,pic2,Category1
     from (select t.*,
      row_number() over(partition by COLLECTNAME,Category1 order by feetype asc) as rank
  from v_v_dprogram_set_fiter t ) a   where a.rank=1' where id=89;

commit;




create or replace procedure p_del_null_singer as
  v_nstatus number;
  v_nrecod  number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_del_null_singer',
                                        '音乐歌手过滤处理');

  update t_mb_singer_new s set s.delflag=1  where not exists(select 1 from t_mb_music_new m where m.delflag=0 and m.singersid=s.sid) and s.delflag=0;
  commit;
  update t_mb_singer_new s set s.delflag=0  where exists(select 1 from t_mb_music_new m where m.delflag=0 and m.singersid=s.sid) and s.delflag=1;
  commit;

  --执行成功
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
  commit;
exception

  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;

end;
/

variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'p_del_null_singer;',
                      next_date => to_date('27-04-2017 05:15:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'SYSDATE + 1');
  commit;
end;
/


