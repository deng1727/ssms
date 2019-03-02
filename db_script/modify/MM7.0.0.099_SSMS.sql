

alter table t_a_ppms_receive_change
add (opt varchar2(1));

alter table t_a_ppms_receive_change
add (appid varchar2(12) );

alter table t_r_gcontent
add (appid varchar2(12) );

alter table t_r_reference
add (appid varchar2(12) );



  comment on column t_a_ppms_receive_change.opt
  is '1,上线;2,下线;3下架';

  comment on column t_a_ppms_receive_change.appid
  is '聚合应用id';
  comment on column t_a_ppms_receive_change.stats
  is '1:更新;9:下线;0:上线;-1:未处理';
  
    comment on column t_a_ppms_receive_change.status
  is '1:更新;9:下线;0:上线;-1:未处理';
  
      comment on column t_a_ppms_receive_change.status
  is '-2：处理失败，-1：未处理，0：已经处理;-3,电子流货架都没有的数据 ,1:处理中';
  
 commit;`
 
 -----------新增33应用的二级分类------------------------------- 
 
  create table T_A_MM_SEC_CAREGORY
(
  MMSECCATE              varchar2(12),
  OPERATORSECCATE            VARCHAR2(12)

)
 --------------------创建同义词;商品中心C表------------------------------
  create or replace synonym V_PKG_MAPPING_OUTPUT_INCR
  for GCENTER.V_PKG_MAPPING_OUTPUT_INCR@DL_MMUC_SPZX;
------------------聚合应用表---------------------------------- 

create table T_R_GAPP
(
  id         VARCHAR2(32) not null,
  appid      VARCHAR2(12) not null,
  contentid  VARCHAR2(12) not null,
  createdate DATE
)
------------授权查询权限给portalmo用户----------
grant select on T_R_GAPP to PORTALMO;


 create table T_A_PPMS_RECEIVE_MESSAGE
(
  id              varchar2(8),
  appid            VARCHAR2(12),
  contentid          VARCHAR2(12),
  action      NUMBER(1),
  changetime    VARCHAR2(30),
  sendtime        VARCHAR2(30),
  status           NUMBER(1) 
)

----------------------预处理--
create table T_A_PPMS_PRETREATMENT_MESSAGE
(
  id              varchar2(8),
  appid            VARCHAR2(12),
  changetime    VARCHAR2(30),
  status           NUMBER(1) 
)
create sequence SEQ_PRETREATMENT_MESSAGE_ID
minvalue 10000001
maxvalue 99999999
start with 10000001
increment by 1
cache 100
cycle;

create sequence SEQ_RECEIVE_MESSAGE_ID
minvalue 10000001
maxvalue 99999999
start with 10000001
increment by 1
cache 100
cycle;

create sequence SEQ_T_R_GAPP_ID
minvalue 10000001
maxvalue 99999999
start with 10000001
increment by 1
cache 100
cycle;

commit;