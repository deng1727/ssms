--------------------------���� t_v_apiRequestParamter�� ��ʼ----------------------------------------------------------------------

create table t_v_apiRequestParamter(
   content varchar2(2),
   contentType varchar2(20),
   contentName varchar2(20)
);

comment on table t_v_apiRequestParamter
  is '����Ƶapi�������';
comment on column  t_v_apiRequestParamter.content is '����';
comment on column  t_v_apiRequestParamter.contentType is '��������';
comment on column  t_v_apiRequestParamter.contentName is '���ݷ���';
-- ��ͨ��Ŀ
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1000','��Ӱ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1001','���Ӿ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1002','��ʵ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1003','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1004','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1005','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1006','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1007','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1008','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1009','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1010','ԭ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1011','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500020','ֱ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500060','����-����С˵');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500067','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500072','����-��̨');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500078','����-�ȵ���Ѷ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500100','����-��ͯ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500106','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500111','����-���а���');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500213','�����ƹ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500323','��Ƶ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500405','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500468','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500424','��Ц');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500377','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500320','�ƾ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500422','ʱ��');

--ҵ���Ʒ�Ͳ�Ʒ�����Ʒ�
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1000','��Ӱ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1001','���Ӿ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1002','��ʵ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1003','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1004','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1005','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1006','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1007','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1008','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1009','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1010','ԭ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1011','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500020','ֱ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500060','����-����С˵');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500067','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500072','����-��̨');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500078','����-�ȵ���Ѷ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500100','����-��ͯ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500106','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500111','����-���а���');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500213','�����ƹ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500323','��Ƶ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500405','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500468','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500424','��Ц');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500377','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500320','�ƾ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500422','ʱ��');
--�����ݷ�
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1000','��Ӱ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1001','���Ӿ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1002','��ʵ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1003','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1004','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1005','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1006','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1007','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1008','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1009','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1010','ԭ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1011','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500020','ֱ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500060','����-����С˵');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500067','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500072','����-��̨');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500078','����-�ȵ���Ѷ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500100','����-��ͯ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500106','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500111','����-���а���');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500213','�����ƹ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500323','��Ƶ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500405','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500468','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500424','��Ц');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500377','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500320','�ƾ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500422','ʱ��');
--�ȵ������б�
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1000','��Ӱ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1001','���Ӿ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1002','��ʵ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1003','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1004','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1005','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1006','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1007','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1008','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1009','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1010','ԭ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1011','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500020','ֱ��');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500060','����-����С˵');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500067','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500072','����-��̨');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500078','����-�ȵ���Ѷ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500100','����-��ͯ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500106','����-����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500111','����-���а���');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500213','�����ƹ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500323','��Ƶ');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500405','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500468','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500424','��Ц');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500377','����');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500320','�ƾ�');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500422','ʱ��');
--------------------------���� t_v_apiRequestParamter�� ��ʼ----------------------------------------------------------------------

----------------------------��t_v_reference ���������ֶ� ��ʼ-----------------------------------------------------------------------
alter table t_v_reference add broadcast varchar(100);
alter table t_v_reference add countriy varchar(100);
alter table t_v_reference add contentType varchar(100);
-- Add comments to the columns 
comment on column t_v_reference.broadcast
  is '�������';
-- Add comments to the columns 
comment on column t_v_reference.countriy
  is '���Ҽ�����';
-- Add comments to the columns 
comment on column t_v_reference.contentType
  is '��������';
----------------------------��t_v_reference ���������ֶ� ����-----------------------------------------------------------------------

----------------------------�޸Ĵ洢����p_v_program_update ��ʼ--------------------------------------------------------------------
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
select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,d.categoryid,'101', d.displayname,d.displayname,'1','1',sysdate as lupdate
from (select distinct m.categoryid,d.displayname
  from t_v_hotcatemap m,t_v_dprogram d
 where to_number(m.titleid) = d.displaytype and m.type = '2' and   not exists (select 1
          from t_v_category c
         where m.categoryid = c.categoryid))d ;

insert into t_v_category(id,categoryid,parentcid,cname,cdesc,isshow,sortid,lupdate)
select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,d.locationid,'105', d.loc_name,d.loc_desc,'1','1',sysdate as lupdate
  from T_V_HOTCONTENT_LOCATION d
 where  not exists (select 1
          from t_v_category c
         where d.locationid = c.categoryid) ;


-----ɾ�����ߵ�
----------
delete from t_v_reference t where not exists(select 1 from t_v_dprogram d where d.programid=t.programid);

delete from t_v_reference t where  exists(select 1 from t_v_category d where d.categoryid = t.categoryid and d.parentcid = '105');

