------���㷺����������Ϣ��ͼ-------
create or replace view v_touchspot_ap as
select c.channelsid as apid,c.channelsname as apname 
from t_open_channels c 
-----������ʽ������������----
where c.status = '0' and c.cotype like '%2%';
------���㷺��Ӧ����Ϣ��ͼ-------
create or replace view v_touchspot_app as
select g.contentid as appid,c.channelsid as apid,g.name as appname
from t_r_gcontent g,t_open_channels c 
where c.channelsid = g.companyid;

alter table t_r_gcontent add channeldisptype VARCHAR2(1) default '0';
comment on column t_r_gcontent.channeldisptype
  is '��������������ַ����ͣ�0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�';

--------------------��v_cm_content�����ֶ�channeldisptype----------------
alter  table v_cm_content add channeldisptype VARCHAR2(1);
comment on column v_cm_content.channeldisptype
  is '��������������ַ����ͣ�0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�';
  
--------���㷺��-�ն˹�˾�����������˺ű�T_OPEN_CHANNELS������4���ֶ�   ��ʼ------------------------------------------------------------------------------------------
alter  table T_OPEN_CHANNELS add cotype VARCHAR2(10);
alter  table T_OPEN_CHANNELS add codate DATE default sysdate;
alter  table T_OPEN_CHANNELS add channelsnumber VARCHAR2(10);
comment on column T_OPEN_CHANNELS.cotype
  is '�������ͣ�1-���ݺ�����2-��������';
comment on column T_OPEN_CHANNELS.codate
  is '��������';
comment on column T_OPEN_CHANNELS.channelsnumber
  is '��������';
  
--------���㷺��-�ն˹�˾�����������˺ű�T_OPEN_CHANNELS������4���ֶ�   ����------------------------------------------------------------------------------------------


--------���������ű�T_OPEN_CHANNEL_NO��   ��ʼ--------------------------------------------------------------------------------------------------------------
create table T_OPEN_CHANNEL_NO
(
  channelsno VARCHAR2(20) not null,
  createdate DATE,
  operator   VARCHAR2(30),
  status     VARCHAR2(2) default 2
);
-- Add comments to the table 
comment on table T_OPEN_CHANNEL_NO
  is '�����ű�';
-- Add comments to the columns 
comment on column T_OPEN_CHANNEL_NO.channelsno
  is '������ID';
comment on column T_OPEN_CHANNEL_NO.createdate
  is '��������';
comment on column T_OPEN_CHANNEL_NO.operator
  is '������';
comment on column T_OPEN_CHANNEL_NO.status
  is '����״̬��1-��ʹ�ã�2-δʹ��';
--------���������ű�T_OPEN_CHANNEL_NO��   ����-------------------------------------------------------------------------------------------------------------

--------������������Ϣ��T_OPEN_CHANNEL_INFO��   ��ʼ---------------
create table T_OPEN_CHANNEL_INFO
(
  channelid   VARCHAR2(20) not null,
  channeltype VARCHAR2(2),
  channelname VARCHAR2(100),
  channeldesc VARCHAR2(200),
  status   VARCHAR2(2),
  createdate  DATE default sysdate,
  updatedate  DATE default sysdate,
  channelsid  VARCHAR2(20) not null
);
-- Add comments to the table 
comment on table T_OPEN_CHANNEL_INFO
  is '������������Ϣ��';
-- Add comments to the columns 
comment on column T_OPEN_CHANNEL_INFO.channelid
  is '����id';
comment on column T_OPEN_CHANNEL_INFO.channeltype
  is '�������ͣ�0-�ͻ���,1-���ݽӿ�,2-��ҳ';
comment on column T_OPEN_CHANNEL_INFO.channelname
  is '��������';
comment on column T_OPEN_CHANNEL_INFO.channeldesc
  is '��������';
comment on column T_OPEN_CHANNEL_INFO.status
  is '����״̬��0-����,1-��ֹ';
comment on column T_OPEN_CHANNEL_INFO.createdate
  is '����ʱ��';
comment on column T_OPEN_CHANNEL_INFO.updatedate
  is '����ʱ��';
comment on column T_OPEN_CHANNEL_INFO.channelsid
  is '������id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_OPEN_CHANNEL_INFO
  add constraint PK_T_OPEN_CHANNEL_INFO_ID primary key (CHANNELID);
