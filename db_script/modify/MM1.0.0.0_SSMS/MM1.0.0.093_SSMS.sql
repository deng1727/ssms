
-----�޸���T_R_CATEGORY�ֶκ���

comment on column T_R_CATEGORY.STATE is '�����Ƿ����Ż�չʾ��1��չʾ��0����չʾ';
update T_R_CATEGORY  set STATE ='1' where STATE='9';

--�޸��ṩ���������ͼ vr_category
 create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
  select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation
from t_r_category
where id not in('701','702');     



-- ����������������
alter table T_CATERULE add randomfactor number(3) default 0 not null;
comment on column T_CATERULE.randomfactor
  is '����ϼ����ӡ���Ʒ���ϼ�ǰ�Ƿ���Ҫ�漴����0�������100���漴��1~99 ���ͻ���С���';



--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.092_SSMS','MM1.0.0.093_SSMS');
commit;