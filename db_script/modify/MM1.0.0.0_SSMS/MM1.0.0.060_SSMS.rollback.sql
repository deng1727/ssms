
-- drop view
drop view vr_a8_goods;

update t_resource set resourcevalue='��������ֻ�ܰ������֡���ĸ��_ ������' ,remard='ֻ�ܺ��֡�Ӣ����ĸ��_ ������' where resourcekey='RESOURCE_CATE_FIELD_CHECK_001';



---------------end-----------------
--�ع��汾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.060_SSMS' and LASTDBVERSION = 'MM1.0.0.053_SSMS';

commit;