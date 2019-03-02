--------------------�Զ��ϼܴ洢����----------------------
create or replace procedure p_v_program_update as
  v_nstatus     number;
  v_nrecod      number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_v_program_update',

                                        '��������Ƶ��Ʒ�ϼܸ���');
---���������ӻ��ܹ�ϵ---
insert into t_v_hotcatemap
select SEQ_T_V_HOTCATEMAP_ID.Nextval id,
     d.displaytype,
      SEQ_T_V_CATEGORY_CID.NEXTVAL  as categoryid,
         '2',
         sysdate                       as lupdate
    from (select distinct  d.displaytype  from t_v_dprogram d
   where not exists (select 1
            from t_v_hotcatemap m1
           where d.displaytype = to_number(m1.titleid))) d;


----��������б�----
insert into t_v_category(id,categoryid,parentcid,cname,cdesc,isshow,sortid,lupdate)
select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,m.categoryid,'101', d.displayname,d.displayname,'1','1',sysdate as lupdate
  from t_v_hotcatemap m,t_v_dprogram d
 where to_number(m.titleid) = d.displaytype and m.type = '2' and   not exists (select 1
          from t_v_category c
         where m.categoryid = c.categoryid) ;


-----ɾ�����ߵ�
----------
delete from t_v_reference t where not exists(select 1 from t_v_dprogram d where d.programid=t.programid);

----�����ϼ�δ�ϼ�һ��������ܵĽ�Ŀ����Ʒ��---
----
insert into t_v_reference(ID,PROGRAMID,CATEGORYID,CMS_ID,PNAME,SORTID,LUPDATE)
select SEQ_T_V_REFERENCE_ID.NEXTVAL as id, d.programid,d.categoryid, cms_id, pname,rownum as sortid,sysdate as lupdate
from (select distinct d.programid,m1.categoryid,d.cmsid as cms_id,d.name as pname
  from t_v_dprogram d,t_v_hotcatemap m1
 where d.displaytype=to_number(m1.titleid) and  not exists (select 1
          from t_v_reference r, t_v_hotcatemap m
         where r.categoryid = m.categoryid
           and m.type = '2'
           and to_number(m.titleid) = d.displaytype
            and r.programid=d.programid)) d ;



 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end p_v_program_update;

---------------------------��������������Ҫ���ֶ�-------------------------------
-- Add/modify columns 
alter table T_V_CATEGORY add video_status varchar2(10) default '1';
alter table T_V_CATEGORY add goods_status varchar2(10) default '1';
alter table T_V_CATEGORY add delpro_status NUMBER(2) default 2;
-- Add comments to the columns 
comment on column T_V_CATEGORY.video_status
  is '��������״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column T_V_CATEGORY.goods_status
  is '��Ʒ����״̬--  0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column T_V_CATEGORY.delpro_status
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
  
-- Add/modify columns 
alter table T_V_REFERENCE add verify_status number(2) default 1;
alter table T_V_REFERENCE add delflag number(2) default 2;
-- Add comments to the columns 
comment on column T_V_REFERENCE.verify_status
  is '����״̬-- 0 �༭��1 �ѷ�����2 ������;3 ������ͨ��';
comment on column T_V_REFERENCE.delflag
  is 'ɾ��֮ǰ״̬-- 0-�༭��1-�ѷ���;2-����;3-������ͨ��';
  
-- Create table
create table T_V_CATEGORY_OPERATION
(
  id            VARCHAR2(30),
  operator      VARCHAR2(30),
  categoryid    VARCHAR2(20),
  operator_time DATE default sysdate,
  approval      VARCHAR2(30),
  approval_time DATE default sysdate,
  operation     VARCHAR2(10)
);
-- Add comments to the table 
comment on table T_V_CATEGORY_OPERATION
  is '��ƵPOMS����������';
-- Add comments to the columns 
comment on column T_MB_CATEGORY_NEW_OPERATION.operator
  is '������';
comment on column T_MB_CATEGORY_NEW_OPERATION.categoryid
  is '���ܱ���';
comment on column T_MB_CATEGORY_NEW_OPERATION.operator_time
  is '����ʱ��';
comment on column T_MB_CATEGORY_NEW_OPERATION.approval
  is '������';
comment on column T_MB_CATEGORY_NEW_OPERATION.approval_time
  is '����ʱ��';
comment on column T_MB_CATEGORY_NEW_OPERATION.operation
  is '����ʽ: 1 POMS���ܹ���; 2 POMS��Ʒ����';

-- Create sequence 
create sequence SEQ_V_APPROVAL_ID
minvalue 1
maxvalue 9999999999
start with 1;

---------------------------------------------------------------------------------------

insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_POMSCATEGORY_RESULT_001','POMS���ܹ����ύ�����ɹ�','POMS���ܹ����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_POMSCATEGORY_RESULT_002','POMS���ܹ������������ɹ�','POMS���ܹ������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_POMSCATEGORY_RESULT_003','POMS���ܹ���������ͨ���ɹ�','POMS���ܹ���������ͨ���ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_POMSCATEGORY_RESULT_004','POMS��Ʒ�����ύ�����ɹ�','POMS��Ʒ�����ύ�����ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_POMSCATEGORY_RESULT_005','POMS��Ʒ�������������ɹ�','POMS��Ʒ�������������ɹ�');
insert into t_resource(RESOURCEKEY,RESOURCEVALUE,remard)values('RESOURCE_POMSCATEGORY_RESULT_006','POMS��Ʒ����������ͨ���ɹ�','POMS��Ʒ����������ͨ���ɹ�');
---------------------------------------------------------------------------------------


alter table t_v_dprogram add FEETYPE NUMBER(2);

comment on column t_v_dprogram.FEETYPE is '�ʷ�����,ö��ֵ��1-��ѣ�2-�շ�; 3-��֧�ְ���';

alter table T_V_REFERENCE add FEETYPE NUMBER(2);

comment on column T_V_REFERENCE.FEETYPE is '�ʷ�����,ö��ֵ��1-��ѣ�2-�շ�; 3-��֧�ְ���';
-- Add/modify columns 
alter table T_V_CATEGORY add delflag NUMBER(2) default 0;
-- Add comments to the columns 
comment on column T_V_CATEGORY.delflag
  is 'ɾ����ʶ��1 ɾ����0 ����';
  -- Add/modify columns 
alter table T_V_REFERENCE add verify_date date default sysdate;
-- Add comments to the columns 
comment on column T_V_REFERENCE.verify_date
  is '����ʱ��';

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.109_SSMS','MM4.0.0.0.129_SSMS');
commit;