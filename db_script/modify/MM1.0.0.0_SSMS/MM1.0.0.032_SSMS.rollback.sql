--�Ƴ����ܹ�����Ա�
drop table T_Category_Rule;
drop synonym T_PPS_COMMENT_GRADE;

 --ɾ���汾��Ϣ
delete DBVERSION where PATCHVERSION = 'MM1.0.0.029_SSMS' and LASTDBVERSION = 'MM1.0.0.023_SSMS';

--���PORTALCOMMON��MM.SSMS����ͬһ���ݿ�ʵ������Ҫִ����һ�нű���
drop database link PORTALCOMMONTOSSMS;

commit;