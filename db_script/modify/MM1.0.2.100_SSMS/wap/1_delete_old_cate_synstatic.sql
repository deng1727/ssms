
delete from t_r_base b
 where b.id in
       (select a.id
          from t_r_reference a
         where a.categoryid in
               (select categoryid
                  from t_r_category t
                 start with t.categoryid in
                            ('100001195', '100001196', '100001197')
                connect by prior categoryid = parentcategoryid));
             
commit;   
delete from t_r_reference a
 where a.categoryid in
       (select categoryid
          from t_r_category t
         start with t.categoryid in ('100001195', '100001196', '100001197')
        connect by prior categoryid = parentcategoryid);

commit;                
--ɾ��ԭ�л��ܷ������ݣ��ĳ�delete������
delete
  from t_r_base a
 where a.id in (select id
                  from t_r_category t
                 start with t.categoryid in
                            ('100001195', '100001196', '100001197')
                connect by prior categoryid = parentcategoryid);

--ɾ��ԭ�л��ܷ����Ӧ��ͬ�����ԣ��ĳ�delete������
delete
  from t_sync_tactic t
 where t.categoryid in (select id
                  from t_r_category t
                 start with t.categoryid in
                            ('100001195', '100001196', '100001197')
                connect by prior categoryid = parentcategoryid);
commit;

delete
  from t_r_category a
 where a.id in (select id
                  from t_r_category t
                 start with t.categoryid in
                            ('100001195', '100001196', '100001197')
                connect by prior categoryid = parentcategoryid);

 commit;
