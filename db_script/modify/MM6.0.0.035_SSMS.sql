 CREATE TABLE T_MM_ESSENTIAL
(
  MMcontentId      VARCHAR2(20) NOT NULL ,           
  contactId        VARCHAR2(20) NOT NULL ,                                          
  updateTime     DATE                       
);


create or replace view v_v_dprogram_fiter as select d.programid VideoID,
       d.name VName,
       '' Type,
       c.fee Price,
       '' Star,
       '' Dayplaynum,
       '' totalPlayNum,
      '' dayDownNum,
       '' totalDownNum,
       to_char(to_date(d.updatetimev, 'yyyy - mm - dd hh24 :mi :ss'), 'yyyyMMdd') updatetimev,
       '' Label,
       REPLACE(d.detail, chr(10), '') Brief,
       '' Hot,
       decode(d.type, 2, 1, 2) ChildCategory,
       'all' FullDevice,
       p5.pic_01_v imgurl1,
       p5.pic_01_v imgurl2,
       '' cid,
       d.CDURATION playtime,
       d.displayname Category1,
       '内容类型' Category2,
       p4.propertyvalue Tag,
       p1.propertyvalue Director,
       p2.propertyvalue Actor,
       p3.propertyvalue contentType,
       d.feetype
  from t_v_dprogram d,
       (select DISTINCT f.fee fee, p.servid
          from t_v_propkg p
          left join t_v_PRODUCT f
            on p.dotfeecode = f.feecode) c,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, '|') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = '导演'
         group by t1.programid, t1.cms_id) p1,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, '|') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = '主演'
         group by t1.programid, t1.cms_id) p2,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, '|') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = '内容形态'
         group by t1.programid, t1.cms_id) p3,
       (select t1.programid,
               t1.cms_id,
               listagg(t1.propertyvalue, '|') within GROUP(order by t1.programid, t1.cms_id) propertyvalue
          from t_v_videospropertys t1
         where t1.propertykey = '内容类型'
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
   and d.PRDPACK_ID <> '1003221';
   
  update t_r_exportsql set exportsql='select VideoID,VName,Type,Price,Star,Dayplaynum,totalPlayNum,dayDownNum,totalDownNum,updatetimev,Label,Brief,Hot,ChildCategory,FullDevice,imgurl1,imgurl2,cid,playtime,Category1,Category2,Tag,Director,Actor,contentType
		 from (select t.*,
      row_number() over(partition by VName,Category2 order by feetype asc) as rank
  from v_v_dprogram_fiter t ) a   where a.rank=1' where id=88;

commit;