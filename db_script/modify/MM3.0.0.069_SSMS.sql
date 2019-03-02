---新增 营销信息表增量同步，业务表增量同步接口----------
insert into t_r_exportsql(exporttypeother,id,exportsql,exportname,exporttype,exportpagenum,exportline,filename, filepath,encoder,exportbyauto,groupid)
     values(',',61,'select t.ID,t.CONTENTID,t.NAME,t.SPNAME,t.TYPE,t.DISCOUNT,t.DATESTART,t.DATEEND,t.TIMESTART,t.TIMEEND,t.LUPDATE,t.USERID,t.MOBILEPRICE,t.EXPPRICE,t.ICPCODE,t.ISRECOMM from T_CONTENT_EXT t where t.LUPDATE>(select e.lasttime from t_r_exportsql e where e.id = ''59'') order by t.ID',
     '营销信息表增量同步','1',50000,16,'s_MKT_%YYYYMMDDHH24%_%NNN%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','UTF-8','2','8');

insert into t_r_exportsql(exporttypeother,id,exportsql,exportname,exporttype,exportpagenum,exportline,filename, filepath,encoder,exportbyauto,groupid)
     values(',',60,'select s.contentid,s.icpcode,s.spname,s.spshortname,s.icpservid,s.servname,s.servstatus,s.umflag,s.servtype,s.chargetype,s.paytype,s.mobileprice,s.dotcardprice,s.chargedesc,s.providertype,s.servattr,s.servdesc,s.pksid,s.lupddate from V_SERVICE s where s.lupddate>(select e.lasttime from t_r_exportsql e where e.id = ''60'') order by s.contentid',
     '业务表增量同步','1',50000,19,'s_SVC_%YYYYMMDDHH24%_%NNN%','/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www','UTF-8','2','8');

----------------创业大赛2013新版-------------------

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (78, '新建创业大赛货架之新流程全部作品货架规则', 'select id from t_r_gcontent g,T_R_DOWN_SORT_NEW s where  g.subtype!=''6'' and g.contentid = s.content_id(+)  and g.ISMMTOEVENT is not null');

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (79, '新建创业大赛货架之客户端和wap门户货架规则', 'select id from t_r_gcontent g,cm_content s where g.subtype!=''6'' and g.contentid = s.contentid(+)');

-- Create the synonym 
create or replace synonym om_developer_company
  for om_developer_company@DL_MM_PPMS_NEW;
 create or replace view ppms_v_cm_content_02 as
select a.contentid,c.contestgroup from cm_content a,om_developer_company b,om_developer_contest c 
where a.companyid = b.companyid and b.developerid = c.developerid;

 
 create or replace view ppms_v_cm_content_01 as
 select contentid,VOICECAPACITY,WINTYPE from cm_content;     
 
alter   table   t_r_gcontent   add(ISMMTOEVENT   varchar2(1));
comment on column t_r_gcontent.ISMMTOEVENT is '创业大赛标志 0为普通参赛、1为一键参赛、非创业大赛该字段为空';

alter   table   v_cm_content   add(ISMMTOEVENT   varchar2(1));
comment on column v_cm_content.ISMMTOEVENT is '创业大赛标志 0为普通参赛、1为一键参赛、非创业大赛该字段为空';

alter   table   t_r_gcontent   add(CopyrightFlag   varchar2(1) default'0');
comment on column t_r_gcontent.CopyrightFlag is '是否正版标示 1:是';

alter   table   v_cm_content   add(CopyrightFlag   varchar2(1) default'0');
comment on column v_cm_content.CopyrightFlag is '是否正版标示  1:是';
alter table T_R_GCONTENT modify CopyrightFlag default 0 not null;

