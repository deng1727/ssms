-- Add/modify columns 
alter table T_CB_CONTENT add ebookUrl VARCHAR2(4000);
-- Add comments to the columns 
comment on column T_CB_CONTENT.ebookUrl
  is '����������URL��֧�ֶ�������֮��������|����';


-- Add/modify columns 
alter table T_RB_REFERENCE_NEW add PROVINCE varchar2(256)  default ';000;';
alter table T_RB_REFERENCE_NEW add CITY varchar2(4000) default ';000;';
-- Add comments to the columns 
comment on column T_RB_REFERENCE_NEW.PROVINCE
  is '���е�ʡ��ID�Էֺ�;������000����ȫ��';
comment on column T_RB_REFERENCE_NEW.CITY
  is '���еĳ���ID�Էֺ�;����';


drop table V_CM_CONTENT_TB;

create table V_CM_CONTENT_TB as select * from PPMS_V_CM_CONTENT_TB where 1=2;

drop table V_CM_CONTENT_TB_tra;

create table V_CM_CONTENT_TB_tra as select * from PPMS_V_CM_CONTENT_TB where 1=2;



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

insert into T_A_CM_DEVICE_RESOURCE(PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,VERSIONNAME,PERMISSION,ISCDN) 
select PID,DEVICE_ID,DEVICE_NAME,CONTENTID,CONTENTNAME,RESOURCEID,ID,ABSOLUTEPATH,URL,PROGRAMSIZE,CREATEDATE,PROSUBMITDATE,MATCH,VERSION,VERSIONNAME,PERMISSION,ISCDN from (select r.*,row_number() over(partition by r.contentid, r.device_id order by prosubmitdate desc,Isnumber(version) desc,pid desc) rn from V_DC_CM_DEVICE_RESOURCE r where r.pid is not null and contentid = v_contentid) where rn = 1;


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


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.085_SSMS','MM2.0.0.0.089_SSMS');

commit;