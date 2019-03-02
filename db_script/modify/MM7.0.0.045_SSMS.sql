create table t_r_blacklist
(
  contentid    VARCHAR2(12),
  isblack  VARCHAR2(1),
  createTime Date
);
comment on column t_r_blacklist.contentid
  is '应用id';
  comment on column t_r_blacklist.isblack
  is '1为屏蔽状态,0为取消屏蔽状态';
  comment on column t_r_blacklist.createTime
  is '创建日期';

  comment on column t_r_gcontent.RiskTag
  is '风险标签';

  comment on column t_r_gcontent.AppType
  is '应用类型,5为MMTV应用';

  comment on column t_r_gcontent.CtrlDev
  is '控制设备';


   insert into t_r_exportsql
values
  ('105',
   'select t.contentid from t_r_gcontent t where t.risktag is not null and t.contentid not in (select contentid from t_r_blacklist r where r.isblack=1) ',
   '风险标签应用导出',
   '2',
   '50000',
   '0x01',
   sysdate,
   '1',
   'risktag',
   '/opt/aspire/product/chroot_panguso/panguso/mo',
   'GB18030',
   '2',
   '1',
   '',
   '21'
   );

commit;




create table t_r_risktag_list
(
  id varchar2(5),
  risktag    VARCHAR2(2),
  stats  VARCHAR2(1),
  handlTime Date
);

comment on column t_r_risktag_list.id
  is '风险标签id,1001为有广告SDK,内嵌广告;1002, 无内容版权;1003,无信息网络传播视听节目许可证;1004,无气象局授权';

comment on column t_r_risktag_list.risktag
  is '风险标签:1为有广告SDK,内嵌广告;2, 无内容版权;3,无信息网络传播视听节目许可证;4,无气象局授权';
  
comment on column t_r_risktag_list.stats
  is '状态:0.正常,1.屏蔽';
  
  comment on column t_r_risktag_list.handlTime
  is '最后处理时间';
  
insert into t_r_risktag_list values ('1001','1','0',sysdate)
insert into t_r_risktag_list values ('1002','2','0',sysdate)
insert into t_r_risktag_list values ('1003','3','0',sysdate)
insert into t_r_risktag_list values ('1004','4','0',sysdate)
commit;
