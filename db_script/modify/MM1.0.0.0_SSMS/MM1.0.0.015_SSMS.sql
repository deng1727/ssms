--ɾ�������������ݣ�������Ʒ�����ܡ���������
--���� t_r_base,t_r_reference,t_r_gcontent
create table t_r_base_015 as select * from t_r_base;
create table t_r_reference_015 as select * from t_r_reference;
create table t_r_gcontent_015 as select * from t_r_gcontent;
create table t_r_category_015 as select * from t_r_category;


delete from t_r_base b where exists (select 1 from t_r_reference r where r.id=b.id and r.refnodeid like 'a8%');
delete from t_r_reference r where r.refnodeid like 'a8%';

delete from t_r_category c where exists (select 1 from t_r_base b where c.id=b.id and b.parentid in ('3001','3002','3003'));
delete from t_r_base b where b.parentid in ('3001','3002','3003');

delete from t_r_gcontent g where g.id like 'a8%';
delete from t_r_base b where b.id like 'a8%';

--��ʼ�����ַ��Ƶ������Ƶ��
insert into t_r_base(id,parentid,path,type) values('3101','3001','{100}.{701}.{1012}.{3001}.{3101}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3101','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3102','3001','{100}.{701}.{1012}.{3001}.{3102}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3102','��ҥ','�ն���������Ƶ������ҥ',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3103','3001','{100}.{701}.{1012}.{3001}.{3103}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3103','ҡ��','�ն���������Ƶ����ҡ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3104','3001','{100}.{701}.{1012}.{3001}.{3104}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3104','��³˹','�ն���������Ƶ������³˹',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3105','3001','{100}.{701}.{1012}.{3001}.{3105}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3105','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3106','3001','{100}.{701}.{1012}.{3001}.{3106}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3106','�й���ͳ����','�ն���������Ƶ�����й���ͳ����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3107','3001','{100}.{701}.{1012}.{3001}.{3107}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3107','˵��','�ն���������Ƶ����˵��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3108','3001','{100}.{701}.{1012}.{3001}.{3108}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3108','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3109','3001','{100}.{701}.{1012}.{3001}.{3109}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3109','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3110','3001','{100}.{701}.{1012}.{3001}.{3110}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3110','���','�ն���������Ƶ�������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3111','3001','{100}.{701}.{1012}.{3001}.{3111}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3111','��ʿ','�ն���������Ƶ������ʿ',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3112','3001','{100}.{701}.{1012}.{3001}.{3112}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3112','�ŵ�','�ն���������Ƶ�����ŵ�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3113','3001','{100}.{701}.{1012}.{3001}.{3113}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3113','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--��ʼ����������Ƶ������Ƶ��
insert into t_r_base(id,parentid,path,type) values('3201','3002','{100}.{701}.{1012}.{3002}.{3201}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3201','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3202','3002','{100}.{701}.{1012}.{3002}.{3202}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3202','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3203','3002','{100}.{701}.{1012}.{3002}.{3203}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3203','Ӣ��','�ն���������Ƶ����Ӣ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3204','3002','{100}.{701}.{1012}.{3002}.{3204}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3204','�������','�ն���������Ƶ�����������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3205','3002','{100}.{701}.{1012}.{3002}.{3205}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3205','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3206','3002','{100}.{701}.{1012}.{3002}.{3206}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3206','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3207','3002','{100}.{701}.{1012}.{3002}.{3207}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3207','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3208','3002','{100}.{701}.{1012}.{3002}.{3208}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3208','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3209','3002','{100}.{701}.{1012}.{3002}.{3209}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3209','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3210','3002','{100}.{701}.{1012}.{3002}.{3210}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3210','������','�ն���������Ƶ����������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3211','3002','{100}.{701}.{1012}.{3002}.{3211}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3211','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3212','3002','{100}.{701}.{1012}.{3002}.{3212}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3212','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3213','3002','{100}.{701}.{1012}.{3002}.{3213}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3213','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3214','3002','{100}.{701}.{1012}.{3002}.{3214}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3214','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3215','3002','{100}.{701}.{1012}.{3002}.{3215}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3215','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--��ʼ�����ֵ���Ƶ������Ƶ��
insert into t_r_base(id,parentid,path,type) values('3301','3003','{100}.{701}.{1012}.{3003}.{3301}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3301','�ڵ��и���','�ն���������Ƶ�����ڵ��и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3302','3003','{100}.{701}.{1012}.{3003}.{3302}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3302','�ڵ�Ů����','�ն���������Ƶ�����ڵ�Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3303','3003','{100}.{701}.{1012}.{3003}.{3303}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3303','�ڵ����','�ն���������Ƶ�����ڵ����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3304','3003','{100}.{701}.{1012}.{3003}.{3304}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3304','��̨�и���','�ն���������Ƶ������̨�и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3305','3003','{100}.{701}.{1012}.{3003}.{3305}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3305','��̨Ů����','�ն���������Ƶ������̨Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3306','3003','{100}.{701}.{1012}.{3003}.{3306}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3306','��̨���','�ն���������Ƶ������̨���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3307','3003','{100}.{701}.{1012}.{3003}.{3307}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3307','ŷ���и���','�ն���������Ƶ����ŷ���и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3308','3003','{100}.{701}.{1012}.{3003}.{3308}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3308','ŷ��Ů����','�ն���������Ƶ����ŷ��Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3309','3003','{100}.{701}.{1012}.{3003}.{3309}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3309','ŷ�����','�ն���������Ƶ����ŷ�����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3310','3003','{100}.{701}.{1012}.{3003}.{3310}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3310','�պ��и���','�ն���������Ƶ�����պ��и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3311','3003','{100}.{701}.{1012}.{3003}.{3311}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3311','�պ�Ů����','�ն���������Ƶ�����պ�Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3312','3003','{100}.{701}.{1012}.{3003}.{3312}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3312','�պ����','�ն���������Ƶ�����պ����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3313','3003','{100}.{701}.{1012}.{3003}.{3313}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3313','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--��t_r_category��t_r_base�Ĺ�ϵ�в��ҵ���Ӧÿ�����ܵĸ����ܱ��룬Ȼ����뵽t_r_category����
update t_r_category t3 set t3.PARENTCATEGORYID = (select CATEGORYID from t_r_category where id=(select t2.parentid from t_r_category t1,t_r_base t2 where t2.id=t1.id and t1.id=t3.id));

-- Create  T_SINGER table
create table T_SINGER
(
  ID          VARCHAR2(25) not null,
  NAME        VARCHAR2(50) not null,
  REGION      VARCHAR2(40),
  TYPE        VARCHAR2(40),
  FIRSTLETTER VARCHAR2(50),
  SINGERZONE  VARCHAR2(90),
  primary key (ID)
);
-- Add comments to the columns 
comment on column T_SINGER.ID
  is '����ID';
comment on column T_SINGER.NAME
  is '	��������';
comment on column T_SINGER.REGION
  is '���ֵ���';
comment on column T_SINGER.TYPE
  is '��������';
comment on column T_SINGER.FIRSTLETTER
  is '	��������ĸ';
-- Create/Recreate indexes 
create index T_SINGER_INITIAL on T_SINGER (FIRSTLETTER);
create index T_SINGER_SINGERZONE on T_SINGER (SINGERZONE);

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.013_SSMS','MM1.0.0.015_SSMS');

--����v_cm_content��ͼ��companycode ����Ϊapcode��ֵΪicpcode
create or replace view v_cm_content as
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
              c.status) as status,
       c.createdate,
       f.onlinedate as marketdate,
       d.servicecode as icpservid,
       d.ProductID,
       e.apcode as icpcode,
       e.companyid,
       e.companyname,
       substr(f.paymethod, 2, 1) as isSupportDotcard,
       greatest(c.lupddate, f.lastupdatedate, f.contentlupddate) as lupddate,
       f.chargeTime
  from cm_content_type    a,
       cm_catalog         b,
       cm_content         c,
       v_om_product       d,
       om_company         e,
       OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype
   and b.catalogid = c.catalogid
   and c.companyid = e.companyid
   and (c.status = '0006' or c.status = '1006' or c.status = '0008')
   and d.AuditStatus = '0003'
   and f.ProductID = d.ProductID
   and c.contentid = f.contentid
   ;

--����V_SERVICE��ͼ

drop materialized view V_SERVICE;

create materialized view V_SERVICE
refresh force on demand
as
select v1.apcode as icpcode,
       v1.CompanyName as spname,
       v1.ShortName as spshortname,
       v2.ServiceCode as icpservid,
       v2.ProductName as servname,
       decode(v2.ProductStatus, '2', 'A', '3', 'B', '4', 'P', '5', 'E') as SERVSTATUS,
       decode(v2.ACCESSMODEID,'00','S','01','W','02','M','10','A','05','E') as umflag,
       decode(v2.ServiceType, 1, 8, 2, 9) as servtype,
       v2.ChargeType as ChargeType,
       v2.Fee as mobileprice,
       V2.PayMode_card as dotcardprice,
       v2.ChargeDesc,
       v2.Description as servdesc,
       v1.apcode||'_'||v2.ServiceCode as pksid,
       v2.LUPDDate
  from om_company v1,
       v_om_product v2,
       (select v1.companycode, v2.ServiceCode
          from OM_Company         v1,
               OM_PRODUCT_CONTENT p,
               cm_content         c,
               v_om_product       v2
         where p.contentid = c.contentid
           and c.companyid = v1.companyid
           and p.productid = v2.productid
         group by (v1.companycode, v2.ServiceCode)) t
 where v1.companycode = t.companycode
   and v2.ServiceCode = t.ServiceCode;
--��������
create index inx_v_service_code_servid on v_service (icpcode,icpservid);

--ȫ������ͬ��
delete from t_lastsynctime;
---T_R_REFERENCE��������
create unique index INDX_T_R_REF_GOODSID on T_R_REFERENCE (GOODSID);

---���������Ƽ�����һ������
insert into t_r_base(id,parentid,path,type) values('1013','701','{100}.{701}.{1013}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1013','�����Ƽ�','�����Ƽ�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
commit;
