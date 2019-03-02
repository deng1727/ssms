
---------------------------------------------- 在t_r_Exportsql添加一条数据 开始----------------------------------------------

insert into t_r_Exportsql
  (id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportTypeOther,
   exportLine,
   fileName,
   filePath,
   ENCODER,
   exportByAuto,
   exportByUser,
   groupId)
values
  (88,
   'select d.programid VideoID
     ,d.name VName 
     ,'''' Type
     , c.fee Price 
     , '''' Star 
     ,'''' Dayplaynum 
     ,'''' totalPlayNum
     , '''' dayDownNum 
     ,'''' totalDownNum 
     ,to_char(to_date(d.updatetimev,''yyyy-mm-dd hh24:mi:ss''),''yyyyMMdd'')
     ,'''' Label 
     ,d.Vshortname Brief
     ,'''' Hot 
     , decode(d.type,2,1,2) ChildCategory 
     ,''all'' FullDevice 
     ,''''   imgurl1 
     ,''''  imgurl2
     ,''''     cid 
     ,d.CDURATION  playtime 
     ,d.displayname Category1
     ,p.key  Category2 
     ,p.value Tag 
 from t_v_dprogram d,(select f.fee fee,p.servid
 from  t_v_propkg p 
   left join t_v_PRODUCT f on p.dotfeecode=f.feecode) c,(select p.programid,p.cms_id,listagg(p.propertyvalue,''|'')within GROUP (order by p.programid,p.cms_id)  as value,listagg(p.propertykey,''|'')within GROUP (order by p.programid,p.cms_id)  as key from t_v_videospropertys p 
group by p.programid,p.cms_id) p where d.prdpack_id = c.servid(+) and d.programid = p.programid(+) and p.cms_id(+) = d.cmsid
and 
  d.LUPDATE > to_date((to_char(sysdate-1,'' yyyy - mm - dd'')||'' 07:00:00''),'' yyyy - mm - dd hh24 :mi :ss '')',
   '新视频业务数据同步',
   '2',
   5000,
   '0x01',
   22,
   'videonew_d',
   '/opt/aspire/product/chroot_panguso/panguso/video',
   'GB18030',
   '2',
   '1',
   '3');
---------------------------------------------- 在t_r_Exportsql添加一条数据 结束----------------------------------------------

---------------------------------------------- 在t_r_Exportsql添加一条数据 开始----------------------------------------------

insert into t_r_Exportsql
  (id,
   exportsql,
   exportname,
   exporttype,
   exportpagenum,
   exportTypeOther,
   exportLine,
   fileName,
   filePath,
   ENCODER,
   exportByAuto,
   exportByUser,
   groupId)
values
  (89,
   'select d.programid COLLECTID,d.name COLLECTNAME,d.VSHORTNAME DESCRIPTION,'''' TOTALPLAYNUM from t_v_dprogram d',
   '新视频内容集数据同步',
   '2',
   5000,
   '0x01',
   4,
   'vo_collectnew',
   '/opt/aspire/product/chroot_panguso/panguso/video',
   'GB18030',
   '2',
   '1',
   '3');
---------------------------------------------- 在t_r_Exportsql添加一条数据 结束----------------------------------------------

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM5.0.0.0.105_SSMS','MM5.0.0.0.305_SSMS');
commit;