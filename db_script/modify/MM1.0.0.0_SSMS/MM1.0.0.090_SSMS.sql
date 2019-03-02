
---创建同步 模糊适配信息给门户的 视图
create or replace view v_match_device_resource 
(device_id, device_name, contentid, contentname, match)
as
select t.device_id,t.device_name,t.contentid,t.contentname,decode(match,1,1,2,2,3,2) match from v_cm_device_resource t;



--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.088_SSMS','MM1.0.0.090_SSMS');
commit;