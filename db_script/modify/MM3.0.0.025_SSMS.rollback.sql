 alter table t_rb_book_new drop column freechaptercount;
 
 
 
 
 delete DBVERSION where PATCHVERSION = 'MM3.0.0.025_SSMS' and LASTDBVERSION = 'MM3.0.0.016_SSMS';

commit;

