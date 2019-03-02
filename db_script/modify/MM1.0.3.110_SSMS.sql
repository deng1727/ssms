----现网需建同义词
----需要授权
-- Create the synonym 
create or replace synonym T_D_PLATFORM
  for portalmo.T_D_PLATFORM;

-- Create the synonym 
create or replace synonym ppms_v_content_label
  for MM_PPMS.V_CM_CONTENT_BRAND_LABEL;

insert into T_CATERULE_COND_BASE (BASE_ID, BASE_NAME, BASE_SQL)
values (15, '从品牌店品牌汇的AP货架中获取', 'select b.id from t_r_base b, t_r_gcontent g, v_service v, t_content_count c, PPMS_V_CONTENT_LABEL l where b.id = g.id and b.type like ''nt:gcontent:app%'' and v.icpcode = g.icpcode and v.icpservid = g.icpservid and g.contentid = c.contentid(+) and g.provider != ''B'' and (g.subtype is null or g.subtype in (''1'', ''2'', ''3'', ''4'', ''5'', ''7'', ''8'', ''9'', ''10'')) and g.contentid = l.contentid');

update t_caterule_cond c
   set c.condtype = '15', c.basecondid = '15'
 where c.basecondid = '10'
   and c.ruleid in
       (select t.ruleid
          from t_category_rule t
         where t.cid in (select id
                           from t_r_category r
                          where r.parentcategoryid =
                                (select y.categoryid
                                   from t_r_category y
                                  where y.id = '1782550')));


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.3.105_SSMS','MM1.0.3.110_SSMS');
commit;
