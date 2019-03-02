 
 drop synonym s_report_MT_RANK_INDEX_D ;
drop table T_MT_RANK_INDEX_D;
drop table  T_MT_RANK_INDEX_D_TRA;
drop view  v_content_newscore;
drop table t_rb_type_detail;
drop table t_rb_type_count;
drop table t_rb_author_detail;
drop table t_rb_author_count;

drop view  v_rb_typeord_his;
drop view  v_rb_authorord_his;
drop table  t_rb_typeord_his;
drop table  t_rb_authorord_his;

alter table T_A_ANDROID_LIST drop column hotscore;
alter table T_A_ANDROID_LIST drop column newrank_hot;
alter table T_A_ANDROID_LIST drop column souar;


delete t_r_exportsql_por where id='9';
 
 ------回滚android_list视图-----
 create or replace view v_android_list as
select g.contentid,

--decode(v.mobileprice, 0, 1, 2)||to_char(trunc(l.createtime),'yyyymmdd')||decode(catename, '软件', 1, '游戏', 1, '主题', 2, 3)
--||to_char(l.createtime, 'hh24miss') as rank_new,

to_char(updatetime,'yymmdd')||decode(v.mobileprice, 0, 1, 0)||(2000000-to_char(l.createtime,'yymmdd'))||decode(catename, '软件', 2, '游戏', 2, '主题', 1, 0)||
(4000-to_char(l.createtime, 'hh24mi')) as rank_new

,(nvl(a.ADD_ORDER_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_all


,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_fee
,(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymmdd') as rank_hot
,(1000+nvl(c.scores,-200))*1000||(nvl(a.ADD_7DAYS_DOWN_COUNT,-1)+2+3*nvl(d.daynum,0))||to_char(l.CREATETIME,'yymm') as rank_scores

,decode(g.catename,'软件','appSoftWare','游戏','appGame','主题','appTheme','') as catename


,g.name,a.ADD_7DAYS_DOWN_COUNT,a.add_order_count,v.mobileprice
--decode(v.mobileprice, 0, 0, 10) as mobileprice_alias,
--trunc(l.createtime) as createtime_trunc,
--decode(catename, '软件', 1,  '主题', 2, 10) as catename_alias,
--to_char(l.createtime, 'hh24miss') as createtime_tochar,



,c.scores,l.createtime,l.updatetime,g.companyid
,nvl(d.daynum,0) as daynum
  from
       v_datacenter_cm_content g,
       t_a_dc_ppms_service   v,
       t_r_servenday_temp_a a,
       v_content_last    l,
       v_serven_sort    c,
       ( select distinct r.contentid from t_a_cm_device_resource r where r.pid is not null) r,
       (select contentid,sum(downcount) as daynum from t_a_content_downcount where trunc_lupdate=trunc(sysdate) group by contentid) d


 where l.contentid = g.contentid
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and g.contentid = a.CONTENT_ID
   and l.osid = '9'
   and g.contentid = c.CONTENT_ID
   and c.os_id=9
   and g.contentid=d.contentid(+)
   and g.contentid=r.contentid;

 
 
 delete DBVERSION where PATCHVERSION = 'MM3.0.0.035_SSMS' and LASTDBVERSION = 'MM3.0.0.029_SSMS';

commit;

