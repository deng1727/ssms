

alter table t_r_exportsql
add (isVerf varchar2(1) default '0' not null);

  comment on column t_r_exportsql.isVerf
  is '0为不需要导出校验文件(默认),1为需要导出校验文件';


insert into T_R_EXPORTSQL_GROUP values('22','zhanghuan@aspirecn.com','必备应用导出','0600','1','7','0','');




insert into t_r_exportsql (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID, ISVERF)
values (106, 'select b.contentid,
       b.name 

,
       t.clientid,
       b.catename,
       b.appcatename,
       h,
       '''',
       ''http://a.10086.cn/pams2/m/s.do?gId=100002328100930100000329951 

'' ||
       b.contentid || ''&'' || ''c=172'' || ''&'' || ''j=l'' || ''&'' || ''p=72'' || ''&'' ||
       ''blockId=17359'' || ''&'' || ''positionid=2766658'' || ''&'' ||
       ''cuid=66857418'',
       t.cdnurl,
       ''1''
  from (select a.contentid,
               a.name 

,
               a.catename,
               a.appcatename,
               wmsys.wm_concat(a.tagname) as h
          from (select g.contentid,
                       g.name 

,
                       g.catename,
                       g.appcatename,
                       m.tagname
                  from t_r_gcontent g, T_CLMS_CONTENTTAG c, T_CLMS_TAG m
                 where g.contentid = c.contentid
                   and c.delflag = 0
                   and c.tagid = m.tagid) a
         group by a.contentid, a.name 

, a.catename, a.appcatename) b,
       (select *
          from (select r.*,
                       row_number() over(partition by r.contentid order by r.device_id desc) rn
                  from t_a_cm_device_resource r
                 where r.iscdn = ''1'')
         where rn = 1) t
 where b.contentid = t.contentid', '必备应用推荐导出', '2', 50000, '0x1F', to_date('03-09-2018 19:48:00', 'dd-mm-yyyy hh24:mi:ss'), 10, 'i_mm-app_%YYYYMMDD%', 'D://work', 'UTF-8', '2', '1', '499', 22, '1');
 
 commit;
 
 alter table t_r_gcontent
add (developer varchar2(100) );

  comment on column t_r_gcontent.developer
  is '开发者';
  
commit;
 