----�����ϼ�δ�ϼ�һ��������ܵĽ�Ŀ����Ʒ��---
----
insert into t_v_reference(ID,PROGRAMID,CATEGORYID,CMS_ID,PNAME,SORTID,FEETYPE,LUPDATE,broadcast,countriy,contentType)
select SEQ_T_V_REFERENCE_ID.NEXTVAL as id, d.programid,d.categoryid, cms_id, pname,rownum as sortid,d.FEETYPE,sysdate as lupdate,d.broadcast,d.countriy,d.contentType
from (select distinct d.programid,m1.categoryid,d.cmsid as cms_id,d.name as pname,d.FEETYPE,p.broadcast,p.countriy,p.contentType
  from t_v_dprogram d,t_v_hotcatemap m1,(select  distinct d.programid,d.cmsid cms_id,p1.broadcast,p2.countriy,p3.contentType from t_v_dprogram d,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id) as broadcast from t_v_videospropertys p where p.propertykey = '�������'
group by p.programid,p.cms_id) p1,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|')within GROUP (order by p.programid,p.cms_id)  as countriy from t_v_videospropertys p where p.propertykey = '���Ҽ�����'
group by p.programid,p.cms_id ) p2,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id)  as contentType from t_v_videospropertys p where p.propertykey = '��������'
group by p.programid,p.cms_id) p3 where d.programid = p1.programid(+) and d.cmsid=p1.cms_id(+) and d.programid = p2.programid(+) and d.cmsid=p2.cms_id(+)
and d.programid = p3.programid(+) and d.cmsid=p3.cms_id(+) ) p
 where d.displaytype=to_number(m1.titleid) and p.cms_id(+) = d.cmsid and p.programid(+) = d.programid and  not exists (select 1
          from t_v_reference r, t_v_hotcatemap m
         where r.categoryid = m.categoryid
           and m.type = '2'
           and to_number(m.titleid) = d.displaytype
            and r.programid=d.programid)) d ;

insert into t_v_reference(ID,PROGRAMID,CATEGORYID,CMS_ID,PNAME,SORTID,LUPDATE,broadcast,countriy,contentType)
select SEQ_T_V_REFERENCE_ID.NEXTVAL as id, d.prdcontid,d.categoryid, d.contentid, d.pname,rownum as sortid,sysdate as lupdate,d.broadcast,d.countriy,d.contentType
from (select  distinct d.prdcontid, d.contentid,l.locationid categoryid,d.title pname,p.broadcast,p.countriy,p.contentType from T_V_HOTCONTENT_PROGRAM d ,T_V_HOTCONTENT_LOCATION l,(
select  distinct d.prdcontid programid,d.contentid cms_id,p1.broadcast,p2.countriy,p3.contentType from T_V_HOTCONTENT_PROGRAM d,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id) as broadcast from t_v_videospropertys p where p.propertykey = '�������'
group by p.programid,p.cms_id) p1,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|')within GROUP (order by p.programid,p.cms_id)  as countriy from t_v_videospropertys p where p.propertykey = '���Ҽ�����'
group by p.programid,p.cms_id ) p2,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id)  as contentType from t_v_videospropertys p where p.propertykey = '��������'
group by p.programid,p.cms_id) p3 where d.prdcontid = p1.programid(+) and d.contentid=p1.cms_id(+) and d.prdcontid = p2.programid(+) and d.contentid=p2.cms_id(+)
and d.prdcontid = p3.programid(+) and d.contentid=p3.cms_id(+) ) p
where d.location = l.locationid and d.contenttype in (1,2,3) and p.programid=d.prdcontid and p.cms_id = d.contentid) d ;      



 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --���ʧ�ܣ���ִ�����д����־
    v_nstatus := pg_log_manage.f_errorlog;
end p_v_program_update;
----------------------------�޸Ĵ洢����p_v_program_update ����--------------------------------------------------------------------

alter table T_V_HOTCONTENT_LOCATION add loc_name varchar2(100);
alter table T_V_HOTCONTENT_LOCATION add loc_desc varchar2(100);
alter table T_V_HOTCONTENT_LOCATION add loc_type varchar2(10);
alter table T_V_HOTCONTENT_LOCATION add choice_type varchar2(10);
alter table T_V_HOTCONTENT_LOCATION add updatetime date default sysdate;
-- Add comments to the columns 
comment on column T_V_HOTCONTENT_LOCATION.updatetime
  is '����ʱ��';
comment on column T_V_HOTCONTENT_LOCATION.loc_name
  is 'λ������';
comment on column T_V_HOTCONTENT_LOCATION.loc_desc
  is 'λ������';
  
delete from t_v_hotcontent_location;

