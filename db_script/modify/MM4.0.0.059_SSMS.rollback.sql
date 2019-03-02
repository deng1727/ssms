drop table t_open_channels;
drop sequence SEQ_t_open_channels_id;
drop table t_open_operation_channel;
drop table t_open_channel_mo;
drop table t_open_channels_category;


-------------------- v_hotcontent��ͼ��ʼ---------------------------
 create or replace view v_hotcontent as
select t."ID",t."CONTENTID",t."NAME",t."CATENAME", rownum as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='���'
and g.id not in (select gcontentid from t_intervenor_gcontent_map t
where intervenorid = '2092'
)
order by t.hotlist desc, g.id desc ) t where rownum <=80
union
select t."ID",t."CONTENTID",t."NAME",t."CATENAME", rownum+80 as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='��Ϸ'
and g.id not in (select gcontentid from t_intervenor_gcontent_map t
where intervenorid = '2092'
)
order by t.hotlist desc, g.id desc ) t where rownum <=20;
-------------------- v_hotcontent��ͼ����---------------------------


delete DBVERSION where PATCHVERSION = 'MM4.0.0.0.059_SSMS' and LASTDBVERSION = 'MM4.0.0.0.055_SSMS';

commit;