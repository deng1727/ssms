
----�޸Ļ�����Ϸ��������ӳ���
-- Add/modify columns 
alter table T_GAME_CATE_MAPPING add alilid VARCHAR2(100);
-- Add comments to the columns 
comment on column T_GAME_CATE_MAPPING.alilid
  is '��Ӫ����ID';

update  t_game_cate_mapping t   set t.alilid = (select v.aliasid  from t_v_om_dictionary v   where v.aliasname = t.mmcatename and v.firstid = '1003') ;

----����������Ϸ��������ӳ����ͼ
CREATE OR REPLACE VIEW V_GAME_CATE_MAPPING AS
SELECT /*+RULE*/
  t1.basecatename,   v2.aliasname as mmcatename  from t_game_cate_mapping t1, V_OM_DICTIONARY v2 where t1.alilid=v2.aliasid;

----ɾ��������Ʒ���Զ����¹���
delete from t_caterule t where t.RULEID='168';
delete from t_caterule_cond t where t.RULEID='168';
delete from t_category_rule t where t.RULEID='168';


----���� �ؼ��ʷ��ֶ�
alter table T_R_GCONTENT add EXPPRICE number(10);
-- Add comments to the columns 
comment on column T_R_GCONTENT.EXPPRICE
  is '������Ϸ �ؼ��ʷѣ���λ ��';

----���� ������Ϸ �����Զ����¹��������ؼ�,���Ի���ruleid��Ӧ�޸�
update t_caterule_cond t set t.wsql='onLinetype=''2'' and expprice=0'  where t.ruleid='210';

----���� ������Ϸ �����Զ����¹��������ؼ�,���Ի���ruleid��Ӧ�޸�
update t_caterule_cond t set t.wsql='chargetime=''2'' and expprice=0'  where t.ruleid='169';

-----add  rule----------
----��ӻ�����Ϸ�ؼ�ר���Զ����¹�����ݾ�������޸� ruleid ��cid -------------------

insert into t_category_rule (CID, RULEID,  EFFECTIVETIME)
values ('65961858', 216, to_date('14-01-2011 13:14:15', 'dd-mm-yyyy hh24:mi:ss'));

insert into t_caterule (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (216, '������Ϸ�ؼ�', 0, 0, 1, 0, 0);

insert into t_caterule_cond (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (216, '', 11, 'expprice >0', '', -1, 1, 1395);

-- Add  ���� �Զ����¹���ÿ����ִ��ʱ��
alter table T_CATEGORY_RULE add lastexcutecount number(10) default 0;
-- Add comments to the columns 
comment on column T_CATEGORY_RULE.lastexcutecount
  is '���һ��ִ��ʱ������λ������';

-- Add/modify columns 
alter table T_GAME_CATE_MAPPING add CID VARCHAR2(30);
-- Add comments to the columns 
comment on column T_GAME_CATE_MAPPING.CID
  is '��Ӧ����ID';

---�޸���Ӧ���½�����parentcategoryid 
update t_game_cate_mapping t set t.cid =(select y.id from t_r_category y where y.name = t.mmcatename and y.parentcategoryid = '100002444') ;

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (211, '��Ʒ����-EA', 0, 0, 1, 0, 0);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (212, '��Ʒ����-Gameloft', 0, 0, 1, 0, 0);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (213, '��Ʒ����-�Ѻ�', 0, 0, 1, 0, 0);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (214, '��Ʒ����-����', 0, 0, 1, 0, 0);
insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR)
values (215, '��Ʒ����-����', 0, 0, 1, 0, 0);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (213, null, 11, 'g.expprice <= 0 and g.icpcode = ''C00044''', null, -1, 1, 1391);
insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (214, null, 11, 'g.expprice <= 0 and g.icpcode = ''C00045''', null, -1, 1, 1392);
insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (215, null, 11, 'g.expprice <= 0 and g.icpcode = ''C00248''', null, -1, 1, 1393);
insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (211, null, 11, 'g.expprice <= 0 and g.icpcode = ''C00210''', null, -1, 1, 1389);
insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID)
values (212, null, 11, 'g.expprice <= 0 and g.icpcode = ''C00343''', null, -1, 1, 1390);

-----CID��Ӧ����Ӧ���½�����id
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65961714', 211, null, to_date('14-01-2011 11:42:49', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65961715', 212, null, to_date('14-01-2011 11:43:08', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65961716', 213, null, to_date('14-01-2011 11:43:24', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65961717', 214, null, to_date('14-01-2011 11:47:01', 'dd-mm-yyyy hh24:mi:ss'));
insert into T_CATEGORY_RULE (CID, RULEID, LASTEXCUTETIME, EFFECTIVETIME)
values ('65961718', 215, null, to_date('14-01-2011 11:47:19', 'dd-mm-yyyy hh24:mi:ss'));

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.003_SSMS','MM1.0.3.007_SSMS');
commit;