delete from t_r_base b
 where b.id in
       (select a.id
          from t_r_reference a
         where a.categoryid in
               (select c.categoryid
                  from t_r_category c
                 where c.parentcategoryid in
                       (select t.categoryid
                          from t_r_category t
                         where t.id in ('1007', '1008', '1009'))));
             
commit;   
delete from t_r_reference a
 where a.categoryid in
       (select c.categoryid
                  from t_r_category c
                 where c.parentcategoryid in
                       (select t.categoryid
                          from t_r_category t
                         where t.id in ('1007', '1008', '1009')));

commit;                

delete
  from t_r_base a
 where a.id in (select c.id
                  from t_r_category c
                 where c.parentcategoryid in
                       (select t.categoryid
                          from t_r_category t
                         where t.id in ('1007', '1008', '1009')));

delete
  from t_sync_tactic t
 where t.categoryid in (select c.id
                  from t_r_category c
                 where c.parentcategoryid in
                       (select t.categoryid
                          from t_r_category t
                         where t.id in ('1007', '1008', '1009')));
commit;

delete
  from t_r_category a
 where a.id in (select c.id
                  from t_r_category c
                 where c.parentcategoryid in
                       (select t.categoryid
                          from t_r_category t
                         where t.id in ('1007', '1008', '1009')));

commit;
       
                         