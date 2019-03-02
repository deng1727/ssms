--alter table t_vo_program add  ftplogopath varchar2(512);

--update t_vo_category t set  t.baseid='m101',t.basetype='1' where t.id='101';
--update t_vo_category t set  t.baseid='m202',t.basetype='2' where t.id='202';

--insert into t_vo_category values('303','0','m303','10','活动专题',0,1,'0','','活动专题_手工建立的');
--insert into t_vo_category values('404','0','m404','10','运营货架',0,1,'0','','运营货架_手工建立的');

--create sequence SEQ_CATEGORY_VIDEO_BASEID
--minvalue 1000
--maxvalue 999999999999999999999999999
--start with 1000
--increment by 1
--cache 20;

--以上SQL已经在2012-07-26号都在现网执行过来。


create table t_vo_video_tra as select * from  t_vo_video ;
create table t_vo_node_tra as select * from  t_vo_node ;
create table t_vo_program_tra as select * from  t_vo_program ;
create table t_vo_live_tra as select * from  t_vo_live ;
create table t_vo_product_tra as select * from  t_vo_product ;
create table t_vo_videodetail_tra as select * from  t_vo_videodetail ;
create table t_vo_reference_tra as select * from  t_vo_reference ;
create table t_vo_category_tra as select * from  t_vo_category ;
commit;

alter   table   t_vo_node_tra   modify  exporttime   date default sysdate ;
alter   table   t_vo_program_tra   modify   exporttime   date default sysdate ;
alter   table   t_vo_videodetail_tra   modify updatetime       date default sysdate ;
alter   table   t_vo_reference_tra   modify   sortid      number(6) default 0 ;
alter   table   t_vo_reference_tra   modify   exporttime  date default sysdate ;
alter   table   t_vo_category_tra   modify   sortid       number(6) default 0 ;
alter   table   t_vo_category_tra   modify   isshow       varchar2(2) default 1;

commit;

drop index idx_vo_live;
create index t_idx_vo_live on t_vo_live  (programid,starttime, endtime,nodeid);
create index t_idx_vo_product on t_vo_product(startdate,feetype, productid);
create index t_idx_vo_coderate on t_vo_coderate(coderatelevel,videoencode, netmilieu);
create index t_idx_vo_reference on t_vo_reference(programid);
create index t_idx_vo_program on t_vo_program(programid);
create index t_idx_vo_videodetail on t_vo_videodetail(programid);
drop index index_baseid;
create index t_idx_vo_category on t_vo_category(baseid);
create index t_idx_vo_node on t_vo_node(nodeid);
drop index pk_nodeid_1; 
create unique index t_idx_vo_nodeext on t_vo_nodeext (nodeid);
commit;

create unique index t_idx_vo_video1 on t_vo_video_tra (videoid, coderateid)
create index t_idx_vo_node1 on t_vo_node_tra(nodeid);
create index t_idx_vo_program1 on t_vo_program_tra(programid);
create index t_idx_vo_live1  on t_vo_live_tra(programid,starttime, endtime,nodeid);
create index t_idx_vo_product1  on t_vo_product_tra(startdate,feetype, productid);
create index t_idx_vo_videodetail1 on t_vo_videodetail_tra(programid);
create index t_idx_vo_reference1  on t_vo_reference_tra(programid);
create index t_idx_vo_category1 on t_vo_category_tra(baseid);
commit;

create sequence SEQ_VIDEO_SYNC_ID
minvalue 1
maxvalue 99999999999
start with 100
increment by 1
cache 20;
commit;

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.1.1.0','MM1.1.1.075_SSMS','MM1.1.1.079_SSMS');

commit;
