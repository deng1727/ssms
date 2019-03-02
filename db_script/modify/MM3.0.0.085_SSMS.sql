-- Create view
--����t_vo_video��t_vo_video_mid�ϲ���ͼ---
create or replace view v_vo_video_full as
select videoid,coderateid from  t_vo_video 
union all select videoid,coderateid from  t_vo_video_mid;

--������Ҫɾ����videoid��ͼ---
create or replace view v_vo_video_delete as
select distinct f.videoid from v_vo_video_full f 
   where not exists (select distinct a.videoid from v_vo_video_full a 
                         where a.videoid = f.videoid and a.coderateid in ('6','11','13','15','33','35','37','40','41'));


create or replace view  v_hotcontent as 
select t.*, rownum as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='���' 
order by t.hotlist desc, g.id desc ) t where rownum <=80
union
select t.*, rownum+80 as sortid from (
select   g.id,g.contentid,g.name,g.catename  from mid_table t,t_r_gcontent g where t.appid=g.contentid and g.catename='��Ϸ' 
order by t.hotlist desc, g.id desc ) t where rownum <=20
;


insert into t_caterule_cond_base (BASE_ID, BASE_NAME, BASE_SQL)
values (81, 'MM4.2�ص�Ӧ�û��ܹ���', 'select   g.id  from v_hotcontent g ');


commit;