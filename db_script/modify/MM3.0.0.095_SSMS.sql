-- Create view


--创建v_vo_video_program_full视图------开始---------
create or replace view v_vo_video_program_full as
select a.videoid,a.programid,a.programtype,b.coderateid from 
(select p.programid,p.videoid,p.programtype from t_vo_program p
union all select m.programid,m.videoid,m.programtype from t_vo_program_mid m ) a,
(select v.videoid,v.coderateid from  t_vo_video v 
union all select d.videoid,d.coderateid from  t_vo_video_mid d ) b
where a.videoid = b.videoid;

--创建v_vo_video_program_full视图------结束---------


--修改v_vo_video_delete视图------开始---------
create or replace view v_vo_video_delete as
select distinct f.videoid
  from v_vo_video_program_full f
 where not exists
 (select distinct a.videoid
          from v_vo_video_program_full a
         where a.videoid = f.videoid
           and a.coderateid in
               ('6', '11', '13', '15', '33', '35', '37', '40', '41','39','52','54','55','56')) and f.programtype = '2'
union all 
select distinct f.videoid
  from v_vo_video_program_full f
 where not exists
 (select distinct a.videoid
          from v_vo_video_program_full a
         where a.videoid = f.videoid
           and a.coderateid in
               ('6', '11', '13', '15', '33', '35', '37', '40', '41','39','48','49','50','51')) and f.programtype = '1';

--修改v_vo_video_delete视图------结束---------


-- 第三方签名信息展示安卓适配关系表新增四个字段
alter table T_A_CM_DEVICE_RESOURCE add caflag VARCHAR2(1) default 0;
alter table T_A_CM_DEVICE_RESOURCE add cadev VARCHAR2(50) default 0;
alter table T_A_CM_DEVICE_RESOURCE add caorg VARCHAR2(50) default 0;
alter table T_A_CM_DEVICE_RESOURCE add cavalidatedate VARCHAR2(50) default 0;
-- Add comments to the columns 
comment on column T_A_CM_DEVICE_RESOURCE.caflag
  is '验签标识位：
1——验签
0——无（默认为0）
';
comment on column T_A_CM_DEVICE_RESOURCE.cadev
  is '进行验签的开发者名称
如果caFlag=1,该字段必填
';
comment on column T_A_CM_DEVICE_RESOURCE.caorg
  is '进行验签的认证机构
如果caFlag=1,该字段必填
';
comment on column T_A_CM_DEVICE_RESOURCE.cavalidatedate
  is '进行验签的认证有效期
如果caFlag=1,该字段必填';

alter table v_cm_device_resource add caflag VARCHAR2(1) default 0;
alter table v_cm_device_resource add cadev VARCHAR2(50) default 0;
alter table v_cm_device_resource add caorg VARCHAR2(50) default 0;
alter table v_cm_device_resource add cavalidatedate VARCHAR2(50) default 0;
-- Add comments to the columns 
comment on column v_cm_device_resource.caflag
  is '验签标识位：
1——验签
0——无（默认为0）
';
comment on column v_cm_device_resource.cadev
  is '进行验签的开发者名称
如果caFlag=1,该字段必填
';
comment on column v_cm_device_resource.caorg
  is '进行验签的认证机构
如果caFlag=1,该字段必填
';
comment on column v_cm_device_resource.cavalidatedate
  is '进行验签的认证有效期
如果caFlag=1,该字段必填';

alter table v_cm_device_resource_mid add caflag VARCHAR2(1) default 0;
alter table v_cm_device_resource_mid add cadev VARCHAR2(50) default 0;
alter table v_cm_device_resource_mid add caorg VARCHAR2(50) default 0;
alter table v_cm_device_resource_mid add cavalidatedate VARCHAR2(50) default 0;
-- Add comments to the columns 
comment on column v_cm_device_resource_mid.caflag
  is '验签标识位：
1——验签
0——无（默认为0）
';
comment on column v_cm_device_resource_mid.cadev
  is '进行验签的开发者名称
如果caFlag=1,该字段必填
';
comment on column v_cm_device_resource_mid.caorg
  is '进行验签的认证机构
如果caFlag=1,该字段必填
';
comment on column v_cm_device_resource_mid.cavalidatedate
  is '进行验签的认证有效期
如果caFlag=1,该字段必填';



commit;