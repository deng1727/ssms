-- Drop columns 
alter table T_R_CATEGORY drop column OTHERNET;

drop table T_R_DOWN_SORT;
drop table T_R_DOWN_SORT_NEW;
drop table T_R_DOWN_SORT_OLD;
drop procedure p_Binding_sort;
drop view v_r_sevencompared;


create or replace view t_r_servenday_temp_a as
select "STAT_TIME","OS_ID","OS_NAME","CONTENT_ID","CONTENT_NAME","PRODUCT_TYPE_ID","DOWN_COUNT","ADD_7DAYS_DOWN_COUNT" from t_r_servenday_temp t where t.os_id=9;

create or replace view t_r_servenday_temp_o as
select "STAT_TIME","OS_ID","OS_NAME","CONTENT_ID","CONTENT_NAME","PRODUCT_TYPE_ID","DOWN_COUNT","ADD_7DAYS_DOWN_COUNT" from t_r_servenday_temp t where t.os_id=3;


update t_caterule_cond_base set base_sql='select b.id from t_r_base b, t_r_gcontent g, v_service v,t_content_count c,T_R_SEVENCOMPARED s where s.CONTENTID=g.contentid and b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.contentid(+) and g.provider !=''B'' and (g.subtype is null or g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10''))' where base_id='15';

update t_caterule_cond_base set base_sql='select b.id from t_r_base b, t_r_gcontent g, v_service v,t_r_servenday_temp_a c,T_R_SEVENCOMPARED s where  s.CONTENTID=g.contentid and   b.id = g.id  and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.CONTENT_ID  and  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')' where base_id='16';

update t_caterule_cond_base set base_sql='select b.id from t_r_base b, t_r_gcontent g, v_service v,t_r_servenday_temp_o c,T_R_SEVENCOMPARED s where  s.CONTENTID=g.contentid and   b.id = g.id  and b.type like ''nt:gcontent:app%''  and v.icpcode = g.icpcode and v.icpservid = g.icpservid  and g.contentid=c.CONTENT_ID  and  g.subtype in (''1'',''2'',''3'',''4'',''5'',''7'',''8'',''9'',''10'')' where base_id='17';


delete DBVERSION where PATCHVERSION = 'MM1.0.3.115_SSMS' and LASTDBVERSION = 'MM1.0.3.110_SSMS';
commit;