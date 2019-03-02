
delete
  from t_r_base b
 where b.id in
       (select a.id
          from t_r_reference a
         where a.categoryid in
               (select categoryid
                  from t_r_category
                 start with categoryid = '100000007'
                connect by prior categoryid = parentcategoryid));
commit;
                
delete
  from t_r_reference a
 where a.categoryid in
       (select categoryid
          from t_r_category
         start with categoryid = '100000007'
        connect by prior categoryid = parentcategoryid);
commit;
--删除原有货架分类数据（改成delete操作）
delete
  from t_r_base a
 where a.id in (select id
                from t_r_category
               start with categoryid = '100000007'
              connect by prior categoryid = parentcategoryid);
              
--删除原有货架分类对应的同步策略（改成delete操作）
delete
  from t_sync_tactic t
 where t.categoryid in (select id
                from t_r_category
               start with categoryid = '100000007'
              connect by prior categoryid = parentcategoryid);
commit;              
delete
  from t_r_category a
 where a.id in (select id
                from t_r_category
               start with categoryid = '100000007'
              connect by prior categoryid = parentcategoryid);

              
commit;              