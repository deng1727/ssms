/**
create or replace synonym V_DATACENTER_CM_CONTENT
  for V_DATACENTER_CM_CONTENT@DL_MM_PPMS_NEW;
  
create or replace synonym V_DC_CM_DEVICE_RESOURCE
  for V_DC_CM_DEVICE_RESOURCE@DL_MM_PPMS_NEW;

  
create or replace synonym V_R_CONTENT_PIC
  for V_DATACENTER_CM_CONTENT_LOGO@DL_MM_PPMS_NEW;
  
  
commit;
*/

ALTER TABLE t_content_push_adv DROP COLUMN uri;
ALTER TABLE t_content_push_adv DROP COLUMN push_pic;
ALTER TABLE t_content_push_adv DROP COLUMN type;


commit;

delete  t_r_exportsql where ID in(93,94,95,96,97,98);
delete T_R_EXPORTSQL_GROUP where groupid ='16';

commit;

create or replace view v_clms_content as
 select t.icpcode, t.contentid,t.name,t.introduction,t.catename,t.appcatename,v.mobileprice,t.marketdate,t.lupddate, t.chargetime,t.subtype,t.logo4 
 from t_r_gcontent t,v_service v 
 where t.icpservid=v.icpservid  and t.provider != 'B';

commit;