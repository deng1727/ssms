
comment on column T_R_GCONTENT.PROGRAMSIZE
  is '程序安装包大小，单位为K;MM应用，此值为昨天申请订购应用的次数';


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
  is '统计刷榜的日期 YYYYMMDD';
comment on column T_AP_WARN.CONTENT_ID
  is '刷榜内容ID';
comment on column T_AP_WARN.WARN_TYPE
  is '刷榜类型 1:下载量波动大   2下载时间密集且单位下载时间过短   3:连号消费现象   4:下载用户重叠率  5:地市消费增幅巨大  6:消费时间固定';
comment on column T_AP_WARN.DAY_DL_TIMES
  is '当日下载次数';
comment on column T_AP_WARN.YESTERDAY_DL_TIMES
  is '昨日下载次数';
comment on column T_AP_WARN.DL_7DAYS_COUNTS
  is '7天下载次数';
comment on column T_AP_WARN.NAME
  is '内容名称';
comment on column T_AP_WARN.PAY_TYPE
  is '0:免费  1:付费';
comment on column T_AP_WARN.WARN_DESC
  is '预警描述';
comment on column T_AP_WARN.TYPE
  is '内容类型';
comment on column T_AP_WARN.PRICE
  is '内容价格';
comment on column T_AP_WARN.SPNAME
  is '企业名称';
comment on column T_AP_WARN.MARKETDATE
  is '内容上线时间';

-- Create/Recreate primary, unique and foreign key constraints 
alter table T_AP_WARN
  add constraint PK_T_BRUSH_TOP primary key (WARN_DATE, CONTENT_ID, WARN_TYPE)
  using index ;





insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.2.070_SSMS','MM1.0.2.075_SSMS');
commit;