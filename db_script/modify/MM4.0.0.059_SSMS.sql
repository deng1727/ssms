-----------���㷺������ʼ-------------
-------�����������˺ű�-------
create table t_open_channels
(
   channelsid VARCHAR2(12) not null,
   channelsname VARCHAR2(100) not null,
   parentchannelsid VARCHAR2(20) default '1000',
   parentchannelsname VARCHAR2(100) default 'MM',
   channelsdesc VARCHAR2(500),
   channelsno VARCHAR2(20) not null,
   channelspwd VARCHAR2(32) not null,
   status VARCHAR2(2) default '0',
   createdate date default sysdate,
   modifydate date default sysdate
);
alter table t_open_channels
  add constraint PK_t_open_channels_id primary key (channelsid);
comment on table t_open_channels
  is '�������˺ű�';
comment on column t_open_channels.channelsid
  is '������id';
comment on column t_open_channels.channelsname
  is '����������';
comment on column t_open_channels.parentchannelsid
  is '����������������id';
comment on column t_open_channels.parentchannelsname
  is '��������������������';
comment on column t_open_channels.channelsdesc
  is '����������';
comment on column t_open_channels.channelsno
  is '�������˺�';
comment on column t_open_channels.channelspwd
  is '����������';
comment on column t_open_channels.status
  is '������״̬��0-������1-����';
comment on column t_open_channels.createdate
  is '����ʱ��';
comment on column t_open_channels.modifydate
  is '�޸�ʱ��';

-------����t_open_channels��channelsid������������-----
create sequence SEQ_t_open_channels_id
minvalue 1001
maxvalue 999999999999
start with 1001
increment by 1
nocache
cycle;

-------����������Ӫ������-------
create table t_open_operation_channel
(
   channelid VARCHAR2(20) not null,
   channelsid VARCHAR2(20) not null,
   createdate date default sysdate
);
alter table t_open_operation_channel
  add constraint PK_t_open_operation_channel_id primary key (channelid);
comment on table t_open_operation_channel
  is '������Ӫ������';
comment on column t_open_operation_channel.channelid
  is '������Ӫ����id';
  comment on column t_open_operation_channel.channelsid
  is '������id';
comment on column t_open_operation_channel.createdate
  is '����ʱ��';
  
-------�����ͻ���������-------
create table t_open_channel_mo
(
   channelid VARCHAR2(20) not null,
   channelname VARCHAR2(100) not null,
   channelsid VARCHAR2(20) not null,
   createdate date default sysdate
);
alter table t_open_channel_mo
  add constraint PK_t_open_channel_mo_id primary key (channelid);
comment on table t_open_channel_mo
  is '�ͻ���������';
comment on column t_open_channel_mo.channelid
  is '�ͻ�������id';
comment on column t_open_channel_mo.channelsid
 	is '������id';
comment on column t_open_channel_mo.channelname
  is '�ͻ�����������';
comment on column t_open_channel_mo.createdate
  is '����ʱ��';


-------���������̻��ܹ�ϵ��-------
create table t_open_channels_category
(
   channelsid VARCHAR2(12) not null,
   categoryid  VARCHAR2(12) not null
);
comment on table t_open_channels_category
  is '�����̸����ܹ�ϵ��';
comment on column t_open_channels_category.channelsid
  is '��Ӧ������id';
comment on column t_open_channels_category.categoryid
  is '��Ӧ������id';


-----------���㷺���������-------------

---------------v_hotcontent��ͼ��ʼ-------------------------------------
 create or replace view v_hotcontent as
select t."ID",t."CONTENTID",t."NAME",t."CATENAME", rownum as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='���'
and g.id not in (select gcontentid from t_intervenor_gcontent_map t
where intervenorid = '2092'
)
order by t.hotlist desc, g.id desc ) t where rownum <=160
union
select t."ID",t."CONTENTID",t."NAME",t."CATENAME", rownum+160 as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='��Ϸ'
and g.id not in (select gcontentid from t_intervenor_gcontent_map t
where intervenorid = '2092'
)
order by t.hotlist desc, g.id desc ) t where rownum <=80;

-----------------v_hotcontent��ͼ����-----------------------------------


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.055_SSMS','MM4.0.0.0.059_SSMS');

 
commit;