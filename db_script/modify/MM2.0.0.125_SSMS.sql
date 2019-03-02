-- Add/modify columns 
alter table T_INTERVENOR_CATEGORY_MAP add type VARCHAR2(3) default 1 not null;
-- Add comments to the columns 
comment on column T_INTERVENOR_CATEGORY_MAP.type
  is '当前干预榜单类型 1：货架榜单 2：商品库榜单';

 ----媒体化联合内容ID
 
 
 create or replace function coltorow(midId varchar) RETURN VARCHAR2 is
Result VARCHAR2(4000);
begin
FOR cur IN (SELECT t2.name FROM t_r_gcontent t2,v_article_reference v  WHERE t2.contentid=v.appid and  midId=v.contentid) LOOP
 exit when  lengthb(RESULT)+lengthb('{'||cur.Name||'};')  >= 4000;
 RESULT:=RESULT||'{'||cur.Name||'};';
END LOOP;
--RESULT:=rtrim(RESULT,'');
return(Result);
end coltorow;

 create or replace view  v_article_con_keyworld as
select coltorow( t.contentid ) as keyword, t.contentid
  from v_article_reference t
 --where g.contentid = t.appid
 group by t.contentid
 
 
 update t_r_exportsql t set t.EXPORTSQL='select v.CONTENTID,TITLE,LABEL||s.keyword as LABEL,PUBTIME from V_ARTICLE v,v_article_con_keyworld s  where v.contentid=s.contentid and  type = 3  and status = 4' where t.id in ('29','32');
 
 
 
 
  
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM2.0.0.0.119_SSMS','MM2.0.0.0.125_SSMS');

commit;

