create or replace synonym REPORT_2012_CYLIST
  for V_PPS_2012_CYLIST_D@REPORT105.ORACLE.COM;

create table T_CYLIST as select * from REPORT_2012_CYLIST where 1=2;

create table T_CYLIST_TRA as select * from REPORT_2012_CYLIST where 1=2;

update t_r_gcontent c set c.provider='' where c.subtype='6';


-- Add comments to the columns 
comment on column T_CYLIST.STAT_TIME
  is '日期 所有日期数据都提供格式：YYYYMMDD;表示下述日统计量是哪一天的统计量。';
comment on column T_CYLIST.CONTENTID
  is '内容ID';
comment on column T_CYLIST.CONTESTCODE
  is '参赛作品编码';
comment on column T_CYLIST.DOWN_COUNT
  is '日下载次数';
comment on column T_CYLIST.DOWN_SCORE
  is '日下载得分';
comment on column T_CYLIST.BALLOT_COUNT
  is '日投票次数';
comment on column T_CYLIST.BALLOT_SCORE
  is '日投票得分';
comment on column T_CYLIST.SCORE
  is '日综合得分';
comment on column T_CYLIST.ADD_DOWN_COUNT
  is '累计下载次数';
comment on column T_CYLIST.ADD_DOWN_SCORE
  is '累计下载得分';
comment on column T_CYLIST.ADD_BALLOT_COUNT
  is '累计投票次数';
comment on column T_CYLIST.ADD_BALLOT_SCORE
  is '累计投票得分';
comment on column T_CYLIST.ADD_SCORE
  is '累计综合得分';
comment on column T_CYLIST.FLOW_TIME
  is '数据生成时间';


-- Add comments to the columns 
comment on column T_CYLIST_TRA.STAT_TIME
  is '日期 所有日期数据都提供格式：YYYYMMDD;表示下述日统计量是哪一天的统计量。';
comment on column T_CYLIST_TRA.CONTENTID
  is '内容ID';
comment on column T_CYLIST_TRA.CONTESTCODE
  is '参赛作品编码';
comment on column T_CYLIST_TRA.DOWN_COUNT
  is '日下载次数';
comment on column T_CYLIST_TRA.DOWN_SCORE
  is '日下载得分';
comment on column T_CYLIST_TRA.BALLOT_COUNT
  is '日投票次数';
comment on column T_CYLIST_TRA.BALLOT_SCORE
  is '日投票得分';
comment on column T_CYLIST_TRA.SCORE
  is '日综合得分';
comment on column T_CYLIST_TRA.ADD_DOWN_COUNT
  is '累计下载次数';
comment on column T_CYLIST_TRA.ADD_DOWN_SCORE
  is '累计下载得分';
comment on column T_CYLIST_TRA.ADD_BALLOT_COUNT
  is '累计投票次数';
comment on column T_CYLIST_TRA.ADD_BALLOT_SCORE
  is '累计投票得分';
comment on column T_CYLIST_TRA.ADD_SCORE
  is '累计综合得分';
comment on column T_CYLIST_TRA.FLOW_TIME
  is '数据生成时间';

---------------存储过程
create or replace procedure p_refresh_cy_productlist as
  v_sql_f   varchar2(1200);
  v_nindnum number; --记录数据是否存在
  v_nstatus number;
  v_nrecod  number;

  ----涉及对表T_CYLIST的同步
begin
  v_nstatus := pg_log_manage.f_startlog('p_refresh_cy_productlist',
                                        '重新初始化T_CYLIST表');
  execute immediate 'truncate table T_CYLIST_TRA';
  --清空结果历史表数据

  insert into T_CYLIST_TRA
    select *
      from REPORT_2012_CYLIST t where t.stat_time = to_char(sysdate-1,'YYYYMMDD');
  v_nrecod := SQL%ROWCOUNT;
  
  select count(9) into v_nindnum from T_CYLIST_TRA;

  if v_nindnum > 0 then
    execute immediate 'alter table T_CYLIST rename to T_CYLIST_BAK';
    execute immediate 'alter table T_CYLIST_TRA rename to T_CYLIST';
    execute immediate 'alter table T_CYLIST_BAK rename to T_CYLIST_TRA';
    --如果成功，将执行情况写入日志
  
    commit;
  else
    raise_application_error(-20088, '电子流提供数据为空');
  end if;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end;
