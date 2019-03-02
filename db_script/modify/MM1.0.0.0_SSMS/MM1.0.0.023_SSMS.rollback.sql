delete from t_r_base b where b.id in ('1014','1015','1016','2016','1017','2017','2018','2019');
delete from t_r_category b where b.id in ('1014','1015','1016','2016','1017','2017','2018','2019');

drop table t_book_commend;
drop table T_BOOK_AUTHOR;
drop table t_bookcate_mapping;
drop table T_GAME_CATE_MAPPING;

 �ع��ű�
-- Add/modify columns 
alter table T_R_GCONTENT modify NAME VARCHAR2(100);
alter table T_R_GCONTENT modify PROVIDER VARCHAR2(60);
 --ɾ���汾��Ϣ
delete DBVERSION where PATCHVERSION = 'MM1.0.0.023_SSMS' and LASTDBVERSION = 'MM1.0.0.019_SSMS';


--��Ҫ�������Ż���wwwpas��mopas��pcpas����t_r_gcontent�ﻯ��ͼ���´�����
--��ֱ������������û�ִ�����½ű�
drop materialized view t_r_gcontent;

---mopas ��ִ�д����ﻯ��ͼ�ű�
create materialized view t_r_gcontent as select * from s_r_gcontent;
--pcpas��wwwpas��ִ������Ĵ����ﻯ��ͼ�ű�
create materialized view t_r_gcontent as select * from s_r_gcontent v where ascii(substr(v.id,1,1))>47 and ascii(substr(v.id,1,1))<58;--wwwpas���˲������Ѷ����
--���������������ﻯ��ͼ��Ӱ��
alter materialized view t_r_base compile;
alter materialized view v_gcontent compile;
alter materialized view v_advancecontent compile;

alter materialized view v_searchcontent compile;---mopas ����Ҫִ��

--t_r_gcontent
create unique index INDEX1_T_R_gcontent on t_r_gcontent (ID);

commit;