create or replace synonym REPORT_2012_CYLIST
  for V_PPS_2012_CYLIST_D@REPORT105.ORACLE.COM;

create table T_CYLIST as select * from REPORT_2012_CYLIST where 1=2;

create table T_CYLIST_TRA as select * from REPORT_2012_CYLIST where 1=2;

update t_r_gcontent c set c.provider='' where c.subtype='6';


-- Add comments to the columns 
comment on column T_CYLIST.STAT_TIME
  is '���� �����������ݶ��ṩ��ʽ��YYYYMMDD;��ʾ������ͳ��������һ���ͳ������';
comment on column T_CYLIST.CONTENTID
  is '����ID';
comment on column T_CYLIST.CONTESTCODE
  is '������Ʒ����';
comment on column T_CYLIST.DOWN_COUNT
  is '�����ش���';
comment on column T_CYLIST.DOWN_SCORE
  is '�����ص÷�';
comment on column T_CYLIST.BALLOT_COUNT
  is '��ͶƱ����';
comment on column T_CYLIST.BALLOT_SCORE
  is '��ͶƱ�÷�';
comment on column T_CYLIST.SCORE
  is '���ۺϵ÷�';
comment on column T_CYLIST.ADD_DOWN_COUNT
  is '�ۼ����ش���';
comment on column T_CYLIST.ADD_DOWN_SCORE
  is '�ۼ����ص÷�';
comment on column T_CYLIST.ADD_BALLOT_COUNT
  is '�ۼ�ͶƱ����';
comment on column T_CYLIST.ADD_BALLOT_SCORE
  is '�ۼ�ͶƱ�÷�';
comment on column T_CYLIST.ADD_SCORE
  is '�ۼ��ۺϵ÷�';
comment on column T_CYLIST.FLOW_TIME
  is '��������ʱ��';


-- Add comments to the columns 
comment on column T_CYLIST_TRA.STAT_TIME
  is '���� �����������ݶ��ṩ��ʽ��YYYYMMDD;��ʾ������ͳ��������һ���ͳ������';
comment on column T_CYLIST_TRA.CONTENTID
  is '����ID';
comment on column T_CYLIST_TRA.CONTESTCODE
  is '������Ʒ����';
comment on column T_CYLIST_TRA.DOWN_COUNT
  is '�����ش���';
comment on column T_CYLIST_TRA.DOWN_SCORE
  is '�����ص÷�';
comment on column T_CYLIST_TRA.BALLOT_COUNT
  is '��ͶƱ����';
comment on column T_CYLIST_TRA.BALLOT_SCORE
  is '��ͶƱ�÷�';
comment on column T_CYLIST_TRA.SCORE
  is '���ۺϵ÷�';
comment on column T_CYLIST_TRA.ADD_DOWN_COUNT
  is '�ۼ����ش���';
comment on column T_CYLIST_TRA.ADD_DOWN_SCORE
  is '�ۼ����ص÷�';
comment on column T_CYLIST_TRA.ADD_BALLOT_COUNT
  is '�ۼ�ͶƱ����';
comment on column T_CYLIST_TRA.ADD_BALLOT_SCORE
  is '�ۼ�ͶƱ�÷�';
comment on column T_CYLIST_TRA.ADD_SCORE
  is '�ۼ��ۺϵ÷�';
comment on column T_CYLIST_TRA.FLOW_TIME
  is '��������ʱ��';

---------------�洢����
create or replace procedure p_refresh_cy_productlist as
  v_sql_f   varchar2(1200);
  v_nindnum number; --��¼�����Ƿ����
  v_nstatus number;
  v_nrecod  number;

  ----�漰�Ա�T_CYLIST��ͬ��
begin
  v_nstatus := pg_log_manage.f_startlog('p_refresh_cy_productlist',
                                        '���³�ʼ��T_CYLIST��');
  execute immediate 'truncate table T_CYLIST_TRA';
  --��ս����ʷ������

  insert into T_CYLIST_TRA
    select *
      from REPORT_2012_CYLIST t where t.stat_time = to_char(sysdate-1,'YYYYMMDD');
  v_nrecod := SQL%ROWCOUNT;
  
  select count(9) into v_nindnum from T_CYLIST_TRA;

  if v_nindnum > 0 then
    execute immediate 'alter table T_CYLIST rename to T_CYLIST_BAK';
    execute immediate 'alter table T_CYLIST_TRA rename to T_CYLIST';
    execute immediate 'alter table T_CYLIST_BAK rename to T_CYLIST_TRA';
    --����ɹ�����ִ�����д����־
  
    commit;
  else
    raise_application_error(-20088, '�������ṩ����Ϊ��');
  end if;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end;
----------------�洢���̽���-------------

----------------�洢���̽���-------------


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
              f.SubOnlineDate) as marketdate, --ȫ��ȡonlinedate��ʡ��ȡSubOnlineDate
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
   and c.companyid = e.companyid ----ap����
   and (c.status = '0006' or c.status = '1006')
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
      --and d. ProductStatus  in ('2','3','5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.Status in ('2', '3') ----��Ʒ���߼Ʒ�or���Ʒ�or  ȥ��������
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and c.thirdapptype = '6'
   and p.developerid = c.developerid



------------2012��ҵ�������ܿ���----------
create table  t_sync_tactic_cy_dk49 as select * from  t_sync_tactic_cy;


-----��������IDΪ�����Ļ���ID,����ʱ������Ҫ�޸�Ϊ����ϵͳ����Ӧ����ID----

update t_sync_tactic_cy  t set t.categoryid='348842473' where t.id='7';---��Ц
update t_sync_tactic_cy  t set t.categoryid='348842475' where t.id='6';---��Ƶ
update t_sync_tactic_cy  t set t.categoryid='348842476' where t.id='5';---ͨѶ
update t_sync_tactic_cy  t set t.categoryid='348842478' where t.id='4';---����
update t_sync_tactic_cy  t set t.categoryid='348842477' where t.id='8';---����
update t_sync_tactic_cy  t set t.categoryid='348842479' where t.id='1';---���
update t_sync_tactic_cy  t set t.categoryid='348842480' where t.id='2';---��Ϸ
update t_sync_tactic_cy  t set t.categoryid='348842474' where t.id='3';---����



insert into t_category_carveout_sqlbase (ID, BASESQL, BASENAME)
values ('7', 'select b.id from t_r_base b, t_r_gcontent g, v_service v,T_CYLIST c where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid and  g.subtype = ''6'' and g.NAMELETTER=''2012''', '2012��ҵ�������а�������');


-------------------2012��ҵ�������Զ����¹�����Ӧ����IDΪ�����Ļ���ID,������Ҫ��Ϊ���Ի���-------------
----------------------------------------------------------------------------------------------------------
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('35', '���ۺϵ÷ְ�', '348842481', 0, 1, 1, '', 'c.score desc  nulls  last', 6000, null, null, sysdate, 0, '7');
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('36', '�ۼ��ۺϵ÷ְ�', '348842482', 0, 1, 1, '', 'c.add_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');


insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('37', '��ר���Ƽ��÷ְ�', '348842483', 0, 1, 1, '', 'c.ballot_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('38', '�ۼ�ר���Ƽ��÷ְ�', '348842484', 0, 1, 1, '', 'c.add_ballot_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');

insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('39', '���ط����հ�', '348842486', 0, 1, 1, '', 'c.down_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');
insert into t_category_carveout_rule (ID, NAME, CID, INTERVALTYPE, EXCUTETIME, EXCUTEINTERVAL, WSQL, OSQL, COUNT, SORTID, LASTEXCUTETIME, EFFECTIVETIME, RULETYPE, SQLBASEID)
values ('40', '���ط����ܰ�', '348842485', 0, 1, 1, '', 'c.add_down_score desc  nulls  last', 6000, null, null, sysdate, 0, '7');






insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.039_SSMS','MM1.1.1.049_SSMS');
commit;
