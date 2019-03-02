-- Create table
drop table T_VO_BLACK;
create table T_VO_BLACK
(
��id number(4) not null primary key,
  programid VARCHAR2(60),
  nodeid    VARCHAR2(21),
  lupdate   DATE default sysdate
);
-- Add comments to the table 
comment on table T_VO_BLACK
  is '��Ƶ��������';
-- Add comments to the columns 
comment on column T_VO_BLACK.programid
  is '��Ŀ��ʾ';
comment on column T_VO_BLACK.nodeid
  is '��Ŀ��ʾ';
comment on column T_VO_BLACK.lupdate
  is '����ʱ��';
  
  
 drop sequence seq_T_VO_BLACK;
  create sequence seq_T_VO_BLACK minvalue 1 maxvalue 99999999
               increment by 1
               start with 1;   /*����Ϊ1*/

 create or replace trigger T_VO_BLACK_tri
          before insert on T_VO_BLACK     /*���������������T_VO_BLACKִ�в������ʱ�����˴�����*/
          for each row                       /*��ÿһ�ж�����Ƿ񴥷�*/
          begin                                  /*��������ʼ*/
                 select seq_T_VO_BLACK.nextval into :new.id from dual;   /*�������������ݣ���������ִ�еĶ������ڴ���ȡ������seq_T_VO_BLACK����һ��ֵ���뵽��T_VO_BLACK�е�id�ֶ���*/
          end;