--������������
insert into t_r_base(id,parentid,path,type) values('1018','701','{100}.{701}.{1018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1018','����Ƶ��','����Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--�����������ܴ�����ʾ��Ϣ���ɲ��ع���ֻ��Ҫ��������������¼����
insert into t_resource (resourcekey,resourcevalue) values('RESOURCE_CATE_BO_CHECK_003','�޷���Ӹ����ܲ������Ĺ����ŵ�,��������Ӹ������ϸù����ŵ�');
insert into t_resource (resourcekey,resourcevalue) values('RESOURCE_CATE_BO_CHECK_004','�޷�ɾ���ӻ��ܰ����Ĺ����ŵ�,������ɾ���ӻ����ϸù����ŵ�');


----���Ӷ���֧��ƽ̨��

-- Create table
create table T_COMIC_PLATFORM
(
  PLATFORMID VARCHAR2(20),
  PLATFORM   VARCHAR2(20)
);
comment on column T_COMIC_PLATFORM.PLATFORMID  is '֧��ƽ̨ID,ȡֵ�����100��101��102��200��300��400';
comment on column T_COMIC_PLATFORM.PLATFORM    is '֧��ƽ̨����ȡֵ���S602nd, S603rd, S605th , WM , Kjava ,OMS';


-- �ӳ�t_r_gcontent ���е�singer�ֶ�
alter table T_R_GCONTENT modify singer VARCHAR2(200);

--��Ҫ�������Ż���wwwpas��mopas��pcpas����t_r_gcontent�ﻯ��ͼ���´�����

---wwwpas��pcpas ��ִ�ж��ﻯ��ͼ���±����ˢ��
alter materialized view t_r_gcontent compile;
exec dbms_mview.refresh(list=>'t_r_gcontent');

--mopas��ִ�����½ű�
drop materialized view t_r_gcontent;
create materialized view t_r_gcontent as select * from s_r_gcontent;
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);
alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;



---------------end-----------------
--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.040_SSMS','MM1.0.0.042_SSMS');


commit;