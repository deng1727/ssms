-- Add/modify columns 
alter table T_CONTENT_BACKLIST add contentType VARCHAR2(10);
-- Add comments to the columns 
comment on column T_CONTENT_BACKLIST.contentType
  is '�������ͣ�M��MM���ݣ�C����ҵ��������';

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
values ('1', 'MO������', '1027', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('2', 'MO����', '1033', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('3', 'MO��Ƶ', '1031', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('4', 'MO����', '1035', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('5', 'MO����', '1029', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('6', 'MO��Ѷ', '1037', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('7', 'MO���', '1040', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('8', 'MO��Ϸ', '1041', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('9', 'www������', '1026', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('10', 'www����', '1032', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('11', 'www��Ƶ', '1030', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('12', 'www����', '1034', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('13', 'www����', '1028', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('14', 'www��Ѷ', '1036', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('15', 'www���', '1038', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('16', 'www��Ϸ', '1039', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('17', 'wap������', '1046', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('18', 'wap����', '1049', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('19', 'wap��Ƶ', '1048', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('20', 'wap����', '1050', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('21', 'wap����', '1047', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('22', 'wap��Ѷ', '1051', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('23', 'wap���', '1044', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into T_CATEGORY_CARVEOUT_RULE (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE)
values ('24', 'wap��Ϸ', '1045', 0, 1, 1, null, 'g.marketdate desc', -1, null, to_date('07-12-2010 10:00:01', 'dd-mm-yyyy hh24:mi:ss'), to_date('19-07-2030', 'dd-mm-yyyy'), 1);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.090_SSMS','MM1.0.2.095_SSMS');
commit;