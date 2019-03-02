
-- Create table
create table T_R_APPCATE_EN
(
  cateid    VARCHAR2(7) not null,
  catename  VARCHAR2(200) not null,
  cateename VARCHAR2(20),
  firstid   VARCHAR2(30) not null
);
-- Add comments to the columns 
comment on column T_R_APPCATE_EN.cateid
  is '二级分类ID';
comment on column T_R_APPCATE_EN.catename
  is '二级分类名称';
comment on column T_R_APPCATE_EN.cateename
  is '二级分类英文名称';
comment on column T_R_APPCATE_EN.firstid
  is '一级分类ID,1001,软件;1002,主题;1003,游戏';



insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('14', '休闲', 'xiuxian', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('15', '棋牌', 'qipai', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('16', '动作', 'dongzuo', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('17', '体育', 'tiyu', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('18', '射击', 'sheji', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('19', '冒险', 'maoxian', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('20', '策略', 'celue', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('21', '赛车', 'saiche', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('22', '飞行', 'feixing', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('23', '格斗', 'gedou', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('24', '角色', 'juese', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('25', '益智', 'yizhi', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('26', '其他', 'qita', '1003');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('1', '工具', 'gongju', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('10', '摄影', 'sheying', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('11', '商务', 'shangwu', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('12', '健康', 'jiankang', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('13', '动漫', 'dongman', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('2', '娱乐', 'yule', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('3', '生活', 'shenghuo', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('4', '书籍', 'shuji', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('5', '新闻', 'xinwen', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('6', '视频', 'shipin', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('7', '社交', 'shejiao', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('8', '教育', 'jiaoyu', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('9', '财务', 'caiwu', '1001');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('27', '节日', 'jieri', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('28', '体育', 'tiyu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('29', '动物', 'dongwu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('30', '植物', 'zhiwu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('31', '插画', 'chahua', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('32', '搞笑', 'gaoxiao', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('33', '建筑', 'jianzhu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('34', '汽车', 'qiche', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('35', '卡通', 'katong', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('36', '风景', 'fengjing', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('37', '人物', 'renwu', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('38', '爱情', 'aiqing', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('39', '创意', 'chuangyi', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('40', '色彩', 'secai', '1002');

insert into T_R_appCATE_EN (CATEID, CATENAME, CATEENAME, FIRSTID)
values ('41', '军事', 'junshi', '1002');

-- 这个语句已经被艾晏在2012-01-12在现网执行了，因为MOPPS的39要跑通他们的程序而弄的。
-- alter table t_r_gcontent add mstatus number(1) default 0;
-- comment on column t_r_gcontent.mstatus
--   is '0:非M券应用，1:M券应用';

-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_PROMOTION
  for V_CM_CONTENT_PROMOTION@DL_PPMS_DEVICE;

create or replace procedure proc_V_CM_CONTENT_PROMOTION  authid current_user is
  begin
  execute immediate 'update t_r_gcontent t set  mstatus = 0 where mstatus=1 ';
  execute immediate 'update t_r_gcontent t set mstatus = 1 where exists(select 1 from PPMS_V_CM_CONTENT_PROMOTION p where p.contentid = t.contentid) and  mstatus=0';
  commit;
  end;
/


variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'proc_V_CM_CONTENT_PROMOTION;',
                      next_date => to_date('05-01-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/


-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_CONLEVEL
  for V_CM_CONTENT_CONLEVEL@DL_PPMS_DEVICE;

create or replace procedure proc_V_CM_CONTENT_CONLEVEL  authid current_user is
  begin
  execute immediate 'update t_r_gcontent t set t.averagemark = (select p.conlevel from PPMS_V_CM_CONTENT_CONLEVEL p  where p.contentid = t.contentid) where exists (select 1 from PPMS_V_CM_CONTENT_CONLEVEL p  where p.contentid = t.contentid)';
  commit;
end;
/



variable job9 number;
begin
  sys.dbms_job.submit(job => :job9,
                      what => 'proc_V_CM_CONTENT_CONLEVEL;',
                      next_date => to_date('05-01-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/

-----------报表数据本地化
create or replace synonym REPORT_DOWN_D
  for MM_REPORT.V_PPS_PORTAL_DOWN_D;

create table t_portal_down_d as select * from REPORT_DOWN_D t where t.stat_time = to_char(sysdate-1,'yyyymmdd');

create index INDEX_PORTAL_CONTENTID on t_portal_down_d (CONTENT_ID);

create or replace procedure p_portal_down_d as
  v_sql_f varchar2(1200);
begin
  --清空结果历史表数据
  execute immediate 'truncate table t_portal_down_d';
  v_sql_f := 'insert into t_portal_down_d
          select * from REPORT_DOWN_D t where t.stat_time = to_char(sysdate-1,''yyyymmdd'')';
  execute immediate v_sql_f;
  commit;
exception
  when others then
    rollback;
end;

variable job1 number;
begin
  sys.dbms_job.submit(job => :job1,
                     what => 'p_portal_down_d;',
                     next_date => to_date('05-01-2012 02:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                     interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.120_SSMS','MM1.0.3.125_SSMS');
commit;
