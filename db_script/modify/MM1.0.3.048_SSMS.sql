

------添加字段
-- Add/modify columns 
alter table T_R_GCONTENT add MODAYORDERTIMES NUMBER(15);
-- Add comments to the columns 
comment on column T_R_GCONTENT.MODAYORDERTIMES
  is '终端上日订购次数';

update t_caterule_cond t
   set osql = 'modayordertimes desc nulls last'
 where t.ruleid in ('5','136','85','90','95','122','126','130','205','209','205')
   and t.condtype = 10;

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.045_SSMS','MM1.0.3.048_SSMS');
commit;