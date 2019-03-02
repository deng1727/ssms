
update t_r_exportsql set exportsql='select d.programid COLLECTID,
       d.name COLLECTNAME,
       d.detail DESCRIPTION,
       '''' TOTALPLAYNUM,
       p1.propertyvalue Director,
       p2.propertyvalue Actor,
       p3.propertyvalue ContentType,
       p4.pic_01_v pic1,
       p4.pic_00_tv pic2,
       d.displayname Category1
  from t_v_dprogram d,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, ''|'') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = ''导演''
         group by t1.programid, t1.cms_id) p1,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, ''|'') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = ''主演''
         group by t1.programid, t1.cms_id) p2,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, ''|'') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = ''内容形态''
         group by t1.programid, t1.cms_id) p3,
         t_v_videopic  p4
 where d.FORMTYPE = 6
   and d.PRDPACK_ID <> ''1003221''
    and d.programid = p1.programid(+)
   and d.programid = p2.programid(+)
   and d.programid = p3.programid(+)
   and d.programid = p4.programid(+)
   and d.cmsid = p1.cms_id(+)
   and d.cmsid = p2.cms_id(+)
   and d.cmsid = p3.cms_id(+)' where id=89;

commit;