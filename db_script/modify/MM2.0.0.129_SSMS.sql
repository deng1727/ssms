-- Add/modify columns 
alter table t_a_cm_device_resource add clientid varchar2(100);
alter table t_a_cm_device_resource add pkgname varchar2(512);
alter table t_a_cm_device_resource add versiondesc varchar2(2000);

-- add comments to the columns 
comment on column t_a_cm_device_resource.clientid
  is '�������package name';
  comment on column t_a_cm_device_resource.pkgname
  is '������ļ�������';
comment on column t_a_cm_device_resource.versiondesc
  is '������汾����˵��';
 
 
 
 -----����CDNURL-----
 alter table t_a_cm_device_resource add cdnurl varchar2(1024);
 comment on column t_a_cm_device_resource.cdnurl
  is 'cdn���ص�ַ'; 
  
  
 alter table v_cm_device_resource add cdnurl varchar2(1024);
 comment on column v_cm_device_resource.cdnurl
  is 'cdn���ص�ַ'; 
  
 alter table v_cm_device_resource_mid add cdnurl varchar2(1024);
 comment on column v_cm_device_resource_mid.cdnurl
  is 'cdn���ص�ַ'; 
  
  -----���Ź���ƽ̨ר�����»���ͬ���ӿ�����ͼ���ֶ���Ϣ
  update t_r_exportsql t set t.exportsql='SELECT APPID,ACCOUNT,NICKNAME,ONEWORDDESC,PORTALURL,icon FROM CM_FEIXIN',t.exportline=6  where t.id=31;
  
  
  
  ----------------

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

insert into T_A_CM_DEVICE_RESOURCE(PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,VERSIONNAME,PERMISSION,ISCDN,picture1,picture2,picture3,picture4,clientid,pkgname,versiondesc,cdnurl)
select PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,VERSIONNAME,PERMISSION,ISCDN,picture1,picture2,picture3,picture4,clientid,pkgname,versiondesc,cdnurl from (select r.*,row_number() over(partition by r.contentid, r.device_id order by Isnumber(version) desc,prosubmitdate desc,pid desc) rn from V_DC_CM_DEVICE_RESOURCE r where r.pid is not null and contentid = v_contentid) where rn = 1;


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

--����ģ���ṹ��ʼ

create table T_DC_CHECKLOG
(
  taskid      NUMBER,
  categoryid  VARCHAR2(50) not null,
  checktime   DATE,
  lucenepath  VARCHAR2(200),
  ip          VARCHAR2(25),
  dbcount     NUMBER,
  lucenecount NUMBER
);
 comment on column T_DC_CHECKLOG.taskid
  is '����ID';
comment on column T_DC_CHECKLOG.categoryid
  is '����ID';
comment on column T_DC_CHECKLOG.checktime
  is 'ִ��ʱ��';
comment on column T_DC_CHECKLOG.lucenepath
  is '��ӦLucene����';
comment on column T_DC_CHECKLOG.ip
  is '��Ӧ�Ļ���IP+Port';
comment on column T_DC_CHECKLOG.dbcount
  is '���ݿ��м�¼��';
comment on column T_DC_CHECKLOG.lucenecount
  is 'Lucene�м�¼��';
 
 

-- Create table
create table t_dc_CheckDetail
(
  taskid     number,
  categoryid Varchar2(50) not null,
  checktime  date,
  lucenepath Varchar2(200),
  ip         Varchar2(25),
  pkid       Varchar2(200),
  statusinfo Number(2)
)
;
-- Add comments to the columns 
comment on column t_dc_CheckDetail.taskid
  is '����ID';
comment on column t_dc_CheckDetail.categoryid
  is '����ID';
comment on column t_dc_CheckDetail.checktime
  is 'ִ��ʱ��';
comment on column t_dc_CheckDetail.lucenepath
  is '��ӦLucene����';
comment on column t_dc_CheckDetail.ip
  is '��Ӧ�Ļ���IP+Port';
comment on column t_dc_CheckDetail.pkid
  is '��LUCENE����/���ݿ��PKֵ';
comment on column t_dc_CheckDetail.statusinfo
  is '1��LUCENE�д��ڣ����ݿ��в�����
2��LUCENE�в����ڣ����ݿ��д���
';

--����ģ���ṹ����

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.125_SSMS','MM2.0.0.0.129_SSMS');

commit;

