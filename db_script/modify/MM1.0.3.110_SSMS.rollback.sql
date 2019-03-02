drop synonym T_D_PLATFORM;

drop synonym ppms_v_content_label;

update t_caterule_cond c
   set c.condtype = '10', c.basecondid = '10'
 where c.basecondid = '15'
   and c.ruleid in
       (select t.ruleid
          from t_category_rule t
         where t.cid in (select id
                           from t_r_category r
                          where r.parentcategoryid =
                                (select y.categoryid
                                   from t_r_category y
                                  where y.id = '1782550')));

delete T_CATERULE_COND_BASE where BASE_ID=15;

delete DBVERSION where PATCHVERSION = 'MM1.0.3.110_SSMS' and LASTDBVERSION = 'MM1.0.3.105_SSMS';
commit;