-- Add/modify columns 
alter table T_INTERVENOR add ISDEL VARCHAR2(1) default 0 not null;
-- Add comments to the columns 
comment on column T_INTERVENOR.ISDEL
  is '0：未删除       1：已删除';


-- Add/modify columns 
alter table T_R_GCONTENT add FULLDEVICEID clob;
-- Add comments to the columns 
comment on column T_R_GCONTENT.FULLDEVICEID
  is '全量机型ID信息';

-- Add/modify columns 
alter table T_MB_MUSIC add MUSIC_PIC varchar2(400);
-- Add comments to the columns 
comment on column T_MB_MUSIC.MUSIC_PIC
  is '歌曲图片地址';



-----wap门户引入基地游戏，测试根据具体ruleid和货架ID做调整--

---网游
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332946', 210, to_date('22-02-2011 01:32:03', 'dd-mm-yyyy hh24:mi:ss'), to_date('05-01-2011 15:09:08', 'dd-mm-yyyy hh24:mi:ss'), 0);

----特价
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332943', 216, to_date('22-02-2011 01:31:28', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-01-2011 13:14:15', 'dd-mm-yyyy hh24:mi:ss'), 0);

---EA品牌店
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332947', 217, to_date('22-02-2011 01:30:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-01-2011 11:42:49', 'dd-mm-yyyy hh24:mi:ss'), 125);
----Gameloft
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332948', 218, to_date('22-02-2011 01:30:52', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-01-2011 11:43:08', 'dd-mm-yyyy hh24:mi:ss'), 49);
----搜狐
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332950', 213, to_date('22-02-2011 01:30:53', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-01-2011 11:43:24', 'dd-mm-yyyy hh24:mi:ss'), 1101);
----新浪
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332951', 214, to_date('22-02-2011 01:30:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-01-2011 11:47:01', 'dd-mm-yyyy hh24:mi:ss'), 1548);
-----华娱
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332949', 215, to_date('22-02-2011 01:30:55', 'dd-mm-yyyy hh24:mi:ss'), to_date('14-01-2011 11:47:19', 'dd-mm-yyyy hh24:mi:ss'), 272);

----试玩
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332945', 169, to_date('22-02-2011 01:32:42', 'dd-mm-yyyy hh24:mi:ss'), to_date('15-07-2010 22:45:17', 'dd-mm-yyyy hh24:mi:ss'), 2226);

----wap基地游戏单机 ruleid根据具体情况调整
insert into t_category_rule (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME, LASTEXCUTECOUNT)
values ('88332944', 223, null, to_date('28-02-2011 20:07:21', 'dd-mm-yyyy hh24:mi:ss'), 0);


insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (223, 'wap基地游戏单机', 0, 0, 1, 0, 0);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (223, '', 11, 'Chargetime=''1''and onlinetype=1 and  expprice <= 0', '', 2000, 1, 1475);






insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.010_SSMS','MM1.0.3.020_SSMS');
commit;