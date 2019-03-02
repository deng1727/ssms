
-- Create sequence 
create sequence SEQ_category_sort_id
minvalue 0
maxvalue 999
start with 1
increment by 1
cache 10
cycle;


-- Create sequence 
create sequence SEQ_category_r_sort_id
minvalue 0
maxvalue 999
start with 1
increment by 1
cache 10
cycle;


-- Create sequence 
create sequence SEQ_category_v_sort_id
minvalue 0
maxvalue 999
start with 1
increment by 1
cache 10
cycle;


-- Create sequence 
create sequence SEQ_category_m_sort_id
minvalue 0
maxvalue 999
start with 1
increment by 1
cache 10
cycle;


-- Create sequence 
create sequence SEQ_category_c_sort_id
minvalue 0
maxvalue 999
start with 1
increment by 1
cache 10
cycle;


-- Create sequence 
create sequence SEQ_category_g_sort_id
minvalue 0
maxvalue 999
start with 1
increment by 1
cache 10
cycle;


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.135_SSMS','MM1.1.1.149_SSMS');


commit;