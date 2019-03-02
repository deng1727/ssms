--------------------------新增 t_v_apiRequestParamter表 开始----------------------------------------------------------------------

create table t_v_apiRequestParamter(
   content varchar2(2),
   contentType varchar2(20),
   contentName varchar2(20)
);

comment on table t_v_apiRequestParamter
  is '新视频api请求参数';
comment on column  t_v_apiRequestParamter.content is '内容';
comment on column  t_v_apiRequestParamter.contentType is '内容类型';
comment on column  t_v_apiRequestParamter.contentName is '内容分类';
-- 普通节目
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1000','电影');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1001','电视剧');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1002','纪实');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1003','体育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1004','新闻');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1005','综艺');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1006','娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1007','动漫');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1008','生活');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1009','旅游');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1010','原创');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','1011','教育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500020','直播');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500060','悦听-有声小说');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500067','悦听-评书');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500072','悦听-电台');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500078','悦听-热点资讯');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500100','悦听-儿童');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500106','悦听-娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500111','悦听-都市白领');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500213','渠道推广');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500323','音频');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500405','军事');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500468','健康');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500424','搞笑');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500377','法制');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500320','财经');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('01','500422','时尚');

--业务产品和产品促销计费
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1000','电影');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1001','电视剧');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1002','纪实');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1003','体育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1004','新闻');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1005','综艺');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1006','娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1007','动漫');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1008','生活');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1009','旅游');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1010','原创');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','1011','教育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500020','直播');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500060','悦听-有声小说');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500067','悦听-评书');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500072','悦听-电台');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500078','悦听-热点资讯');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500100','悦听-儿童');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500106','悦听-娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500111','悦听-都市白领');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500213','渠道推广');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500323','音频');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500405','军事');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500468','健康');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500424','搞笑');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500377','法制');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500320','财经');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('02','500422','时尚');
--计数据费
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1000','电影');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1001','电视剧');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1002','纪实');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1003','体育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1004','新闻');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1005','综艺');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1006','娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1007','动漫');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1008','生活');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1009','旅游');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1010','原创');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','1011','教育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500020','直播');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500060','悦听-有声小说');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500067','悦听-评书');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500072','悦听-电台');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500078','悦听-热点资讯');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500100','悦听-儿童');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500106','悦听-娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500111','悦听-都市白领');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500213','渠道推广');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500323','音频');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500405','军事');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500468','健康');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500424','搞笑');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500377','法制');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500320','财经');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('03','500422','时尚');
--热点主题列表
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1000','电影');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1001','电视剧');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1002','纪实');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1003','体育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1004','新闻');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1005','综艺');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1006','娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1007','动漫');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1008','生活');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1009','旅游');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1010','原创');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','1011','教育');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500020','直播');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500060','悦听-有声小说');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500067','悦听-评书');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500072','悦听-电台');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500078','悦听-热点资讯');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500100','悦听-儿童');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500106','悦听-娱乐');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500111','悦听-都市白领');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500213','渠道推广');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500323','音频');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500405','军事');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500468','健康');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500424','搞笑');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500377','法制');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500320','财经');
insert into t_v_apiRequestParamter(content,contentType,contentName)values('04','500422','时尚');
--------------------------新增 t_v_apiRequestParamter表 开始----------------------------------------------------------------------

----------------------------在t_v_reference 新增三个字段 开始-----------------------------------------------------------------------
alter table t_v_reference add broadcast varchar(100);
alter table t_v_reference add countriy varchar(100);
alter table t_v_reference add contentType varchar(100);
-- Add comments to the columns 
comment on column t_v_reference.broadcast
  is '播出年代';
-- Add comments to the columns 
comment on column t_v_reference.countriy
  is '国家及地区';
-- Add comments to the columns 
comment on column t_v_reference.contentType
  is '内容类型';
----------------------------在t_v_reference 新增三个字段 结束-----------------------------------------------------------------------

----------------------------修改存储过程p_v_program_update 开始--------------------------------------------------------------------
create or replace procedure p_v_program_update as
  v_nstatus     number;
  v_nrecod      number;
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
    from (select distinct  d.displaytype  from t_v_dprogram d
   where not exists (select 1
            from t_v_hotcatemap m1
           where d.displaytype = to_number(m1.titleid))) d;


----插入货架列表----
insert into t_v_category(id,categoryid,parentcid,cname,cdesc,isshow,sortid,lupdate)
select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,d.categoryid,'101', d.displayname,d.displayname,'1','1',sysdate as lupdate
from (select distinct m.categoryid,d.displayname
  from t_v_hotcatemap m,t_v_dprogram d
 where to_number(m.titleid) = d.displaytype and m.type = '2' and   not exists (select 1
          from t_v_category c
         where m.categoryid = c.categoryid))d ;

