

alter table t_a_ppms_receive_change
add (opt varchar2(1));

alter table t_a_ppms_receive_change
add (appid varchar2(12) );

alter table t_r_gcontent
add (appid varchar2(12) );

alter table t_r_reference
add (appid varchar2(12) );



  comment on column t_a_ppms_receive_change.opt
  is '1,����;2,����;3�¼�';

  comment on column t_a_ppms_receive_change.appid
  is '�ۺ�Ӧ��id';
  comment on column t_a_ppms_receive_change.stats
  is '1:����;9:����;0:����;-1:δ����';
  
    comment on column t_a_ppms_receive_change.status
  is '1:����;9:����;0:����;-1:δ����';
  
      comment on column t_a_ppms_receive_change.status
  is '-2������ʧ�ܣ�-1��δ����0���Ѿ�����;-3,���������ܶ�û�е����� ,1:������';
  
 commit;`
 
 -----------����33Ӧ�õĶ�������------------------------------- 
 
  create table T_A_MM_SEC_CAREGORY
(
  MMSECCATE              varchar2(12),
  OPERATORSECCATE            VARCHAR2(12)

)
 --------------------����ͬ���;��Ʒ����C��------------------------------
  create or replace synonym V_PKG_MAPPING_OUTPUT_INCR
  for GCENTER.V_PKG_MAPPING_OUTPUT_INCR@DL_MMUC_SPZX;
------------------�ۺ�Ӧ�ñ�---------------------------------- 

create table T_R_GAPP
(
  id         VARCHAR2(32) not null,
  appid      VARCHAR2(12) not null,
  contentid  VARCHAR2(12) not null,
  createdate DATE
)
------------��Ȩ��ѯȨ�޸�portalmo�û�----------
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

----------------------Ԥ����--
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