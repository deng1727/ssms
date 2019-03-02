
--基地新视频商品上架更新  存储过程 begin

----插入上架未上架一级分类货架的节目到商品表---
create or replace procedure p_v_program_update as
  v_nstatus number;
  v_nrecod  number;
begin
  v_nstatus := pg_log_manage.f_startlog('p_v_program_update',
                                        
                                        '基地新视频商品上架更新');
  
  ---插入新增加货架关系---
  insert into t_v_hotcatemap
    select SEQ_T_V_HOTCATEMAP_ID.Nextval id,
           d.displaytype,
           SEQ_T_V_CATEGORY_CID.NEXTVAL  as categoryid,
           '2',
           sysdate                       as lupdate
      from (select distinct d.displaytype
              from t_v_dprogram d
             where not exists
             (select 1
                      from t_v_hotcatemap m1
                     where d.displaytype = to_number(m1.titleid))) d;

  ----插入货架列表----
  insert into t_v_category
    (id, categoryid, parentcid, cname, cdesc, isshow, sortid, lupdate)
    select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,
           d.categoryid,
           '101',
           d.displayname,
           d.displayname,
           '1',
           '1',
           sysdate                     as lupdate
      from (select distinct m.categoryid, d.displayname
              from t_v_hotcatemap m, t_v_dprogram d
             where to_number(m.titleid) = d.displaytype
               and m.type = '2'
               and not exists
             (select 1
                      from t_v_category c
                     where m.categoryid = c.categoryid)) d;

  insert into t_v_category
    (id, categoryid, parentcid, cname, cdesc, isshow, sortid, lupdate)
    select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,
           d.locationid,
           '105',
           d.loc_name,
           d.loc_desc,
           '1',
           '1',
           sysdate                     as lupdate
      from T_V_HOTCONTENT_LOCATION d
     where not exists
     (select 1 from t_v_category c where d.locationid = c.categoryid);

  -----删除下线的
  ----------
  delete from t_v_reference t
   where not exists
   (select 1 from t_v_dprogram d where d.programid = t.programid);

  delete from t_v_reference t
   where exists (select 1
            from t_v_category d
           where d.categoryid = t.categoryid
             and d.parentcid = '105');

  ----插入上架未上架一级分类货架的节目到商品表---
  ----
  insert into t_v_reference
    (ID,
     PROGRAMID,
     CATEGORYID,
     CMS_ID,
     PNAME,
     SORTID,
     FEETYPE,
     LUPDATE,
     broadcast,
     countriy,
     contentType)
    select SEQ_T_V_REFERENCE_ID.NEXTVAL as id,
           d.programid,
           d.categoryid,
           cms_id,
           pname,
           rownum                       as sortid,
           d.FEETYPE,
           sysdate                      as lupdate,
           d.broadcast,
           d.countriy,
           d.contentType
      from (select distinct d.programid,
                            m1.categoryid,
                            d.cmsid       as cms_id,
                            d.name        as pname,
                            d.FEETYPE,
                            p.broadcast,
                            p.countriy,
                            p.contentType
              from t_v_dprogram d,
                   t_v_hotcatemap m1,
                   (select distinct d.programid,
                                    d.cmsid cms_id,
                                    p1.broadcast,
                                    p2.countriy,
                                    p3.contentType
                      from t_v_dprogram d,
                           (select p.programid,
                                   p.cms_id,
                                   listagg(p.propertyvalue, '|') within GROUP(order by p.programid, p.cms_id) as broadcast
                              from t_v_videospropertys p
                             where p.propertykey = '播出年代'
                             group by p.programid, p.cms_id) p1,
                           (select p.programid,
                                   p.cms_id,
                                   listagg(p.propertyvalue, '|') within GROUP(order by p.programid, p.cms_id) as countriy
                              from t_v_videospropertys p
                             where p.propertykey = '国家及地区'
                             group by p.programid, p.cms_id) p2,
                           (select p.programid,
                                   p.cms_id,
                                   listagg(p.propertyvalue, '|') within GROUP(order by p.programid, p.cms_id) as contentType
                              from t_v_videospropertys p
                             where p.propertykey = '内容类型'
                             group by p.programid, p.cms_id) p3
                     where d.programid = p1.programid(+)
                       and d.cmsid = p1.cms_id(+)
                       and d.programid = p2.programid(+)
                       and d.cmsid = p2.cms_id(+)
                       and d.programid = p3.programid(+)
                       and d.cmsid = p3.cms_id(+)) p
                       
             where d.displaytype = to_number(m1.titleid)
               and p.cms_id(+) = d.cmsid
               and p.programid(+) = d.programid
               and not exists
             (select 1
                      from t_v_reference r, t_v_hotcatemap m
                     where r.categoryid = m.categoryid
                       and m.type = '2'
                       and to_number(m.titleid) = d.displaytype
                       and r.programid = d.programid)) d;

  insert into t_v_reference
    (ID,
     PROGRAMID,
     CATEGORYID,
     CMS_ID,
     PNAME,
     SORTID,
     LUPDATE,
     broadcast,
     countriy,
     contentType)
    select SEQ_T_V_REFERENCE_ID.NEXTVAL as id,
           d.prdcontid,
           d.categoryid,
           d.contentid,
           d.pname,
           rownum                       as sortid,
           sysdate                      as lupdate,
           d.broadcast,
           d.countriy,
           d.contentType
      from (select distinct d.prdcontid,
                            d.contentid,
                            l.locationid  categoryid,
                            d.title       pname,
                            p.broadcast,
                            p.countriy,
                            p.contentType
              from T_V_HOTCONTENT_PROGRAM d,
                   T_V_HOTCONTENT_LOCATION l,
                   (select distinct d.prdcontid    programid,
                                    d.contentid    cms_id,
                                    p1.broadcast,
                                    p2.countriy,
                                    p3.contentType
                      from T_V_HOTCONTENT_PROGRAM d,
                           (select p.programid,
                                   p.cms_id,
                                   listagg(p.propertyvalue, '|') within GROUP(order by p.programid, p.cms_id) as broadcast
                              from t_v_videospropertys p
                             where p.propertykey = '播出年代'
                             group by p.programid, p.cms_id) p1,
                           (select p.programid,
                                   p.cms_id,
                                   listagg(p.propertyvalue, '|') within GROUP(order by p.programid, p.cms_id) as countriy
                              from t_v_videospropertys p
                             where p.propertykey = '国家及地区'
                             group by p.programid, p.cms_id) p2,
                           (select p.programid,
                                   p.cms_id,
                                   listagg(p.propertyvalue, '|') within GROUP(order by p.programid, p.cms_id) as contentType
                              from t_v_videospropertys p
                             where p.propertykey = '内容类型'
                             group by p.programid, p.cms_id) p3
                     where d.prdcontid = p1.programid(+)
                       and d.contentid = p1.cms_id(+)
                       and d.prdcontid = p2.programid(+)
                       and d.contentid = p2.cms_id(+)
                       and d.prdcontid = p3.programid(+)
                       and d.contentid = p3.cms_id(+)) p
             where d.location = l.locationid
               and d.contenttype in (1, 2, 3)
               and p.programid = d.prdcontid
               and p.cms_id = d.contentid) d;

  commit;
  v_nrecod  := SQL%ROWCOUNT;
  v_nstatus := pg_log_manage.f_successlog(vn_RECORDNUM => v_nrecod);
