--ЙѕіэН¬ТеґК
drop synonym sms_notify ; 
drop synonym SEQ_SMS_NOTIFY_ID ; 
delete DBVERSION where PATCHVERSION = 'MM1.0.3.010_SSMS' and LASTDBVERSION = 'MM1.0.3.007_SSMS';
commit;