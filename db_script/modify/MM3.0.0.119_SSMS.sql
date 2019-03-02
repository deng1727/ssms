create or replace synonym v_r_content_pic 
  for v_datacenter_cm_content_logo @DL_MM_PPMS_NEW;
  
alter table T_A_PPMS_RECEIVE_change
add (imagetype varchar2(2) default '0' not null);
