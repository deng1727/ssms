----�ع�����ʱ����������Ʒ�Ļ��ܱ�

-- drop table
drop table T_CATEGORY_EXPORT;


---------------end-----------------
--�ع��汾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.053_SSMS' and LASTDBVERSION = 'MM1.0.0.042_SSMS';

commit;