
-- Add/modify columns 
alter table T_CB_CHAPTER add published varchar2(14);
-- Add comments to the columns 
comment on column T_CB_CHAPTER.published
  is '����ʱ���ʽ��YYYYMMDDHHMMSS';


-- Add/modify columns 
alter table T_CB_CONTENT add WeekNum number(12);
alter table T_CB_CONTENT add MonthNum number(12);
alter table T_CB_CONTENT add WeekFlowersNum number(12);
alter table T_CB_CONTENT add MonthFlowersNum number(12);
-- Add comments to the columns 
comment on column T_CB_CONTENT.WeekNum
  is '����ܴ���������ͳ�ƣ�';
comment on column T_CB_CONTENT.MonthNum
  is '����´���������ͳ�ƣ�';
comment on column T_CB_CONTENT.WeekFlowersNum
  is '�ʻ��ܴ���������ͳ�ƣ�';
comment on column T_CB_CONTENT.MonthFlowersNum
  is '�ʻ��´���������ͳ�ƣ�';


alter table T_GAMESTOP add constraint T_GAMESTOP_key primary key (SERVICECODE, MMPROVINCEID);

-- ��Ҫ��portalmo����û��˺�ִ��������Ȩ�ű���--����ϵͳ
grant execute on SYNC_VIDEO_CONTENT_ALL to &ssms;


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.099_SSMS','MM2.0.0.0.105_SSMS');

commit;