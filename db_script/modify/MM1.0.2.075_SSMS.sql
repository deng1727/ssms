
comment on column T_R_GCONTENT.PROGRAMSIZE
  is '����װ����С����λΪK;MMӦ�ã���ֵΪ�������붩��Ӧ�õĴ���';


create or replace view ap_warn_view as
select g.contentid,
       g.spname,
       g.name,
       decode(b.type,'nt:gcontent:appGame','1','nt:gcontent:appSoftWare','0','nt:gcontent:appTheme','2') type ,
       g.dayordertimes,
       g.downloadtimes,
       g.programsize,
       g.chargetime,
       g.marketdate,
       v.mobileprice price
  from t_r_base b,
       t_r_gcontent g,
       (select *
          from (select vs.contentid,
                       vs.mobileprice,
                       row_number() over(partition by vs.contentid order by vs.mobileprice asc) rn
                  from v_service vs)
         where rn = 1 ) v
 where   g.contentid = v.contentid
   and g.id = b.id
   and g.subtype in ('1','2','5','7');


-- Create table
create table T_AP_WARN
(
  WARN_DATE          VARCHAR2(15) not null,
  CONTENT_ID         VARCHAR2(30) not null,
  WARN_TYPE          NUMBER(1) not null,
  DAY_DL_TIMES       NUMBER(15),
  YESTERDAY_DL_TIMES NUMBER(15),
  DL_7DAYS_COUNTS    NUMBER(15),
  NAME               VARCHAR2(300) not null,
  PAY_TYPE           NUMBER(1),
  WARN_DESC          VARCHAR2(500) not null,
  TYPE               NUMBER(1),
  PRICE              NUMBER(15),
  SPNAME             VARCHAR2(100),
  MARKETDATE         VARCHAR2(30)
);
-- Add comments to the columns 
comment on column T_AP_WARN.WARN_DATE
  is 'ͳ��ˢ������� YYYYMMDD';
comment on column T_AP_WARN.CONTENT_ID
  is 'ˢ������ID';
comment on column T_AP_WARN.WARN_TYPE
  is 'ˢ������ 1:������������   2����ʱ���ܼ��ҵ�λ����ʱ�����   3:������������   4:�����û��ص���  5:�������������޴�  6:����ʱ��̶�';
comment on column T_AP_WARN.DAY_DL_TIMES
  is '�������ش���';
comment on column T_AP_WARN.YESTERDAY_DL_TIMES
  is '�������ش���';
comment on column T_AP_WARN.DL_7DAYS_COUNTS
  is '7�����ش���';
comment on column T_AP_WARN.NAME
  is '��������';
comment on column T_AP_WARN.PAY_TYPE
  is '0:���  1:����';
comment on column T_AP_WARN.WARN_DESC
  is 'Ԥ������';
comment on column T_AP_WARN.TYPE
  is '��������';
comment on column T_AP_WARN.PRICE
  is '���ݼ۸�';
comment on column T_AP_WARN.SPNAME
  is '��ҵ����';
comment on column T_AP_WARN.MARKETDATE
  is '��������ʱ��';

-- Create/Recreate primary, unique and foreign key constraints 
alter table T_AP_WARN
  add constraint PK_T_BRUSH_TOP primary key (WARN_DATE, CONTENT_ID, WARN_TYPE)
  using index ;





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.070_SSMS','MM1.0.2.075_SSMS');
commit;