insert into t_v_category
  (id, categoryid, parentcid, cname, cdesc, isshow, sortid, lupdate)
values
  (SEQ_T_V_CATEGORY_ID.NEXTVAL,
   '105',
   '-1',
   '�ȵ��������ݸ�����',
    '�ȵ��������ݸ�����',
   '1',
   '1',
   sysdate);

insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10169','�乾��Ƶ-�Ƽ�-����̬','�乾��Ƶ-�Ƽ�-����̬','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10170','�乾��Ƶ-�Ƽ�-��������','�乾��Ƶ-�Ƽ�-��������','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10172','�乾��Ƶ-�Ƽ�-��������','�乾��Ƶ-�Ƽ�-��������','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10173','�乾��Ƶ-�Ƽ�-����','�乾��Ƶ-�Ƽ�-����','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10174','�乾��Ƶ-�Ƽ�-�ƾ�����','�乾��Ƶ-�Ƽ�-�ƾ�����','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10175','�乾��Ƶ-�Ƽ�-��Ӱ','�乾��Ƶ-�Ƽ�-��Ӱ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10221','�乾��Ƶ-��Ա-��ͼ','�乾��Ƶ-��Ա-��ͼ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10222','�乾��Ƶ-��Ա-�׷���Ƭ','�乾��Ƶ-��Ա-�׷���Ƭ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10223','�乾��Ƶ-��Ա-����ר��','�乾��Ƶ-��Ա-����ר��','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10226','�乾��Ƶ-��Ա-ȫ���ʵ','�乾��Ƶ-��Ա-ȫ���ʵ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10163','�乾��Ƶ-ֱ��-���ʻؿ�','�乾��Ƶ-ֱ��-���ʻؿ�','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10168','�乾��Ƶ-�Ƽ�-���ְ���','�乾��Ƶ-�Ƽ�-���ְ���','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10161','�乾��Ƶ-ֱ��-��ͼ','�乾��Ƶ-ֱ��-��ͼ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10105','��Ա��Ʒ-����','��Ա��Ʒ-����','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10108','��Ա��Ʒ-ԭ����Ц','��Ա��Ʒ-ԭ����Ц','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10162','�乾��Ƶ-ֱ��-��������ֱ��','�乾��Ƶ-ֱ��-��������ֱ��','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10164','�乾��Ƶ-ֱ��-���ŵ���̨','�乾��Ƶ-ֱ��-���ŵ���̨','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10165','�乾��Ƶ-�Ƽ�-��ͼ','�乾��Ƶ-�Ƽ�-��ͼ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10166','�乾��Ƶ-�Ƽ�-�����ȵ�','�乾��Ƶ-�Ƽ�-�����ȵ�','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10167','�乾��Ƶ-�Ƽ�-�����ȵ�ͼƬλ','�乾��Ƶ-�Ƽ�-�����ȵ�ͼƬλ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10171','�乾��Ƶ-�Ƽ�-��¼Ƭ','�乾��Ƶ-�Ƽ�-��¼Ƭ','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10241','��Ա��Ʒ-IOS-��ͼ','��Ա��Ʒ-IOS-��ͼ','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10106','��Ա��Ʒ-����','��Ա��Ʒ-����','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10103','��Ա��Ʒ-��Ӱ','��Ա��Ʒ-��Ӱ','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10104','��Ա��Ʒ-���Ӿ�','��Ա��Ʒ-���Ӿ�','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10107','��Ա��Ʒ-����','��Ա��Ʒ-����','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10109','��Ա��Ʒ-��ʵ','��Ա��Ʒ-��ʵ','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10110','��Ա��Ʒ-����','��Ա��Ʒ-����','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10111','��Ա��Ʒ-����','��Ա��Ʒ-����','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10113','��Ա��Ʒ-����','��Ա��Ʒ-����','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10181','��Ա��Ʒ-��������','��Ա��Ʒ-��������','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10176','�乾��Ƶ-�Ƽ�-���Ӿ�','�乾��Ƶ-�Ƽ�-���Ӿ�','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10201','��Ա��Ʒ-V��ѡ','��Ա��Ʒ-V��ѡ','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10224','�乾��Ƶ-��Ա-�þ����','�乾��Ƶ-��Ա-�þ����','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10101','��Ա��Ʒ-��ͼ','��Ա��Ʒ-��ͼ','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10225','�乾��Ƶ-��Ա-��������','�乾��Ƶ-��Ա-��������','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10102','��Ա��Ʒ-��Ѷ','��Ա��Ʒ-��Ѷ','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10112','��Ա��Ʒ-����','��Ա��Ʒ-����','9','1');


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.129_SSMS','MM5.0.0.0.105_SSMS');
commit;