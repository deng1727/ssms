

--动漫基地开始 add by aiyan 2012-11-20
create table comic_portal_mo_tra as select id from t_cb_content where 1=2;
create table comic_portal_mo as select id from t_cb_content where 1=2;
create table comic_portal_wap_tra as select id from t_cb_content where 1=2;
create table comic_portal_wap as select id from t_cb_content where 1=2;
create index IDX_CHAPTER_CHAPTERID on T_CB_CHAPTER (CHAPTERID);

create or replace function f_buildPortal  return number as
  v_status number;--日志返回
   v_nrecord number;--影响记录数目

begin
   v_status:=pg_log_manage.f_startlog('f_buildPortal','计算动漫内容的门户属性值' );
   
   --动画、漫画开始
--动漫基地“呈现”文件接口
--漫画：
--11.WAP在线观看
--12.客户端在线观看
--13.客户端下载
--14.WAP插件在线观看
--动画：
--21.WAP在线观看
--22.WAP下载
--23.客户端在线观看
--24.客户端下载

--101:Theme，主题
--116:MovieSeries，动画片
--115:Information，资讯
--220:ComicSeries，漫画书

  --基地给的portal都是3 不准确，这里初始化为0，不是任何门户的。
  update  t_cb_content set portal = '0' where type in ('220', '116');--把漫画、动画、主题的PORTAL变成0先
  delete from comic_portal_mo_tra;
  delete from comic_portal_wap_tra;
  commit;

  insert into comic_portal_mo_tra select distinct c.id from t_cb_content c, t_cb_chapter ch, t_cb_adapter a
                   where ch.contentid = c.id
                   and ch.chapterid = a.chapterid
                   and c.type in ('220', '116')
                   and a.use_type in (12, 13, 23, 24);
   v_nrecord:=sql%rowcount;
   if v_nrecord > 0 then
     execute immediate 'alter table comic_portal_mo rename to comic_portal_mo_bak';
     execute immediate 'alter table comic_portal_mo_tra rename to comic_portal_mo';
     execute immediate 'alter table comic_portal_mo_bak rename to comic_portal_mo_tra';
     commit;
   end if;


   insert into comic_portal_wap_tra select distinct c.id from t_cb_content c, t_cb_chapter ch, t_cb_adapter a
                 where ch.contentid = c.id
                   and ch.chapterid = a.chapterid
                   and c.type in ('220', '116')
                   and a.use_type in (11,14,21,22);
   v_nrecord:=sql%rowcount;
   if v_nrecord > 0 then
     execute immediate 'alter table comic_portal_wap rename to comic_portal_wap_bak';
     execute immediate 'alter table comic_portal_wap_tra rename to comic_portal_wap';
     execute immediate 'alter table comic_portal_wap_bak rename to comic_portal_wap_tra';
     commit;
   end if;

   update t_cb_content set portal = '1' where id in (select id from comic_portal_mo);
   update t_cb_content set portal = '2' where id in (select id from comic_portal_wap);
   update t_cb_content set portal = '3' where id in (select m.id from comic_portal_mo m , comic_portal_wap w where m.id = w.id);
   commit;
   --动画、漫画结束
v_status :=pg_log_manage.f_successlog();
   return 0;




exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
     return 1;
 end;


/
--动漫基地结束




--融合终端门户的货架提供给报表(毕辉要求做的) add by aiyan 2012-11-15  这个没有测试故放到月底上。

create or replace view vr_category
(categoryid, categoryname, parentcategoryid, state, changedate, relation)
as
(
select categoryID,name,parentCategoryID,decode(delflag,1,9,0,1) state,to_date(changeDate,'yyyy-mm-dd hh24:mi:ss'),relation,id
from t_r_category
where id not in('701','702')
union
select categoryid,name,parentid,decode(state,1,1,0,9) state,changeDate,'O' relation,id  from portalmo.t_d_category
);

create or replace view vr_goods
(goodsid, spcode, servicecode, spid, serviceid, contentid, categoryid, goodsname, umflag)
as
(
select t1.goodsid,t2.icpcode,t2.icpservid,t2.companyid,t2.productid,t2.contentid,t1.categoryid,t2.name, s.umflag
from t_r_reference t1 , t_r_gcontent t2 LEFT OUTER JOIN  v_service s on(t2.icpcode= s.icpcode and t2.icpservid=s.icpservid)
where t2.id = t1.refnodeid and ascii(substr(t2.id,1,1))>47 and ascii(substr(t2.id,1,1))<58

union
--xpas的非基地业务
select distinct t1.goodsid,t2.icpcode,t2.icpservid,t2.companyid,t2.productid,t2.contentid,t1.categoryid,t2.name, s.umflag
from  portalmo.t_d_reference t1,t_r_gcontent t2,v_service s
where  t2.icpcode= s.icpcode(+) and t2.icpservid=s.icpservid(+) and t2.contentid = t1.cid and t1.ctype='APP'

--XPAS的基地业务 毕总说基地业务不需要进入商品表，报表统计跟这个没关系，故删除以下
--union
--select distinct '' goodsid,''icpcode,''icpservid,''companyid,''productid,t1.cid,t1.categoryid,t1.cname,''umflag from mopas.t_d_reference t1 where t1.ctype!='APP'
);




insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.109_SSMS','MM1.1.1.115_SSMS');


commit;