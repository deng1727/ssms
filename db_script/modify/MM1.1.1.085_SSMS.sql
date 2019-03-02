
-- Add/modify columns 
alter table T_VO_CATEGORY add nodeNum number(5) default 0;
alter table T_VO_CATEGORY add refNum number(5) default 0;
-- Add comments to the columns 
comment on column T_VO_CATEGORY.nodeNum
  is '当前货架下的子货架数';
comment on column T_VO_CATEGORY.refNum
  is '当前货架下的商品数';

-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_AUTHOR_NEW
  add constraint pk_author_key_id primary key (AUTHORID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_BOOKCONTENT_NEW
  add constraint pk_bookcontent_id primary key (BOOKBAGID, BOOKID);

-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RB_TYPE_NEW
  add constraint pk_rb_type_id primary key (TYPEID);


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.079_SSMS','MM1.1.1.085_SSMS');


commit;