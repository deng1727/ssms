--�ڻ��ܹ���ϵͳ���ݿ��û�&SSMS����Ȩ�������Ż�
grant select,insert,delete,update on DBVERSION to &portalmo;--�ն��Ż�
grant select on SEQ_DB_VERSION to &portalmo;--�ն��Ż�
grant select on V_THIRD_SERVICE to &portalmo;--�ն��Ż�

grant select,insert,delete,update on DBVERSION to &portalwww;--www�Ż�
grant select on SEQ_DB_VERSION to &portalwww;--www�Ż�

grant select,insert,delete,update on DBVERSION to &portalpc;--pc�Ż�
grant select on SEQ_DB_VERSION to &portalpc;--pc�Ż�

--���Ż����ݿ��û��´���ͬ���
create or replace synonym DBVERSION for &SSMS .DBVERSION;
create or replace synonym SEQ_DB_VERSION for &SSMS .SEQ_DB_VERSION;
create or replace synonym V_THIRD_SERVICE for &SSMS .V_THIRD_SERVICE; 

------zcom ���ݽ������ssms��ppms��ͬһ���ݿ�ʵ��������PPMS���ݿ��û���ִ��
--------ͬһ��ʵ�������
-----��PPMS���ݿ��û�����Ȩ������
grant select on v_cm_content_zcom to &ssms;--����ϵͳ
grant select on v_cm_device_resource_zcom to &ssms;--����ϵͳ
---��SSMS�û��´���ͬ���
create synonym ppms_v_cm_content_zcom  for &mm_ppms.v_cm_content_zcom;
create synonym ppms_v_cm_device_resource_zcom  for &mm_ppms.v_cm_device_resource_zcom;
--------ͬһ��ʵ�����end-------


--------ͬһ��ʵ�������
-----��SSMS���ݿ��û�����Ȩ���Ż�PAS
grant select on T_GAME_BASE to &MOPAS;--�ն��Ż�PAS
---���ն�PAS�û��´���ͬ���
create synonym T_GAME_BASE  for &ssms.T_GAME_BASE;
--------ͬһ��ʵ�����end-------

-------�������ֶ�������---
------��Ȩ���ն��Ż�ʹ��---
grant select on T_MB_MUSIC to &portalmo;--�ն��Ż�
grant select on T_MB_CATEGORY to &portalmo;--�ն��Ż�
grant select on T_MB_REFERENCE to &portalmo;--�ն��Ż�
