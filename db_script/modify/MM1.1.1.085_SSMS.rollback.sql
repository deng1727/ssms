-- Drop columns 
alter table T_VO_CATEGORY drop column NODENUM;
alter table T_VO_CATEGORY drop column REFNUM;

-- Drop primary, unique and foreign key constraints 
alter table T_RB_AUTHOR_NEW
  drop constraint PK_AUTHOR_KEY_ID cascade;

-- Drop primary, unique and foreign key constraints 
alter table T_RB_BOOKCONTENT_NEW
  drop constraint PK_BOOKCONTENT_ID cascade;

-- Drop primary, unique and foreign key constraints 
alter table T_RB_TYPE_NEW
  drop constraint PK_RB_TYPE_ID cascade;


delete DBVERSION where PATCHVERSION = 'MM1.1.1.085_SSMS' and LASTDBVERSION = 'MM1.1.1.079_SSMS';
commit;