   --重点机型和应用的笛卡尔积
   create or replace view v_Important_Match as
    select a.brand_name,
       a.device_id,
       a.device_name,
       a.os_name,
       b.ap_id,
       b.ap_name,
       b.content_id,
       b.content_name
  from t_pivot_device a, T_PIVOT_CONTENT b
 where a.os_name in (select distinct(o.osname) as osname
  from v_cm_device_resource t, t_device d, t_device_os o
 where t.contentid = b.content_id
 and t.device_id=d.device_id
and d.os_id=o.os_id  );
    
   --重点机型和应用未适配表 
        create table t_Important_NOMatch
    as select *
      from v_Important_Match ;