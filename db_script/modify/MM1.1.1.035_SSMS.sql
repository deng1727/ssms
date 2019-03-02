

------------创建WAP最新榜所需应用提交时间和更新时间视图------
-------------------------------------------------------------
create or replace view  v_content_time as  
 select contentid,osid,createtime,updatetime from (
   select a.contentid,a.osid,a.createtime,a.updatetime,
   row_number()over(partition by a.contentid order by a.contentid,a.updatetime desc,a.createtime asc)  sortnum
   from V_CONTENT_LAST  a
   )
where (sortnum=1)


insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (35, 'wap_www最新榜基础语句', 'select b.id from t_r_base b, t_r_gcontent g, V_CONTENT_TIME d, v_service v where b.id = g.id and d.contentid = g.contentid and v.icpcode = g.icpcode and v.icpservid = g.icpservid and b.type like ''nt:gcontent:app%''  and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10''))');



insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.135_SSMS','MM1.1.1.035_SSMS');
commit;