insert into t_v_category(id,categoryid,parentcid,cname,cdesc,isshow,sortid,lupdate)
select SEQ_T_V_CATEGORY_ID.NEXTVAL as id,d.locationid,'105', d.loc_name,d.loc_desc,'1','1',sysdate as lupdate
  from T_V_HOTCONTENT_LOCATION d
 where  not exists (select 1
          from t_v_category c
         where d.locationid = c.categoryid) ;


-----删除下线的
----------
delete from t_v_reference t where not exists(select 1 from t_v_dprogram d where d.programid=t.programid);

delete from t_v_reference t where  exists(select 1 from t_v_category d where d.categoryid = t.categoryid and d.parentcid = '105');

----插入上架未上架一级分类货架的节目到商品表---
----
insert into t_v_reference(ID,PROGRAMID,CATEGORYID,CMS_ID,PNAME,SORTID,FEETYPE,LUPDATE,broadcast,countriy,contentType)
select SEQ_T_V_REFERENCE_ID.NEXTVAL as id, d.programid,d.categoryid, cms_id, pname,rownum as sortid,d.FEETYPE,sysdate as lupdate,d.broadcast,d.countriy,d.contentType
from (select distinct d.programid,m1.categoryid,d.cmsid as cms_id,d.name as pname,d.FEETYPE,p.broadcast,p.countriy,p.contentType
  from t_v_dprogram d,t_v_hotcatemap m1,(select  distinct d.programid,d.cmsid cms_id,p1.broadcast,p2.countriy,p3.contentType from t_v_dprogram d,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id) as broadcast from t_v_videospropertys p where p.propertykey = '播出年代'
group by p.programid,p.cms_id) p1,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|')within GROUP (order by p.programid,p.cms_id)  as countriy from t_v_videospropertys p where p.propertykey = '国家及地区'
group by p.programid,p.cms_id ) p2,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id)  as contentType from t_v_videospropertys p where p.propertykey = '内容类型'
group by p.programid,p.cms_id) p3 where d.programid = p1.programid(+) and d.cmsid=p1.cms_id(+) and d.programid = p2.programid(+) and d.cmsid=p2.cms_id(+)
and d.programid = p3.programid(+) and d.cmsid=p3.cms_id(+) ) p
 where d.displaytype=to_number(m1.titleid) and p.cms_id(+) = d.cmsid and p.programid(+) = d.programid and  not exists (select 1
          from t_v_reference r, t_v_hotcatemap m
         where r.categoryid = m.categoryid
           and m.type = '2'
           and to_number(m.titleid) = d.displaytype
            and r.programid=d.programid)) d ;

insert into t_v_reference(ID,PROGRAMID,CATEGORYID,CMS_ID,PNAME,SORTID,LUPDATE,broadcast,countriy,contentType)
select SEQ_T_V_REFERENCE_ID.NEXTVAL as id, d.prdcontid,d.categoryid, d.contentid, d.pname,rownum as sortid,sysdate as lupdate,d.broadcast,d.countriy,d.contentType
from (select  distinct d.prdcontid, d.contentid,l.locationid categoryid,d.title pname,p.broadcast,p.countriy,p.contentType from T_V_HOTCONTENT_PROGRAM d ,T_V_HOTCONTENT_LOCATION l,(
select  distinct d.prdcontid programid,d.contentid cms_id,p1.broadcast,p2.countriy,p3.contentType from T_V_HOTCONTENT_PROGRAM d,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id) as broadcast from t_v_videospropertys p where p.propertykey = '播出年代'
group by p.programid,p.cms_id) p1,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|')within GROUP (order by p.programid,p.cms_id)  as countriy from t_v_videospropertys p where p.propertykey = '国家及地区'
group by p.programid,p.cms_id ) p2,(
select p.programid,p.cms_id,listagg(p.propertyvalue,'|') within GROUP (order by p.programid,p.cms_id)  as contentType from t_v_videospropertys p where p.propertykey = '内容类型'
group by p.programid,p.cms_id) p3 where d.prdcontid = p1.programid(+) and d.contentid=p1.cms_id(+) and d.prdcontid = p2.programid(+) and d.contentid=p2.cms_id(+)
and d.prdcontid = p3.programid(+) and d.contentid=p3.cms_id(+) ) p
where d.location = l.locationid and d.contenttype in (1,2,3) and p.programid=d.prdcontid and p.cms_id = d.contentid) d ;      



 commit;
 v_nrecod:=SQL%ROWCOUNT;
   v_nstatus :=pg_log_manage.f_successlog(vn_RECORDNUM =>v_nrecod );
