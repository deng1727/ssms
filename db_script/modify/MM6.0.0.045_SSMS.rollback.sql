
ALTER table t_a_android_list drop column CHANNELDISPTYPE;

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
,nvl(d.daynum,0) as daynum,
decode(n.hotscore,null,0,n.hotscore) as hotscore,
decode(n.newRANK_HOT,null,0,n.newRANK_HOT) as newRANK_HOT,
decode(n.souar,null,0,n.souar) as souar,
decode(m.hotlist,null,-0.001*mg.sortid,m.hotlist) as hotlist,
decode(m.riselist,null,0,m.riselist) as riselist,
g.channeldisptype
  from
       t_r_gcontent g,
       t_a_dc_ppms_service   v,
       t_r_servenday_temp_a a,
       v_content_last    l,
       v_serven_sort    c,
       --v_content_newscore n,
       (select case
         when cn.content_id is null then
          co.contentid
         when co.contentid is null then
          cn.content_id
         else
          co.contentid
       end as content_id,
       cn.newRANK_HOT,
       cn.souar,
       case
         when nvl(cn.hotscore,0) < nvl(co.hotscore,0) then
          co.hotscore
         else
          cn.hotscore
       end as hotscore
  from v_content_newscore cn full join t_r_commerce co
 on cn.content_id = co.contentid)n,
       mid_table m,
       ( select distinct r.contentid from t_a_cm_device_resource r where r.pid is not null) r,
       (select g.contentid,row_number() over(order by g.plupddate desc) sortid from t_r_gcontent g where not exists(select 1 from mid_table m where g.contentid = m.appid))mg,
       (select contentid,sum(downcount) as daynum from t_a_content_downcount where trunc_lupdate=trunc(sysdate) group by contentid) d


 where l.contentid = g.contentid
   and v.icpcode = g.icpcode
   and v.icpservid = g.icpservid
   and v.contentid = g.contentid
   and g.contentid = a.CONTENT_ID
   and l.osid = '9'
   and g.contentid = c.CONTENT_ID
   and c.os_id=9
   and g.subtype !='16'
   and g.contentid = n.content_id(+)
   and g.contentid = m.appid(+)
   and g.contentid=d.contentid(+)
   and g.contentid=r.contentid
   and g.contentid=mg.contentid(+)
   and g.channeldisptype='0'
   and g.contentid not in (select contentid from t_rank_black);
