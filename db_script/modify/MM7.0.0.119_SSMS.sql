alter table  t_a_cm_device_resource add (apprealname varchar2(100));

alter table t_pivot_app_monitor_result add (id varchar2(32));

alter table t_pivot_app_monitor add (id varchar2(32));

comment on column t_a_cm_device_resource.apprealname is '����Ӧ����';
  
comment on column t_pivot_app_monitor_result.id is '�ۺ�Ӧ��id';
  
comment on column t_pivot_app_monitor.id is '�ۺ�Ӧ��id';
commit;