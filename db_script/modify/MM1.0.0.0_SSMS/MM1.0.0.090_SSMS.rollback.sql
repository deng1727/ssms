
---ɾ��ͬ�� ģ��������Ϣ���Ż��� v_match_device_resource ��ͼ
drop view v_match_device_resource;


--ɾ���汾----
delete DBVERSION where PATCHVERSION = 'MM1.0.0.090_SSMS' and LASTDBVERSION = 'MM1.0.0.088_SSMS';
commit;