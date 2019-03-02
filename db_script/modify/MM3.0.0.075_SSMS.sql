-- Create table
drop table T_VO_BLACK;
create table T_VO_BLACK
(
　id number(4) not null primary key,
  programid VARCHAR2(60),
  nodeid    VARCHAR2(21),
  lupdate   DATE default sysdate
);
-- Add comments to the table 
comment on table T_VO_BLACK
  is '视频黑名单表';
-- Add comments to the columns 
comment on column T_VO_BLACK.programid
  is '节目标示';
comment on column T_VO_BLACK.nodeid
  is '栏目标示';
comment on column T_VO_BLACK.lupdate
  is '更新时间';
  
  
 drop sequence seq_T_VO_BLACK;
  create sequence seq_T_VO_BLACK minvalue 1 maxvalue 99999999
               increment by 1
               start with 1;   /*步长为1*/

 create or replace trigger T_VO_BLACK_tri
          before insert on T_VO_BLACK     /*触发条件：当向表T_VO_BLACK执行插入操作时触发此触发器*/
          for each row                       /*对每一行都检测是否触发*/
          begin                                  /*触发器开始*/
                 select seq_T_VO_BLACK.nextval into :new.id from dual;   /*触发器主题内容，即触发后执行的动作，在此是取得序列seq_T_VO_BLACK的下一个值插入到表T_VO_BLACK中的id字段中*/
          end;