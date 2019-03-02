

-----����ҵ�����
alter table V_CM_CONTENT_TB add servicecode VARCHAR2(10);
alter table V_CM_CONTENT_TB_TRA add servicecode VARCHAR2(10);

-- Create table ��ʷ�Ķ��Ƽ��ӿ�
create table t_rb_like_his_read
(
  RECORD_DAY varchar2(8) not null,
  MSISDN     varchar2(11) not null,
  BOOKID     varchar2(19) not null,
  RESON      varchar2(256) not null,
  sortId     varchar2(2) not null,
  createTime date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_rb_like_his_read.RECORD_DAY
  is '��¼ʱ��';
comment on column t_rb_like_his_read.MSISDN
  is '�û�����';
comment on column t_rb_like_his_read.BOOKID
  is '�Ƽ����߶�Ӧͼ��';
comment on column t_rb_like_his_read.RESON
  is '��ͼ���Ƽ�����';
comment on column t_rb_like_his_read.sortId
  is '�Ƽ����';
comment on column t_rb_like_his_read.createTime
  is '����ʱ��';


-- Create/Recreate indexes 
create index pk_his_read_id on t_rb_like_his_read (msisdn);

-- Create table ����ϲ�������Ƽ��ӿ�
create table t_rb_like_author
(
  RECORD_DAY varchar2(8) not null,
  MSISDN     varchar2(11) not null,
  AUTHORID   varchar2(64) not null,
  BOOKID     varchar2(19) not null,
  RECTYPE     varchar2(3) not null,
  sortId     varchar2(2) not null,
  createTime date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_rb_like_author.RECORD_DAY
  is '��¼ʱ��';
comment on column t_rb_like_author.MSISDN
  is '�û�����';
comment on column t_rb_like_author.AUTHORID
  is '�Ƽ����߱��';
comment on column t_rb_like_author.BOOKID
  is '�Ƽ����߶�Ӧͼ��';
comment on column t_rb_like_author.RECTYPE
  is '1��ר������    2����������    3����������';
comment on column t_rb_like_author.sortId
  is '�Ƽ����';
comment on column t_rb_like_author.createTime
  is '����ʱ��';


-- Create/Recreate indexes 
create index pk_LIKE_AUTHOR_id on T_RB_LIKE_AUTHOR (msisdn);

-- Create table ͼ�鼶�Ķ������Ƽ��ӿ�
create table t_rb_like_read_Perc
(
  RECORD_DAY varchar2(8) not null,
  SOURCEBOOKID varchar2(19) not null,
  BOOKID     varchar2(19) not null,
  rate       varchar2(10) not null,
  sortId     varchar2(2) not null,
  createTime date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_rb_like_read_Perc.RECORD_DAY
  is '��¼ʱ��';
comment on column t_rb_like_read_Perc.SOURCEBOOKID
  is '�û�����';
comment on column t_rb_like_read_Perc.BOOKID
  is '�Ƽ����߶�Ӧͼ��';
comment on column t_rb_like_read_Perc.rate
  is 'ͼ�������ٷֱ�';
comment on column t_rb_like_read_Perc.sortId
  is '�Ƽ����';
comment on column t_rb_like_read_Perc.createTime
  is '����ʱ��';

-- Create/Recreate indexes 
create index pk_read_Perc_id on t_rb_like_read_Perc (SOURCEBOOKID);

-- Create table ͼ�鼶���������Ƽ��ӿ�
create table t_rb_like_order_Perc
(
  RECORD_DAY varchar2(8) not null,
  SOURCEBOOKID varchar2(19) not null,
  BOOKID     varchar2(19) not null,
  rate       varchar2(10) not null,
  sortId     varchar2(2) not null,
  createTime date default sysdate not null
)
;
-- Add comments to the columns 
comment on column t_rb_like_order_Perc.RECORD_DAY
  is '��¼ʱ��';
comment on column t_rb_like_order_Perc.SOURCEBOOKID
  is '�û�����';
comment on column t_rb_like_order_Perc.BOOKID
  is '�Ƽ����߶�Ӧͼ��';
comment on column t_rb_like_order_Perc.rate
  is 'ͼ�������ٷֱ�';
comment on column t_rb_like_order_Perc.sortId
  is '�Ƽ����';
comment on column t_rb_like_order_Perc.createTime
  is '����ʱ��';

-- Create/Recreate indexes 
create index pk_order_Perc_id on t_rb_like_order_Perc (SOURCEBOOKID);

-- Create the synonym 
create or replace synonym PPMS_V_OM_dev_college
  for v_om_developer_college@DL_PPMS_DEVICE;


create table t_om_dev_college as select * from PPMS_V_OM_dev_college  where 1=2;

create table t_om_dev_college_tra as select * from PPMS_V_OM_dev_college  where 1=2;


insert into T_R_EXPORTSQL_POR (ID, NAME, FROM_NAME, TO_NAME, TO_NAME_TRA, TO_NAME_BAK, TO_WHERE, TO_COLUMNS)
values (6, '��ҵ������У����', 'PPMS_V_OM_dev_college', 't_om_dev_college', 't_om_dev_college_tra', 't_om_dev_college_bak', null, null);

insert into T_R_EXPORTSQL (ID, EXPORTSQL, EXPORTNAME, EXPORTTYPE, EXPORTPAGENUM, EXPORTTYPEOTHER, LASTTIME, EXPORTLINE, FILENAME, FILEPATH, ENCODER, EXPORTBYAUTO, EXPORTBYUSER, EXECTIME, GROUPID)
values (28, 'select a.developerid,a.collegeid,a.college,a.provinceid,a.provname from t_om_dev_college a', '��ҵ������У����', '1', 100000, ',', null, 5, 'i_VDPC_%YYYYMMDD%_%NNN%', '/opt/aspire/product/chroot_sftp_ssms_www/sftp_ssms_www', 'UTF-8', '2', '1', null, 1);



-- Add/modify columns 
alter table T_A_CM_DEVICE_RESOURCE add picture1 VARCHAR2(256);
alter table T_A_CM_DEVICE_RESOURCE add picture2 VARCHAR2(256);
alter table T_A_CM_DEVICE_RESOURCE add picture3 VARCHAR2(256);
alter table T_A_CM_DEVICE_RESOURCE add picture4 VARCHAR2(256);
-- Add comments to the columns 
comment on column T_A_CM_DEVICE_RESOURCE.picture1
  is 'picture1';
comment on column T_A_CM_DEVICE_RESOURCE.picture2
  is 'picture2';
comment on column T_A_CM_DEVICE_RESOURCE.picture3
  is 'picture3';
comment on column T_A_CM_DEVICE_RESOURCE.picture4
  is 'picture4';




--------------�洢����
create or replace function f_syncAndroid_init(v_id in varchar2,v_categoryid in varchar2)
  return number as
  cursor my_cursor is  select r.goodsid,r.categoryid,c.id,r.refnodeid,r.sortid
,replace(replace(replace(r.loaddate,'-',''),':',''),' ','') as loaddate
 from t_r_reference r ,t_r_category c where r.categoryid=c.categoryid
 and r.categoryid=v_categoryid;

   my_row my_cursor%rowtype;
   v_status number;
   v_message varchar2(1000);
   v_message0 varchar2(1000);
   v_message1 varchar2(1000);

   v_contentid varchar2(50);
   n_resource number;
   n_ref number;
   isFirst boolean;

   v_status_init number(1);


begin

v_status_init :=-1;

  v_status := pg_log_manage.f_startlog('p_syncAndroid_init',
                                        '��Ʒ���Ż�����Ӫ���ܴ���һ��');
   n_ref:=0;--��������Ʒ�ɹ�¼�룡
   isFirst:=true;

  --���𷽽�����ˮ�ţ��ڷ���Ψһ��ʶһ�����׵���ˮ�ţ�ϵͳ��Ψһ
--�������:YYYYMMDDMMMMMMM
--  ���У�YYYYΪ�꣬MMΪ�£�DDΪ��
--        MMMMMMMΪ0000001��9999999�����ִ�����С����ѭ��ʹ��
--���������������ContextUtil����ʵ�ֵģ�����洢���̾ʹ�1000001��9999999

   --transactionid_num:=1000001;
   --select to_char(sysdate,'yyyymmdd') into transactionid_today from dual;


            v_message0 := v_id||':1';
            insert into t_a_messages (id,status,type,transactionid,message) values(SEQ_T_A_MESSAGES.NEXTVAL,v_status_init,'CatogoryModifyReq','',v_message0);
            commit;


 for my_row in my_cursor loop

          v_contentid:=substr(my_row.goodsid,-12);

         select count(1) into n_resource from V_DC_CM_DEVICE_RESOURCE where contentid=v_contentid;
         if n_resource>0 then
          begin

          --dbms_output.put_line(v_contentid||'---'||n_resource);
          delete from T_A_CM_DEVICE_RESOURCE where contentid=v_contentid;
       --   insert into T_A_CM_DEVICE_RESOURCE(PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,PERMISSION,ISCDN)
       --   select PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,PERMISSION,ISCDN from V_DC_CM_DEVICE_RESOURCE where contentid=v_contentid;

insert into T_A_CM_DEVICE_RESOURCE(PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,VERSIONNAME,PERMISSION,ISCDN,picture1,picture2,picture3,picture4)
select PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,VERSIONNAME,PERMISSION,ISCDN,picture1,picture2,picture3,picture4 from (select r.*,row_number() over(partition by r.contentid, r.device_id order by prosubmitdate desc,Isnumber(version) desc,pid desc) rn from V_DC_CM_DEVICE_RESOURCE r where r.pid is not null and contentid = v_contentid) where rn = 1;


          v_message:=my_row.goodsid||':'||my_row.categoryid||':'||my_row.id||':'||my_row.refnodeid||':'||my_row.sortid||':'||my_row.loaddate||':0';
          v_message1:=v_contentid||':1';




          insert into t_a_messages (id,status,type,transactionid,message) values(SEQ_T_A_MESSAGES.NEXTVAL,v_status_init,'ContentModifyReq','',v_message1);
          insert into t_a_messages (id,status,type,transactionid,message) values(SEQ_T_A_MESSAGES.NEXTVAL,v_status_init,'RefModifyReq','',v_message);
          n_ref:=n_ref+1;
          commit;
          exception
            when others then
             dbms_output.put_line('find error:'||v_contentid||'---'||n_resource);
             rollback;
          end;
         end if;
  end loop;
   v_status := pg_log_manage.f_successlog;
   return n_ref;
exception
  when others then
    rollback;
     --���ʧ�ܣ���ִ�����д����־
    v_status := pg_log_manage.f_errorlog;
    return -1;
end;

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.095_SSMS','MM2.0.0.0.099_SSMS');

commit;