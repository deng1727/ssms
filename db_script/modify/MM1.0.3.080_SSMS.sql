
-- Add/modify columns 
alter table T_GAME_BASE add ProvinceCtrol VARCHAR2(500) null;
-- Add comments to the columns 
comment on column T_GAME_BASE.ProvinceCtrol
  is '将游戏包暂停的省份编码，以{}作为边界符，使用'',''分隔开。各省份对应编码见附录一。';


alter table T_R_GCONTENT modify PVCID VARCHAR2(4000);
alter table T_R_GCONTENT modify CITYID VARCHAR2(4000);

-- Add/modify columns 
alter table T_GAME_ATTR modify DOWNLOADCHANGE NUMBER(5,2);
alter table T_GAME_ATTR modify DAYACTIVITYUSER NUMBER(5,2);

create table t_r_category_dk_0830 as select * from t_r_category;
update t_r_category c  set c.sortid=(select s.sortid from portalmo.t_Category_Taxis s where s.id=c.id) where c.id in (select id from portalmo.t_Category_Taxis);

  insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.075_SSMS','MM1.0.3.080_SSMS');
commit;


