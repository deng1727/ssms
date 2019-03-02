-- Add/modify columns 
alter table T_CONTENT_BACKLIST add contentType VARCHAR2(10);
-- Add comments to the columns 
comment on column T_CONTENT_BACKLIST.contentType
  is '内容类型：M：MM内容，C：创业大赛内容';

delete from t_content_backlist t
 where not exists (select *
          from t_r_gcontent g
         where t.contentid = g.contentid
           and g.provider = 'O'
           and ascii(substr(g.id, 1, 1)) > 47
           and ascii(substr(g.id, 1, 1)) < 58);

update t_content_backlist b
   set b.contenttype = (select decode(g.subtype, 6, 'C', 'M')
                          from t_r_gcontent g
                         where g.contentid = b.contentid)
 where b.contenttype is null;

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('1', 'MO电子书', '1027', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('2', 'MO动漫', '1033', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('3', 'MO视频', '1031', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('4', 'MO音乐', '1035', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('5', 'MO主题', '1029', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('6', 'MO资讯', '1037', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('7', 'MO软件', '1040', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('8', 'MO游戏', '1041', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('9', 'www电子书', '1026', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('10', 'www动漫', '1032', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('11', 'www视频', '1030', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('12', 'www音乐', '1034', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('13', 'www主题', '1028', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('14', 'www资讯', '1036', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('15', 'www软件', '1038', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('16', 'www游戏', '1039', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('17', 'wap电子书', '1046', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('18', 'wap动漫', '1049', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('19', 'wap视频', '1048', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('20', 'wap音乐', '1050', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('21', 'wap主题', '1047', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('22', 'wap资讯', '1051', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('23', 'wap软件', '1044', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('24', 'wap游戏', '1045', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.090_SSMS','MM1.0.2.095_SSMS');
commit;