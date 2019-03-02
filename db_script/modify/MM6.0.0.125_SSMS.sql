
update t_v_propkg t set t.prodname='20Ôª¶©¹º°üÔÂ' where id =21;
	
commit;
	
	
create or replace view v_gc_app_info_ssms_increment 
  as select a.*
     from (select t.*,
      row_number() over(partition by t.goodsid order by t.process_date asc) as rank
  from (select * from v_gc_app_info v where  v.process_date>sysdate-1 ) t ) a   where a.rank=1;
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  	
commit;
	
	
	
	
	
	
	
	
	
	
	
	





















	
	
	
	
	
	