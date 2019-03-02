

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
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.109_SSMS','MM1.1.1.115_SSMS');


commit;