----------------存储过程结束-------------

----------------存储过程结束-------------


create or replace view ppms_v_cm_content_cy as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       c.ContentCode,
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
              f.SubOnlineDate) as marketdate, --全网取onlinedate，省内取SubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       p.developername companyname,
       p.contestgroup as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate,
       f.chargeTime,
       c.thirdapptype,
       p.province as pvcid,
       p.cityid,
       c.contestcode,
       c.contestyear,
       q.COLLEGE as college,
       q.COLLEGEID as collegeId,
       c.contestchannel,
       decode(c.hatchappid, null, decode(c.contenttype, '1002', '0', '1'), '0') as othernet

  from cm_content_type      a,
       cm_catalog           b,
       cm_content           c,
       v_om_product         d,
       v_valid_company      e,
       OM_PRODUCT_CONTENT   f,
       OM_DEVELOPER_CONTEST p,
       cm_content_college   q
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and p.college = q.COLLEGEID(+)
   and c.companyid = e.companyid ----ap商用
   and (c.status = '0006' or c.status = '1006')
      ----应用商用或变更后商用或者省内上线/下线
   and d.AuditStatus = '0003' ----产品审核通过
      --and d. ProductStatus  in ('2','3','5') ----产品上线计费or不计费or 下线
   and f.Status in ('2', '3') ----产品上线计费or不计费or  去掉了下线
   and f.ID = d.ID ----生成产品
   and c.contentid = f.contentid ----产品表ID存在于内容表中
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid



------------2012创业大赛功能开发----------
create table  t_sync_tactic_cy_dk49 as select * from  t_sync_tactic_cy;


-----下述货架ID为现网的货架ID,测试时根据需要修改为测试系统的相应货架ID----

update t_sync_tactic_cy  t set t.categoryid='348842473' where t.id='7';---搞笑
update t_sync_tactic_cy  t set t.categoryid='348842475' where t.id='6';---视频
update t_sync_tactic_cy  t set t.categoryid='348842476' where t.id='5';---通讯
update t_sync_tactic_cy  t set t.categoryid='348842478' where t.id='4';---音乐
update t_sync_tactic_cy  t set t.categoryid='348842477' where t.id='8';---新闻
update t_sync_tactic_cy  t set t.categoryid='348842479' where t.id='1';---软件
update t_sync_tactic_cy  t set t.categoryid='348842480' where t.id='2';---游戏
update t_sync_tactic_cy  t set t.categoryid='348842474' where t.id='3';---生活



insert into t_category_carveout_sqlbase (ID, BASESQL, BASENAME)
values ('7', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,T_CYLIST c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid and  g.subtype = ''6'' and g.NAMELETTER=''2012''', '2012创业大赛排行榜基础语句');


-------------------2012创业大赛榜单自动更新规则，相应货架ID为现网的货架ID,测试需要改为测试环境-------------
----------------------------------------------------------------------------------------------------------
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('35', '日综合得分榜', '348842481', 0, 1, 1, '', 'c.score desc  nulls  last', 6000, null, null, sysdate, 0, '7');
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('36', '累计综合得分榜', '348842482', 0, 1, 1, '', 'c.add_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');


insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('37', '日专家推荐得分榜', '348842483', 0, 1, 1, '', 'c.ballot_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('38', '累计专家推荐得分榜', '348842484', 0, 1, 1, '', 'c.add_ballot_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('39', '下载分类日榜', '348842486', 0, 1, 1, '', 'c.down_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('40', '下载分类总榜', '348842485', 0, 1, 1, '', 'c.add_down_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');






insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.039_SSMS','MM1.1.1.049_SSMS');
commit;