---按时增量导出---
update t_r_exportsql set exportsql='select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
      c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
      c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
      c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
      c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
      c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,
      c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT from t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s where c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = s.content_id(+)   and to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and c.plupddate is not null and length(c.plupddate) = 19 UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', t.contentid, '''', '''',  '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null, null, null, null, '''', null, '''', null, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), '''', '''',   to_clob(''''), '''', '''', null, '''', '''', ''D'','''', '''','''','''',null  from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''40'') and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by id'
      , exportline='85' where id='40';

update t_r_exportsql set exportsql='select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,
c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),'''',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT from t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,(select * from T_R_DOWN_SORT_NEW where os_id=''9'') s where  c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = s.content_id(+)  and C.SUBTYPE IN (''1'',''2'',''5'',''6'',''7'',''8'',''11'')  order by c.id',
 exportline='85' where id='2';      
      
update t_r_exportsql set exportsql='select c.id,c.name,c.catename,c.appcatename,c.appcateid,c.spname,c.icpcode,c.icpservid,c.contenttag,c.auditionurl,c.introduction,
c.nameletter,c.singerletter,c.downloadtimes,c.settimes,c.bigcatename,c.contentid,c.companyid,c.productid,c.keywords,c.createdate,c.marketdate,c.lupddate,
c.language,c.wwwpropapicture1,c.wwwpropapicture2,c.wwwpropapicture3,c.clientpreviewpicture1,c.clientpreviewpicture2,c.clientpreviewpicture3,c.clientpreviewpicture4,
c.provider,c.handbook,c.manual,c.handbookpicture,c.userguide,c.userguidepicture,c.gamevideo,c.logo1,c.logo2,c.logo3,c.cartoonpicture,c.dayordertimes,
c.weekordertimes,c.monthordertimes,c.ordertimes,c.averagemark,c.issupportdotcard,c.programsize,c.programid,c.onlinetype,c.version,c.picture1,c.picture2,
c.picture3,c.picture4,c.picture5,c.picture6,c.picture7,c.picture8,c.platform,c.chargetime,c.logo4,c.brand,c.servattr,c.subtype,c.pvcid,c.cityid,
c.match_deviceid,c.expprice,c.fulldeviceid,c.plupddate,c.othernet,c.richappdesc,c.advertpic,c.pricetype,c.comparednumber,c.funcdesc,decode(c.logo5,null,c.logo4,'''',c.logo4,c.logo5),''A'',f.VOICECAPACITY,f.WINTYPE,g.CONTESTGROUP,c.ISMMTOEVENT,s.ADD_ORDER_COUNT from t_r_gcontent c,ppms_v_cm_content_01 f,ppms_v_cm_content_02 g,(select * from T_R_DOWN_SORT_NEW  where os_id=''9'') s where  c.contentid=f.contentid(+) and c.contentid=g.contentid(+) and c.contentid = s.content_id(+)  and  to_date(c.plupddate, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') and c.plupddate is not null and length(c.plupddate) = 19  AND C.SUBTYPE IN (''1'',''2'',''5'',''6'',''7'',''8'',''11'') UNION ALL select '''', t.contentname, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null,  '''', t.contentid, '''', '''',  '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', null, null, null, null, null, '''', null, '''', null, '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''', '''',  to_clob(''''),  null,  to_clob(''''), '''', '''',   to_clob(''''), '''', '''', null, '''', '''',''D'', '''', '''','''','''',null  from t_syn_result t where to_date(t.syntime, ''yyyy-mm-dd hh24:mi:ss'') > (select e.lasttime from t_r_exportsql e where e.id = ''6'') and t.syntype = ''3'' and t.syntime is not null and length(t.syntime) = 19 order by id',
exportline='85' where id='6';



create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,
              '10',
              c.oviappid,
              '12',
              c.oviappid,
        '16',
              c.oviappid,
              c.contentcode) ContentCode,
       c.Keywords,
       decode(c.status,
              '0006',
              decode(f.status, 2, '0006', 5, '0008'),
              '1006',
              decode(f.status, 2, '0006', 5, '0008'),
              '0015',
              decode(f.status || f.substatus, '61', '0006', '0008'),
              '1015',
              decode(f.status || f.substatus, '61', '0006', '0008')) as status,
       decode(c.status, '0015', 'L', '1015', 'L', d.servattr) as ServAttr,
       c.createdate,
       decode(c.status,
              '0006',
              f.onlinedate,
              '1006',
              f.onlinedate,
              f.SubOnlineDate) as marketdate,
       --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM创业计划优秀应用展示',
                     e.companyname)) as companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----增加应用更新时间
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype) thirdapptype,
       c.pvcid,
       c.citysid as cityid,
       decode(f.chargetime ||
              decode(c.chargetype, '01', '02', c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
              '1',
              '0') as othernet,
              c.ismmtoevent,
              c.COPYRIGHTFLAG 
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f --
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006' or f.status = 5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status = 6)))
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
   and d. ProductStatus in ('2', '3', '5') ----产品上线计费or不计费or 下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in ('1', '2', '7', '11', '12', '13', '14','16','21') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'));
             
------------创业大赛2013新版 end----------------

----------------视频文件全量导入 start-------------------
create table T_VO_VIDEO_FULL
(
  VIDEOID          VARCHAR2(60),
  CODERATEID       VARCHAR2(6),
  FILEPATH         VARCHAR2(512),
  DOWNLOADFILEPATH VARCHAR2(512),
  FILESIZE         NUMBER(12),
  UPDATETIME       DATE  default sysdate,
  STATUS           VARCHAR2(2),
  HASHC            VARCHAR2(60)
)
;

comment on table T_VO_VIDEO_FULL
  is '视频全量文件数据导入临时表';
comment on column T_VO_VIDEO_FULL.VIDEOID
  is '视频标识';
comment on column T_VO_VIDEO_FULL.CODERATEID
  is '码率标识';
comment on column T_VO_VIDEO_FULL.FILEPATH
  is '物理播放路径';
comment on column T_VO_VIDEO_FULL.DOWNLOADFILEPATH
  is '物理下载路径';
comment on column T_VO_VIDEO_FULL.FILESIZE
  is '文件大小';
  comment on column T_VO_VIDEO_FULL.STATUS
  is '操作类型。';
  comment on column T_VO_VIDEO_FULL.UPDATETIME
  is '更新时间。';
  comment on column T_VO_VIDEO_FULL.HASHC
  is 'hash值:由VIDEOID、CODERATEID、FILEPATH、DOWNLOADFILEPATH、FILESIZE这5个字段拼接计算得出';

alter table t_vo_video_mid add HASHC VARCHAR2(60);
 comment on column t_vo_video_mid.HASHC
  is 'hash值:由VIDEOID、CODERATEID、FILEPATH、DOWNLOADFILEPATH、FILESIZE这5个字段拼接计算得出';

alter table t_vo_video add HASHC VARCHAR2(60);
 comment on column t_vo_video.HASHC
  is 'hash值:由VIDEOID、CODERATEID、FILEPATH、DOWNLOADFILEPATH、FILESIZE这5个字段拼接计算得出';


----创建p_videofull_delete_insert存储过程开始------------
create or replace procedure p_videofull_delete_insert as
  v_countnum number; --记录数据大小
  v_nstatus  number;
  v_nrecod   number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_videofull_delete_insert',

                                        '全量同步视频相关数据');

  select count(1) into v_countnum from T_VO_VIDEO_FULL;
  if v_countnum > 10000000 then
    begin
      insert into T_VO_VIDEO_MID
        (VIDEOID,
         CODERATEID,
         FILEPATH,
         FILESIZE,
         DOWNLOADFILEPATH,
         UPDATETIME,
         HASHC,
         status)
        select VIDEOID,
               CODERATEID,
               FILEPATH,
               FILESIZE,
               DOWNLOADFILEPATH,
               UPDATETIME,
               HASHC,
               'A'
          from T_VO_VIDEO_FULL a
         where not exists (select 1
                  from T_VO_VIDEO b
                 where a.videoid = b.videoid
                   and a.coderateid = b.coderateid);

      insert into T_VO_VIDEO_MID
        (VIDEOID,
         CODERATEID,
         FILEPATH,
         FILESIZE,
         DOWNLOADFILEPATH,
         UPDATETIME,
         HASHC,
         status)
        select VIDEOID,
               CODERATEID,
               FILEPATH,
               FILESIZE,
               DOWNLOADFILEPATH,
               UPDATETIME,
               HASHC,
               'U'
          from T_VO_VIDEO_FULL a
         where exists (select 1
                  from T_VO_VIDEO b
                 where a.videoid = b.videoid
                   and a.coderateid = b.coderateid
                   and a.hashc <> b.hashc);

      insert into T_VO_VIDEO_MID
        (VIDEOID,
         CODERATEID,
         FILEPATH,
         FILESIZE,
         DOWNLOADFILEPATH,
         UPDATETIME,
         HASHC,
         status)
        select VIDEOID,
               CODERATEID,
               FILEPATH,
               FILESIZE,
               DOWNLOADFILEPATH,
               UPDATETIME,
               HASHC,
               'D'
          from T_VO_VIDEO a
         where not exists (select 1
                  from T_VO_VIDEO_FULL b
                 where a.videoid = b.videoid
                   and a.coderateid = b.coderateid);

      commit;
    end;
  end if;
  v_nrecod  := SQL%ROWCOUNT;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;

