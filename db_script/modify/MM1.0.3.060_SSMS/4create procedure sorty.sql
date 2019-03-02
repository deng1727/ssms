create or replace procedure p_shelf_sortby(v_ccategoryid varchar2) as
  v_status number;
  --自定义类型
  type type_table_CATEGORYID is table of varchar2(20) index by binary_integer;
  type type_table_NAME is table of VARCHAR2(100) index by binary_integer;
  type type_table_sort is table of number index by binary_integer;
  type type_table_path is table of VARCHAR2(300) index by binary_integer;

  --将根货架数据放在这些变量中
  v_tCATEGORYID type_table_CATEGORYID;
  v_tname       type_table_NAME;
  v_tid         type_table_sort;
  v_tpath       type_table_path;
  r_countrow    number;
  -------------------
  --将子货架和适配机型存放在这些变量中
  v_allicpcode type_table_CATEGORYID;
  v_allid      type_table_NAME;
  v_allunion   type_table_NAME;
  --------------------
  --前60数据放在下面变量中
  v_previcpcode type_table_CATEGORYID;
  v_previd      type_table_NAME;
  v_prevsort    type_table_sort;
  v_prevunion   type_table_NAME;

  --存放原前60位分类的数据据
  v_backicpcode type_table_CATEGORYID;
  v_backid      type_table_NAME;
  v_backsort    type_table_sort;
  v_backunion   type_table_NAME;
  v_plus        number := 1;
  v_nstatus     number := 0;
  v_backplus    number := 1;
  ----------------------------
  --seq的最大值
  v_nseqnum type_table_sort;
  v_addnum  number := 1;
  v_ntotal  number := 0;
  --记录sql%rowcount的值
  v_nrowcount number;
begin
  --监控存储过程情况的包
  v_status := pg_log_manage.f_startlog('P_SHELF_SORTBY',
                                       '对商品前60名排序，取base表的最大ID，获取货架的详细路径');

  --获取t_r_base表的最大id，
  select max(id)
    into v_nseqnum(1)
    from t_r_base t
   where ascii(substr(t.id, 1, 1)) > 47
     and ascii(substr(t.id, 1, 1)) < 58;
  v_nseqnum(1) := v_nseqnum(1) + 1;

  --获取货架的信息和path
  select t.id, t.name, t.categoryid, b.path bulk collect
    into v_tid, v_tname, v_tCATEGORYID, v_tpath
    from t_r_category t, t_r_base b
   where t.id = b.id
     and t.parentcategoryid = v_ccategoryid
     and t.delflag = '0';
  r_countrow := sql%rowcount;
  v_status   := pg_log_manage.f_successlog(vn_logid => v_status);

  --监控存储过程情况的包
  v_status := pg_log_manage.f_startlog('P_SHELF_SORTBY', '对前60名排序过滤');
  --根货架下应用循环
  /*
    根据货架查找该货架下的应用，对每一个适配机型的应用进行排序，在前60名中一个SP最多者能出现两次，多余的一次排在前60
    名之后
  
  */
  --for循环根据根货架，找的适配机型对应的应用
  for r in 1 .. r_countrow loop
    v_plus     := 1;
    v_backplus := 1;
  
    --根据父货架，提取商品信息
    select g.icpcode,
           g.id,
           g.companyid || g.productid || g.contentid || r.categoryid bulk collect
      into v_allicpcode, v_allid, v_allunion
      from t_r_reference r, t_r_gcontent g
     where r.refnodeid = g.id
       and r.categoryid = v_ccategoryid
       and g.fulldevicename like '%' || v_tname(r) || '%'
     order by r.sortid desc;
    v_nrowcount := sql%rowcount;
    if v_nrowcount > 0 then
    
      -----------------------------------------------------------
    
      delete from t_r_base b
       where exists (select 1
                from t_r_reference r
               where r.categoryid = v_tCATEGORYID(r)
                 and b.id = r.id);
    
      delete from t_r_reference r where r.categoryid = v_tCATEGORYID(r);
      -----------------------------------------------------------
    
      v_previcpcode(v_plus) := v_allicpcode(1);
      v_previd(v_plus) := v_allid(1);
      v_prevunion(v_plus) := v_allunion(1);
      v_prevsort(v_plus) := 1060;
    
      --对每一种适配机型前60名排序循环
      for i in 2 .. v_nrowcount loop
      
        --如果找到了前60名排序停止
        if v_plus <= 60 then
        
          v_nstatus := 0;
          --判断应用出现的次数，大于2移出
          for j in 1 .. v_plus loop
            if v_allicpcode(i) = v_previcpcode(j) and v_nstatus < 2 then
              v_nstatus := v_nstatus + 1;
            end if;
          end loop;
        
          v_nseqnum(v_addnum + 1) := v_nseqnum(v_addnum) + 1;
          v_addnum := v_addnum + 1;
        
          --如果出现次数大于2，就放在变量v_back*
          if v_nstatus >= 2 then
            v_backicpcode(v_backplus) := v_allicpcode(i);
            v_backid(v_backplus) := v_allid(i);
            v_backunion(v_backplus) := v_allunion(i);
            v_backplus := v_backplus + 1;
            --如果出现次数小于2，就放在变量v_prev*
          else
            v_plus := v_plus + 1;
            v_previcpcode(v_plus) := v_allicpcode(i);
            v_previd(v_plus) := v_allid(i);
            v_prevunion(v_plus) := v_allunion(i);
            v_prevsort(v_plus) := 1061 - v_plus;
          
          end if;
        end if;
      end loop;
    
      --合并排序后的数据
      v_backplus := v_backplus - 1;
    
      for h in 1 .. v_backplus loop
        v_previcpcode(v_plus) := v_backicpcode(h);
        v_previd(v_plus) := v_backid(h);
        v_prevunion(v_plus) := v_backunion(h);
        v_prevsort(v_plus) := v_prevsort(v_plus - 1) - 1;
        v_plus := v_plus + 1;
      end loop; --结束合并排序后的数据
    
      v_plus := v_plus - 1;
      --将排好序的数据放在t_r_reference
      for k in 1 .. v_plus loop
        insert into t_r_reference
          (id, refnodeid, sortid, goodsid, categoryid, loaddate)
        values
          (v_nseqnum(v_ntotal + k),
           v_previd(k),
           v_prevsort(k),
           v_tCATEGORYID(r) || v_prevunion(k),
           v_tCATEGORYID(r),
           to_char(sysdate, 'yyyy-MM-dd hh24:mi:ss'));
      end loop;
    
      --将生产的数据放在t_r_base
      for k in 1 .. v_plus loop
        insert into t_r_base
          (id, parentid, path, type)
        values
          (v_nseqnum(v_ntotal + k),
           v_tCATEGORYID(r),
           v_tpath(r) || '.{' || v_previd(k) || '}',
           'nt:reference');
      end loop;
      v_ntotal := v_plus + v_ntotal;
    end if;
  end loop;
  --如果成功，将执行情况写入日志
  v_status := pg_log_manage.f_successlog(vn_logid => v_status);
  commit;
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_status := pg_log_manage.f_errorlog(vn_logid => v_status);
  
end;
