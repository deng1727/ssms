----�ع���������
delete from t_r_base b where b.id in('1018');
delete from t_r_category b where b.id in ('1018');

----�ع�����֧��ƽ̨��

-- drop table
drop table T_COMIC_PLATFORM;

-- �ع�t_r_gcontent ���е�singer�ֶ�
alter table T_R_GCONTENT modify singer VARCHAR2(50);

--��Ҫ�������Ż���wwwpas��mopas��pcpas����t_r_gcontent�ﻯ��ͼ���´�����

---wwwpas��pcpas ��ִ�ж��ﻯ��ͼ���±����ˢ��
alter materialized view t_r_gcontent compile;
exec dbms_mview.refresh(list=>'t_r_gcontent');

--mopas��ִ�����½ű�
drop materialized view t_r_gcontent;
create materialized view t_r_gcontent as select * from s_r_gcontent;
alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;


--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);

---------------end-----------------
--�ع��汾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.042_SSMS' and LASTDBVERSION = 'MM1.0.0.040_SSMS';

commit;