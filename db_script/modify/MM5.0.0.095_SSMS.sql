create table t_r_exportsql_20160923 as (select * from t_r_exportsql where id in ('88','89'));

--新视频业务数据同步
update t_r_exportsql
   set exportsql = '
select d.programid VideoID,
       d.name VName,
       '''' Type,
       c.fee Price,
       '''' Star,
       '''' Dayplaynum,
       '''' totalPlayNum,
      '''' dayDownNum,
       '''' totalDownNum,
       to_char(to_date(d.updatetimev, ''yyyy - mm - dd hh24 :mi :ss''), ''yyyyMMdd''),
       '''' Label,
       d.detail Brief,
       '''' Hot,
       decode(d.type, 2, 1, 2) ChildCategory,
       ''all'' FullDevice,
       p5.pic_01_v imgurl1,
       p5.pic_01_v imgurl2,
       '''' cid,
       d.CDURATION playtime,
       d.displayname Category1,
       ''内容类型'' Category2,
       p4.propertyvalue Tag,
       p1.propertyvalue Director,
       p2.propertyvalue Actor,
       p3.propertyvalue contentType
  from t_v_dprogram d,
       (select DISTINCT f.fee fee, p.servid
          from t_v_propkg p
          left join t_v_PRODUCT f
            on p.dotfeecode = f.feecode) c,
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
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, ''|'') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = ''内容类型''
         group by t1.programid, t1.cms_id) p4,
         t_v_videopic  p5
 where d.prdpack_id = c.servid(+)
   and d.programid = p1.programid(+)
   and d.programid = p2.programid(+)
   and d.programid = p3.programid(+)
   and d.programid = p4.programid(+)
   and d.programid = p5.programid(+)
   and d.cmsid = p1.cms_id(+)
   and d.cmsid = p2.cms_id(+)
   and d.cmsid = p3.cms_id(+)
   and d.cmsid = p4.cms_id(+)
   and d.FORMTYPE not in (6, 7)
   and d.PRDPACK_ID <> ''1003221'''
   where id=88;

--新视频内容集数据同步
update t_r_exportsql
   set exportsql = '
select d.programid COLLECTID,
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
   and d.cmsid = p3.cms_id(+)',
   exportline=10
   where id=89;
  
  commit;
  insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM5.0.0.0.079_SSMS','MM5.0.0.0.095_SSMS');
commit;