----p_videofull_delete_insert存储过程结束------------

----p_delete_insert存储过程开始------------
create or replace procedure p_delete_insert as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_delete_insert',

                                        '增量同步视屏相关数据');
delete T_VO_LIVE a
where exists (
select 1 from T_VO_LIVE_MID b where a.nodeid=b.nodeid and a.programid=b.programid
and a.starttime=b.starttime and a.nodeid=b.nodeid);
insert into T_VO_LIVE (NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME,SID)
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME, SID from (
select NODEID, PROGRAMID, LIVENAME, STARTTIME, ENDTIME, SID,
       row_number()over(partition by a.programid, starttime,NODEID order by LIVENAME desc) sort
  from T_VO_LIVE_MID a
where status in ('A', 'U')
)where sort=1
;


delete T_VO_NODE c
where exists(select 1 from T_VO_NODE_MID d where c.nodeid=d.nodeid);
insert into T_VO_NODE(NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME, COLLECTFLAG)
select NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME, COLLECTFLAG from (
select NODEID, NODENAME, DESCRIPTION, PARENTNODEID, LOGOPATH, SORTID, PRODUCTID, EXPORTTIME, COLLECTFLAG,
       row_number()over(partition by a.nodeid order by NODENAME desc) sort
  from T_VO_NODE_MID a
where status in ('A', 'U')
)where sort=1
;


