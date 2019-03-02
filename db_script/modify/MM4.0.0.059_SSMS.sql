-----------触点泛化需求开始-------------
-------创建渠道商账号表-------
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
  is '渠道商账号表';
comment on column t_open_channels.channelsid
  is '渠道商id';
comment on column t_open_channels.channelsname
  is '渠道商名称';
comment on column t_open_channels.parentchannelsid
  is '所属（父）渠道商id';
comment on column t_open_channels.parentchannelsname
  is '所属（父）渠道商名称';
comment on column t_open_channels.channelsdesc
  is '渠道商描述';
comment on column t_open_channels.channelsno
  is '渠道商账号';
comment on column t_open_channels.channelspwd
  is '渠道商密码';
comment on column t_open_channels.status
  is '渠道商状态：0-正常，1-下线';
comment on column t_open_channels.createdate
  is '创建时间';
comment on column t_open_channels.modifydate
  is '修改时间';

-------创建t_open_channels表channelsid的自增长序列-----
create sequence SEQ_t_open_channels_id
minvalue 1001
maxvalue 999999999999
start with 1001
increment by 1
nocache
cycle;

-------创建开发运营渠道表-------
create table t_open_operation_channel
(
   channelid VARCHAR2(20) not null,
   channelsid VARCHAR2(20) not null,
   createdate date default sysdate
);
alter table t_open_operation_channel
  add constraint PK_t_open_operation_channel_id primary key (channelid);
comment on table t_open_operation_channel
  is '开发运营渠道表';
comment on column t_open_operation_channel.channelid
  is '开发运营渠道id';
  comment on column t_open_operation_channel.channelsid
  is '渠道商id';
comment on column t_open_operation_channel.createdate
  is '创建时间';
  
-------创建客户端渠道表-------
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
  is '客户端渠道表';
comment on column t_open_channel_mo.channelid
  is '客户端渠道id';
comment on column t_open_channel_mo.channelsid
 	is '渠道商id';
comment on column t_open_channel_mo.channelname
  is '客户端渠道名称';
comment on column t_open_channel_mo.createdate
  is '创建时间';


-------创建渠道商货架关系表-------
create table t_open_channels_category
(
   channelsid VARCHAR2(12) not null,
   categoryid  VARCHAR2(12) not null
);
comment on table t_open_channels_category
  is '渠道商根货架关系表';
comment on column t_open_channels_category.channelsid
  is '对应渠道商id';
comment on column t_open_channels_category.categoryid
  is '对应根货架id';


-----------触点泛化需求结束-------------

---------------v_hotcontent视图开始-------------------------------------
 create or replace view v_hotcontent as
select t."ID",t."CONTENTID",t."NAME",t."CATENAME", rownum as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='软件'
and g.id not in (select gcontentid from t_intervenor_gcontent_map t
where intervenorid = '2092'
)
order by t.hotlist desc, g.id desc ) t where rownum <=160
union
select t."ID",t."CONTENTID",t."NAME",t."CATENAME", rownum+160 as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='游戏'
and g.id not in (select gcontentid from t_intervenor_gcontent_map t
where intervenorid = '2092'
)
order by t.hotlist desc, g.id desc ) t where rownum <=80;

-----------------v_hotcontent视图结束-----------------------------------


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.055_SSMS','MM4.0.0.0.059_SSMS');

 
commit;