exception
  when others then
    rollback;
     --如果失败，将执行情况写入日志
    v_nstatus := pg_log_manage.f_errorlog;
end p_v_program_update;
----------------------------修改存储过程p_v_program_update 结束--------------------------------------------------------------------

alter table T_V_HOTCONTENT_LOCATION add loc_name varchar2(100);
alter table T_V_HOTCONTENT_LOCATION add loc_desc varchar2(100);
alter table T_V_HOTCONTENT_LOCATION add loc_type varchar2(10);
alter table T_V_HOTCONTENT_LOCATION add choice_type varchar2(10);
alter table T_V_HOTCONTENT_LOCATION add updatetime date default sysdate;
-- Add comments to the columns 
comment on column T_V_HOTCONTENT_LOCATION.updatetime
  is '更新时间';
comment on column T_V_HOTCONTENT_LOCATION.loc_name
  is '位置名称';
comment on column T_V_HOTCONTENT_LOCATION.loc_desc
  is '位置描述';
  
delete from t_v_hotcontent_location;

insert into t_v_category
  (id, categoryid, parentcid, cname, cdesc, isshow, sortid, lupdate)
values
  (SEQ_T_V_CATEGORY_ID.NEXTVAL,
   '105',
   '-1',
   '热点主题内容根货架',
    '热点主题内容根货架',
   '1',
   '1',
   sysdate);

insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10169','咪咕视频-推荐-社会百态','咪咕视频-推荐-社会百态','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10170','咪咕视频-推荐-劲爆体育','咪咕视频-推荐-劲爆体育','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10172','咪咕视频-推荐-精彩生活','咪咕视频-推荐-精彩生活','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10173','咪咕视频-推荐-旅游','咪咕视频-推荐-旅游','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10174','咪咕视频-推荐-财经新闻','咪咕视频-推荐-财经新闻','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10175','咪咕视频-推荐-电影','咪咕视频-推荐-电影','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10221','咪咕视频-会员-滚图','咪咕视频-会员-滚图','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10222','咪咕视频-会员-首发大片','咪咕视频-会员-首发大片','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10223','咪咕视频-会员-海外专区','咪咕视频-会员-海外专区','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10226','咪咕视频-会员-全球纪实','咪咕视频-会员-全球纪实','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10163','咪咕视频-直播-精彩回看','咪咕视频-直播-精彩回看','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10168','咪咕视频-推荐-娱乐八卦','咪咕视频-推荐-娱乐八卦','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10161','咪咕视频-直播-滚图','咪咕视频-直播-滚图','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10105','会员产品-综艺','会员产品-综艺','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10108','会员产品-原创搞笑','会员产品-原创搞笑','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10162','咪咕视频-直播-今日热门直播','咪咕视频-直播-今日热门直播','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10164','咪咕视频-直播-热门电视台','咪咕视频-直播-热门电视台','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10165','咪咕视频-推荐-滚图','咪咕视频-推荐-滚图','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10166','咪咕视频-推荐-今日热点','咪咕视频-推荐-今日热点','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10167','咪咕视频-推荐-今日热点图片位','咪咕视频-推荐-今日热点图片位','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10171','咪咕视频-推荐-纪录片','咪咕视频-推荐-纪录片','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10241','会员产品-IOS-滚图','会员产品-IOS-滚图','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10106','会员产品-动漫','会员产品-动漫','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10103','会员产品-电影','会员产品-电影','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10104','会员产品-电视剧','会员产品-电视剧','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10107','会员产品-娱乐','会员产品-娱乐','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10109','会员产品-纪实','会员产品-纪实','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10110','会员产品-体育','会员产品-体育','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10111','会员产品-生活','会员产品-生活','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10113','会员产品-听音','会员产品-听音','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10181','会员产品-今日最热','会员产品-今日最热','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10176','咪咕视频-推荐-电视剧','咪咕视频-推荐-电视剧','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10201','会员产品-V精选','会员产品-V精选','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10224','咪咕视频-会员-好剧独播','咪咕视频-会员-好剧独播','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10101','会员产品-滚图','会员产品-滚图','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10225','咪咕视频-会员-明星综艺','咪咕视频-会员-明星综艺','11','2');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10102','会员产品-资讯','会员产品-资讯','9','1');
insert into t_v_hotcontent_location(locationid,loc_name,loc_desc,loc_type,choice_type)values('10112','会员产品-教育','会员产品-教育','9','1');


insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.129_SSMS','MM5.0.0.0.105_SSMS');
commit;