delete T_VO_PRODUCT e
where exists(select 1 from t_Vo_Product_Mid f where e.productid=f.productid and f.status = 'D');

MERGE INTO t_Vo_Product dd
USING (SELECT PRODUCTID,
              PRODUCTNAME,
              FEE,
              CPID,
              FEETYPE,
              STARTDATE,
              FEEDESC,
              FREETYPE,
              FREEEFFECTIME,
              FREETIMEFAIL,
              PRODUCTDESC
         from t_Vo_Product_Mid m
        where m.status in ('A', 'U')) s
ON (dd.productid = s.PRODUCTID)
WHEN MATCHED THEN
  UPDATE
     SET dd.productname   = s.productname,
         dd.fee           = s.fee,
         dd.cpid          = s.cpid,
         dd.feetype       = s.feetype,
         dd.startdate     = s.startdate,
         dd.freetype      = s.freetype,
         dd.freeeffectime = s.freeeffectime,
         dd.freetimefail  = s.freetimefail,
         dd.productdesc   = s.productdesc
WHEN NOT MATCHED THEN
  INSERT
    (PRODUCTID,
     PRODUCTNAME,
     FEE,
     CPID,
     FEETYPE,
     STARTDATE,
     FEEDESC,
     FREETYPE,
     FREEEFFECTIME,
     FREETIMEFAIL,
     PRODUCTDESC)
  VALUES
    (s.PRODUCTID,
     s.PRODUCTNAME,
     s.FEE,
     s.CPID,
     s.FEETYPE,
     s.STARTDATE,
     s.FEEDESC,
     s.FREETYPE,
     s.FREEEFFECTIME,
     s.FREETIMEFAIL,
     s.PRODUCTDESC);


