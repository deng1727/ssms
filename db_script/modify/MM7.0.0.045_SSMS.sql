create table t_r_blacklist
(
  contentid    VARCHAR2(12),
  isblack  VARCHAR2(1),
  createTime Date
);
comment on column t_r_blacklist.contentid
  is 'Ӧ��id';
  comment on column t_r_blacklist.isblack
  is '1Ϊ����״̬,0Ϊȡ������״̬';
  comment on column t_r_blacklist.createTime
  is '��������';

  comment on column t_r_gcontent.RiskTag
  is '���ձ�ǩ';

  comment on column t_r_gcontent.AppType
  is 'Ӧ������,5ΪMMTVӦ��';

  comment on column t_r_gcontent.CtrlDev
  is '�����豸';


   insert into t_r_exportsql
values
  ('105',
   'select t.contentid from t_r_gcontent t where t.risktag is not null and t.contentid not in (select contentid from t_r_blacklist r where r.isblack=1) ',
   '���ձ�ǩӦ�õ���',
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
  is '���ձ�ǩid,1001Ϊ�й��SDK,��Ƕ���;1002, �����ݰ�Ȩ;1003,����Ϣ���紫��������Ŀ���֤;1004,���������Ȩ';

comment on column t_r_risktag_list.risktag
  is '���ձ�ǩ:1Ϊ�й��SDK,��Ƕ���;2, �����ݰ�Ȩ;3,����Ϣ���紫��������Ŀ���֤;4,���������Ȩ';
  
comment on column t_r_risktag_list.stats
  is '״̬:0.����,1.����';
  
  comment on column t_r_risktag_list.handlTime
  is '�����ʱ��';
  
insert into t_r_risktag_list values ('1001','1','0',sysdate)
insert into t_r_risktag_list values ('1002','2','0',sysdate)
insert into t_r_risktag_list values ('1003','3','0',sysdate)
insert into t_r_risktag_list values ('1004','4','0',sysdate)
commit;
