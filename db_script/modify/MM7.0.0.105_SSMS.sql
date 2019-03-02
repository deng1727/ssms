create or replace procedure p_update_appid as
  v_nstatus     number;
  v_appid         varchar2(30);

  cursor my_cursor  is SELECT v.appid FROM t_r_gcontent t, v_pkg_mapping_output_full v WHERE (t.contentid = v.contentid AND t.appid IS NULL AND t.provider != 'B') ;
begin    
    v_nstatus := pg_log_manage.f_startlog('p_update_appid',
                                        'p_update_appid 同步开始');
        for myrow in my_cursor loop
            v_appid := myrow.appid;   
                insert into T_A_PPMS_PRETREATMENT_MESSAGE values(SEQ_PRETREATMENT_MESSAGE_ID.NEXTVAL,v_appid,to_char(sysdate,'yyyymmddhh24miss'),-1);
        end loop;
        commit;
        DBMS_OUTPUT.put_line('成功');
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
     DBMS_OUTPUT.put_line('出错了');
     v_nstatus := pg_log_manage.f_errorlog;
end p_update_appid;


variable job20 number;
begin
     dbms_job.submit(:job20,'p_update_appid;',sysdate,'sysdate+1/12');
     commit;
 end;
 
 
 
 --------------触点应用导出---------------
 ---------------将t_r_exportsql字段拓展成clob--------------------
alter table t_r_exportsql add ( exportsql2 clob) ;
update t_r_exportsql set exportsql2 = exportsql ;
alter table t_r_exportsql drop column exportsql;
alter table t_r_exportsql rename column exportsql2 to exportsql;


 insert into T_R_EXPORTSQL_GROUP (GROUPID, TOMAIL, MAILTITLE, STARTTIME, TIMETYPE, TIMETYPECON, FTPID, URL)
values (24, 'zhanghuan@aspirecn.com', '触点应用导出', '1000', '1', '7', '0', null);
commit;

insert into t_r_exportsql (ID, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID, ISVERF, EXPORTSQL)
values (130, '触点应用导出', '2', 50000, '0x01', null, 30, 'chudian', '/opt/aspire/product/mm_ssms/apache-tomcat-7.0.70/bin/ssms/ftpdata/searchfile/mo', 'GB18030', '2', '1', null, 24, '0', 'select ''999000001'' || ''000000000000000000'' || g.contentid,
       g.id,
       g.name,
       1 as aa,
       g.singer,
       1 as bb,
       g.spname,
       g.fulldevicename,
       ''O'' as cc,
       s.mobileprice,
       g.averagemark,
       g.marketdate,
       g.keywords,
       replace(replace(g.introduction, to_char(chr(13)), ''''),
               to_char(chr(10)),
               '''') as introduction,
       g.scantimes,
       g.appcateid,
       g.chargetime,
       g.pvcid,
       g.cityid,
       g.othernet,
       decode(g.provider, ''B'', ''99'', g.subtype) as subtype,
       g.platform,
       h.marketingcha,
       g.logo3,
       g.logo4,
       g.COPYRIGHTFLAG,
       v.packname,
       m.channelid,
       n.id,
       substr(b.type, 13, length(b.type))
  from t_r_gcontent g,
       t_r_base b,
       v_a_service s,
       V_CM_CONTENT_DARKHORSE h,
       v_unified_packname_app_ref v,
       (select contentid,
               xmlagg(xmlparse(content to_char(channelid) || ''|'' wellformed) order by contentid)
               .getclobval() as channelid
          from (select distinct g.contentid, p.channelid
                  from t_r_gcontent  g left join t_r_reference r on g.id = r.refnodeid
left join t_r_category c on r.categoryid = c.categoryid
left join t_pps_appforopen p on p.categoryid = c.id
                    )
         group by contentid) m,
       (select contentid,
               xmlagg(xmlparse(content to_char(id) || ''|'' wellformed) order by id)
               .getclobval() as id
          from (select distinct c.id, g.contentid
                  from t_r_gcontent  g left join t_r_reference r on g.id = r.refnodeid
left join t_r_category c on r.categoryid = c.categoryid
left join t_pps_appforopen p on p.categoryid = c.id
                    )
         group by contentid) n
 where g.id = b.id
   and g.contentid = m.contentid
   and g.contentid = n.contentid
   and g.contentid = h.contentid(+)
   and g.icpcode = s.icpcode
   and g.icpservid = s.icpservid
   and g.contentid = s.contentid
   and g.contentid = v.contentid
   and b.type in (''nt:gcontent:appGame'', ''nt:gcontent:appSoftWare'')
   and (g.subtype is null or
       (g.subtype <> ''6'' and g.subtype <> ''11'' and g.subtype <> ''12'' and
       g.subtype <> ''16'' and g.subtype <> ''21''))
   and (g.provider != ''B'' or (g.provider = ''B'' and g.programid = ''-1''))
   and g.CHANNELDISPTYPE = ''1''
   and (g.apptype != ''5'' or g.apptype is null)
   and g.contentid not in
       (select contentid from t_r_blacklist where isblack = ''1'')');