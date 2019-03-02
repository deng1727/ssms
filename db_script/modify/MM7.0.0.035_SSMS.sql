alter table t_r_gcontent add (RiskTag varchar2(256) );
alter table t_r_gcontent add (AppType varchar2(2) );
alter table t_r_gcontent add (CtrlDev varchar2(128) );
alter table t_a_cm_device_resource add (RISKTAG varchar2(256) );


insert into T_R_EXPORTSQL_GROUP values('20','zhanghuan@aspirecn.com,dengshaobo@aspirecn.com',
'MMTV软件导出','0700','1','7','0','');

insert into T_R_EXPORTSQL_GROUP values('21','zhanghuan@aspirecn.com,dengshaobo@aspirecn.com',
'MMTV游戏导出','0700','1','7','0','');

insert into t_r_exportsql
values
  ('103',
   'select ''999000001''||substr(r.goodsid,-30),
       g.id,
       g.name,
       1 as temp1,
       g.singer,
       1 as temp2,
       g.spname,
       g.fulldevicename,
       ''O'' as temp3,
       s.mobileprice,
       g.averagemark,
       g.marketdate,
       g.keywords,
       g.introduction,
       g.scantimes,
       g.appcateid,
       g.chargetime,
       g.pvcid,
       g.cityid,
       g.othernet,
       decode(g.provider, ''B'', ''99'', g.subtype) as subtype,
       g.platform,
       h.marketingcha,
       g.logo3,
       g.logo4,
       g.COPYRIGHTFLAG
  from t_r_gcontent g, t_r_base b, v_service s, V_CM_CONTENT_DARKHORSE h,(select a.goodsid from (select f.*,
      row_number() over(partition by substr(f.goodsid,-30) order by f.id asc) as rank
  from t_r_reference f ) a   where a.rank=1) r
 where g.channeldisptype = ''0''
   and g.id = b.id
   and g.contentid = h.contentid(+)
   and g.icpcode = s.icpcode
   and g.icpservid = s.icpservid
   and g.contentid = s.contentid
   and (g.subtype is null or
       (g.subtype <> ''6'' and g.subtype <> ''11'' and g.subtype <> ''12'' and
       g.subtype <> ''16'' and g.subtype <> ''21''))
   and (g.provider != ''B'' or (g.provider = ''B'' and g.programid = ''-1''))
   and g.contentid = substr(r.goodsid,-12)
   and g.apptype = ''5''
   and g.catename=''软件''
 order by g.id',
   'MMTV软件导出',
   '2',
   '50000',
   '0x01',
   sysdate,
   '26',
   'mmtv_software',
   '/opt/aspire/product/chroot_panguso/panguso/mo',
   'GB18030',
   '2',
   '1',
   '',
   '20'
   )
   
   
   insert into t_r_exportsql
values
  ('104',
   'select ''999000001''||substr(r.goodsid,-30),
       g.id,
       g.name,
       1 as temp1,
       g.singer,
       1 as temp2,
       g.spname,
       g.fulldevicename,
       ''O'' as temp3,
       s.mobileprice,
       g.averagemark,
       g.marketdate,
       g.keywords,
       g.introduction,
       g.scantimes,
       g.appcateid,
       g.chargetime,
       g.pvcid,
       g.cityid,
       g.othernet,
       decode(g.provider, ''B'', ''99'', g.subtype) as subtype,
       g.platform,
       h.marketingcha,
       g.logo3,
       g.logo4,
       g.COPYRIGHTFLAG
  from t_r_gcontent g, t_r_base b, v_service s, V_CM_CONTENT_DARKHORSE h,(select a.goodsid from (select f.*,
      row_number() over(partition by substr(f.goodsid,-30) order by f.id asc) as rank
  from t_r_reference f ) a   where a.rank=1) r
 where g.channeldisptype = ''0''
   and g.id = b.id
   and g.contentid = h.contentid(+)
   and g.icpcode = s.icpcode
   and g.icpservid = s.icpservid
   and g.contentid = s.contentid
   and (g.subtype is null or
       (g.subtype <> ''6'' and g.subtype <> ''11'' and g.subtype <> ''12'' and
       g.subtype <> ''16'' and g.subtype <> ''21''))
   and (g.provider != ''B'' or (g.provider = ''B'' and g.programid = ''-1''))
   and g.contentid = substr(r.goodsid,-12)
   and g.apptype = ''5''
   and g.catename=''游戏''
 order by g.id',
   'MMTV游戏导出',
   '2',
   '50000',
   '0x01',
   sysdate,
   '26',
   'mmtv_game',
   '/opt/aspire/product/chroot_panguso/panguso/mo',
   'GB18030',
   '2',
   '1',
   '',
   '21'
   )
   
   
   commit;
