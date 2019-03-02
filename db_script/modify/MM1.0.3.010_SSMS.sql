-- Create table 内容同步，自动更新，人工干预短信通知表
------ 根据具体情况采用以下方式
----- 以PPMS用户身份赋予货架插入权限
grant insert on sms_notify to &ssms;
grant select on SEQ_SMS_NOTIFY_ID to &ssms;
--  同实例采用以下方式 Create the synonym 
create or replace synonym sms_notify for &MM_PPMS.sms_notify;
create or replace synonym SEQ_SMS_NOTIFY_ID for &MM_PPMS.SEQ_SMS_NOTIFY_ID;


-----不同实例，需要通过DBLINK方式创建同义词-----------
create or replace synonym sms_notify for sms_notify@DL_PPMS_DEVICE; 
create or replace synonym SEQ_SMS_NOTIFY_ID for SEQ_SMS_NOTIFY_ID@DL_PPMS_DEVICE; 



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.007_SSMS','MM1.0.3.010_SSMS');
commit;