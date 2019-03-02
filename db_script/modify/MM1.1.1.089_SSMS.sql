-- Add/modify columns 
alter table T_RB_REFERENCE_NEW modify CATEGORYID null;

  -- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_CHANNEL_SSMS
  for MM_PPMS.V_CM_CONTENT_CHANNEL_SSMS;
  
  -- Create the synonym 
create or replace synonym PPMS_V_OM_OPEN_CHANNEL
  for MM_PPMS.V_OM_OPEN_CHANNEL;


create table V_CM_OPEN_CHANNEL as select * from PPMS_V_OM_OPEN_CHANNEL t;

create table V_CM_OPEN_CONTEN as select * from PPMS_V_CM_CONTENT_CHANNEL_SSMS t;


-- Create table
create table T_R_OPENCRTE_MAP
(
  CATEGORYID      VARCHAR2(30) not null,
  OPENCHANNELCODE VARCHAR2(7) not null,
  COMPANYNAME     VARCHAR2(100) not null
)
;
-- Add comments to the columns 
comment on column T_R_OPENCRTE_MAP.CATEGORYID
  is '����id';
comment on column T_R_OPENCRTE_MAP.OPENCHANNELCODE
  is '�����̴���';
comment on column T_R_OPENCRTE_MAP.COMPANYNAME
  is '����������';


insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('398440045', 'k000049', '����');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632989', 'k000041', '�ų�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632990', 'k000040', '360');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632991', 'k000042', '��ɭŵ��');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632992', 'k000043', '����Ӫҵ��');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632993', 'k000044', '����');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632994', 'k000045', '�����ƶ�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632995', 'k000046', '�ݽ�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('393632996', 'k000047', '�׼Ҷ���');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('384137910', 'k000031', 'Ӧ�û�����');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('384137911', 'k000032', 'UC����');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('384137912', 'k000038', 'ɱ���ȷ�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('411210089', 'k000051', '�������̵�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('390751579', 'k000034', '������');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('457187354', 'k000057', '�����ƶ�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('457187355', 'k000054', 'ɽ���ƶ�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('457187356', 'k000058', '�Ϻ��ƶ�');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('420826023', 'k000052', 'app��ʱ����');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('420826024', 'k000053', '��U����');
insert into T_R_OPENCRTE_MAP (CATEGORYID, OPENCHANNELCODE, COMPANYNAME)
values ('428643244', 'k000033', '����');


-- Create/Recreate primary, unique and foreign key constraints 
alter table T_R_OPENCRTE_MAP
  add constraint p_opencretmap_id primary key (CATEGORYID, OPENCHANNELCODE);

-- Create the synonym 
create or replace synonym PPMS_V_CM_CONTENT_DARKHORSE
  for MM_PPMS.V_CM_CONTENT_DARKHORSE;

create table V_CM_CONTENT_DARKHORSE as select * from PPMS_V_CM_CONTENT_DARKHORSE;

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (41, '�ӿ�����Ӫ�ṩ�����л�ȡ', 'select b.id from t_r_base b, t_r_gcontent g, V_CM_OPEN_CONTEN s, T_R_OPENCRTE_MAP m, t_r_category c where b.id = g.id and g.contentid = s.contentid and s.openchannelcode = m.openchannelcode and m.categoryid = c.id and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL) values (42, '������΢��Ӧ�û�ȡ����', 'select b.id from t_r_base b, t_r_gcontent g, v_service v, V_CM_CONTENT_DARKHORSE d, t_portal_down_d p where b.id = g.id and  p.content_id = g.contentid and d.contentid = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and d.marketingcha = 1 and p.portal_id = 1 and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');

-- Add/modify columns 
alter table T_RB_AUTHOR_NEW add RECOMMEND_MANUAL NUMBER(10) default -1 not null;
-- Add comments to the columns 
comment on column T_RB_AUTHOR_NEW.RECOMMEND_MANUAL
  is '�Ƽ��ֶα�ʶ��-1Ϊ���Ƽ�������0Ϊ�Ƽ���ͼ������ߣ��������򣻴���1000000��Ϊ�ö��Ƽ�����';

-- Add/modify columns 
alter table T_RB_BOOK_NEW add RECOMMEND_MANUAL NUMBER(10) default -1 not null;
-- Add comments to the columns 
comment on column T_RB_BOOK_NEW.RECOMMEND_MANUAL
  is '�Ƽ��ֶα�ʶ��-1Ϊ���Ƽ�������0Ϊ�Ƽ���ͼ������ߣ��������򣻴���1000000��Ϊ�ö��Ƽ�����';


insert into T_RB_CATEGORY_NEW (ID, CATEGORYID, CATEGORYNAME, CATALOGTYPE, DECRISPTION, PARENTID, PICURL, LUPDATE, TOTAL, PLATFORM, CITYID, TYPE, SORTID, PARENTCATEGORYID)
values (303, null, '�Զ����', '0', null, null, null, to_date('22-05-2012 17:37:48', 'dd-mm-yyyy hh24:mi:ss'), null, '{0000}', '{0000}', '1', 0, null);

--------------------------ע��'�����Ӧ���ڼƷѶ���ID'������idҪ��ǰһ�����ɵ�RULEID��д
--------------------------ע��'�����Ӧ���ڼƷѶ���ID'������idҪ��ǰһ�����ɵ�RULEID��д

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '�����Ӧ���ڼƷѶ���', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('�����Ӧ���ڼƷѶ���ID', null, 42, 'd.THIRDAPPTYPE=1', 'p.add_order_count desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '����������Ӧ�ö���', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('����������Ӧ�ö���ID', null, 42, 'v.mobileprice=0', 'p.add_order_count desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);


insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '����������Ӧ������ඩ��', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('����������Ӧ������ඩ��ID', null, 42, 'v.mobileprice=0 and b.type=''nt:gcontent:appSoftWare''', 'g.marketdate desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '����������Ӧ����Ϸ�ඩ��', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('����������Ӧ����Ϸ�ඩ��ID', null, 42, 'v.mobileprice=0 and b.type=''nt:gcontent:appGame''', 'g.marketdate desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '����������Ӧ�������ඩ��', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('����������Ӧ�������ඩ��ID', null, 42, 'v.mobileprice=0 and b.type=''nt:gcontent:appTheme''', 'g.marketdate desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);


insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '�����Ӧ���ڼƷ�����ඩ��', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('�����Ӧ���ڼƷ�����ඩ��ID', null, 42, 'd.THIRDAPPTYPE=1 and b.type=''nt:gcontent:appSoftWare''', 'g.marketdate desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);

insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '�����Ӧ���ڼƷ���Ϸ�ඩ��', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('�����Ӧ���ڼƷ���Ϸ�ඩ��ID', null, 42, 'd.THIRDAPPTYPE=1 and b.type=''nt:gcontent:appGame''', 'g.marketdate desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);


insert into T_CATERULE (RULEID, RULENAME, RULETYPE, INTERVALTYPE, EXCUTEINTERVAL, EXCUTETIME, RANDOMFACTOR, MAXGOODSNUM)
values (SEQ_caterule_ID.nextval, '�����Ӧ���ڼƷ������ඩ��', 0, 0, 1, 0, 0, null);

insert into T_CATERULE_COND (RULEID, CID, CONDTYPE, WSQL, OSQL, COUNT, SORTID, ID, BASECONDID)
values ('�����Ӧ���ڼƷ������ඩ��ID', null, 42, 'd.THIRDAPPTYPE=1 and b.type=''nt:gcontent:appTheme''', 'g.marketdate desc', -1, 1, SEQ_CATERULE_COND_ID.nextval, 42);


--------------------------ע��'�����Ӧ���ڼƷѶ���ID'������idҪ��ǰһ�����ɵ�RULEID��д
--------------------------ע��'�����Ӧ���ڼƷѶ���ID'������idҪ��ǰһ�����ɵ�RULEID��д


-- Add/modify columns 
alter table T_RB_BOOKBAG_NEW add packetType NUMBER(2) default 1 not null;
-- Add comments to the columns 
comment on column T_RB_BOOKBAG_NEW.packetType
  is '������ͣ�1�����������2���ǰ������';

update T_RB_BOOKBAG_NEW b set b.packettype=2 where b.fee=0;



-----------------------------�����洢����
create or replace procedure proc_V_CM_CONTENT_DARKHORSE  authid current_user is
  
  v_local_create_sql varchar2(512);
  v_nstatus number;
  v_cnt number;

  
  begin
  select count(1) into v_cnt from user_tables where table_name='V_CM_CONTENT_DARKHORSE';
  if v_cnt>0 then 
     execute immediate 'drop table V_CM_CONTENT_DARKHORSE';
  end if;
  
  v_nstatus:=pg_log_manage.f_startlog('proc_V_CM_CONTENT_CONLEVEL','������-����-Ӧ�ú���');
  v_local_create_sql := 'create table V_CM_CONTENT_DARKHORSE as select * from PPMS_V_CM_CONTENT_DARKHORSE';

  execute immediate v_local_create_sql;--�������ر�
  v_nstatus:=pg_log_manage.f_successlog;
  
  commit;
   
  exception
   when others then
   rollback;
   v_nstatus:=pg_log_manage.f_errorlog;
   
  
  end proc_V_CM_CONTENT_DARKHORSE;


----------------------------�����洢���̽���

----------------------------������ʱ����
variable job8 number;
begin
  sys.dbms_job.submit(job => :job8,
                      what => 'proc_V_CM_CONTENT_DARKHORSE;',
                      next_date => to_date('27-08-2012 01:00:00', 'dd-mm-yyyy hh24:mi:ss'),
                      interval => 'to_date(to_char(sysdate+1,''yyyy/mm/dd'') || '' 02:00:00'',''yyyy/mm/dd hh24:mi:ss'')');
  commit;
end;
/
----------------------------������ʱ�������


alter table T_VO_CATEGORY modify nodenum NUMBER(11);
alter table T_VO_CATEGORY modify refnum NUMBER(11);


-----------------------------�����洢���̺���
create or replace function f_update_video_rnum  return number as
  v_status number;--��־����
    v_ncount      number; --��¼��
--�Զ����������ͣ�
type type_col_id is table of t_vo_category.id%type
index by binary_integer;

--�Զ������ͱ���
v_id type_col_id;
begin
   v_status:=pg_log_manage.f_startlog('f_update_video_rnum','������Ƶ���������������������Ʒ��' );
--������ID�����Զ������

update t_vo_category t set t.refnum=(select count(*) from t_vo_reference r where r.categoryid=t.id);

--commit;

select id bulk collect into v_id from t_vo_category;

v_ncount := sql%rowcount;
--ѭ������
for i in 1..sql%rowcount loop

update t_vo_category t set t.nodenum=(
select sum(refnum)
  from (select id, c.refnum
          from t_vo_category c
         start with c.id = v_id(i)
        connect by prior c.id = c.parentid)
--select count(1) from t_vo_reference r where r.categoryid in
-- (select c.id  from t_vo_category c start with c.id = v_id(i) connect by prior  c.id = c.parentid)
 )  where t.id= v_id(i);
end loop;
 v_status:= pg_log_manage.f_successlog(vn_RECORDNUM =>v_ncount);

exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
 end;

----------------------------�����洢���̽���


----Ӧ���ڸ���Ӧ����ͼ
create or replace view  v_cm_content_apppay as 
select t.contentid,t.name from cm_content t where t.thirdapptype='14';



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.085_SSMS','MM1.1.1.089_SSMS');


commit;