--------������������Ϣ��T_OPEN_CHANNEL_INFO������---------------  

--------����ͬ���cm_ct_program_apply ��ʼ---------
create or replace synonym cm_ct_program_apply
  for cm_ct_program_apply@DL_MM_PPMS_NEW;
--------����ͬ���cm_ct_program_apply ����--------- 
  
---------ppms_v_cm_content ��ͼ��ʼ-----
create or replace view ppms_v_cm_content as
select a.typename,
       b.catalogid,
       b.name as cateName,
       c.contentid,
       c.name,
       decode(c.thirdapptype,
              '10',
              c.oviappid,
              '12',
              c.oviappid,
              '16',
              c.oviappid,
              c.contentcode) ContentCode,
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
              f.SubOnlineDate) as marketdate,
       --ȫ��ȡonlinedate��ʡ��ȡSubOnlineDate
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       decode(c.thirdapptype,
              '12',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '12'),
              '16',
              (select max(m.developername)
                 from s_cm_content_motoext m
                where c.oviappid = m.appid
                  and m.thirdapptype = '16'),
              decode(c.companyid,
                     '116216',
                     '2010MM��ҵ�ƻ�����Ӧ��չʾ',
                     decode(c.devcompanyid,
                            null,
                            e.companyname,
                            (select so.companyname
                               from soft_company so
                              where so.companyid = c.devcompanyid)))) as companyname, --�ṩ��
       decode(c.mapcompanyid,
              null,
              '',
              (select so1.companyname
                 from om_company so1
                where c.mapcompanyid = so1.companyid)) devcompanyname, --������
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate, e.lupddate) as plupddate,
       c.conlupddate as lupddate, -----����Ӧ�ø���ʱ��
       decode(c.thirdapptype, '7', '2', f.chargeTime) chargeTime,
       --decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype) thirdapptype, ����APP����
       decode(c.secondarytype,
              '2',
              '21',
              decode(c.thirdapptype, '13', '1', '14', '1', c.thirdapptype)) thirdapptype,
       c.pvcid,
       c.citysid as cityid,
       decode(f.chargetime ||
              decode(c.chargetype, '01', '02', c.chargetype) || c.contattr ||
              e.operationsmode || c.thirdapptype,
              '102G01',
              '1',
              '102G02',
              '1',
              '102G05',
              '1',
              '102G012',
              '1',
              '0') as othernet,
       c.ismmtoevent,
       c.COPYRIGHTFLAG,
       decode((select count(pa.channel_disp_type) from cm_ct_program_apply pa where pa.contentid=c.contentid and
       pa.channel_disp_type = '1'),0,'0','1') as channeldisptype
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       v_valid_company    e,
       OM_PRODUCT_CONTENT f --
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid ----ap����
   and (c.status = '0006' or c.status = '1006' or f.status = 5 or
       ((f.substatus = '1' or f.substatus = '0') and
       (c.status = '0015' or c.status = '1015') and (f.status = 6)))
      ----Ӧ�����û��������û���ʡ������/����
   and d.AuditStatus = '0003' ----��Ʒ���ͨ��
   and d. ProductStatus in ('2', '3', '5') ----��Ʒ���߼Ʒ�or���Ʒ�or ����
   and f.ID = d.ID ----���ɲ�Ʒ
   and c.contentid = f.contentid ----��Ʒ��ID���������ݱ���
   and d.startdate <= sysdate
   and (d.paytype = 0 or d.paytype is null)
   and (c.thirdapptype in
       ('1', '2', '7', '11', '12', '13', '14', '16', '21') or
       (c.thirdapptype = '5' and c.Jilstatus = '1'));

 ---------ppms_v_cm_content ��ͼ��ʼ-----
  
  
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_COOPERATION_RESULT_001','���������̳ɹ�','���������̳ɹ�'); 
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_COOPERATION_RESULT_002','���º����̳ɹ�','���º����̳ɹ�');


insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CHANNELINFO_RESULT_001','���������ɹ�','���������ɹ�'); 
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CHANNELINFO_RESULT_002','���������ɹ�','���������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_CHANNELINFO_RESULT_003','�����������꣬�����������','�����������꣬�����������');
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.095_SSMS','MM4.0.0.0.099_SSMS');

 
commit;