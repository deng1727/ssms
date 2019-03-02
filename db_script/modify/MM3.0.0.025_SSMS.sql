 alter table t_rb_book_new add freechaptercount number(12)  default 0 not null;
 comment on column t_rb_book_new.freechaptercount
  is 'Ãâ·ÑÕÂ½ÚÊý'; 
  
  
  
  insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'3.0.0.0','MM3.0.0.0.016_SSMS','MM3.0.0.025_SSMS');

commit;

