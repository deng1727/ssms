-----��׿ȫ��Ӧ�û����Զ����³�ʼ�����ݿ�ʼ-----
declare 
  cateruleid number(8);
begin

insert into t_caterule_cond_base(base_id,base_name,base_sql) values(84,'��׿ȫ��Ӧ������','select b.id from t_r_base b,t_r_gcontent g,v_service v where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.provider != ''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))');

select Seq_Caterule_Id.Nextval into cateruleid from dual;
insert into t_caterule(ruleid,rulename,ruletype,intervaltype,excuteinterval,excutetime,randomfactor)
values(cateruleid,'��׿ȫ��Ӧ��',0,0,1,0,0);
insert into t_category_rule(cid,ruleid,effectivetime,lastexcutecount) values('1225519609',cateruleid,sysdate,0);
insert into t_caterule_cond values(cateruleid,null,0,'g.icpcode != ''139123'' and g.companyid != ''278908''','g.createdate desc',-1,1,SEQ_CATERULE_COND_ID.Nextval,84);

insert into t_a_auto_category(id,categoryid,isnulltosync) values(92,'100038687','1');

commit;
end;

-----��׿ȫ��Ӧ�û����Զ����³�ʼ�����ݽ���-----

-----------�����񵥸�Ԥt_bid_inter���paramֵ�ľ�ȷ�ȿ�ʼ----------
-------����t_bid_inter��------
create table t_bid_inter_bak as select * from t_bid_inter;
-------����t_bid_inter���param�ֶ�Ϊnull------
update t_bid_inter set param = null;
-------�޸�t_bid_inter���param�ֶξ�ȷ��------
alter table t_bid_inter modify param number(8,6);
-------�ѱ��ݱ��е�param�ֶ�ֵ���»�ԭ����ֶ�ֵ------
update t_bid_inter t set t.param = (select b.param from t_bid_inter_bak b where t.id = b.id);
-------ɾ�����ݱ�----
drop table t_bid_inter_bak;

-----------�����񵥸�Ԥt_bid_inter���paramֵ�ľ�ȷ�Ƚ���----------

------------�����ͻ����ص�Ӧ�ñ�ǩ��---------
-- Create table
create table t_key_content_tag
(
  contentid    VARCHAR2(30) not null,
  tagname  VARCHAR2(50)
);
comment on table t_key_content_tag
  is '�ͻ����ص�Ӧ�ñ�ǩ��';
comment on column t_key_content_tag.contentid
  is '����ID';
comment on column t_key_content_tag.tagname
  is '��ǩ����';
alter table t_key_content_tag
  add constraint PK_t_key_content_tag_ID primary key (contentid);


------------�����ص�Ӧ�ü�ر�---------
-- Create table
create table t_pivot_app_monitor
(
  type   VARCHAR2(2),
  appid  VARCHAR2(30),
  packagename VARCHAR2(255),
  name VARCHAR2(255)
);
comment on table t_pivot_app_monitor
  is '�ص�Ӧ�ü�ر�';
comment on column t_pivot_app_monitor.type
  is '������ͣ�1-MMӦ��,2-���Ӧ��';
comment on column t_pivot_app_monitor.appid
  is 'Appid��MMӦ�ñ�����Ӧ�ÿ�ѡ��';
comment on column t_pivot_app_monitor.packagename
  is 'Packagename�����Ӧ�ñ��MMӦ�ÿ�ѡ��';
comment on column t_pivot_app_monitor.name
  is 'Ӧ������';


------------�����ص�Ӧ�ü�ؽ����---------
-- Create table

create table t_pivot_app_monitor_result
(
  type   VARCHAR2(2),
  appid  VARCHAR2(30),
  packagename VARCHAR2(255),
  name VARCHAR2(255),
  versionname VARCHAR2(50),
  updatedate VARCHAR2(30),
  hj_state VARCHAR2(1) default '0',
  hj_state_updatedate date default sysdate,
  ss_state VARCHAR2(1) default '0',
  ss_state_updatedate date default sysdate,
  dc_state VARCHAR2(1) default '0',
  dc_state_updatedate date default sysdate
);
comment on table t_pivot_app_monitor_result
  is '�ص�Ӧ�ü�ؽ����';
comment on column t_pivot_app_monitor_result.type
  is '������ͣ�1-MMӦ��,2-���Ӧ��';
comment on column t_pivot_app_monitor_result.appid
  is 'Appid��MMӦ�ñ�����Ӧ�ÿ�ѡ��';
comment on column t_pivot_app_monitor_result.packagename
  is 'Packagename�����Ӧ�ñ��MMӦ�ÿ�ѡ��';
comment on column t_pivot_app_monitor_result.name
  is 'Ӧ������';
comment on column t_pivot_app_monitor_result.versionname
  is '�汾version name';
comment on column t_pivot_app_monitor_result.updatedate
  is 'Ӧ�ø���ʱ��';
comment on column t_pivot_app_monitor_result.hj_state
  is '�Ƿ�����������ܣ�0-�ǣ�1-��';
comment on column t_pivot_app_monitor_result.hj_state_updatedate
  is '����״̬����ʱ��';
comment on column t_pivot_app_monitor_result.dc_state
  is '�Ƿ���������ͻ����Ż���0-�ǣ�1-��';
comment on column t_pivot_app_monitor_result.dc_state_updatedate
  is '�ͻ����Ż�״̬����ʱ��';
comment on column t_pivot_app_monitor_result.ss_state
  is '�Ƿ��������������0-�ǣ�1-��';
comment on column t_pivot_app_monitor_result.ss_state_updatedate
  is '����״̬����ʱ��';
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.049_SSMS','MM4.0.0.0.055_SSMS');

 
commit;