delete T_VO_PROGRAM g
where exists (select 1 from T_VO_PROGRAM_MID h where g.programid=h.programid and g.nodeid=h.nodeid);
insert into T_VO_PROGRAM(PROGRAMID, VIDEOID, PROGRAMNAME, NODEID, DESCRIPTION, LOGOPATH,
 TIMELENGTH, SHOWTIME, LASTUPTIME, PROGRAMTYPE, EXPORTTIME, FTPLOGOPATH, TRUELOGOPATH,SORTID,ISLINK,PRODUCTID)
select PROGRAMID,
       VIDEOID,
       PROGRAMNAME,
       NODEID,
       DESCRIPTION,
       LOGOPATH,
       TIMELENGTH,
       SHOWTIME,
       LASTUPTIME,
       PROGRAMTYPE,
       EXPORTTIME,
       FTPLOGOPATH,
       TRUELOGOPATH,
       SORTID,
       ISLINK,
       PRODUCTID from (
select PROGRAMID,
       VIDEOID,
       PROGRAMNAME,
       NODEID,
       DESCRIPTION,
       LOGOPATH,
       TIMELENGTH,
       SHOWTIME,
       LASTUPTIME,
       PROGRAMTYPE,
       EXPORTTIME,
       FTPLOGOPATH,
       TRUELOGOPATH,
       SORTID,
       ISLINK,
       PRODUCTID,
       row_number()over(partition by a.programid,a.nodeid order by to_date(a.lastuptime,'yyyy-mm-dd hh24:mi:ss') desc) sort
  from T_VO_PROGRAM_MID a
where status in ('A', 'U')
)where sort=1
;


delete T_VO_VIDEO i
where exists(select 1 from T_VO_VIDEO_MID j where i.videoid=j.videoid and i.coderateid=j.coderateid);
insert into T_VO_VIDEO(VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, HASHC, UPDATETIME)
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, HASHC, UPDATETIME from (
select VIDEOID, CODERATEID, FILEPATH, FILESIZE, DOWNLOADFILEPATH, HASHC, UPDATETIME,
       row_number()over(partition by a.videoid,a.coderateid order by FILESIZE desc) sort
  from T_VO_VIDEO_MID a
where status in ('A', 'U')
)where sort=1
;


delete  T_VO_VIDEODETAIL k
where exists(select 1 from T_VO_VIDEODETAIL_MID l where k.programid=l.programid);
insert into T_VO_VIDEODETAIL(PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME)
select PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME from (
select PROGRAMID, DAYPLAYNUM, TOTALPLAYNUM, DAYDOWNLOADNUM, TOTALDOWNLOADNUM, UPDATETIME,
       row_number()over(partition by a.programid order by UPDATETIME desc) sort
  from T_VO_VIDEODETAIL_MID a
where status in ('A', 'U')
)where sort=1
;


delete T_VO_PROGRAM g
where not exists (select 1 from t_vo_video v where g.videoid=v.videoid);

delete t_Vo_Reference g
where not exists (select 1 from T_VO_PROGRAM h where g.programid=h.programid and g.nodeid=h.nodeid);


 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;
----p_delete_insert存储过程结束------------


----创建f_MD5函数开始------------
CREATE OR REPLACE FUNCTION F_MD5(dataString IN VARCHAR2) RETURN VARCHAR2 IS
  retval varchar2(32);
BEGIN
  retval := Utl_Raw.Cast_To_Raw(sys.DBMS_OBFUSCATION_TOOLKIT.MD5(input_string =>dataString));
  RETURN retval;
END;

----创建f_MD5函数结束-------------

-------初始化视频表t_vo_video的hashc字段--------
update t_vo_video set hashc =  f_md5(videoid||coderateid||filepath||downloadfilepath||filesize);

----------------视频文件全量导入 end---------------------

commit;

