
-- 为了创业大赛加大创业大赛作品编码
alter table T_R_GCONTENT modify PROGRAMID VARCHAR2(30);

-- Create table
create table t_r_circle
(
  categoryid varchar2(30),
  relation   varchar2(30),
  target     varchar2(30)
)
;
-- Add comments to the columns 
comment on column t_r_circle.categoryid
  is '货架ID';
comment on column t_r_circle.relation
  is '关联门户：A,WAP,W,WWW';
comment on column t_r_circle.target
  is '渠道商，001，飞信，002  139邮箱，003 139说客';



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.025_SSMS','MM1.0.3.030_SSMS');
commit;