exception
  when others then
    rollback;
    --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end p_v_program_update;
--基地新视频商品上架更新  存储过程 end


--恢复导出
update t_r_exportsql set exportsql='select d.programid COLLECTID,d.name COLLECTNAME,d.VSHORTNAME DESCRIPTION,'''' TOTALPLAYNUM from t_v_dprogram d where d.FORMTYPE=6' where id=89;
commit;
update t_r_exportsql set exportsql='select d.programid VideoID,d.name VName,'''' Type, c.fee Price, '''' Star,'''' Dayplaynum,'''' totalPlayNum, '''' dayDownNum ,'''' totalDownNum ,to_char(to_date(d.updatetimev,''yyyy-mm-dd hh24:mi:ss''),''yyyyMMdd'') ,'''' Label,d.Vshortname Brief ,'''' Hot, decode(d.type,2,1,2) ChildCategory,''all'' FullDevice ,''''   imgurl1,''''  imgurl2,''''     cid,d.CDURATION  playtime,d.displayname Category1,p.key  Category2,p.value Tag from t_v_dprogram d,(select f.fee fee,p.servid from  t_v_propkg p left join t_v_PRODUCT f on p.dotfeecode=f.feecode) c,(select p.programid,p.cms_id,listagg(p.propertyvalue,''|'')within GROUP (order by p.programid,p.cms_id)  as value,listagg(p.propertykey,''|'')within GROUP (order by p.programid,p.cms_id)  as key from t_v_videospropertys p group by p.programid,p.cms_id) p where d.prdpack_id = c.servid(+) and d.programid = p.programid(+) and p.cms_id(+) = d.cmsid and d.FORMTYPE not in (6,7)' where id=88;
commit;

delete DBVERSION where PATCHVERSION = 'MM5.0.0.0.059_SSMS' and LASTDBVERSION = 'MM5.0.0.0.505_SSMS';
commit;