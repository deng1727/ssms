
-- Create table
drop table T_CONTENT_COUNT;


----����
update t_caterule_cond set osql='createdate desc,dayOrderTimes desc,mobilePrice desc,name asc' where condtype ='10' and   ruleid in ('88','98','93','3');
----�Ƽ�
update t_caterule_cond set osql='dayOrderTimes desc,createdate desc,starLevel desc,mobilePrice desc,name asc' where condtype ='10' and   ruleid in ('86','91','96','4');

drop table t_category_name_mapping;
--��ԭ��ǰ�Ĺ���
update t_caterule t set t.ruletype=0 where t.ruleid between 84 and 98;
comment on column T_CATERULE.RULETYPE
  is '�������� 0��ˢ�»�������Ʒ��1����������Ʒ����˳��
��5���������ͼ����Ӫ�Ƽ�ͼ�顣';
--ɾ���汾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.088_SSMS' and LASTDBVERSION = 'MM1.0.0.080_SSMS';
commit;