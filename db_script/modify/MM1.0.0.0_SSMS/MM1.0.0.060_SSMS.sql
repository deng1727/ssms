---创建该视图供报表对a8音乐统计使用
create or replace view vr_a8_goods
(goodsid, contentid, categoryid, goodsname,singer,singerzone)
as
select t1.goodsid,
       t2.contentid,
       t1.categoryid,
       t2.name,
       t2.singer,
       t2.bigcatename
  from t_r_reference t1, t_r_gcontent t2
 where t2.id = t1.refnodeid
   and t2.id like 'a8%';


update t_resource set resourcevalue='货架名称只能包含汉字、字母、_ 和数字以及以下特殊字符!@#$%^-－()[]{}【】＊。！＠＃￥％……＆（）《》“”？：．· ' ,remard='只能汉字、英文字母、_ 、数字以及以下特殊字符!@#$%^-－()[]{}【】＊。！＠＃￥％……＆（）《》“”？：．· ' where resourcekey='RESOURCE_CATE_FIELD_CHECK_001';



--添加版本----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.053_SSMS','MM1.0.0.060_SSMS');


commit;